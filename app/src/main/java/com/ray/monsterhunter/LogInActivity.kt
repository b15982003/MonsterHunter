package com.ray.monsterhunter


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.ray.monsterhunter.data.User
import com.ray.monsterhunter.ext.getVmFactory
import com.ray.monsterhunter.util.Logger
import com.ray.monsterhunter.util.UserManager
import kotlinx.android.synthetic.main.fragment_log_in_activity.*
import kotlinx.android.synthetic.main.fragment_log_in_activity.view.*


@Suppress("DEPRECATION")
class LogInActivity : AppCompatActivity() {
    val viewModel by viewModels<MainViewModel> { getVmFactory() }
    var auth: FirebaseAuth? = null
    var googleSignInClient: GoogleSignInClient? = null
    var GOOGLE_LOGIN_CODE = 9001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_log_in_activity)
        auth = FirebaseAuth.getInstance()

        google_sign_in_button.setOnClickListener {
            //First step
            googleLogin()
        }

        var gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("1057474322422-96rtu3su5rou898acu7nqsqpbr8m3lpi.apps.googleusercontent.com")
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

    }

//    override fun onStart() {
//        super.onStart()
//
////        moveMainPage(auth?.currentUser)
//    }

    fun googleLogin() {
        var signInIntent = googleSignInClient?.signInIntent
        startActivityForResult(signInIntent, GOOGLE_LOGIN_CODE)
    }


    //google get token
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GOOGLE_LOGIN_CODE) {
            var result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            if (result != null) {
                if (result.isSuccess) {
                    var account = result.signInAccount

                    var user = User(
                        image = account?.photoUrl.toString(),
                        id = account?.displayName,
                        email = account?.email
                    )
                    UserManager.userData= user

                    viewModel.pushUser(user)
                    //Second step
                    Handler().postDelayed({
                        firebaseAuthWithGoogle(account)
                    },500)

                }
            }
        }
    }

    //get token to firebase verification
    fun firebaseAuthWithGoogle(account: GoogleSignInAccount?) {
        var credential = GoogleAuthProvider.getCredential(account?.idToken, null)
        auth?.signInWithCredential(credential)
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    //Login
                        moveMainPage(task.result?.user)


                } else {
                    //Show the error message
                    Toast.makeText(this, task.exception?.message, Toast.LENGTH_LONG).show()
                }
            }
    }

    fun moveMainPage(user: FirebaseUser?) {
        if (user != null) {

            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }


}
