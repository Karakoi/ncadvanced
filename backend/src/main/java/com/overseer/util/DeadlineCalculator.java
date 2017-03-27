package com.overseer.util;

import java.time.DayOfWeek;
import java.time.LocalDate;

/**
 * Calculator for deadline dates considering the weekends.
 */
public class DeadlineCalculator {

    /**
     * Calculates deadline date according to assign date and estimate time in working days.
     * @param start date of request assigning.
     * @param estimate estimate time in working days.
     * @return deadline date.
     */
    public static LocalDate getDeadline(LocalDate start, int estimate) {
        DayOfWeek currentDay;
        while (estimate > 1) {
            currentDay = start.getDayOfWeek();
            if (currentDay != DayOfWeek.SATURDAY && currentDay != DayOfWeek.SUNDAY) {
                estimate--;
            }
            start = start.plusDays(1);
        }
        return start;
    }
}
