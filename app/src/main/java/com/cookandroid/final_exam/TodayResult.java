package com.cookandroid.final_exam;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class TodayResult {
    private String fileName_today;
    private MainActivity mainActivity;

    private int check;

    private List<CheckBox> checkBoxList;
    private List<view_Calender> list_Calender;
    private LinearLayout calender;

    private TextView percent_Text;
    private ImageView percent_Image;

    private String userName;

    private int calender_id;
    private List<Integer> calender_id_all;

    private EditText userName_input;
    private Button startApplicationFirst;

    private Calculator_Time calculator_time;
    private Execute execute;

    private TextView inputUserName;
    private EditText userNameFirst;
    private String name;

    private LinearLayout todayResult;
//    private TimePickerDialog.OnTimeSetListener mTimeSetListener;

    private ImageButton menu_button;

    private String t_Hour, t_Minute;
    private int sHour, sMinute;
    private int eHour, eMinute;

    private TextView startTime_addCalender, endTime_addCalender;
    private EditText desc_addCalender;
    private View dialogView, removeView;

    private AlertDialog.Builder dial, removeDial;


    private String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Todo";

    private int index;
    private view_Calender change_calender;

    public View first_execute, execute_layout, todayResult_layout;

    public TodayResult() {
        this.index = -1;
        this.calender_id_all = new ArrayList<Integer>();
    }

    public void associate(MainActivity mainActivity, View first_execute, View execute_layout, View todayResult_layout) {
        this.mainActivity = mainActivity;
        this.first_execute = first_execute;
        this.execute_layout = execute_layout;
        this.todayResult_layout = todayResult_layout;
    }

    public void setList(List<view_Calender> list_Calender) {

        if (list_Calender == null) {
            this.list_Calender = new ArrayList<view_Calender>();
        } else {
            this.list_Calender = list_Calender;
            for (view_Calender view_calender : list_Calender)
                this.calender_id_all.add(view_calender.getID());
            if (list_Calender.size() > 0)
                this.calender_id = calender_id_all.get(calender_id_all.size() - 1) + 1;
        }
    }

    public void execute_TodayResult() {
        mainActivity.setContentView(todayResult_layout);

        this.calculator_time = new Calculator_Time();

        this.todayResult = todayResult_layout.findViewById(R.id.todayResult);

        this.calender = todayResult_layout.findViewById(R.id.list_Calender);
        this.percent_Image = todayResult_layout.findViewById(R.id.percent_Image);
        this.percent_Text = todayResult_layout.findViewById(R.id.percent_Text);
        this.menu_button = todayResult_layout.findViewById(R.id.menu);
        this.menu_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(mainActivity.getBaseContext(), v);
                MenuInflater inflater = popupMenu.getMenuInflater();
                Menu menu = popupMenu.getMenu();
                inflater.inflate(R.menu.main_menu, menu);

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.addCalender_menu) {
//                            Log.v("추가", "일정");

                            dialogView = View.inflate(mainActivity, R.layout.addcalender, null);

                            startTime_addCalender = dialogView.findViewById(R.id.startTime_addCalender);
                            endTime_addCalender = dialogView.findViewById(R.id.endTime_addCalender);
                            desc_addCalender = dialogView.findViewById(R.id.desc_addCalender);
                            dial = new AlertDialog.Builder(mainActivity);
                            dial.setView(dialogView);

                            startTime_addCalender.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    TimePickerDialog.OnTimeSetListener myTimeListener = new TimePickerDialog.OnTimeSetListener() {
                                        @Override
                                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                            if (view.isShown()) {

                                                sHour = hourOfDay;
                                                sMinute = minute;

                                                if(hourOfDay < 10) {
                                                    String temp = hourOfDay+"";
                                                    t_Hour = "0" + temp;
                                                } else {
                                                    t_Hour = hourOfDay+"";
                                                }

                                                if(minute < 10) {
                                                    String temp = minute+"";
                                                    t_Minute = "0" + temp;
                                                } else {
                                                    t_Minute = minute+"";
                                                }

                                                if(sHour > eHour) {
                                                    eHour = sHour;
                                                    endTime_addCalender.setText(t_Hour + ":" + t_Minute);
                                                } else if(sHour == eHour) {
                                                    eMinute = sMinute;
                                                    if(sMinute > eMinute) endTime_addCalender.setText(t_Hour + ":" + t_Minute);
                                                }

                                                startTime_addCalender.setText(t_Hour + ":" + t_Minute);
                                            }
                                        }
                                    };

                                    TimePickerDialog timePickerDialog = new TimePickerDialog(mainActivity, android.R.style.Theme_Holo_Light_Dialog_NoActionBar, myTimeListener, sHour, sMinute, true);
                                    timePickerDialog.setTitle("시작 시간");
                                    timePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                                    timePickerDialog.show();
                                }
                            });

                            endTime_addCalender.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    TimePickerDialog.OnTimeSetListener myTimeListener = new TimePickerDialog.OnTimeSetListener() {
                                        @Override
                                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                            if (view.isShown()) {
                                                eHour = hourOfDay;
                                                eMinute = minute;

                                                if(hourOfDay < 10) {
                                                    String temp = hourOfDay+"";
                                                    t_Hour = "0" + temp;
                                                } else {
                                                    t_Hour = hourOfDay+"";
                                                }

                                                if(minute < 10) {
                                                    String temp = minute+"";
                                                    t_Minute = "0" + temp;
                                                } else {
                                                    t_Minute = minute+"";
                                                }

                                                if(sHour > eHour) {
                                                    sHour = eHour;
                                                    sMinute = eMinute;
                                                    startTime_addCalender.setText(t_Hour + ":" + t_Minute);
                                                } else if(sHour == eHour) {
                                                    if(sMinute > eMinute) startTime_addCalender.setText(t_Hour + ":" + t_Minute);
                                                }

                                                endTime_addCalender.setText(t_Hour + ":" + t_Minute);
                                            }
                                        }
                                    };

                                    TimePickerDialog timePickerDialog = new TimePickerDialog(mainActivity, android.R.style.Theme_Holo_Light_Dialog_NoActionBar, myTimeListener, eHour, eMinute, true);
                                    timePickerDialog.setTitle("종료 시간");
                                    timePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                                    timePickerDialog.show();
                                }
                            });

                            dial.setPositiveButton("취소", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });

                            dial.setNegativeButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    view_Calender view_calender = new view_Calender();
                                    view_calender.setID(calender_id);
                                    calender_id_all.add(calender_id);
                                    calender_id++;
                                    view_calender.setCalender_Time(startTime_addCalender.getText() + " - " + endTime_addCalender.getText());
                                    view_calender.setsTime(startTime_addCalender.getText()+"");
                                    view_calender.seteTime(endTime_addCalender.getText()+"");
                                    view_calender.setCalender_Desc(desc_addCalender.getText()+"");
                                    list_Calender.add(view_calender);
                                    makeCalender(list_Calender);
                                    addAllRow(list_Calender);

                                    sHour = 0;
                                    sMinute = 0;
                                    eHour = 0;
                                    eMinute = 0;
                                }
                            });

                            dial.show();
