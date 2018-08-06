package com.cc.my_pt_manager.model

/**
 * Created by wee on 2018. 5. 23..
 */

data class ChatChannel(val userIds: MutableList<String>) {
    constructor() : this(mutableListOf())
}

