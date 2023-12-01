package com.example.super_duper_calculator

import kotlin.math.ln
import kotlin.math.sqrt

class Calculator {
    companion object {
        fun calculate(
            numbers: ArrayList<Float>,
            actions: ArrayList<Char>
        ): CalculationResult<Float> {

            var n = 0;
            while (n < actions.size) {
                var res = when (actions[n]) {
                    '*' -> numbers[n] * numbers[n + 1]
                    '/' -> {
                        if (numbers[n + 1] == 0f) {
                            return CalculationResult.failed("ZeroDivide")
                        }
                        numbers[n] / numbers[n + 1]
                    }

                    'l' -> ln(numbers[n].toDouble()).toFloat();
                    '^' -> sqrt(numbers[n]);
                    else -> {
                        n++;
                        continue
                    };
                }
                actions.remove(actions[n]);
                numbers[n] = res;
                if (numbers.size - 1 < n + 1) {
                    break;
                }
                numbers.remove(numbers[n + 1]);
            }

            for (n in actions.indices) {
                numbers[0] = when (actions[n]) {
                    '+' -> numbers[0] + numbers[n + 1]
                    '-' -> numbers[0] - numbers[n + 1]
                    else -> continue;
                }
            }


            return CalculationResult.success(numbers[0]);
        }
    }
}