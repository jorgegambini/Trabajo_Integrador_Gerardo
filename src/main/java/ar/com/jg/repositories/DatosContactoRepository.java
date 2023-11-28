package ar.com.jg.repositories;

import ar.com.jg.model.DatosContacto;
import jakarta.persistence.EntityManager;


import java.util.List;

public class DatosContactoRepository implements CrudRepository<DatosContacto>{

    private EntityManager em;

    public DatosContactoRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<DatosContacto> listar() {

        return em.createQuery("from DatosContacto",DatosContacto.class).getResultList();

    }

    @Override
    public DatosContacto buscarPorId(Long id) {

        return em.find(DatosContacto.class, id);

    }

    @Override
    public void guardar(DatosContacto datosContacto) {

        if(datosContacto.getId() != null && datosContacto.getId() > 0){

            em.merge(datosContacto);

        }else{

            em.persist(datosContacto);

        }

    }

    @Override
    public void eliminar(Long id) {

        em.remove(buscarPorId(id));

    }

}

