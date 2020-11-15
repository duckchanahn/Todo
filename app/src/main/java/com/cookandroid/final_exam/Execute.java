package com.cookandroid.final_exam;

import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Execute {

    private String fileName_today;
    private MainActivity mainActivity;

    private String userName;

    private int calender_id;

    private EditText userName_input;
    private Button startApplicationFirst;

    private List<view_Calender> list;

//    private String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Todo";
    private String fileroot = "/data/data/com.cookandroid.final_exam/Todo/userName.txt";
    private String calenderFileRoot = "/data/data/com.cookandroid.final_exam/Todo/calender.txt";

    public View first_execute, execute_layout, todayResult_layout;

    public TodayResult todayResult;

    public Execute() {
        this.calender_id = 1;
        this.todayResult = new TodayResult();
        this.list = new ArrayList<view_Calender>();
    }

    public void associate(MainActivity mainActivity, View first_execute, View execute_layout, View todayResult_layout) {
        this.mainActivity = mainActivity;
        this.first_execute = first_execute;
        this.execute_layout = execute_layout;
        this.todayResult_layout = todayResult_layout;
        this.todayResult.associate(this.mainActivity, this.first_execute, this.execute_layout, this.todayResult_layout);
    }


    public void execute() {
        try {
            File file = new File(fileroot);
            Scanner scan = new Scanner(file);

            this.mainActivity.setContentView(this.execute_layout);
            Animation animation = AnimationUtils.loadAnimation(mainActivity,R.anim.slowlyshow);
            this.execute_layout.setAnimation(animation);

            if (scan.hasNext()) {
                Log.v("파일을 찾았습니다", scan.hasNext()+"유저네임");

                TextView welcomeUser = execute_layout.findViewById(R.id.welcomeUser);
                welcomeUser.setText(scan.next() + "님 반갑습니다.");

                Button startApplicationFirst = execute_layout.findViewById(R.id.startApplicationFirst);
                startApplicationFirst.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loadCalender();
                        todayResult.setList(list);
                        todayResult.execute_TodayResult();
                        todayResult.addAllRow(list);
                    }
                });


            } else {
                Log.v("파일을 찾았습니다", "근데 이름을 못읽네요..");
                this.first_Execute();
            }
            scan.close();

        } catch (FileNotFoundException e) {
            Log.v("파일을 찾을 수 없습니다", userName+"유저네임");

        }
    }

    private void first_Execute() { // 앱 설치 후 처음 접속했을 때
        // 파일 생성 이름 입력

        Animation animation = AnimationUtils.loadAnimation(mainActivity,R.anim.slowlyshow);

        this.first_execute.setAnimation(animation);

        this.userName_input = this.first_execute.findViewById(R.id.userNameFirst);
        this.startApplicationFirst = this.first_execute.findViewById(R.id.startApplicationFirst);

        this.startApplicationFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userName =  userName_input.getText()+""; // 입력받은 이름
                if(!userName.equals("")) {

                    try {
                        File file = new File(fileroot);
                        FileWriter scan = new FileWriter(file) ;
                        scan.write(userName);
                        scan.close();
//                        Scanner scanner = new Scanner(file);
//                        Log.v("적는데 왜 안적히니 흑", scanner.hasNext()+"");
                    } catch (FileNotFoundException e) {}
                    catch (IOException e) {}


                    todayResult.execute_TodayResult();

                } else {
                    Toast.makeText(mainActivity, "이름을 입력해주세요", Toast.LENGTH_SHORT).show();
                }

            }
        });
        this.todayResult.setList(null);
        this.mainActivity.setContentView(this.first_execute);
    }

    private List<view_Calender> loadCalender() { // 실행시 이전 캘린더 받아오기
        File file = new File(calenderFileRoot);
        view_Calender view_calender;

        try {
            Scanner scan = new Scanner(file);

        while(scan.hasNext()) {
            view_calender = new view_Calender();
            view_calender.setCalenderDesec(scan.nextLine());
            list.add(view_calender);
        }

        Log.v("잘 들어갔나?", list.size()+"잘들어갔나?");

        scan.close();
        } catch (FileNotFoundException e) {}
        return list;
    }
}