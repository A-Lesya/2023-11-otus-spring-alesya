package ru.otus.hw.dao;

import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.dao.dto.QuestionDto;
import ru.otus.hw.domain.Question;
import ru.otus.hw.exceptions.QuestionReadException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RequiredArgsConstructor
@Component
public class CsvQuestionDao implements QuestionDao {
    private final TestFileNameProvider fileNameProvider;

    @Override
    public List<Question> findAll() {
        var testFileName = fileNameProvider.getTestFileName();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(testFileName);

        if (inputStream == null) {
            throw new QuestionReadException("file not found: " + testFileName, null);
        }

        try (Reader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8)) {
            return new CsvToBeanBuilder<QuestionDto>(reader)
                    .withSeparator(';')
                    .withSkipLines(1)
                    .withType(QuestionDto.class)
                    .build()
                    .parse()
                    .stream()
                    .map(QuestionDto::toDomainObject)
                    .toList();
        } catch (IOException e) {
            throw new QuestionReadException("File reading error", e);
        }
    }
}
