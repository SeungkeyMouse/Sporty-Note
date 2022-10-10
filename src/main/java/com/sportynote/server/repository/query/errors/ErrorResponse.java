package com.sportynote.server.repository.query.errors;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sportynote.server.domain.Machine;
import com.sportynote.server.domain.Record;
import com.sportynote.server.domain.UserBasic;
import com.sportynote.server.type.exception.ErrorCode;
import com.sportynote.server.type.exception.ExceptionCode;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.stream.DoubleStream;

@Getter
@Builder
@RequiredArgsConstructor
public class ErrorResponse {

    private final String code;
    private final String message;

    public static ErrorResponse toErrorResponse(ErrorCode errorCode) {
        return ErrorResponse.builder()
                .code(errorCode.name())
                .message(errorCode.getMessage())
                .build();
    }


}
