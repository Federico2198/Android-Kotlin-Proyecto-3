package com.example.proyecto_3_federico_moreira.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.proyecto_3_federico_moreira.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class AuthViewModel:ViewModel() {
    private var auth:FirebaseAuth = Firebase.auth
    var currentUser = auth.currentUser
    private val _isLoggedIn = MutableLiveData<Boolean>(false)
    val isLoggedIn: LiveData<Boolean>
        get() = _isLoggedIn
    var message = ""
    var isError = false

    // Referencia a Database //
    private var database: DatabaseReference = Firebase.database.reference

    init{
        if (currentUser!=null){
            _isLoggedIn.value = true
        }
    }

    fun auth(email:String,password:String, name:String, profilePic:String, isLogin:Boolean){
        if(isLogin){
            login(email = email, password = password)
        }else{
            register(email = email, password = password, name = name, profilePic = profilePic)
        }
    }


    private fun login(email:String,password:String){
        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener{ result ->
                if(result.isSuccessful){
                    currentUser = auth.currentUser
                    _isLoggedIn.value = true
                    Log.d("AUTH","Usuario registrado: "+currentUser.toString())
                }else{
                    Log.d("AUTH","Usuario fallido registrado: ",result.exception)
                    message = "El inicio de sesión falló"
                    isError = true
                }
            }
    }

    private fun register(email:String,password:String, name: String, profilePic: String){
        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener { result ->
                if(result.isSuccessful){
                    currentUser = auth.currentUser
                    _isLoggedIn.value = true
                    println(result.result.user?.uid)
                    Log.d("AUTH","Usuario registrado: "+currentUser.toString())

                    //Hacer otra consulta (RealTimeDB) para guardar la foto y nombre
                    // Ejemplo:
                    //val user = User(name,profilePic,email,id)
                    //database.child("users").child(result.result.user?.uid).setValue(user)

                    //Ejemplo de lectura:
                    /*val usersListener = object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            // dataSnaphost son los datos de usuarios
                            val user = dataSnapshot.getValue<User>()
                        }
                        override fun onCancelled(databaseError: DatabaseError) {
                            //Fallo de lectura
                            Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
                        }
                    }*/
                    //database.child("users").addValueEventListener(usersListener)

                    val id = result.result.user?.uid
                    val user = User( name, profilePic)
                    database.child("users").child(id!!).setValue(user)


                }else{
                    Log.d("AUTH","Usuario fallido registrado: ",result.exception)
                    message = "El inicio de sesión falló"
                    isError = true
                }
            }
    }
}