package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.SellerDao;
import cn.itcast.travel.domain.Seller;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class SellerDaoImpl implements SellerDao {

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());


    /**
     * 根据id查询
     * @param id
     * @return
     */
    @Override
    public Seller findById(int id) {
        // 数据库里面只有黑马那一条的卖家数据
        String sql = "select * from tab_seller where sid = ? ";
        // 查询结果，将结果封装为对象
        return template.queryForObject(sql,new BeanPropertyRowMapper<Seller>(Seller.class), id);
    }
}
