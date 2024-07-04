package com.payment.challange.infrastructure;

public class ApiResponseConstants {
    public static final String STATUS_200_OK = "OK";
    public static final String STATUS_201_CREATED = "Created";
    public static final String STATUS_400_BAD_REQUEST = "Bad request";
    public static final String STATUS_404_NOT_FOUND = "Not Found";
    public static final String STATUS_422_UNPROCESSABLE_ENTITY = "Unprocessable Entity";
    public static final String STATUS_500_INTERNAL_SERVER_ERROR = "Internal Server Error";

    private ApiResponseConstants() {
        throw new IllegalStateException("ApiResponseConstants class");
    }
}
