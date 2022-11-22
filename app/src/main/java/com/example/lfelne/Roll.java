package com.example.lfelne;

import android.text.format.DateFormat;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class Roll implements Serializable
{
    private final String NAME;
    private final String ROLL_TYPE;
    private final int MAX_EXPOSURES;
    private int exposures;
    private final Date START_DATE;
    private Date end_date;

    public Roll(String input) {
        String[] input_parts = input.split("-");
        NAME = input_parts[0];
        ROLL_TYPE = input_parts[1];
        MAX_EXPOSURES = Integer.parseInt(input_parts[2]);
        exposures = Integer.parseInt(input_parts[3]);
        START_DATE = new Date(Long.parseLong(input_parts[4]));
        end_date = new Date(Long.parseLong(input_parts[5]));
    }

    @NonNull
    public String toString() {
        // In roll initialization format
        return NAME + "-" + ROLL_TYPE + "-" + MAX_EXPOSURES +
                "-" + exposures + "-" + getSTART_DATE() + "-" + getEnd_date();
    }



    public String getNAME() {
        return NAME;
    }

    public String getROLL_TYPE() {
        return ROLL_TYPE;
    }

    public int getMAX_EXPOSURES() {
        return MAX_EXPOSURES;
    }

    public int getExposures() {
        return exposures;
    }

    public void setExposures(int exposures) {
        this.exposures = exposures;
    }

    public long getSTART_DATE() {
        return START_DATE.getTime();
    }

    public String getStringSTART_DATE() {
        return (String) DateFormat.format("MM/dd/yy", START_DATE);
    }

    public long getEnd_date() {
        return end_date.getTime();
    }

    public String getStringEnd_date() {
        return (String) DateFormat.format("MM/dd/yy", end_date);
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }
}
