/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicethree.repository;

import com.servicethree.entitys.Aanbeveling;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Yannick van Leeuwen
 */
public interface AanbevelingRepository extends JpaRepository<Aanbeveling, Long> {
    
    @Query("SELECT a FROM Aanbeveling a WHERE LOWER(a.to.name) = LOWER(:name)")
    public Aanbeveling findAanbevelingByNaam(@Param("name") String naam);
}
