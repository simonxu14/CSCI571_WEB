package com.simon.hw9;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Simon on 12/1/16.
 */
public class TimeFormat {
    public String parseDateToMMMddy(String time) {
        String inputPattern = "yyyy-MM-dd";
        String outputPattern = "MMM dd,y";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }


}
