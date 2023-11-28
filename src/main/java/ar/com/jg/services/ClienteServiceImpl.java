package ar.com.jg.services;

import ar.com.jg.model.Cliente;
import ar.com.jg.repositories.ClienteRepository;
import ar.com.jg.repositories.CrudRepository;
import ar.com.jg.repositories.EspecialidadRepository;
import jakarta.persistence.EntityManager;
import javax.swing.*;
import java.util.List;
import java.util.Optional;


public class ClienteServiceImpl implements ClienteService{

    private EntityManager em;
    private CrudRepository<Cliente> cr;

    public ClienteServiceImpl(EntityManager em) {

        this.em = em;
        cr = new ClienteRepository(em);

    }

    @Override
    public List<Cliente> listarClientes() {

        return cr.listar();

    }

    @Override
    public Optional<Cliente> buscarClientePorId(Long id) {

        return Optional.ofNullable(cr.buscarPorId(id));

    }

    @Override
    public Optional<Cliente> buscarClientePorCUIT(Long cuit) {

        return Optional.ofNullable(((ClienteRepository) cr).buscarPorCUIT(cuit));

    }

    @Override
    public void guardarCliente(Cliente cliente) {

        int numCliente = 0;

        try {

            if(cliente.getId() != null && cliente.getId() > 0) numCliente = 1;

            em.getTransaction().begin();
            cr.guardar(cliente);
            em.getTransaction().commit();

            if (numCliente > 0) {

                JOptionPane.showMessageDialog(null, "El Cliente se ha actualizado correctamente.");

            }else{

                JOptionPane.showMessageDialog(null, "El Cliente se ha ingresado correctamente.");

            }

        }catch (Exception ex){

            em.getTransaction().rollback();
            JOptionPane.showMessageDialog(null, ex.getMessage());

        }

    }

    @Override
    public void eliminarCliente(long id) {

        try {

            em.getTransaction().begin();
            cr.eliminar(id);
            em.getTransaction().commit();

            JOptionPane.showMessageDialog(null, "El Cliente se ha eliminado correctamente.");

        }catch (Exception ex){

            em.getTransaction().rollback();
            JOptionPane.showMessageDialog(null, ex.getMessage());

        }

    }

}
