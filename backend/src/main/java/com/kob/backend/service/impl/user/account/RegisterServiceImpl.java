package com.kob.backend.service.impl.user.account;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kob.backend.mapper.UserMapper;
import com.kob.backend.pojo.User;
import com.kob.backend.service.user.account.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Map<String, String> register(String username, String password, String confirmedPassword) {
        Map<String, String> map = new HashMap<>();
        if (username == null) {
            map.put("error_message", "username can not be empty");
            return map;
        }
        if (password == null || confirmedPassword == null) {
            map.put("error_message", "password can not be empty");
            return map;
        }

        username = username.trim();
        if (username.length() == 0) {
            map.put("error_message", "Username can not be empty");
            return map;
        }
        if (password.length() == 0 || confirmedPassword.length() == 0) {
            map.put("error_message", "password can not be empty");
            return map;
        }
        if (username.length() > 100) {
            map.put("error_message", "password can not longer than 100");
            return map;
        }
        if (password.length() > 100 || confirmedPassword.length() > 100) {
            map.put("error_message", "password can not longer than 100");
            return map;
        }

        if (!password.equals(confirmedPassword)) {
            map.put("error_message", "Confirmed password not match, Please Re-entry");
            return map;
        }

        // 判断用户是否已存在，先从数据库读入所有用户，然多对比，如果有的话加到list
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        List<User> users = userMapper.selectList(queryWrapper);
        if (!users.isEmpty()) {
            map.put("error_message", "User already exist");
            return map;
        }

        // 实现了密码加密并且添加进数据库，创建个人profile
        String encodedPassword = passwordEncoder.encode(password);
        String photo = "https://cdn.acwing.com/media/user/profile/photo/249430_lg_efe4612c5c.jpg";
        User user = new User(null, username, encodedPassword, photo);
        userMapper.insert(user);

        map.put("error_message", "success");
        return map;
    }
}
