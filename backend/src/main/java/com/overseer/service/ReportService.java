package com.overseer.service;

import com.overseer.dto.RequestDTO;

import java.util.List;

/**
 * Email service, which process messages and send it.
 */
public interface ReportService {

    /**
     * Method generate PDF report for user role Admin.
     *
     * @param start          period start.
     * @param end            period end.
     * @param countTop       count managers in top.
     * @param encryptedEmail user encrypted email.
     * @return pdf doc in byte[].
     */
    byte[] generateAdminPDFReport(String start, String end, int countTop, String encryptedEmail);

    /**
     * Method generate PDF report for user role Manager.
     *
     * @param start          period start.
     * @param end            period end.
     * @param id             manager id.
     * @param encryptedEmail user encrypted email.
     * @return pdf doc in byte[].
     */
    byte[] generateManagerPDFReport(String start, String end, int id, String encryptedEmail);

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
    byte[] generateRequestPDFReport(Long requestId);
}
