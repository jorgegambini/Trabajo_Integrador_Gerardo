package ar.com.jg.services;

import ar.com.jg.model.ReporteIncidencia;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


public interface ReporteIncidenciaService {

    List<ReporteIncidencia> listarReportesIncidencia();
    List<ReporteIncidencia> listarReportesIncidenciaPorRangoFecha(LocalDate fechaDesde, LocalDate fechaHasta);
    Optional<ReporteIncidencia> buscarReporteIncidenciaPorId(Long id);
    Optional<ReporteIncidencia> buscarReporteIncidenciaPorCodigo(String codigoReporte);
    void guardarReporteIncidencia(ReporteIncidencia reporteIncidencia);
    void eliminarReporteIncidencia(long id);

}
