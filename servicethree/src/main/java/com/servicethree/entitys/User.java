/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicethree.entitys;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
/**
 *
 * @author Yannick van Leeuwen
 */

@Entity
@Table(name = "app_user")
public class User implements Serializable {

    @Id
    @GeneratedValue
    private long id;
    
    @NotNull
    private String name;
    
    @NotNull
    private String mail;
    
    @NotNull
    private String password;
    
    @NotNull
    private int age;
    
    @ManyToMany
    private List<Subject> followingSubjects;
    
    @ManyToMany
    private List<Aanbeveling> aanbevelingen;
    
    public User()
    {
        followingSubjects = new ArrayList<Subject>();
        aanbevelingen = new ArrayList<Aanbeveling>();
    }
    
    public User(String name, String mail, String password, int age)
    {
        this.name = name;
        this.mail = mail;
        this.password = password;
        this.age = age;
        followingSubjects = new ArrayList<Subject>();
        aanbevelingen = new ArrayList<Aanbeveling>();
    }
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<Subject> getFollowingSubjects() {
        return followingSubjects;
    }

    public void setFollowingSubjects(List<Subject> followingSubjects) {
        this.followingSubjects = followingSubjects;
    }

    public List<Aanbeveling> getAanbevelingen() {
        List<Aanbeveling> tempUsers = new ArrayList<>();
        for(Aanbeveling a:aanbevelingen)
        {
            Aanbeveling aa = new Aanbeveling(a.getTo(), a.getFrom(), a.getSubject(), a.getWaarom());
            tempUsers.add(aa);
        }
        return tempUsers;
    }

    public void setAanbevelingen(List<Aanbeveling> aanbevelingen) {
        this.aanbevelingen = aanbevelingen;
    }

    public List<Subject> addNews(Subject n) {
        List<Subject> returnList = getFollowingSubjects();
        returnList.add(n);
        setFollowingSubjects(returnList);
        return returnList;
    }
    
    public List<Aanbeveling> addAanbeveling(){
        List<Aanbeveling> returnList = new ArrayList<>();
        for(Aanbeveling a: getAanbevelingen())
        {
            if(!getAanbevelingen().contains(a))
            {
                returnList.add(a);
                setAanbevelingen(returnList);
            }
        }
        return returnList;
    }

    public List<Subject> addSubject(Subject n) {
        List<Subject> returnList = getFollowingSubjects();
        if(! returnList.contains(n) && getAge() >= n.getAgeLimit())
        {
            returnList.add(n);
        }
        setFollowingSubjects(returnList);
        return returnList;
    }
    
}
