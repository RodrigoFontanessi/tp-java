/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mavha.cursos.java.app.jpa.logica;

import com.mavha.cursos.java.app.jpa.dao.EmpleadoDao;
import com.mavha.cursos.java.app.jpa.dao.EmpleadoDaoJPA;
import com.mavha.cursos.java.app.jpa.dao.ProyectoDao;
import com.mavha.cursos.java.app.jpa.dao.ProyectoDaoJPA;
import com.mavha.cursos.java.app.jpa.dao.TareaDao;
import com.mavha.cursos.java.app.jpa.dao.TareaDaoJPA;
import com.mavha.cursos.java.app.jpa.modelo.Empleado;
import com.mavha.cursos.java.app.jpa.modelo.Proyecto;
import com.mavha.cursos.java.app.jpa.modelo.Tarea;
import java.util.Date;

/**
 *
 * @author mdominguez
 */
public class ProyectoLogicDefaultImpl implements ProyectoLogic{

    private ProyectoDao proyectoDao;
    private EmpleadoDao empleadoDao;
    private TareaDao tareaDao;
    
    public ProyectoLogicDefaultImpl(){
        proyectoDao = new ProyectoDaoJPA();
        empleadoDao = new EmpleadoDaoJPA();
        tareaDao = new TareaDaoJPA();
    }
    
    @Override
    public Proyecto crearProyecto(String nombre, Double presupuesto, Integer idLider) {
        Empleado e = empleadoDao.buscarPorId(idLider);        
        Proyecto p = null;
        if(nombre!=null && nombre.length()>1 && nombre.length()<20 && presupuesto>0.0 && presupuesto <100000.0){
            p=new Proyecto();
            p.setNombre(nombre);
            p.setPresupuesto(presupuesto);
            p.setLider(e);
            p = proyectoDao.crear(p);
        }
        return p;        
    }
            
    @Override
    public Double presupuestoDisponible(Integer idProyecto) {
        
        // TODO 3.5 buscar el proyecto de la capa DAO
        Proyecto p =  proyectoDao.buscarPorId(idProyecto);
        // TODO 3.5 con una expresion lambda, procesar todas las tareas de un proyecto p 
        //          usando mapToDouble calcular cuanto cuesta cada tarea y usar el operador sum() para sumarizar
        
        Double gastado = p.getTarea().stream().mapToDouble(j -> j.getDuracionEstimada() *j.getResponsable().getSalarioHora()).sum();
        // TODO 3.5 el presupuesto disponible es la diferencia entre el presupuesto y lo gastado
        return p.getPresupuesto()-gastado;
    }
    
    @Override
    public Tarea asignarTarea(Integer idProyecto, Integer idResponsable,String descripcion,Integer duracionEstimada) {
        Tarea t =null;
        // TODO 3.6 si el presupuesto disponible menos el costo de la tarea al responsable propuesto es mayor que cero
        // entonces se puede asignar la tarea al proyecto.
        Empleado empleado = empleadoDao.buscarPorId(idResponsable);
        Proyecto proyecto  = proyectoDao.buscarPorId(idProyecto);
        
        Tarea tarea =  new Tarea();
        tarea.setDescripcion( descripcion);
        tarea.setDuracionEstimada(duracionEstimada);
        tarea.setFechaInicio(new Date());
        // Buscar una instancia de empleado por ID
        // Buscar una instancia de Proyecto por ID
        // Crear una tarea
        // Setear todos los atributos 
        // Invocar a TareaDAO para que realice la creación de la tarea        
        tarea.setProyecto(proyecto);
        tarea.setResponsable(empleado);
        
        tareaDao.crear(tarea);
        return tarea;
    } 
}