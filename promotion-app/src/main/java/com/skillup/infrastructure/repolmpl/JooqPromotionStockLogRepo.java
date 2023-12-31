package com.skillup.infrastructure.repolmpl;

import com.skillup.domain.promotionStockLog.util.PromotionStockLogDomain;
import com.skillup.domain.promotionStockLog.util.PromotionStockLogRepo;
import com.skillup.domain.promotionStockLog.util.OperationName;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
@Repository
public class JooqPromotionStockLogRepo implements PromotionStockLogRepo {

    @Override
    public void createPromotionLog(PromotionStockLogDomain promotionStockLogDomain) {

    }

    @Override
    public PromotionStockLogDomain getByOrderIdAndOperation(Long orderId, String operationName) {
        return null;
    }
}

//@Repository
//public class JooqPromotionStockLogRepo implements PromotionStockLogRepo, DomainRecord<PromotionStockLogDomain, PromotionLogRecord> {
//
//    @Autowired
//    DSLContext dslContext;
//
//    private static final PromotionLog PLT = new PromotionLog();
//    @Override
//    @Transactional(propagation = Propagation.REQUIRED)
//    public void createPromotionLog(PromotionStockLogDomain promotionStockLogDomain) {
//        dslContext.executeInsert(toRecord(promotionStockLogDomain));
//    }
//
//    @Override
//    @Transactional(propagation = Propagation.REQUIRED)
//    public PromotionStockLogDomain getByOrderIdAndOperation(Long orderId, String operationName) {
//
//        return dslContext.selectFrom(PLT).where(PLT.ORDER_NUMBER.eq(orderId).and(PLT.OPERATION.NAME.eq(operationName)))
//                .fetchOptional(this::toDomain).orElse(null);
//    }
//
//    @Override
//    public PromotionStockLogDomain toDomain(PromotionRecord promotionLogRecord) {
//        return PromotionStockLogDomain.builder()
//                .orderNumber(promotionLogRecord.getOrderNumber())
//                .promotionId(promotionLogRecord.getPromotionId())
//                .userId(promotionLogRecord.getUserId())
//                .operationName(OperationName.valueOf(promotionLogRecord.getOperationName()))
//                .createTimes(promotionLogRecord.getCreateTime())
//                .build();
//
//    }
//
//    @Override
//    public PromotionLogRecord toRecord(PromotionStockLogDomain promotionStockLogDomain) {
//        return new PromotionLogRecord(
//                promotionStockLogDomain.getOrderNumber(),
//                promotionStockLogDomain.getUserId(),
//                promotionStockLogDomain.getPromotionId(),
//                promotionStockLogDomain.getOperationName().toString(),
//                promotionStockLogDomain.getCreateTimes()
//        );
//    }
//}
