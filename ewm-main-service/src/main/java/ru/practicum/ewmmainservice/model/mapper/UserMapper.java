package ru.practicum.ewmmainservice.model.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.ewmmainservice.model.Dto.user.NewUserDto;
import ru.practicum.ewmmainservice.model.Dto.user.UserDto;
import ru.practicum.ewmmainservice.model.Dto.user.UserShortDto;
import ru.practicum.ewmmainservice.model.User;

@Component
public final class UserMapper {

    public static User fromNewUserDto(NewUserDto newUserDto) {
        return User.builder()
                .name(newUserDto.getName())
                .email(newUserDto.getEmail())
                .build();
    }

    public static UserDto fromUserUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }


    public static UserShortDto fromUserShortDto(User user) {
        return UserShortDto.builder()
                .id(user.getId())
                .name(user.getName())
                .build();
    }
}
