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


public class ReporteIncidenciaOperadorForm {

    private MenuForm menuForm;
    private IngresarCliente ingresarCliente;
    private IngresarServicio ingresarServicio;
    private IngresarTecnico ingresarTecnico;
    private IngresarTipoProblema ingresarTipoProblema;
    private IngresarAreaTexto ingresarAreaTexto;
    private IngresarNumero ingresarNumero;
    private IngresarFecha ingresarFecha;
    private IngresarEstadoProblema ingresarEstadoProblema;
    private MostrarReportesIncidencia mostrarReportesIncidencia;
    private IngresarTexto ingresarTexto;

    private int valorRetornado;
    private String opcionMenu;
    private LocalDate fechaAlta;
    private OperadorMesaAyuda operadorMesaAyuda;
    @Getter
    @Setter
    private Cliente cliente;
    private Servicio servicio;
    private Tecnico tecnico;
    private TipoProblema tipoProblema;
    private String descripcionProblema;
    private int tiempoResolucion;
    private String strTiempoResolucion;
    private LocalDate fechaResolucion;
    private EstadoProblema estadoProblema;
    private String codigoReporte;

    private ReporteIncidenciaService rs;
    private ReporteIncidencia reporteIncidencia;

    private EntityManager em;
    @Getter
    @Setter
    private int cantidadElementosCombo;

    public ReporteIncidenciaOperadorForm(EntityManager em, OperadorMesaAyuda operadorMesaAyuda) {

        rs = new ReporteIncidenciaServiceImpl(em);
        this.operadorMesaAyuda = operadorMesaAyuda;
        this.em = em;

        init();

    }

