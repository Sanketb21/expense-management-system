package com.splitwiseClone.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.splitwiseClone.dto.SettlementRequest;
import com.splitwiseClone.dto.SettlementResponse;
import com.splitwiseClone.mapper.SettlementMapper;
import com.splitwiseClone.model.Group;
import com.splitwiseClone.model.Settlement;
import com.splitwiseClone.model.User;
import com.splitwiseClone.repository.GroupRepository;
import com.splitwiseClone.repository.SettlementRepository;
import com.splitwiseClone.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/settlements")
@Tag(name = "Settlements", description = "Record and list settlements for groups")
public class SettlementController {
    private final SettlementRepository settlementRepository;
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    public SettlementController(SettlementRepository settlementRepository, GroupRepository groupRepository, UserRepository userRepository) {
        this.settlementRepository = settlementRepository;
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
    }

    @PostMapping
    @Operation(summary = "Record a settlement", description = "Creates a settlement (payment) between two users in a group")
    public ResponseEntity<SettlementResponse> createSettlement(@Validated @RequestBody SettlementRequest req){
        Group group = groupRepository.findById(req.getGroupId()).orElseThrow(() -> new EntityNotFoundException("Group not found: " + req.getGroupId()));
        User fromUser = userRepository.findById(req.getFromUserId()).orElseThrow(() -> new EntityNotFoundException("User not found: " + req.getFromUserId()));
        User toUser = userRepository.findById(req.getToUserId()).orElseThrow(() -> new EntityNotFoundException("User not found: " + req.getToUserId()));

        if (fromUser.getId() == toUser.getId()) {
            throw new IllegalArgumentException("fromUser and toUser cannot be the same");
        }
        if (req.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("amount must be positive");
        }

        Settlement s = new Settlement();
        s.setGroup(group);
        s.setFromUser(fromUser);
        s.setToUser(toUser);
        s.setAmount(req.getAmount());

        Settlement saved = settlementRepository.save(s);
        return ResponseEntity.ok(SettlementMapper.toResponse(saved));
    }

    @GetMapping("/group/{groupId}")
    @Operation(summary = "List settlements for a group")
    public ResponseEntity<List<SettlementResponse>> listSettlements(@PathVariable long groupId){
        List<Settlement> settlements = settlementRepository.findByGroupId(groupId);
        List<SettlementResponse> response = new ArrayList<>();
        for (Settlement s : settlements) {
            response.add(SettlementMapper.toResponse(s));
        }
        return ResponseEntity.ok(response);
    }
}


