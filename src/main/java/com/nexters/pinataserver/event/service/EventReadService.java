package com.nexters.pinataserver.event.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nexters.pinataserver.common.exception.e4xx.NotFoundException;
import com.nexters.pinataserver.common.util.ImageUtil;
import com.nexters.pinataserver.event.domain.Event;
import com.nexters.pinataserver.event.domain.EventRepository;
import com.nexters.pinataserver.event.domain.EventsRepository;
import com.nexters.pinataserver.event.dto.response.EventResponse;
import com.nexters.pinataserver.event.dto.response.ReadCurrentParticipateEventResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EventReadService {

	private final EventRepository eventRepository;

	private final EventsRepository eventsRepository;

	private final EventValidateService eventValidateService;

	private final ImageUtil imageUtil;

	@Transactional
	public ReadCurrentParticipateEventResponse getParticipateEvent(Long participantId, String eventCode) {

		// 이벤트 조회
		Event foundEvent = eventRepository.findByCode(eventCode)
			.orElseThrow(NotFoundException.EVENT);

		// 이벤트 참가 가능한 이벤트인지 검증
		eventValidateService.validateCanParticipate(participantId, foundEvent);

		return convertToReadCurrentParticipateEventResponse(foundEvent);
	}

	private ReadCurrentParticipateEventResponse convertToReadCurrentParticipateEventResponse(Event foundEvent) {
		return ReadCurrentParticipateEventResponse.builder()
			.title(foundEvent.getTitle())
			.code(foundEvent.getCode())
			.status(foundEvent.getStatus())
			.type(foundEvent.getType())
			.hitMessage(foundEvent.getHitMessage())
			.hitImageUrl(
				imageUtil.getFullImageUrl(foundEvent.getHitImageFileName())
			)
			.missMessage(foundEvent.getMissMessage())
			.missImageUrl(
				imageUtil.getFullImageUrl(foundEvent.getMissImageFileName())
			)
			.isPeriod(foundEvent.getEventDateTime().getIsPeriod())
			.openAt(foundEvent.getEventDateTime().getOpenAt())
			.closeAt(foundEvent.getEventDateTime().getCloseAt())
			.build();
	}

	public List<EventResponse> getEvents(Long userId, Pageable pageable) {
		return eventsRepository.getMyEvents(userId, pageable).stream()
			.map(EventResponse::from)
			.collect(Collectors.toList());
	}

}
