package com.overseer.service;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;

import java.io.IOException;

/**
 * The <code>ReportBuilder</code> interface describes methods creation
 * templates for reports documents.
 */
public interface ReportBuilder {

    /**
     * Constructs {@link Document} for provided entity.
     *
     * @return complete {@link Document} for reports in pdf format.
     */
    Document buildPDFTemplate() throws DocumentException, IOException;

    /**
     * Constructs {@link Document} for provided entity.
     * //     * @return complete {@link Document} for reports in excel format.
     */
    void buildExcelTemplate();

    /**
     * Method generate report in pdf format.
     *
     * @return complete {@link Document} for reports in pdf format.
     */
    Document generatePDFReport() throws IOException, DocumentException;

    /**
     * Method generate report in excel format.
     * //     * @return complete {@link Document} for reports in excel format.
     */
    void generateExcelReport();

}
