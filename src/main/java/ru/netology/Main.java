package ru.netology;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpHeaders;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;


public class Main {
    public static final String REMOTE_SERVICE_URL =
            "https://raw.githubusercontent.com/netology-code/jd-homeworks/master/http/task1/cats";
    public static ObjectMapper mapper = new ObjectMapper();


    public static void main(String[] args) throws IOException {
        CloseableHttpClient client =
                HttpClientBuilder.create().setUserAgent("Cat detective").
                        setDefaultRequestConfig(RequestConfig.custom().setConnectTimeout(5000).
                                setSocketTimeout(30000).setRedirectsEnabled(false).build()).build();


        HttpGet request = new HttpGet(REMOTE_SERVICE_URL);
        request.setHeader(HttpHeaders.ACCEPT, ContentType.APPLICATION_JSON.getMimeType());

        CloseableHttpResponse response = client.execute(request);
        InputStream cats = response.getEntity().getContent();

        List<Cat> catList = mapper.readValue(cats, new TypeReference<>() {
        });

        catList.stream().filter(value -> value.getUpvotes() != null && value.getUpvotes() > 0).forEach(System.out::println);

    }
}