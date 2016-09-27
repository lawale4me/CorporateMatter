/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cm.customexporter;

/**
 *
 * @author Ahmed
 */
    
    
    import javax.faces.FacesException;
import javax.faces.context.FacesContext;

import java.util.ServiceLoader;
import org.primefaces.extensions.component.exporter.ExcelExporter;
import org.primefaces.extensions.component.exporter.ExporterFactory;
import org.primefaces.extensions.component.exporter.PDFExporter;

/**
 * <code>Exporter</code> component.
 *
 * @author Sudheer Jonna / last modified by $Author$
 * @since 0.7.0
 */
public class MyConverter {

    private static final String KEY = MyConverter.class.getName();

    public static ExporterFactory getExporterFactory(FacesContext context) {

        ExporterFactory factory = (ExporterFactory) context.getExternalContext().getApplicationMap().get(KEY);

        if (factory == null) {
            ServiceLoader<ExporterFactory> loader = ServiceLoader.load(ExporterFactory.class);
            for (ExporterFactory currentFactory : loader) {
                factory = currentFactory;
                break;
            }

            if (factory == null) {
                factory = new DefaultExporterFactory();
            }

            context.getExternalContext().getApplicationMap().put(KEY, factory);
        }

        return factory;
    }
}

class DefaultExporterFactory implements ExporterFactory {
  

    static public enum ExporterType {
        PDF,
        XLSX
    }

    @Override
    public org.primefaces.extensions.component.exporter.Exporter getExporterForType(String type) {

        org.primefaces.extensions.component.exporter.Exporter exporter = null;

        try {
            ExporterType exporterType = ExporterType.valueOf(type.toUpperCase());

            switch (exporterType) {

                case PDF:
                    exporter = new PDFCustomExporter();
                    break;

                case XLSX:
                    exporter = new ExcelExporter();
                    break;

                default: {
                    exporter = new PDFCustomExporter();
                    break;
                }

            }
        } catch (IllegalArgumentException e) {
            throw new FacesException(e);
        }

        return exporter;
    }

}
