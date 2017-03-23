package com.overseer.service;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import org.springframework.web.servlet.View;

import java.io.IOException;

/**
 * Email service, which process messages and send it.
 */
public interface ReportService {

    /**
     * Method generate PDF report for user role Admin.
     */
    Document generateAdminPDFReport() throws IOException, DocumentException;

    /**
     * Method generate PDF report for user role Office Manager.
     */
    Document generateManagerPDFReport() throws IOException, DocumentException;

    /**
     * Method generate PDF report for user role Employee.
     */
    Document generateEmployeePDFReport() throws IOException, DocumentException;

    /**
     * Method generate excel report for user role Admin.
     */
    void generateAdminExcelReport();

    /**
     * Method generate excel report for user role Office Manager.
     */
    void generateManagerExcelReport();

    /**
     * Method generate excel report for user role Employee.
     */
    void generateEmployeeExcelReport();

    /**
     * Method generate PDF report for user role Admin.
     */
    View generateRequestPDFReport(Long requestId);
}
