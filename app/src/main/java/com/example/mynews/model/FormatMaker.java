package com.example.mynews.model;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Set;

/**
 * Class that contains different methods that format some data.
 */
public class FormatMaker {

    /**
     * This function is use to set min date and max date for the DatePicker.
     *
     * @param date A string that represent a date in format dd/mm/yyyy.
     *             For example "21/08/2004".
     * @return if the date passed in argument has a good format then return the date in millisecond,
     * else return the current time in millisecond.
     * The result of this function is independent of the time zone.
     */
    public static long stringDateToMillis(String date) {
        String[] arrayDate = date.split("/");

        Calendar calendar = new GregorianCalendar();
        if (arrayDate.length == 3) {
            calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(arrayDate[0]));
            calendar.set(Calendar.MONTH, Integer.parseInt(arrayDate[1]) - 1);
            calendar.set(Calendar.YEAR, Integer.parseInt(arrayDate[2]));
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            long dateInMillis = calendar.getTimeInMillis();
            return dateInMillis + calendar.getTimeZone().getOffset(dateInMillis);
        } else return System.currentTimeMillis();
    }

    /**
     * This function is used to format begin_date and end_date for the search NYT API.
     *
     * @param date A string that represent a date in format dd/mm/yyyy.
     *             For example "21/08/2004".
     * @return if the date passed in argument has a good format
     * then return the date in the format "yyyymmdd", else return "".
     * For example d8DateFromat("21/08/2004") = 20040821.
     */
    public static String d8DateFormat(String date) {
        String[] arrayDate = date.split("/");
        if (arrayDate.length == 3) {
            String month = arrayDate[1];
            String day = arrayDate[0];
            return arrayDate[2] + month + day;
        } else return "";
    }

    /**
     * This function is used to format the filter_query for the search NYT API.
     *
     * @param topics A set of string that represents the name of the topics that are checked
     *               for the search activity.
     * @return the string "news_desk:(\"string1\" \"string2\" \"string3\")" where
     * strings inside the parentheses are all string in the set of string topics.
     * The number of string in the parentheses depend on the number of string in
     * the parameter topics.
     */
    public static String filterQueryFormat(Set<String> topics) {
        boolean first = true;
        StringBuilder result = new StringBuilder("news_desk:(");
        for (String topic : topics) {
            if (first) {
                result.append("\"");
                first = false;
            } else result.append(" \"");
            result.append(topic);
            result.append("\"");
        }
        return result.append(")").toString();
    }
}
