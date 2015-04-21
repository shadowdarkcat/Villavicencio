package mx.com.villavicencio.system.movimientos.movimientos.reporte.reporte;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import javax.servlet.http.HttpServletResponse;
import mx.com.villavicencio.commons.exception.ApplicationException;
import mx.com.villavicencio.generics.report.GenericReport;
import mx.com.villavicencio.properties.PropertiesBean;
import mx.com.villavicencio.properties.Property;
import mx.com.villavicencio.system.movimientos.movimientos.dto.DtoMovimientos;
import mx.com.villavicencio.system.movimientos.movimientos.factory.MovimientosFactory;
import mx.com.villavicencio.system.usuario.dto.DtoUsuario;
import mx.com.villavicencio.utils.StringUtils;

/**
 *
 * @author Gabriel J
 */
public class ReporteMovimientos extends GenericReport<DtoUsuario, DtoMovimientos> {

    private static BigDecimal saldoDisponible;
    private static Boolean isMovimientos = Boolean.FALSE;

    public static BigDecimal getSaldoDisponible() {
        return saldoDisponible;
    }

    public static Boolean getIsMovimientos() {
        return isMovimientos;
    }

    @Override
    public void generarReporte(DtoUsuario user, DtoMovimientos object, String rutaServer, String rutaReporte, String formato, HttpServletResponse response) {
        if ((user != null) && (user.getIdUsuario() != 0)) {
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
        } else {
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public void generarReporte(DtoUsuario user, Collection<DtoMovimientos> object, String rutaServer, String rutaReporte, String formato, HttpServletResponse response) {
        if ((user != null) && (user.getIdUsuario() != 0)) {
            isMovimientos = Boolean.TRUE;
            destino = "movimientos";
            master = "movimientos";
            Integer[] ids = new Integer[object.size()];
            Integer index = 0;
            Integer idAnterior = 0;
            for (DtoMovimientos movimientos : object) {
                ids[index] = movimientos.getNotaVenta().getIdNotaVenta();
                index++;
            }
            Integer indexAnterior = 0;
            for (index = 0; index < ids.length; index++) {
                Integer id = ids[index];
                if (!Objects.equals(id, idAnterior)) {
                    if (index == 0) {
                        ids[index] = id;
                    } else {
                        Integer resultado = indexAnterior - 1;
                        ids[resultado] = id;
                    }
                } else {
                    ids[index] = 0;
                    indexAnterior++;
                }
                idAnterior = id;
            }
            Collection<DtoMovimientos> collection = new ArrayList<>();
            Collection<DtoMovimientos> collectionReport = new ArrayList<>();
            Boolean flag = Boolean.FALSE;
            Integer contador = 1;
            Iterator<DtoMovimientos> iterator = object.iterator();
            index = 0;
            while (iterator.hasNext()) {
                DtoMovimientos movimientos = iterator.next();
                while (index < ids.length) {
                    if (Objects.equals(ids[index], movimientos.getNotaVenta().getIdNotaVenta())) {
                        DtoMovimientos movs = movimientos;
                        movs.setNoMovimiento(contador);
                        collection.add(movs);
                        flag = Boolean.TRUE;
                        contador++;
                        break;
                    } else {
                        if (flag) {
                            DtoMovimientos movs = MovimientosFactory.newInstance();
                            movs.setMovimientos(collection);
                            collectionReport.add(movs);
                            collection = new ArrayList<>();
                            flag = Boolean.FALSE;
                            contador = 1;
                        }
                    }
                    index++;
                }
            }

            BigDecimal totalCargo = BigDecimal.ZERO;
            BigDecimal totalAbono = BigDecimal.ZERO;
            BigDecimal totalCredito = BigDecimal.ZERO;
            Boolean aplicado = Boolean.FALSE;
            iterator = object.iterator();
            Integer idNotaAnterior = 0;
            while (iterator.hasNext()) {
                DtoMovimientos movs = iterator.next();
                if (!Objects.equals(idNotaAnterior, movs.getNotaVenta().getIdNotaVenta())) {
                    if (movs.getCredito().getIdCredito() != null) {
                        totalCredito = movs.getCredito().getCantidadMonetaria();
                        if (!StringUtils.isReallyEmptyOrNull(movs.getCredito().getEstatusCredito())) {
                            aplicado = Boolean.TRUE;
                        }
                    }
                    totalCargo = totalCargo.add(movs.getCargos().getCargo());
                }
                if (movs.getAbonos().getAbono() != null) {
                    totalAbono = totalAbono.add(movs.getAbonos().getAbono());
                }
                idNotaAnterior = movs.getNotaVenta().getIdNotaVenta();
            }
            if (aplicado) {
                BigDecimal disponible = totalCredito.subtract(totalCargo);
                saldoDisponible = disponible.add(totalAbono);
            } else {
                saldoDisponible = totalCredito;
            }

            if (flag) {
                DtoMovimientos movs = MovimientosFactory.newInstance();
                movs.setMovimientos(collection);
                collectionReport.add(movs);
            }
            createReporte(collectionReport, rutaServer, rutaReporte, formato, response);
        } else {
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }
}
