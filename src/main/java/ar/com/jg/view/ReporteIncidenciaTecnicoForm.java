package ar.com.jg.view;

import ar.com.jg.model.*;
import ar.com.jg.model.enums.EstadoProblema;
import ar.com.jg.model.enums.TipoProblema;
import ar.com.jg.services.ReporteIncidenciaService;
import ar.com.jg.services.ReporteIncidenciaServiceImpl;
import ar.com.jg.view.accessories.*;
import jakarta.persistence.EntityManager;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.time.LocalDate;


public class ReporteIncidenciaTecnicoForm {

    private MenuForm menuForm;

    private IngresarAreaTexto ingresarAreaTexto;
    private IngresarNumero ingresarNumero;
    private IngresarFecha ingresarFecha;
    private IngresarEstadoProblema ingresarEstadoProblema;
    private MostrarReportesIncidencia mostrarReportesIncidencia;
    private IngresarTexto ingresarTexto;

    private int valorRetornado;
    private String opcionMenu;
    private LocalDate fechaAlta;
    private Tecnico tecnico;
    private TipoProblema tipoProblema;
    private String observacionesTecnico;
    private int tiempoResolucion;
    private String strTiempoResolucion;
    private LocalDate fechaResolucion;
    private EstadoProblema estadoProblema;
    private String codigoReporte;

    private ReporteIncidenciaService rs;
    private ReporteIncidencia reporteIncidencia;

    private EntityManager em;
    @Getter @Setter
    private int cantidadElementosCombo;

    public ReporteIncidenciaTecnicoForm(EntityManager em, Tecnico tecnico) {

        rs = new ReporteIncidenciaServiceImpl(em);
        this.tecnico = tecnico;
        this.em = em;

        init();

    }

