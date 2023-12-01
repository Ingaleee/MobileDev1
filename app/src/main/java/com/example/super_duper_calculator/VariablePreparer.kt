package com.example.super_duper_calculator

class VariablePreparer{
    private var x: Float? = null;
    private var y: Float? = null;
    private var z: Float? = null;

    fun pointVariables(x: Float?, y: Float?, z: Float?){
        this.x = x;
        this.y = y;
        this.z = z;
    }
    fun normalize(input: String): String {
        if(input.contains('x') && x == null){
            throw Exception("Specify the value of the variable x.");
        }

        if(input.contains('y') && y == null){
            throw Exception("Specify the value of the variable y.");
        }

        if(input.contains('z') && z == null){
            throw Exception("Specify the value of the variable z.");
        }

        return input.replace(Regex("(\\d+)([xyz])"), "$1*$2")
            .replace("x", x.toString())
            .replace("y", y.toString())
            .replace("z", z.toString())
    }
}