package ar.com.jg.model;

import jakarta.persistence.*;
import lombok.*;


@MappedSuperclass
@NoArgsConstructor @RequiredArgsConstructor
@Getter @Setter
@ToString(callSuper = true, includeFieldNames = false, exclude = {"datosContacto"})
@EqualsAndHashCode(callSuper = true)
public abstract class Empleado extends EntidadId{

    @Column(unique = true, nullable = false)
    @EqualsAndHashCode.Exclude
    protected Long legajo;
    @Column(nullable = false)
    @NonNull
    @EqualsAndHashCode.Exclude
    protected String nombre;
    @Column(nullable = false)
    @NonNull
    @EqualsAndHashCode.Exclude
    protected String apellido;
    @Column(nullable = false)
    @NonNull
    @EqualsAndHashCode.Exclude
    protected String usuario;
    @Column(nullable = false)
    @NonNull
    @EqualsAndHashCode.Exclude
    protected String password;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_datos_contacto", nullable = false)
    @EqualsAndHashCode.Exclude
    protected DatosContacto datosContacto; // One2One

}
