package com.cc.my_pt_manager.model

import java.util.*

/**
 * Created by wee on 2018. 6. 1..
 */
data class ImageMessage(val imagePath: String,
                        override val time: Date,
                        override val senderId: String,
                        override val type: String = MessageType.IMAGE)
    : Message {
    constructor(): this("",Date(0),"")
}