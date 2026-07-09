package com.bruno.medconnectcenter.exceptions;

public record FieldMessage(

        String fieldName,
        String message
) {
}
