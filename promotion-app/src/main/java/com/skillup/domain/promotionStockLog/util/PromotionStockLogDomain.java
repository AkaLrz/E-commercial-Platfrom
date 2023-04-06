package com.skillup.domain.promotionStockLog.util;

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
    OperationName operationName;
    private String userId;
    private LocalDateTime createTimes;
}
