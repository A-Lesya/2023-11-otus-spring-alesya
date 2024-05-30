package ru.otus.hw.service.impl;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import ru.otus.hw.domain.Student;
import ru.otus.hw.service.AskingQuestionService;
import ru.otus.hw.service.LocalizedIOService;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.argThat;

@DisplayName("service TestServiceImpl")
class TestServiceImplTest {
    private final Random random = new Random();

    private final List<Question> questions = generateQuestions();

    private Integer correctAnswersCount = 0;


    @Test
    @DisplayName("asks all the given questions and nothing else and saves answers correctly")
    void shouldAskAllTheGivenQuestionsAndNothingElseAndSaveAnswersCorrectly() {
        var ioService = Mockito.mock(LocalizedIOService.class);
        var questionDao = buildQuestionDaoMock();
        var askingQuestionService = buildAskingQuestionService();

        var testService = new TestServiceImpl(ioService, questionDao, askingQuestionService);

        var student = generateStudent();
        var testResult = testService.executeTestFor(student);

        questions.forEach(question -> Mockito.verify(askingQuestionService).askQuestion(argThat(q -> q == question)));

        assertThat(testResult.getRightAnswersCount()).isSameAs(correctAnswersCount);

        assertThat(testResult.getAnsweredQuestions()).hasSameElementsAs(questions);
    }

    private QuestionDao buildQuestionDaoMock() {
        var questionDao = Mockito.mock(QuestionDao.class);

        Mockito.when(questionDao.findAll()).thenReturn(questions);

        return questionDao;
    }

    private AskingQuestionService buildAskingQuestionService() {
        var askingQuestionService = Mockito.mock(AskingQuestionService.class);

        Mockito.when(askingQuestionService.askQuestion(argThat(questions::contains)))
                .thenAnswer((input) -> {
                    boolean answerIsCorrect = random.nextBoolean();

                    if (answerIsCorrect) {
                        correctAnswersCount++;
                    }

                    return answerIsCorrect;
                });

        Mockito.when(askingQuestionService.askQuestion(argThat(question -> !questions.contains(question))))
                .thenThrow(new IllegalArgumentException("Asking unexpected question"));

        return askingQuestionService;
    }

    private Student generateStudent() {
        var firstName = RandomStringUtils.randomAlphabetic(5, 10);
        var lastName = RandomStringUtils.randomAlphabetic(5, 10);
        return new Student(firstName, lastName);
    }

    private List<Question> generateQuestions() {
        List<Question> questions = new LinkedList<>();

        var questionsNumber = random.nextInt(5, 10);

        for (int i = 0; i < questionsNumber; i++) {
            var answers = generatePossibleAnswers();
            var text = generateText();
            var question = new Question(text, answers);

            questions.add(question);
        }

        return questions;
    }

    private List<Answer> generatePossibleAnswers() {
        List<Answer> answers = new ArrayList<>();
        int answersNumber = random.nextInt(2, 10);
        for (int i = 0; i < answersNumber; i++) {
            var correctAnswerIndex = random.nextInt(answersNumber);
            var isCorrect = i == correctAnswerIndex;

            var text = generateText();

            answers.add(new Answer(text, isCorrect));
        }

        return answers;
    }

    private String generateText() {
        return RandomStringUtils.randomAlphabetic(10, 50);
    }
}