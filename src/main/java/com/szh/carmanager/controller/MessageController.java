/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * @autoor szh
 */

package com.szh.carmanager.controller;

import com.szh.carmanager.MyWxCp;
import com.szh.carmanager.MyWxCp;
import com.szh.carmanager.domain.weixin.Message;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.cp.api.impl.WxCpServiceImpl;
import me.chanjar.weixin.cp.bean.WxCpXmlMessage;
import me.chanjar.weixin.cp.bean.WxCpXmlOutMessage;
import me.chanjar.weixin.cp.config.WxCpConfigStorage;
import me.chanjar.weixin.cp.message.WxCpMessageRouter;
import me.chanjar.weixin.cp.util.crypto.WxCpCryptUtil;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author  szh
 * QQ:873689
 * @date 2018-7-18 15:54:06
 */

@Slf4j
@RestController
@RequestMapping("/message")
public class MessageController {
    @Autowired
    MyWxCp mywxcp;
    
    /**
     * 微信接收信息的验证
     * @param message
     * 
     * @return 解密后的信息
     */     
    @GetMapping("/recive")
    public String validate( Message message){
            WxCpConfigStorage  wxCpConfigStorage = mywxcp.getMyconfig();
            WxCpServiceImpl wxCpService = mywxcp.getWxCpService();
            String msgSignature = message.getMsg_signature();
            String nonce = message.getNonce();
            String timestamp = message.getTimestamp();
            String echostr = message.getEchostr();

            
            //response.setStatus(HttpServletResponse.SC_OK);
            if (StringUtils.isNotBlank(echostr)) {
                if (!wxCpService.checkSignature(msgSignature, timestamp, nonce, echostr)) {
                  // 消息签名不正确，说明不是公众平台发过来的消息
                   return "";
                  //out.println("非法请求");
                  //System.out.println("非法请求");
                }else{
                    WxCpCryptUtil cryptUtil = new WxCpCryptUtil(wxCpConfigStorage);
                    String plainText = cryptUtil.decrypt(echostr);
                    // 企业号应用在开启回调模式的时候，会传递加密echostr给服务器，需要解密并echo才能接入成功
                    return  plainText;
                    //out.println(plainText);
                    //System.out.println(plainText);
                }
              }
        return "";
    }
    
    /**
     * 微信信息的接收
     * @param message
     * @param request
     * @return  String
     * @throws java.io.IOException
     * 
     */
    @PostMapping("/recive")
    public String recive(Message message,HttpServletRequest request) throws IOException{
            WxCpConfigStorage  wxCpConfigStorage = mywxcp.getMyconfig();
            WxCpServiceImpl wxCpService = mywxcp.getWxCpService();
            //wxCpService.setWxCpConfigStorage(wxCpConfigStorage);
            WxCpMessageRouter wxCpMessageRouter = mywxcp.getMyWxCpMessageRouter();
            String msgSignature = message.getMsg_signature();
            String nonce = message.getNonce();
            String timestamp = message.getTimestamp();
            String echostr = message.getEchostr();
            
            
            //response.setStatus(HttpServletResponse.SC_OK);
            if (!StringUtils.isNotBlank(echostr) ) {
                // 如果没有echostr，则说明传递过来的用户消息，在解密方法里会自动验证消息是否合法
                WxCpXmlMessage inMessage = WxCpXmlMessage.fromEncryptedXml(request.getInputStream(), wxCpConfigStorage, timestamp, nonce, msgSignature);
                System.out.println(inMessage);
                //WxCpMessage.TEXT().agentId(inMessage.getAgentId()).content("sssss").build();
                WxCpXmlOutMessage outMessage = wxCpMessageRouter.route(inMessage);
               //outMessage.
                //System.out.println(outMessage.getToUserName());
                if (outMessage != null) {
                  // 将需要同步回复的消息加密，然后再返回给微信平台
                  return outMessage.toEncryptedXml(wxCpConfigStorage);
                }
              }
        return "";
    }
    
}
