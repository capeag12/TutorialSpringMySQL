/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.TutorialSpringMySQL;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author capea
 */
@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/personas")
public class ControllerPersona {
    @Autowired
    RepositorioPersona repPersona;
    
    @PostMapping("/addPersona")
    public ResponseEntity<Persona> addPersona(@RequestBody Persona p){
        try {
            repPersona.save(p);
            return ResponseEntity.ok().body(p);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    
     //Buscar todas las personas
    @GetMapping("/getPersonas")
    public ResponseEntity<List<Persona>> obtenerPersonas(){
        try {
            List<Persona> personas = new ArrayList<Persona>();
            repPersona.findAll().forEach(personas::add);
            if (personas.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            
            return ResponseEntity.ok().body(personas);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
            
    }
    //Buscar por apellido
    @GetMapping("/getPersonasApellido/{apellido}")
    public ResponseEntity<List<Persona>> obtenerPersonasApellido(@PathVariable("apellido") String apellido){ //Parametros en la ruta ej: http://localhost:8080/personas/getPersonasApellido/Apellido1
        try{
        List<Persona> listaPersonas = repPersona.findByApellido(apellido);
        
        if(listaPersonas.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        
        return ResponseEntity.ok().body(listaPersonas);
        }
        catch(Exception e){
            return new ResponseEntity(null,HttpStatus.BAD_GATEWAY);
        }
       
    }
    
    //Buscar por nombre usando parametros en la url
    @GetMapping("/getPersonaNombre")
    public ResponseEntity<List<Persona>> obtenerPersonasNombre(@RequestParam(name = "nombre", required = true) String nombre){ //Parametros de url ej: http://localhost:8080/personas/getPersonaNombre?nombre=Nombre
        List<Persona> personas = null;
        personas = repPersona.findByNombreLike(nombre);
        
        if(personas.isEmpty()){
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok().body(personas);
       
    }
    @PutMapping("/actualizarPersona/{id}")
    public ResponseEntity<Persona> actualizarPersona(@PathVariable("id") int id, @RequestBody Persona p){
        Optional<Persona> personaBuscada = repPersona.findById(id);
        
        if(personaBuscada.isPresent()){
            Persona pEditada = personaBuscada.get();
            if(p.getNombre()!=null){
                pEditada.setNombre(p.getNombre());
            }
            if(p.getApellido()!=null){
                pEditada.setApellido(p.getApellido());
            }
            if(p.getCurso()!=null){
                pEditada.setCurso(p.getCurso());
            }
            if(p.getNacionalidad()!=null){
                pEditada.setNacionalidad(p.getNacionalidad());
            }
            repPersona.save(pEditada);
            return ResponseEntity.ok().body(pEditada);
            
        }
        
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Persona> eliminarPersona(@PathVariable("id") int id){
        Optional<Persona> personaEncontrada = repPersona.findById(id);
        
        if (personaEncontrada.isPresent()==true) {
            Persona persona = personaEncontrada.get();
            
            repPersona.deleteById(id);
            return ResponseEntity.ok().body(persona);
        }
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    
}
