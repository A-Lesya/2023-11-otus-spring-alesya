package ru.otus.hw.exceptions;

public class NoCorrectAnswerException extends RuntimeException {
    public NoCorrectAnswerException(String message) {
        super(message);
    }
}
