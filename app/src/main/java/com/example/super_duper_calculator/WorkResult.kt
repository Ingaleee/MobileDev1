package com.example.super_duper_calculator

class WorkResult<TResult> {
    var hasError: Boolean = false;
    var error: String? = null;
    var result: TResult? = null
    companion object{
        fun <T>good(value: T): WorkResult<T>{
            var res = WorkResult<T>();
            res.result = value
            res.hasError = false;
            return res;
        }

        fun <T>bad(error: String): WorkResult<T>{
            var res = WorkResult<T>();
            res.error = error;
            res.hasError = false;
            return res;
        }
    }
}