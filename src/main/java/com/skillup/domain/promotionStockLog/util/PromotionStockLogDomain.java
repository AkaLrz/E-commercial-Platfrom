package com.skillup.domain.promotionStockLog.util;

import com.skillup.domain.promotionStockLog.util.StockOperation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PromotionStockLogDomain {
    String promotionId;
    Long orderNumber;
    StockOperation stockOperation;
    private String userId;
    private LocalDateTime createTimes;
}
