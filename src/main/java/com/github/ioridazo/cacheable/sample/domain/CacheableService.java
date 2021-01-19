package com.github.ioridazo.cacheable.sample.domain;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class CacheableService {

    @Cacheable("form-cache")
    public SampleForm tryCache(final int id) throws InterruptedException {
        // キャッシュ確認のためWaitさせる
        TimeUnit.SECONDS.sleep(1L);

        return new SampleForm(
                id,
                "cacheable-sample",
                SampleForm.Result.OK,
                null,
                generateString(), generateString(), generateString(), generateString(), generateString(),
                generateString(), generateString(), generateString(), generateString(), generateString(),
                generateString(), generateString(), generateString(), generateString(), generateString(),
                generateString(), generateString(), generateString(), generateString(), generateString(),
                generateString(), generateString(), generateString(), generateString(), generateString(),
                generateString(), generateString(), generateString(), generateString(), generateString(),
                generateString(), generateString(), generateString(), generateString(), generateString(),
                generateString(), generateString(), generateString(), generateString(), generateString(),
                generateString(), generateString(), generateString(), generateString(), generateString(),
                generateString(), generateString(), generateString(), generateString(), generateString(),
                generateString()
        );
    }

    private String generateString() {
        return UUID.randomUUID().toString() + " | " + UUID.randomUUID().toString() + " | " + UUID.randomUUID().toString();
    }
}
