package com.logistics.deliveryscheduler.common;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.logistics.deliveryscheduler.common.errors.InvalidFieldException;
import com.logistics.deliveryscheduler.common.errors.MissingFieldsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Common controller for handling API exceptions.
 */
@ControllerAdvice
@RestController
public class GlobalExceptionHandler {

    /**
     * Logger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Returns HTTP 404 response in case of invalid request route.
     *
     * @param ex {@link NoHandlerFoundException}
     * @return 404 response
     */
    @ExceptionHandler
    public ResponseEntity<ApiResponse<Object>> handleNoHandlerFoundException(
            final NoHandlerFoundException ex
    ) {
        LOG.info("handler not found", ex);

        final ErrorData errorData = new ErrorData.Builder()
                .code(HttpStatus.NOT_FOUND.name())
                .message("invalid route")
                .build();

        final ApiResponse<Object> apiResponse = new ApiResponse.Builder<Object>()
                .status(HttpStatus.NOT_FOUND.value())
                .error(errorData)
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
    }

    /**
     * Returns HTTP 400 response in case of missing fields validation errors.
     *
     * @param ex {@link MissingFieldsException}
     * @return 400 response
     */
    @ExceptionHandler
    public ResponseEntity<ApiResponse<Object>> handleMissingFieldsException(final MissingFieldsException ex) {
        final List<ErrorData> errorDetails = new ArrayList<>();

        ex.getMissingFields().forEach(field -> {
            errorDetails.add(new ErrorData.Builder().target(field).message(field.concat(" missing")).build());
        });

        final ErrorData errorData = new ErrorData.Builder()
                .code(HttpStatus.BAD_REQUEST.name())
                .message(ex.getMessage())
                .details(errorDetails)
                .build();

        final ApiResponse<Object> apiResponse = new ApiResponse.Builder<Object>()
                .status(HttpStatus.BAD_REQUEST.value())
                .error(errorData)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }

    /**
     * Returns HTTP 400 response in case of invalid fields in request.
     *
     * @param ex {@link HttpMessageNotReadableException}
     * @return 400 response
     */
    @ExceptionHandler
    public ResponseEntity<ApiResponse<Object>> handleHttpMessageNotReadableException(
            final HttpMessageNotReadableException ex
    ) {
        final ErrorData errorData = new ErrorData.Builder()
                .code(HttpStatus.BAD_REQUEST.name())
                .message("Invalid data format")
                .build();

        final Throwable throwableCause = ex.getCause();

        // find target in case of invalid datatypes in request body
        if (throwableCause instanceof JsonMappingException) {
            final JsonMappingException jsonMappingException = (JsonMappingException) throwableCause;
            final List<JsonMappingException.Reference> fieldReferences = jsonMappingException.getPath();
            final String target = String.join(".", fieldReferences
                    .subList(0, fieldReferences.size())
                    .stream()
                    .map(ref -> ref.getFieldName())
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList())
            );

            errorData.setDetails(
                    Arrays.asList(new ErrorData.Builder().target(target).message(target.concat(" is invalid")).build())
            );
        }

        final ApiResponse<Object> apiResponse = new ApiResponse.Builder<Object>()
                .status(HttpStatus.BAD_REQUEST.value())
                .error(errorData)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }

    /**
     * Returns HTTP 400 response in case of invalid data in request.
     *
     * @param ex {@link InvalidFieldException}
     * @return 400 response
     */
    @ExceptionHandler
    public ResponseEntity<ApiResponse<Object>> handleInvalidFieldException(
            final InvalidFieldException ex
    ) {
        final ErrorData errorData = new ErrorData.Builder()
                .code(HttpStatus.BAD_REQUEST.name())
                .message(ex.getMessage())
                .build();

        final ApiResponse<Object> apiResponse = new ApiResponse.Builder<Object>()
                .status(HttpStatus.BAD_REQUEST.value())
                .error(errorData)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }

    /**
     * Catch-all mechanism for unexpected errors.
     * Returns HTTP 500 response.
     *
     * @param ex any unhandled {@link Exception}
     * @return 500 response
     */
    @ExceptionHandler
    public ResponseEntity<ApiResponse<Object>> handleGeneralException(final Exception ex) {
        LOG.error("internal server error", ex);

        final ErrorData errorData = new ErrorData.Builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.name())
                .target("target")
                .build();

        final ApiResponse<Object> apiResponse = new ApiResponse.Builder<Object>()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error(errorData)
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
    }

}
