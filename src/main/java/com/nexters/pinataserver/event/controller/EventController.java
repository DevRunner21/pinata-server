package com.nexters.pinataserver.event.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nexters.pinataserver.common.dto.response.CommonApiResponse;
import com.nexters.pinataserver.common.security.AuthenticationPrincipal;
import com.nexters.pinataserver.event.dto.request.ParticipateEventRequest;
import com.nexters.pinataserver.event.dto.request.RegisterEventRequest;
import com.nexters.pinataserver.event.dto.response.OrganizersEventResponse;
import com.nexters.pinataserver.event.dto.response.ParticipateEventResponse;
import com.nexters.pinataserver.event.dto.response.ReadCurrentParticipateEventResponse;
import com.nexters.pinataserver.event.dto.response.ReadParticipateEventsResponse;
import com.nexters.pinataserver.event.dto.response.ReadEventResponse;
import com.nexters.pinataserver.event.dto.response.RegisterEventResponse;
import com.nexters.pinataserver.event.service.EventCreateService;
import com.nexters.pinataserver.event.service.EventParticipateService;
import com.nexters.pinataserver.event.service.EventReadService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "https://pinata-gift.com")
@RequestMapping("/api/v1/events")
public class EventController {

    private final EventReadService eventReadService;

    private final EventCreateService eventCreateService;

    private final EventParticipateService eventParticipateService;

    @GetMapping
    public CommonApiResponse<List<OrganizersEventResponse>> getEvents(
            @AuthenticationPrincipal Long userId,
            @PageableDefault(page = 0, size = 100) Pageable pageable
    ) {
        List<OrganizersEventResponse> response = eventReadService.getEvents(userId, pageable);
        return CommonApiResponse.ok(response);
    }

    @GetMapping("/participate/me")
    public CommonApiResponse<List<ReadParticipateEventsResponse>> getParticipationEvents(
            @AuthenticationPrincipal Long userId,
            @PageableDefault(page = 0, size = 100) Pageable pageable
    ) {
        List<ReadParticipateEventsResponse> response = eventReadService.getParticipationEvents(userId, pageable);
        return CommonApiResponse.ok(response);
    }

    @PostMapping
    public CommonApiResponse<RegisterEventResponse> registerEvent(
            @Valid @RequestBody RegisterEventRequest request,
            @AuthenticationPrincipal Long userId
    ) {
        RegisterEventResponse response = eventCreateService.createEvent(userId, request);
        return CommonApiResponse.<RegisterEventResponse>ok(response);
    }

    @GetMapping("/participate/{eventCode}")
    public CommonApiResponse<ReadCurrentParticipateEventResponse> readCurrentParticipateEvent(
            @PathVariable("eventCode") String eventCode,
            @AuthenticationPrincipal Long userId
    ) {
        ReadCurrentParticipateEventResponse response = eventReadService.getParticipateEvent(userId, eventCode);
        return CommonApiResponse.<ReadCurrentParticipateEventResponse>ok(response);
    }

    @PostMapping("/participate")
    public CommonApiResponse<ParticipateEventResponse> participateEvent(
            @Valid @RequestBody ParticipateEventRequest request,
            @AuthenticationPrincipal Long userId
    ) {
        ParticipateEventResponse response = eventParticipateService.participateEvent(userId, request.getCode());
        return CommonApiResponse.<ParticipateEventResponse>ok(response);
    }

    @GetMapping("/{eventCode}")
    public CommonApiResponse<ReadEventResponse> readEvent(
            @PathVariable("eventCode") String eventCode,
            @AuthenticationPrincipal Long userId
    ) {
        ReadEventResponse response = eventReadService.getEvent(userId, eventCode);
        return CommonApiResponse.ok(response);
    }

}
