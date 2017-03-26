package com.overseer.controller;

import com.overseer.dto.RequestDTO;
import com.overseer.model.enums.ProgressStatus;
import com.overseer.service.ReportService;
import com.overseer.service.RequestService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.View;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Controller provides api for creating, getting reports.
 */
@RestController
@RequestMapping("/api/reports")
@AllArgsConstructor
public class ReportController {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final RequestService requestService;
    private final ReportService reportService;

    /**
     * Handle request to download an Excel document.
     */
    @RequestMapping(value = "/request", method = RequestMethod.GET)
    public View download(@RequestParam String id) {
        Long requestId = Long.valueOf(id);
        return reportService.generateRequestPDFReport(requestId);
    }

    /**
     * Gets Document pdf report.
     *
     * @return Document doc with reporting data.
     */
    @GetMapping("/adminPDFReport")
    public View getAdminPDFReport(@RequestParam String beginDate, @RequestParam String endDate) {

        LocalDate start = LocalDate.parse(beginDate, formatter);
        LocalDate end = LocalDate.parse(endDate, formatter);

        return reportService.generateAdminPDFReport(start, end);
    }

    /**
     * Gets list of request transfer objects which created in the same period.
     *
     * @param beginDate date from
     * @param endDate   date to
     * @return return list of requestDTO from one period of time
     */
    @GetMapping("/getAllStatisticsOfCreatedRequestsByPeriod")
    public ResponseEntity<List<RequestDTO>> getAllStatisticsOfCreatedRequestsByPeriod(@RequestParam String beginDate,
                                                                                      @RequestParam String endDate) {

        LocalDate start = LocalDate.parse(beginDate, formatter);
        LocalDate end = LocalDate.parse(endDate, formatter);
        return new ResponseEntity<>(reportService.getAllStatisticsOfCreatedRequestsByPeriod(start, end), HttpStatus.OK);
    }

    /**
     * Gets list of request transfer objects which created in the same period.
     *
     * @param beginDate date from
     * @param endDate   date to
     * @return return list of requestDTO from one period of time
     */
    @GetMapping("/getAllStatisticsOfClosedRequestsByPeriod")
    public ResponseEntity<List<RequestDTO>> getAllStatisticsOfClosedRequestsByPeriod(@RequestParam String beginDate,
                                                                                     @RequestParam String endDate) {
        LocalDate start = LocalDate.parse(beginDate, formatter);
        LocalDate end = LocalDate.parse(endDate, formatter);
        return new ResponseEntity<>(reportService.getAllStatisticsOfClosedRequestsByPeriod(start, end), HttpStatus.OK);
    }

    /**
     * Gets list of best managers which created in the same period. Data showed with status closed.
     *
     * @param beginDate date from
     * @param endDate   date to
     * @return return list of managers from one period of time
     */
    @GetMapping("/getBestManagersWithClosedStatusByPeriod")
    public ResponseEntity<List<RequestDTO>> getBestManagersWithClosedStatusByPeriod(@RequestParam String beginDate,
                                                                                    @RequestParam String endDate) {
        LocalDate start = LocalDate.parse(beginDate, formatter);
        LocalDate end = LocalDate.parse(endDate, formatter);
        List<RequestDTO> topManagers = requestService.findBestManagersByPeriod(start, end, ProgressStatus.CLOSED.getId());
        return new ResponseEntity<>(topManagers, HttpStatus.OK);
    }

    /**
     * Gets list of best managers which created in the same period. Data showed with status free.
     *
     * @param beginDate date from
     * @param endDate   date to
     * @return return list of managers from one period of time
     */
    @GetMapping("/getBestManagersWithFreeStatusByPeriod")
    public ResponseEntity<List<RequestDTO>> getBestManagersWithFreeStatusByPeriod(@RequestParam String beginDate,
                                                                                  @RequestParam String endDate) {
        LocalDate start = LocalDate.parse(beginDate, formatter);
        LocalDate end = LocalDate.parse(endDate, formatter);
        List<RequestDTO> topManagers = requestService.findBestManagersByPeriod(start, end, ProgressStatus.FREE.getId());
        return new ResponseEntity<>(topManagers, HttpStatus.OK);
    }

    /**
     * Gets list of request transfer objects which created in the same period for manager.
     *
     * @param beginDate date from.
     * @param endDate   date to.
     * @param id        manager id.
     * @return return list of requestDTO from one period of time for manager.
     */
    @GetMapping("/getManagerStatisticsOfClosedRequestsByPeriod")
    public ResponseEntity<List<RequestDTO>> getManagerStatisticsOfClosedRequestsByPeriod(@RequestParam String beginDate,
                                                                                         @RequestParam String endDate,
                                                                                         @RequestParam int id) {
        LocalDate start = LocalDate.parse(beginDate, formatter);
        LocalDate end = LocalDate.parse(endDate, formatter);
        return new ResponseEntity<>(reportService.getManagerStatisticsOfClosedRequestsByPeriod(start, end, id), HttpStatus.OK);
    }
}