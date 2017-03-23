package com.overseer.service;

import com.itextpdf.text.Document;
import org.springframework.web.servlet.View;

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

    /**
     * Method generate PDF report for user role Admin.
     */
    View generateRequestPDFReport(Long requestId);
}
