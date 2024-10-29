package com.example.sprintproject.model;

public interface DateComparison {
    /**
     * Method to check if the start date is before the end date
     * @param startDateStr Start date
     * @param endDateStr End date
     * @return boolean
     */
    boolean isStartDateBeforeEndDate(String startDateStr, String endDateStr);
}
