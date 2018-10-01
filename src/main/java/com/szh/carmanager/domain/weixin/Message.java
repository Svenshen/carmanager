/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * @autoor szh
 */

package com.szh.carmanager.domain.weixin;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author  szh
 * QQ:873689
 * @date 2018-7-19 10:11:34
 */
@Component
@Scope("prototype")
@Data
public class Message {
    private String msg_signature;
    private String timestamp;
    private String nonce;
    private String echostr;
}
