package ar.com.jg.utility;

import jakarta.persistence.*;

public class JPAUtil {

    private static final EntityManagerFactory entityManagerFactory = buildEntityManagerFactory();

    private static EntityManagerFactory buildEntityManagerFactory(){

        return Persistence.createEntityManagerFactory("TrabajoIntegrador");

    }

    public static EntityManager getEntityManager() {

        return entityManagerFactory.createEntityManager();

    }

}
