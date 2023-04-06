package com.skillup.infrastructure.redis;

import com.skillup.domain.promotionCacheDomain.PromotionCacheDomain;
import com.skillup.domain.promotionCacheDomain.PromotionCacheRepo;
import com.skillup.domain.stockCache.StockCacheDomain;
import com.skillup.domain.stockCache.StockCacheRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import java.util.Collections;
import java.util.Objects;
import com.alibaba.fastjson2.*;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Repository;

@Repository
public class RedisPromotionCacheRepo implements PromotionCacheRepo, StockCacheRepo {

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    @Autowired
    DefaultRedisScript<Long> redisLockStockScript;

    @Autowired
    DefaultRedisScript<Long> redisRevertStockScript;

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
        try{
            Long stock = redisTemplate.execute(redisLockStockScript, Collections.singletonList(StockCacheDomain.createStockKey(id)));
            if (stock >= 0) {
                return true;
            } else {
                // -1 means sold out, -2 promotion doesn't exist
                return false;
            }
        } catch (Throwable throwable) {
            throw new RuntimeException(throwable);
        }
    }

    @Override
    public boolean revertStock(String id) {
        // 1 select from available_stock = ? lock_stock = ?
        try{
            Long stock = redisTemplate.execute(redisRevertStockScript, Collections.singletonList(StockCacheDomain.createStockKey(id)));
            if (stock > 0) {
                return true;
            } else {
                // -1 means sold out, -2 promotion doesn't exist
                return false;
            }
        } catch (Throwable throwable) {
            throw new RuntimeException(throwable);
        }
    }

    @Override
    public Long getPromotionAvailableStock(String promotionId) {
        String stockKey = StockCacheDomain.createStockKey(promotionId);
        return JSON.parseObject(get(stockKey), Long.class);
    }

    @Override
    public void setPromotionAvailableStock(String promotionId, Long availableStock) {
        String stockKey = StockCacheDomain.createStockKey(promotionId);
        set(promotionId, availableStock);
    }
}
