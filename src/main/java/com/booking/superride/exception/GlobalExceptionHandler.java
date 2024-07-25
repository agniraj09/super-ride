package com.booking.superride.exception;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(TaxiNotFoundException.class)
    ProblemDetail handleTaxiNotFoundException(TaxiNotFoundException exception) {
        log.error(exception.getMessage(), exception);
        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exception.getMessage());
        problemDetail.setTitle("Taxi not available");
        // problemDetail.setProperty("stackTrace", getStackTraceAsString(exception));
        return problemDetail;
    }

    @ExceptionHandler(DuplicateDataException.class)
    ProblemDetail handleDuplicateDataException(DuplicateDataException exception) {
        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, exception.getMessage());
        problemDetail.setTitle("Duplicate data");
        return problemDetail;
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, MethodArgumentTypeMismatchException.class})
    ProblemDetail handleMethodArgumentExceptions(Exception exception) {
        List<String> errors;

        if (exception instanceof MethodArgumentNotValidException e) {
            errors = e.getBindingResult().getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
        } else {
            errors = List.of("Invalid input: " + ((MethodArgumentTypeMismatchException) exception).getValue());
        }

        var problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setProperties(Map.of("detail", errors));
        return problemDetail;
    }

    private String getStackTraceAsString(Exception exception) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        exception.printStackTrace(printWriter);
        return stringWriter.toString();
    }
}
