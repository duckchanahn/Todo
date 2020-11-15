package com.cookandroid.final_exam;

import java.util.List;

public class Calculator_Time {

    private int sHour, sMinute;
    private int eHour, eMinute;





    // 겹치게 만들 수 있어야할듯 --> 이거 하면 안될드ㅡ으으읏
    public boolean overlapTime(int sHour, int sMinute, int eHour, int eMinute, List<view_Calender> view_calenders) {

        boolean overlap = false;

        for(view_Calender view : view_calenders) {
            if((view.getsHour() < sHour && sHour < view.geteHour()) || (view.getsHour() < eHour && eHour < view.geteHour())) {
                overlap = true;
            } else if((sHour < view.getsHour() && view.getsHour() < sHour) || (eHour < view.getsHour() && view.getsHour() < eHour)) {
                overlap = true;
            } else if(view.getsHour() == sHour) {

            } else if(view.geteHour() == sHour) {

            } else if(view.getsHour() == eHour) {

            } else if(view.geteHour() == eHour) {

            }
        }
        return overlap;
    }

}
