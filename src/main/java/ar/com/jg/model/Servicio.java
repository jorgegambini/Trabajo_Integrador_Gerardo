package ar.com.jg.model;

import jakarta.persistence.*;
import lombok.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "servicios")
@AttributeOverride(name = "id", column = @Column(name = "id_servicio"))
//@NoArgsConstructor @RequiredArgsConstructor
@Getter @Setter
@EqualsAndHashCode(callSuper = true)
public class Servicio extends EntidadId{

    @Column(nullable = false)
    @NonNull
    @EqualsAndHashCode.Exclude
    private String denominacion;
    @ManyToMany(mappedBy = "servicios")
    @EqualsAndHashCode.Exclude
    private List<Cliente> clientes; //Many2Many
    @OneToMany(mappedBy = "servicio", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    private List<ReporteIncidencia> reportesIncidencia;
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})//mappedBy = "servicio", , orphanRemoval = true
    @JoinTable(name = "servicios_especialidades", joinColumns = @JoinColumn(name = "id_servicio"), inverseJoinColumns = @JoinColumn(name = "id_especialidad"), uniqueConstraints = @UniqueConstraint(columnNames = {"id_servicio", "id_especialidad"}))
    @EqualsAndHashCode.Exclude
    private List<Especialidad> especialidades;

    public Servicio() {

        this.especialidades = new ArrayList<>();
        this.clientes = new ArrayList<>();
        this.reportesIncidencia = new ArrayList<>();

    }

    public Servicio(@NonNull String denominacion) {

        this();
        this.denominacion = denominacion;

    }

    public Servicio addReporteIncidencia(ReporteIncidencia reporte){

        this.reportesIncidencia.add(reporte);
        reporte.setServicio(this);
        return this;

    }

    public Servicio removeReporteIncidencia(ReporteIncidencia reporte){

        this.reportesIncidencia.remove(reporte);
        reporte.setServicio(null);
        return this;

    }

    public Servicio addEspecialidad(Especialidad especialidad){

        if(!this.especialidades.contains(especialidad)) {

            this.especialidades.add(especialidad);
            //especialidad.setServicio(this);

        }else{

            JOptionPane.showMessageDialog(null, "La Especialidad ya fue agregada con anterioridad.");

        }

        return this;

    }

    public Servicio removeEspecialidad(Especialidad especialidad){

        if(this.especialidades.contains(especialidad)) {

            this.especialidades.remove(especialidad);
            //especialidad.setServicio(null);

        }else{

            JOptionPane.showMessageDialog(null, "La Especialidad ya fue quitada con anterioridad.");

        }
        return this;

    }

    @Override
    public String toString() {
        return this.denominacion;
    }

}
