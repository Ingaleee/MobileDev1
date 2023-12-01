package com.example.super_duper_calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.OrientationEventListener
import com.example.super_duper_calculator.fragments.PortraitClassicFragment
import com.example.super_duper_calculator.fragments.PortraitVariableFragment

class MainActivity : AppCompatActivity(), MyFragmentListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showOtherOrientation()

//        val orientationListener = object : OrientationEventListener(this){
//            override fun onOrientationChanged(p0: Int) {
//                showOtherOrientation();
//            }
//        }
//
//        if (orientationListener.canDetectOrientation()){
//            orientationListener.enable()
//        }
    }

    private fun showOtherOrientation(){
        val currentOrientation = resources.configuration.orientation

        val transaction = supportFragmentManager.beginTransaction()
        if (GlobalVariables.calculationMode == CalculationMode.Classic) {
                transaction.replace(R.id.fragmentContainer, PortraitClassicFragment())
        } else if (GlobalVariables.calculationMode == CalculationMode.Variable) {
                transaction.replace(R.id.fragmentContainer, PortraitVariableFragment())
        }

        transaction.commit()
    }

    override fun onButtonClicked() {
        showOtherOrientation()
    }

//    fun pressNumber(view: View){
//        var pressedBtnText: String = (view as Button).text.toString();
//        inputField.text = inputField.text.toString() + pressedBtnText;
//    }
//
//    fun pressAction(view: View){
//        // TODO: fix for input negative numbers
//        if(inputField.text.isEmpty()){
//            return;
//        }
//
//        var lastChar = inputField.text[inputField.text.length - 1];
//        if(!lastChar.isDigit() && lastChar != ')'){
//            return;
//        }
//
//        var pressedBtnText: String = (view as Button).text.toString();
//        inputField.text = inputField.text.toString() + pressedBtnText;
//    }
//
//    fun pressClear(view: View){
//        inputField.text = "";
//    }
//
//    fun pressEqual(view: View){
//        var input = inputField.text.toString();
//        var calculationWorker = Handler();
//        var calculationResult = calculationWorker.handle(input)
//
//        if (calculationResult.hasError){
//            inputField.text = calculationResult.error;
//            return;
//        }
//
//        inputField.text = "${calculationResult.result}";
//    }
}