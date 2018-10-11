/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * @autoor szh
 */

package com.szh.carmanager.domain;

import javax.persistence.*;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author  szh
 * QQ:873689
 * @date 2018-7-20 15:25:58
 */
@Data

@Entity(name = "carmanager_cheliang")
public class Cheliang {
    @Id
    @Column
    private String chepai;
    @Column(nullable = false)
    private String dianhua;
    @Column
    String username;
    
    
}
