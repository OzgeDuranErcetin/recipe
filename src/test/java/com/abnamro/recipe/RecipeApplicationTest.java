package com.abnamro.recipe;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class RecipeApplicationTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void whenSwaggerEndpointCalled_thenReturnsSwagger() {
        ResponseEntity<String> swaggerResponse =
                testRestTemplate.getForEntity("/swagger-ui/index.html", String.class);

        assertEquals(HttpStatus.OK, swaggerResponse.getStatusCode());
        assertTrue(swaggerResponse.getBody().contains("swagger-ui"));
        assertEquals(MediaType.TEXT_HTML, swaggerResponse.getHeaders().getContentType());
    }

}