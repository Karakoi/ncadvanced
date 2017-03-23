package com.overseer.service;

import org.springframework.web.servlet.View;

import java.time.LocalDate;

/**
 * Email service, which process messages and send it.
 */
public interface ReportService {

    /**
     * Method generate PDF report for user role Admin.
     *
     * @param start period start.
     * @param end   period end.
     * @return admin reports documents.
     */
    View generateAdminPDFReport(LocalDate start, LocalDate end);

    /**
     * Method generate PDF report for user role Admin.
     */
    View generateRequestPDFReport(Long requestId);
}
