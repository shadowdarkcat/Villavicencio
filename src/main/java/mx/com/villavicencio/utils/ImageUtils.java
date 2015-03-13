package mx.com.villavicencio.utils;

import java.util.ArrayList;
import java.util.Collection;
import mx.com.villavicencio.generics.variables.Variables;
import mx.com.villavicencio.image.helper.ImageViewHelper;
import mx.com.villavicencio.system.cliente.dto.DtoCliente;
import mx.com.villavicencio.system.productos.productos.dto.DtoProducto;
import mx.com.villavicencio.system.productos.productos.factory.ProductoFactory;
import mx.com.villavicencio.system.vendedor.dto.DtoVendedor;

/**
 *
 * @author Gabriel J
 */
public class ImageUtils {

    public static Collection<DtoProducto> getEstablecidos(DtoCliente clientes, String context) {
        Collection<DtoProducto> establecido = new ArrayList<>();
        for (DtoProducto productos : clientes.getEstablecidos()) {
            establecido.add(getProducto(productos, context));
        }
        return establecido;
    }

    public static DtoProducto getEstablecidos(DtoProducto producto, String context) {
        return getProducto(producto, context);
    }

    public static Collection<DtoProducto> getPersonalizado(DtoCliente clientes, String context) {
        Collection<DtoProducto> personalizado = new ArrayList<>();
        for (DtoProducto productos : clientes.getPersonalizados()) {
            personalizado.add(getProducto(productos, context));
        }
        return personalizado;
    }

    public static DtoProducto getPersonalizado(DtoProducto producto, String context) {
        return getProducto(producto, context);
    }

    public static Collection<DtoProducto> getEstablecidos(DtoVendedor vendedor, String context) {
        Collection<DtoProducto> establecido = new ArrayList<>();
        for (DtoProducto productos : vendedor.getEstablecidos()) {
            establecido.add(getProducto(productos, context));
        }
        return establecido;
    }

    public static Collection<DtoProducto> getPersonalizado(DtoVendedor vendedor, String context) {
        Collection<DtoProducto> personalizado = new ArrayList<>();
        for (DtoProducto productos : vendedor.getPersonalizados()) {
            personalizado.add(getProducto(productos, context));
        }
        return personalizado;
    }

    private static DtoProducto getProducto(DtoProducto productos, String context) {
        DtoProducto producto = ProductoFactory.newInstance();

        producto.setIdProducto(productos.getIdProducto());
        producto.setClaveProducto(productos.getClaveProducto());
        if (!StringUtils.isReallyEmptyOrNull(productos.getImagenProducto())) {
            producto.setImagenProducto(ImageViewHelper.getPath(context, productos.getImagenProducto()));
        } else {
            producto.setImagenProducto(context + Variables.NO_DISPONIBLE);
        }
        producto.setNombreProducto(productos.getNombreProducto());

        producto.setPeso(productos.getPeso());
        producto.setCostoUnitario(productos.getCostoUnitario());
        producto.setComision(NumberUtils.convertPorcentajeToNumber(productos.getComision()));
        producto.setPesoMinimo(productos.getPesoMinimo());
        producto.setPesoMaximo(productos.getPesoMaximo());
        return producto;
    }
}
