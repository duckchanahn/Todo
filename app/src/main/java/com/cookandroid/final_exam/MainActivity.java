package com.cookandroid.final_exam;

        import android.os.Bundle;
        import android.view.View;
        import  androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    public View first_execute, execute_layout, todayResult;
    public Execute execute;

    @Override
    public void onCreate(Bundle savedInstanceState) { // 날짜 별로 다르게 해야됨!!!
        super.onCreate(savedInstanceState);

        this.first_execute = View.inflate(this, R.layout.first_execute, null);
        this.execute_layout = View.inflate(this, R.layout.main, null);
        this.todayResult = View.inflate(this, R.layout.todayresult, null);

        this.execute = new Execute();
        this.execute.associate(this, this.first_execute, this.execute_layout, this.todayResult);
        this.execute.execute();
    }
}

