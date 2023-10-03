package com.kob.backend.controller.user;

/** 将pojo中的class操作转换成sql语句 */

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kob.backend.mapper.UserMapper;
import com.kob.backend.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController /** 等同于@Controller + @ResponseBody, 表示方法的返回值直接以指定的格式写入Http response body中 */
public class UserController {

    @Autowired // 如果需要用数据库里的Mapper，需要加上这个注解
    UserMapper userMapper; // 生成一个UserMapper类的对象userMapper

    @GetMapping("/user/all/") // 返回到这个地址
    public List<User> getAll() {
        return userMapper.selectList(null); // 返回全部数据
    }

    // 得到pojo的一个对象，相当于是返回数据库里的一条数据
    @GetMapping("/user/{userId}/") // 数据传到 e.g. local:3000/user/1
    public User getuser(@PathVariable int userId) { // 这里返回对应id的内容，读入是读上面注解输入的id多少，这里就读入多少
        QueryWrapper<User> queryWrapper = new QueryWrapper<>(); // 封装一个对象
        queryWrapper.eq("id", userId); // 找到查询对象
        return userMapper.selectOne(queryWrapper); // 返回对应的值
    }

    @GetMapping("/user/add/{userId}/{username}/{password}/")
    public String addUser(
            @PathVariable int userId,
            @PathVariable String username,
            @PathVariable String password) { // 读入上面几个变量
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(password); // 将存的密码按照加密之后的密码存进数据库
        User user = new User(userId, username, encodedPassword); // 定义一个user object
        userMapper.insert(user); // 插入user操作
        return "Add User Successfully";
    }

    @GetMapping("/user/delete/{userId}/") // 删除一个用户
    public String deleteUser(@PathVariable int userId) {
        userMapper.deleteById(userId);
        return "Delete User Successfully";
    }
}