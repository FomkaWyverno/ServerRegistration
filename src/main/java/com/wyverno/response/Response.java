package com.wyverno.response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wyverno.ParserJSON;

public class Response implements ParserJSON {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private final int requestID;
    private final Type type;
    private final int code;
    private final String data;


    public Response(int requestID, Type type, int code, String data) {
        this.requestID = requestID;
        this.type = type;
        this.code = code;
        this.data = data;
    }

    public int getRequestID() {
        return requestID;
    }

    public Type getType() {
        return type;
    }

    public int getCode() {
        return code;
    }

    public String getData() {
        return data;
    }

    @Override
    public String toJSON() throws JsonProcessingException {
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(this);
    }

    public enum Type {
        registration, login;
    }
}
