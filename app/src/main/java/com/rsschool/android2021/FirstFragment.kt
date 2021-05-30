package com.rsschool.android2021

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import java.lang.RuntimeException


class FirstFragment : Fragment() {
    private var mListener: OnFragment1DataListener? = null
    private var generateButton: Button? = null
    private var previousResult: TextView? = null
    private var minTextView: TextView? = null
    private var maxTextView: TextView? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mListener = if (context is OnFragment1DataListener) {
            context as OnFragment1DataListener
        } else {
            throw RuntimeException(
                context.toString()
                    .toString() + " must implement OnFragment2DataListener"
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        previousResult = view.findViewById(R.id.previous_result)
        generateButton = view.findViewById(R.id.generate)
        minTextView = view.findViewById(R.id.min_value)
        maxTextView = view.findViewById(R.id.max_value)

        generateButton?.isEnabled = false  //делаем изначально неактивной кнопку генерации

        val result = arguments?.getInt(PREVIOUS_RESULT_KEY)
        previousResult?.text = "Previous result: ${result.toString()}"

        minTextView?.addTextChangedListener(textWatcher)
        maxTextView?.addTextChangedListener(textWatcher)

        generateButton?.setOnClickListener {
            val min = minTextView?.text
            val max = maxTextView?.text
            mListener?.onFragment1DataListener(Integer.parseInt(min.toString()),
                Integer.parseInt(max.toString()) );
        }
    }
    val textWatcher = object : TextWatcher{
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            checkNumbersToGenerate()
        }

        override fun afterTextChanged(s: Editable?) {
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(previousResult: Int): FirstFragment {
            val fragment = FirstFragment()
            val args = Bundle()
            args.putInt(PREVIOUS_RESULT_KEY, previousResult)
            fragment.arguments = args
            return fragment
        }

        private const val PREVIOUS_RESULT_KEY = "PREVIOUS_RESULT"
    }

    private fun checkNumbersToGenerate()
    {
        if ((minTextView?.text?.isEmpty() == true)
            ||
            (maxTextView?.text?.isEmpty() == true))
            {
            generateButton?.isEnabled = false
        }
        else
        {
            try {
                val min = Integer.parseInt( minTextView?.text.toString() )
                val max = Integer.parseInt( maxTextView?.text.toString() )

                generateButton?.isEnabled = (min<max)
            } catch (nfe: NumberFormatException) {
                generateButton?.isEnabled = false
            }

        }
    }
}