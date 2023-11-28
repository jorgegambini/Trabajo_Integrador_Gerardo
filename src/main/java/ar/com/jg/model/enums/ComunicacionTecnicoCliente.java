package ar.com.jg.model.enums;


import lombok.Getter;

public enum ComunicacionTecnicoCliente {

    Email(0),
    WhatsApp(1);

    @Getter
    private final int idComunicacionTecnicoCliente;

    private ComunicacionTecnicoCliente(int idComunicacionTecnicoCliente) {

        this.idComunicacionTecnicoCliente = idComunicacionTecnicoCliente;

    }

}
