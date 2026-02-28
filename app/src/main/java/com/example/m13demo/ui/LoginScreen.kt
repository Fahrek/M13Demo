
package com.example.m13demo.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.m13demo.R
import com.example.m13demo.ui.theme.M13DemoTheme

/**
 * Composable that allows the user to open the session. It's the first app screen
 */
@Composable
fun LoginScreen(
    navigate: ()->Unit={},
    viewModel:LoginViewModel=viewModel(),
    modifier: Modifier = Modifier
) {
    var server by remember {mutableStateOf("")}
    var port by remember {mutableStateOf("")}
    var username by remember {mutableStateOf("")}
    var password by remember {mutableStateOf("")}

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))
        ) {
            // Title

            Text(stringResource(R.string.login),fontSize=25.sp)

            // Fields
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))
            TextField(
                label= { Text(stringResource(R.string.server))},
                value= server,
                onValueChange = {server=it}
            )

            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))
            TextField(
                label= { Text(stringResource(R.string.port))},
                value= port,
                onValueChange = {port=it}
            )

            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))
            TextField(
                label= { Text(stringResource(R.string.username))},
                value= username,
                onValueChange = {username=it}
            )

            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))
            TextField(
                label= { Text(stringResource(R.string.password))},
                value= password,
                onValueChange = {password=it},

                // Password options

                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))

            // Action button

            Button(onClick={
              viewModel.doLogin(server, port, username, password)
            }){
                Text(stringResource(R.string.login))
            }

            // Error message if necessary

            if(viewModel.uiState.collectAsState().value.loginTried){
                if(!viewModel.uiIOState.collectAsState().value.goodResult){
                    Text(stringResource(R.string.connection_error_try_again), color= Color.Red)
                }else{
                    navigate()
                }
            }
        }
    }
}


@Preview
@Composable
fun LoginPreview() {
    M13DemoTheme {
        LoginScreen(viewModel=LoginViewModel())

            }
}
