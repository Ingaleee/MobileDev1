package com.example.super_duper_calculator

class CalculationResult<T : Number> {
    var isSuccess: Boolean = false;
    var value: T? = null;
    var error: String? = null;

    companion object{
        fun <T: Number>success(value: T): CalculationResult<T>{
            var result = CalculationResult<T>()
            result.isSuccess = true;
            result.value = value;
            return result;
        }

        fun <T: Number>failed(error: String): CalculationResult<T>{
            var result = CalculationResult<T>()
            result.isSuccess = false;
            result.error = error;
            return result;
        }
    }
}