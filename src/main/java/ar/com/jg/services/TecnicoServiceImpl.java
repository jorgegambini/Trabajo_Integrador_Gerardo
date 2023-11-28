package ar.com.jg.services;

import ar.com.jg.model.Tecnico;
import ar.com.jg.repositories.CrudRepository;
import ar.com.jg.repositories.TecnicoRepository;
import jakarta.persistence.EntityManager;

import javax.swing.*;
import java.util.List;
import java.util.Optional;


public class TecnicoServiceImpl implements TecnicoService{

    private EntityManager em;
    private CrudRepository<Tecnico> tr;

    public TecnicoServiceImpl(EntityManager em) {

        this.em = em;
        tr = new TecnicoRepository(em);

    }

    @Override
    public List<Tecnico> listarTecnicos() {

        return tr.listar();

    }

    @Override
    public Optional<Tecnico> buscarTecnicoPorId(Long id) {

        return Optional.ofNullable(tr.buscarPorId(id));

    }

    @Override
    public Optional<Tecnico> buscarTecnicoPorLegajo(Long legajo) {

        return Optional.ofNullable(((TecnicoRepository) tr).buscarPorLegajo(legajo));

    }

    public Optional<Tecnico> loginTecnico(String user, String pass) {

        return Optional.ofNullable(((TecnicoRepository)tr).login(user, pass));

    }

    @Override
    public void guardarTecnico(Tecnico tecnico) {

        int numTecnico = 0;

        try {

            if(tecnico.getId() != null && tecnico.getId() > 0) numTecnico = 1;

            em.getTransaction().begin();
            tr.guardar(tecnico);
            em.getTransaction().commit();

            if(numTecnico > 0){

                JOptionPane.showMessageDialog(null, "El Técnico se ha modificado correctamente.");

            }else{

                JOptionPane.showMessageDialog(null, "El Técnico se ha ingresado correctamente.");

            }

        }catch (Exception ex){

            em.getTransaction().rollback();
            JOptionPane.showMessageDialog(null, ex.getMessage());

        }

    }

    @Override
    public void eliminarTecnico(long id) {

        try {

            em.getTransaction().begin();
            tr.eliminar(id);
            em.getTransaction().commit();

            JOptionPane.showMessageDialog(null, "El Técnico se ha eliminado correctamente");

        }catch (Exception ex){

            em.getTransaction().rollback();
            JOptionPane.showMessageDialog(null, ex.getMessage());

        }

    }

}
