package com.overseer.service.impl;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import com.overseer.dao.RequestDao;
import com.overseer.dto.RequestDTO;
import com.overseer.model.enums.ProgressStatus;
import com.overseer.service.HistoryService;
import com.overseer.service.ReportService;
import com.overseer.service.RequestService;
import com.overseer.service.impl.report.AdminReportBuilder;
import com.overseer.service.impl.report.ManagerReportBuilder;
import com.overseer.service.impl.report.RequestReportPdfBuilder;
import com.overseer.util.LocalDateFormatter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of {@link ReportService} interface.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    public static final Long DEFAULT_MONTHS_STEP = 1L;
    private static final int COUNT_MONTHS_IN_YEAR = 12;
    private final RequestService requestService;
    private final RequestDao requestDao;
    private final HistoryService historyService;
    private final AdminReportBuilder adminReportBuilder;
    private final ManagerReportBuilder managerReportBuilder;
    private final RequestReportPdfBuilder requestReportPdfBuilder;

    /**
     * {@inheritDoc}.
     */
    @Override
    public byte[] generateAdminPDFReport(String beginDate, String endDate, int countTop, String encryptedEmail) {
        adminReportBuilder.setDatePeriod(beginDate, endDate, countTop, encryptedEmail);
        Document document = new Document();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            PdfWriter.getInstance(document, byteArrayOutputStream);
            adminReportBuilder.buildPdfDocument(document);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return byteArrayOutputStream.toByteArray();
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public byte[] generateManagerPDFReport(String beginDate, String endDate, int id, String encryptedEmail) {
        Document document = new Document();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        managerReportBuilder.setDatePeriod(beginDate, endDate, id, encryptedEmail);
        try {
            PdfWriter.getInstance(document, byteArrayOutputStream);
            managerReportBuilder.buildPdfDocument(document);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return byteArrayOutputStream.toByteArray();
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<RequestDTO> getAllStatisticsOfFreeRequestsByPeriod(String beginDate, String endDate) {

        //Convert dates to LocalDate format
        LocalDate start = LocalDate.parse(beginDate, LocalDateFormatter.FORMATTER);
        LocalDate end = LocalDate.parse(endDate, LocalDateFormatter.FORMATTER);

        //Create main list with request transfer objects
        List<RequestDTO> allRequests = new ArrayList<>();
        LocalDate localStart;
        LocalDate localEnd;
        switch (getDifferenceBetweenDates(start, end)) {
            case 0:
                //Receive data from small period, without dividing to other intervals
                allRequests.add(requestService.findCountRequestsBySmallPeriod(start, end, ProgressStatus.FREE.getId()));
                break;
            case 1:
                //Round the date until next month
                localStart = start.plusDays((start.lengthOfMonth() - start.getDayOfMonth()) + 1);
                //Receive data before the 1st day of the next month (after start date)
                allRequests.add(requestService.findCountRequestsBySmallPeriod(start, localStart, ProgressStatus.FREE.getId()));
                if (start.getDayOfMonth() != end.getDayOfMonth()) {
                    allRequests.add(requestService.findCountRequestsBySmallPeriod(localStart, end, ProgressStatus.FREE.getId()));
                }
                break;
            default:
                //Round the date until next month
                localStart = start.plusDays((start.lengthOfMonth() - start.getDayOfMonth()) + 1);
                //Receive data before the 1st day of the next month (after start date)
                allRequests.add(requestService.findCountRequestsBySmallPeriod(start, localStart, ProgressStatus.FREE.getId()));

                //Round the date of the last month by the 1st day of this month
                localEnd = end.minusDays(end.getDayOfMonth() - 1);
                //Receive data between the 1st day of the next month and the 1st day of the last month
                List<RequestDTO> dataFromCentralDates = requestService.findListCountRequestsByPeriod(localStart, localEnd, ProgressStatus.FREE.getId());
                LocalDate local = loadGeneralList(allRequests, dataFromCentralDates, localStart, localEnd);
                if (!(end.equals(local))) {
                    //Receive data from the 1st day of the last month
                    allRequests.add(requestService.findCountRequestsBySmallPeriod(local, end, ProgressStatus.FREE.getId()));
                }
                break;
        }
        return allRequests;
    }

    /**
     * Gets Difference between two dates in months.
     *
     * @param start date from.
     * @param end   date to.
     * @return return count months between dates.
     */
    private int getDifferenceBetweenDates(LocalDate start, LocalDate end) {

        //Dates difference in months
        int countYears = Period.between(start, end).getYears();
        int countMonth;
        if (countYears != 0) {
            countMonth = countYears * COUNT_MONTHS_IN_YEAR + Period.between(start, end).getMonths();
        } else {
            countMonth = Period.between(start, end).getMonths();
        }
        return countMonth;
    }

    /**
     * Gets general list of request transfer objects which created in the same period.
     *
     * @param generalList main collection with DTO's.
     * @param hourlyList  collection with DTO's in period between the 1st day of the second month and the 1st day of the last month.
     * @param localStart  date from.
     * @param localEnd    date to.
     * @return return last date from period and load list of request DTO's from this period of time.
     */
    private LocalDate loadGeneralList(List<RequestDTO> generalList, List<RequestDTO> hourlyList, LocalDate localStart, LocalDate localEnd) {

        int countMonth = getDifferenceBetweenDates(localStart, localEnd);
        if (countMonth == 0) {
            return localEnd;
        }
        boolean key;
        LocalDate local = null;

        //Load main collection with data
        for (int i = 0; i < countMonth; i++) {
            key = false;
            for (RequestDTO r : hourlyList) {
                if (localStart.equals(r.getStartDateLimit())) {
                    generalList.add(r);
                    key = true;
                    break;
                }
            }
            local = localStart.plusMonths(DEFAULT_MONTHS_STEP);
            if (!key) {
                RequestDTO temp = new RequestDTO();
                temp.setStartDateLimit(localStart);
                temp.setCount(0L);
                temp.setEndDateLimit(local);
                generalList.add(temp);
            }
            localStart = local;
        }
        return local;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<RequestDTO> getAllStatisticsOfClosedRequestsByPeriod(String beginDate, String endDate) {

        //Convert dates to LocalDate format
        LocalDate start = LocalDate.parse(beginDate, LocalDateFormatter.FORMATTER);
        LocalDate end = LocalDate.parse(endDate, LocalDateFormatter.FORMATTER);

        //Main list with request transfer objects
        List<RequestDTO> allRequests = new ArrayList<>();

        LocalDate localStart;
        LocalDate localEnd;
        switch (getDifferenceBetweenDates(start, end)) {
            case 0:
                //Receive data from small period, without dividing to other intervals
                allRequests.add(requestService.findCountRequestsByPeriod(start, end, ProgressStatus.CLOSED.getId()));
                break;
            case 1:
                //Round the date until next month
                localStart = start.plusDays((start.lengthOfMonth() - start.getDayOfMonth()) + 1);
                //Receive data before the 1st day of the next month (after start date)
                allRequests.add(requestService.findCountRequestsByPeriod(start, localStart, ProgressStatus.CLOSED.getId()));
                if (start.getDayOfMonth() != end.getDayOfMonth()) {
                    allRequests.add(requestService.findCountRequestsByPeriod(localStart, end, ProgressStatus.CLOSED.getId()));
                }
                break;
            default:
                //Round the date until next month
                localStart = start.plusDays((start.lengthOfMonth() - start.getDayOfMonth()) + 1);
                //Receive data before the 1st day of the next month (after start date)
                allRequests.add(requestService.findCountRequestsBySmallPeriod(start, localStart, ProgressStatus.CLOSED.getId()));

                //Round the date of the last month by the 1st day of this month
                localEnd = end.minusDays(end.getDayOfMonth() - 1);
                //Receive data between the 1st day of the next month and the 1st day of the last month
                List<RequestDTO> dataFromCentralDates = requestService.findListCountRequestsByPeriod(localStart, localEnd, ProgressStatus.CLOSED.getId());
                LocalDate local = loadGeneralList(allRequests, dataFromCentralDates, localStart, localEnd);
                if (!(end.equals(local))) {
                    //Receive data from the 1st day of the last month
                    allRequests.add(requestService.findCountRequestsBySmallPeriod(local, end, ProgressStatus.CLOSED.getId()));
                }
                break;
        }
        return allRequests;

    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<RequestDTO> getManagerStatisticsOfClosedRequestsByPeriod(String beginDate, String endDate, int id) {

        LocalDate start = LocalDate.parse(beginDate, LocalDateFormatter.FORMATTER);
        LocalDate end = LocalDate.parse(endDate, LocalDateFormatter.FORMATTER);

        List<RequestDTO> requests = new ArrayList<>();

        LocalDate localStart;
        LocalDate localEnd;
        switch (getDifferenceBetweenDates(start, end)) {
            case 0:
                //Receive data from small period, without dividing to other intervals
                requests.add(requestService.findCountRequestsByManagerAndSmallPeriod(start, end, ProgressStatus.CLOSED.getId(), id));
                break;
            case 1:
                //Round the date until next month
                localStart = start.plusDays((start.lengthOfMonth() - start.getDayOfMonth()) + 1);
                //Receive data before the 1st day of the next month (after start date)
                requests.add(requestService.findCountRequestsByManagerAndSmallPeriod(start, localStart, ProgressStatus.CLOSED.getId(), id));
                if (start.getDayOfMonth() != end.getDayOfMonth()) {
                    requests.add(requestService.findCountRequestsByManagerAndSmallPeriod(localStart, end, ProgressStatus.CLOSED.getId(), id));
                }
                break;
            default:
                localStart = start.plusDays((start.lengthOfMonth() - start.getDayOfMonth()) + 1);
                requests.add(requestService.findCountRequestsByManagerAndSmallPeriod(start, localStart, ProgressStatus.CLOSED.getId(), id));
                localEnd = end.minusDays(end.getDayOfMonth() - 1);
                List<RequestDTO> dataFromCentralDates = requestService.findListCountRequestsByManagerAndPeriod(localStart, localEnd, ProgressStatus.CLOSED.getId(), id);
                LocalDate local = loadGeneralList(requests, dataFromCentralDates, localStart, localEnd);
                if (!(end.equals(local))) {
                    requests.add(requestService.findCountRequestsByManagerAndSmallPeriod(local, end, ProgressStatus.CLOSED.getId(), id));
                }
                break;
        }
        return requests;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public byte[] generateRequestPDFReport(Long requestId) {
        requestReportPdfBuilder.setRequest(requestDao.findOne(requestId));
        requestReportPdfBuilder.setSubRequests(requestDao.findSubRequests(requestId));
        requestReportPdfBuilder.setJoinedRequests(requestDao.findJoinedRequests(requestId));
        requestReportPdfBuilder.setHistoryList(historyService.findHistory(requestId));

        Document document = new Document();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            PdfWriter.getInstance(document, byteArrayOutputStream);
            requestReportPdfBuilder.buildPdfDocument(document);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return byteArrayOutputStream.toByteArray();
    }
}
