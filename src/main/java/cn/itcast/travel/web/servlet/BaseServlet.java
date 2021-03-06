package cn.itcast.travel.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class BaseServlet extends HttpServlet {
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取请求路径
        String uri = request.getRequestURI();
        //System.out.println("我是基础:"+uri);  //  /travel/user/find
        //获取方法名称
        String methodName = uri.substring(uri.lastIndexOf('/') + 1);//要加1，为了去掉‘/’
        //System.out.println("方法名称:"+methodName);

        //3.获取方法对象Method
        try {
            //获取方法
            Method method = this.getClass().getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            //4.执行方法
            method.invoke(this,request,response);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }



    }

    /**
     * 直接将传入的对象序列化为json，并且写回客户端
     * @param obj
     * @param response
     * @throws IOException
     */
        public void writeValue(Object obj, HttpServletResponse response) throws ServletException, IOException {
            ObjectMapper mapper = new ObjectMapper();
            response.setContentType("application/json;charset=utf-8");
            mapper.writeValue(response.getOutputStream(),obj);
    }

    /**
     * 将传入的对象序列化为json，返回给调用者
     * @param obj
     * @return
     */
    public String writeValueAsString(Object obj) throws ServletException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(obj);
    }
}
