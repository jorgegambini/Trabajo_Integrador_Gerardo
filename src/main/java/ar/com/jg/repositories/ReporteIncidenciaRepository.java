package ar.com.jg.repositories;

import ar.com.jg.model.ReporteIncidencia;
import ar.com.jg.model.enums.EstadoProblema;
import ar.com.jg.utility.RandomString;
import ar.com.jg.view.accessories.MostrarLabel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

import javax.swing.*;
import java.time.LocalDate;
import java.util.List;


public class ReporteIncidenciaRepository implements CrudRepository<ReporteIncidencia> {

    private EntityManager em;

    public ReporteIncidenciaRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<ReporteIncidencia> listar() {

        return em.createQuery("from ReporteIncidencia", ReporteIncidencia.class).getResultList();

    }

    public List<ReporteIncidencia> listarPorRangoFecha(LocalDate fechaDesde, LocalDate fechaHasta) {

        EstadoProblema estadoProblema = EstadoProblema.Resuelto;

        return em.createQuery("from ReporteIncidencia r where r.estadoProblema = :estadoProblema and r.fechaAlta between :fechaDesde and :fechaHasta", ReporteIncidencia.class).setParameter("estadoProblema", estadoProblema).setParameter("fechaDesde", fechaDesde).setParameter("fechaHasta", fechaHasta).getResultList();

    }

    @Override
    public ReporteIncidencia buscarPorId(Long id) {

        return em.find(ReporteIncidencia.class, id);

    }

    public ReporteIncidencia buscarPorCodigo(String codigoReporte) {

        EstadoProblema estadoProblema1 = EstadoProblema.Pendiente;
        EstadoProblema estadoProblema2 = EstadoProblema.Procesado;

        try {

            return em.createQuery("from ReporteIncidencia r where r.codigoReporte like :codigoReporte and (r.estadoProblema = :estadoProblema1 or r.estadoProblema = :estadoProblema2)", ReporteIncidencia.class).setParameter("codigoReporte", codigoReporte).setParameter("estadoProblema1", estadoProblema1).setParameter("estadoProblema2", estadoProblema2).getSingleResult();

        } catch (NoResultException e) {

            return null;

        }

    }

    @Override
    public void guardar(ReporteIncidencia reporteIncidencia) {

        String reportCode = RandomString.getAlphaNumericString(10);

        if (reporteIncidencia.getId() != null && reporteIncidencia.getId() > 0) {

            if(reporteIncidencia.getEstadoProblema().name().equals("Cancelado"))reporteIncidencia.setCodigoReporte(reportCode);

            em.merge(reporteIncidencia);

        } else {

            reporteIncidencia.setCodigoReporte(reportCode);

            em.persist(reporteIncidencia);

        }

    }

    @Override
    public void eliminar(Long id) {

        em.remove(buscarPorId(id));

    }

}
