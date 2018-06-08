/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicethree;

import com.servicethree.entity.request.SubjectRequest;
import com.servicethree.entity.request.UserRequest;
import com.servicethree.entitys.Subject;
import com.servicethree.entitys.User;
import com.servicethree.repository.SubjectRepository;
import com.servicethree.repository.UserRepository;
import com.sun.javafx.scene.control.skin.VirtualFlow;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 *
 * @author yanni
 */
@Component
public class Seeder implements ApplicationRunner{
    
    private UserRepository userRepository;
    private UserRequest userRequest;
    private SubjectRepository subjectRepository;
    private SubjectRequest subjectRequest;
    
    @Autowired
    public Seeder(UserRepository userRepository, SubjectRepository subjectRepository)
    {
        this.userRepository = userRepository;
        userRequest = new UserRequest();
        this.subjectRepository = subjectRepository;
        subjectRequest = new SubjectRequest();
    }

    @Override
    public void run(ApplicationArguments aa) {
        
      for(User u: userRequest.getAllFromTwo())
        {
            User toPersist = new User(u.getName(), u.getMail(), u.getPassword(), u.getAge());
            for(Subject s: userRequest.getAllSubjectsPerUserFromTwo(u.getName()))
            {
                List<Subject> toAdd = new ArrayList<>();
                
                toAdd.add(s);
                toPersist.setFollowingSubjects(toAdd);
                //Subject sub = subjectRepository.findOne(subjectRepository.findSingleSubjectByNaam(s.getNaam()).getId());
                subjectRepository.save(s);
            }
            
            userRepository.save(toPersist);
        }
    }
    
}