//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
                        } else if (item.getItemId() == R.id.removeAllCalender_menu) {
//                            Log.v("제거 ", "일정");
                            calender.removeAllViews();
                            list_Calender.clear();
                            checkBoxList.clear();
                            check = 0;
                            setBackGround();
                            makeCalender(list_Calender);
//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
                        } else if (item.getItemId() == R.id.removeCalender_menu) {
//                            Log.v("제거 ", "일정");
                            if(index == -1) {
                                Toast.makeText(mainActivity, "일정을 선택해주세요", Toast.LENGTH_SHORT).show();
                            } else {
                                CheckBox temp = calender.findViewById(index).findViewById(R.id.calender_check);
                                if (temp.isChecked()) check--;
                                try {
                                    for (int i = 0; i < list_Calender.size(); i++) {
                                        if (list_Calender.get(i).getID() == index) {
                                            list_Calender.remove(i);
                                            checkBoxList.remove(i);
                                        }
                                    }
                                } catch (NullPointerException e) {
                                }
                                setBackGround();
                                makeCalender(list_Calender);

                                addAllRow(list_Calender);
                                index = -1;
                            }
//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
                        } else if (item.getItemId() == R.id.changeCalender_menu) {
                            if(index == -1) {
                                Toast.makeText(mainActivity, "일정을 선택해주세요", Toast.LENGTH_SHORT).show();
                            } else {

                                try {
                                    for (int i = 0; i < list_Calender.size(); i++) {
                                        if (list_Calender.get(i).getID() == index) {
                                            change_calender = list_Calender.get(i);
                                        }
                                    }
                                } catch (NullPointerException e) {}

                                dialogView = View.inflate(mainActivity, R.layout.addcalender, null);

                                startTime_addCalender = dialogView.findViewById(R.id.startTime_addCalender);
                                startTime_addCalender.setText(change_calender.getsTime());
                                endTime_addCalender = dialogView.findViewById(R.id.endTime_addCalender);
                                endTime_addCalender.setText(change_calender.geteTime());
                                desc_addCalender = dialogView.findViewById(R.id.desc_addCalender);
                                desc_addCalender.setText(change_calender.getCalender_Desc());
                                dial = new AlertDialog.Builder(mainActivity);
                                dial.setView(dialogView);

                                startTime_addCalender.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        TimePickerDialog.OnTimeSetListener myTimeListener = new TimePickerDialog.OnTimeSetListener() {
                                            @Override
                                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                                if (view.isShown()) {

                                                    sHour = hourOfDay;
                                                    sMinute = minute;

                                                    if(hourOfDay < 10) {
                                                        String temp = hourOfDay+"";
                                                        t_Hour = "0" + temp;
                                                    } else {
                                                        t_Hour = hourOfDay+"";
                                                    }

                                                    if(minute < 10) {
                                                        String temp = minute+"";
                                                        t_Minute = "0" + temp;
                                                    } else {
                                                        t_Minute = minute+"";
                                                    }

                                                    if(sHour > eHour) {
                                                        eHour = sHour;
                                                        endTime_addCalender.setText(t_Hour + ":" + t_Minute);
                                                    } else if(sHour == eHour) {
                                                        eMinute = sMinute;
                                                        if(sMinute > eMinute) endTime_addCalender.setText(t_Hour + ":" + t_Minute);
                                                    }

                                                    startTime_addCalender.setText(t_Hour + ":" + t_Minute);
                                                }
                                            }
                                        };

                                        TimePickerDialog timePickerDialog = new TimePickerDialog(mainActivity, android.R.style.Theme_Holo_Light_Dialog_NoActionBar, myTimeListener, sHour, sMinute, true);
                                        timePickerDialog.setTitle("시작 시간");
                                        timePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                                        timePickerDialog.show();
                                    }
                                });

                                endTime_addCalender.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        TimePickerDialog.OnTimeSetListener myTimeListener = new TimePickerDialog.OnTimeSetListener() {
                                            @Override
                                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                                if (view.isShown()) {
                                                    eHour = hourOfDay;
                                                    eMinute = minute;

                                                    if(hourOfDay < 10) {
                                                        String temp = hourOfDay+"";
                                                        t_Hour = "0" + temp;
                                                    } else {
                                                        t_Hour = hourOfDay+"";
                                                    }

                                                    if(minute < 10) {
                                                        String temp = minute+"";
                                                        t_Minute = "0" + temp;
                                                    } else {
                                                        t_Minute = minute+"";
                                                    }

                                                    if(sHour > eHour) {
                                                        sHour = eHour;
                                                        sMinute = eMinute;
                                                        startTime_addCalender.setText(t_Hour + ":" + t_Minute);
                                                    } else if(sHour == eHour) {
                                                        if(sMinute > eMinute) startTime_addCalender.setText(t_Hour + ":" + t_Minute);
                                                    }

                                                    endTime_addCalender.setText(t_Hour + ":" + t_Minute);
                                                }
                                            }
                                        };

                                        TimePickerDialog timePickerDialog = new TimePickerDialog(mainActivity, android.R.style.Theme_Holo_Light_Dialog_NoActionBar, myTimeListener, eHour, eMinute, true);
                                        timePickerDialog.setTitle("종료 시간");
                                        timePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                                        timePickerDialog.show();
                                    }
                                });

                                dial.setPositiveButton("취소", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });

                                dial.setNegativeButton("확인", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        for (int i = 0; i < list_Calender.size(); i++) {
                                            if (list_Calender.get(i).getID() == index) {
                                                list_Calender.remove(i);
                                                checkBoxList.remove(i);
                                            }
                                        }

                                        change_calender.setCalender_Time(startTime_addCalender.getText() + " - " + endTime_addCalender.getText());
                                        change_calender.setsTime(startTime_addCalender.getText()+"");
                                        change_calender.seteTime(endTime_addCalender.getText()+"");
                                        change_calender.setCalender_Desc(desc_addCalender.getText()+"");
                                        list_Calender.add(change_calender);
                                        makeCalender(list_Calender);
                                        addAllRow(list_Calender);

                                        sHour = 0;
                                        sMinute = 0;
                                        eHour = 0;
                                        eMinute = 0;
                                    }
                                });

                                dial.show();
                                makeCalender(list_Calender);
                                addAllRow(list_Calender);
                            }
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });

        this.checkBoxList = new ArrayList<CheckBox>();

        this.check = 0;
    }


    public void addRow(final view_Calender one_Calender) { // 일정 하나 추가

        LayoutInflater inflater = (LayoutInflater) mainActivity.getSystemService(mainActivity.LAYOUT_INFLATER_SERVICE);
        final LinearLayout calender_one = (LinearLayout) inflater.inflate(R.layout.view_calender, null, true);
        calender_one.setId(one_Calender.getID());
        TextView time = calender_one.findViewById(R.id.time);
        time.setText(one_Calender.getCalender_Time());
        TextView text = calender_one.findViewById(R.id.desc);
        text.setText(one_Calender.getCalender_Desc());
        CheckBox checkBox = calender_one.findViewById(R.id.calender_check);
        if (one_Calender.getChecked()) {
            checkBox.setChecked(true);
            check++;
        } else {
            checkBox.setChecked(false);
        }
        checkBox.setOnClickListener(new View.OnClickListener() { // 체크하면 체크 여부도 파일에 적을 것!
            @Override
            public void onClick(View v) {
                if(((CheckBox)v).isChecked()) {
                    check++;
                    one_Calender.setChecked(true);
                    makeCalender(list_Calender);
                }
                else {check--;
                one_Calender.setChecked(false);
                    makeCalender(list_Calender);
                }
                setBackGround();
            }
        });
        if(one_Calender.getChecked()) checkBox.setChecked(true);

        this.checkBoxList.add(checkBox);
//        this.list_Calender.add(one_Calender);

        Log.v("@@", calender_one.getId()+"@@@#!@#!@#!@#!@#!@#!@#@!#!@#!@#!#!@#!@#!@#!#!#!#!#!#!@#!@");

        this.calender.addView(calender_one);

        this.calender.findViewById(calender_one.getId()).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                index = one_Calender.getID();

                for(int i = 0; i < calender_id; i++) {
                    if(calender.findViewById(i) != null) {
                        calender.findViewById(i).setBackground(null);
                    }
                }

                calender.findViewById(calender_one.getId()).setBackgroundColor(Color.GRAY);
                calender.findViewById(calender_one.getId()).setAlpha(0.9f);

                return false;
            }
        });

        this.setBackGround();
    }


    public void addAllRow(List<view_Calender> list_Calender) { // 일정 모두 추가
        Collections.sort(list_Calender);
//        if (this.calender != null)
            this.calender.removeAllViews();

        for (int i = checkBoxList.size()-1; 0 <= i ; i--) this.checkBoxList.remove(i);
        this.check = 0;

        for(view_Calender view_Calender: list_Calender) {
            addRow(view_Calender);
        }
    }

    private void makeCalender(List<view_Calender> view_Calender) { // 일정 만들었을 때 파일에 입력 + 화면에 추가
        String calenderFileRoot = "/data/data/com.cookandroid.final_exam/Todo/calender.txt";
        File file = new File(calenderFileRoot);
        // 파일에 입력
    try {
        String calenderDesc = "";
        for (view_Calender view_calender : view_Calender) {
            calenderDesc = calenderDesc + view_calender.getCalenderDesc() +"\r\n";
        }

        Log.v("대박", calenderDesc+"파일에잘들아가나");

        FileWriter writer = new FileWriter(file);
        writer.write(calenderDesc);
//        for (view_Calender view_calender : view_Calender) {
//            writer.write(view_calender.getCalenderDesc());
//            writer.newLine();
//        }
        writer.close();
    } catch (IOException e) {}
    }

    private void setBackGround() { // 퍼센트 조절
        int percent;
        if (this.checkBoxList.size() > 0) {
            percent = 100 * check / this.checkBoxList.size();
        } else {
            percent = 0;
        }
        Log.v("체크박스", this.checkBoxList.size()+"총사이즈");
        Log.v("체크박스", this.check+"선택한 사이즈");
        percent_Text.setText(percent + "%");
        if(percent <= 30) {
            this.percent_Image.setImageResource(R.drawable.resulttodaycirclered);
            this.todayResult_layout.setBackgroundColor(Color.parseColor("#FFE6F8"));
        } else if(30 < percent && percent < 70) {
            this.percent_Image.setImageResource(R.drawable.resulttodaycircleyellow);
            this.todayResult_layout.setBackgroundColor(Color.parseColor("#FFFBE6"));
        } else if(70 <= percent) {
            this.percent_Image.setImageResource(R.drawable.resulttodaycircleblue);
            this.todayResult_layout.setBackgroundColor(Color.parseColor("#E6FDFF"));
        }
    }

}
