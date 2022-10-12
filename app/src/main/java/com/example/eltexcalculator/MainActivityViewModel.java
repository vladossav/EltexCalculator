package com.example.eltexcalculator;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Objects;


public class MainActivityViewModel extends ViewModel {
    public MutableLiveData<String> numberString = new MutableLiveData<>("0");
    private MutableLiveData<Double> tempArg = new MutableLiveData<>(0.0);
    public MutableLiveData<String> tempText = new MutableLiveData<>("");
    public MutableLiveData<String> operationCode = new MutableLiveData<>("0");

    final String PLUS_CODE = "+";
    final String MINUS_CODE = "-";
    final String DIVIDE_CODE = "/";
    final String MULTIPLY_CODE = "*";

    private Double getNumStrAsDouble() {
        return Double.parseDouble(numberString.getValue());
    }
    public String getDoubleTempAsString() {
        Double s = tempArg.getValue();
        if (s.intValue() == s) return String.valueOf(s.intValue());
        else return s.toString();
    }

    public void clearAll() {
        operationCode.setValue("0");
        tempArg.setValue(0.0);
        numberString.setValue("0");
        tempText.setValue("");
    }

    public void setOperation(String code) {
        if (prepareOperation(code)) tempText.setValue(getDoubleTempAsString()+code);
    }

    public void ac() {
        if (Objects.equals(numberString.getValue(), "0")) {
            tempText.setValue("");
            clearAll();
        }
        else numberString.setValue("0");
    }


    public boolean prepareOperation(String code) {
        if (!Objects.equals(operationCode.getValue(), "0") && !numberString.getValue().equals("0")) {
            return false;
        }
        if (!Objects.equals(operationCode.getValue(), "0")) {
            operationCode.setValue(code);
            return true;
        }
        operationCode.setValue(code);
        if (tempArg.getValue() != 0) {
            tempArg.setValue(getOperationResult());
        }
        else tempArg.setValue(getNumStrAsDouble());
        numberString.setValue("0");
        return true;
    }

    public void calculate() {
        Double s = getOperationResult();
        tempArg.setValue(0.0);
        operationCode.setValue("0");
        if (s.intValue() == s) numberString.setValue(String.valueOf(s.intValue()));
        else numberString.setValue(s.toString());
    }

    private Double getOperationResult() {
        Double a = tempArg.getValue();
        Double b = getNumStrAsDouble();
        Double s = 0.0;
        switch (operationCode.getValue()) {
            case PLUS_CODE:  s = a + b; break;
            case MINUS_CODE: s = a - b; break;
            case MULTIPLY_CODE: s = a * b; break;
            case DIVIDE_CODE: s = a / b;break;
        }
        return s;
    }

    public void equal() {
        if (!Objects.equals(operationCode.getValue(), "0")) {
            String s = tempText.getValue() + numberString.getValue() + "=";
            tempText.setValue(s);
            calculate();
        }
    }

    public void addDigit(int n) {
        if (tempText.getValue().contains("=")) {
            clearAll();
        }
        String str = numberString.getValue();
        if (Objects.equals(str, "0")) str = "";
        if (Objects.equals(str, "0") && (n == 0)) str += ".";
        numberString.setValue(str + n);
    }

    public void plusMinus() {
        StringBuilder sb = new StringBuilder(numberString.getValue());
        if (sb.charAt(0) == '-') sb.deleteCharAt(0);
        else sb.insert(0,"-");
        numberString.setValue(sb.toString());
    }

    public void percent() {
        Double s = getNumStrAsDouble();
        s = s/100;
        if (s.intValue() == s) numberString.setValue(String.valueOf(s.intValue()));
        else numberString.setValue(String.valueOf(s));;
    }

    public void addDot() {
        String s = numberString.getValue();
        if (!s.contains(".")) numberString.setValue(s + "." );
    }
}
