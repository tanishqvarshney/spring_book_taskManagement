package com.task.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReturnResponse {
    private String message;
    private Object data;

    public static ReturnResponse data(Object data) {
        return new ReturnResponse(null, data);
    }

    public static ReturnResponse message(String message) {
        return new ReturnResponse(message, null);
    }

}
