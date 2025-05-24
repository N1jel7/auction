package by.n1jel.auction.utils;

import by.n1jel.auction.model.User;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Configuration;

import java.util.*;

@Getter
@Configuration
public class UserConfig {

    private ArrayList<User> users;

    public void putUser(User user) {
        users.add(user);
    }

    public User findByUsername(String username) {
        User findedUser = null;

        for (User user : users) {
            if (Objects.equals(user.getUsername(), username)) {
                findedUser = user;
            }
        }
        return findedUser;
    }

    @PostConstruct
    public void init() {
        users = new ArrayList<>();
    }


}
