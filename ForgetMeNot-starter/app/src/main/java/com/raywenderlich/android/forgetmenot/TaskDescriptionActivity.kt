package com.raywenderlich.android.forgetmenot

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_task_description.*

class TaskDescriptionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_description)

    }

    fun doneClicked(view: View) {
        val taskDescription = descriptionText.text.toString()
        if (!taskDescription.isEmpty()) {
            val result = Intent()
            result.putExtra(EXTRA_TASK_DESCRIPTION, taskDescription)
            setResult(Activity.RESULT_OK, result)
        } else {
            setResult(Activity.RESULT_CANCELED)
        }

        finish()
    }

    companion object {
        var EXTRA_TASK_DESCRIPTION = "task"
    }
}
