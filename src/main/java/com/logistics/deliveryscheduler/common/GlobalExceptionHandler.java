package com.logistics.deliveryscheduler.common;

import com.logistics.deliveryscheduler.common.errors.DeliveriesNotPossibleException;
import com.logistics.deliveryscheduler.common.errors.MissingFieldsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.ArrayList;
import java.util.List;

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
     * Returns HTTP 422 response in case deliveries are not possible.
     *
     * @param ex {@link DeliveriesNotPossibleException}
     * @return 422 response
     */
    @ExceptionHandler
    public ResponseEntity<ApiResponse<Object>> handleDeliveriesNotPossibleException(
            final DeliveriesNotPossibleException ex
    ) {
        final ErrorData errorData = new ErrorData.Builder()
                .code(HttpStatus.UNPROCESSABLE_ENTITY.name())
                .message(ex.getMessage())
                .build();

        final ApiResponse<Object> apiResponse = new ApiResponse.Builder<Object>()
                .status(HttpStatus.UNPROCESSABLE_ENTITY.value())
                .error(errorData)
                .build();

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(apiResponse);
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
