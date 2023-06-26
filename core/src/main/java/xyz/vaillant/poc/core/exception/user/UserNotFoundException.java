package xyz.vaillant.poc.core.exception.user;

public class UserNotFoundException extends RuntimeException {

    private final Long id;

    public UserNotFoundException(String message, final Long userId) {
        super(message);
        this.id = userId;
    }

    @Override
    public void printStackTrace() {
        System.err.println("User id : " + id);
        super.printStackTrace();
    }
}
