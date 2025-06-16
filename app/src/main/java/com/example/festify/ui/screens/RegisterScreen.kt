package com.example.festify.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.festify.R
import com.example.festify.ui.navigation.Login
import com.example.festify.ui.navigation.Register
import com.example.festify.ui.viewmodels.AuthRegisterViewModel

@Preview(name = "Register Body Preview")
@Composable
fun RegisterBodyPreview() {
    // Puedes envolverlo en tu tema si es necesario
    // YourAppTheme {
    Box(
        Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xF84A0286), // Morado muy oscuro
                        Color(0xF329074F), // Morado oscuro
                        Color(0xFF040109),  // Morado medio
                        Color(0xFF040109)  // Morado medio
                    )
                )
            )
            .statusBarsPadding()
            .verticalScroll(rememberScrollState()),
        contentAlignment = Alignment.Center,

    ){
        Column(
            Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            HeaderPresentation()

            HeaderImage()

            Box(
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .background(
                        color = Color(0xEEB39FC0).copy(alpha = 0.2f),
                        shape = RoundedCornerShape(16.dp)
                    ),
            ) {
                RegisterBody(
                    username = "testuser",
                    email = "preview@example.com",
                    password = "password123",
                    onUsernameChange = { /* No-op para preview */ },
                    onEmailChange = { /* No-op para preview */ },
                    onPasswordChange = { /* No-op para preview */ },
                    onClick = { /* No-op para preview */ },
                    errorUsername = "",
                    errorEmail = "",
                    errorPassword = "",
                    onRedirectToLogin = { /* No-op para preview */ }
                )
            }

        }

    }
}

@Composable
fun MainRegisterScreen(viewModel: AuthRegisterViewModel = hiltViewModel(),navController: NavController){
    val uiState by viewModel.uiState.collectAsState()

    Box(
        Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xF84A0286), // Morado muy oscuro
                        Color(0xF329074F), // Morado oscuro
                        Color(0xFF040109),  // Morado medio
                        Color(0xFF040109)  // Morado medio
                    )
                )
            )
            .statusBarsPadding()
            .verticalScroll(rememberScrollState()),
        contentAlignment = Alignment.Center,
    ){
        //principal column
        Column(
            Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            //title
            HeaderPresentation()

            //image
            HeaderImage()

            //form
            Box(
                 Modifier
                     .padding(horizontal = 10.dp)
                     .background(
                         color = Color(0xEEB39FC0).copy(alpha = 0.2f),
                         shape = RoundedCornerShape(16.dp)
                     ),
            ){
                RegisterBody(
                    email = uiState.email,
                    password = uiState.password,
                    username = uiState.username,
                    onEmailChange = viewModel::onEmailChange,
                    onPasswordChange = viewModel::onPasswordChange,
                    onUsernameChange = viewModel::onUsernameChange,
                    onClick = viewModel::onRegister,
                    errorEmail = uiState.emailError,
                    errorPassword = uiState.passwordError,
                    errorUsername = uiState.usernameError,
                    onRedirectToLogin = {
                        navController.navigate(Login) {
                            popUpTo(Register) {
                                inclusive = true
                            }
                        }
                    }
                )
            }

        }

        when(uiState.isLoading){
            true -> CircularProgressIndicator()
            false -> {}
        }
    }
}



@Composable
fun RegisterBody(
    username:String,
    email:String,
    password: String,
    onUsernameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    errorUsername:String?,
    errorEmail:String?,
    errorPassword:String?,
    onRedirectToLogin: () -> Unit,

    onClick: () -> Unit
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        UsernameInput(username,errorUsername,onUsernameChange)
        Spacer(modifier = Modifier.height(16.dp))

        EmailInput(email,errorEmail,onEmailChange)
        Spacer(modifier = Modifier.height(16.dp))

        PasswordInput(password,errorPassword,onPasswordChange)
        Spacer(modifier = Modifier.height(16.dp))

        ButtonActionRegister(onClick)
        Spacer(modifier = Modifier.height(16.dp))

        ButtonToLogin(onRedirectToLogin)
    }
}

@Composable
fun HeaderPresentation(){
    Text("Bienvenido a Festify", textAlign = TextAlign.Center,color = Color.White,fontSize = 30.sp,fontWeight = FontWeight.Bold)
}

@Composable
fun HeaderImage(){
    val configuration = LocalConfiguration.current
    val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT

    val screenHeight = configuration.screenHeightDp.dp
    val imageHeight = if (isPortrait) screenHeight * 0.25f else screenHeight * 0.4f

    Image(
        painter = painterResource(id = R.drawable.people),
        contentDescription = "Ilustración de personas",
        modifier = Modifier
            .fillMaxWidth()
            .height(imageHeight),
        contentScale = if (isPortrait) ContentScale.Crop else ContentScale.Fit
    )
}

@Composable
fun UsernameInput(username:String,errorMsg:String?, onUsernameChange: (String) -> Unit){
    val onChange = rememberUpdatedState(newValue = onUsernameChange)

    OutlinedTextField(
        value = username,
        onValueChange = { onChange.value(it) },
        modifier = Modifier.fillMaxWidth(),
        label = { Text("Nombre de usuario") },
        isError = !errorMsg.isNullOrEmpty(),
        supportingText = {
            if (!errorMsg.isNullOrEmpty()){
                Text(errorMsg)
            }
        }
    )
}

@Composable
fun EmailInput(email:String,errorMsg:String?, onEmailChange:(String) -> Unit){
    val onChange = rememberUpdatedState(newValue = onEmailChange)
    OutlinedTextField(
        value = email,
        onValueChange = { onChange.value(it) },
        modifier = Modifier.fillMaxWidth(),
        label = { Text("Correo electronico") },
        isError = !errorMsg.isNullOrEmpty(),
        supportingText = {
            if (!errorMsg.isNullOrEmpty()){
                Text(errorMsg)
            }
        }
    )
}

@Composable
fun PasswordInput(password:String,errorMsg:String?,onPasswordChange: (String) -> Unit){
    val onChange = rememberUpdatedState(newValue = onPasswordChange)
    OutlinedTextField(
        value = password,
        onValueChange = { onChange.value(it) },
        label = { Text("Contraseña") },
        modifier = Modifier.fillMaxWidth(),
        visualTransformation = PasswordVisualTransformation(),
        isError = !errorMsg.isNullOrEmpty(),
        supportingText = {
            if (!errorMsg.isNullOrEmpty()){
                Text(errorMsg)
            }
        }
    )
}

@Composable
fun ButtonActionRegister(onClick: () -> Unit){
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("Registrarme")
    }
}

@Composable
fun ButtonToLogin(onClick: () -> Unit){
    TextButton(
        onClick = onClick
    ){
        Text("Ya tengo una cuenta")
    }
}
