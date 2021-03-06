package com.overseer.controller;

import com.overseer.dto.RequestDTO;
import com.overseer.model.enums.ProgressStatus;
import com.overseer.service.ReportService;
import com.overseer.service.RequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller provides api for creating, getting reports.
 */
@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final RequestService requestService;
    private final ReportService reportService;

    /**
     * Handle request to download an Excel document.
     */
    @RequestMapping(value = "/request", method = RequestMethod.GET)
    public ResponseEntity<byte[]> download(@RequestParam String id) {

        Long requestId = Long.valueOf(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/pdf"));
        String filename = "report.pdf";
        headers.setContentDispositionFormData(filename, filename);
        return new ResponseEntity<>(reportService.generateRequestPDFReport(requestId), headers, HttpStatus.OK);
    }

    //<editor-fold defaultstate="collapsed" desc="methods for manager reports">

    /**
     * Method gets pdf report for office manager.
     *
     * @return bytes with reporting data.
     */
    @RequestMapping(value = "/managerPDFReport", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getManagerPDFReport(@RequestParam String beginDate,
                                                      @RequestParam String endDate,
                                                      @RequestParam int id,
                                                      @RequestParam String encryptedEmail) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/pdf"));
        String filename = "report.pdf";
        headers.setContentDispositionFormData(filename, filename);
        return new ResponseEntity<>(reportService.generateManagerPDFReport(beginDate, endDate, id, encryptedEmail), headers, HttpStatus.OK);
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
        return new ResponseEntity<>(reportService.getManagerStatisticsOfClosedRequestsByPeriod(beginDate, endDate, id), HttpStatus.OK);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="methods for admin reports">

    /**
     * Method gets pdf report for admin.
     *
     * @return bytes with reporting data.
     */
    @RequestMapping(value = "/adminPDFReport", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getAdminPDFReport(@RequestParam String beginDate,
                                                    @RequestParam String endDate,
                                                    @RequestParam int countTop,
                                                    @RequestParam String encryptedEmail) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/pdf"));
        String filename = "report.pdf";
        headers.setContentDispositionFormData(filename, filename);
        return new ResponseEntity<>(reportService.generateAdminPDFReport(beginDate, endDate, countTop, encryptedEmail), headers, HttpStatus.OK);
    }

    /**
     * Gets list of request transfer objects which created in the same period.
     *
     * @param beginDate date from
     * @param endDate   date to
     * @return return list of requestDTO from one period of time
     */
    @GetMapping("/getAllStatisticsOfFreeRequestsByPeriod")
    public ResponseEntity<List<RequestDTO>> getAllStatisticsOfFreeRequestsByPeriod(@RequestParam String beginDate,
                                                                                   @RequestParam String endDate) {
        return new ResponseEntity<>(reportService.getAllStatisticsOfFreeRequestsByPeriod(beginDate, endDate), HttpStatus.OK);
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
        return new ResponseEntity<>(reportService.getAllStatisticsOfClosedRequestsByPeriod(beginDate, endDate), HttpStatus.OK);
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
                                                                                    @RequestParam String endDate,
                                                                                    @RequestParam int countTop) {
        List<RequestDTO> topManagers = requestService.findBestManagersByPeriod(beginDate, endDate, ProgressStatus.CLOSED.getId(), countTop);
        return new ResponseEntity<>(topManagers, HttpStatus.OK);
    }

    //</editor-fold >

}