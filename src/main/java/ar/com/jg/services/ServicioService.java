package ar.com.jg.services;

import ar.com.jg.model.Servicio;
import java.util.List;
import java.util.Optional;


public interface ServicioService {

    List<Servicio> listarServicios();
    Optional<Servicio> buscarServicioPorId(Long id);
    void guardarServicio(Servicio servicio);
    void eliminarServicio(long id);

}
