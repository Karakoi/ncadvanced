package com.overseer.service.impl;

import com.itextpdf.text.Document;
import com.overseer.service.ReportService;
import com.overseer.service.impl.report.AdminReportBuilderImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

/**
 * Implementation of {@link ReportService} interface.
 */
@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService{

    //    @Qualifier("adminReportBuilderImpl")
    @Autowired
    @Qualifier("adminReportBuilderImpl")
    private final AdminReportBuilderImpl adminBuilder;

//    @Qualifier("managerReportBuilderImpl")
//    private ReportBuilderImpl managerBuilder;
//
//    @Qualifier("employeeReportBuilderImpl")
//    private ReportBuilderImpl employeeBuilder;


    /**
     * {@inheritDoc}.
     */
    @Override
    public Document generateAdminPDFReport(Document document, LocalDate start, LocalDate end) {
        return adminBuilder.generateReport(document, start, end);
    }


}
