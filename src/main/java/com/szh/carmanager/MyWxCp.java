/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * @autoor szh
 */

package com.szh.carmanager;


import com.szh.carmanager.controller.MyMesageRouter;
import java.util.Date;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.session.InternalSession;
import me.chanjar.weixin.common.session.StandardSession;
import me.chanjar.weixin.common.session.StandardSessionFacade;
import me.chanjar.weixin.common.session.WxSession;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.api.impl.WxCpServiceImpl;
import me.chanjar.weixin.cp.api.impl.WxCpServiceOkHttpImpl;
import me.chanjar.weixin.cp.bean.WxCpXmlMessage;
import me.chanjar.weixin.cp.bean.WxCpXmlOutMessage;
import me.chanjar.weixin.cp.bean.WxCpXmlOutTextMessage;
import me.chanjar.weixin.cp.config.WxCpInMemoryConfigStorage;
import me.chanjar.weixin.cp.message.WxCpMessageHandler;
import me.chanjar.weixin.cp.message.WxCpMessageRouter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 *
 * @author  szh
 * QQ:873689
 * @date 2018-7-19 16:44:09
 */

@Log4j
@Component 
public class MyWxCp {
    static final String sToken = "ytziKVeOq";
    static final String sCorpID = "wx29d28562d3398ad2";
    static final String sEncodingAESKey = "GgBsomMWNzpYm2z58rejCG8PlIGntwbPAVoF5JrSgib";
    static final String corpsecret = "UWpAp2AzcM1w-oGjdx7Ika1i2JCtY82aq460iuxD7hU";
    public static final int Agentid = 1000002;
    public Long ss ;
    private WxCpInMemoryConfigStorage config;
    WxCpService myservice;
    WxCpMessageRouter router;
    
    
    public MyWxCp(){
        config = new WxCpInMemoryConfigStorage();
        config.setCorpId(sCorpID);      // 设置微信企业号的sCorpID
        config.setCorpSecret(corpsecret);  // 设置微信企业号的app corpSecret
        config.setAgentId(Agentid);     // 设置微信企业号应用ID
        config.setToken(sToken);       // 设置微信企业号应用的token
        config.setAesKey(sEncodingAESKey);      // 设置微信企业号应用的EncodingAESKey
        ss  = System.currentTimeMillis();
        myservice = new WxCpServiceOkHttpImpl();
        myservice.setWxCpConfigStorage(config);
        
        
        router = new WxCpMessageRouter(myservice);
        WxCpMessageHandler handler = (WxCpXmlMessage wxMessage, Map<String, Object> context, WxCpService wxCpService, WxSessionManager sessionManager) -> {
            
            initcar(wxMessage, sessionManager);
            
            MyMesageRouter car = (MyMesageRouter)sessionManager.getSession(wxMessage.getFromUserName()).getAttribute("MyMesageRouter");
            WxCpXmlOutTextMessage m = WxCpXmlOutMessage
                    .TEXT()
                    .content(car.getMessage(wxMessage.getContent(),sessionManager.getSession(wxMessage.getFromUserName())))
                    .fromUser(wxMessage.getToUserName())
                    .toUser(wxMessage.getFromUserName())
                    .build();
            return m;
        };
        
        
        router
                .rule()
                .async(false)
                .msgType(WxConsts.XmlMsgType.TEXT)
                .handler(handler)
                .end();
        
    }
    
    public  WxCpInMemoryConfigStorage getMyconfig(){
        return config;
    }
    
    public WxCpService getWxCpService(){
        return myservice;
    }
    
    public WxCpMessageRouter getMyWxCpMessageRouter(){
        return router;
    }
    
    
    private  String guolv(String msg){
        // 只允许字母和数字 // String regEx ="[^a-zA-Z0-9]"; 
        // 清除掉所有特殊字符 
        String regEx="[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]"; 
        Pattern p = Pattern.compile(regEx); 
        Matcher m = p.matcher(msg);
        return m.replaceAll("").trim();
    }
    
    private void initcar(WxCpXmlMessage wxMessage,WxSessionManager sessionManager){
         wxMessage.setContent(guolv(wxMessage.getContent()));
         WxSession standardSession   = sessionManager.getSession(wxMessage.getFromUserName(),false);
         Date shijian ;
         if(standardSession == null) {
             shijian = new Date();
          }else{
             shijian = (Date)standardSession.getAttribute("time");
         }
         long shijiancha = (new Date().getTime()-shijian.getTime())/1000/60;
         log.info(wxMessage.getContent());
         if(standardSession == null || "主菜单".equals(wxMessage.getContent())  || shijiancha > 15){
                WxSession wxSession = sessionManager.getSession(wxMessage.getFromUserName());
                MyMesageRouter myMesageRouter = new MyMesageRouter();
                //wxSession = sessionManager.getSession(wxMessage.getFromUserName());
                wxSession.setAttribute("MyMesageRouter", myMesageRouter);
                wxSession.setAttribute("user", wxMessage.getFromUserName());
            }else{
                WxSession wxSession = sessionManager.getSession(wxMessage.getFromUserName());
                MyMesageRouter c = (MyMesageRouter) wxSession.getAttribute("MyMesageRouter");
                if(c.getStatus() == 0){
                    try{
                        if(Integer.valueOf(wxMessage.getContent())>0&&Integer.valueOf(wxMessage.getContent())<9){
                            c.setStatus(Integer.valueOf(wxMessage.getContent()));
                        }
                    }catch(NumberFormatException e){
                        log.error(e.getMessage(), e);
                    }
                }
                
         }
         WxSession wxSession = sessionManager.getSession(wxMessage.getFromUserName());
         wxSession.setAttribute("time", new Date());
         
         
    }
}
