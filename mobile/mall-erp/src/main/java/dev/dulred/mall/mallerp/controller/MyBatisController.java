package dev.dulred.mall.mallerp.controller;

import dev.dulred.mall.mallerp.entity.User;
import dev.dulred.mall.mallerp.repository.UserDao;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author dulred
 * @description
 * @github https://github.com/dulred
 */
@RestController
public class MyBatisController {

    @Resource
    UserDao userDao;

    // 查询所有记录
    @GetMapping("/users/mybatis/queryAll")
    public List<User> queryAll() {
        return userDao.findAllUsers();
    }

    // 新增一条记录
    @GetMapping("/users/mybatis/insert")
    public Boolean insert(String name, String password) {
        if (StringUtils.isEmpty(name) || StringUtils.isEmpty(password)) {
            return false;
        }
        User user = new User();
        user.setName(name);
        user.setPassword(password);
        return userDao.insertUser(user) > 0;
    }

    // 修改一条记录
    @GetMapping("/users/mybatis/update")
    public Boolean update(Integer id, String name, String password) {
        if (id == null || id < 1 || StringUtils.isEmpty(name) || StringUtils.isEmpty(password)) {
            return false;
        }
        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setPassword(password);
        return userDao.updUser(user) > 0;
    }

    // 删除一条记录
    @GetMapping("/users/mybatis/delete")
    public Boolean delete(Integer id) {
        if (id == null || id < 1) {
            return false;
        }
        return userDao.delUser(id) > 0;
    }
}