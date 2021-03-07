package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.impl.FavoriteDaoImpl;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.service.FavoriteService;

public class FavoriteServiceImpl implements FavoriteService {

    private FavoriteDao favoriteDao = new FavoriteDaoImpl();

    @Override
    public boolean isFavorite(String rid, int uid) {

        Favorite favorite = favoriteDao.findByRidAndUid(Integer.parseInt(rid), uid);

        return favorite != null;   //如果对象有值，则为true，反之，则为false
    }

    /**
     * 添加收藏线路的方法
     * @param rid   线路id
     * @param uid   用户id
     */
    @Override
    public void add(String rid, int uid) {
        // 调用dao层的添加方法，将相关的参数添加到数据库中
        favoriteDao.add(Integer.parseInt(rid),uid);
    }
}
