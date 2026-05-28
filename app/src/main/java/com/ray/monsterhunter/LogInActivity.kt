package com.ray.monsterhunter


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.ray.monsterhunter.data.User
import com.ray.monsterhunter.ext.getVmFactory
import com.ray.monsterhunter.util.Logger
import com.ray.monsterhunter.util.UserManager


class LogInActivity : AppCompatActivity() {
    val viewModel by viewModels<MainViewModel> { getVmFactory() }
    private var auth: FirebaseAuth? = null
    private var googleSignInClient: GoogleSignInClient? = null

    private val signInLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            handleSignInResult(result.data)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_log_in_activity)
        auth = FirebaseAuth.getInstance()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        findViewById<View>(R.id.google_sign_in_button).setOnClickListener {
            googleLogin()
        }
    }

    override fun onStart() {
        super.onStart()
        moveMainPage(auth?.currentUser)
    }

    private fun googleLogin() {
        val signInIntent = googleSignInClient?.signInIntent ?: return
        signInLauncher.launch(signInIntent)
    }

    private fun handleSignInResult(data: Intent?) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        try {
            val account = task.getResult(ApiException::class.java) ?: return
            UserManager.userData = User(
                image = account.photoUrl?.toString(),
                id = account.displayName,
                email = account.email,
            )
            Handler(Looper.getMainLooper()).postDelayed({
                firebaseAuthWithGoogle(account)
            }, 500)
        } catch (e: ApiException) {
            Logger.w("Google sign-in failed: code=${e.statusCode} msg=${e.localizedMessage}", e)
            Toast.makeText(
                this,
                "Google 登入失敗：${e.statusCode}",
                Toast.LENGTH_LONG,
            ).show()
        }
    }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth?.signInWithCredential(credential)
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    moveMainPage(task.result?.user)
                } else {
                    Logger.d("Firebase auth failed: ${task.exception?.message}")
                    Toast.makeText(this, task.exception?.message, Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun moveMainPage(user: FirebaseUser?) {
        if (user != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}
