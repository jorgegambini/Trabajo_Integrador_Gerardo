package ar.com.jg.services;

import ar.com.jg.model.DatosContacto;
import java.util.List;
import java.util.Optional;


public interface DatosContactoService {

    List<DatosContacto> listarDatosContactos();
    Optional<DatosContacto> buscarDatosContactoPorId(Long id);
    void guardarDatosContacto(DatosContacto datosContacto);
    void eliminarDatosContacto(long id);

}
