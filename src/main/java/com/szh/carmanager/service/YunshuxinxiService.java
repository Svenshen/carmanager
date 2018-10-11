/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * @autoor szh
 */

package com.szh.carmanager.service;

import com.szh.carmanager.domain.Yunshuxinxi;
import com.szh.carmanager.dao.YunshuxinxiDao;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author  szh
 * QQ:873689
 * @date 2018-7-25 13:39:55
 */
@Log4j
@Service
public class YunshuxinxiService {

    @Autowired
    YunshuxinxiDao yunshuxinxiDao;
    /**
     * 增加运输信息
     * @param yunshuxinxi
     * @param chepai
     * @return 
     */
    public boolean addYunshuxinxi(Yunshuxinxi yunshuxinxi){
        boolean flag = true;
        try{
            
            yunshuxinxi.setCreatetime(new Date());
            yunshuxinxiDao.save(yunshuxinxi);
        }catch(Exception e){
            log.error(e.getMessage(),e);
            flag = false;
        }
        return flag;
    }
    /**
     * 根据车牌查询运输信息
     * @param chepai
     * @return 
     */
    public List<Yunshuxinxi> findYunshuxinxi(String chepai){
        return yunshuxinxiDao.findByChepaiOrderByCreatetimeDesc(chepai);
    }
    
    
    public List<Yunshuxinxi> findByXinxiDingshi(){
        return yunshuxinxiDao.findByXinxiDingshi();
    }
    
    public List<Object[]> findByxinxihuizong(){
        return yunshuxinxiDao.findByXinxihuizong();
    }
    
    public void delBychepai(String chepai){
        for(Yunshuxinxi x : findYunshuxinxi(chepai)){
            x.setIs_del(true);
            yunshuxinxiDao.save(x);
        }
    }
    
}
