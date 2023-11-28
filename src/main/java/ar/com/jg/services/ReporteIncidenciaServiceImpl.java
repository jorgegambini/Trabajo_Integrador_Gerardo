package ar.com.jg.services;

import ar.com.jg.model.ReporteIncidencia;
import ar.com.jg.repositories.CrudRepository;
import ar.com.jg.repositories.ReporteIncidenciaRepository;
import ar.com.jg.view.accessories.MostrarLabel;
import jakarta.persistence.EntityManager;

import javax.swing.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


public class ReporteIncidenciaServiceImpl implements ReporteIncidenciaService{

    private EntityManager em;
    private CrudRepository<ReporteIncidencia> rr;

    public ReporteIncidenciaServiceImpl(EntityManager em) {

        this.em = em;
        rr = new ReporteIncidenciaRepository(em);

    }

    @Override
    public List<ReporteIncidencia> listarReportesIncidencia() {

        return rr.listar();

    }

    @Override
    public List<ReporteIncidencia> listarReportesIncidenciaPorRangoFecha(LocalDate fechaDesde, LocalDate fechaHasta) {

        return ((ReporteIncidenciaRepository)rr).listarPorRangoFecha(fechaDesde, fechaHasta);

    }

    @Override
    public Optional<ReporteIncidencia> buscarReporteIncidenciaPorId(Long id) {

        return Optional.ofNullable(rr.buscarPorId(id));

    }

    @Override
    public Optional<ReporteIncidencia> buscarReporteIncidenciaPorCodigo(String codigoReporte) {

        return Optional.ofNullable(((ReporteIncidenciaRepository) rr).buscarPorCodigo(codigoReporte));

    }

    @Override
    public void guardarReporteIncidencia(ReporteIncidencia reporteIncidencia) {

        String lblReporte = "";
        int numReporte = 0;

        try {

            if(reporteIncidencia.getId() != null && reporteIncidencia.getId() > 0) numReporte = 1;

            em.getTransaction().begin();
            rr.guardar(reporteIncidencia);
            em.getTransaction().commit();

            if (numReporte > 0) {

                String estado = reporteIncidencia.getEstadoProblema().name();

                if (estado.equals("Cancelado")) {

                    lblReporte = "<html><div style=\"text-align: center;\">El Reporte de Incidencia se ha " + estado + " correctamente.</div><br>" +
                            "<div style=\"text-align: center;\"> <h1 style=\"font-size: 24px; color: red;\">" + reporteIncidencia.getCodigoReporte() + "</h1></div></html>";

                    MostrarLabel mostrarLabel = new MostrarLabel(lblReporte, 350, 50);
                    JOptionPane.showOptionDialog(null, mostrarLabel, "Reporte Incidencia Actualizado", JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new Object[]{"Aceptar"}, "Aceptar");

                } else {

                    JOptionPane.showMessageDialog(null, "El Reporte Incidencia se ha actualizado correctamente.");

                }

            }else{

                lblReporte = "<html><div style=\"text-align: center;\">El Reporte de Incidencia se ha ingresado correctamente.</div><br>" +
                        "<div style=\"text-align: center;\"> <h1 style=\"font-size: 24px; color: red;\">" + reporteIncidencia.getCodigoReporte() + "</h1></div></html>";

                MostrarLabel mostrarLabel = new MostrarLabel(lblReporte, 320, 50);
                JOptionPane.showOptionDialog(null, mostrarLabel, "Reporte Incidencia Generado", JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new Object[]{"Aceptar"}, "Aceptar");


            }

        }catch (Exception ex){

            em.getTransaction().rollback();
            JOptionPane.showMessageDialog(null, ex.getMessage());

        }

    }

    @Override
    public void eliminarReporteIncidencia(long id) {

        try {

            em.getTransaction().begin();
            rr.eliminar(id);
            em.getTransaction().commit();

            JOptionPane.showMessageDialog(null, "El Reporte de Incidencia se ha eliminado correctamente.");

        }catch (Exception ex){

            em.getTransaction().rollback();
            JOptionPane.showMessageDialog(null, ex.getMessage());

        }

    }

}
