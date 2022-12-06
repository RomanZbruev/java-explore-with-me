package ru.practicum.ewmmainservice.service.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewmmainservice.exception.NotFoundException;
import ru.practicum.ewmmainservice.model.Dto.user.NewUserDto;
import ru.practicum.ewmmainservice.model.Dto.user.UserDto;
import ru.practicum.ewmmainservice.model.User;
import ru.practicum.ewmmainservice.model.mapper.UserMapper;
import ru.practicum.ewmmainservice.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AdminUserServiceImpl implements AdminUserService {

    private final UserRepository userRepository;

    public AdminUserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserDto> getUserDtoList(List<Integer> ids, int from, int size) {
        List<User> users;
        if (ids == null) {
            int fromPage = from / size;
            Pageable pageable = PageRequest.of(fromPage, size);
            users = userRepository.findAll(pageable).getContent();
            log.info("Получаем постраничный список пользователей");
        } else {
            users = userRepository.getUsersByIds(ids);
            log.info("Получаем список пользователей по идентификаторам");
        }
        return users.stream()
                .map(UserMapper::fromUserUserDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public UserDto postUser(NewUserDto newUserDto) {
        User user = UserMapper.fromNewUserDto(newUserDto);
        User newUser = userRepository.save(user);
        log.info("Пользователь с айди: {} добавлен в хранилище", newUser.getId());
        return UserMapper.fromUserUserDto(newUser);
    }

    @Transactional
    @Override
    public void deleteUser(int userId) {
        User user = userRepository.getUserById(userId);
        if (user == null) {
            log.error("Пользователь с айди: {} не найден", userId);
            throw new NotFoundException("Пользователь с айди: " + userId + " не найден!");
        } else {
            log.info("Удаление пользователя");
            userRepository.deleteById(userId);
        }
    }
}
