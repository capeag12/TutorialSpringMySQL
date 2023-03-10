/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.TutorialSpringMySQL;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
@CrossOrigin(origins = "http://localhost:8080") //Sirve para habilitar el intercambio de recursos de origen cruzado
@RestController //Se usa para definir un controlador 
@RequestMapping("/clases")
public class ControllerClase {
    @Autowired
    RepositorioClase repositorio;
    
    @Autowired
    RepositorioPersona repositorioPersona;
    
    @GetMapping("/devolverClases")
    public ResponseEntity<List<Clase>> getListaClases(){
        try {
            List<Clase> listaClases = repositorio.findAll();
            
            if(listaClases.isEmpty()){
                return new ResponseEntity(HttpStatus.NO_CONTENT);
            }
            
            return ResponseEntity.ok().body(listaClases);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
            
        }
       
    }
    
    @PostMapping("/addClase")
    public ResponseEntity<Clase> addClase(@RequestBody Clase c){
        System.out.println(c);
        try {
            repositorioPersona.save(c.getProfesor());
            repositorio.save(c);
            
            return ResponseEntity.ok().body(c);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
         
    }
    
    
    @GetMapping("/getProfesor")
    public ResponseEntity<Persona> getProfesor(@RequestParam(name = "id", required = true) String id){ 
        Optional<Clase> claseFind = repositorio.findById(id);
        
        if(claseFind.isPresent()==true){
            Clase c = claseFind.get();
            
            return ResponseEntity.ok().body(c.getProfesor());
        }
        else{
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
    
    }
    
    @GetMapping("/getClasebyProfesorName/{nombre}")
    public ResponseEntity<Clase> getClaseByNombreProfesor(@PathVariable("nombre") String nombre){
        Persona p= repositorioPersona.findOneByNombre(nombre);
        if(p!=null){
            Clase claseFind = repositorio.findOneByProfesor(p);
        
            if (claseFind!=null) {
                return ResponseEntity.ok().body(claseFind);
            }
            else return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        else return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
    
    @DeleteMapping("/deleteClaseByIdProfesor/{id}")
    public ResponseEntity<Clase> removeClaseByNombreProfesor(@PathVariable("id") String id){
        Clase c = repositorio.findOneByProfesorId(id);
        if(c!=null){
            Persona p = c.getProfesor();
        
            repositorio.delete(c);
            repositorioPersona.delete(p);
            return ResponseEntity.ok().body(c);
            
        } else{
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
    
    @PutMapping("/editarProfesorClase/{id}")
    public ResponseEntity<Persona> editarProfesorClase(@PathVariable("id") String id, @RequestBody Persona p){
        Optional<Clase> claseBuscada = repositorio.findById(id);
        
        if(claseBuscada.isPresent()){
            Clase cEncontrada = claseBuscada.get();
            Persona personaClase = cEncontrada.getProfesor();
            
            if(p.getNombre()!=null){
                personaClase.setNombre(p.getNombre());
            }
            if(p.getApellido()!=null){
                personaClase.setApellido(p.getApellido());
            }
            if(p.getCurso()!=null){
                personaClase.setCurso(p.getCurso());
            }
            if(p.getNacionalidad()!=null){
                personaClase.setNacionalidad(p.getNacionalidad());
            }
            repositorioPersona.save(personaClase);
            return ResponseEntity.ok().body(personaClase);
            
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }}
}

       
    
