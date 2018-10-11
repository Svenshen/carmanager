/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * @autoor szh
 */

package com.szh.carmanager.dao;

import com.szh.carmanager.domain.Yunshushichang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author  szh
 * QQ:873689
 * @date 2018-10-8 16:13:13
 */
@Repository
public interface YunshushichangDao extends JpaRepository<Yunshushichang,Long>{

    public Yunshushichang findByShifadiAndDaodadi(String Shifadi,String daodadi);
    
}
