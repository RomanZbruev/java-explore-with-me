package ru.practicum.ewmmainservice.controller.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmmainservice.model.Dto.user.NewUserDto;
import ru.practicum.ewmmainservice.model.Dto.user.UserDto;
import ru.practicum.ewmmainservice.service.admin.AdminUserService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping(path = "/admin/users")
@Slf4j
@Validated
public class AdminUserController {

    private final AdminUserService adminUserService;

    public AdminUserController(AdminUserService adminUserService) {
        this.adminUserService = adminUserService;
    }

    @GetMapping
    public List<UserDto> getUserAdmin(@RequestParam(required = false) List<Integer> ids,
                                      @RequestParam(required = false, defaultValue = "0") @PositiveOrZero int from,
                                      @RequestParam(required = false, defaultValue = "10") @Positive int size) {
        log.info("Запрос админа на получение списка друзей");
        return adminUserService.getUserDtoList(ids, from, size);
    }

    @PostMapping
    public UserDto postUserAdmin(@Valid @RequestBody NewUserDto userDto) {
        log.info("Запрос админа на добавление нового пользователя");
        return adminUserService.postUser(userDto);
    }

    @DeleteMapping("/{userId}")
    public void deleteUserAdmin(@PathVariable(required = true) int userId) {
        log.info("Запрос админа на удаление пользователя");
        adminUserService.deleteUser(userId);
    }
}
