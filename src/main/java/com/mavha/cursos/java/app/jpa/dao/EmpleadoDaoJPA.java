/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mavha.cursos.java.app.jpa.dao;

import com.mavha.cursos.java.app.jpa.modelo.Empleado;
import com.mavha.cursos.java.app.jpa.modelo.Tarea;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.stream.Collectors;

/**
 *
 * @author mdominguez
 */
public class EmpleadoDaoJPA implements EmpleadoDao{
    private double salarioPromedio;
    private EntityManager em;
    
    @Override
    public Empleado guardar(Empleado e) {
      //TODO 2.1 implementar el guardado
        em = ConexionJPA.getInstance().em();
        em.getTransaction().begin();
        try {
            em.persist(e);
            em.getTransaction().commit();
            return e;
        } catch (Exception error) {
            System.err.println("Error al persistir empleado");
        }
        em.close();
        return null;
    }

    @Override
    public void borrar(Integer e) {
      //TODO 2.2 implementar el guardado
        em = ConexionJPA.getInstance().em();
        em.getTransaction().begin();
        try {
            em.remove(e);
            em.getTransaction().commit();
        } catch (Exception error) {
            System.err.println("Error al eliminar empleado");
        }
        em.close();
    }
    
    @Override
    public Empleado actualizar(Empleado e) {
        em = ConexionJPA.getInstance().em();
        em.getTransaction().begin();
        Empleado empActualizado = em.merge(e);
        em.getTransaction().commit();
        em.close();        
        return empActualizado;
    }
    
    @Override
    public Empleado buscarPorId(Integer id) {
        em = ConexionJPA.getInstance().em();
        em.getTransaction().begin();
        Query query = em.createQuery("SELECT e FROM Empleado e LEFT JOIN FETCH e.participaDe WHERE e.id = :id");
        query.setParameter("id", id);
        try {
            Empleado e = (Empleado) query.getSingleResult();
            em.getTransaction().commit();
            em.close();
            return e;
        } catch (Exception e) {
            em.getTransaction().commit();
            em.close();
            return null;
        }
    }

    @Override
    public List<Empleado> buscarTodos() {
   String consulta ="SELECT e FROM Empleado e";
        em = ConexionJPA.getInstance().em();
        em.getTransaction().begin();
        Query query = em.createQuery(consulta);
        List<Empleado> resultado = query.getResultList();
        em.getTransaction().commit();
        em.close();
        return resultado;
    }
   
    @Override
    public Double salarioPromedioTodos(){
        ////TODO 2.3 Ejecutar una consulta para conocer el salario promedio de todos los empleados de una empresa
        String consulta ="SELECT AVG(salarioHora) FROM Empleado e";
        em = ConexionJPA.getInstance().em();
        em.getTransaction().begin();
        Query query = em.createQuery(consulta);
        Object avg = (double) query.getSingleResult();
        em.getTransaction().commit();
        em.close();
        if (avg == null) {
            return 0.0;
        } else {
            return (Double) avg;
        }
    }

    @Override
    public List<Tarea> tareasPendientes(Integer idEmpleado) {
        //TODO 2.4 ejecutar una consulta que retorne todas las tareas que tiene pendiente un Empleado. 
        // Las tareas pendientes son todas las tareas asignadas al empleado, que tiene fecha de fin NULL
        String consulta = "SELECT t FROM Tarea t WHERE t.";
        em = ConexionJPA.getInstance().em();
        em.getTransaction().begin();
        Query query = em.createQuery("SELECT   FROM Empleado e  WHERE e.tareasAsignadas.fechaFin IS NULL  "
                + " AND e.id = :idEmpleado  ");
        query.setParameter("id", idEmpleado);
        List<Tarea> resultado = query.getResultList();
        em.getTransaction().commit();
        em.close();

        return null;
    }

    
    
}
