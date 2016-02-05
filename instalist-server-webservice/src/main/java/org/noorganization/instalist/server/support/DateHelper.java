package org.noorganization.instalist.server.support;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateHelper {
    public static Date parseDate(String _dateString) {
        SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        try {
            return parser.parse(_dateString);
        } catch (ParseException _e) {
            return null;
        }
    }

    public static String writeDate(Date _date) {
        SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        return parser.format(_date);
    }
}
