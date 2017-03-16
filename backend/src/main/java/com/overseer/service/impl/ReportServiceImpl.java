package com.overseer.service.impl;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.overseer.service.ReportBuilder;
import com.overseer.service.ReportService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

/**
 * Implementation of {@link ReportService} interface.
 */
@Service
@Transactional
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class ReportServiceImpl implements ReportService {

    @Qualifier("adminReportBuilderImpl")
    private ReportBuilder adminBuilder;

    @Qualifier("managerReportBuilderImpl")
    private ReportBuilder managerBuilder;

    @Qualifier("employeeReportBuilderImpl")
    private ReportBuilder employeeBuilder;



    /**
     * {@inheritDoc}.
     */
    @Override
    public Document generateAdminPDFReport() throws IOException, DocumentException {
        return adminBuilder.generatePDFReport();
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public Document generateManagerPDFReport() throws IOException, DocumentException {
        return managerBuilder.generatePDFReport();
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public Document generateEmployeePDFReport() throws IOException, DocumentException {
        return employeeBuilder.generatePDFReport();
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void generateAdminExcelReport() {
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void generateManagerExcelReport() {
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void generateEmployeeExcelReport() {
    }
}
