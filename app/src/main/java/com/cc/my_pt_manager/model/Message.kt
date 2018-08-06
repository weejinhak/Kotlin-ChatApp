package com.cc.my_pt_manager.model

import java.util.*

/**
 * Created by wee on 2018. 5. 23..
 */


object MessageType {
    const val TEXT = "TEXT"
    const val IMAGE = "IMAGE"
}

interface Message {
    val time: Date
    val senderId: String
    val type: String
}