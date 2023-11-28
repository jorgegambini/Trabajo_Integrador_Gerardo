package ar.com.jg.view;

import ar.com.jg.model.DatosContacto;
import ar.com.jg.model.Servicio;
import ar.com.jg.model.Cliente;
import ar.com.jg.services.*;
import ar.com.jg.view.accessories.*;
import jakarta.persistence.EntityManager;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;


public class ClienteForm {

    private MenuForm menuForm;

    private IngresarTexto ingresarTexto;
    private IngresarNumero ingresarNumero;
    private IngresarServicio ingresarServicio;
    private MostrarClientes mostrarClientes;

    private int valorRetornado;
    private String opcionMenu;
    private String strCUIT;
    private String razonSocial;
    private String strTelefono;
    private String strCelular;
    private String email;


    private ClienteService cs;
    private Cliente cliente;
    //private DatosContactoService ds;
    DatosContacto datosContacto;
    //private ServicioService ss;
    @Getter
    @Setter
    private Servicio servicio;

    private EntityManager em;
    @Getter
    @Setter
    private int cantidadElementosCombo;

    public ClienteForm(EntityManager em) {

        cs = new ClienteServiceImpl(em);
        //ds = new DatosContactoServiceImpl(em);
        //ss = new ServicioServiceImpl(em);

        this.em = em;

        init();

    }

