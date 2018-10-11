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
    
    @Query(value = "select s.*  from (select *, row_number() over (partition by chepai order by createtime desc) as group_idx from carmanager_yunshuxinxi ) s where s.group_idx = 1 and s.is_del = 0",nativeQuery = true)
    public List<Yunshuxinxi> findByXinxiDingshi();
    
    @Query(value = "select s.daodadi,s.shifadi,s.zhuangtai,count(*)  from (select *, row_number() over (partition by chepai order by createtime desc) as group_idx from carmanager_yunshuxinxi ) s where s.group_idx = 1 and s.is_del = 0 group by s.daodadi,s.shifadi,s.zhuangtai",nativeQuery = true)
    public List<Object[]> findByXinxihuizong();
}
