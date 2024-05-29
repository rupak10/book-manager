package com.app.config;

import com.app.model.User;
import com.app.repository.UserRepository;
import com.app.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DbSeeder implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        String userName = "CCT1234";
        String password = "54321";
        if(userRepository.findByUsername(userName).isEmpty()) {
            User user = new User();
            user.setUsername(userName);
            user.setPassword(CommonUtil.getEncodedPassword(password));
            user.setStatus(1);
            userRepository.save(user);
        }
    }
}
