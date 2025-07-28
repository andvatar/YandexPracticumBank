package ru.yandex.practicum.tarasov.cash.configuration;

import feign.FeignException;
import feign.Response;
import feign.RetryableException;
import feign.codec.ErrorDecoder;

import static feign.FeignException.errorStatus;

public class CustomErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder defaultErrorDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        FeignException exception = errorStatus(methodKey, response);

        if(response.status() >= 400 && response.status() <= 599) {
            RetryableException e = new RetryableException(response.status(),
                    exception.getMessage(),
                    response.request().httpMethod(),
                    (Long) null,
                    response.request());

            System.out.println("FeignException: " + e);

            return e;
        }
        return defaultErrorDecoder.decode(methodKey, response);
    }
}
