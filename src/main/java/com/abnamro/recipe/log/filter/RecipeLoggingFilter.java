package com.abnamro.recipe.log.filter;

import com.abnamro.recipe.log.LogContent;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Slf4j
public class RecipeLoggingFilter extends OncePerRequestFilter {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        Date requestDate = Calendar.getInstance().getTime();
        try {
            filterChain.doFilter(requestWrapper, responseWrapper);
        } catch (Exception ex) {
            log.error("Unexpected exception for call {} {} : {} ", request.getMethod(), requestWrapper.getRequestURI(), ex.toString());
            throw ex;
        }
        LogContent logContent = prepareLogContent(requestWrapper, responseWrapper, requestDate);
        log.info(mapper.writeValueAsString(logContent));
        responseWrapper.copyBodyToResponse();
    }
    private LogContent prepareLogContent(ContentCachingRequestWrapper requestWrapper, ContentCachingResponseWrapper responseWrapper, Date requestDate){
        return LogContent.builder().id(UUID.randomUUID().toString()).url(requestWrapper.getRequestURI()).method(requestWrapper.getMethod())
                .request(getRequestBody(requestWrapper)).requestDate(requestDate).responseStatus(responseWrapper.getStatus())
                .response(getResponseBody(responseWrapper)).responseDate(Calendar.getInstance().getTime()).build();
    }

    private String getRequestBody(ContentCachingRequestWrapper requestWrapper) {
        byte[] requestBody = requestWrapper.getContentAsByteArray();
        return new String(requestBody, StandardCharsets.UTF_8);
    }

    private String getResponseBody(ContentCachingResponseWrapper responseWrapper) {
        byte[] responseBody = responseWrapper.getContentAsByteArray();
        return new String(responseBody, StandardCharsets.UTF_8);
    }
}