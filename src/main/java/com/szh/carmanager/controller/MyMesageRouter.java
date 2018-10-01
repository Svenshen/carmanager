/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * @autoor szh
 */

package com.szh.carmanager.controller;

import com.szh.carmanager.SpringUtil;
import com.szh.carmanager.domain.Cheliang;
import com.szh.carmanager.domain.Weizhi;
import com.szh.carmanager.domain.Yunshuxinxi;
import com.szh.carmanager.service.CheliangService;
import java.util.List;
import javax.annotation.PostConstruct;
import lombok.Data;
import me.chanjar.weixin.common.session.WxSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import com.szh.carmanager.dao.CheliangDao;
import com.szh.carmanager.service.WeizhiService;
import com.szh.carmanager.service.YunshuxinxiService;
import lombok.extern.log4j.Log4j;

/**
 *
 * @author  szh
 * QQ:873689
 * @date 2018-7-20 13:36:48
 */
@Log4j
@Data
//@Scope("prototype")
public class MyMesageRouter {
    
    /**
     * 状态值0表示无任何状态,初始值
     * 1表示车辆新增，2表示车辆修改
     * 3表示车辆卸车,4表示车辆装车
     * 5表示车辆发车,6表示车辆到达
     * 7表示车辆状态报表
     */
    private int status = 0;
    private int step=0;
    
    
    CheliangService carService  = SpringUtil.getBean(CheliangService.class);
    YunshuxinxiService yunshuxinxiService = SpringUtil.getBean(YunshuxinxiService.class);
    WeizhiService weizhiService = SpringUtil.getBean(WeizhiService.class);
    
        
    Cheliang car = new Cheliang();
    Yunshuxinxi yunshuxinxi = new Yunshuxinxi();
    Weizhi weizhi = new Weizhi();
    
    /**
     * 初始化
     * @param wxSession
     * @void
     * 
     */
    public void init(WxSession wxSession){
        wxSession.invalidate();
    }
    /**
     * 获取消息反馈
     * @param msg
     * @param wxSession
     * @return 
     */
    public String getMessage(String msg,WxSession wxSession){
        StringBuilder sb  = new StringBuilder();
        
            switch(status){
                case 1:
                    switch (step){
                        
                        case 1:
                            if(carService.isCarexits(msg)){
                                sb.append("车辆已存在，请重新输入车牌");
                            }else{
                                sb.append("请输入司机电话");
                                car.setChepai(msg);
                                step++;
                            }
                            
                            break;
                        case 2:
                            car.setDianhua(msg);
                            
                            if(carService.saveCar(car)){
                                 sb.append("车辆新增成功");
                            }else{
                                sb.append("请核对车牌信息");
                            }
                            init(wxSession);
                            break;
                        default:
                            sb.append("请输入车牌");
                            step++;
                            break;
                    }
                    break;
                case 2:
                    switch(step){
                        case 1:
                            if(!carService.isCarexits(msg)){
                                sb.append("车辆不存在，请重新输入车牌");
                            }else{
                                sb.append("请输入司机电话");
                                car.setChepai(msg);
                                step++;
                            }
                            break;
                        case 2:
                            car.setDianhua(msg);
                            if(carService.saveCar(car)){
                                sb.append("修改成功");
                            }else{
                                sb.append("修改出错");
                            }
                            init(wxSession);
                            break;
                        default:
                            List<Cheliang> carlist = carService.findAllCar();
                            if(carlist.isEmpty()){
                                sb.append("没有车辆信息");
                                init(wxSession);
                            }else{
                                sb.append("车辆信息\r\n");
                                sb.append("-------------------\r\n");
                                carlist.forEach((c) -> {
                                    sb.append("车牌：").append(c.getChepai()).append(",").append("司机电话：").append(c.getDianhua()).append("\r\n");
                                });
                                sb.append("-------------------\r\n");                                
                                sb.append("请输入要修改的车辆车牌");
                            }
                            step++;
                            break;
                    }
                    break;
                case 3:
                    yunshuxinxi.setZhuangtai("车辆发车");
                    switch(step){
                        case 1:
                            if(!carService.isCarexits(msg)){
                                sb.append("车辆不存在，请重新输入车牌");
                            }else{
                                car = carService.findCar(msg);
                                yunshuxinxi.setChepai(car.getChepai());
                                yunshuxinxi.setDianhua(car.getDianhua());
                                sb.append("目的地信息\r\n");
                                sb.append("-------------------\r\n");
                                List<Weizhi> weizhi = weizhiService.findAllweizhi();
                                weizhi.forEach((w) ->{
                                    sb.append("").append(w.getId()).append(".").append(w.getWeizhi()).append("\r\n");
                                });
                                sb.append("-------------------\r\n");
                                sb.append("请输入你要选择的目的地序号");
                                step++;
                            }
                            break;
                        case 2:
                            
                            Long id = -1L;
                            try{
                                id = Long.valueOf(msg);
                            }catch(Exception e){
                                log.error(e.getMessage(), e);
                            }
                            if(id != -1L){
                                if(weizhiService.isWeizhiexits(id)){
                                    weizhi = weizhiService.findWeizhi(id);
                                    yunshuxinxi.setDaodadi(weizhi.getWeizhi());
                                    List<Yunshuxinxi> yunshuxinxilist = yunshuxinxiService.findYunshuxinxi(yunshuxinxi.getChepai());
                                    if(yunshuxinxilist.isEmpty()){
                                        yunshuxinxiService.addYunshuxinxi(yunshuxinxi);
                                        sb.append("车辆发车成功");
                                    }else{
                                        String zhuangtai = yunshuxinxilist.get(0).getZhuangtai();
                                        if("车辆发车".equals(zhuangtai)){
                                            sb.append("车辆已经发车，无法重复发车");
                                        }else{
                                            yunshuxinxiService.addYunshuxinxi(yunshuxinxi);   
                                            sb.append("车辆发车成功");
                                        }
                                    }
                                    init(wxSession);
                                }else{
                                    sb.append("输入错误，请重新输入");
                                }
                            }else{
                                sb.append("输入错误请重新输入");
                            }
                            break;
                        default:
                            sb.append("请输入要发车的车辆车牌");
                            step++;
                            break;
                    }
                    break;
                case 4:
                    yunshuxinxi.setZhuangtai("车辆到达");
                    switch(step){
                        case 1:
                            if(!carService.isCarexits(msg)){
                                sb.append("车辆不存在，请重新输入车牌");
                            }else{
                                car = carService.findCar(msg);
                                yunshuxinxi.setChepai(car.getChepai());
                                yunshuxinxi.setDianhua(car.getDianhua());
                            }
                            break;                        
                        default:
                            sb.append("请输入到达的车辆车牌");
                            step++;
                            break;
                    }
                    init(wxSession);
                    break;
                case 5:
                    init(wxSession);
                    break;
                case 6:
                    init(wxSession);
                    break;
                case 7:
                    init(wxSession);
                    break;
                case 8:
                    init(wxSession);
                    break;
                default:
                    sb.append("主菜单\r\n");
                    sb.append("-------------------\r\n");
                    sb.append("1.车辆新增\r\n");
                    sb.append("2.车辆信息修改\r\n");
                    sb.append("3.车辆发车\r\n");
                    sb.append("4.车辆到达\r\n");
                    sb.append("5.车辆卸车\r\n");
                    sb.append("6.车辆装车\r\n");
                    sb.append("7.车辆行程结束\r\n");
                    sb.append("8.车辆状态报表");
                    break;
            }
            
        return sb.toString();
    }
    
    
}
