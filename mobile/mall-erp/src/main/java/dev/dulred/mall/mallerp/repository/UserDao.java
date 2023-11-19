package dev.dulred.mall.mallerp.repository;

import dev.dulred.mall.mallerp.entity.User;
import java.util.List;

/**
 * @author dulred
 * @description 功能测试
 * @github https://github.com/dulred
 */

public interface UserDao {
    /**
     * 返回数据列表
     *
     * @return
     */
    List<User> findAllUsers();

    /**
     * 添加
     *
     * @param User
     * @return
     */
    int insertUser(User User);

    /**
     * 修改
     *
     * @param User
     * @return
     */
    int updUser(User User);

    /**
     * 删除
     *
     * @param id
     * @return
     */
    int delUser(Integer id);
}

