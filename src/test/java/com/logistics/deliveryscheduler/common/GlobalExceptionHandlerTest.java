package com.logistics.deliveryscheduler.common;

import static org.junit.jupiter.api.Assertions.*;

import com.logistics.deliveryscheduler.common.errors.MissingFieldsException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.logistics.deliveryscheduler.common.errors.DeliveriesNotPossibleException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class GlobalExceptionHandlerTest {

    @Test
    public void testHandleNoHandlerFoundException() {
        final GlobalExceptionHandler handler = new GlobalExceptionHandler();

        final ErrorData errorData = new ErrorData.Builder()
                .code(HttpStatus.NOT_FOUND.name())
                .message("invalid route")
                .build();

        final ApiResponse<Object> responseData = new ApiResponse.Builder<Object>()
                .status(HttpStatus.NOT_FOUND.value())
                .error(errorData)
                .build();

        final ResponseEntity<ApiResponse<Object>> expected = ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(responseData);

        final ResponseEntity<ApiResponse<Object>> actual = handler
                .handleNoHandlerFoundException(new NoHandlerFoundException("GET", "/", new HttpHeaders()));

        assertEquals(expected.getStatusCode(), actual.getStatusCode());
        assertNull(actual.getBody().getData());
        assertEquals(expected.getBody().getError().getCode(), actual.getBody().getError().getCode());
    }

    @Test
    public void testHandleMissingFieldsException() {
        final GlobalExceptionHandler handler = new GlobalExceptionHandler();
        MissingFieldsException missingFieldsException = new MissingFieldsException("message", Arrays.asList("a", "b"));
        final List<ErrorData> errorDetails = new ArrayList<>();

        missingFieldsException.getMissingFields().forEach(field -> {
            errorDetails.add(new ErrorData.Builder().target(field).message(field.concat(" missing")).build());
        });

        final ErrorData errorData = new ErrorData.Builder()
                .code(HttpStatus.BAD_REQUEST.name())
                .message(missingFieldsException.getMessage())
                .details(errorDetails)
                .build();

        final ApiResponse<Object> responseData = new ApiResponse.Builder<Object>()
                .status(HttpStatus.BAD_REQUEST.value())
                .error(errorData)
                .build();

        final ResponseEntity<ApiResponse<Object>> expected = ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(responseData);

        final ResponseEntity<ApiResponse<Object>> actual = handler
                .handleMissingFieldsException(missingFieldsException);

        assertEquals(expected.getStatusCode(), actual.getStatusCode());
        assertNull(actual.getBody().getData());
        assertEquals(expected.getBody().getError().getCode(), actual.getBody().getError().getCode());
    }

    @Test
    public void testHandleDeliveriesNotPossibleException() {
        final GlobalExceptionHandler handler = new GlobalExceptionHandler();

        final ErrorData errorData = new ErrorData.Builder()
                .code(HttpStatus.UNPROCESSABLE_ENTITY.name())
                .message("Deliveries not possible")
                .build();

        final ApiResponse<Object> responseData = new ApiResponse.Builder<Object>()
                .status(HttpStatus.UNPROCESSABLE_ENTITY.value())
                .error(errorData)
                .build();

        final ResponseEntity<ApiResponse<Object>> expected = ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(responseData);

        final ResponseEntity<ApiResponse<Object>> actual = handler
                .handleDeliveriesNotPossibleException(new DeliveriesNotPossibleException("Deliveries not possible"));

        assertEquals(expected.getStatusCode(), actual.getStatusCode());
        assertNull(actual.getBody().getData());
        assertEquals(expected.getBody().getError().getCode(), actual.getBody().getError().getCode());
    }

    @Test
    public void testHandleGeneralException() {
        final GlobalExceptionHandler handler = new GlobalExceptionHandler();

        final ErrorData errorData = new ErrorData.Builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.name())
                .message("internal server error")
                .build();

        final ApiResponse<Object> responseData = new ApiResponse.Builder<Object>()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error(errorData)
                .build();

        final ResponseEntity<ApiResponse<Object>> expected = ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(responseData);

        final ResponseEntity<ApiResponse<Object>> actual = handler
                .handleGeneralException(new RuntimeException("error"));

        assertEquals(expected.getStatusCode(), actual.getStatusCode());
        assertNull(actual.getBody().getData());
        assertEquals(expected.getBody().getError().getCode(), actual.getBody().getError().getCode());
    }
}
