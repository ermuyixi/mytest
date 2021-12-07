package edu.soft1.controller;

import edu.soft1.pojo.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
@RequestMapping("/param1")
public class MyController {


//    @RequestMapping("/hello.do")
//    public String hello() {
//        System.out.println("Hello");
//        return "hello";
//    }

//@RequestMapping( "/hello")
//    public ModelAndView hello() {
//        System.out.println("--------hello()-------");//进入方法的标志
//       ModelAndView mav = new ModelAndView();
//        mav.setViewName("hello");
//        mav.addObject("msg","I am Peter!");
//        //添加到ModelAndView中的对象，存入了request作用域
//        return mav;
//    }

    @RequestMapping("/hello")
    public String hello(String username, Model model){
        System.out.println("----hello()----");
        //将传入的参数添加到Mpdel对象（存入request作用域）
//        model.addAttribute("username",username);
       model.addAttribute(username);
        return "hello";
    }



    @RequestMapping(value = "param1", method = {RequestMethod.GET})
    public String param1(HttpServletRequest request) {
        //接收client发来的数据
        String name = request.getParameter("name");//获取数据
        System.out.println("name=" + name);//打印接收过来的数据（参数形式）
        request.setAttribute("name", name);//将数据存入request
        //调用业务层的方法
        //页面跳转
        return "hello";
    }

    @RequestMapping(value = "param2", method = {RequestMethod.GET, RequestMethod.POST})
    public String param2(HttpServletRequest request, HttpSession session) {
        //接收client发来的数据
        String name = request.getParameter("username");//获取数据
        String age = request.getParameter("age");
        System.out.println("name=" + name+",age="+age);//打印接收过来的数据（参数形式）
        session.setAttribute("age",age);
        request.setAttribute("name", name);//将数据存入request
        //调用业务层的方法
        //页面跳转
        return "hello";
    }
    @RequestMapping(value = "param3",method = {RequestMethod.POST})//数据名与方法参数名相同
    public String param3(String username,int age){
        System.out.println("----param3()------");
        System.out.println("username=" + username+",age="+age);
        return "hello";
    }
    @RequestMapping(value = "param4",method = {RequestMethod.POST})//数据名与方法参数名不同
    public String param4(
            @RequestParam(value = "username",required = false) String u,
            @RequestParam(value = "age",defaultValue ="11") int a,HttpSession session){
        System.out.println("----param4()------");
        System.out.println("u=" + u+",a="+a);
        session.setAttribute("name",u);
        return "redirect:test";//url:/WEB-INF/jsp/hello.jsp(默认为转发跳转)
    }
    @RequestMapping(value = "param5",method = {RequestMethod.POST})//数据名与方法参数名不同
    public String param5(User user,HttpSession session){
        System.out.println("----param5()------");
        System.out.println("username=" + user.getUsername()+",age="+ user.getAge());
        session.setAttribute("name", user.getUsername());
        return "redirect:test";
    }

    @RequestMapping("/test")
    public String test(){
        System.out.println("---test()---");
        return "hello";//hello.jsp
    }

//    @RequestMapping("reg")
//    public  String reg(String username, String pwd, Date birthday){
//        System.out.println("username="+username);
//        System.out.println("pwd="+pwd);
//        System.out.println("birthday="+birthday);
//        return "redirect:test";
//    }
    @RequestMapping("/reg")
    public  String reg(User user){
        System.out.println("username="+user.getUsername());
        System.out.println("pwd="+user.getAge());
        System.out.println("birthday="+user.getBirthday());
        System.out.println("address="+user.getAddress().getCity());
        System.out.println("phone="+user.getAddress().getPhone());
        System.out.println("street="+user.getAddress().getStreet());
        return "redirect:test";
    }


}