package com.skillup.application.promotion;

import com.skillup.application.promotion.util.CacheDomainMapper;
import com.skillup.domain.promotion.PromotionDomain;
import com.skillup.domain.promotion.PromotionService;
import com.skillup.domain.promotionCacheDomain.PromotionCacheDomain;
import com.skillup.domain.promotionCacheDomain.PromotionCacheService;
import com.skillup.domain.stockCache.StockCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class PromotionApplication {

    @Autowired
    PromotionService promotionService;
    @Autowired
    PromotionCacheService promotionCacheService;
    @Autowired
    StockCacheService stockCacheService;

    public PromotionDomain getPromotionById(String id) {
        // 1 try to hit cache
        PromotionCacheDomain cachedPromotion = promotionCacheService.getPromotionById(id);
        // 2 not hit, read database
        if (Objects.isNull(cachedPromotion)) {
            PromotionDomain promotionDomain = promotionService.getPromotionById(id);
            if (Objects.isNull(promotionDomain)) {
                return null;
            }
            //3. update cache
            cachedPromotion = CacheDomainMapper.domainToCache(promotionDomain);
            promotionCacheService.updatePromotion(cachedPromotion);
        }
        // 4 return promotionDomain
        //TODO: update avaliable stock
        Long availableStock = stockCacheService.getAvailableStock(cachedPromotion.getPromotionId());
        cachedPromotion.setAvailableStock(availableStock);
        return CacheDomainMapper.cacheToDomain(cachedPromotion);
    }

}
