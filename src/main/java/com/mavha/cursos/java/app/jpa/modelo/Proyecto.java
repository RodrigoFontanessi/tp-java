/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mavha.cursos.java.app.jpa.modelo;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author mdominguez
 */
@Entity
public class Proyecto implements Serializable{
    
    @ManyToMany
    private List<Empleado> empleados;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;    private String nombre;
    private Double presupuesto;
    //TODO 1.9 mapear relacion con empleado
    
    @ManyToOne
    private Empleado lider;
    //TODO 1.10 mapear relacion con tarea
    @OneToMany(mappedBy = "proyecto" ,fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Tarea> tarea;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getPresupuesto() {
        return presupuesto;
    }

    public void setPresupuesto(Double presupuesto) {
        this.presupuesto = presupuesto;
    }
    
    
    @ManyToMany(fetch=FetchType.LAZY)
    public Empleado getLider() {
        return lider;
    }

    public void setLider(Empleado lider) {
        this.lider = lider;
    }

    public List<Tarea> getTarea() {
        return tarea;
    }

    public void setTarea(List<Tarea> tarea) {
        this.tarea = tarea;
    }

    @Override
    public String toString() {
        return "Proyecto{" + "id=" + id + ", nombre=" + nombre + ", presupuesto=" + presupuesto + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Proyecto other = (Proyecto) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    
    
}
