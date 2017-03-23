package com.overseer.service.impl;

import com.overseer.dao.RequestDao;
import com.overseer.model.Request;
import com.overseer.service.ReportService;
import com.overseer.service.impl.report.view.AdminReportView;
import com.overseer.service.impl.report.view.RequestReportPdfView;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    private final RequestDao requestDao;
    private final AdminReportView adminReportView;

    /**
     * {@inheritDoc}.
     */
    @Override
    public View generateAdminPDFReport(LocalDate start, LocalDate end) {
        adminReportView.setDatePeriod(start, end);
        return adminReportView;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public View generateRequestPDFReport(Long requestId) {
        Request request = requestDao.findOne(requestId);
        List<Request> subRequests = requestDao.findSubRequests(requestId);
        List<Request> joinedRequests = requestDao.findJoinedRequests(requestId);
        return new RequestReportPdfView(request, subRequests, joinedRequests);
    }
}
