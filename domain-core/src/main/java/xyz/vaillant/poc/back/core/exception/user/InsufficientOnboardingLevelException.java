package xyz.vaillant.poc.back.core.exception.user;

public class InsufficientOnboardingLevelException extends RuntimeException {
    public InsufficientOnboardingLevelException(String message) {
        super(message);
    }
}