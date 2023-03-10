package com.skillup.infrastructure.repolmpl;

import com.skillup.domain.promotionStockLog.util.PromotionStockLogDomain;
import com.skillup.domain.promotionStockLog.util.PromotionStockLogRepo;
import com.skillup.infrastructure.jooq.tables.records.PromotionRecord;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class JooqPromotionStockLogRepo implements PromotionStockLogRepo, DomainRecord<PromotionStockLogDomain, PromotionRecord> {

    @Autowired
    DSLContext dslContext;
    @Override
    public void createPromotionLog(PromotionStockLogDomain promotionStockLogDomain) {
        dslContext.executeInsert();
    }

    @Override
    public PromotionStockLogDomain getByOrderIdAndOperation(Long orderId, String operationName) {
        return null;
    }

    @Override
    public PromotionStockLogDomain toDomain(PromotionRecord promotionLogRecord) {

    }

    @Override
    public PromotionRecord toRecord(PromotionStockLogDomain promotionStockLogDomain) {
        return null;
    }
}
