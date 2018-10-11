/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * @autoor szh
 */

package com.szh.carmanager.domain;

import java.util.Date;
import javax.persistence.*;
import lombok.Data;

/**
 *
 * @author  szh
 * QQ:873689
 * @date 2018-7-21 17:47:36
 */
@Entity(name = "carmanager_yunshuxinxi")
@Data
public class Yunshuxinxi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column
    private String chepai;
    @Column
    private String dianhua;
    /**
     * 
     */
    @Column
    private String zhuangtai;
    @Column
    private String shifadi;
    @Column
    private String daodadi;
    @Column
    private Date createtime;
    @Column
    String username;
    @Column
    Boolean is_del=false;
    
}
