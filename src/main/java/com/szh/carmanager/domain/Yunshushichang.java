/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * @autoor szh
 */

package com.szh.carmanager.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;

/**
 *
 * @author  szh
 * QQ:873689
 * @date 2018-7-27 9:25:05
 */
@Entity(name = "carmanager_shijianduiyingbiao")
@Data
public class Yunshushichang {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    
    @Column
    private String shifadi;
    @Column
    private String daodadi;
    @Column
    private long shichang;
    
}
