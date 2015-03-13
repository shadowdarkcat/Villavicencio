package mx.com.villavicencio.generics.spring.bo;

import java.util.Collection;

/**
 *
 * @author Gabriel J
 * @param <U>
 * @param <D>
 */
public interface GenericBo<U, D> {

    Collection<D> findAll(U user);

    D findById(U user, D object);

    void ingresar(U user, D object);

    void modificar(U user, D object);

    void eliminar(U user, D object);

}
