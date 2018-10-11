/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * @autoor szh
 */

package com.szh.carmanager.controller;


import com.szh.carmanager.MyWxCp;
import com.szh.carmanager.domain.Cheliang;
import lombok.extern.log4j.Log4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.cp.bean.WxCpMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author  szh
 * QQ:873689
 * @date 2018-7-19 14:10:08
 */
@Log4j
@RestController
public class HelloWorld2 {

    @Autowired
    MyWxCp mmy;
    
    @RequestMapping("/hello21")
    public String Hello() throws WxErrorException{
        WxCpMessage message = WxCpMessage
            .TEXT()
            .agentId(mmy.getMyconfig().getAgentId()) // 企业号应用ID
            .toUser("szh")
            .content("sfsfdsdf")
            .build();
        mmy.getWxCpService().messageSend(message);
        return "" ;
    }
}
