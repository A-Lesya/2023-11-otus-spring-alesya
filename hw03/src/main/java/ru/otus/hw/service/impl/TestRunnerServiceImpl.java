package ru.otus.hw.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;
import ru.otus.hw.exceptions.QuestionReadException;
import ru.otus.hw.service.LocalizedIOService;
import ru.otus.hw.service.ResultService;
import ru.otus.hw.service.StudentService;
import ru.otus.hw.service.TestRunnerService;
import ru.otus.hw.service.TestService;

@Service
@RequiredArgsConstructor
@Slf4j
public class TestRunnerServiceImpl implements TestRunnerService {

    private final TestService testService;

    private final StudentService studentService;

    private final ResultService resultService;

    private final LocalizedIOService ioService;

    @Override
    public void run() {
        var student = studentService.determineCurrentStudent();

        var testResult = executeTestWithCatchingException(student);

        if (testResult != null) {
            resultService.showResult(testResult);
        }
    }

    private TestResult executeTestWithCatchingException(Student student) {
        try {
            return testService.executeTestFor(student);
        } catch (QuestionReadException e) {
            log.error(e.getMessage(), e);
            ioService.printLineLocalized("TestRunnerService.question.read.exception");
        }

        return null;
    }
}
