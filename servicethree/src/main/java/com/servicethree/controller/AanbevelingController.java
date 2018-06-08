/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicethree.controller;

import com.servicethree.entitys.Aanbeveling;
import com.servicethree.entitys.AanbevelingDTO;
import com.servicethree.entitys.Subject;
import com.servicethree.entitys.User;
import com.servicethree.repository.AanbevelingRepository;
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
@RequestMapping("aanbevelingen")
public class AanbevelingController {
    
    private UserRepository userRepository;
    private AanbevelingRepository aanbevelingRepository;
    private SubjectRepository subjectRepository;
    
    @Autowired
    public AanbevelingController(UserRepository userRepository, AanbevelingRepository aanbevelingRepository, SubjectRepository subjectRepository){
        this.userRepository = userRepository;
        this.aanbevelingRepository = aanbevelingRepository;
        this.subjectRepository = subjectRepository;
    }
    
    // TEST URL: http://localhost:8094/aanbevelingen
    @RequestMapping(method = RequestMethod.GET)
    public List<Aanbeveling> findAllSubjects() {
        return aanbevelingRepository.findAll();
    }
    
    //TEST URL: http://localhost:8094/aanbevelingen/create?toName=Yannick&fromName=Dennis&subjectName=Een&waarom=omdat ik je lief vindt
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Aanbeveling createAanbeveling(@RequestParam("toName") String toName, @RequestParam("fromName") String fromName, @RequestParam("subjectName") String subjectName, @RequestParam("waarom") String waarom) {
        User to = userRepository.findByName(toName);
        User from = userRepository.findByName(fromName);
        Subject s = subjectRepository.findSingleSubjectByNaam(subjectName);
        Aanbeveling a = new Aanbeveling();
        a.setTo(to);
        a.setFrom(from);
        a.setSubject(s);
        a.setWaarom(waarom);
        System.out.println(a);
        aanbevelingRepository.save(a);
        String ss = "Debug";
        
        return a;
    }
    
    // TEST URL: http://localhost:8094/aanbevelingen/getByName?name=Yannick
    @RequestMapping(value = "/getByName", method = RequestMethod.GET)
    public List<AanbevelingDTO> getByName(@RequestParam("naam") String naam) {
        List<AanbevelingDTO> dto = new ArrayList<>();
        User u = userRepository.findByName(naam);
        for(Aanbeveling a: u.getAanbevelingen())
        {
            AanbevelingDTO adto = new AanbevelingDTO(a);
            dto.add(adto);
        }
        return dto;
    }
}