    private void init() {

        String menuReporteIncidencia = """
                <html>MENU OPCIONES:<br><br>
                               
                1 - LISTAR REPORTES DE INCIDENTE.<br>
                2 - RESOLVER INCIDENTE.<br>
                3 - SALIR.<br><br></html>""";

        do {

            menuForm = new MenuForm(menuReporteIncidencia, 250, 0, 3);
            valorRetornado = JOptionPane.showOptionDialog(null, menuForm, "Seleccione una Opcion", JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new Object[]{"Aceptar"}, "Aceptar");

            if (JOptionPane.OK_OPTION == valorRetornado) {

                opcionMenu = menuForm.getOption().trim();

                if (!validarMenuOpcion(opcionMenu)) JOptionPane.showMessageDialog(null, "Ingrese una Opción Correcta.");

                switch (opcionMenu) {

                    case "1" -> {

                        mostrarReportesIncidencia = new MostrarReportesIncidencia(em, tecnico);
                        JOptionPane.showOptionDialog(null, mostrarReportesIncidencia, "Listado Reportes Incidente", JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new Object[]{"Aceptar"}, "Aceptar");

                        valorRetornado = JOptionPane.CANCEL_OPTION;

                    }

                    case "2" -> {

                        codigoReporte = "";

                        do {

                            ingresarTexto = new IngresarTexto("Código Reporte", "", 110, 100, 0);
                            valorRetornado = JOptionPane.showOptionDialog(null, ingresarTexto, "Ingrese Código Reporte", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);

                            if (JOptionPane.OK_OPTION == valorRetornado) {

                                codigoReporte = ingresarTexto.getTexto().trim();

                                if (!validarCodigo(codigoReporte)) {

                                    JOptionPane.showMessageDialog(null, "Ingrese una Codigo Correcto.");

                                } else if (rs.buscarReporteIncidenciaPorCodigo(codigoReporte).isEmpty()) {

                                    JOptionPane.showMessageDialog(null, "El Codigo ingresado no existe o el Reporte fue Cancelado/Resuelto.");

                                } else {

                                    reporteIncidencia = rs.buscarReporteIncidenciaPorCodigo(codigoReporte).get();
                                    tipoProblema = reporteIncidencia.getTipoProblema();
                                    tiempoResolucion = reporteIncidencia.getTipoProblema().getTiempoResolucion();
                                    fechaAlta = reporteIncidencia.getFechaAlta();
                                    observacionesTecnico = reporteIncidencia.getObservacionesTecnico();
                                    fechaResolucion = reporteIncidencia.getFechaPosibleResolucion();

                                    do {

                                        ingresarAreaTexto = new IngresarAreaTexto("Observaciones Problema:", observacionesTecnico, 160, 300);
                                        valorRetornado = JOptionPane.showOptionDialog(null, ingresarAreaTexto, "Ingrese Observacion", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);

                                        if (JOptionPane.OK_OPTION == valorRetornado) {

                                            observacionesTecnico = ingresarAreaTexto.getTexto().trim();

                                        //} else {

                                            do {

                                                ingresarNumero = new IngresarNumero("Tiempo Resolución:", "" + reporteIncidencia.getTiempoEstimadoResolucion(), 130, 70, 30, true);
                                                valorRetornado = JOptionPane.showOptionDialog(null, ingresarNumero, "Ingrese Tiempo resolución", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

                                                strTiempoResolucion = "0";

                                                if (JOptionPane.OK_OPTION == valorRetornado) {

                                                    strTiempoResolucion = ingresarNumero.getNumero().trim();

                                                    if (!validarNumero(strTiempoResolucion)) {

                                                        JOptionPane.showMessageDialog(null, "Ingrese un Tiempo válido.");

                                                    } else if (!tipoProblema.equals(TipoProblema.Complejo) && tiempoResolucion < Integer.valueOf(strTiempoResolucion)) {

                                                        JOptionPane.showMessageDialog(null, "El Tiempo debe ser Menor o Igual al Tiempo Mostrado.");
                                                        valorRetornado = JOptionPane.CLOSED_OPTION;

                                                    } else if (tipoProblema.equals(TipoProblema.Complejo) && tiempoResolucion > Integer.valueOf(strTiempoResolucion)) {

                                                        JOptionPane.showMessageDialog(null, "El Tiempo debe ser Mayor o Igual al Tiempo Mostrado.");
                                                        valorRetornado = JOptionPane.CLOSED_OPTION;

                                                    } else {

                                                        tiempoResolucion = Integer.valueOf(strTiempoResolucion);

                                                        do {

                                                            ingresarFecha = new IngresarFecha("Fecha", fechaResolucion, 70, 150, 30);
                                                            valorRetornado = JOptionPane.showOptionDialog(null, ingresarFecha, "Ingrese Fecha Resolución", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);

                                                            fechaResolucion = LocalDate.now();

                                                            if (JOptionPane.OK_OPTION == valorRetornado) {

                                                                fechaResolucion = ingresarFecha.getDateField();

                                                                if (fechaResolucion.isBefore(fechaAlta) || fechaResolucion.isBefore(LocalDate.now())) {

                                                                    JOptionPane.showMessageDialog(null, "La Fecha debe ser Mayor o Igual a la Fecha Actual.");

                                                                } else {

                                                                    ingresarEstadoProblema = new IngresarEstadoProblema(EstadoProblema.Procesado, true);
                                                                    JOptionPane.showOptionDialog(null, ingresarEstadoProblema, "Estado Reporte", JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new Object[]{"Aceptar"}, "Aceptar");
                                                                    estadoProblema = ingresarEstadoProblema.getEstadoProblema();

                                                                    reporteIncidencia.setObservacionesTecnico(observacionesTecnico);
                                                                    reporteIncidencia.setTiempoEstimadoResolucion(tiempoResolucion);
                                                                    reporteIncidencia.setFechaPosibleResolucion(fechaResolucion);
                                                                    reporteIncidencia.setEstadoProblema(estadoProblema);

                                                                    if(estadoProblema.name().equals("Resuelto")){

                                                                        valorRetornado = JOptionPane.showOptionDialog(null, "El Reporte se marcara como Resulto y no se podrá acceder. Continua?", "Reporte Resuelto", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

                                                                        if (JOptionPane.OK_OPTION == valorRetornado) {

                                                                            String strMsg = "<html><div style=\"text-align: center;\">Se ha enviado un " + reporteIncidencia.getTecnico().getComunicacionTecnicoCliente().name() + "</div><br>" +
                                                                                            "<div style=\"text-align: center;\">a  " + reporteIncidencia.getCliente() + "</div><br>" +
                                                                                            "<div style=\"text-align: center;\">informando que su Incidente con código: <h3 style=\"font-size: 10px; color: red;\">" + reporteIncidencia.getCodigoReporte() + "</h1></div><br>" +
                                                                                            "<div style=\"text-align: center;\">fué Resuelto Satisfactoriamente.</div></html>";

                                                                            rs.guardarReporteIncidencia(reporteIncidencia);
                                                                            //JOptionPane.showMessageDialog(null, strMsg);

                                                                            MostrarLabel mostrarLabel = new MostrarLabel(strMsg, 250, 50);
                                                                            JOptionPane.showOptionDialog(null, mostrarLabel, "Reporte Incidencia Resuelto", JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new Object[]{"Aceptar"}, "Aceptar");

                                                                        }

                                                                    }else{

                                                                        rs.guardarReporteIncidencia(reporteIncidencia);

                                                                    }

                                                                }

                                                            }

                                                        } while (valorRetornado == JOptionPane.CLOSED_OPTION || ((fechaResolucion.isBefore(fechaAlta) || fechaResolucion.isBefore(LocalDate.now())) && valorRetornado == JOptionPane.OK_OPTION));

                                                    }

                                                }

                                            } while (valorRetornado == JOptionPane.CLOSED_OPTION || (!validarNumero(strTiempoResolucion) && valorRetornado == JOptionPane.OK_OPTION));

                                        }

                                    } while (valorRetornado == JOptionPane.CLOSED_OPTION);

                                }

                            }

                        } while (valorRetornado == JOptionPane.CLOSED_OPTION || (!validarCodigo(codigoReporte) && valorRetornado == JOptionPane.OK_OPTION));

                        valorRetornado = JOptionPane.CANCEL_OPTION;

                    }

                    case "3" ->
                            valorRetornado = JOptionPane.showOptionDialog(null, "Está seguro que desea salir?", "Salir",
                                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

                }

            }

        } while (valorRetornado != JOptionPane.OK_OPTION || !validarMenuOpcion(opcionMenu));


    }

    private boolean validarMenuOpcion(String args) {
        return args.matches("^[1-3]$");
    }

    private boolean validarNumero(String numero) {

        return numero.matches("^[1-9]\\d{0,5}$");

    }

    private boolean validarCodigo(String codigo) {

        return codigo.matches("^[A-Za-z0-9]{10}$");

    }

    private boolean validarEmail(String email) {

        return email.matches("^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-zA-Z]{2,})$");

    }

    private boolean validarTexto(String denominacion) {

        return denominacion.matches("^([A-ZÁÉÍÓÚÜÑ0-9]([A-ZÁÉÍÓÚÜÑa-záéíóüñ0-9]+))([ ][A-ZÁÉÍÓÚÜÑ0-9]([A-ZÁÉÍÓÚÜÑa-záéíóüñ0-9]*)){0,5}");

    }

    private boolean validarTexto1(String denominacion) {

        return denominacion.matches("^([A-ZÁÉÍÓÚÜÑa-záéíóüñ0-9]([A-ZÁÉÍÓÚÜÑa-záéíóüñ0-9]+))([ ][A-ZÁÉÍÓÚÜÑa-záéíóüñ0-9]([A-ZÁÉÍÓÚÜÑa-záéíóüñ0-9]*)){0,5}");

    }

    private boolean validarNombre(String denominacion) {

        return denominacion.matches("^([A-ZÁÉÍÓÚÜÑ]([a-záéíóüñ]+))([ ][A-ZÁÉÍÓÚÜÑ]([a-záéíóüñ]*)){0,5}");

    }

}
