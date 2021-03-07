package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserDaoImpl implements UserDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 根据用户名查询数据库中是存在该username
     * @param username
     * @return
     */
    @Override
    public User findByUsername(String username) {
        User user = null;
        try {
            // 定义sql
            String sql = "select * from tab_user where username = ?";

            // 执行sql
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username);
        } catch (Exception e) {

        }

        return user;
    }


    /**
     * 将user信息保存到数据库中
     * @param user
     */
    @Override
    public void save(User user) {
        // 定义sql
        String sql = "insert into tab_user (username,password,name,birthday,sex,telephone,email,status,code)" +
                "values(?,?,?,?,?,?,?,?,?)";

        // 执行sql
        template.update(sql,
                user.getUsername(),
                user.getPassword(),
                user.getName(),
                user.getBirthday(),
                user.getSex(),
                user.getTelephone(),
                user.getEmail(),
                user.getStatus(),
                user.getCode()
        );
    }


    /**
     * 通过激活码查询用户
     * @param code
     * @return
     */
    @Override
    public User findByCode(String code) {
        User user = null;
        try {
            String sql = "select * from tab_user where code = ?";

            // 执行sql语句，并返回user对象
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), code);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }

        return user;
    }

    /**
     * 用户激活成功，修改激活状态
     * @param user
     */
    @Override
    public void updateStatus(User user) {
        // status为Y表示用户已经成功激活了
        String sql = "update tab_user set status = 'Y' where uid = ?";

        template.update(sql, user.getUid());
    }


    /**
     * 通过用户名和密码查询数据库中的数据是否正确，返回整个的user对象
     * @param name
     * @param password
     * @return
     */
    @Override
    public User findByUsernameAndPassword(String username, String password) {
        User user = null;
        try {
            // 定义sql
            String sql = "select * from tab_user where username = ? and password = ?";

            // 执行sql
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username, password);
        } catch (Exception e) {

        }

        return user;
    }
}
