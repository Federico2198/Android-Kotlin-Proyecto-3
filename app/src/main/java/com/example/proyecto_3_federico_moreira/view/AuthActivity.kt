package com.example.proyecto_3_federico_moreira.view


import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Observer
import com.example.proyecto_3_federico_moreira.R
import com.example.proyecto_3_federico_moreira.viewmodel.AuthViewModel


class AuthActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel = AuthViewModel()

        viewModel.isLoggedIn.observe(this, Observer {
            if(it){
                val intent = Intent(this,ChatsActivity::class.java)

                startActivity(intent)
            }
        })

        setContent {
            AuthActivityContent(){ email,password, name, profilePic, isLogin ->
                viewModel.auth(email = email, password = password, name = name, profilePic = profilePic, isLogin = isLogin)
            }
        }
    }
}


@Composable
fun AuthActivityContent(auth:(email:String,password:String, name:String, profilePic: String, isLogin:Boolean)->Unit){
    val email = remember{
        mutableStateOf("")
    }
    val password = remember{
        mutableStateOf("")
    }
    val name = remember{
        mutableStateOf("")
    }

    val profilePic = remember{
        mutableStateOf("")
    }

    val isLogIn = remember {
        mutableStateOf(true)
    }

    Column {
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            // Logo - User //
            Image(painter = painterResource(id = R.drawable.user),
                contentDescription = "Imagen de Usuario",
                modifier = Modifier
                    .height(150.dp)
                    .padding(top = 0.dp))
            // Box - Formulario Registro, Logeo, Switch //
            Box(
                Modifier.fillMaxHeight()
                    .fillMaxWidth()
                    .padding(top = 50.dp,),
            ){
                Column(
                    Modifier
                        .fillMaxSize()
                        .padding(40.dp)
                        .height((140.dp))
                ){
                    Row(){
                        if (isLogIn.value){
                            Text(
                                text = "Login", fontSize = 36.sp)
                        } else {
                            Text(
                                text = "Registro", fontSize = 36.sp
                            )
                        }

                        Spacer(modifier = Modifier.width(50.dp))

                        Switch(checked = isLogIn.value, onCheckedChange = {
                            isLogIn.value = it
                        })
                    }

                    Column(
                        Modifier
                            .fillMaxSize()
                            .padding(top = 30.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        // Field Email //
                        EmailField(
                            value = email.value){  newValue ->
                            email.value = newValue
                        }

                        // Field Password //
                        PasswordField(value = password.value){ newValue ->
                            password.value = newValue
                        }

                        // Field Name //
                        if (!isLogIn.value) {
                            NameField(value = name.value, changed = { name.value = it
                            })

                        // Field ProfilePic //
                            ProfilePicField(value = profilePic.value, changed =  { profilePic.value = it
                            })
                        }

                        Spacer(modifier = Modifier.height(30.dp))



                        // Boton de Inicio de Sesion y Registro //
                        Button(onClick = {

                            auth(email.value, password.value, name.value, profilePic.value, isLogIn.value)
                        })
                        {
                            if(isLogIn.value){
                                Text("Iniciar sesión")
                            }else{
                                Text("Registrarse")
                            }
                        }
                    }
                }
            }
        }
    }
}

// Composable para el field Email //
@Composable
fun EmailField(value: String, changed: (String) -> Unit) {
    TextField(
        value = value,
        onValueChange = changed,
        label = { Text("Ingrese el correo") },
        modifier = Modifier
            .fillMaxWidth()
    )
}

// Composable para el field Password //
@Composable
fun PasswordField(value: String, changed: (String) -> Unit) {
    TextField(
        value = value,
        onValueChange = changed,
        label = { Text("Ingrese la Contraseña") },
        modifier = Modifier.fillMaxWidth()
    )
}

// Composable para el field Name //
@Composable
fun NameField(value: String, changed: (String) -> Unit) {
    TextField(
        value = value,
        onValueChange = changed,
        label = { Text("Ingrese el nombre") },
        modifier = Modifier.fillMaxWidth()
    )
}

// Composable para el field ProfilePic //
@Composable
fun ProfilePicField(value: String, changed: (String) -> Unit) {
    TextField(
        value = value,
        onValueChange = changed,
        label = { Text("Ingrese la url de la imagen") },
        modifier = Modifier.fillMaxWidth()
    )
}

// Permite mostrar un pre-visualizacion, y es recomendable colocarlo al final del archivo
@Preview(showBackground = true)
@Composable
fun PreviewAuthActivity(){
    AuthActivityContent(){ email, password, name, profilePic, isLogin ->
        print(email)
        print(password)
    }
}