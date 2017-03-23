package com.overseer.controller;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.overseer.service.ReportService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.View;

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
        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        }
        return document;
    }

    /**
     * Handle request to download an Excel document.
     */
    @RequestMapping(value = "/request", method = RequestMethod.GET)
    public View download(@RequestParam String id) {
        Long requestId = Long.valueOf(id);
        return reportService.generateRequestPDFReport(requestId);
    }

}
