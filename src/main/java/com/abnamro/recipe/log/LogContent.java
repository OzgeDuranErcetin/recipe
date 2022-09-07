package com.abnamro.recipe.log;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
@Builder
public class LogContent {

    private String id;
    private String method;
    private String url;
    private String request;
    private Date requestDate;
    private int responseStatus;
    private String response;
    private Date responseDate;
}
