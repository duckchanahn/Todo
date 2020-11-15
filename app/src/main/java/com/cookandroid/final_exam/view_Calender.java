package com.cookandroid.final_exam;

import android.util.Log;

public class view_Calender implements Comparable<view_Calender>{

    private int id;
    private String calender_Time, calender_Desc;
    private String sTime, eTime;
    private int sHour, sMinute;
    private int eHour, eMinute;
    private boolean isChecked = false;


    public int compareTo(view_Calender view_Calender) {
        return this.calender_Time.compareTo(view_Calender.getCalender_Time());
    }

    public int getID() {return id;}
    public void setID(int id) {this.id = id;}

    public int getsHour() {return sHour;}
    public void setsHour(int sHour) {this.sHour = sHour;}

    public int getsMinute() {return sMinute;}
    public void setsMinute(int sMinute) {this.sMinute = sMinute;}

    public int geteHour() {return eHour;}
    public void seteHour(int eHour) {this.eHour = eHour;}

    public int geteMinute() {return eMinute;}
    public void seteMinute(int eMinute) {this.eMinute = eMinute;}

    public String getsTime() {return this.sTime;}
    public void setsTime(String sTime) {this.sTime = sTime;}

    public String geteTime() {return this.eTime;}
    public void seteTime(String eTime) {this.eTime = eTime;}

    public String getCalender_Time() {return this.calender_Time;}
    public void setCalender_Time(String calender_Time) {this.calender_Time = calender_Time;}

    public String getCalender_Desc() {return this.calender_Desc;}
    public void setCalender_Desc(String calender_Desc) {this.calender_Desc = calender_Desc;}

    public boolean getChecked() { return this.isChecked; }
    public void setChecked(boolean isChecked) {this.isChecked = isChecked;}

    public void setCalenderDesec(String desc) {
        String[] desc_split = desc.split("/");
        this.calender_Time = desc_split[0];
        this.calender_Desc = desc_split[1];
        if (desc_split[2].equals("true")) {
            this.isChecked = true;
        }

    }
    public String getCalenderDesc() {
        return calender_Time + "/" + calender_Desc + "/" + isChecked;
    }
}
