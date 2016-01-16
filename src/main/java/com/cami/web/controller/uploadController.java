/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cami.web.controller;

import com.cami.web.form.UploadedFile;
import java.io.IOException;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 *
 * @author gervais
 */
@Controller
@RequestMapping("/upload")
public class uploadController {
 
    UploadedFile ufile;
    
    @Autowired
    ServletContext servletContext;

    public uploadController() {
        System.out.println("init upload Controller");
        ufile = new UploadedFile();
    }
    
     @RequestMapping(value = "/", method = RequestMethod.GET)
    public String indexAction(final ModelMap model, final WebRequest webRequest)
    {
        
     
        System.out.println("HomeController");
        return "hello";
    }
    
    @RequestMapping(value = "/get/{value}", method = RequestMethod.GET)
  public void get(HttpServletResponse response,@PathVariable String value){
        try {
 
            response.setContentType(ufile.type);
            response.setContentLength(ufile.length);
            FileCopyUtils.copy(ufile.bytes, response.getOutputStream());
 
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
  }
 
   @RequestMapping(value = "/upload", method = RequestMethod.POST)
   public @ResponseBody String upload(MultipartHttpServletRequest request, HttpServletResponse response) {                 
 
     //0. notice, we have used MultipartHttpServletRequest
 
     //1. get the files from the request object
     Iterator<String> itr =  request.getFileNames();
 
     MultipartFile mpf = request.getFile(itr.next());
     System.out.println(mpf.getOriginalFilename() +" uploaded!");
 
     try {
                //just temporary save file info into ufile
        ufile.length = mpf.getBytes().length;
        ufile.bytes= mpf.getBytes();
        ufile.type = mpf.getContentType();
        ufile.name = mpf.getOriginalFilename();
 
    } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
     
 
     //2. send it back to the client as <img> that calls get method
     //we are using getTimeInMillis to avoid server cached image 
 
       
     return "<img src='"+request.getContextPath()+"/upload/get/"+Calendar.getInstance().getTimeInMillis()+"' />";
 
  }
}
