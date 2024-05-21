package ru.otus.hw.dao.dto;

import org.springframework.stereotype.Component;
import ru.otus.hw.domain.Question;
import ru.otus.hw.exceptions.NoCorrectAnswerException;

@Component
public class QuestionConverter {
    public QuestionForUIDto convert(Question question) {
        var textWithPossibleAnswers = new StringBuilder(question.text());
        Integer expectedCorrectAnswer = null;
        textWithPossibleAnswers.append(System.lineSeparator());

        for (int i = 0; i < question.answers().size(); i++) {
            var indexDisplayedToUser = i + 1;
            var currentAnswer = question.answers().get(i);
            if (currentAnswer.isCorrect()) {
                expectedCorrectAnswer = indexDisplayedToUser;
            }

            var answerText = currentAnswer.text();
            textWithPossibleAnswers.append(String.format("%d. %s%s",
                    indexDisplayedToUser, answerText, System.lineSeparator()));
        }

        if (expectedCorrectAnswer == null) {
            var message = String.format("Correct answer is absent for question %s", question.text());
            throw new NoCorrectAnswerException(message);
        }

        return new QuestionForUIDto(textWithPossibleAnswers.toString(), expectedCorrectAnswer);
    }
}
