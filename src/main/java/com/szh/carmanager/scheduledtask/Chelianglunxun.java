/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * @autoor szh
 */

package com.szh.carmanager.scheduledtask;

import com.szh.carmanager.MyWxCp;
import com.szh.carmanager.dao.YunshuxinxiDao;
import com.szh.carmanager.domain.Yunshuxinxi;
import com.szh.carmanager.service.YunshushichangService;
import com.szh.carmanager.service.YunshuxinxiService;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import me.chanjar.weixin.cp.bean.WxCpMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 *
 * @author  szh
 * QQ:873689
 * @date 2018-10-8 14:12:01
 */
@Component
public class Chelianglunxun {

    @Autowired
    YunshuxinxiService yunshuxinxiService;
    @Autowired
    YunshushichangService yunshushichangService;
    @Autowired
    MyWxCp mmy;
    
    SimpleDateFormat sdf=new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
    
    @Scheduled(cron = "0 0 * * * ?")
    public void jianchaxinxi(){
        List<Yunshuxinxi> list = yunshuxinxiService.findByXinxiDingshi();
        for(Yunshuxinxi y : list){
            switch(y.getZhuangtai()){
                case "车辆发车":
                    long shichangfenzhong =  yunshushichangService.findShichang(y.getShifadi(), y.getDaodadi()).getShichang();
                    //System.out.println(shichangfenzhong);
                    long shichang = shichangfenzhong*60*1000;
                    long shijiancha = new Date().getTime()  - y.getCreatetime().getTime();
                    if(shijiancha >= shichang){
                        WxCpMessage message = WxCpMessage
                        .TEXT()
                        .agentId(mmy.getMyconfig().getAgentId()) // 企业号应用ID
                        .toUser("@all")
                        .content("测试信息："+y.getChepai()+"电话"+y.getDianhua()+"，于"+sdf.format(y.getCreatetime())+"从"+y.getShifadi()+"到"+y.getDaodadi()+"，超预计"+String.format("%.1f",(shijiancha-shichang)/1000.0/60.0/60.0)+"小时未到，请及时联系")
                        .build();
                        try{
                            mmy.getWxCpService().messageSend(message);
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                    break;
                case "车辆到达":
                    //long shichangfenzhong =  yunshushichangService.findShichang(y.getShifadi(), y.getDaodadi()).getShichang();
                    //System.out.println(shichangfenzhong);
                    //long shichang = shichangfenzhong*60*1000;
                    long shichangD = 3*1000*60*60;
                    long shijianchaD = new Date().getTime()  - y.getCreatetime().getTime();
                    if(shijianchaD >= shichangD){
                        WxCpMessage message = WxCpMessage
                        .TEXT()
                        .agentId(mmy.getMyconfig().getAgentId()) // 企业号应用ID
                        .toUser("@all")
                        .content("测试信息："+y.getChepai()+"电话"+y.getDianhua()+"，于"+sdf.format(y.getCreatetime())+"到达"+y.getDaodadi()+"，已停留"+String.format("%.1f",shijianchaD/1000.0/60.0/60.0)+"小时，请及时联系")
                        .build();
                        try{
                            mmy.getWxCpService().messageSend(message);
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                    break;
            }
            
            
        }
    }
}
