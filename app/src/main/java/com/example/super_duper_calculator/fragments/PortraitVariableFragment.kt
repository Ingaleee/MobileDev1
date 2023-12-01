package com.example.super_duper_calculator.fragments

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.super_duper_calculator.CalculationMode
import com.example.super_duper_calculator.GlobalVariables
import com.example.super_duper_calculator.Handler
import com.example.super_duper_calculator.MyFragmentListener
import com.example.super_duper_calculator.R
import com.example.super_duper_calculator.VariablePreparer

class PortraitVariableFragment : Fragment() {

    private lateinit var inputField: TextView;
    private var fragmentListener: MyFragmentListener? = null

    private val numButtonList: MutableList<Button> = mutableListOf()
    private val actButtonList: MutableList<Button> = mutableListOf()
    private val varButtonList: MutableList<Button> = mutableListOf()

    private var xValue: Float? = null;
    private var yValue: Float? = null;
    private var zValue: Float? = null;

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view =  inflater.inflate(R.layout.fragment_variable, container, false)

        numButtonList.add(view.findViewById(R.id.oneNumBtn_var));
        numButtonList.add(view.findViewById(R.id.twoNumBtn_var));
        numButtonList.add(view.findViewById(R.id.threeNumBtn_var));
        numButtonList.add(view.findViewById(R.id.fourNumBtn_var));
        numButtonList.add(view.findViewById(R.id.fiveNumBtn_var));
        numButtonList.add(view.findViewById(R.id.sixNumBtn_var));
        numButtonList.add(view.findViewById(R.id.sevenNumBtn_var));
        numButtonList.add(view.findViewById(R.id.eightNumBtn_var));
        numButtonList.add(view.findViewById(R.id.nineNumBtn_var));
        numButtonList.add(view.findViewById(R.id.zeroNumBtn_var));
        numButtonList.add(view.findViewById(R.id.parenthesisBtn_open_var));
        numButtonList.add(view.findViewById(R.id.parenthesisBtn_close_var));

        numButtonList.forEach { button ->
            button.setOnClickListener {
                pressNumber(button);
            }
        }

        actButtonList.add(view.findViewById(R.id.plusActBtn_var))
        actButtonList.add(view.findViewById(R.id.minusActBtn_var))
        actButtonList.add(view.findViewById(R.id.multActBtn_var))
        actButtonList.add(view.findViewById(R.id.divideActBtn_var))
        actButtonList.add(view.findViewById(R.id.logActBtn_var))
        actButtonList.add(view.findViewById(R.id.sqrtActBtn_var))

        actButtonList.forEach { button ->
            button.setOnClickListener {
                pressAction(button);
            }
        }

        varButtonList.add(view.findViewById(R.id.xBtn_var))
        varButtonList.add(view.findViewById(R.id.yBtn_var))
        varButtonList.add(view.findViewById(R.id.zBtn_var))

        varButtonList.forEach { button ->
            button.setOnClickListener {
                pressNumber(button);
            }
        }

        var equalButton = view.findViewById<Button>(R.id.equalActBtn_var)
        equalButton.setOnClickListener{
            pressEqual(equalButton)
        }

        var cleanButton = view.findViewById<Button>(R.id.cleanActBtn_var)
        cleanButton.setOnClickListener{
            pressClear(cleanButton)
        }

        var modeButton = view.findViewById<Button>(R.id.modeBtn_var)
        modeButton.setOnClickListener{
            onModeChange(modeButton)
        }

        inputField = view.findViewById(R.id.inputField_var);

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
        fragmentListener?.onButtonClicked()
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
//        var isVariable = lastChar == 'x' || lastChar == 'y' || lastChar == 'z';
//        if(!(lastChar.isDigit() || isVariable) && lastChar != ')'){
//            return;
//        }

        var pressedBtnText: String = (view as Button).text.toString();
        inputField.text = inputField.text.toString() + pressedBtnText;
    }

    fun pressClear(view: View){
        inputField.text = "";
    }

    private fun showAlertDialog(value: Char) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Input $value")

        var input = EditText(requireContext());
        builder.setView(input)

        builder.setPositiveButton("ОК") { _, _ ->
            when (value) {
                'x' -> {
                    xValue = input.text.toString().toFloatOrNull();
                    showAlertDialog('y')
                }
                'y' -> {
                    yValue = input.text.toString().toFloatOrNull();
                    showAlertDialog('z')

                }
                'z' -> {
                    zValue = input.text.toString().toFloatOrNull();
                    var preparer = VariablePreparer();
                    preparer.pointVariables(xValue, yValue, zValue)

                    var input = preparer.normalize(inputField.text.toString());
                    var calculationWorker = Handler();
                    var calculationResult = calculationWorker.handle(input)

                    if (calculationResult.hasError){
                        inputField.text = calculationResult.error;
                        return@setPositiveButton;
                    }

                    inputField.text = "${calculationResult.result}";
                }
            }
        }

        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.cancel()
        }
        builder.show()
    }

    fun pressEqual(view: View){
        showAlertDialog('x');
    }
}