package ar.com.jg.services;

import ar.com.jg.model.Especialidad;
import java.util.List;
import java.util.Optional;


public interface EspecialidadService {

    List<Especialidad> listarEspecialidades();
    Optional<Especialidad> buscarEspecialidadPorId(Long id);
    Optional<Especialidad> buscarEspecialidadPorDenominacion(String denominacion);
    void guardarEspecialidad(Especialidad especialidad);
    void eliminarEspecialidad(long id);

}
