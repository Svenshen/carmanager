/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * @autoor szh
 */

package com.szh.carmanager.service;

import com.szh.carmanager.domain.Weizhi;
import com.szh.carmanager.dao.WeizhiDao;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.log4j.Log4j;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;
/**
 *
 * @author  szh
 * QQ:873689
 * @date 2018-7-23 13:59:26
 */
@Log4j
@Service
public class WeizhiService {

    @Autowired
    private WeizhiDao weizhiDao;
    
    /**
     * 增加位置
     * @param weizhi
     * @return 
     */
   public boolean addWeizhi(Weizhi weizhi){
       boolean flag = true;
       try{
           weizhiDao.save(weizhi);
       }catch(Exception e){
           log.error(e.getMessage(), e);
           flag = false;
       }
       return flag;
   }
   
   /**
    * 查询所有位置信息
    * @return 
    */
   public List<Weizhi> findAllweizhi(){
      return weizhiDao.findAll();
   }
   
   /**
    * 查询位置是否存在
    * @param id
    * @return 
    */
   public boolean isWeizhiexits(Long id){
       return weizhiDao.existsById(id);
   }
   
   /**
    * 根据编号查找位置
    * @param id
    * @return 
    */
   public Weizhi findWeizhi(Long id){
       
       return weizhiDao.findById(id).get();
   }
}
