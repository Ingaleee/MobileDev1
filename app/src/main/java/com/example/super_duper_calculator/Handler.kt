package com.example.super_duper_calculator

class Handler {
    private val preparer: Orderer = Orderer();

    fun handle(input: String): WorkResult<Float> {
        var normalizedInput = preparer.prepareInput(input);
        var queue = preparer.makeQueue(normalizedInput);

        var n = queue.size - 1
        while(n > 0){
            var numbers = preparer.selectNumbers(queue[n])
            var actions = preparer.selectActions(queue[n])
            var tempResult = Calculator.calculate(numbers, actions);

            var i = n - 1
            if (tempResult.isSuccess) {
                while (i >= 0) {
                    queue[i] = queue[i].replace("(${queue[n]})", "${tempResult.value}");
                    i--
                }
            }
            n--;
        }
        // x + 2y
        // x: 23 y: 5
        // 23*1+2*5

        var finalExpr = queue[0]
        var finalExprNumbers = preparer.selectNumbers(finalExpr);
        var finalExprActions = preparer.selectActions(finalExpr);

        var finalResult = Calculator.calculate(finalExprNumbers, finalExprActions);

        return ResultMapper.calcToWork(finalResult);
    }
}