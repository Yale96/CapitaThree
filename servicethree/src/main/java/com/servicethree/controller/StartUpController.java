/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicethree.controller;

import com.servicethree.entity.request.SubjectRequest;
import com.servicethree.entity.request.UserRequest;
import com.servicethree.entitys.Subject;
import com.servicethree.entitys.User;
import com.servicethree.repository.SubjectRepository;
import com.servicethree.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Yannick van Leeuwen
 */
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("startup")
public class StartUpController {
    private UserRepository userRepository;
    private UserRequest userRequest;
    private SubjectRepository subjectRepository;
    private SubjectRequest subjectRequest;
    
    @Autowired
    public StartUpController(UserRepository userRepository, SubjectRepository subjectRepository)
    {
        this.userRepository = userRepository;
        userRequest = new UserRequest();
        this.subjectRepository = subjectRepository;
        subjectRequest = new SubjectRequest();
    }
    
    // TEST URL: http://localhost:8094/startup
    @RequestMapping(method = RequestMethod.GET)
    public void initialize(ApplicationArguments aa) {
      
        for(Subject s: subjectRequest.getAllFromTwo())
        {
            Subject toPersist = new Subject(s.getNaam(), s.getOmschrijving(), s.getAgeLimit());
            subjectRepository.save(toPersist);
            String ss = "Debug";
        }
        
      for(User u: userRequest.getAllFromTwo())
        {
            User toPersist = new User(u.getName(), u.getMail(), u.getPassword(), u.getAge());
            for(Subject s: userRequest.getAllSubjectsPerUserFromTwo(u.getName()))
            {
                List<Subject> toAdd = new ArrayList<>();
                Subject addHere = subjectRepository.findSingleSubjectByNaam(s.getNaam());
                toAdd.add(addHere);
                toPersist.setFollowingSubjects(toAdd);
//                if(subjectRepository.findSingleSubjectByNaam(s.getNaam()) == null)
//                {
//                    subjectRepository.save(s);
//                }
//                else
//                    subjectRepository.save(s);
            }
            userRepository.save(toPersist);
        }
    }
}
