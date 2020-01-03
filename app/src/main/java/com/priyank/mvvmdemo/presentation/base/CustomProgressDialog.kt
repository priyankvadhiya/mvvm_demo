package com.priyank.mvvmdemo.presentation.base


import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.priyank.mvvmdemo.R


class CustomProgressDialog(context: Context) : Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_dialog_progress)
        setCancelable(false)
    }
}