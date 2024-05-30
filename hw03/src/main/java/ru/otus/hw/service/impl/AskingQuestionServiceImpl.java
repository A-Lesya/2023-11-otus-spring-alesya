package ru.otus.hw.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.dao.dto.QuestionConverter;
import ru.otus.hw.dao.dto.QuestionForUIDto;
import ru.otus.hw.domain.Question;
import ru.otus.hw.service.AskingQuestionService;
import ru.otus.hw.service.LocalizedIOService;

@Service
@RequiredArgsConstructor
public class AskingQuestionServiceImpl implements AskingQuestionService {

    private final LocalizedIOService ioService;

    private final QuestionConverter questionConverter;

    @Override
    public boolean askQuestion(Question question) {

        QuestionForUIDto questionForUI = questionConverter.convert(question);

        askQuestion(questionForUI);

        var studentAnswer = readAnswer(question);

        return studentAnswer == questionForUI.expectedCorrectAnswer();
    }

    private void askQuestion(QuestionForUIDto questionForUI) {
        ioService.printLine("");
        ioService.printLine(questionForUI.textWithPossibleAnswers());
    }

    private int readAnswer(Question question) {
        int min = 1;
        int max = question.answers().size();
        return ioService.readIntForRangeWithPromptLocalized(
                min,
                max,
                "TestService.answer.index.reading",
                "TestService.answer.index.reading.error"
        );

    }
}
