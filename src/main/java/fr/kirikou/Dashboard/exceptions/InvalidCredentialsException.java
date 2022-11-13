package fr.kirikou.Dashboard.exceptions;

public class InvalidCredentialsException extends Exception {
    public InvalidCredentialsException(String error) {
        super(error);
    }
}
