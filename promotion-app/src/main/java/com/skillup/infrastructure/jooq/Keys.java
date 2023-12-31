/*
 * This file is generated by jOOQ.
 */
package com.skillup.infrastructure.jooq;


import com.skillup.infrastructure.jooq.tables.Promotion;
import com.skillup.infrastructure.jooq.tables.PromotionLog;
import com.skillup.infrastructure.jooq.tables.records.PromotionLogRecord;
import com.skillup.infrastructure.jooq.tables.records.PromotionRecord;

import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.Internal;


/**
 * A class modelling foreign key relationships and constraints of tables in 
 * promotion-app.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Keys {

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<PromotionRecord> KEY_PROMOTION_PRIMARY = Internal.createUniqueKey(Promotion.PROMOTION, DSL.name("KEY_promotion_PRIMARY"), new TableField[] { Promotion.PROMOTION.PROMOTION_ID }, true);
    public static final UniqueKey<PromotionLogRecord> KEY_PROMOTION_LOG_PRIMARY = Internal.createUniqueKey(PromotionLog.PROMOTION_LOG, DSL.name("KEY_promotion_log_PRIMARY"), new TableField[] { PromotionLog.PROMOTION_LOG.ORDER_NUMBER, PromotionLog.PROMOTION_LOG.OPERATION_NAME }, true);
}
