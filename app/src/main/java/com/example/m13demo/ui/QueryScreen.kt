
package com.example.m13demo.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.m13demo.R
import com.example.m13demo.ShowIOResult
import com.example.m13demo.ui.theme.M13DemoTheme

/**
 * Composable that allows to get user information based on his username
 */
@Composable
fun QueryScreen(
    viewModel: QueryViewModel=viewModel(),
    modifier: Modifier = Modifier
) {
    var username by remember {mutableStateOf("")}


    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        // Title

        Text(stringResource(R.string.find_user_by_username),fontSize=25.sp)
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))

        Column(
            modifier = Modifier.fillMaxWidth().fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))
        ) {
            // Input field

          //  Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))
            TextField(
                label= { Text(stringResource(R.string.username))},
                value= username,
                onValueChange = {username=it}
            )

            //Action button

            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))
            Button(onClick={viewModel.getUser(username)})
            {
                Text(stringResource(R.string.query))
            }

            // Information field(s). Is / are read only field(s)

            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))
            TextField(
                label = {Text(stringResource(R.string.name))},
                value=viewModel.uiState.collectAsState().value.name,
                onValueChange = {},
                readOnly=true
            )

            ShowIOResult(!viewModel.uiIOState.collectAsState().value.goodResult)


        }

    }
}


@Preview
@Composable
fun QueryPreview() {
    M13DemoTheme {
       QueryScreen(QueryViewModel())

    }
}
