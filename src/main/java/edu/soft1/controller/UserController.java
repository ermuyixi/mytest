package edu.soft1.controller;
import com.sun.applet2.AppletParameters;
import edu.soft1.pojo.User;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLEncoder;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    @RequestMapping(value = "upload",method = {RequestMethod.POST})
    public  String fileUpload(MultipartFile image,HttpServletRequest request) throws IOException {
        InputStream is = image.getInputStream();//输入流
        //获取文件的真实名字
        String filename = image.getOriginalFilename();//文件名称
        //在webapp文件夹下建立一个，新建一个images文件夹，作为上传下载的存储位置，这里获取它的真实路径
        String realPath = request.getServletContext().getRealPath("/images");
        System.out.println("上传路径="+realPath);
        //根据images文件夹的真实路径和文件的名字创建输出流对象
        FileOutputStream os = new FileOutputStream(new File(realPath,doFileName(filename)));
        //把输入流文件写入到输出流,完成文件的上传
        int size = IOUtils.copy(is,os);
        System.out.println("完成上传size="+size+"Byte");
        //释放资源，原则：先开后关，后开先关
        os.close();
        is.close();//关闭流
        return "welcome";
    }
    private String doFileName(String filename){
        String extension = FilenameUtils.getExtension(filename);//获取文件的后缀名称
        String uuid = UUID.randomUUID().toString();//获取uuid字符，规避名称重复
        System.out.println("上传文件名="+uuid);
        return  uuid+"."+extension;
    }

    @RequestMapping(value = "upload2",method = {RequestMethod.POST})
    public  String fileUpload2(MultipartFile[] images,HttpServletRequest request) throws IOException {
        InputStream is = null;
        OutputStream os = null;
        int count = 0;//计数器
        for (MultipartFile image:images) {
             is = image.getInputStream();//获取对象的输入流对象
            String filename = image.getOriginalFilename();//文件名称
            System.out.println("文件原名称="+filename);
            if (filename.equals("")){
                System.out.println("空字符串，进入下一轮循环");
                continue;//进入下一轮循环
            }
            String realPath = request.getServletContext().getRealPath("/images");
            System.out.println("上传路径realPath=" + realPath);
            os = new FileOutputStream(new File(realPath, doFileName(filename)));
            int size = IOUtils.copy(is, os);
            if (size > 0){
                count++;
            }
//            System.out.println("完成上传size=" + size + "Byte");
//            IOUtils.copy(is,os); //把输入流文件写入到输出流,完成文件的上传
        }
        os.close();
        is.close();//关闭流
        AppletParameters map = null;
        map.put("mag2",count);
        System.out.println("上传成功"+count+"张图片！");
        return "welcome";
    }

    @RequestMapping(value = "/login")   
    public String login(User user, HttpSession session,HttpServletRequest request){
        System.out.println("-----login()-----");
        int flag = 1;
        if (flag == 1){//调用业务层(业务层调用dao层)，获取flag的值
            System.out.println("username="+user.getUsername());
            session.setAttribute("user",user);//登录对象放入session中
            return "welcome";//登录成功
        }
        System.out.println("登录失败，返回首页！");
        request.setAttribute("error","登录失败，请重新尝试！");
        return "forward:/index.jsp";//登录失败
//        return "forward:index.jsp";
    }
    @RequestMapping("/delete")
    public String delete(HttpServletRequest request){
        System.out.println("-----执行delete()成功！-----");
        request.setAttribute("CRUDmsg","删除功能执行完毕！");
        return "welcome";
    }

    @RequestMapping("logout")//登出功能
    public String logout(HttpSession session){
        //清空session
        session.invalidate();
        System.out.println("已登出！");
        return  "redirect:/index.jsp";//重定向跳转至首页
    }

        @RequestMapping("/hello.do")
    public String hello() {
        System.out.println("Hello");
        return "hello";
    }

    @RequestMapping("/reg")
    public  String reg(User user){
        System.out.println("username="+user.getUsername());
        System.out.println("pwd="+user.getAge());
        System.out.println("pwd="+user.getPwd());
        System.out.println("birthday="+user.getBirthday());
        System.out.println("address="+user.getAddress().getCity());
        System.out.println("phone="+user.getAddress().getPhone());
        System.out.println("street="+user.getAddress().getStreet());
        return "hello";
    }
    @RequestMapping(value = "/load.do/{filename}")
    public void load(@PathVariable String filename, HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setHeader("Content-Disposition","attachment;filename="+doFileName2(request,filename));
        String realPath =request.getServletContext().getRealPath("/images");
        System.out.println("下载路径="+realPath);
       FileInputStream is = new FileInputStream(new File(realPath,filename));
       OutputStream os = response.getOutputStream();
      // IOUtils.copy(is,os);
        int size = IOUtils.copy(is,os);
        if (size > 0){
            response.setContentType("text/html;charset=utf-8");
            PrintWriter writer = response.getWriter();
            writer.print("<script type='text/javascript'>alert('')</script>");
        }
       os.close();is.close();
    }

    private String doFileName2(HttpServletRequest request, String filename) {
        return "";
    }
//    private String doFilename2(String filename,HttpServletRequest request){
//        String userAgent = request.getHeader("User-Agent");
//        if (userAgent.toLowerCase().indexOf("FIREFOX")>0){
//            filename = "?UTF-8?B?"+new String();
//        }else {
//            filename = URLEncoder.encode()
//        }
//        return "";
//    }

}
