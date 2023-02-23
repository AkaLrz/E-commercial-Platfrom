package com.skillup.domain.promotionCacheDomain;

public interface PromotionCacheRepo {
    public PromotionCacheDomain getPromotionById(String id);

    public void  setPromotion(PromotionCacheDomain cacheDomain);
}
