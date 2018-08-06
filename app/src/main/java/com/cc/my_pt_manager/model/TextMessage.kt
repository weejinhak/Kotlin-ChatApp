package com.cc.my_pt_manager.model

import java.util.*

/**
 * Created by wee on 2018. 5. 23..
 */
data class TextMessage(val text: String,
                       override val time: Date,
                       override val senderId: String,
                       override val type: String)
    : Message {
    constructor() : this("", Date(0), "", "")


}