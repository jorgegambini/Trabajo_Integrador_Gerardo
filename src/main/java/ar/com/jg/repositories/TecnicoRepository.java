package ar.com.jg.repositories;

import ar.com.jg.model.Tecnico;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

import javax.swing.*;
import java.util.List;


public class TecnicoRepository implements CrudRepository<Tecnico> {

    private EntityManager em;

    public TecnicoRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Tecnico> listar() {

        return em.createQuery("from Tecnico",Tecnico.class).getResultList();

    }

    @Override
    public Tecnico buscarPorId(Long id) {

        return em.find(Tecnico.class, id);

    }

    public Tecnico buscarPorLegajo(Long legajo) {

        try {

            return em.createQuery("from Tecnico t where t.legajo = :legajo", Tecnico.class).setParameter("legajo", legajo).getSingleResult();

        } catch (NoResultException e) {

            return null;

        }

    }

    public Tecnico login (String user, String pass) {

        try {

            return em.createQuery("from Tecnico t where t.usuario = :user and t.password = :pass", Tecnico.class).setParameter("user", user).setParameter("pass", pass).getSingleResult();

        }catch (NoResultException e){

            return null;

        }

    }

    @Override
    public void guardar(Tecnico tecnico) {

        if(tecnico.getId() != null && tecnico.getId() > 0){

            em.merge(tecnico);

        }else{

            em.persist(tecnico);

        }

    }

    @Override
    public void eliminar(Long id) {

        em.remove(buscarPorId(id));

    }

}
