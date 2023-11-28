package ar.com.jg.model;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "datos_contactos")
@AttributeOverride(name = "id", column = @Column(name = "id_datos_contacto"))
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@ToString
@EqualsAndHashCode(callSuper = true)
public class DatosContacto extends EntidadId{

    @Column(nullable = false)
    @EqualsAndHashCode.Exclude
    private Long telefono;
    @Column(nullable = false)
    @EqualsAndHashCode.Exclude
    private Long celular;
    @Column(nullable = false)
    @EqualsAndHashCode.Exclude
    @NonNull
    private String email;

}
