package com.dosug.app.respose.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;
import java.util.List;

/**
 * work when we need return SMTH or errors;
 * @param <ResultData>
 */
public class Response<ResultData> {

    private static final String SUCCESS = "success";
    private static final String FAILURE = "failure";

    @JsonProperty
    private String result;

    @JsonProperty
    private ResultData resultData;

    @JsonProperty
    private List<ErrorForResponse> errors;

    // чтобы не было открытого конструктора
    public Response() {}

    public Response success(ResultData authToken) {
        this.result = SUCCESS;
        this.resultData = authToken;
        return this;
    }

    public Response failure(List<ErrorForResponse> errors) {
        this.result = FAILURE;
        this.errors = errors;
        return this;
    }

    public Response failure(ErrorForResponse error) {
        return failure(Arrays.asList(error));
    }
}
