/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * @autoor szh
 */

package com.szh.carmanager.dao;

import com.szh.carmanager.domain.Weizhi;
import com.szh.carmanager.domain.Yunshuxinxi;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author  szh
 * QQ:873689
 * @date 2018-7-25 13:39:25
 */
@Repository
public interface YunshuxinxiDao extends JpaRepository<Yunshuxinxi,Long>{

    
    public List<Yunshuxinxi> findByChepaiOrderByCreatetimeDesc(String chepai);
    
    public List<Yunshuxinxi> findByChepaiOrderByCreatetime(String chepai);
}
