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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.festify.R
import com.example.festify.ui.navigation.Home
import com.example.festify.ui.navigation.Login
import com.example.festify.ui.navigation.Register
import com.example.festify.ui.viewmodels.AuthLoginViewModel

@Preview(name = "login Body Preview")
@Composable
fun PreviewLoginScreen(){
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
            HeaderPresentationLogin()

            Box(
                Modifier
                    .padding(horizontal = 10.dp)
                    .background(
                        color = Color(0xEEB39FC0).copy(alpha = 0.2f),
                        shape = RoundedCornerShape(16.dp)
                    )
            ){
                LoginBody(
                    email = "email",
                    password = "password",
                    errorEmail = "",
                    errorPassword = "",
                    onPasswordChange = {  },
                    onEmailChange = {  },
                    save = true,
                    onChipChange = {},
                    onClick = { },
                    goToRegister = {  }
                )
            }
        }

    }
}

@Composable
fun MainLoginScreen(viewModel: AuthLoginViewModel = hiltViewModel(),  navController: NavController){
    val uiState by viewModel.uiLoginState.collectAsState()

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
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HeaderPresentationLogin()

            Box(
                Modifier
                    .padding(horizontal = 10.dp)
                    .background(
                        color = Color(0xEEB39FC0).copy(alpha = 0.2f),
                        shape = RoundedCornerShape(16.dp)
                    )
            ){
                LoginBody(
                    email = uiState.email,
                    password = uiState.password,
                    errorEmail = uiState.emailError,
                    errorPassword = uiState.passwordError,
                    onPasswordChange = viewModel::onPasswordChange,
                    onEmailChange =  viewModel::onEmailChange,
                    save = uiState.savedData,
                    onClick = viewModel::onLogin,
                    onChipChange = viewModel::onClickCip,
                    goToRegister = { navController.navigate(Register){
                        popUpTo(Login) {
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
        if (uiState.isSuccess) {
            LaunchedEffect(Unit) {
                navController.navigate(Home) {
                    popUpTo(Login) { inclusive = true }
                }
            }
        }


    }

}

@Composable
fun LoginBody(
    email:String,
    password: String,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    errorEmail:String?,
    errorPassword:String?,
    save: Boolean,
    onClick: () -> Unit,
    onChipChange: () -> Unit,
    goToRegister: () -> Unit
){

    Column(
        Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        InputEmailLogin(email, errorEmail,onEmailChange)
        Spacer(modifier = Modifier.height(16.dp))

        InputPasswordLogin(password,errorPassword,onPasswordChange)
        Spacer(modifier = Modifier.height(16.dp))

        ButtonActionLogin(onClick)
        Spacer(modifier = Modifier.height(16.dp))

        GoToRegisterTextButton(goToRegister)
        Spacer(modifier = Modifier.height(16.dp))

        SaveDataLogin(save,onChipChange)

    }

}

@Composable
fun HeaderPresentationLogin(){
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
fun InputEmailLogin(email:String,errorMsg:String?, onEmailChange:(String) -> Unit){
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
fun InputPasswordLogin(password:String,errorMsg:String?,onPasswordChange: (String) -> Unit){
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
fun SaveDataLogin (save:Boolean,onClick: () -> Unit){
    FilterChip(
        onClick = onClick,
        label = {
            Text("Guardar datos")
        },
        selected = save,
        leadingIcon = if (save) {
            {
                Icon(
                    imageVector = Icons.Filled.Done,
                    contentDescription = "Done icon",
                    modifier = Modifier.size(FilterChipDefaults.IconSize)
                )
            }
        } else {
            null
        }

    )
}

@Composable
fun ButtonActionLogin(onClick: () -> Unit){
    OutlinedButton(
        onClick = onClick
    ) {
        Text("Iniciar sesion")
    }
}

@Composable
fun GoToRegisterTextButton(toRegister: () -> Unit){
    TextButton(
        onClick = toRegister
    ) {
        Text("No tienes una cuenta?.Registrate")
    }
}