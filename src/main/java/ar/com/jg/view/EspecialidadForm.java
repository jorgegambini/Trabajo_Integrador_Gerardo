package ar.com.jg.view;

import ar.com.jg.model.Especialidad;
import ar.com.jg.services.EspecialidadService;
import ar.com.jg.services.EspecialidadServiceImpl;
import ar.com.jg.view.accessories.*;
import jakarta.persistence.EntityManager;

import javax.swing.*;


public class EspecialidadForm {

    private MenuForm menuForm;

    private IngresarTexto ingresarTexto;
    private IngresarNumero ingresarNumero;
    private MostrarEspecialidades mostrarEspecialidades;

    private int valorRetornado;
    private String opcionMenu;
    private String strID;
    private String denominacion;

    private EspecialidadService es;
    private Especialidad especialidad;

    private EntityManager em;

    public EspecialidadForm(EntityManager em) {

        es = new EspecialidadServiceImpl(em);
        this.em = em;

        init();

    }

    private void init() {

        String menuEspecialidad = """
                <html>MENU OPCIONES:<br><br>
                               
                1 - INGRESAR UNA ESPECIALIDAD.<br>
                2 - EDITAR UNA ESPECIALIDAD.<br>
                3 - ELIMINAR UNA ESPECIALIDAD.<br>
                4 - LISTAR ESPECIALIDADES.<br>
                5 - SALIR.<br><br></html>""";

        do {

            menuForm = new MenuForm(menuEspecialidad, 250, 50, 5);
            valorRetornado = JOptionPane.showOptionDialog(null, menuForm, "Seleccione una Opcion", JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new Object[]{"Aceptar"}, "Aceptar");

            if (JOptionPane.OK_OPTION == valorRetornado) {

                opcionMenu = menuForm.getOption().trim();

                if (!validarMenuOpcion(opcionMenu)) JOptionPane.showMessageDialog(null, "Ingrese una Opción Correcta.");

                switch (opcionMenu) {

                    case "1" -> {

                        denominacion = "";

                        do {

                            ingresarTexto = new IngresarTexto("Denominación", "", 90, 200, 0);
                            valorRetornado = JOptionPane.showOptionDialog(null, ingresarTexto, "Ingrese Denominacion", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);

                            if (JOptionPane.OK_OPTION == valorRetornado) {

                                denominacion = ingresarTexto.getTexto().trim();

                                if (!validarDenominacion(denominacion)) {

                                    JOptionPane.showMessageDialog(null, "Ingrese una Denominación Correcta.");

                                } else {

                                    especialidad = new Especialidad();
                                    especialidad.setDenominacion(denominacion);
                                    es.guardarEspecialidad(especialidad);

                                }

                            }

                        } while (!validarDenominacion(denominacion) && (valorRetornado == JOptionPane.CLOSED_OPTION || valorRetornado == JOptionPane.OK_OPTION));

                        valorRetornado = JOptionPane.CANCEL_OPTION;

                    }

                    case "2" -> {

                        strID = "0";
                        denominacion = "";

                        do {

                            ingresarNumero = new IngresarNumero("ID:", "0", 20, 70, 130, true);
                            valorRetornado = JOptionPane.showOptionDialog(null, ingresarNumero, "Ingrese ID", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

                            if (JOptionPane.OK_OPTION == valorRetornado) {

                                strID = ingresarNumero.getNumero().trim();

                                if (!validarId(strID)) {

                                    JOptionPane.showMessageDialog(null, "Ingrese un Id Correcto.");

                                } else if (es.buscarEspecialidadPorId(Long.valueOf(ingresarNumero.getNumero().trim())).isEmpty()) {

                                    JOptionPane.showMessageDialog(null, "El Id ingresado no existe.");

                                } else {

                                    do {

                                        especialidad = es.buscarEspecialidadPorId(Long.valueOf(ingresarNumero.getNumero().trim())).get();

                                        ingresarTexto = new IngresarTexto("Denominación", especialidad.getDenominacion(), 90, 200, 0);
                                        valorRetornado = JOptionPane.showOptionDialog(null, ingresarTexto, "Ingrese Denominacion", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);

                                        if (JOptionPane.OK_OPTION == valorRetornado) {

                                            denominacion = ingresarTexto.getTexto().trim();

                                            if (!validarDenominacion(denominacion)) {

                                                JOptionPane.showMessageDialog(null, "Ingrese una Denominación Correcta.");

                                            } else {

                                                especialidad.setDenominacion(denominacion);
                                                es.guardarEspecialidad(especialidad);

                                            }

                                        }

                                    } while (!validarDenominacion(denominacion) && (valorRetornado == JOptionPane.CLOSED_OPTION || valorRetornado == JOptionPane.OK_OPTION));

                                }

                            }

                        } while (!validarId(strID) && (valorRetornado == JOptionPane.CLOSED_OPTION || valorRetornado == JOptionPane.OK_OPTION));

                        valorRetornado = JOptionPane.CANCEL_OPTION;

                    }
                    case "3" -> {

                        strID = "0";

                        do {

                            ingresarNumero = new IngresarNumero("ID:", "0", 20, 70, 130, true);
                            valorRetornado = JOptionPane.showOptionDialog(null, ingresarNumero, "Ingrese ID", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

                            if (JOptionPane.OK_OPTION == valorRetornado) {

                                strID = ingresarNumero.getNumero().trim();

                                if (!validarId(strID)) {

                                    JOptionPane.showMessageDialog(null, "Ingrese un Id Correcto.");

                                } else if (es.buscarEspecialidadPorId(Long.valueOf(ingresarNumero.getNumero().trim())).isEmpty()) {

                                    JOptionPane.showMessageDialog(null, "El Id ingresado no existe.");

                                } else {

                                    valorRetornado = JOptionPane.showOptionDialog(null, "Está seguro de borrar Especialidad?", "Eliminar Especialidad", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);

                                    if (JOptionPane.OK_OPTION == valorRetornado) {

                                        especialidad = es.buscarEspecialidadPorId(Long.valueOf(ingresarNumero.getNumero().trim())).get();
                                        es.eliminarEspecialidad(especialidad.getId());

                                    }

                                }

                            }

                        } while (!validarId(strID) && (valorRetornado == JOptionPane.CLOSED_OPTION || valorRetornado == JOptionPane.OK_OPTION));

                        valorRetornado = JOptionPane.CANCEL_OPTION;

                    }

                    case "4" -> {
                        mostrarEspecialidades = new MostrarEspecialidades(em);
                        JOptionPane.showOptionDialog(null, mostrarEspecialidades, "Listado Especialidades", JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new Object[]{"Aceptar"}, "Aceptar");

                        valorRetornado = JOptionPane.CANCEL_OPTION;
                    }

                    case "5" ->
                            valorRetornado = JOptionPane.showOptionDialog(null, "Está seguro que desea salir?", "Salir",
                                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

                }

            }

        } while (valorRetornado != JOptionPane.OK_OPTION || !validarMenuOpcion(opcionMenu));


    }

    private boolean validarMenuOpcion(String args) {
        return args.matches("^[1-5]$");
    }

    private boolean validarId(String id) {

        return id.matches("^[1-9]\\d{0,8}$");

    }

    private boolean validarDenominacion(String denominacion) {

        return denominacion.matches("^([A-ZÁÉÍÓÚÜÑ]([A-ZÁÉÍÓÚÜÑa-záéíóüñ0-9]+))([ ][A-ZÁÉÍÓÚÜÑ]([A-ZÁÉÍÓÚÜÑa-záéíóüñ0-9]*)){0,5}");

    }

}
