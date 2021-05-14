package com.kyser.mynotes.view.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kyser.mynotes.databinding.FragmentNoteViewBinding
import com.kyser.mynotes.view.MainActivity
import com.kyser.mynotes.view.viewmodel.NoteGridViewModel
import kotlinx.android.synthetic.main.activity_main.*

class NoteView : Fragment(), TextWatcher {

    private lateinit var mNoteListModel: NoteGridViewModel
    private lateinit var mFragmentBinding: FragmentNoteViewBinding
    private var mNoteId = 0
    private var mTextUpdate = false

    override fun onCreateView(  inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        mFragmentBinding = FragmentNoteViewBinding.inflate(inflater, container, false)
        return mFragmentBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mNoteId = requireArguments().getInt("id")
        mFragmentBinding.noteTitleInp.setText(requireArguments().getString("title"))
        mFragmentBinding.noteBodyInp.setText(requireArguments().getString("note"))
        mFragmentBinding.noteTitleInp.addTextChangedListener(this)
        mFragmentBinding.noteBodyInp.addTextChangedListener(this)
        mNoteListModel =   (activity as MainActivity).getViewModel()
        (activity as MainActivity).getMainBinding().toolbar.setNavigationOnClickListener(View.OnClickListener { backPressed() })
    }



    private fun backPressed() {
        if (mTextUpdate)
            mNoteListModel.updateNote(  mNoteId,  mFragmentBinding.noteTitleInp.text.toString(), mFragmentBinding.noteBodyInp.text.toString() )
        (activity as MainActivity).getMainBinding().toolbar.setNavigationOnClickListener(null)
        requireActivity().onBackPressed()
    }
    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =  NoteView()
    }

    override fun afterTextChanged(p0: Editable?) {
        mTextUpdate = !mFragmentBinding.noteBodyInp.text.toString()
            .equals(requireArguments().getString("note"))
        mTextUpdate = !mFragmentBinding.noteBodyInp.text.toString()
            .equals(requireArguments().getString("title"))
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }
}