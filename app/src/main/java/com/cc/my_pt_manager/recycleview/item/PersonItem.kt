package com.cc.my_pt_manager.recycleview.item

import android.content.Context
import com.cc.my_pt_manager.R
import com.cc.my_pt_manager.glide.GlideApp
import com.cc.my_pt_manager.model.UserModel
import com.cc.my_pt_manager.util.StorageUtil
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_person.*

/**
 * Created by wee on 2018. 5. 18..
 */
class PersonItem(val person: UserModel,
                 val userId: String,
                 private val context: Context)
    : Item() {

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.textView_name.text = person.name
        viewHolder.textView_bio.text = person.bio
        if (person.profilePicturePath != null)
            GlideApp.with(context)
                    .load(StorageUtil.pathToReference(person.profilePicturePath))
                    .placeholder(R.drawable.ic_account_circle_black_24dp)
                    .into(viewHolder.imageView_profile_picture)
    }

    override fun getLayout() = R.layout.item_person
}