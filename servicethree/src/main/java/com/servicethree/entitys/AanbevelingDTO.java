/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicethree.entitys;

/**
 *
 * @author Yannick van Leeuwen
 */
public class AanbevelingDTO {
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
}
