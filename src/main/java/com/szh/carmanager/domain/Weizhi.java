/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * @autoor szh
 */
package com.szh.carmanager.domain;

import javax.persistence.*;
import lombok.*;

/**
 *
 * @author szh QQ:873689
 * @date 2018-7-21 18:03:43
 */
@Data
@Entity(name = "carmanager_weizhi")
public class Weizhi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column(unique = true)
    private String weizhi;
    
}
