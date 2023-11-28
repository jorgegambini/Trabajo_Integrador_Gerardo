package ar.com.jg.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;


@MappedSuperclass
@Inheritance(strategy = InheritanceType.JOINED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter @Setter
public abstract class EntidadId implements Serializable { //Representa la PK a Nivel Relacional

    @Id //Indico cual es el atributo que será PK
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Indico la Estrategia AutoNumérica para el PK
    @EqualsAndHashCode.Include
    private Long id;

}
