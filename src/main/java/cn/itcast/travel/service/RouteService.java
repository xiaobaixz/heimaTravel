package cn.itcast.travel.service;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;

/**
 * 线路的Service
 */
public interface RouteService {
    /**
     * 根据类别进行分页查询的方法
     * @param cid
     * @param currentPage
     * @param pageSize
     * @return
     */
    public PageBean<Route> PageQuery(int cid, int currentPage, int pageSize, String rname);

    /**
     * 根据rid查询
     * @param rid
     * @return
     */
    Route findOne(String rid);
}
