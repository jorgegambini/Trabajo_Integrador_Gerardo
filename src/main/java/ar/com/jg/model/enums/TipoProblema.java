package ar.com.jg.model.enums;


import lombok.Getter;

public enum TipoProblema {

    Basico(180),
    Intermedio(300),
    Complejo(600);

    @Getter
    private final int tiempoResolucion;

    private TipoProblema(int tiempoResolucion) {

        this.tiempoResolucion = tiempoResolucion;

    }

}
