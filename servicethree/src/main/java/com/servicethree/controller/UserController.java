/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicethree.controller;

import com.servicethree.entity.request.UserRequest;
import com.servicethree.entitys.Aanbeveling;
import com.servicethree.entitys.Subject;
import com.servicethree.entitys.User;
import com.servicethree.repository.AanbevelingRepository;
import com.servicethree.repository.SubjectRepository;
import com.servicethree.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Yannick van Leeuwen
 */
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("users")
public class UserController {

    private UserRepository userRepository;
    private SubjectRepository subjectRepository;
    private UserRequest userRequest;
    private AanbevelingRepository aanbevelingRepository;

    @Autowired
    public UserController(UserRepository userRepository, SubjectRepository subjectRepository, AanbevelingRepository aanbevelingRepository) {
        this.userRepository = userRepository;
        this.subjectRepository = subjectRepository;
        userRequest = new UserRequest();
        this.aanbevelingRepository = aanbevelingRepository;
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
    
    // TEST URL: http://localhost:8094/users/getAllSubsByName?naam=Yannick
    @RequestMapping(value = "/getAllSubsByName", method = RequestMethod.GET)
    public List<Subject> getAllSubsByName(@RequestParam("naam") String naam) {
        //refreshAanbevelingen(naam);
        List<Subject> returnList = new ArrayList<>();
        for(Subject s: userRepository.findSubsByName(naam))
        {
            returnList.add(s);
        }
        return returnList;
    }

////     TEST URL: http://localhost:8094/users/refresh
//    @RequestMapping(value = "/refresh", method = RequestMethod.GET)
//    public User refreshFollowingSubjects(@RequestParam("naam") String naam) {
//        User u = userRepository.findOne(userRepository.findByName(naam).getId());
//        List<Subject> getAllPerUser = userRequest.getAllSubjectsPerUserFromTwo(naam);
//        List<Subject> savedObjects = new ArrayList<>();
//        for (Subject s : getAllPerUser) {
//            for (Subject subject : subjectRepository.findSubjectByNaam(s.getNaam())) {
//                Subject toSave = subjectRepository.findOne(subject.getId());
//                subjectRepository.save(toSave);
//                savedObjects.add(toSave);
//            }
//        }
//        u.setFollowingSubjects(savedObjects);
//        userRepository.save(u);
//        return u;
//    }
    
    // TEST URL: http://localhost:8094/users/refresh
    @RequestMapping(value = "/refresh", method = RequestMethod.GET)
    public User refreshAanbevelingen(@RequestParam("naam") String naam)
    {
         List<Aanbeveling> toAdd = new ArrayList<>();
         User u = userRepository.findOne(userRepository.findByName(naam).getId());
         List<Aanbeveling> aanbevelingen = aanbevelingRepository.findAanbevelingenByNaam(naam);
         for(Aanbeveling a: aanbevelingen)
         {
             Aanbeveling addToList = new Aanbeveling();
             addToList.setTo(a.getTo());
             addToList.setFrom(a.getFrom());
             addToList.setSubject(a.getSubject());
             addToList.setWaarom(a.getWaarom());
             aanbevelingRepository.save(addToList);
             String s = "Debug";
             toAdd.add(addToList);
         }
         for(Aanbeveling a: toAdd)
         {
             aanbevelingRepository.save(a);
             u.getAanbevelingen().add(a);
         }
         u.setAanbevelingen(aanbevelingen);
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
    
    //TEST URL: http://localhost:8094/users/addSubjectToUser?name=Yannick&subject=Twee
    @RequestMapping(value = "/addSubjectToUser", method = RequestMethod.POST)
    public User subscribeToNews(@RequestParam("name") String name, @RequestParam("subject") String subject) {
        User u = userRepository.findOne(userRepository.findByName(name).getId());
        Subject s = subjectRepository.findOne(subjectRepository.findByName(subject).getId());
        u.addSubject(s);
        userRepository.save(u);
        String ss = "Debug";
        return u;
    }
}
