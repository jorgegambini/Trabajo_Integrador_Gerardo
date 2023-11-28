package ar.com.jg.services;

import ar.com.jg.model.OperadorMesaAyuda;
import ar.com.jg.repositories.CrudRepository;
import ar.com.jg.repositories.OperadorMesaAyudaRepository;
import jakarta.persistence.EntityManager;

import javax.swing.*;
import java.util.List;
import java.util.Optional;


public class OperadorMesaAyudaServiceImpl implements OperadorMesaAyudaService{

    private EntityManager em;
    private CrudRepository<OperadorMesaAyuda> or;

    public OperadorMesaAyudaServiceImpl(EntityManager em) {

        this.em = em;
        or = new OperadorMesaAyudaRepository(em);

    }

    @Override
    public List<OperadorMesaAyuda> listarOperadoresMesaAyuda() {

        return or.listar();

    }

    @Override
    public Optional<OperadorMesaAyuda> buscarOperadorMesaAyudaPorId(Long id) {

        return Optional.ofNullable(or.buscarPorId(id));

    }

    @Override
    public Optional<OperadorMesaAyuda> buscarOperadorMesaAyudaPorLegajo(Long legajo) {

        return Optional.ofNullable(((OperadorMesaAyudaRepository) or).buscarPorLegajo(legajo));

    }

    public Optional<OperadorMesaAyuda> loginOperadorMesaAyuda(String user, String pass) {

        return Optional.ofNullable(((OperadorMesaAyudaRepository)or).login(user, pass));

    }

    @Override
    public void guardarOperadorMesaAyuda(OperadorMesaAyuda operadorMesaAyuda) {

        int numOperador = 0;

        try {

            if(operadorMesaAyuda.getId() != null && operadorMesaAyuda.getId() > 0) numOperador = 1;

            em.getTransaction().begin();
            or.guardar(operadorMesaAyuda);
            em.getTransaction().commit();

            if(numOperador > 0){

                JOptionPane.showMessageDialog(null, "El Operador se ha modificado correctamente.");

            }else{

                JOptionPane.showMessageDialog(null, "El Operador se ha ingresado correctamente.");

            }

        }catch (Exception ex){

            em.getTransaction().rollback();
            JOptionPane.showMessageDialog(null, ex.getMessage());

        }

    }

    @Override
    public void eliminarOperadorMesaAyuda(long id) {

        try {

            em.getTransaction().begin();
            or.eliminar(id);
            em.getTransaction().commit();

            JOptionPane.showMessageDialog(null, "El Operador se ha eliminado correctamente.");

        }catch (Exception ex){

            em.getTransaction().rollback();
            JOptionPane.showMessageDialog(null, ex.getMessage());

        }

    }

}
