package cn.itcast.travel.service;

import cn.itcast.travel.domain.Category;

import java.util.List;

public interface CategoryService {
    /**
     * 找到所有路线
     * @return
     */
    public List<Category> findAll();
}
