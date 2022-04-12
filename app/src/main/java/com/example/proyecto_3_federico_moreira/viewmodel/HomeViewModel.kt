package com.example.proyecto_3_federico_moreira.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.proyecto_3_federico_moreira.model.Chat
import com.example.proyecto_3_federico_moreira.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class HomeViewModel: ViewModel() {
    private var auth: FirebaseAuth = Firebase.auth
    private val _isLoggedIn = MutableLiveData<Boolean>(true)
    val isLoggedIn: LiveData<Boolean>
        get() = _isLoggedIn

    // Referencia a Firebase database //
    private var database: DatabaseReference = Firebase.database.reference

    // logout //
    fun logout(){
        auth.signOut()
        _isLoggedIn.value = false
    }

    // Detalle de envio y lectura de un Snippets de database //
    val chatListener = object : ValueEventListener {

        override fun onDataChange(snapshot: DataSnapshot) {
            val chat = snapshot.getValue<User>()
        }

        override fun onCancelled(databaseError: DatabaseError) {
            Log.w("loadPost:onCancelled", databaseError.toException())
        }
    }

    fun setMessage(content:String) {
        FirebaseDatabase.getInstance()
            .getReference("chats")
            .push()
            .setValue(
                Chat(content),
                FirebaseAuth.getInstance()
                    .currentUser
                    ?.displayName
            )
    }
}