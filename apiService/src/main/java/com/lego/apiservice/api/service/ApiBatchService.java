package com.lego.apiservice.api.service;

import com.lego.apiservice.api.entity.domain.Api;
import com.lego.apiservice.api.entity.domain.ApiMethod;
import com.lego.apiservice.api.entity.domain.ApiStatus;
import com.lego.apiservice.api.repostiory.ApiRepository;
import com.lego.apiservice.category.repository.CategoryRepository;
import com.lego.apiservice.redis.service.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class ApiBatchService {

    private final ApiRepository apiRepository;
    private final CategoryRepository categoryRepository;
    private final RedisService redisService;

    @Scheduled(cron = "0 0/30 * * * *")
    public void apiHealthCheck() {
        apiRepository.findAll().forEach(api -> {
            try {
                if (api.getApiMethod().equals(ApiMethod.GET)) {
                    getRestTemplate(api);
                } else {
                    postRestTemplate(api);
                }
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void getRestTemplate(Api api) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONArray jsonArray = (JSONArray) parser.parse(api.getInput());
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        jsonArray.forEach(str -> {
            try {
                JSONObject jsonObject = (JSONObject) parser.parse(String.valueOf(str));
                log.info(jsonObject.toJSONString());
                params.put((String) jsonObject.get("name"), Collections.singletonList(jsonObject.get("example").toString()));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        });

        URI uri = UriComponentsBuilder
                .fromUriString(api.getEndpoint())
                .queryParams(params)
                .encode()
                .build()
                .toUri();

        LocalDateTime first = null;
        try {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set("Authorization", "E3EABEF2F41EFE6894E9CE08A0FF5E52C8E8AF8D2A09AAEDC3BB815B494F8F91");
            HttpEntity<?> httpEntity = new HttpEntity<>(httpHeaders);
            RestTemplate restTemplate = new RestTemplate();
            first = LocalDateTime.now();
            restTemplate.exchange(uri, HttpMethod.GET, httpEntity, Object.class);
            api.setApiStatus(ApiStatus.정상);
        } catch (RestClientException e) {
            log.info(e.getMessage().substring(0, 1));
            if (e.getMessage().substring(0, 1).equals("4")) {
                api.setApiStatus(ApiStatus.점검);
            } else {
                api.setApiStatus(ApiStatus.오류);
            }
        }
        LocalDateTime second = LocalDateTime.now();
        Duration diff = Duration.between(first, second);
        log.info("diff " + diff.toMillis());
        api.setResponseTime(String.valueOf(diff.toMillis()));
        api.setUpdatedAt(LocalDateTime.now());
        apiRepository.save(api);
    }

    public void postRestTemplate(Api api) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONArray jsonArray = (JSONArray) parser.parse(api.getInput());
        Map<String, String> params = new HashMap<>();

        jsonArray.forEach(str -> {
            try {
                JSONObject jsonObject = (JSONObject) parser.parse(String.valueOf(str));
                log.info(jsonObject.toJSONString());
                params.put((String) jsonObject.get("name"), (String) jsonObject.get("example"));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        });

        URI uri = UriComponentsBuilder
                .fromUriString(api.getEndpoint())
                .encode()
                .build()
                .toUri();

        LocalDateTime first = null;
        try {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set("Authorization", "E3EABEF2F41EFE6894E9CE08A0FF5E52C8E8AF8D2A09AAEDC3BB815B494F8F91");
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<?> httpEntity = new HttpEntity<>(params, httpHeaders);
            RestTemplate restTemplate = new RestTemplate();
            first = LocalDateTime.now();
            restTemplate.exchange(uri, HttpMethod.POST, httpEntity, Object.class);
            api.setApiStatus(ApiStatus.정상);
        } catch (RestClientException e) {
            log.info(e.getMessage().substring(0, 1));
            if (e.getMessage().substring(0, 1).equals("4")) {
                api.setApiStatus(ApiStatus.점검);
            } else {
                api.setApiStatus(ApiStatus.오류);
            }
        }
        LocalDateTime second = LocalDateTime.now();
        Duration diff = Duration.between(first, second);
        log.info("diff " + diff.toMillis());
        api.setResponseTime(String.valueOf(diff.toMillis()));
        api.setUpdatedAt(LocalDateTime.now());
        apiRepository.save(api);
    }

    @Scheduled(cron = "0 0 1 * * ?")
    public void categoryUpdate() {
        categoryRepository.findAll().forEach(category -> {
            redisService.setValue(String.valueOf(category.getId()), category.getName());
        });
        apiRepository.findAll().forEach(api -> {
            redisService.setValue(api.getEndpoint().replace("https://k9c201.p.ssafy.io/api", ""),
                    String.valueOf(api.getCategory().getId()));
        });
    }
}
