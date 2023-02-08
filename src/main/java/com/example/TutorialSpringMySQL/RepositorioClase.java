/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.TutorialSpringMySQL;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author capea
 */
public interface RepositorioClase extends JpaRepository<Clase, String>{
    Clase findOneByProfesor(Persona p);
    
    Clase findOneByProfesorId(String id);
    
    
}
