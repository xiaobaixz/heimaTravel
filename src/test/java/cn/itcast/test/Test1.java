package cn.itcast.test;

import cn.itcast.travel.web.servlet.CategoryServlet;
import org.junit.Test;

import javax.servlet.ServletException;
import java.io.IOException;


public class Test1 {
    /**
     * 测试idea控制台中文乱码的问题
     */
    @Test
    public void test1() {
        System.out.println("现在是不是不乱码了，从理论上来说是的");
    }

    @Test
    public void test2() throws ServletException, IOException {
        CategoryServlet servlet = new CategoryServlet();

    }
}
