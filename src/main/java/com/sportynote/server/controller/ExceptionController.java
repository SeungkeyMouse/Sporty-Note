package com.sportynote.server.controller;

import com.sportynote.server.domain.Routine;
import com.sportynote.server.repository.query.errors.ErrorResponse;
import com.sportynote.server.type.SocialType;
import com.sportynote.server.type.exception.ErrorCode;
import com.sportynote.server.type.exception.ExceptionCode;
import com.sportynote.server.type.exception.RestApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class ExceptionController extends ResponseEntityExceptionHandler {

    @ExceptionHandler({RestApiException.class,IllegalArgumentException.class})
    public ResponseEntity<?> handleCustomException(RestApiException e) {
        ErrorCode errorCode = e.getErrorCode();
        return handleExceptionInternal(errorCode);
    }

//    @ExceptionHandler(IllegalArgumentException.class)
//    public ResponseEntity<Object> handleIllegalArgument(IllegalArgumentException e) {
//        log.warn("handleIllegalArgument", e);
//        System.out.println("2222");
//        ErrorCode errorCode = ExceptionCode.INVALID_PARAMETER;
//        return handleExceptionInternal(errorCode);
//    }

//    @ExceptionHandler({RuntimeException.class})
//    public ResponseEntity<Object> handleAllException(Exception ex) {
//        log.warn("handleAllException", ex);
//        System.out.println("ex,"+ex);
//        ErrorCode errorCode = ExceptionCode.INTERNAL_SERVER_ERROR;
//        return handleExceptionInternal(errorCode);
//    }




    private ResponseEntity<Object> handleExceptionInternal(ErrorCode errorCode) {
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(makeErrorResponse(errorCode));
    }

    private ErrorResponse makeErrorResponse(ErrorCode errorCode) {
        return ErrorResponse.toErrorResponse(errorCode);
    }







    private ResponseEntity<Object> handleExceptionInternal(ErrorCode errorCode, String message) {
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(makeErrorResponse(errorCode, message));
    }

    private ErrorResponse makeErrorResponse(ErrorCode errorCode, String message) {
        return ErrorResponse.builder()
                .code(errorCode.name())
                .message(message)
                .build();
    }
}