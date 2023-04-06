package com.skillup.domain.promotionStockLog.util;

public interface PromotionStockLogRepo {
    public void createPromotionLog(PromotionStockLogDomain promotionStockLogDomain);

    public PromotionStockLogDomain getByOrderIdAndOperation(Long orderId, String operationName);
}
