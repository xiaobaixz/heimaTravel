package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.dao.impl.CategoryDaoImpl;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CategoryServiceImpl implements CategoryService {
    // 初始化dao那层的东西
    private CategoryDao dao = new CategoryDaoImpl();

    @Override
    public List<Category> findAll() {
        // 从redis中查询
        // 获取jedis的客户端
        Jedis jedis = JedisUtil.getJedis();

//        // zrange方法可以排序查询
//        Set<String> categorys = jedis.zrange("category", 0, -1);   // Set是集合的意思，是同种对象的集合
        // 查询sorted中的分数(cid)和值(zrangeWithScores可以返回cid和cname)
        Set<Tuple> categorys = jedis.zrangeWithScores("category", 0, -1);


        List<Category> cs=null;

        // 判断查询的集合是否为空
        if (categorys==null || categorys.size()==0){
            // System.out.println("I come from mysql");
            // 如果为空，需要从数据中查询，将数据存入redis
            // 在数据库里面查询
            cs = dao.findAll();

            for (int i=0; i<cs.size(); i++){
                // 将集合数据存储到redis中的 category的key
                jedis.zadd("category", cs.get(i).getCid(), cs.get(i).getCname());
            }
        } else {
            // System.out.println("I come from redis");

            // 如果不为空，将set的数据存入list(set集合转为list集合)
            cs = new ArrayList<Category>();
            for (Tuple tuple : categorys) {
                Category category = new Category();
                category.setCname(tuple.getElement());
                category.setCid((int)tuple.getScore());     // 默认存的是分数，这里进行了int强制转换
                cs.add(category);
            }
        }

        return cs;
    }
}
