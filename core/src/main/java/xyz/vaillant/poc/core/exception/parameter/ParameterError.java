package xyz.vaillant.poc.core.exception.parameter;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@ToString
@Getter
public class ParameterError<T> {
    ParameterErrorType parameterErrorType;
    Class<T> aClass;
    String name;
    String description;
}
