package com.overseer.service;

import com.itextpdf.text.Document;

import java.time.LocalDate;

/**
 * Email service, which process messages and send it.
 */
public interface ReportService {

    /**
     * Method generate PDF report for user role Admin.
     *
     * @param document document which must be generated.
     * @param start period start.
     * @param end   period end.
     * @return admin reports documents.
     */
    Document generateAdminPDFReport(Document document, LocalDate start, LocalDate end);

//    /**
//     * Method generate PDF report for user role Admin.
//     */
//    Document generateAdminPDFReport() throws IOException, DocumentException;
//
//    /**
//     * Method generate PDF report for user role Office Manager.
//     */
//    Document generateManagerPDFReport() throws IOException, DocumentException;
//
//    /**
//     * Method generate PDF report for user role Employee.
//     */
//    Document generateEmployeePDFReport() throws IOException, DocumentException;
//
//    /**
//     * Method generate excel report for user role Admin.
//     */
//    void generateAdminExcelReport();
//
//    /**
//     * Method generate excel report for user role Office Manager.
//     */
//    void generateManagerExcelReport();
//
//    /**
//     * Method generate excel report for user role Employee.
//     */
//    void generateEmployeeExcelReport();


}
