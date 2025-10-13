package com.splitwiseClone.mapper;

import com.splitwiseClone.dto.SettlementResponse;
import com.splitwiseClone.model.Settlement;

public class SettlementMapper {
    public static SettlementResponse toResponse(Settlement s) {
        SettlementResponse r = new SettlementResponse();
        r.setId(s.getId());
        r.setGroupId(s.getGroup() != null ? s.getGroup().getId() : null);
        r.setFromUserId(s.getFromUser() != null ? s.getFromUser().getId() : null);
        r.setToUserId(s.getToUser() != null ? s.getToUser().getId() : null);
        r.setAmount(s.getAmount());
        r.setCreatedAt(s.getCreatedAt());
        return r;
    }
}


