package com.github.ioridazo.cacheable.sample.web;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.github.ioridazo.cacheable.sample.domain.CacheableService;
import com.github.ioridazo.cacheable.sample.domain.SampleForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@ControllerAdvice
@RestController
public class CacheableController {

    private final CacheableService service;

    public CacheableController(
            final CacheableService service) {
        this.service = service;
    }

    @GetMapping("/sample/cacheable")
    public SampleForm CacheableSample(@RequestParam(name = "id") final int id) throws InterruptedException {
        final long start = System.currentTimeMillis();
        final SampleForm sampleForm = service.tryCache(id);
        log.info("id:{} - TIME:{}ms", id, System.currentTimeMillis() - start);
        return sampleForm;
    }

    @ExceptionHandler(Exception.class)
    public SampleForm exceptionHandler(final Exception e) {
        log.error("予期せぬエラーが発生しました。", e);
        return SampleForm.ofError(e);
    }

    @Bean
    public ObjectMapper objectMapper() {
        var mapper = new ObjectMapper();
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper;
    }
}
