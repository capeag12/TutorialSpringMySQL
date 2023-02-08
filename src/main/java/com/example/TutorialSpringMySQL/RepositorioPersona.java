/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.TutorialSpringMySQL;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Dam
 */
@Repository
public interface RepositorioPersona extends JpaRepository<Persona,Integer>{
    Persona findOneByNombre(String nombre);
    List<Persona> findByNombreLike(String nombre);
    List<Persona> findByApellido(String apellido);
}
