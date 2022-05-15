package com.example.chatapp.view.base

import android.os.Bundle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlin.coroutines.CoroutineContext

open class BaseFullBottomSheetDialogFragment : BaseFullScreenDialogFragment(), CoroutineScope {
    private lateinit var job: Job
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        job = SupervisorJob()
        super.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }
}