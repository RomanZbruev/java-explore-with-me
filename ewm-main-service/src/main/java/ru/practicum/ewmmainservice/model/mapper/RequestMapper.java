package ru.practicum.ewmmainservice.model.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.ewmmainservice.model.Dto.request.ParticipationRequestDto;
import ru.practicum.ewmmainservice.model.Request;

@Component
public class RequestMapper {

    public static Request fromRequestDto(ParticipationRequestDto participationRequestDto) {
        return Request.builder()
                .created(participationRequestDto.getCreated())
                .status(participationRequestDto.getStatus())
                .build();
    }

    public static ParticipationRequestDto fromRequestToDto(Request request) {
        return ParticipationRequestDto.builder()
                .id(request.getId())
                .event(request.getEvent().getId())
                .requester(request.getRequester().getId())
                .created(request.getCreated())
                .status(request.getStatus())
                .build();
    }

}
