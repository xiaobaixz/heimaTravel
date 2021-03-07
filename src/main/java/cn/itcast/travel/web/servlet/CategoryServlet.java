package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.service.impl.CategoryServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/category/*")
public class CategoryServlet extends BaseServlet {
    // 初始化Service层
    private CategoryService service = new CategoryServiceImpl();

    /**
     * 查询数据库中所有类别的Servlet方法
     * @throws ServletException
     * @throws IOException
     */
    public void findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 调用service查询所有
        List<Category> cs = service.findAll();


        // 序列化返回数据(这里调用父类BaseServlet的方法)
        writeValue(cs, response);
    }
}
