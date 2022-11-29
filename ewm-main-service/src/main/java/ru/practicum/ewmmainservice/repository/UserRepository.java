package ru.practicum.ewmmainservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.ewmmainservice.model.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    public User getUserById(Integer userId);

    @Query("select u " +
            "from User u " +
            "where u.id in ?1 " +
            "group by u.id " +
            "order by u.id desc")
    public List<User> getUsersByIds(List<Integer> ids);
}
