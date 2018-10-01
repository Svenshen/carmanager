/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * @autoor szh
 */

package com.szh.carmanager.service;

import com.szh.carmanager.domain.Cheliang;
import java.util.ArrayList;
import java.util.List;
import lombok.NonNull;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.szh.carmanager.dao.CheliangDao;

/**
 *
 * @author  szh
 * QQ:873689
 * @date 2018-7-20 19:40:30
 */
@Log4j
@Service
public class CheliangService {

    
    
    @Autowired
    CheliangDao cheliangDao ;
    
    /**
     * 更新或新增车辆
     * @param car
     * @return 
     */
    public boolean saveCar(@NonNull Cheliang car){
        boolean flag =true;
        try{
            cheliangDao.save(car);
            }catch(Exception e){
                log.error(e.getMessage(), e);
                flag = false;        
            }
        return flag;
    }
    
    /**
     * 查找车辆是否已存在
     * @param chepai
     * @return 
     */
    public boolean isCarexits(@NonNull String chepai){
        return cheliangDao.existsById(chepai);
    }
    
    /**
     * 查找所有车辆
     * @return 
     */
    public List<Cheliang> findAllCar(){
        return cheliangDao.findAll();
    }
    
    /**
     * 根据车牌查找车辆
     * @param chepai
     * @return 
     */
    public Cheliang findCar(String chepai){
        return cheliangDao.findById(chepai).get();
    }
}
