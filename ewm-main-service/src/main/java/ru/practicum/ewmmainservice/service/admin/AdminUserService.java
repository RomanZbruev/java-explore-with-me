package ru.practicum.ewmmainservice.service.admin;


import org.springframework.stereotype.Service;
import ru.practicum.ewmmainservice.model.Dto.user.NewUserDto;
import ru.practicum.ewmmainservice.model.Dto.user.UserDto;

import java.util.List;

@Service
public interface AdminUserService {

    List<UserDto> getUserDtoList(List<Integer> ids, int from, int size);

    UserDto postUser(NewUserDto newUserDto);

    void deleteUser(int userId);
}
