package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.util.MailUtils;
import cn.itcast.travel.util.UuidUtil;

public class UserServiceImpl implements UserService {
    private UserDao userDao = new UserDaoImpl();

    /**
     * 注册用户
     *
     * @param user
     * @return
     */
    @Override
    public boolean regist(User user) {
        // 根据用户名查询用户对象
        User u = userDao.findByUsername(user.getUsername());

        // 判断u不为Null，u为null说明数据库中没有该username，可以直接创建
        if (u != null) {
            // 用户名存在，返回false
            return false;
        }

        // 设置激活码，唯一字符串(用于判断是哪个用户)
        // UuidUtil是一个工具类，可以生成全球唯一的码
        user.setCode(UuidUtil.getUuid());

        // 设置状态(以此来判断是否激活了)
        user.setStatus("N");

        // 保存用户信息
        userDao.save(user);

        // 激活邮件发送，发送的页面内容
        String content = "<a href='http://localhost/travel/user/active?code=" + user.getCode() + "'>点击激活【黑马旅游网】</a>";
        MailUtils.sendMail(user.getEmail(), content, "激活邮件");
        return true;
    }

    /**
     * 激活用户
     *
     * @param code
     * @return
     */
    @Override
    public boolean active(String code) {
        // 根据激活码查询用户对象
        User user = userDao.findByCode(code);
        if (user != null) {
            // 存在该用户，调用dao的修改激活状态的方法
            userDao.updateStatus(user);
            return true;
        } else {
            return false;
        }

    }


    /**
     * 登录的方法
     * @param user
     * @return
     */
    @Override
    public User login(User user) {
        return userDao.findByUsernameAndPassword(user.getUsername(), user.getPassword());
    }
}
