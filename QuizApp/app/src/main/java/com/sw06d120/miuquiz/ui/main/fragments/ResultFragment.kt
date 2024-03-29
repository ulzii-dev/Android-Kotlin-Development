package com.sw06d120.miuquiz.ui.main.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.sw06d120.miuquiz.R
import com.sw06d120.miuquiz.models.QuizViewModel
import kotlinx.android.synthetic.main.fragment_result.*
import kotlinx.android.synthetic.main.fragment_result.view.*

/**
 * A simple [Fragment] subclass.
 * Use the [ResultFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ResultFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private val viewModel: QuizViewModel by activityViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        txtTotalCount.textSize = 40f
        txtTotalCount.typeface =
            activity?.let { ResourcesCompat.getFont(it.applicationContext, R.font.oswald_regular) }
        txtCorrectCount.textSize = 40f
        txtCorrectCount.typeface =
            activity?.let { ResourcesCompat.getFont(it.applicationContext, R.font.oswald_regular) }
        txtCorrectCount.setTextColor(Color.parseColor("#00796B"))

        txtWrongCount.textSize = 40f
        txtWrongCount.typeface =
            activity?.let { ResourcesCompat.getFont(it.applicationContext, R.font.oswald_regular) }
        txtWrongCount.setTextColor(Color.parseColor("#FFE64A19"))

        btnRetry.typeface =
            activity?.let { ResourcesCompat.getFont(it.applicationContext, R.font.oswald_regular) }

        btnDetailedResult.typeface =
            activity?.let { ResourcesCompat.getFont(it.applicationContext, R.font.oswald_regular) }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_result, container, false)
        view.btnRetry.setOnClickListener {
            viewModel.reset()
            Navigation.findNavController(view).navigate(R.id.quizPrevFragment)
        }
        view.btnDetailedResult.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.resultDetailedFragment)
        }
        viewModel.correctAnswerCounter.observe(viewLifecycleOwner, Observer { currentQuestionId ->
            txtTotalCount.text = "Total: " + viewModel.questions.size.toString()
            txtCorrectCount.text = "Correct: " + viewModel.correctAnswerCounter.value.toString()
            txtWrongCount.text =
                "Wrong: " + (viewModel.questions.size - viewModel.correctAnswerCounter.value!!).toString()
        })

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ResultFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ResultFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}