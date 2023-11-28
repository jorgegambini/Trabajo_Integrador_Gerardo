package ar.com.jg.model;

import ar.com.jg.model.enums.EstadoProblema;
import ar.com.jg.model.enums.TipoProblema;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;


@Entity
@Table(name = "reportes_incidencia")
@AttributeOverride(name = "id", column = @Column(name = "id_reporte_incidencia"))
@Getter @Setter
@NoArgsConstructor @RequiredArgsConstructor
@ToString(callSuper = true, includeFieldNames = false, of = {"fechaAlta", "descripcionProblema", "tipoProblema", "tiempoEstimadoResolucion", "fechaPosibleResolucion", "estado", "observacionesTecnico"})
@EqualsAndHashCode(callSuper = true)
public class ReporteIncidencia extends EntidadId{


    @Column(name = "fecha_alta", nullable = false)
    //@Temporal(TemporalType.DATE)
    @NonNull
    @EqualsAndHashCode.Exclude
    private LocalDate fechaAlta;
    @Column(name = "codigo_reporte", unique = true, nullable = false)
    @NonNull
    @EqualsAndHashCode.Exclude
    private String codigoReporte;
    @ManyToOne
    @JoinColumn(name = "id_operador_mesa_ayuda", nullable = false)
    @EqualsAndHashCode.Exclude
    private OperadorMesaAyuda operador; //Many2One
    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    @EqualsAndHashCode.Exclude
    private Cliente cliente; //Many2One
    @ManyToOne
    @JoinColumn(name = "id_servicio", nullable = false)
    @EqualsAndHashCode.Exclude
    private Servicio servicio; //Many2One
    @ManyToOne
    @JoinColumn(name = "id_tecnico", nullable = false)
    @EqualsAndHashCode.Exclude
    private Tecnico tecnico; //Many2One
    @Column(name = "tipo_problema", nullable = false)
    @Enumerated(EnumType.STRING)
    @NonNull
    @EqualsAndHashCode.Exclude
    private TipoProblema tipoProblema; //Basico, Intermedio, Complejo
    @Column(name = "descripcion_problema", nullable = false)
    @NonNull
    @EqualsAndHashCode.Exclude
    private String descripcionProblema;
    @Column(name = "tiempo_estimado_resolucion")//, nullable = false
    @EqualsAndHashCode.Exclude
    private Integer tiempoEstimadoResolucion; //en minutos
    @Column(name = "fecha_posible_resolucion")//, nullable = false
    //@Temporal(TemporalType.DATE)
    @NonNull
    @EqualsAndHashCode.Exclude
    private LocalDate fechaPosibleResolucion;
    @Column(name = "estado_problema", nullable = false)
    @Enumerated(EnumType.STRING)
    @NonNull
    @EqualsAndHashCode.Exclude
    private EstadoProblema estadoProblema; //Pendiente, Procesado, Resuelto, Cancelado
    @Column(name = "observaciones_tecnico")//, nullable = false
    @EqualsAndHashCode.Exclude
    private String observacionesTecnico;

}
