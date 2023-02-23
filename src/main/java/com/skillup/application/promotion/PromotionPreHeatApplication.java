package com.skillup.application.promotion;

import com.skillup.application.promotion.util.CacheDomainMapper;
import com.skillup.domain.promotion.PromotionDomain;
import com.skillup.domain.promotion.PromotionService;
import com.skillup.domain.promotionCacheDomain.PromotionCacheService;
import com.skillup.domain.stockCache.StockCacheDomain;
import com.skillup.domain.stockCache.StockCacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class PromotionPreHeatApplication implements ApplicationRunner {
    @Autowired
    PromotionCacheService promotionCacheService;
    @Autowired
    PromotionService promotionService;

    @Autowired
    StockCacheService stockCacheService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("---- Cache preheat Init active promotion stok into Cache ----");
        List<PromotionDomain> activePromotions = promotionService.getPromotionByStatus(1);
        activePromotions.stream().forEach( promotionDomain -> {
            //1. cache active promotion(cache)domain
            promotionCacheService.updatePromotion(CacheDomainMapper.domainToCache(promotionDomain));
            //2. cache active promotion stock info
            stockCacheService.setAvailableStock(StockCacheDomain.createStockKey(promotionDomain.getPromotionId()), promotionDomain.getAvailableStock());
        });
    }
}
