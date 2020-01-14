package com.example.mynews.model;

import android.widget.CheckBox;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class FormatMaker {

    public long stringDateToMillis(String date) {
        String[] arrayDate = date.split("/");

        Calendar calendar = new GregorianCalendar();
        if (arrayDate.length == 3) {
            calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(arrayDate[0]));
            calendar.set(Calendar.MONTH, Integer.parseInt(arrayDate[1]) - 1);
            calendar.set(Calendar.YEAR, Integer.parseInt(arrayDate[2]));
            calendar.set(Calendar.HOUR_OF_DAY,0);
            calendar.set(Calendar.MINUTE,0);
            calendar.set(Calendar.SECOND,0);
            calendar.set(Calendar.MILLISECOND,0);
            long dateInMillis = calendar.getTimeInMillis();
            return dateInMillis + calendar.getTimeZone().getOffset(dateInMillis);
        } else return System.currentTimeMillis();
    }

    public String d8DateFormat(String date) {
        String[] arrayDate = date.split("/");
        if (arrayDate.length == 3) {
            String month = twoDigitFormat(arrayDate[1]);
            String day = twoDigitFormat(arrayDate[0]);
            return arrayDate[2] + month + day;
        } else return "";
    }

    private String twoDigitFormat(String number) {
        if (number.length() == 1) return "0" + number;
        else return number;
    }

    public String filterQueryFormat(CheckBox[] checkBoxes) {
        boolean first = true;
        StringBuilder result = new StringBuilder("news_desk:(");
        for (CheckBox checkBox : checkBoxes) {
            System.out.println("avant if");
            if (checkBox.isChecked()) {
                System.out.println("if check");
                if (first) {
                    result.append("\"");
                    first = false;
                }
                else result.append(" \"");
                result.append(checkBox.getText());
                result.append("\"");
            }
            System.out.println("fin if1");
        }
        return result.append(")").toString();
    }

}