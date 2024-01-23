package com.kob.backend.service.impl.user.bot;

import com.kob.backend.mapper.BotMapper;
import com.kob.backend.pojo.Bot;
import com.kob.backend.pojo.User;
import com.kob.backend.service.impl.utils.UserDetailsImpl;
import com.kob.backend.service.user.bot.AddService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class AddServiceImpl implements AddService {

    @Autowired
    private BotMapper botMapper;

    @Override
    public Map<String, String> add(Map<String, String> data) {
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authenticationToken.getPrincipal();
        User user = loginUser.getUser();

        String title = data.get("title");
        String description = data.get("description");
        String content = data.get("content");

        Map<String, String> map = new HashMap<>();

        if (title == null || title.length() == 0) {
            map.put("error_message", "Title can't be blank");
            return map;
        }

        if (title.length() > 30) {
            map.put("error_message", "Title too long");
            return map;
        }

        if (description == null || description.length() == 0) {
            description = "This user is too lazy and write nothing here...";
        }

        if (description.length() > 300) {
            map.put("error_message", "Description too long");
            return map;
        }

        if (content == null || content.length() == 0) {
            map.put("error_message", "Content can't be blank");
            return map;
        }

        if (content.length() > 10000) {
            map.put("error_message", "Content too long");
            return map;
        }

        Date now = new Date();
        Bot bot = new Bot(null, user.getId(), title, description, content, now, now);


        botMapper.insert(bot);
        map.put("error_message", "Success in creating a bot");

        return map;
    }
}
