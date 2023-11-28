package ar.com.jg.view;

import ar.com.jg.model.DatosContacto;
import ar.com.jg.model.Especialidad;
import ar.com.jg.model.Tecnico;
import ar.com.jg.model.enums.ComunicacionTecnicoCliente;
import ar.com.jg.services.TecnicoService;
import ar.com.jg.services.TecnicoServiceImpl;
import ar.com.jg.view.accessories.*;
import jakarta.persistence.EntityManager;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;


public class TecnicoForm {

    private MenuForm menuForm;

    private IngresarTexto ingresarTexto;
    private IngresarNumero ingresarNumero;
    private IngresarComunicacionTecnicoCliente ingresarComunicacionTecnicoCliente;
    private IngresarEspecialidad ingresarEspecialidad;
    private MostrarTecnicos mostrarTecnicos;

    private int valorRetornado;
    private String opcionMenu;
    private String strLegajo;
    private String nombre;
    private String apellido;
    private String usuario;
    private String password;
    private ComunicacionTecnicoCliente comunicacionTecnicoCliente;
    private String strTelefono;
    private String strCelular;
    private String email;

    private TecnicoService ts;
    Tecnico tecnico;
    DatosContacto datosContacto;
    @Getter
    @Setter
    Especialidad especialidad;

    private EntityManager em;

    public TecnicoForm(EntityManager em) {

        ts = new TecnicoServiceImpl(em);

        this.em = em;

        init();

    }

