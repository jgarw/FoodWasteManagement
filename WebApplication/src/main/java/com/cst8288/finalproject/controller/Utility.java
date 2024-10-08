package com.cst8288.finalproject.controller;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Class that stores static utility methods useful for other classes
 */
public class Utility {
    /**
     * Takes a date String (such as from an HTML form) and returns a java.sql.Date object
     * @param dateString
     * @return Date object of type java.sql.Date
     */
    public static Date parseDate(String dateString) {
        // Expected date format from HTML form
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        // First we instantiate a java.util.Date object to use with SimpleDateFormat before convert to java.sql.Date
        java.util.Date utilDate = null;
        try {
            // Parse the string into a java.util.Date object
            utilDate = dateFormat.parse(dateString);
        } catch (ParseException e) {
            System.err.println("Date parsing failed: " + e.getMessage());
        }

        // Converting java.util.Date to java.sql.Date
        return new Date(utilDate.getTime());
    }
}
