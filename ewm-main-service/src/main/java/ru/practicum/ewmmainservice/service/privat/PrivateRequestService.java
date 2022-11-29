package ru.practicum.ewmmainservice.service.privat;

import ru.practicum.ewmmainservice.model.Dto.request.ParticipationRequestDto;

import java.util.List;

public interface PrivateRequestService {

    List<ParticipationRequestDto> getUserRequestsPrivate(Integer userId);

    ParticipationRequestDto postRequestPrivate(Integer userId, Integer eventId);

    ParticipationRequestDto cancelRequest(Integer reqId, Integer userId);


}
