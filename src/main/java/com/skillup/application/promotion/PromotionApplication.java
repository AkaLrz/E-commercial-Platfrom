package com.skillup.application.promotion;

import com.skillup.domain.promotion.PromotionDomain;
import com.skillup.domain.promotion.PromotionService;
import com.skillup.domain.promotionCacheDomain.PromotionCacheDomain;
import com.skillup.domain.promotionCacheDomain.PromotionCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class PromotionApplication {

    @Autowired
    PromotionService promotionService;

    @Autowired
    PromotionCacheService promotionCacheService;

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
            cachedPromotion = promotionCacheService.updatePromotion(domainToCache(promotionDomain));
        }
        // 4 return promotionDomain
        return cacheToDomain(cachedPromotion);
    }

    private PromotionCacheDomain domainToCache(PromotionDomain domain) {
        return PromotionCacheDomain.builder()
                .promotionId(domain.getPromotionId())
                .promotionName(domain.getPromotionName())
                .commodityId(domain.getCommodityId())
                .startTime(domain.getStartTime())
                .endTime(domain.getEndTime())
                .originalPrice(domain.getOriginalPrice())
                .promotionalPrice(domain.getPromotionalPrice())
                .totalStock(domain.getTotalStock())
                .availableStock(domain.getAvailableStock())
                .lockStock(domain.getLockStock())
                .imageUrl(domain.getImageUrl())
                .status(domain.getStatus())
                .build();
    }
    public PromotionDomain cacheToDomain(PromotionCacheDomain cache) {
        return PromotionDomain.builder()
                .promotionId(cache.getPromotionId())
                .promotionName(cache.getPromotionName())
                .commodityId(cache.getCommodityId())
                .startTime(cache.getStartTime())
                .endTime(cache.getEndTime())
                .originalPrice(cache.getOriginalPrice())
                .promotionalPrice(cache.getPromotionalPrice())
                .totalStock(cache.getTotalStock())
                .availableStock(cache.getAvailableStock())
                .lockStock(cache.getLockStock())
                .imageUrl(cache.getImageUrl())
                .status(cache.getStatus())
                .build();
    }

}
