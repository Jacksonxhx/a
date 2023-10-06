package com.kob.backend.service.impl.user.account;

import com.kob.backend.pojo.User;
import com.kob.backend.service.impl.utils.UserDetailsImpl;
import com.kob.backend.service.user.account.LoginService;
import com.kob.backend.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public Map<String, String> getToken(String username, String password) {
        // 将用户名密码封装成这个类，里面存加密之后的用户名密码
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username, password);

        // 验证是否能登陆，自动处理登录异常
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);

        UserDetailsImpl loginUser = (UserDetailsImpl) authenticate.getPrincipal();
        User user = loginUser.getUser(); // 取出用户

        String jwt = JwtUtil.createJWT(user.getId().toString()); // 将用户的userId封装成jet token

        Map<String, String> map = new HashMap<>(); // 返回结果

        map.put("error_message", "success");
        map.put("token", jwt);

        return map;
    }
}
