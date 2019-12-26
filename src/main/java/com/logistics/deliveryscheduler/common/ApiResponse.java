package com.logistics.deliveryscheduler.common;

import java.io.Serializable;

import lombok.Getter;

/**
 * This class represents the general response structure of the api.
 * @param <T> response data
 */
@Getter
public final class ApiResponse<T> implements Serializable {

    /**
     * HTTP status.
     */
    private Integer status;

    /**
     * Error data.
     */
    private ErrorData error;

    /**
     * Response data.
     */
    private T data;

    private ApiResponse(final Builder<T> builder) {
        this.status = builder.status;
        this.error = builder.error;
        this.data = builder.data;
    }

    /**
     * Builder for {@link ApiResponse}.
     * @param <T> response data type
     */
    public static final class Builder<T> {

        /**
         * Status value.
         */
        private Integer status;

        /**
         * Error value.
         */
        private ErrorData error;

        /**
         * Data value.
         */
        private T data;

        /**
         * Set HTTP status.
         * @param statusValue value for status
         * @return builder
         */
        public Builder<T> status(final Integer statusValue) {
            this.status = statusValue;

            return this;
        }

        /**
         * Set error data.
         * @param errorValue value for error
         * @return builder
         */
        public Builder<T> error(final ErrorData errorValue) {
            this.error = errorValue;

            return this;
        }

        /**
         * Set response data.
         * @param dataValue value for data
         * @return builder
         */
        public Builder<T> data(final T dataValue) {
            this.data = dataValue;

            return this;
        }

        /**
         * @return instance of {@link ApiResponse}.
         */
        public ApiResponse<T> build() {
            return new ApiResponse<>(this);
        }
    }
}
