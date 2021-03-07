package cn.itcast.travel.dao;

import cn.itcast.travel.domain.User;

public interface UserDao {

    /**
     * 根据用户名查询数据库中是存在该username
     * @param username
     * @return
     */
    public User findByUsername(String username);


    /**
     * 将user信息保存到数据库中
     * @param user
     */
    public void save(User user);


    /**
     * 通过激活码查询用户
     * @param code
     * @return
     */
    User findByCode(String code);


    /**
     * 用户激活成功，修改激活状态
     * @param user
     */
    void updateStatus(User user);


    /**
     * 通过用户名和密码查询数据库中的数据是否正确，返回整个的user对象
     * @param name
     * @param password
     * @return
     */
    User findByUsernameAndPassword(String username, String password);
}
