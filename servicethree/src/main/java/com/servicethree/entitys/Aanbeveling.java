/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicethree.entitys;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Yannick van Leeuwen
 */
@Entity
@Table(name = "app_aanbeveling")
public class Aanbeveling implements Serializable {
    @Id
    @GeneratedValue
    private long id;
    
    @NotNull
    @OneToOne
    private User to;
    
    @NotNull
    @OneToOne
    private User from;
    
    @NotNull
    @OneToOne
    private Subject subject;
    
    @NotNull
    private String waarom;
    
    @ManyToMany
    private List<User> aanbevolenAan;
    
    public Aanbeveling()
    {
        aanbevolenAan = new ArrayList<>();
    }
    
    public Aanbeveling(User to, User from, Subject subject, String waarom)
    {
        aanbevolenAan = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getTo() {
        return to;
    }

    public void setTo(User to) {
        this.to = to;
    }

    public User getFrom() {
        return from;
    }

    public void setFrom(User from) {
        this.from = from;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public String getWaarom() {
        return waarom;
    }

    public void setWaarom(String waarom) {
        this.waarom = waarom;
    }

    public List<User> getAanbevolenAan() {
        return aanbevolenAan;
    }

    public void setAanbevolenAan(List<User> aanbevolenAan) {
        this.aanbevolenAan = aanbevolenAan;
    }
}
