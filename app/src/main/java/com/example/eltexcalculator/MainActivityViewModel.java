package com.example.eltexcalculator;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Objects;

/**
 * Обработка смены конфигурации и нажатия клавиш калькулятора
 */
public class MainActivityViewModel extends ViewModel {
    /**
     * Строка основного вывода результата
     */
    public MutableLiveData<String> numberString = new MutableLiveData<>("0");
    /**
     * double Переменная, храняшая аргумент операции
     */
    private MutableLiveData<Double> tempArg = new MutableLiveData<>(0.0);
    /**
     * Вспомогательная строка вывода, хранит tempArg c символами текущей операции
     */
    public MutableLiveData<String> tempText = new MutableLiveData<>("");

    /**
     * Текущая операция, хранит код операции
     */
    private MutableLiveData<String> operationCode = new MutableLiveData<>("0");

    final String PLUS_CODE = "+";
    final String MINUS_CODE = "-";
    final String DIVIDE_CODE = "/";
    final String MULTIPLY_CODE = "*";


    /**
     * Метод, возвращающий число типа double из строки
     */
    private Double getNumStrAsDouble() {
        return Double.parseDouble(numberString.getValue());
    }

    /**
     * Метод, возвращающий число из временной переменной
     */
    public String getDoubleTempAsString() {
        Double s = tempArg.getValue();
        if (s.intValue() == s) return String.valueOf(s.intValue());
        else return s.toString();
    }

    /**
     * Очистка памяти калькулятора
     */
    public void clearAll() {
        operationCode.setValue("0");
        tempArg.setValue(0.0);
        numberString.setValue("0");
        tempText.setValue("");
    }

    /**
     * Обработка операций +-/*
     * @param code - идентификатор операции
     */
    public void setOperation(String code) {
        Log.d("operation","operation " + code);
        if (prepareOperation(code)) tempText.setValue(getDoubleTempAsString()+code);
    }

    /**
     * Обработка операции AC
     * AllCancel Метод отмены ввода текущего значения и сброса временной переменной
     */
    public void ac() {
        if (Objects.equals(numberString.getValue(), "0")) {
            tempText.setValue("");
            clearAll();
            Log.d("operation","operation AC");
        }
        else {
            numberString.setValue("0");
            Log.d("operation","operation C");
        }
    }


    /**
     * Вспомогательный метод обработки операций
     * @param code - идентификатор операции
     * @return {@code false} - запрет смены знака в случае, когда число уже введено
     * @return {@code true} - разрешает операцию
     */
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

    /**
     * Получение результата операции по коду операции
     * @return double-число - результат операции
     */
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

    /**
     * Обработка нажатия клавиши = пользователем
     */
    public void equal() {
        Log.d("operation","operation =");
        if (!Objects.equals(operationCode.getValue(), "0")) {
            String s = tempText.getValue() + numberString.getValue() + "=";
            tempText.setValue(s);

            Double result = getOperationResult();
            tempArg.setValue(0.0);
            operationCode.setValue("0");
            if (result.intValue() == result) numberString.setValue(String.valueOf(result.intValue()));
            else numberString.setValue(result.toString());
        }
    }

    /**
     *Обработка нажатия клавиш 0-9 калькулятора, добавление цифры в строку
     * @param n цифры калькулятора 0-9
     */
    public void addDigit(int n) {
        Log.d("operation","operation enter "+ n);
        if (tempText.getValue().contains("=")) {
            clearAll();
        }
        String str = numberString.getValue();
        if (Objects.equals(str, "0")) str = "";
        if (Objects.equals(str, "0") && (n == 0)) str += ".";
        numberString.setValue(str + n);
    }

    /**
     * Обработка нажатия клавиши +/- пользователем
     */
    public void plusMinus() {
        Log.d("operation","operation +/-");
        StringBuilder sb = new StringBuilder(numberString.getValue());
        if (sb.charAt(0) == '-') sb.deleteCharAt(0);
        else sb.insert(0,"-");
        numberString.setValue(sb.toString());
    }

    /**
     * Обработка нажатия клавиши % пользователем
     */
    public void percent() {
        Log.d("operation","operation %");
        Double s = getNumStrAsDouble();
        s = s/100;
        if (s.intValue() == s) numberString.setValue(String.valueOf(s.intValue()));
        else numberString.setValue(String.valueOf(s));;
    }

    /**
     * Обработка нажатия клавиши . пользователем
     */
    public void addDot() {
        Log.d("operation","operation .");
        String s = numberString.getValue();
        if (!s.contains(".")) numberString.setValue(s + "." );
    }
}
