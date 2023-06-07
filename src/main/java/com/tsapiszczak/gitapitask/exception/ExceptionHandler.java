package com.tsapiszczak.gitapitask.exception;


import lombok.SneakyThrows;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionHandler {
    @SneakyThrows
    @org.springframework.web.bind.annotation.ExceptionHandler(value = {APIException.class})
    public ResponseEntity<Object> handleRequestException(APIException e) {
        JSONObject responseJson = new JSONObject();
        responseJson.put("status", e.getStatusCode());
        responseJson.put("Message", e.getMessage());

        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(responseJson.toString(), header, HttpStatus.valueOf(e.getStatusCode()));
    }
}