    private void init() {

        fechaAlta = LocalDate.now();

        String menuReporteIncidencia = """
                <html>MENU OPCIONES:<br><br>
                               
                1 - INGRESAR REPORTE DE INCIDENTE.<br>
                2 - CANCELAR REPORTE DE INCIDENTE.<br>
                3 - LISTAR REPORTES DE INCIDENTE.<br>
                4 - SALIR.<br><br></html>""";

        do {

            menuForm = new MenuForm(menuReporteIncidencia, 250, 0, 4);
            valorRetornado = JOptionPane.showOptionDialog(null, menuForm, "Seleccione una Opcion", JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new Object[]{"Aceptar"}, "Aceptar");

            if (JOptionPane.OK_OPTION == valorRetornado) {

                opcionMenu = menuForm.getOption().trim();

                if (!validarMenuOpcion(opcionMenu)) JOptionPane.showMessageDialog(null, "Ingrese una Opción Correcta.");

                switch (opcionMenu) {

                    case "1" -> {

                        do {

                            ingresarCliente = new IngresarCliente(em);
                            valorRetornado = JOptionPane.showOptionDialog(null, ingresarCliente, "Ingrese Cliente", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);

                            if (JOptionPane.OK_OPTION == valorRetornado) {

                                cliente = ingresarCliente.getCliente();

                                do {

                                    ingresarServicio = new IngresarServicio(em, cliente);
                                    valorRetornado = JOptionPane.showOptionDialog(null, ingresarServicio, "Ingrese Servicio", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);

                                    if (JOptionPane.OK_OPTION == valorRetornado) {

                                        servicio = ingresarServicio.getServicio();

                                        do {

                                            ingresarTecnico = new IngresarTecnico(servicio);
                                            valorRetornado = JOptionPane.showOptionDialog(null, ingresarTecnico, "Ingrese Tecnico", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);

                                            if (JOptionPane.OK_OPTION == valorRetornado) {

                                                tecnico = ingresarTecnico.getTecnico();

                                                do {

                                                    ingresarTipoProblema = new IngresarTipoProblema();
                                                    valorRetornado = JOptionPane.showOptionDialog(null, ingresarTipoProblema, "Ingrese Tipo Problema", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);

                                                    if (JOptionPane.OK_OPTION == valorRetornado) {

                                                        tipoProblema = ingresarTipoProblema.getTipoProblema();
                                                        tiempoResolucion = tipoProblema.getTiempoResolucion();

                                                        do {

                                                            descripcionProblema = "";

                                                            ingresarAreaTexto = new IngresarAreaTexto("Descripcion Problema:", "", 140, 300);
                                                            valorRetornado = JOptionPane.showOptionDialog(null, ingresarAreaTexto, "Ingrese Descripcion", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);

                                                            if (JOptionPane.OK_OPTION == valorRetornado) {

                                                                descripcionProblema = ingresarAreaTexto.getTexto().trim();

                                                                if (descripcionProblema.isEmpty()) {

                                                                    JOptionPane.showMessageDialog(null, "La Descripción no puede estar vacia.");

                                                                } else {

                                                                    do {

                                                                        strTiempoResolucion = "";

                                                                        ingresarNumero = new IngresarNumero("Tiempo Resolución:", "" + tiempoResolucion, 130, 70, 30, true);
                                                                        valorRetornado = JOptionPane.showOptionDialog(null, ingresarNumero, "Ingrese Tiempo resolución", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

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

                                                                                    fechaResolucion = LocalDate.now();

                                                                                    ingresarFecha = new IngresarFecha("Fecha", LocalDate.now(), 70, 150, 30);
                                                                                    valorRetornado = JOptionPane.showOptionDialog(null, ingresarFecha, "Ingrese Fecha Resolución", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);

                                                                                    if (JOptionPane.OK_OPTION == valorRetornado) {

                                                                                        fechaResolucion = ingresarFecha.getDateField();

                                                                                        if (fechaResolucion.isBefore(fechaAlta)) {

                                                                                            JOptionPane.showMessageDialog(null, "La Fecha debe ser Mayor o Igual a la Fecha Actual.");

                                                                                        } else {

                                                                                            ingresarEstadoProblema = new IngresarEstadoProblema(EstadoProblema.Pendiente, false);
                                                                                            JOptionPane.showOptionDialog(null, ingresarEstadoProblema, "Estado Reporte", JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new Object[]{"Aceptar"}, "Aceptar");
                                                                                            estadoProblema = ingresarEstadoProblema.getEstadoProblema();

                                                                                            reporteIncidencia = new ReporteIncidencia();
                                                                                            reporteIncidencia.setFechaAlta(fechaAlta);
                                                                                            reporteIncidencia.setOperador(operadorMesaAyuda);
                                                                                            reporteIncidencia.setCliente(cliente);
                                                                                            reporteIncidencia.setServicio(servicio);
                                                                                            reporteIncidencia.setTecnico(tecnico);
                                                                                            reporteIncidencia.setTipoProblema(tipoProblema);
                                                                                            reporteIncidencia.setDescripcionProblema(descripcionProblema);
                                                                                            reporteIncidencia.setTiempoEstimadoResolucion(tiempoResolucion);
                                                                                            reporteIncidencia.setFechaPosibleResolucion(fechaResolucion);
                                                                                            reporteIncidencia.setEstadoProblema(estadoProblema);
                                                                                            rs.guardarReporteIncidencia(reporteIncidencia);


                                                                                        }

                                                                                    }

                                                                                } while (valorRetornado == JOptionPane.CLOSED_OPTION || (fechaResolucion.isBefore(fechaAlta) && valorRetornado == JOptionPane.OK_OPTION));

                                                                            }

                                                                        }

                                                                    } while (valorRetornado == JOptionPane.CLOSED_OPTION || (!validarNumero(strTiempoResolucion) && valorRetornado == JOptionPane.OK_OPTION));

                                                                }

                                                            }

                                                        } while (valorRetornado == JOptionPane.CLOSED_OPTION || (valorRetornado == JOptionPane.OK_OPTION && descripcionProblema.isEmpty()));

                                                    }

                                                } while (valorRetornado == JOptionPane.CLOSED_OPTION);

                                            }

                                        } while (valorRetornado == JOptionPane.CLOSED_OPTION);


                                    }

                                } while (valorRetornado == JOptionPane.CLOSED_OPTION);

                            }

                        } while (valorRetornado == JOptionPane.CLOSED_OPTION);

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

                                    JOptionPane.showMessageDialog(null, "El Codigo ingresado no existe o el Reporte fue Cancelado/Resuelto.", "Información", JOptionPane.INFORMATION_MESSAGE);

                                } else {

                                    valorRetornado = JOptionPane.showOptionDialog(null, "El Reporte se marcara como Cancelado y no se podrá acceder. Continua?", "Reporte Cancelado", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, null, null);

                                    if (JOptionPane.OK_OPTION == valorRetornado) {

                                        reporteIncidencia = rs.buscarReporteIncidenciaPorCodigo(codigoReporte).get();

                                        ingresarEstadoProblema = new IngresarEstadoProblema(EstadoProblema.Cancelado, false);
                                        JOptionPane.showOptionDialog(null, ingresarEstadoProblema, "Estado Reporte", JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new Object[]{"Aceptar"}, "Aceptar");
                                        estadoProblema = ingresarEstadoProblema.getEstadoProblema();

                                        reporteIncidencia.setEstadoProblema(estadoProblema);
                                        rs.guardarReporteIncidencia(reporteIncidencia);

                                    }

                                }

                            }

                        } while (valorRetornado == JOptionPane.CLOSED_OPTION || (!validarCodigo(codigoReporte) && valorRetornado == JOptionPane.OK_OPTION));

                        valorRetornado = JOptionPane.CANCEL_OPTION;

                    }

                    case "3" -> {

                        mostrarReportesIncidencia = new MostrarReportesIncidencia(em, null);
                        JOptionPane.showOptionDialog(null, mostrarReportesIncidencia, "Listado Reportes Incidente", JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new Object[]{"Aceptar"}, "Aceptar");

                        valorRetornado = JOptionPane.CANCEL_OPTION;

                    }

                    case "4" ->
                            valorRetornado = JOptionPane.showOptionDialog(null, "Está seguro que desea salir?", "Salir",
                                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

                }

            }

        } while (valorRetornado != JOptionPane.OK_OPTION || !validarMenuOpcion(opcionMenu));


    }

    private boolean validarMenuOpcion(String args) {
        return args.matches("^[1-4]$");
    }

    private boolean validarNumero(String numero) {

        return numero.matches("^[1-9]\\d{0,5}$");

    }

    private boolean validarCodigo(String codigo) {

        return codigo.matches("^[A-Za-z0-9]{10}$");

    }

}
