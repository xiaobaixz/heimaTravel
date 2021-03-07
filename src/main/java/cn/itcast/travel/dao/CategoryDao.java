package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Category;

import java.util.List;

public interface CategoryDao {
    /**
     * 在数据库中查询出来所有的类别信息，通过service和servlet放到html页面上进行展示
     * @return
     */
    public List<Category> findAll();
}