    private void init() {

        String menuOperadorMesaAyuda = """
                <html>MENU OPCIONES:<br><br>
                               
                1 - INGRESAR UN TECNICO.<br>
                2 - EDITAR UN TECNICO.<br>
                3 - ELIMINAR UN TECNICO.<br>
                4 - LISTAR TECNICOS.<br>
                5 - SALIR.<br><br></html>""";

        do {

            menuForm = new MenuForm(menuOperadorMesaAyuda, 200, 0, 5);
            valorRetornado = JOptionPane.showOptionDialog(null, menuForm, "Seleccione una Opcion", JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new Object[]{"Aceptar"}, "Aceptar");

            if (JOptionPane.OK_OPTION == valorRetornado) {

                opcionMenu = menuForm.getOption().trim();

                if (!validarMenuOpcion(opcionMenu)) JOptionPane.showMessageDialog(null, "Ingrese una Opción Correcta.");

                switch (opcionMenu) {

                    case "1" -> {

                        strLegajo = "0";
                        nombre = "";
                        apellido = "";
                        usuario = "";
                        password = "";
                        strTelefono = "0";
                        strCelular = "0";
                        email = "";

                        do {

                            ingresarNumero = new IngresarNumero("Legajo:", "0", 60, 100, 100, true);
                            valorRetornado = JOptionPane.showOptionDialog(null, ingresarNumero, "Ingrese Legajo", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);

                            if (JOptionPane.OK_OPTION == valorRetornado) {

                                strLegajo = ingresarNumero.getNumero().trim();

                                if (!validarNumero(strLegajo)) {

                                    JOptionPane.showMessageDialog(null, "Ingrese un Legajo Correcto.");

                                } else {

                                    do {

                                        ingresarTexto = new IngresarTexto("Nombre:", "", 70, 150, 0);
                                        valorRetornado = JOptionPane.showOptionDialog(null, ingresarTexto, "Ingrese Nombre", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);

                                        if (JOptionPane.OK_OPTION == valorRetornado) {

                                            nombre = ingresarTexto.getTexto().trim();

                                            if (!validarNombre(nombre)) {

                                                JOptionPane.showMessageDialog(null, "Ingrese un Nombre Correcto.");

                                            } else {

                                                do {

                                                    ingresarTexto = new IngresarTexto("Apellido:", "", 70, 150, 0);
                                                    valorRetornado = JOptionPane.showOptionDialog(null, ingresarTexto, "Ingrese Apellido", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);

                                                    if (JOptionPane.OK_OPTION == valorRetornado) {

                                                        apellido = ingresarTexto.getTexto().trim();

                                                        if (!validarNombre(apellido)) {

                                                            JOptionPane.showMessageDialog(null, "Ingrese un Apellido Correcto.");

                                                        } else {

                                                            do {

                                                                ingresarTexto = new IngresarTexto("Usuario:", "", 70, 150, 0);
                                                                valorRetornado = JOptionPane.showOptionDialog(null, ingresarTexto, "Ingrese Usuario", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);

                                                                if (JOptionPane.OK_OPTION == valorRetornado) {

                                                                    usuario = ingresarTexto.getTexto().trim();

                                                                    if (!validarTexto1(usuario)) {

                                                                        JOptionPane.showMessageDialog(null, "Ingrese un Usuario Correcto.");

                                                                    } else {

                                                                        do {

                                                                            ingresarTexto = new IngresarTexto("Password:", "", 70, 150, 0);
                                                                            valorRetornado = JOptionPane.showOptionDialog(null, ingresarTexto, "Ingrese Password", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);

                                                                            if (JOptionPane.OK_OPTION == valorRetornado) {

                                                                                password = ingresarTexto.getTexto().trim();

                                                                                if (!validarTexto1(password)) {

                                                                                    JOptionPane.showMessageDialog(null, "Ingrese un Password Correcto.");

                                                                                } else {

                                                                                    do {

                                                                                        ingresarComunicacionTecnicoCliente = new IngresarComunicacionTecnicoCliente();
                                                                                        valorRetornado = JOptionPane.showOptionDialog(null, ingresarComunicacionTecnicoCliente, "Ingrese Medio Información", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);

                                                                                        if (JOptionPane.OK_OPTION == valorRetornado) {

                                                                                            comunicacionTecnicoCliente = ingresarComunicacionTecnicoCliente.getComunicacionTecnicoCliente();

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
                                                                                                                                tecnico = new Tecnico();
                                                                                                                                tecnico.setLegajo(Long.valueOf(strLegajo));
                                                                                                                                tecnico.setNombre(nombre);
                                                                                                                                tecnico.setApellido(apellido);
                                                                                                                                tecnico.setUsuario(usuario);
                                                                                                                                tecnico.setPassword(password);
                                                                                                                                tecnico.setComunicacionTecnicoCliente(comunicacionTecnicoCliente);
                                                                                                                                tecnico.setDatosContacto(datosContacto);

                                                                                                                                do
                                                                                                                                {

                                                                                                                                    do
                                                                                                                                    {

                                                                                                                                        ingresarEspecialidad = new IngresarEspecialidad(em);
                                                                                                                                        valorRetornado = JOptionPane.showOptionDialog(null, ingresarEspecialidad, "Ingrese Especialidad", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);

                                                                                                                                        if (JOptionPane.OK_OPTION == valorRetornado) {

                                                                                                                                            if (ingresarEspecialidad.getEspecialidad() != null) {

                                                                                                                                                tecnico.addEspecialidad(ingresarEspecialidad.getEspecialidad());

                                                                                                                                            }

                                                                                                                                        }

                                                                                                                                    } while (valorRetornado == JOptionPane.CLOSED_OPTION);

                                                                                                                                    if (valorRetornado == JOptionPane.OK_OPTION && tecnico.getEspecialidades().size() > 0 && ingresarEspecialidad.getCantidadElementosCombo() != 0) {

                                                                                                                                        valorRetornado = JOptionPane.showOptionDialog(null, "Desea agregar otra especialidad?", "Ingrese Especialidad", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);

                                                                                                                                    }

                                                                                                                                } while (valorRetornado == JOptionPane.OK_OPTION);

                                                                                                                                if (valorRetornado == JOptionPane.CANCEL_OPTION && tecnico.getEspecialidades().size() > 0)
                                                                                                                                    ts.guardarTecnico(tecnico);

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

                                                                                    } while (valorRetornado == JOptionPane.CLOSED_OPTION);
                                                                                }

                                                                            }

                                                                        } while ((!validarTexto1(password) && valorRetornado == JOptionPane.OK_OPTION) || valorRetornado == JOptionPane.CLOSED_OPTION);

                                                                    }

                                                                }

                                                            } while ((!validarTexto1(usuario) && valorRetornado == JOptionPane.OK_OPTION) || valorRetornado == JOptionPane.CLOSED_OPTION);

                                                        }

                                                    }

                                                } while ((!validarNombre(apellido) && valorRetornado == JOptionPane.OK_OPTION) || valorRetornado == JOptionPane.CLOSED_OPTION);

                                            }

                                        }

                                    } while ((!validarNombre(nombre) && valorRetornado == JOptionPane.OK_OPTION) || valorRetornado == JOptionPane.CLOSED_OPTION);

                                }

                            }

                        } while ((!validarNumero(strLegajo) && valorRetornado == JOptionPane.OK_OPTION) || valorRetornado == JOptionPane.CLOSED_OPTION);

                        valorRetornado = JOptionPane.CANCEL_OPTION;

                    }

                    case "2" -> {

                        strLegajo = "0";
                        nombre = "";
                        apellido = "";
                        usuario = "";
                        password = "";
                        strTelefono = "0";
                        strCelular = "0";
                        email = "";

                        do {

                            ingresarNumero = new IngresarNumero("Legajo:", "0", 60, 100, 100, true);
                            valorRetornado = JOptionPane.showOptionDialog(null, ingresarNumero, "Ingrese Legajo", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);

                            if (JOptionPane.OK_OPTION == valorRetornado) {

                                strLegajo = ingresarNumero.getNumero().trim();

                                if (!validarNumero(strLegajo)) {

                                    JOptionPane.showMessageDialog(null, "Ingrese un Legajo Correcto.");

                                } else if (ts.buscarTecnicoPorLegajo(Long.valueOf(ingresarNumero.getNumero().trim())).isEmpty()) {

                                    JOptionPane.showMessageDialog(null, "El Legajo ingresado no existe.");

                                } else {

                                    do {

                                        tecnico = ts.buscarTecnicoPorLegajo(Long.valueOf(ingresarNumero.getNumero().trim())).get();
                                        datosContacto = tecnico.getDatosContacto();

                                        ingresarTexto = new IngresarTexto("Nombre:", tecnico.getNombre(), 70, 150, 0);
                                        valorRetornado = JOptionPane.showOptionDialog(null, ingresarTexto, "Ingrese Nombre", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);

                                        if (JOptionPane.OK_OPTION == valorRetornado) {

                                            nombre = ingresarTexto.getTexto().trim();

                                            if (!validarNombre(nombre)) {

                                                JOptionPane.showMessageDialog(null, "Ingrese un Nombre Correcto.");

                                            } else {

                                                do {

                                                    ingresarTexto = new IngresarTexto("Apellido:", tecnico.getApellido(), 70, 150, 0);
                                                    valorRetornado = JOptionPane.showOptionDialog(null, ingresarTexto, "Ingrese Apellido", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);

                                                    if (JOptionPane.OK_OPTION == valorRetornado) {

                                                        apellido = ingresarTexto.getTexto().trim();

                                                        if (!validarNombre(apellido)) {

                                                            JOptionPane.showMessageDialog(null, "Ingrese un Apellido Correcto.");

                                                        } else {

                                                            do {

                                                                ingresarTexto = new IngresarTexto("Usuario:", tecnico.getUsuario(), 70, 150, 0);
                                                                valorRetornado = JOptionPane.showOptionDialog(null, ingresarTexto, "Ingrese Usuario", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);

                                                                if (JOptionPane.OK_OPTION == valorRetornado) {

                                                                    usuario = ingresarTexto.getTexto().trim();

                                                                    if (!validarTexto1(usuario)) {

                                                                        JOptionPane.showMessageDialog(null, "Ingrese un Usuario Correcto.");

                                                                    } else {

                                                                        do {

                                                                            ingresarTexto = new IngresarTexto("Password:", tecnico.getPassword(), 70, 150, 0);
                                                                            valorRetornado = JOptionPane.showOptionDialog(null, ingresarTexto, "Ingrese Password", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);

                                                                            if (JOptionPane.OK_OPTION == valorRetornado) {

                                                                                password = ingresarTexto.getTexto().trim();

                                                                                if (!validarTexto1(password)) {

                                                                                    JOptionPane.showMessageDialog(null, "Ingrese un Password Correcto.");

                                                                                } else {

                                                                                    do {

                                                                                        ingresarComunicacionTecnicoCliente = new IngresarComunicacionTecnicoCliente();
                                                                                        valorRetornado = JOptionPane.showOptionDialog(null, ingresarComunicacionTecnicoCliente, "Ingrese Medio Información", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);

                                                                                        if (JOptionPane.OK_OPTION == valorRetornado) {

                                                                                            comunicacionTecnicoCliente = ingresarComunicacionTecnicoCliente.getComunicacionTecnicoCliente();


                                                                                            do {

                                                                                                ingresarNumero = new IngresarNumero("Telefono:", tecnico.getDatosContacto().getTelefono().toString(), 70, 100, 0, true);
                                                                                                valorRetornado = JOptionPane.showOptionDialog(null, ingresarNumero, "Ingrese Telefono", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);

                                                                                                if (JOptionPane.OK_OPTION == valorRetornado) {

                                                                                                    strTelefono = ingresarNumero.getNumero().trim();

                                                                                                    if (!validarTelefono(strTelefono)) {

                                                                                                        JOptionPane.showMessageDialog(null, "Ingrese un Telefono Correcto.");

                                                                                                    } else {

                                                                                                        do {

                                                                                                            ingresarNumero = new IngresarNumero("Celular:", tecnico.getDatosContacto().getCelular().toString(), 70, 100, 0, true);
                                                                                                            valorRetornado = JOptionPane.showOptionDialog(null, ingresarNumero, "Ingrese Celular", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);

                                                                                                            if (JOptionPane.OK_OPTION == valorRetornado) {

                                                                                                                strCelular = ingresarNumero.getNumero().trim();

                                                                                                                if (!validarTelefono(strCelular)) {

                                                                                                                    JOptionPane.showMessageDialog(null, "Ingrese un Celular Correcto.");

                                                                                                                } else {

                                                                                                                    do {

                                                                                                                        ingresarTexto = new IngresarTexto("Email:", tecnico.getDatosContacto().getEmail(), 70, 250, 0);
                                                                                                                        valorRetornado = JOptionPane.showOptionDialog(null, ingresarTexto, "Ingrese Email", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);

                                                                                                                        if (JOptionPane.OK_OPTION == valorRetornado) {

                                                                                                                            email = ingresarTexto.getTexto().trim();

                                                                                                                            if (!validarEmail(email)) {

                                                                                                                                JOptionPane.showMessageDialog(null, "Ingrese un Email Correcto.");

                                                                                                                            } else {

                                                                                                                                datosContacto.setTelefono(Long.valueOf(strTelefono));
                                                                                                                                datosContacto.setCelular(Long.valueOf(strCelular));
                                                                                                                                datosContacto.setEmail(email);
                                                                                                                                tecnico.setLegajo(Long.valueOf(strLegajo));
                                                                                                                                tecnico.setNombre(nombre);
                                                                                                                                tecnico.setApellido(apellido);
                                                                                                                                tecnico.setUsuario(usuario);
                                                                                                                                tecnico.setPassword(password);
                                                                                                                                tecnico.setComunicacionTecnicoCliente(comunicacionTecnicoCliente);
                                                                                                                                tecnico.setDatosContacto(datosContacto);

                                                                                                                                valorRetornado = JOptionPane.showOptionDialog(null, "Desea recargar las Especialidades?", "Ingrese Especialidad", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);

                                                                                                                                if (JOptionPane.OK_OPTION == valorRetornado) {

                                                                                                                                    tecnico.getEspecialidades().clear();

                                                                                                                                    do
                                                                                                                                    {

                                                                                                                                        do
                                                                                                                                        {

                                                                                                                                            ingresarEspecialidad = new IngresarEspecialidad(em);
                                                                                                                                            valorRetornado = JOptionPane.showOptionDialog(null, ingresarEspecialidad, "Ingrese Especialidad", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);

                                                                                                                                            if (JOptionPane.OK_OPTION == valorRetornado) {

                                                                                                                                                tecnico.addEspecialidad(ingresarEspecialidad.getEspecialidad());

                                                                                                                                            }

                                                                                                                                        } while (valorRetornado == JOptionPane.CLOSED_OPTION);

                                                                                                                                        if (valorRetornado == JOptionPane.OK_OPTION && tecnico.getEspecialidades().size() > 0 && ingresarEspecialidad.getCantidadElementosCombo() != 0) {

                                                                                                                                            valorRetornado = JOptionPane.showOptionDialog(null, "Desea agregar otra especialidad?", "Ingrese Especialidad", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);

                                                                                                                                        }

                                                                                                                                    } while (valorRetornado == JOptionPane.OK_OPTION);

                                                                                                                                }

                                                                                                                                if (valorRetornado == JOptionPane.CANCEL_OPTION && tecnico.getEspecialidades().size() > 0)
                                                                                                                                    ts.guardarTecnico(tecnico);


                                                                                                                            }

                                                                                                                        }

                                                                                                                    } while (!validarEmail(email) && (valorRetornado == JOptionPane.CLOSED_OPTION || valorRetornado == JOptionPane.OK_OPTION));

                                                                                                                }

                                                                                                            }

                                                                                                        } while (!validarTelefono(strCelular) && (valorRetornado == JOptionPane.CLOSED_OPTION || valorRetornado == JOptionPane.OK_OPTION));

                                                                                                    }

                                                                                                }

                                                                                            } while (!validarTelefono(strTelefono) && (valorRetornado == JOptionPane.CLOSED_OPTION || valorRetornado == JOptionPane.OK_OPTION));

                                                                                        }

                                                                                    } while (valorRetornado == JOptionPane.CLOSED_OPTION);
                                                                                }

                                                                            }

                                                                        } while (!validarTexto1(password) && (valorRetornado == JOptionPane.CLOSED_OPTION || valorRetornado == JOptionPane.OK_OPTION));

                                                                    }

                                                                }

                                                            } while (!validarTexto1(usuario) && (valorRetornado == JOptionPane.CLOSED_OPTION || valorRetornado == JOptionPane.OK_OPTION));

                                                        }

                                                    }

                                                } while (!validarNombre(apellido) && (valorRetornado == JOptionPane.CLOSED_OPTION || valorRetornado == JOptionPane.OK_OPTION));

                                            }

                                        }

                                    } while (!validarNombre(nombre) && (valorRetornado == JOptionPane.CLOSED_OPTION || valorRetornado == JOptionPane.OK_OPTION));

                                }

                            }

                        } while (!validarNumero(strLegajo) && (valorRetornado == JOptionPane.CLOSED_OPTION || valorRetornado == JOptionPane.OK_OPTION));

                        valorRetornado = JOptionPane.CANCEL_OPTION;

                    }
                    case "3" -> {

                        strLegajo = "0";

                        do {

                            ingresarNumero = new IngresarNumero("Legajo:", "0", 60, 100, 100, true);
                            valorRetornado = JOptionPane.showOptionDialog(null, ingresarNumero, "Ingrese Legajo", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

                            if (JOptionPane.OK_OPTION == valorRetornado) {

                                strLegajo = ingresarNumero.getNumero().trim();

                                if (!validarNumero(strLegajo)) {

                                    JOptionPane.showMessageDialog(null, "Ingrese un Legajo Correcto.");

                                } else if (ts.buscarTecnicoPorLegajo(Long.valueOf(ingresarNumero.getNumero().trim())).isEmpty()) {

                                    JOptionPane.showMessageDialog(null, "El Legajo ingresado no existe.");

                                } else {

                                    valorRetornado = JOptionPane.showOptionDialog(null, "Está seguro de borrar Técnico?", "Eliminar Técnicoo", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);

                                    if (JOptionPane.OK_OPTION == valorRetornado) {

                                        tecnico = ts.buscarTecnicoPorLegajo(Long.valueOf(ingresarNumero.getNumero().trim())).get();
                                        ts.eliminarTecnico(tecnico.getId());

                                    }

                                }

                            }

                        } while (!validarNumero(strLegajo) && (valorRetornado == JOptionPane.CLOSED_OPTION || valorRetornado == JOptionPane.OK_OPTION));

                        valorRetornado = JOptionPane.CANCEL_OPTION;

                    }

                    case "4" -> {
                        mostrarTecnicos = new MostrarTecnicos(em);
                        JOptionPane.showOptionDialog(null, mostrarTecnicos, "Listado Técnicos", JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new Object[]{"Aceptar"}, "Aceptar");

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

    private boolean validarNumero(String numero) {

        return numero.matches("^[1-9]\\d{0,8}$");

    }

    private boolean validarTelefono(String telefono) {

        return telefono.matches("^[1-9]\\d{9}$");

    }

    private boolean validarEmail(String email) {

        return email.matches("^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-zA-Z]{2,})$");

    }

    private boolean validarTexto1(String denominacion) {

        return denominacion.matches("^([A-ZÁÉÍÓÚÜÑa-záéíóüñ0-9]([A-ZÁÉÍÓÚÜÑa-záéíóüñ0-9]+))([ ][A-ZÁÉÍÓÚÜÑa-záéíóüñ0-9]([A-ZÁÉÍÓÚÜÑa-záéíóüñ0-9]*)){0,5}");

    }

    private boolean validarNombre(String denominacion) {

        return denominacion.matches("^([A-ZÁÉÍÓÚÜÑ]([a-záéíóüñ]+))([ ][A-ZÁÉÍÓÚÜÑ]([a-záéíóüñ]*)){0,5}");

    }

}
