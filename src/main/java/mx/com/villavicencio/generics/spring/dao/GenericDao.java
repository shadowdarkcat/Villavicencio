package mx.com.villavicencio.generics.spring.dao;

import java.util.Collection;

/**
 *
 * @author Gabriel J
 * @param <D>
 */
public interface GenericDao <D>{

    Collection<D> findAll();

    D findById(D object);
    
    void ingresar(D object);

    void modificar(D object);

    void eliminar(D object);
    
}
