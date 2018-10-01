/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * @autoor szh
 */

package com.szh.carmanager.controller;




import com.szh.carmanager.MyWxCp;
import com.szh.carmanager.domain.Cheliang;
import com.szh.carmanager.domain.Weizhi;
import com.szh.carmanager.domain.Yunshuxinxi;
import com.szh.carmanager.service.CheliangService;
import com.szh.carmanager.service.WeizhiService;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.szh.carmanager.dao.CheliangDao;
import com.szh.carmanager.service.YunshuxinxiService;
import java.util.List;

/**
 *
 * @author  szh
 * QQ:873689
 * @date 2018-7-18 15:04:56
 */
@Log4j
@RestController
public class HelloWorld {
   
    @Autowired
    WeizhiService weizhiService;
    @Autowired
    YunshuxinxiService yunshuxinxiService;
    
    @RequestMapping("/hello")
    public String Hello(){
       //car.getTime();
        Weizhi weizhi1 = new Weizhi();
        weizhi1.setWeizhi("处理中心");
        Weizhi weizhi2 = new Weizhi();
        weizhi2.setWeizhi("电商");
        weizhiService.addWeizhi(weizhi1);
        weizhiService.addWeizhi(weizhi2);
        return "" ;
    }
    
    
    @RequestMapping("/hello2")
    public String Hello2(){
       //car.getTime();
       String s = "";
       List<Yunshuxinxi> list = yunshuxinxiService.findYunshuxinxi("050");
       for(Yunshuxinxi y :list){
           s+=y.toString();
       }
       return s;
    }
    
    @RequestMapping("/hello34")
    public Boolean Hello3(){
       //car.getTime();
       String s = "";
       List<Yunshuxinxi> list = yunshuxinxiService.findYunshuxinxi("050x");
      
       return list.isEmpty();
    }
}
