package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/user/*")
public class UserServlet extends BaseServlet {
    // 调用service完成注册操作
    private UserService service = new UserServiceImpl();

    /**
     * 注册功能
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void regist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 验证验证码是否正确
        String check = request.getParameter("check");
        // 从session中获取验证码
        HttpSession session = request.getSession();     // 获取session对象
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");    // 明确知道是字符串格式的，进行强制转换

        // 避免验证码复用的情况，立马移除验证码
        session.removeAttribute("CHECKCODE_SERVER");


        // 比较前台输入的验证码和后端生成的验证码是否相似(这里是忽略大小写的比较)
        if (checkcode_server==null || !checkcode_server.equalsIgnoreCase(check)){
            ResultInfo info = new ResultInfo();     // 用于封装后端返回前端数据对象

            // 验证码错误
            info.setFlag(false);
            info.setErrorMsg("验证码错误!");

            // 将info对象序列化为json，写回客户端
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(info);

            // 将json数据写回客户端
            response.setContentType("application-json;charset=utf-8");  // 设置类型，让客户端识别到这是json的数据
            response.getWriter().write(json);
            return;
        }


        // 获取数据(map集合保存键值对的关系，相当于字典)
        Map<String, String[]> map = request.getParameterMap();

        // 封装对象，将map集合封账到user对象中
        User user = new User();
        try {
            BeanUtils.populate(user, map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        // 调用service完成注册操作
        // UserService service = new UserServiceImpl();
        boolean flag = service.regist(user);    // 接受user对象，完成注册操作
        ResultInfo info = new ResultInfo();     // 用于封装后端返回前端数据对象

        // 响应结果
        if (flag) {
            // 注册成功
            info.setFlag(true);
        } else {
            // 注册失败
            info.setFlag(false);
            info.setErrorMsg("注册失败!");
        }

        // 将info对象序列化为json，写回客户端
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(info);

        // 将json数据写回客户端
        response.setContentType("application-json;charset=utf-8");  // 设置类型，让客户端识别到这是json的数据
        response.getWriter().write(json);
    }

    /**
     * 登录的功能
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取用户名和密码
        Map<String, String[]> map = request.getParameterMap();
        // 封装User对象
        User user = new User();
        try {
            BeanUtils.populate(user, map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        // 调用service完成查询的操作
        // UserService service = new UserServiceImpl();
        User u = service.login(user);    // 这里的封装可以封装所有的对象

        ResultInfo info = new ResultInfo();

        // 判断用户对象是否为null
        if (u == null){
            // 用户名或密码错误
            info.setFlag(false);
            info.setErrorMsg("用户名或密码错误");
        }

        // 判断用户是否激活
        if (u!=null && !"Y".equals(u.getStatus())){
            // 用户还没有激活
            info.setFlag(false);
            info.setErrorMsg("您还没有激活，请登录邮箱激活");
        }

        // 登录成功的判断
        if (u!=null && "Y".equals(u.getStatus())){
            // 登录成功
            info.setFlag(true);
            request.getSession().setAttribute("user",u);    // 登录成功，将存上session对象
        }

        // 响应数据
        ObjectMapper mapper = new ObjectMapper();
        // 设置响应格式为json类型，让前台的工作人员容易操作
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(), info);
    }

    /**
     * 查找一个对象
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findone(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 在session中获取登录用户
        Object user = request.getSession().getAttribute("user");


        // 将user写回客户端
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(), user);
    }

    /**
     * 退出功能
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void exit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 销毁session
        request.getSession().invalidate();

        // 跳转页面
        response.sendRedirect(request.getContextPath() + "/login.html");
    }


    /**
     * 激活的功能
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void active(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取激活码
        String code = request.getParameter("code");
        if (code != null) {
            // 调用service完成激活的操作
            // UserService service = new UserServiceImpl();
            boolean flag = service.active(code);

            // 判断标记
            String msg = null;
            if (flag) {
                // 激活成功
                msg = "激活成功，请<a href='login.html'>登录</a>";
            } else {
                // 激活失败
                msg = "激活失败，请联系管理员!";
            }

            response.setContentType("text/html;charset=utf-8"); // 防止前台乱码
            response.getWriter().write(msg);    // 返回响应的信息
        }
    }
}
