package com.rsschool.android2021

import androidx.fragment.app.Fragment
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.activity.addCallback
import kotlin.random.Random



class SecondFragment : Fragment() {
    private var mListener: OnFragment2DataListener? = null
    private var backButton: Button? = null
    private var result: TextView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mListener = if (context is OnFragment2DataListener) {
            context as OnFragment2DataListener
        } else {
            throw RuntimeException(
                context.toString()
                    .toString() + " must implement OnFragment2DataListener"
            )
        }
        //переопределяем физическую кнопку назад
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            mListener?.onFragment2DataListener(result?.text.toString().toIntOrNull()?:0);
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        result = view.findViewById(R.id.result)
        backButton = view.findViewById(R.id.back)

        val min = arguments?.getInt(MIN_VALUE_KEY) ?: 0
        val max = arguments?.getInt(MAX_VALUE_KEY) ?: 0

        result?.text = generate(min, max).toString()

        backButton?.setOnClickListener {
            mListener?.onFragment2DataListener(result?.text.toString().toIntOrNull()?:0);
        }
    }

    private fun generate(min: Int, max: Int): Int {
        return Random.nextInt(min,max)
    }

    companion object {

        @JvmStatic
        fun newInstance(min: Int, max: Int): SecondFragment {
            val fragment = SecondFragment()
            val args = Bundle().apply {
                putInt(MIN_VALUE_KEY,min)
                putInt(MAX_VALUE_KEY,max)
            }
            fragment.arguments = args
            return fragment
        }

        private const val MIN_VALUE_KEY = "MIN_VALUE"
        private const val MAX_VALUE_KEY = "MAX_VALUE"
    }
}