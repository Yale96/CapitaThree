/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicethree.controller;

import com.servicethree.entity.request.UserRequest;
import com.servicethree.entitys.Subject;
import com.servicethree.entitys.User;
import com.servicethree.repository.SubjectRepository;
import com.servicethree.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Yannick van Leeuwen
 */
@RestController
@RequestMapping("users")
public class UserController {

    private UserRepository userRepository;
    private SubjectRepository subjectRepository;
    private UserRequest userRequest;

    @Autowired
    public UserController(UserRepository userRepository, SubjectRepository subjectRepository) {
        this.userRepository = userRepository;
        this.subjectRepository = subjectRepository;
        userRequest = new UserRequest();
    }

    // TEST URL: http://localhost:8094/users
    @RequestMapping(method = RequestMethod.GET)
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    // TEST URL: http://localhost:8094/users/getAllByName?naam=Yannick
    @RequestMapping(value = "/getAllByName", method = RequestMethod.GET)
    public List<String> getAllFromOneByName(@RequestParam("naam") String naam) {
        List<String> returnList = new ArrayList<>();
        for(Subject s: userRequest.getAllSubjectsPerUserFromTwo(naam))
        {
            returnList.add(s.getNaam());
        }
        return returnList;
    }

//     TEST URL: http://localhost:8094/users/refresh
    @RequestMapping(value = "/refresh", method = RequestMethod.GET)
    public User refreshFollowingSubjects(@RequestParam("naam") String naam) {
        User u = userRepository.findOne(userRepository.findByName(naam).getId());
        List<Subject> getAllPerUser = userRequest.getAllSubjectsPerUserFromTwo(naam);
        List<Subject> savedObjects = new ArrayList<>();
        for (Subject s : getAllPerUser) {
            for (Subject subject : subjectRepository.findSubjectByNaam(s.getNaam())) {
                Subject toSave = subjectRepository.findOne(subject.getId());
                subjectRepository.save(toSave);
                savedObjects.add(toSave);
            }
        }
        u.setFollowingSubjects(savedObjects);
        userRepository.save(u);
        return u;
    }
    
//    public User recommendSubject(String from, String subjectName, String to)
//    {
//        User u = userRepository.getOne(userRepository.findByName(to).getId());
//        for(Subject s: userRequest.getAllSubjectsPerUserFromTwo(u.getName()))
//            {
//                List<Subject> toAdd = new ArrayList<>();
//                toAdd.add(s);
//                u.setFollowingSubjects(toAdd);
//                subjectRepository.save(s);
//            }
//        return u;
//    }
}
