package ar.com.jg.services;

import ar.com.jg.model.Servicio;
import ar.com.jg.repositories.ServicioRepository;
import ar.com.jg.repositories.CrudRepository;
import jakarta.persistence.EntityManager;
import javax.swing.*;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Optional;


public class ServicioServiceImpl implements ServicioService{

    private EntityManager em;
    private CrudRepository<Servicio> sr;

    public ServicioServiceImpl(EntityManager em) {

        this.em = em;
        sr = new ServicioRepository(em);

    }

    @Override
    public List<Servicio> listarServicios() {

        return sr.listar();

    }

    @Override
    public Optional<Servicio> buscarServicioPorId(Long id) {

        return Optional.ofNullable(sr.buscarPorId(id));

    }

    @Override
    public void guardarServicio(Servicio servicio) {

        int numServicio = 0;

        try {

            if(servicio.getId() != null && servicio.getId() > 0) numServicio = 1;

            em.getTransaction().begin();
            sr.guardar(servicio);
            em.getTransaction().commit();

            if(numServicio > 0){

                JOptionPane.showMessageDialog(null, "El Servicio se ha modificado correctamente.");

            }else{

                JOptionPane.showMessageDialog(null, "El Servicio se ha ingresado correctamente.");

            }

        }catch (Exception ex){

            em.getTransaction().rollback();
            JOptionPane.showMessageDialog(null, ex.getMessage());

        }

    }

    @Override
    public void eliminarServicio(long id) {

        try {

            em.getTransaction().begin();
            sr.eliminar(id);
            em.getTransaction().commit();

            JOptionPane.showMessageDialog(null, "El Servicio se ha eliminado correctamente.");

        }catch (Exception ex){

            em.getTransaction().rollback();
            JOptionPane.showMessageDialog(null, ex.getMessage());

        }

    }

}
