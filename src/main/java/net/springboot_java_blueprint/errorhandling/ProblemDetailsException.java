package net.springboot_java_blueprint.errorhandling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class ProblemDetailsException extends RuntimeException {
    private final transient ProblemDetail problemDetail;

    public ProblemDetailsException(HttpStatus status, String title, String detail) {
        this(status, title, detail, Map.of());
    }

    public ProblemDetailsException(HttpStatus status, String title, String detail, Map<String, Object> extensions) {
        super(detail);
        problemDetail = ProblemDetail.forStatusAndDetail(status, detail);
        problemDetail.setTitle(toPascalCase(status) + " - " + title);
        extensions.forEach(problemDetail::setProperty);
    }

    public ProblemDetail getProblemDetail() {
        return problemDetail;
    }

    private static String toPascalCase(HttpStatus status) {
        return Arrays.stream(status.name().split("_"))
                .map(word -> word.charAt(0) + word.substring(1).toLowerCase())
                .collect(Collectors.joining());
    }
}