    private void init() {

        String menuOperadorMesaAyuda = """
                <html>MENU OPCIONES:<br><br>
                               
                1 - INGRESAR UN CLIENTE.<br>
                2 - EDITAR UN CLIENTE.<br>
                3 - ELIMINAR UN CLIENTE.<br>
                4 - LISTAR CLIENTES.<br>
                5 - SALIR.<br><br></html>""";

        do {

            menuForm = new MenuForm(menuOperadorMesaAyuda, 200, 0, 5);
            valorRetornado = JOptionPane.showOptionDialog(null, menuForm, "Seleccione una Opcion", JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new Object[]{"Aceptar"}, "Aceptar");

            if (JOptionPane.OK_OPTION == valorRetornado) {

                opcionMenu = menuForm.getOption().trim();

                if (!validarMenuOpcion(opcionMenu)) JOptionPane.showMessageDialog(null, "Ingrese una Opción Correcta.");

                switch (opcionMenu) {

                    case "1" -> {

                        strCUIT = "0";
                        razonSocial = "";
                        strTelefono = "0";
                        strCelular = "0";
                        email = "";

                        do {

                            ingresarNumero = new IngresarNumero("CUIT:", "0", 60, 100, 100, true);
                            valorRetornado = JOptionPane.showOptionDialog(null, ingresarNumero, "Ingrese CUIT", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);

                            if (JOptionPane.OK_OPTION == valorRetornado) {

                                strCUIT = ingresarNumero.getNumero().trim();

                                if (!validarCUIT(strCUIT)) {

                                    JOptionPane.showMessageDialog(null, "Ingrese un CUIT Correcto.");

                                } else {

                                    do {

                                        ingresarTexto = new IngresarTexto("Razón Social:", "", 90, 200, 0);
                                        valorRetornado = JOptionPane.showOptionDialog(null, ingresarTexto, "Ingrese Razón Social", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);

                                        if (JOptionPane.OK_OPTION == valorRetornado) {

                                            razonSocial = ingresarTexto.getTexto().trim();

                                            if (!validarTexto1(razonSocial)) {

                                                JOptionPane.showMessageDialog(null, "Ingrese una Razón Social Correcta.");

                                            } else {

                                                do {

                                                    ingresarNumero = new IngresarNumero("Telefono:", "0", 70, 100, 0, true);
                                                    valorRetornado = JOptionPane.showOptionDialog(null, ingresarNumero, "Ingrese Telefono", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);

                                                    if (JOptionPane.OK_OPTION == valorRetornado) {

                                                        strTelefono = ingresarNumero.getNumero().trim();

                                                        if (!validarTelefono(strTelefono)) {

                                                            JOptionPane.showMessageDialog(null, "Ingrese un Telefono Correcto.");

                                                        } else {

                                                            do {

                                                                ingresarNumero = new IngresarNumero("Celular:", "0", 70, 100, 0, true);
                                                                valorRetornado = JOptionPane.showOptionDialog(null, ingresarNumero, "Ingrese Celular", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);

                                                                if (JOptionPane.OK_OPTION == valorRetornado) {

                                                                    strCelular = ingresarNumero.getNumero().trim();

                                                                    if (!validarTelefono(strCelular)) {

                                                                        JOptionPane.showMessageDialog(null, "Ingrese un Celular Correcto.");

                                                                    } else {

                                                                        do {

                                                                            ingresarTexto = new IngresarTexto("Email:", "", 70, 250, 0);
                                                                            valorRetornado = JOptionPane.showOptionDialog(null, ingresarTexto, "Ingrese Email", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);

                                                                            if (JOptionPane.OK_OPTION == valorRetornado) {

                                                                                email = ingresarTexto.getTexto().trim();

                                                                                if (!validarEmail(email)) {

                                                                                    JOptionPane.showMessageDialog(null, "Ingrese un Email Correcto.");

                                                                                } else {

                                                                                    datosContacto = new DatosContacto();
                                                                                    datosContacto.setTelefono(Long.valueOf(strTelefono));
                                                                                    datosContacto.setCelular(Long.valueOf(strCelular));
                                                                                    datosContacto.setEmail(email);
                                                                                    cliente = new Cliente();
                                                                                    cliente.setCuit(Long.valueOf(strCUIT));
                                                                                    cliente.setRazonSocial(razonSocial);
                                                                                    cliente.setDatosContacto(datosContacto);

                                                                                    do {

                                                                                        do {

                                                                                            ingresarServicio = new IngresarServicio(em, null);
                                                                                            valorRetornado = JOptionPane.showOptionDialog(null, ingresarServicio, "Ingrese Servicio", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);

                                                                                            if (JOptionPane.OK_OPTION == valorRetornado) {

                                                                                                cliente.addServicio(ingresarServicio.getServicio());

                                                                                            }

                                                                                        } while (valorRetornado == JOptionPane.CLOSED_OPTION);

                                                                                        if (valorRetornado == JOptionPane.OK_OPTION && cliente.getServicios().size() > 0 && ingresarServicio.getCantidadElementosCombo() != 0) {

                                                                                            valorRetornado = JOptionPane.showOptionDialog(null, "Desea agregar otro servicio?", "Ingrese Servicio", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);

                                                                                        }

                                                                                    } while (valorRetornado == JOptionPane.OK_OPTION);

                                                                                    if (valorRetornado == JOptionPane.CANCEL_OPTION && cliente.getServicios().size() > 0)
                                                                                        cs.guardarCliente(cliente);

                                                                                }

                                                                            }

                                                                        } while ((!validarEmail(email) && valorRetornado == JOptionPane.OK_OPTION) || valorRetornado == JOptionPane.CLOSED_OPTION);

                                                                    }

                                                                }

                                                            } while ((!validarTelefono(strCelular) && valorRetornado == JOptionPane.OK_OPTION) || valorRetornado == JOptionPane.CLOSED_OPTION);

                                                        }

                                                    }

                                                } while ((!validarTelefono(strTelefono) && valorRetornado == JOptionPane.OK_OPTION) || valorRetornado == JOptionPane.CLOSED_OPTION);

                                            }

                                        }

                                    } while ((!validarTexto1(razonSocial) && valorRetornado == JOptionPane.OK_OPTION) || valorRetornado == JOptionPane.CLOSED_OPTION);

                                }

                            }

                        } while ((!validarCUIT(strCUIT) && valorRetornado == JOptionPane.OK_OPTION) || valorRetornado == JOptionPane.CLOSED_OPTION);

                        valorRetornado = JOptionPane.CANCEL_OPTION;

                    }

                    case "2" -> {

                        strCUIT = "0";
                        razonSocial = "";
                        strTelefono = "0";
                        strCelular = "0";
                        email = "";

                        do {

                            ingresarNumero = new IngresarNumero("CUIT:", "0", 60, 100, 100, true);
                            valorRetornado = JOptionPane.showOptionDialog(null, ingresarNumero, "Ingrese CUIT", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);

                            if (JOptionPane.OK_OPTION == valorRetornado) {

                                strCUIT = ingresarNumero.getNumero().trim();

                                if (!validarCUIT(strCUIT)) {

                                    JOptionPane.showMessageDialog(null, "Ingrese un CUIT Correcto.");

                                } else if (cs.buscarClientePorCUIT(Long.valueOf(ingresarNumero.getNumero().trim())).isEmpty()) {

                                    JOptionPane.showMessageDialog(null, "ElCUIT ingresado no existe.");


                                } else {

                                    do {

                                        cliente = cs.buscarClientePorCUIT(Long.valueOf(ingresarNumero.getNumero().trim())).get();
                                        datosContacto = cliente.getDatosContacto();

                                        ingresarTexto = new IngresarTexto("Nombre:", cliente.getRazonSocial(), 90, 200, 0);
                                        valorRetornado = JOptionPane.showOptionDialog(null, ingresarTexto, "Ingrese Razon Social", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);

                                        if (JOptionPane.OK_OPTION == valorRetornado) {

                                            razonSocial = ingresarTexto.getTexto().trim();

                                            if (!validarTexto1(razonSocial)) {

                                                JOptionPane.showMessageDialog(null, "Ingrese una Razón Social Correcta.");

                                            } else {

                                                do {

                                                    ingresarNumero = new IngresarNumero("Telefono:", cliente.getDatosContacto().getTelefono().toString(), 70, 100, 0, true);
                                                    valorRetornado = JOptionPane.showOptionDialog(null, ingresarNumero, "Ingrese Telefono", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);

                                                    if (JOptionPane.OK_OPTION == valorRetornado) {

                                                        strTelefono = ingresarNumero.getNumero().trim();

                                                        if (!validarTelefono(strTelefono)) {

                                                            JOptionPane.showMessageDialog(null, "Ingrese un Telefono Correcto.");

                                                        } else {

                                                            do {

                                                                ingresarNumero = new IngresarNumero("Celular:", cliente.getDatosContacto().getCelular().toString(), 70, 100, 0, true);
                                                                valorRetornado = JOptionPane.showOptionDialog(null, ingresarNumero, "Ingrese Celular", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);

                                                                if (JOptionPane.OK_OPTION == valorRetornado) {

                                                                    strCelular = ingresarNumero.getNumero().trim();

                                                                    if (!validarTelefono(strCelular)) {

                                                                        JOptionPane.showMessageDialog(null, "Ingrese un Celular Correcto.");

                                                                    } else {

                                                                        do {

                                                                            ingresarTexto = new IngresarTexto("Email:", cliente.getDatosContacto().getEmail(), 70, 250, 0);
                                                                            valorRetornado = JOptionPane.showOptionDialog(null, ingresarTexto, "Ingrese Email", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);

                                                                            if (JOptionPane.OK_OPTION == valorRetornado) {

                                                                                email = ingresarTexto.getTexto().trim();

                                                                                if (!validarEmail(email)) {

                                                                                    JOptionPane.showMessageDialog(null, "Ingrese un Email Correcto.");

                                                                                } else {

                                                                                    datosContacto.setTelefono(Long.valueOf(strTelefono));
                                                                                    datosContacto.setCelular(Long.valueOf(strCelular));
                                                                                    datosContacto.setEmail(email);
                                                                                    cliente.setCuit(Long.valueOf(strCUIT));
                                                                                    cliente.setRazonSocial(razonSocial);
                                                                                    cliente.setDatosContacto(datosContacto);

                                                                                    valorRetornado = JOptionPane.showOptionDialog(null, "Desea recargar los Servicios?", "Ingrese Servicios", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);

                                                                                    if (JOptionPane.OK_OPTION == valorRetornado) {

                                                                                        cliente.getServicios().clear();

                                                                                        do {

                                                                                            do {

                                                                                                ingresarServicio = new IngresarServicio(em, null);
                                                                                                valorRetornado = JOptionPane.showOptionDialog(null, ingresarServicio, "Ingrese Servicio", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);

                                                                                                if (JOptionPane.OK_OPTION == valorRetornado) {

                                                                                                    cliente.addServicio(ingresarServicio.getServicio());

                                                                                                }

                                                                                            } while (valorRetornado == JOptionPane.CLOSED_OPTION);

                                                                                            if (valorRetornado == JOptionPane.OK_OPTION && cliente.getServicios().size() > 0 && ingresarServicio.getCantidadElementosCombo() != 0) {

                                                                                                valorRetornado = JOptionPane.showOptionDialog(null, "Desea agregar otro Servicio?", "Ingrese Servicio", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);

                                                                                            }

                                                                                        } while (valorRetornado == JOptionPane.OK_OPTION);

                                                                                    }

                                                                                    if (valorRetornado == JOptionPane.CANCEL_OPTION && cliente.getServicios().size() > 0)
                                                                                        cs.guardarCliente(cliente);

                                                                                }

                                                                            }

                                                                        } while ((!validarEmail(email) && valorRetornado == JOptionPane.OK_OPTION) || valorRetornado == JOptionPane.CLOSED_OPTION);

                                                                    }

                                                                }

                                                            }
                                                            while ((!validarTelefono(strCelular) && valorRetornado == JOptionPane.OK_OPTION) || valorRetornado == JOptionPane.CLOSED_OPTION);

                                                        }

                                                    }

                                                }
                                                while ((!validarTelefono(strTelefono) && valorRetornado == JOptionPane.OK_OPTION) || valorRetornado == JOptionPane.CLOSED_OPTION);

                                            }

                                        }

                                    } while ((!validarTexto1(razonSocial) && valorRetornado == JOptionPane.OK_OPTION) || valorRetornado == JOptionPane.CLOSED_OPTION);

                                }

                            }

                        } while ((!validarCUIT(strCUIT) && valorRetornado == JOptionPane.OK_OPTION) || valorRetornado == JOptionPane.CLOSED_OPTION);

                        valorRetornado = JOptionPane.CANCEL_OPTION;

                    }
                    case "3" -> {

                        strCUIT = "0";

                        do {

                            ingresarNumero = new IngresarNumero("CUIT:", "0", 60, 100, 100, true);
                            valorRetornado = JOptionPane.showOptionDialog(null, ingresarNumero, "Ingrese CUIT", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

                            if (JOptionPane.OK_OPTION == valorRetornado) {

                                strCUIT = ingresarNumero.getNumero().trim();

                                if (!validarCUIT(strCUIT)) {

                                    JOptionPane.showMessageDialog(null, "Ingrese un CUIT Correcto.");

                                } else if (cs.buscarClientePorCUIT(Long.valueOf(ingresarNumero.getNumero().trim())).isEmpty()) {

                                    JOptionPane.showMessageDialog(null, "El CUIT ingresado no existe.");

                                } else {

                                    valorRetornado = JOptionPane.showOptionDialog(null, "Está seguro de borrar Cliente?", "Eliminar Cliente", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);

                                    if (JOptionPane.OK_OPTION == valorRetornado) {

                                        cliente = cs.buscarClientePorCUIT(Long.valueOf(ingresarNumero.getNumero().trim())).get();
                                        cs.eliminarCliente(cliente.getId());

                                    }

                                }

                            }

                        } while (!validarCUIT(strCUIT) && (valorRetornado == JOptionPane.CLOSED_OPTION || valorRetornado == JOptionPane.OK_OPTION));

                        valorRetornado = JOptionPane.CANCEL_OPTION;

                    }

                    case "4" -> {
                        mostrarClientes = new MostrarClientes(em);
                        JOptionPane.showOptionDialog(null, mostrarClientes, "Listado Clientes", JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new Object[]{"Aceptar"}, "Aceptar");

                        valorRetornado = JOptionPane.CANCEL_OPTION;
                    }

                    case "5" ->
                            valorRetornado = JOptionPane.showOptionDialog(null, "Está seguro que desea salir?", "Salir",
                                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

                }

            }

        } while (valorRetornado != JOptionPane.OK_OPTION || !

                validarMenuOpcion(opcionMenu));


    }

    private boolean validarMenuOpcion(String args) {
        return args.matches("^[1-5]$");
    }

    private boolean validarTelefono(String telefono) {

        return telefono.matches("^[1-9]\\d{9}$");

    }

    private boolean validarCUIT(String cuit) {

        return cuit.matches("^[1-9]\\d{10}$");

    }

    private boolean validarEmail(String email) {

        return email.matches("^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-zA-Z]{2,})$");

    }

    private boolean validarTexto1(String denominacion) {

        return denominacion.matches("^([A-ZÁÉÍÓÚÜÑa-záéíóüñ0-9]([A-ZÁÉÍÓÚÜÑa-záéíóüñ0-9]+))([ ][A-ZÁÉÍÓÚÜÑa-záéíóüñ0-9]([A-ZÁÉÍÓÚÜÑa-záéíóüñ0-9]*)){0,5}");

    }

}
