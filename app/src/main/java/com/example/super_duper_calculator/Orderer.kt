package com.example.super_duper_calculator

class Orderer {
    private val operators: Array<Char> = arrayOf('+', '-', '/', '*', 'l', '^');

    fun prepareInput(input: String): String {
        return input.replace("log", "l").replace("2√", "^");
    }

    fun makeQueue(input: String): ArrayList<String>{
        var queue: ArrayList<String> = arrayListOf("");
        var deepIndex: Int = 0;
        var currentPosition = 0

        for(n in input.indices){
            if (input[n] == '('){
                queue.add("")
                deepIndex++;
                currentPosition = queue.size - 1;
                continue;
            }

            if (input[n] == ')'){
                deepIndex--;
                if(deepIndex == 0){
                    queue[0] += "(${queue[currentPosition]})";
                    continue;
                }

                var i = 0
                while (!operators.contains(queue[currentPosition - i].last())){
                    i++;
                }

                queue[currentPosition - i] += "(${queue[currentPosition]})";
                currentPosition -= i;

                continue;
            }

            if (deepIndex == 0){
                queue[0] += "${input[n]}"
                continue;
            }
            queue[currentPosition] += "${input[n]}"
        }

        return queue;
    }

    fun selectActions(input: String) : ArrayList<Char>{
        var actions: ArrayList<Char> = ArrayList();
        for(n in input.indices){
            var isAction = operators.contains(input[n]) && !(n > 0 && operators.contains(input[n - 1]))
            if (isAction) {
                actions.add(input[n])
            }
        }

        return actions;
    }

    fun selectNumbers(input: String) : ArrayList<Float>{
        var numbers: ArrayList<Float> = ArrayList();
        var strCache = "";
        for (n in input.indices) {
            var isSign = input[n] == '-' && (n == 0 || operators.contains(input[n - 1]));
            if (input[n].isDigit() || input[n] == '.' || isSign) {
                strCache += input[n];
            } else if (!strCache.isNullOrEmpty()) {
                var parsed = strCache.toFloat();
                strCache = "";
                numbers.add(parsed);
                continue;
            }

            if (n == input.length - 1 && input[n].isDigit()) {
                numbers.add(strCache.toFloat());
            }
        }

        return numbers;
    }
}