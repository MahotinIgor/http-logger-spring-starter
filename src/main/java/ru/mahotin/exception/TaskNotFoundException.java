package ru.mahotin.exception;

public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException(
            final String message
    ) {
        super(message);
    }
}