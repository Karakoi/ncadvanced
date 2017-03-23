package com.overseer.service.impl;

import com.itextpdf.text.Document;
import com.overseer.dao.RequestDao;
import com.overseer.model.Request;
import com.overseer.service.ReportService;
import com.overseer.service.impl.report.AdminReportBuilderImpl;
import com.overseer.service.impl.report.view.RequestReportPdfView;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.View;

import java.time.LocalDate;
import java.util.List;

/**
 * Implementation of {@link ReportService} interface.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    @Autowired
    @Qualifier("adminReportBuilderImpl")
    private final AdminReportBuilderImpl adminBuilder;

    private final RequestDao requestDao;

    /**
     * {@inheritDoc}.
     */
    @Override
    public Document generateAdminPDFReport(Document document, LocalDate start, LocalDate end) {
        return adminBuilder.generateReport(document, start, end);
    }

    @Override
    public View generateRequestPDFReport(Long requestId) {
        Request request = requestDao.findOne(requestId);
        List<Request> subRequests = requestDao.findSubRequests(requestId);
        List<Request> joinedRequests = requestDao.findJoinedRequests(requestId);
        return new RequestReportPdfView(request, subRequests, joinedRequests);
    }
}
