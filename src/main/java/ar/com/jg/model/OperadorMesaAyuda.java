package ar.com.jg.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "operadores_mesa_ayuda")
@AttributeOverride(name = "id", column = @Column(name = "id_operador_mesa_ayuda"))
//@NoArgsConstructor
@Getter @Setter
//@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class OperadorMesaAyuda extends Empleado {

    @OneToMany(mappedBy = "operador", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    private List<ReporteIncidencia> reportesIncidencia;

    public OperadorMesaAyuda() {

        this.reportesIncidencia = new ArrayList<>();

    }

    public OperadorMesaAyuda(Long legajo, @NonNull String nombre, @NonNull String apellido, @NonNull String usuario, @NonNull String password) {

        this();
        this.legajo = legajo;
        this.nombre = nombre;
        this.apellido = apellido;
        this.usuario = usuario;
        this.password = password;

    }

    public OperadorMesaAyuda addReporteIncidencia(ReporteIncidencia reporte){

        this.reportesIncidencia.add(reporte);
        reporte.setOperador(this);
        return this;

    }

    public OperadorMesaAyuda removeReporteIncidencia(ReporteIncidencia reporte){

        this.reportesIncidencia.remove(reporte);
        reporte.setOperador(null);
        return this;

    }

    @Override
    public String toString() {

        return "Legajo: " + legajo + " - " + nombre + ", " + apellido;

    }

}
