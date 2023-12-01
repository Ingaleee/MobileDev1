package com.example.super_duper_calculator

class ResultMapper {
    companion object{
        fun <T: Number>calcToWork(calcResult: CalculationResult<T>): WorkResult<T>{
            var workResult = WorkResult<T>();

            workResult.result = calcResult.value
            workResult.error = calcResult.error
            workResult.hasError = !calcResult.isSuccess;

            return workResult;
        }
    }
}