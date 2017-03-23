package com.overseer.service.impl;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.overseer.dao.RequestDao;
import com.overseer.model.Request;
import com.overseer.service.ReportBuilder;
import com.overseer.service.ReportService;
import com.overseer.service.impl.report.view.RequestReportPdfView;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.View;

import java.io.IOException;
import java.util.List;

/**
 * Implementation of {@link ReportService} interface.
 */
@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    @Qualifier("adminReportBuilderImpl")
    private ReportBuilder adminBuilder;

    @Qualifier("managerReportBuilderImpl")
    private ReportBuilder managerBuilder;

    @Qualifier("employeeReportBuilderImpl")
    private ReportBuilder employeeBuilder;

    private final RequestDao requestDao;

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

    @Override
    public View generateRequestPDFReport(Long requestId) {
        Request request = requestDao.findOne(requestId);
        List<Request> subRequests = requestDao.findSubRequests(requestId);
        List<Request> joinedRequests = requestDao.findJoinedRequests(requestId);
        return new RequestReportPdfView(request, subRequests, joinedRequests);
    }
}
