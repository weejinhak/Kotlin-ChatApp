package com.cc.my_pt_manager.ui

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.cc.my_pt_manager.R
import com.cc.my_pt_manager.fragment.MyQrCodeFragment
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_people -> {
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_my_account -> {
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_chat_manager -> {
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_take_qrCode -> {
                    IntentIntegrator(this).initiateScan() // `this` is the current Activity
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_my_qrCode -> {
                    replaceFragment(MyQrCodeFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                else -> return@setOnNavigationItemSelectedListener false
            }
        }
    }

    // QR Code Get the results
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Scanned: " + result.contents, Toast.LENGTH_LONG).show()
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_layout, fragment).commit()
    }
}

