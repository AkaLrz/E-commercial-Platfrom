package com.skillup.infrastructure.redis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class RedisPromotionCacheRepoTest {

    @Autowired
    RedisPromotionCacheRepo redisService;

    public static String KEY = "test_key";

    public static String VALUE = "test_value";

    @Test
    void setAndGetValue() {
        redisService.set(KEY, VALUE);
        String value = redisService.get(KEY);
        //assertEquals(value, VALUE);
        //assertThat(value).isEqualTo(VALUE);
    }


}