package com.overseer.service;

import com.overseer.dto.RequestDTO;
import org.springframework.web.servlet.View;

import java.util.List;

/**
 * Email service, which process messages and send it.
 */
public interface ReportService {

    /**
     * Method generate PDF report for user role Admin.
     *
     * @param start period start.
     * @param end   period end.
     * @param countTop   count managers in top.
     * @return view with admin reports.
     */
    View generateAdminPDFReport(String start, String end, int countTop);

    /**
     * Method generate PDF report for user role Manager.
     *
     * @param start period start.
     * @param end   period end.
     * @param id    manager id.
     * @return view with manager reports.
     */
    View generateManagerPDFReport(String start, String end, int id);

    /**
     * The method receives all the created requests for a period of months.
     *
     * @param start period start.
     * @param end   period end.
     * @return list of requests transfer objects.
     */
    List<RequestDTO> getAllStatisticsOfFreeRequestsByPeriod(String start, String end);

    /**
     * The method receives all the closed requests for a period of months.
     *
     * @param start period start.
     * @param end   period end.
     * @return list of requests transfer objects.
     */
    List<RequestDTO> getAllStatisticsOfClosedRequestsByPeriod(String start, String end);

    /**
     * The method receives all manager's closed requests for a period of months.
     *
     * @param start period start.
     * @param end   period end.
     * @return list of requests transfer objects.
     */
    List<RequestDTO> getManagerStatisticsOfClosedRequestsByPeriod(String start, String end, int id);

    /**
     * Method generate PDF for request.
     */
    View generateRequestPDFReport(Long requestId);
}
