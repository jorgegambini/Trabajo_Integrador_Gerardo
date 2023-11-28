package ar.com.jg.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "especialidades")
@AttributeOverride(name = "id", column = @Column(name = "id_especialidad"))
//@NoArgsConstructor @RequiredArgsConstructor
@Getter @Setter
@EqualsAndHashCode(callSuper = true)
public class Especialidad extends EntidadId{

    @Column(nullable = false)
    @NonNull
    @EqualsAndHashCode.Exclude
    private String denominacion;
//    @ManyToOne
//    @JoinColumn(name = "id_servicio")
//    @EqualsAndHashCode.Exclude
//    private Servicio servicio; //Many2One
    @ManyToMany(mappedBy = "especialidades")
    @EqualsAndHashCode.Exclude
    private List<Tecnico> tecnicos; //Many2Many

    public Especialidad() {

        this.tecnicos = new ArrayList<>();

    }

    public Especialidad(@NonNull String denominacion) {

        this();
        this.denominacion = denominacion;

    }

    @Override
    public String toString() {
        return this.denominacion;
    }

}
