/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.TutorialSpringMySQL;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

/**
 *
 * @author capea
 */
@Entity
@Table(name = "clases")
public class Clase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nombre;
    
    @OneToOne
    @JoinColumn(name = "idProfesor", referencedColumnName = "id")
    private Persona profesor;

    public Clase(String nombre, Persona profesor) {
        this.nombre = nombre;
        this.profesor = profesor;
    }

    public Clase() {
    }
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Persona getProfesor() {
        return profesor;
    }

    public void setProfesor(Persona profesor) {
        this.profesor = profesor;
    }

    @Override
    public String toString() {
        return "Clase{" + "id=" + id + ", nombre=" + nombre + ", profesor=" + profesor + '}';
    }
    
}
