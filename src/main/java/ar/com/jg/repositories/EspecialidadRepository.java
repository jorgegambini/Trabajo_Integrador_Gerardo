package ar.com.jg.repositories;

import ar.com.jg.model.Especialidad;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import javax.swing.*;
import java.util.List;


public class EspecialidadRepository implements CrudRepository<Especialidad>{

    private EntityManager em;

    public EspecialidadRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Especialidad> listar() {

        return em.createQuery("from Especialidad",Especialidad.class).getResultList();

    }

    public List<Especialidad> listarSinServicio() {

        return em.createQuery("from Especialidad e where e.servicio = null",Especialidad.class).getResultList();

    }

    @Override
    public Especialidad buscarPorId(Long id) {

        return em.find(Especialidad.class, id);

    }

    public Especialidad buscarPorDenominacion(String denominacion) {

        try {

            return em.createQuery("from Especialidad e where e.denominacion like :denominacion", Especialidad.class).setParameter("denominacion", denominacion).getSingleResult();

        }catch (NoResultException e){

            return null;

        }

    }

    @Override
    public void guardar(Especialidad especialidad) {

        if(especialidad.getId() != null && especialidad.getId() > 0){

            em.merge(especialidad);

            JOptionPane.showMessageDialog(null, "La Especialidad se ha modificado correctamente.");

        }else{

            em.persist(especialidad);

            JOptionPane.showMessageDialog(null, "La Especialidad se ha ingresado correctamente.");

        }

    }

    @Override
    public void eliminar(Long id) {

        em.remove(buscarPorId(id));

    }

}

