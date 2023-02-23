package com.skillup.infrastructure.redis;

import com.skillup.domain.promotionCacheDomain.PromotionCacheDomain;
import com.skillup.domain.promotionCacheDomain.PromotionCacheRepo;
import com.skillup.domain.stockCache.StockCacheRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import java.util.Objects;
import com.alibaba.fastjson2.*;
import org.springframework.stereotype.Repository;

@Repository
public class RedisPromotionCacheRepo implements PromotionCacheRepo, StockCacheRepo {

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

    @Override
    public boolean lockStock(String id) {
        return false;
    }

    @Override
    public boolean revertStock(String id) {
        return false;
    }

    @Override
    public Long getPromotionAvailableStock(String promotionId) {
        return null;
    }

    @Override
    public void setPromotionAvailableStock(String promotionId, Long availableStock) {
        set(promotionId, availableStock);
    }
}
