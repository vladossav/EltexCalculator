package com.example.eltexcalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import java.util.Objects;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainActivityViewModel vm = new ViewModelProvider(this).get(MainActivityViewModel.class);

        TextView res = (TextView) findViewById(R.id.result_txt);
        TextView temp = (TextView) findViewById(R.id.temp_txt);
        Button btn1 = (Button) findViewById(R.id.button1);
        Button btn2 = (Button) findViewById(R.id.button2);
        Button btn3 = (Button) findViewById(R.id.button3);
        Button btn4 = (Button) findViewById(R.id.button4);
        Button btn5 = (Button) findViewById(R.id.button5);
        Button btn6 = (Button) findViewById(R.id.button6);
        Button btn7 = (Button) findViewById(R.id.button7);
        Button btn8 = (Button) findViewById(R.id.button8);
        Button btn9 = (Button) findViewById(R.id.button9);
        Button btn0 = (Button) findViewById(R.id.button0);
        Button btn_comma = (Button) findViewById(R.id.button_comma);
        Button btn_plus_minus = (Button) findViewById(R.id.button_plus_minus);
        Button btn_ac = (Button) findViewById(R.id.button_AC);
        Button btn_percent = (Button) findViewById(R.id.button_percent);
        Button btn_multiply = (Button) findViewById(R.id.button_multiply);
        Button btn_plus = (Button) findViewById(R.id.button_plus);
        Button btn_minus = (Button) findViewById(R.id.button_minus);
        Button btn_divide = (Button) findViewById(R.id.button_divide);
        Button btn_equal = (Button) findViewById(R.id.button_equal);

        vm.numberString.observe(this, num -> {
            if (Objects.equals(num, "0")) btn_ac.setText("AC");
            else btn_ac.setText("C");
            res.setText(num);
        });
        vm.tempText.observe(this, expr -> {
            temp.setText(expr);
        });

        //orange keys
        btn_multiply.setOnClickListener(view -> vm.setOperation(vm.MULTIPLY_CODE));
        btn_divide.setOnClickListener(view -> vm.setOperation(vm.DIVIDE_CODE));
        btn_plus.setOnClickListener(view -> vm.setOperation(vm.PLUS_CODE));
        btn_minus.setOnClickListener(view -> vm.setOperation(vm.MINUS_CODE));
        btn_equal.setOnClickListener(view -> vm.equal());

        //light_gray keys
        btn_ac.setOnClickListener(view -> vm.ac());
        btn_plus_minus.setOnClickListener(view -> vm.plusMinus());
        btn_percent.setOnClickListener(view -> vm.percent());
        btn_comma.setOnClickListener(view -> vm.addDot());

        //num keys
        btn0.setOnClickListener(view -> vm.addDigit(0));
        btn1.setOnClickListener(view -> vm.addDigit(1));
        btn2.setOnClickListener(view -> vm.addDigit(2));
        btn3.setOnClickListener(view -> vm.addDigit(3));
        btn4.setOnClickListener(view -> vm.addDigit(4));
        btn5.setOnClickListener(view -> vm.addDigit(5));
        btn6.setOnClickListener(view -> vm.addDigit(6));
        btn7.setOnClickListener(view -> vm.addDigit(7));
        btn8.setOnClickListener(view -> vm.addDigit(8));
        btn9.setOnClickListener(view -> vm.addDigit(9));
    }
}