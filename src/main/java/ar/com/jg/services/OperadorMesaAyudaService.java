package ar.com.jg.services;

import ar.com.jg.model.OperadorMesaAyuda;

import java.util.List;
import java.util.Optional;


public interface OperadorMesaAyudaService {

    List<OperadorMesaAyuda> listarOperadoresMesaAyuda();
    Optional<OperadorMesaAyuda> buscarOperadorMesaAyudaPorId(Long id);
    Optional<OperadorMesaAyuda> buscarOperadorMesaAyudaPorLegajo(Long legajo);
    Optional<OperadorMesaAyuda> loginOperadorMesaAyuda(String user, String pass);
    void guardarOperadorMesaAyuda(OperadorMesaAyuda operadorMesaAyuda);
    void eliminarOperadorMesaAyuda(long id);

}
