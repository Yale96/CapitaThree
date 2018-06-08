/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicethree.entity.request;

import com.servicethree.entitys.Subject;
import com.servicethree.entitys.User;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author Yannick van Leeuwen
 */
public class UserRequest {

    @Autowired
    public UserRequest() {

    }

    public List<User> getAllFromTwo() {
        RestTemplate restTemplate = new RestTemplate();
        List<LinkedHashMap<String, Object>> subjectsMap = restTemplate.getForObject("http://localhost:8092/users", List.class);

        List<User> returnList = new ArrayList<>();

        if (subjectsMap != null) {
            for (LinkedHashMap<String, Object> map : subjectsMap) {
                System.out.println("User id : id=" + map.get("id"));
                String name = map.get("name").toString();
                String mail = map.get("mail").toString();
                String password = map.get("password").toString();
                int age = (int) map.get("age");
                User u = new User(name, mail, password, age);
                returnList.add(u);
            }
        } else {
            System.out.println("No user exist----------");
        }
        return returnList;
    }

    public List<Subject> getAllSubjectsPerUserFromTwo(String name) {
        RestTemplate restTemplate = new RestTemplate();
        List<LinkedHashMap<String, Object>> subjectsMap = restTemplate.getForObject("http://localhost:8092/subjects/getAllByName?naam=" + name, List.class);

        List<Subject> returnList = new ArrayList<>();

        if (subjectsMap != null) {
            for (LinkedHashMap<String, Object> map : subjectsMap) {
                System.out.println("Subject : naam=" + map.get("naam"));
                String omschrijving = map.get("omschrijving").toString();
                String naam = map.get("naam").toString();
                int ageLimit = (int) map.get("ageLimit");
                Subject s = new Subject(naam, omschrijving, ageLimit);
                returnList.add(s);
            }
        } else {
            System.out.println("No user exist----------");
        }
        return returnList;
    }
}
