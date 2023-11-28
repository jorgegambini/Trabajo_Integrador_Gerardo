package ar.com.jg.services;

import ar.com.jg.model.DatosContacto;
import ar.com.jg.repositories.CrudRepository;
import ar.com.jg.repositories.DatosContactoRepository;
import jakarta.persistence.EntityManager;
import javax.swing.*;
import java.util.List;
import java.util.Optional;


public class DatosContactoServiceImpl implements DatosContactoService{

    private EntityManager em;
    private CrudRepository<DatosContacto> dr;

    public DatosContactoServiceImpl(EntityManager em) {

        this.em = em;
        dr = new DatosContactoRepository(em);

    }

    @Override
    public List<DatosContacto> listarDatosContactos() {

        return dr.listar();

    }

    @Override
    public Optional<DatosContacto> buscarDatosContactoPorId(Long id) {

        return Optional.ofNullable(dr.buscarPorId(id));

    }

    @Override
    public void guardarDatosContacto(DatosContacto datosContacto) {

        try {

            em.getTransaction().begin();
            dr.guardar(datosContacto);
            em.getTransaction().commit();

        }catch (Exception ex){

            em.getTransaction().rollback();
            JOptionPane.showMessageDialog(null, ex.getMessage());

        }

    }

    @Override
    public void eliminarDatosContacto(long id) {

        try {

            em.getTransaction().begin();
            dr.eliminar(id);
            em.getTransaction().commit();

        }catch (Exception ex){

            em.getTransaction().rollback();
            JOptionPane.showMessageDialog(null, ex.getMessage());

        }

    }

}
