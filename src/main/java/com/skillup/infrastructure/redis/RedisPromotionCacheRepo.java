package com.skillup.infrastructure.redis;

import com.skillup.domain.promotionCacheDomain.PromotionCacheDomain;
import com.skillup.domain.promotionCacheDomain.PromotionCacheRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import java.util.Objects;
import com.alibaba.fastjson2.*;

public class RedisPromotionCacheRepo implements PromotionCacheRepo {

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    @Override
    public PromotionCacheDomain getPromotionById(String id) {
        return JSON.parseObject(get(id), PromotionCacheDomain.class);
    }

    @Override
    public void setPromotion(PromotionCacheDomain cacheDomain) {
        set(cacheDomain.getCommodityId(), cacheDomain);
    }
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, JSON.toJSONString(value));
    }

    public String get(String key) {
        if (Objects.isNull(key)) return null;
        return redisTemplate.opsForValue().get(key);
    }
}
