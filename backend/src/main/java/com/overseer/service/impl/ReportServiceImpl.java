package com.overseer.service.impl;

import com.overseer.dao.RequestDao;
import com.overseer.dto.RequestDTO;
import com.overseer.model.Request;
import com.overseer.service.ReportService;
import com.overseer.service.RequestService;
import com.overseer.service.impl.report.view.AdminReportView;
import com.overseer.service.impl.report.view.RequestReportPdfView;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.View;

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

    private static final Long DEFAULT_MONTHS_STEP = 1L;
    private static final int COUNT_MONTHS_IN_YEAR = 12;
    private final RequestService requestService;
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
    public List<RequestDTO> getAllStatisticsOfCreatedRequestsByPeriod(LocalDate start, LocalDate end) {
        //Main list with Request DTO's
        List<RequestDTO> allRequests = new ArrayList<>();

        //Round the date until next month
        LocalDate localStart = start.plusDays((start.lengthOfMonth() - start.getDayOfMonth()) + 1);

        //Receive data before the 1st day of the next month (after start date)
        allRequests.add(requestService.findCountRequestsByPeriod(start, localStart, "Free"));

        //Round the date of the last month by the 1st day of this month
        LocalDate localEnd = end.minusDays(end.getDayOfMonth() - 1);
        if (!(localStart.equals(localEnd))) {
            //Receive data between the 1st day of the next month and the 1st day of the last month
            List<RequestDTO> dataFromCentralDates = requestService.findListCountRequestsByPeriod(localStart, localEnd, "Free");
            LocalDate local = loadGeneralList(allRequests, dataFromCentralDates, localStart, localEnd);

            //Receive data from the 1st day of the last month
            allRequests.add(requestService.findCountRequestsByPeriod(local, end, "Free"));
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
     * @param generalList main collection with DTO's
     * @param hourlyList  collection with DTO's in period between the 1st day of the second month and the 1st day of the last month
     * @param localStart  date from
     * @param localEnd    date to
     * @return return list of request DTO's from one period of time
     */
    private LocalDate loadGeneralList(List<RequestDTO> generalList, List<RequestDTO> hourlyList, LocalDate localStart, LocalDate localEnd) {

        int countMonth = getDifferenceBetweenDates(localStart, localEnd);
        if (countMonth == 0) {
            return localEnd;
        }
        boolean key;
        LocalDate local = null;
        for (int i = 0; i < countMonth; i++) {
            key = false;
            for (RequestDTO r : hourlyList) {
                if (localStart.equals(r.getStartDateLimit())) {
                    generalList.add(r);
                    key = true;
                    break;
                } else {
                    key = false;
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
    public List<RequestDTO> getAllStatisticsOfClosedRequestsByPeriod(LocalDate start, LocalDate end) {
        //Main list with request transfer objects
        List<RequestDTO> allRequests = new ArrayList<>();

        //Round the date until next month
        LocalDate localStart = start.plusDays((start.lengthOfMonth() - start.getDayOfMonth()) + 1);

        //Receive data before the 1st day of the next month (after start date)
        allRequests.add(requestService.findCountRequestsByPeriod(start, localStart, "Closed"));

        //Round the date of the last month by the 1st day of this month
        LocalDate localEnd = end.minusDays(end.getDayOfMonth() - 1);
        if (!(localStart.equals(localEnd))) {
            //Receive data between the 1st day of the next month and the 1st day of the last month
            List<RequestDTO> dataFromCentralDates = requestService.findListCountRequestsByPeriod(localStart, localEnd, "Closed");
            LocalDate local = loadGeneralList(allRequests, dataFromCentralDates, localStart, localEnd);

            //Receive data from the 1st day of the last month
            allRequests.add(requestService.findCountRequestsByPeriod(local, end, "Closed"));
        }
        return allRequests;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<RequestDTO> getManagerStatisticsOfClosedRequestsByPeriod(LocalDate start, LocalDate end, int id) {
        List<RequestDTO> requests = new ArrayList<>();
        LocalDate localStart = start.plusDays((start.lengthOfMonth() - start.getDayOfMonth()) + 1);
        requests.add(requestService.findCountRequestsByManagerAndPeriod(start, localStart, "Closed", id));
        LocalDate localEnd = end.minusDays(end.getDayOfMonth() - 1);
        if (!(localStart.equals(localEnd))) {
            List<RequestDTO> dataFromCentralDates = requestService.findListCountRequestsByManagerAndPeriod(localStart, localEnd, "Closed", id);
            LocalDate local = loadGeneralList(requests, dataFromCentralDates, localStart, localEnd);
            requests.add(requestService.findCountRequestsByManagerAndPeriod(local, end, "Closed", id));
        }
        return requests;
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
