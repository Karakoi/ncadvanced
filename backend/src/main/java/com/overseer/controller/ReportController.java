package com.overseer.controller;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.overseer.service.ReportService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * Controller provides api for creating, getting reports.
 */
@RestController
@RequestMapping("/api/reports")
@AllArgsConstructor
public class ReportController {

    private final ReportService reportService;

    /**
     * Gets {@link Document} pdf report.
     *
     * @return {@link Document} doc with reporting data.
     */
//    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(produces = "application/pdf")
    public Document getPDF() {
        Document document = null;
        try {
            document = reportService.generateAdminPDFReport();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return document;
    }

}
