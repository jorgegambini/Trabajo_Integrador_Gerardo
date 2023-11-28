package ar.com.jg.view;

import ar.com.jg.model.Especialidad;
import ar.com.jg.services.EspecialidadService;
import ar.com.jg.services.EspecialidadServiceImpl;
import ar.com.jg.view.accessories.*;
import jakarta.persistence.EntityManager;

import javax.swing.*;
import java.time.LocalDate;


public class ReporteForm {

    private MenuForm menuForm;

    private IngresarFecha ingresarFecha;
    private MostrarReportes mostrarReportes;
    private IngresarEspecialidad ingresarEspecialidad;

    private int valorRetornado;
    private String opcionMenu;
    private LocalDate fechaDesde;
    private LocalDate fechaHasta;

    private EspecialidadService es;
    private Especialidad especialidad;

    private EntityManager em;

    public ReporteForm(EntityManager em) {

        es = new EspecialidadServiceImpl(em);
        this.em = em;

        init();

    }

    private void init() {

        String menuEspecialidad = """
                <html>MENU OPCIONES:<br><br>
                               
                1 - TECNICO CON MAS INCIDENTES RESUELTOS.<br>
                2 - TECNICO CON MAS INCIDENTES RESUELTOS POR ESPECIALIDAD.<br>
                3 - TECNICO CON INCIDENTES MAS RAPIDO RESUELTO.<br>
                4 - SALIR.<br><br></html>""";

        do {

            fechaDesde = LocalDate.now();
            fechaHasta = LocalDate.now();

            menuForm = new MenuForm(menuEspecialidad, 400, 150, 4);
            valorRetornado = JOptionPane.showOptionDialog(null, menuForm, "Seleccione una Opcion", JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new Object[]{"Aceptar"}, "Aceptar");

            if (JOptionPane.OK_OPTION == valorRetornado) {

                opcionMenu = menuForm.getOption().trim();

                if (!validarMenuOpcion(opcionMenu)) JOptionPane.showMessageDialog(null, "Ingrese una Opción Correcta.");

                switch (opcionMenu) {

                    case "1" -> {

                        do {

                            ingresarFecha = new IngresarFecha("Fecha Desde", LocalDate.now(), 110, 150, 30);
                            valorRetornado = JOptionPane.showOptionDialog(null, ingresarFecha, "Ingrese Fecha", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);

                            if (JOptionPane.OK_OPTION == valorRetornado) {

                                fechaDesde = ingresarFecha.getDateField();

                                do {

                                    ingresarFecha = new IngresarFecha("Fecha Hasta", LocalDate.now(), 110, 150, 30);
                                    valorRetornado = JOptionPane.showOptionDialog(null, ingresarFecha, "Ingrese Fecha", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);

                                    if (JOptionPane.OK_OPTION == valorRetornado) {

                                        fechaHasta = ingresarFecha.getDateField();

                                        if (fechaHasta.isBefore(fechaDesde)) {

                                            JOptionPane.showMessageDialog(null, "Fecha Desde debe ser Menor o Igual a Fecha Hasta.");

                                        } else {

                                            mostrarReportes = new MostrarReportes(em, 1, fechaDesde, fechaHasta, null);
                                            JOptionPane.showOptionDialog(null, mostrarReportes, "Listado Cant. Reportes Resueltos", JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new Object[]{"Aceptar"}, "Aceptar");

                                        }
                                    }

                                } while (valorRetornado == JOptionPane.CLOSED_OPTION);

                            }

                        } while (valorRetornado == JOptionPane.CLOSED_OPTION);

                        valorRetornado = JOptionPane.CANCEL_OPTION;

                    }

                    case "2" -> {

                        do {

                            fechaDesde = LocalDate.now();
                            fechaHasta = LocalDate.now();

                            ingresarFecha = new IngresarFecha("Fecha Desde", LocalDate.now(), 110, 150, 30);
                            valorRetornado = JOptionPane.showOptionDialog(null, ingresarFecha, "Ingrese Fecha", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);

                            if (JOptionPane.OK_OPTION == valorRetornado) {

                                fechaDesde = ingresarFecha.getDateField();

                                do {

                                    ingresarFecha = new IngresarFecha("Fecha Hasta", LocalDate.now(), 110, 150, 30);
                                    valorRetornado = JOptionPane.showOptionDialog(null, ingresarFecha, "Ingrese Fecha", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);

                                    if (JOptionPane.OK_OPTION == valorRetornado) {

                                        fechaHasta = ingresarFecha.getDateField();

                                        if (fechaHasta.isBefore(fechaDesde)) {

                                            JOptionPane.showMessageDialog(null, "Fecha Desde debe ser Menor o Igual a Fecha Hasta.");

                                        } else {

                                            do {

                                                ingresarEspecialidad = new IngresarEspecialidad(em);
                                                valorRetornado = JOptionPane.showOptionDialog(null, ingresarEspecialidad, "Seleccione Especialidad", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);

                                                if (JOptionPane.OK_OPTION == valorRetornado) {

                                                    especialidad = ingresarEspecialidad.getEspecialidad();

                                                    mostrarReportes = new MostrarReportes(em, 2, fechaDesde, fechaHasta, especialidad);
                                                    JOptionPane.showOptionDialog(null, mostrarReportes, "Listado Cant. Reportes Resueltos por Especialidad", JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new Object[]{"Aceptar"}, "Aceptar");

                                                }

                                            } while (valorRetornado == JOptionPane.CLOSED_OPTION);

                                        }
                                    }

                                } while (valorRetornado == JOptionPane.CLOSED_OPTION || (valorRetornado == JOptionPane.OK_OPTION && fechaHasta.isBefore(fechaDesde)));

                            }

                        } while (valorRetornado == JOptionPane.CLOSED_OPTION);

                        valorRetornado = JOptionPane.CANCEL_OPTION;

                    }
                    case "3" -> {

                        do {

                            fechaDesde = LocalDate.now();
                            fechaHasta = LocalDate.now();

                            ingresarFecha = new IngresarFecha("Fecha Desde", LocalDate.now(), 110, 150, 30);
                            valorRetornado = JOptionPane.showOptionDialog(null, ingresarFecha, "Ingrese Fecha", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);

                            if (JOptionPane.OK_OPTION == valorRetornado) {

                                fechaDesde = ingresarFecha.getDateField();

                                do {

                                    ingresarFecha = new IngresarFecha("Fecha Hasta", LocalDate.now(), 110, 150, 30);
                                    valorRetornado = JOptionPane.showOptionDialog(null, ingresarFecha, "Ingrese Fecha", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);

                                    if (JOptionPane.OK_OPTION == valorRetornado) {

                                        fechaHasta = ingresarFecha.getDateField();

                                        if (fechaHasta.isBefore(fechaDesde)) {

                                            JOptionPane.showMessageDialog(null, "Fecha Desde debe ser Menor o Igual a Fecha Hasta.");

                                        } else {

                                            mostrarReportes = new MostrarReportes(em, 3, LocalDate.parse("2023-11-20"), LocalDate.parse("2023-11-30"), null);
                                            JOptionPane.showOptionDialog(null, mostrarReportes, "Listado Técnicos más Eficientes", JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new Object[]{"Aceptar"}, "Aceptar");

                                        }
                                    }

                                } while (valorRetornado == JOptionPane.CLOSED_OPTION);

                            }

                        } while (valorRetornado == JOptionPane.CLOSED_OPTION);

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

}
