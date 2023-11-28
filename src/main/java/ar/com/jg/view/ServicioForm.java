package ar.com.jg.view;

import ar.com.jg.view.accessories.*;
import ar.com.jg.model.Especialidad;
import ar.com.jg.model.Servicio;
import ar.com.jg.services.ServicioService;
import ar.com.jg.services.ServicioServiceImpl;
import jakarta.persistence.EntityManager;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;;


public class ServicioForm {

    private MenuForm menuForm;

    private IngresarTexto ingresarTexto;
    private IngresarNumero ingresarNumero;
    private IngresarEspecialidad ingresarEspecialidad;
    private MostrarServicios mostrarServicios;

    private int valorRetornado;
    private String opcionMenu;
    private String strID;
    private String denominacion;

    private ServicioService ss;
    private Servicio servicio;
    @Getter
    @Setter
    private Especialidad especialidad;

    private EntityManager em;

    public ServicioForm(EntityManager em) {

        ss = new ServicioServiceImpl(em);
        this.em = em;

        init();

    }

    private void init() {

        String menuServicio = """
                <html>MENU OPCIONES:<br><br>
                               
                1 - INGRESAR UN SERVICIO.<br>
                2 - EDITAR UN SERVICIO.<br>
                3 - ELIMINAR UN SERVICIO.<br>
                4 - LISTAR SERVICIOS C/ESPECIALIDADES.<br>
                5 - SALIR.<br><br></html>""";

        do {

            menuForm = new MenuForm(menuServicio, 300, 0, 5);
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

                                    servicio = new Servicio();
                                    servicio.setDenominacion(denominacion);

                                    do {

                                        do {

                                            ingresarEspecialidad = new IngresarEspecialidad(em);
                                            valorRetornado = JOptionPane.showOptionDialog(null, ingresarEspecialidad, "Ingrese Especialidad", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);

                                            if (JOptionPane.OK_OPTION == valorRetornado) {

                                                if (ingresarEspecialidad.getEspecialidad() != null) {

                                                    servicio.addEspecialidad(ingresarEspecialidad.getEspecialidad());

                                                }

                                            }

                                        } while (valorRetornado == JOptionPane.CLOSED_OPTION);

                                        if (valorRetornado == JOptionPane.OK_OPTION && servicio.getEspecialidades().size() > 0 && ingresarEspecialidad.getCantidadElementosCombo() != 0) {

                                            valorRetornado = JOptionPane.showOptionDialog(null, "Desea agregar otra especialidad?", "Ingrese Especialidad", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);

                                        }

                                    } while (valorRetornado == JOptionPane.OK_OPTION);

                                    if (valorRetornado == JOptionPane.CANCEL_OPTION && servicio.getEspecialidades().size() > 0)
                                        ss.guardarServicio(servicio);

                                }

                            }

                        } while ((!validarDenominacion(denominacion) && valorRetornado == JOptionPane.OK_OPTION) || valorRetornado == JOptionPane.CLOSED_OPTION);

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

                                } else if (ss.buscarServicioPorId(Long.valueOf(ingresarNumero.getNumero().trim())).isEmpty()) {

                                    JOptionPane.showMessageDialog(null, "El Id ingresado no existe.");

                                } else {

                                    do {

                                        servicio = ss.buscarServicioPorId(Long.valueOf(ingresarNumero.getNumero().trim())).get();

                                        ingresarTexto = new IngresarTexto("Denominación", servicio.getDenominacion(), 90, 200, 0);
                                        valorRetornado = JOptionPane.showOptionDialog(null, ingresarTexto, "Ingrese Denominacion", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);

                                        if (JOptionPane.OK_OPTION == valorRetornado) {

                                            denominacion = ingresarTexto.getTexto().trim();

                                            if (!validarDenominacion(denominacion)) {

                                                JOptionPane.showMessageDialog(null, "Ingrese una Denominación Correcta.");

                                            } else {

                                                servicio.setDenominacion(denominacion);

                                                valorRetornado = JOptionPane.showOptionDialog(null, "Desea recargar las Especialidades?", "Ingrese Especialidad", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);

                                                if (JOptionPane.OK_OPTION == valorRetornado) {

                                                    servicio.getEspecialidades().clear();

                                                    do {

                                                        do {

                                                            ingresarEspecialidad = new IngresarEspecialidad(em);
                                                            valorRetornado = JOptionPane.showOptionDialog(null, ingresarEspecialidad, "Ingrese Especialidad", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);

                                                            if (JOptionPane.OK_OPTION == valorRetornado) {

                                                                servicio.addEspecialidad(ingresarEspecialidad.getEspecialidad());

                                                            }

                                                        } while (valorRetornado == JOptionPane.CLOSED_OPTION); //|| (valorRetornado == JOptionPane.CANCEL_OPTION && servicio.getEspecialidades().isEmpty() && ingresarEspecialidad.getCantidadElementosCombo() != 0));

                                                        if (valorRetornado == JOptionPane.OK_OPTION && servicio.getEspecialidades().size() > 0 && ingresarEspecialidad.getCantidadElementosCombo() != 0) {

                                                            valorRetornado = JOptionPane.showOptionDialog(null, "Desea agregar otra especialidad?", "Ingrese Especialidad", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);

                                                        }

                                                    } while (valorRetornado == JOptionPane.OK_OPTION);

                                                }

                                                if (valorRetornado == JOptionPane.CANCEL_OPTION && servicio.getEspecialidades().size() > 0)
                                                    ss.guardarServicio(servicio);

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

                                } else if (ss.buscarServicioPorId(Long.valueOf(ingresarNumero.getNumero().trim())).isEmpty()) {

                                    JOptionPane.showMessageDialog(null, "El Id ingresado no existe.");

                                } else {

                                    valorRetornado = JOptionPane.showOptionDialog(null, "Está seguro de borrar Servicio?", "Eliminar Servicio", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);

                                    if (JOptionPane.OK_OPTION == valorRetornado) {

                                        servicio = ss.buscarServicioPorId(Long.valueOf(ingresarNumero.getNumero().trim())).get();
                                        ss.eliminarServicio(servicio.getId());

                                    }

                                }

                            }

                        } while (!validarId(strID) && (valorRetornado == JOptionPane.CLOSED_OPTION || valorRetornado == JOptionPane.OK_OPTION));

                        valorRetornado = JOptionPane.CANCEL_OPTION;

                    }

                    case "4" -> {
                        mostrarServicios = new MostrarServicios(em);
                        JOptionPane.showOptionDialog(null, mostrarServicios, "Listado Servicios c/Especialidades", JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new Object[]{"Aceptar"}, "Aceptar");

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
