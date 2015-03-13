package mx.com.villavicencio.generics.report;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletResponse;
import mx.com.villavicencio.commons.exception.ApplicationException;
import mx.com.villavicencio.generics.variables.Variables;
import mx.com.villavicencio.system.vendedor.dto.DtoVendedor;
import mx.com.villavicencio.system.vendedor.factory.VendedorFactory;
import mx.com.villavicencio.utils.StringUtils;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;
import org.apache.commons.beanutils.PropertyUtils;
import org.reflections.ReflectionUtils;
import static org.reflections.ReflectionUtils.withModifier;

/**
 *
 * @author Gabriel J
 * @param <U>
 * @param <D>
 */
public abstract class GenericReport<U, D> extends GenericReportBoImpl<U, D> {

    protected JasperDesign disenioReporte;
    protected JasperReport reporteCompilado;
    protected Map<String, Object> parametrosReporte;
    protected JasperPrint reporteCompleto;
    protected String message;
    protected String destino;
    protected String master;

    @Override
    protected void createReporte(Collection collection, String rutaServer, String rutaReporte, String formato, HttpServletResponse response) {
        try {
            File fileDestino = new File(rutaServer + File.separator + destino + ".pdf");
            if (!fileDestino.exists()) {
                fileDestino.mkdirs();
            }
            if (!StringUtils.isReallyEmptyOrNull(master)) {
                disenioReporte = JRXmlLoader.load(rutaReporte + File.separator + master + ".jrxml");
            } else {
                disenioReporte = JRXmlLoader.load(rutaReporte + File.separator + destino + ".jrxml");
            }
            reporteCompilado = JasperCompileManager.compileReport(disenioReporte);
            parametrosReporte = new HashMap<>();
            parametrosReporte.put("PATH_IMAGENES", rutaServer + File.separator + "image" + File.separator);
            parametrosReporte.put("SUBREPORT_DIR", rutaReporte + File.separator);
            JRBeanCollectionDataSource beanDataSource = new JRBeanCollectionDataSource(collection);
            reporteCompleto = JasperFillManager.fillReport(reporteCompilado, parametrosReporte, beanDataSource);
            this.viewReporte(formato, reporteCompleto, fileDestino, destino, response);
        } catch (JRException | IOException ex) {
            throw new ApplicationException("Error al generar el reporte.", ex);
        } finally {
            System.runFinalization();
            Runtime garbage = Runtime.getRuntime();
            garbage.gc();
        }
    }

    private void viewReporte(String tipoReporte, JasperPrint jasperPrint, File fileDestino, String destino, HttpServletResponse response) throws IOException {
        OutputStream out = null;
        FileOutputStream filePDF = null;
        File report = null;
        try {
            if (tipoReporte.equalsIgnoreCase(Variables.PDF)) {
                byte bytes[] = JasperExportManager.exportReportToPdf(jasperPrint);

                if (fileDestino != null && destino != null) {
                    report = new File(fileDestino, destino);
                    filePDF = new FileOutputStream(report);
                    filePDF.write(bytes);
                }

                out = response.getOutputStream();
                response.setContentType("application/pdf");
                response.setContentLength(bytes.length);
                out.write(bytes, 0, bytes.length);

            } else if (tipoReporte.equalsIgnoreCase(Variables.EXCEL)) {

                out = response.getOutputStream();
                response.setContentType("application/vnd.ms-excel");

                JRXlsxExporter xlsExporter = new JRXlsxExporter();

                SimpleExporterInput simpleExporterInput = new SimpleExporterInput(jasperPrint);
                xlsExporter.setExporterInput(simpleExporterInput);
                SimpleOutputStreamExporterOutput simpleOutputStreamExporterOutput = new SimpleOutputStreamExporterOutput(out);
                xlsExporter.setExporterOutput(simpleOutputStreamExporterOutput);
                SimpleOutputStreamExporterOutput simple = new SimpleOutputStreamExporterOutput(out);
                xlsExporter.setExporterOutput(simple);
                SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
                configuration.setRemoveEmptySpaceBetweenColumns(Boolean.TRUE);
                configuration.setRemoveEmptySpaceBetweenRows(Boolean.TRUE);
                configuration.setIgnoreCellBorder(Boolean.FALSE);
                configuration.setWhitePageBackground(Boolean.FALSE);
                configuration.setDetectCellType(Boolean.TRUE);
                xlsExporter.setConfiguration(configuration);
                xlsExporter.exportReport();
            }
        } catch (FileNotFoundException ex) {
            String message = "Error al generar el reporte " + destino;
            throw new ApplicationException(message);
        } catch (JRException ex) {
            message = "Error al generar el reporte " + destino;
            throw new ApplicationException(message);
        } catch (IOException ex) {
            message = "Error al generar el reporte " + destino;
            throw new ApplicationException(message);
        } finally {
            if (out != null) {
                out.flush();
                out.close();
            }
            if (filePDF != null) {
                filePDF.flush();
                filePDF.close();
            }
            System.runFinalization();
            Runtime garbage = Runtime.getRuntime();
            garbage.gc();
        }
    }

}
