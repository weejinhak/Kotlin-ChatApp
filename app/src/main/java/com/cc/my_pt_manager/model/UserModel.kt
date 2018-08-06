package com.cc.my_pt_manager.model

/**
 * Created by wee on 2018. 5. 2..
 */

data class UserModel(val name: String,
                     val bio : String,
                     val profilePicturePath: String?){
    constructor() : this("","",null)
}

