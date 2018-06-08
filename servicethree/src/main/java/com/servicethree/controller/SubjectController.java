/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicethree.controller;

import com.servicethree.entity.request.SubjectRequest;
import com.servicethree.entity.request.UserRequest;
import com.servicethree.entitys.Subject;
import com.servicethree.repository.SubjectRepository;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author Yannick van Leeuwen
 */
@RestController
@RequestMapping("subjects")
public class SubjectController {
    
    private SubjectRepository subjectRepository;
    private SubjectRequest subjectRequest; 
    private UserRequest userRequest;
    
    @Autowired
    public SubjectController(SubjectRepository subjectRepository){
        this.subjectRepository = subjectRepository;
        subjectRequest = new SubjectRequest();
        userRequest = new UserRequest();
    }
    
    // TEST URL: http://localhost:8094/subjects
    @RequestMapping(method = RequestMethod.GET)
    public List<Subject> findAllSubjects() {
        return subjectRepository.findAll();
    }
    
    // TEST URL: http://localhost:8094/subjects/getAll
    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public List<Subject> getAllFromOne() {
        return subjectRequest.getAllFromTwo();
    }
    
    // TEST URL: http://localhost:8094/subjects/getAllByName?name=Yannick
    @RequestMapping(value = "/getAllByName", method = RequestMethod.GET)
    public List<Subject> getAllFromOneByName(@RequestParam("naam") String naam) {
        return userRequest.getAllSubjectsPerUserFromTwo(naam);
    }
    
    // TEST URL: http://localhost:8094/subjects/refresh
    @RequestMapping(value = "/refresh", method = RequestMethod.GET)
    public List<Subject> refreshSubjects() {
        List<Subject> getAll = subjectRequest.getAllFromTwo();
        for (Subject s : getAll) {
                Subject toSave = subjectRepository.findOne(subjectRepository.findSingleSubjectByNaam(s.getNaam()).getId());
                if(toSave == null)
                {
                    Subject sub = new Subject(s.getNaam(), s.getOmschrijving(), s.getAgeLimit());
                    subjectRepository.save(sub);
                }
                else
                    subjectRepository.save(toSave);
            }
        return subjectRepository.findAll();
    }
}
