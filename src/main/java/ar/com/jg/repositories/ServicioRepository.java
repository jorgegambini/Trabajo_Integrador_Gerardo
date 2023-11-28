package ar.com.jg.repositories;

import ar.com.jg.model.Servicio;
import jakarta.persistence.EntityManager;
import javax.swing.*;
import java.util.List;


public class ServicioRepository implements CrudRepository<Servicio>{

    private EntityManager em;

    public ServicioRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Servicio> listar() {

        return em.createQuery("from Servicio",Servicio.class).getResultList();

    }

    @Override
    public Servicio buscarPorId(Long id) {

        return em.find(Servicio.class, id);

    }

    @Override
    public void guardar(Servicio servicio) {

        if(servicio.getId() != null && servicio.getId() > 0){

            em.merge(servicio);

        }else{

            em.persist(servicio);

        }

    }

    @Override
    public void eliminar(Long id) {

        em.remove(buscarPorId(id));

    }

}
