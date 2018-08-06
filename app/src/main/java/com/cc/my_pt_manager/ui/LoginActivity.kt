package com.cc.my_pt_manager.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import com.cc.my_pt_manager.R
import com.cc.my_pt_manager.util.FirestoreUtil
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider

/* Created by wee on 2018. 5. 2..
 */
class LoginActivity : AppCompatActivity(),GoogleApiClient.OnConnectionFailedListener {

    private val TAG = "LoginActivity"

    lateinit var mGoogleSignInClient: GoogleSignInClient
    lateinit var gso : GoogleSignInOptions
    val RC_SIGN_IN : Int = 1

    private var mAuth: FirebaseAuth? = null

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val signInButton = findViewById<View>(R.id.google_login_button) as SignInButton
        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        signInButton.setOnClickListener {
            View : View? -> signInGoogle()
        }

        mAuth = FirebaseAuth.getInstance()

    }

    @SuppressLint("RestrictedApi")
    private fun signInGoogle() {
        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent,RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == RC_SIGN_IN){
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            if (result.isSuccess) {
                // successful -> authenticate with Firebase
                val account = result.signInAccount
                FirestoreUtil.initCurrentUserIfFirstTime {}
                firebaseAuthWithGoogle(account!!)
            } else {
                // failed -> update UI
                updateUI(null)
                Toast.makeText(applicationContext, "SignIn: failed!",
                        Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {

            Log.e(TAG, "firebaseAuthWithGoogle():" + acct.id!!)

            val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
            mAuth!!.signInWithCredential(credential)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success
                            Log.e(TAG, "signInWithCredential: Success!")
                            val user = mAuth!!.currentUser
                            updateUI(user)
                        } else {
                            // Sign in fails
                            Log.w(TAG, "signInWithCredential: Failed!", task.exception)
                            Toast.makeText(applicationContext, "Authentication failed!",
                                    Toast.LENGTH_SHORT).show()
                            updateUI(null)
                        }
                    }
    }

    private fun updateUI(user: FirebaseUser?) {
        Toast.makeText(this,"success",Toast.LENGTH_LONG).show()
        val nextIntent = Intent(this, MainActivity::class.java)
        startActivity(nextIntent)

    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {
        Log.e(TAG, "onConnectionFailed():" + connectionResult);
        Toast.makeText(applicationContext, "Google Play Services error.", Toast.LENGTH_SHORT).show();
    }


}