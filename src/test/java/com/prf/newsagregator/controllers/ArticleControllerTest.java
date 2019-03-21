package com.prf.newsagregator.controllers;

import com.fasterxml.jackson.databind.annotation.NoClass;
import com.prf.newsagregator.common.BaseControllerTest;
import com.prf.newsagregator.dtos.RestResponsePage;
import com.prf.newsagregator.entities.Article;
import com.prf.newsagregator.repositories.ArticleRepository;
import net.minidev.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {
                "classpath:sql/create_articles.sql"
        }),
        @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = {
                "classpath:sql/delete_articles.sql"
        })
})
public class ArticleControllerTest extends BaseControllerTest {
    
    private String ARTICLE_BASE_URL;
    
    private String jwtToken;
    
    @Autowired
    RestTemplate restTemplate;
    
    @Autowired
    ArticleRepository articleRepository;
    
    @Value("${secret}")
    private String SECRET;
    
    @Before
    public void setUp() {
        ARTICLE_BASE_URL = "http://localhost:" + serverPort;
        requestJwtToken();
    }
    
    @Test
    public void testFindPaginated() {
        HttpHeaders headers = initHttpHeaders();    
        HttpEntity<String> entity = new HttpEntity<>(headers);
        
        ResponseEntity<RestResponsePage<Article>> response = restTemplate
                .exchange(ARTICLE_BASE_URL + "/api/article/paginated", HttpMethod.GET, entity, 
                        new ParameterizedTypeReference<RestResponsePage<Article>>() {});
        
        List<Article> articlePage = response.getBody().getContent();
    
        Assert.assertEquals(20, articlePage.size());
    }
    
    @Test
    public void testDeleteAll() {    
        HttpHeaders headers = initHttpHeaders();    
        HttpEntity<String> entity = new HttpEntity<>(headers);
        
        restTemplate.exchange(ARTICLE_BASE_URL + "/api/article/delete", HttpMethod.DELETE, entity, NoClass.class);
    
        Assert.assertEquals(0, articleRepository.findAll().size());
    }
    
    @Test
    public void testFindRecent() {
        HttpHeaders headers = initHttpHeaders();    
        HttpEntity<String> entity = new HttpEntity<>(headers);
        
        ResponseEntity<List<Article>> response = restTemplate
                .exchange(ARTICLE_BASE_URL + "/api/article", HttpMethod.GET, entity, 
                        new ParameterizedTypeReference<List<Article>>() {});
        
        List<Article> recentArticles = response.getBody();
    
        Assert.assertEquals(10, recentArticles.size());
    }
    
    @Test
    public void testDeleteBySourceId() {    
        HttpHeaders headers = initHttpHeaders();    
        HttpEntity<String> entity = new HttpEntity<>(headers);
        
        Assert.assertEquals(20, articleRepository.findAll().size());
        
        restTemplate.exchange(ARTICLE_BASE_URL + "/api/article/delete/abc-news", HttpMethod.DELETE, entity, 
                NoClass.class);
    
        Assert.assertEquals(0, articleRepository.findAll().size());
    }
    
    private void requestJwtToken() {
        JSONObject payload = new JSONObject();
        
        payload.put("id", 1);
        payload.put("username", "jsmith");
        payload.put("role", "admin");
        payload.put("password", "$2a$10$EQXlrL9BjMN8aui2n06agOK8pEyXpUU8v2Mt1HWJ8FOr.Z1j/MyMq");
        
        HttpHeaders headers = new HttpHeaders();
        
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<>(payload.toJSONString(), headers);
        
        ResponseEntity<String> tokenResponse = restTemplate
                .exchange(ARTICLE_BASE_URL + "/token", HttpMethod.POST, httpEntity, String.class);
        
        jwtToken = tokenResponse.getBody();
    }
    
    private HttpHeaders initHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Token " + jwtToken);
        return headers;
    }    
}
