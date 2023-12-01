package com.example.super_duper_calculator.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.super_duper_calculator.CalculationMode
import com.example.super_duper_calculator.GlobalVariables
import com.example.super_duper_calculator.Handler
import com.example.super_duper_calculator.MainActivity
import com.example.super_duper_calculator.MyFragmentListener
import com.example.super_duper_calculator.R

class PortraitClassicFragment : Fragment() {
    private var fragmentListener: MyFragmentListener? = null

    private lateinit var inputField: TextView;

    private val numButtonList: MutableList<Button> = mutableListOf()
    private val actButtonList: MutableList<Button> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_classic, container, false)

        numButtonList.add(view.findViewById(R.id.oneNumBtn));
        numButtonList.add(view.findViewById(R.id.twoNumBtn));
        numButtonList.add(view.findViewById(R.id.threeNumBtn));
        numButtonList.add(view.findViewById(R.id.fourNumBtn));
        numButtonList.add(view.findViewById(R.id.fiveNumBtn));
        numButtonList.add(view.findViewById(R.id.sixNumBtn));
        numButtonList.add(view.findViewById(R.id.sevenNumBtn));
        numButtonList.add(view.findViewById(R.id.eightNumBtn));
        numButtonList.add(view.findViewById(R.id.nineNumBtn));
        numButtonList.add(view.findViewById(R.id.zeroNumBtn));
        numButtonList.add(view.findViewById(R.id.parenthesisBtn_open));
        numButtonList.add(view.findViewById(R.id.parenthesisBtn_close));

        numButtonList.forEach { button ->
            button.setOnClickListener {
                pressNumber(button);
            }
        }

        actButtonList.add(view.findViewById(R.id.plusActBtn))
        actButtonList.add(view.findViewById(R.id.minusActBtn))
        actButtonList.add(view.findViewById(R.id.multActBtn))
        actButtonList.add(view.findViewById(R.id.divideActBtn))
        actButtonList.add(view.findViewById(R.id.logActBtn))
        actButtonList.add(view.findViewById(R.id.sqrtActBtn))

        actButtonList.forEach { button ->
            button.setOnClickListener {
                pressAction(button);
            }
        }

        var equalButton = view.findViewById<Button>(R.id.equalActBtn)
        equalButton.setOnClickListener{
            pressEqual(equalButton)
        }

        var cleanButton = view.findViewById<Button>(R.id.cleanActBtn)
        cleanButton.setOnClickListener{
            pressClear(cleanButton)
        }

        var modeButton = view.findViewById<Button>(R.id.modeBtn)
        modeButton.setOnClickListener{
            onModeChange(modeButton)
        }

        inputField = view.findViewById(R.id.inputField);

        return view;
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            fragmentListener = context as MyFragmentListener
        } catch (e: ClassCastException) {
            throw ClassCastException("$context must implement MyFragmentListener")
        }
    }

    fun onModeChange(view: View){
        if(GlobalVariables.calculationMode == CalculationMode.Classic){
            (view as Button)?.text = "Variable"
            GlobalVariables.calculationMode = CalculationMode.Variable
        } else if (GlobalVariables.calculationMode == CalculationMode.Variable){
            (view as Button)?.text = "Classic"
            GlobalVariables.calculationMode = CalculationMode.Classic
        }
        fragmentListener?.onButtonClicked();
    }

    fun pressNumber(view: View){
        var pressedBtnText: String = (view as Button).text.toString();
        inputField.text = inputField.text.toString() + pressedBtnText;
    }

    fun pressAction(view: View){
        // TODO: fix for input negative numbers
//        if(inputField.text.isEmpty()){
//            return;
//        }

//        var lastChar = inputField.text[inputField.text.length - 1];
//        if(!lastChar.isDigit() && lastChar != ')'){
//            return;
//        }

        var pressedBtnText: String = (view as Button).text.toString();
        inputField.text = inputField.text.toString() + pressedBtnText;
    }

    fun pressClear(view: View){
        inputField.text = "";
    }

    fun pressEqual(view: View){
        var input = inputField.text.toString();
        var calculationWorker = Handler();
        var calculationResult = calculationWorker.handle(input)

        if (calculationResult.hasError){
            inputField.text = calculationResult.error;
            return;
        }

        inputField.text = "${calculationResult.result}";
    }
}