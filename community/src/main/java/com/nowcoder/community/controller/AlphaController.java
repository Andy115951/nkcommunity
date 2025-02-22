package com.nowcoder.community.controller;


import com.nowcoder.community.service.AlphaService;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

@Controller
@RequestMapping("/alpha")
public class AlphaController {
    @Autowired
    private AlphaService alphaService;

    @RequestMapping("/hello")
    @ResponseBody
    public String sayHello(){
        return "helloworld.";
    }
    @RequestMapping("/data")
    @ResponseBody
    public String getdata(){
        return alphaService.getAlphaDao();
    }

    @RequestMapping("/http")
    public void http(HttpServletRequest request, HttpServletResponse response){
        System.out.println(request.getMethod());
        System.out.println(request.getServletPath());
        Enumeration<String>enumeration=request.getHeaderNames();
        while(enumeration.hasMoreElements()){
            String name=enumeration.nextElement();
            String value=request.getHeader(name);
            System.out.println(name+value);

        }
        System.out.println(request.getParameter("code"));
        response.setContentType("text/html;charset=utf-8");
        try(
                PrintWriter writer=response.getWriter();
                ) {

            writer.write("<h1>nowcode</h1>");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @RequestMapping(path="/teacher",method = RequestMethod.GET)
    public ModelAndView getTeacher(){
        ModelAndView mav = new ModelAndView();
        mav.addObject( "name", "张三");
        mav.addObject( "age",30);
        mav.setViewName("/demo/view");
        return mav;
    }




}
