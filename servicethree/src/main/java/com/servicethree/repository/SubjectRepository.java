/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicethree.repository;

import com.servicethree.entitys.Subject;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Yannick van Leeuwen
 */
public interface SubjectRepository extends JpaRepository<Subject, Long>{
    
    @Query("SELECT s FROM Subject s WHERE LOWER(s.naam) = LOWER(:naam)")
    public List<Subject> findSubjectByNaam(@Param("naam") String naam);
    
    @Query("SELECT s FROM Subject s WHERE LOWER(s.naam) = LOWER(:naam)")
    public Subject findSingleSubjectByNaam(@Param("naam") String naam);
    
}
