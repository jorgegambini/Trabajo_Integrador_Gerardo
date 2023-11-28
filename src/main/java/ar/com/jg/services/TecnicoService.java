package ar.com.jg.services;

import ar.com.jg.model.Tecnico;

import java.util.List;
import java.util.Optional;


public interface TecnicoService {

    List<Tecnico> listarTecnicos();
    Optional<Tecnico> buscarTecnicoPorId(Long id);
    Optional<Tecnico> buscarTecnicoPorLegajo(Long legajo);
    Optional<Tecnico> loginTecnico(String user, String pass);
    void guardarTecnico(Tecnico tecnico);
    void eliminarTecnico(long id);

}
