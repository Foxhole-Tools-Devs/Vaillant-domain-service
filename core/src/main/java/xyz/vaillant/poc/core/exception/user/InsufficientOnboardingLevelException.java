package xyz.vaillant.poc.core.exception.user;

public class InsufficientOnboardingLevelException extends RuntimeException {
    public InsufficientOnboardingLevelException(String message) {
        super(message);
    }
}