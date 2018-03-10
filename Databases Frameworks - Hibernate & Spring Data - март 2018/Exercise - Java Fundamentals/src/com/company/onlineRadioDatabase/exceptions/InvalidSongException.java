package com.company.onlineRadioDatabase.exceptions;

public class InvalidSongException extends IllegalArgumentException {

    private static final String DEFFAULT_MESSAGE = "Invalid song.";

    public InvalidSongException() {
        super(DEFFAULT_MESSAGE);
    }

    public InvalidSongException(String message) {
        super(message);
    }
}
