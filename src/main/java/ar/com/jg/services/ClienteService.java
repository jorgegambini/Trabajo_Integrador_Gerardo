package ar.com.jg.services;

import ar.com.jg.model.Cliente;
import java.util.List;
import java.util.Optional;


public interface ClienteService {

    List<Cliente> listarClientes();
    Optional<Cliente> buscarClientePorId(Long id);
    Optional<Cliente> buscarClientePorCUIT(Long cuit);
    void guardarCliente(Cliente cliente);
    void eliminarCliente(long id);

}
