package com.overseer.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.overseer.controller.RequestController;
import com.overseer.service.RequestService;
import lombok.Data;

/**
 * DTO class for request filtering params on client side data table representing.
 * The {@link RequestController#searchRequests} takes an object with the searchRequests parameters and delegates it
 * to the {@link RequestService#searchRequests(RequestSearchDTO)} for building the sql 'WHERE' conditions.
 */
@Data
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, isGetterVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.ANY)
@SuppressWarnings("PMD.UnusedPrivateField")
public class RequestSearchDTO {
    private String title;
    private String dateOfCreation;
    private int estimate;
    private String priority;
    private String progress;
    private String reporterName;
    private String assigneeName;
    private int limit;
}
