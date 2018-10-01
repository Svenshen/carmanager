/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * @autoor szh
 */

package com.szh.carmanager.dao;

import com.szh.carmanager.domain.Weizhi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author  szh
 * QQ:873689
 * @date 2018-7-23 14:00:15
 */
@Repository
public interface WeizhiDao extends JpaRepository<Weizhi,Long>{

}
