package org.example;

import com.google.common.math.IntMath;
import java.math.RoundingMode;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello and welcome!");

        int number = IntMath.divide(10, 3, RoundingMode.UP);
        System.out.println(number);
    }
}