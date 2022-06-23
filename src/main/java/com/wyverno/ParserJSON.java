package com.wyverno;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface ParserJSON {
    String toJSON() throws JsonProcessingException;
}
