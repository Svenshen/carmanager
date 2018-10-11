/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * @autoor szh
 */

package com.szh.carmanager.service;

import com.szh.carmanager.dao.YunshushichangDao;
import com.szh.carmanager.domain.Yunshushichang;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author  szh
 * QQ:873689
 * @date 2018-10-8 16:16:30
 */
@Service
public class YunshushichangService {

    @Autowired
    YunshushichangDao yunshushichangDao;
    
    public Yunshushichang findShichang(String shifadi,String daodadi){
        return yunshushichangDao.findByShifadiAndDaodadi(shifadi, daodadi);
    }
    
   
    
}
