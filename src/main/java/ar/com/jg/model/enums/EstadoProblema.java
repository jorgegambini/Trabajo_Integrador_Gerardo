package ar.com.jg.model.enums;


import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum EstadoProblema {

    Pendiente(0, false),
    Procesado(1, true),
    Resuelto(2, true),
    Cancelado(3, false);

    @Getter
    private final int idEstadoProblema;
    @Getter
    private final boolean optionTecnico;

    private EstadoProblema(int idEstadoProblema, boolean optionTecnico) {

        this.idEstadoProblema = idEstadoProblema;
        this.optionTecnico = optionTecnico;

    }

    public static List<EstadoProblema> getOptionsTecnico() {

        return Arrays.stream(values())
                .filter(EstadoProblema::isOptionTecnico)
                .collect(Collectors.toList());

    }

}
