/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicethree.entitys;

import java.io.Serializable;

/**
 *
 * @author Yannick van Leeuwen
 */
public class AanbevelingDTO implements Serializable{
    private String to;
    
    private String from;
    
    private String subject;
    
    private String waarom;
    
    public AanbevelingDTO(Aanbeveling a)
    {
        this.to = a.getTo().getName();
        this.from = a.getFrom().getName();
        this.subject = a.getSubject().getNaam();
        this.waarom = a.getWaarom();
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getWaarom() {
        return waarom;
    }

    public void setWaarom(String waarom) {
        this.waarom = waarom;
    }
    
    
}
