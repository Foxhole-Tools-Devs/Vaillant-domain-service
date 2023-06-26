package xyz.vaillant.poc.core.exception.parameter;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ParameterException extends RuntimeException {

    private final List<ParameterError> parameterErrors;

    ParameterException(List<ParameterError> parameterErrors) {
        this.parameterErrors = parameterErrors;
    }

    @Override
    public void printStackTrace() {
        parameterErrors.forEach(parameterError -> System.err.println(parameterError.toString()));
        super.printStackTrace();
    }

    public static class ParameterExceptionBuilder<E> {

        private final List<ParameterError> parameterErrors = new ArrayList<>();

        public static ParameterExceptionBuilder createParameterException() {
            return new ParameterExceptionBuilder();
        }

        public ParameterExceptionBuilder addMissingParameter(Class aClass, String name) {
            parameterErrors.add(new ParameterError<E>(ParameterErrorType.MISSING, aClass, name, ""));
            return this;
        }

        public ParameterExceptionBuilder addMissingParameter(Class aClass, String name, String description) {
            parameterErrors.add(new ParameterError<E>(ParameterErrorType.MISSING , aClass, name, description));
            return this;
        }

        public ParameterExceptionBuilder addBadValueParameter(Class aClass, String name) {
            parameterErrors.add(new ParameterError<E>(ParameterErrorType.BAD_VALUE, aClass, name, ""));
            return this;
        }

        public ParameterExceptionBuilder addBadValueParameter(Class aClass, String name, String description) {
            parameterErrors.add(new ParameterError<E>(ParameterErrorType.BAD_VALUE , aClass, name, description));
            return this;
        }

        public ParameterException build() {
            return new ParameterException(parameterErrors);
        }
    }
}
