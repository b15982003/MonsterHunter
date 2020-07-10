package com.ray.monsterhunter.profile

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.ray.monsterhunter.data.source.MonsterRepository

class ProfileViewModel(repository: MonsterRepository) : ViewModel() {

    var auth = FirebaseAuth.getInstance()

     fun signOut() {

        auth.signOut()
    }

}
