package com.kyser.mynotes.view.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kyser.mynotes.databinding.FragmentNoteViewBinding
import com.kyser.mynotes.model.service.NotesManager

class NoteView : Fragment(), TextWatcher {

    private lateinit var mFragmentBinding: FragmentNoteViewBinding
    private var mNoteId = 0
    private var mTextUpdate = false

    override fun onCreateView(  inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        mFragmentBinding = FragmentNoteViewBinding.inflate(inflater, container, false)
        return mFragmentBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mFragmentBinding.back.setOnClickListener { view -> backPressed() }
        mNoteId = requireArguments().getInt("id")
        mFragmentBinding.noteTitleInp.setText(requireArguments().getString("title"))
        mFragmentBinding.noteBodyInp.setText(requireArguments().getString("note"))
        mFragmentBinding.noteTitleInp.addTextChangedListener(this)
        mFragmentBinding.noteBodyInp.addTextChangedListener(this)
    }

    private fun backPressed() {
        if (mTextUpdate) NotesManager.instance.updateNote(
            mNoteId,
            mFragmentBinding.noteTitleInp.text.toString(),
            mFragmentBinding.noteBodyInp.text.toString()
        )
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