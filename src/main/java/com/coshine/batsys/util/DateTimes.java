package com.coshine.batsys.util;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Created by jia on 2015/7/25.
 */
public class DateTimes {

    public static final String TIMESTAMP_PATTERN = "yyyy-MM-dd HH:mm:ss SSS";
    public static final String DATE_PATTERN = "yyyy-MM-dd";
    public static final String TIME_PATTERN = "HH:mm:ss";

    public static final String timestamp(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern(TIMESTAMP_PATTERN));
    }

    public static final String nowTimestamp() {
        return timestamp(LocalDateTime.now());
    }
    
    public static final String date(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern(DATE_PATTERN));
    }

    public static final String nowDate() {
        return date(LocalDate.now());
    }
    
    public static final String time(LocalTime time) {
        return time.format(DateTimeFormatter.ofPattern(TIME_PATTERN));
    }

    public static final String nowTime() {
        return date(LocalDate.now());
    }
    
	public static final Date localDateToDate(LocalDate localDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = sdf.parse(localDate.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}
}
