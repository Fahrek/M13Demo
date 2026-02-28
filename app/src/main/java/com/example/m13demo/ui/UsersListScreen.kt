package com.example.m13demo.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.m13demo.R
import com.example.m13demo.ShowIOResult
import com.example.m13demo.data.User
import com.example.m13demo.ui.theme.M13DemoTheme

/**
 * This composable shows the users list from the server
 */
@Composable
fun UsersListScreen(
    viewModel:UsersListViewModel=viewModel(),
    modifier: Modifier = Modifier
) {
    // Title

    Column() {
        Text(stringResource(R.string.users_list), fontSize = 25.sp)
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))
    }

    val first_weight=0.3f
    val second_weight=0.7f
    //Header
    Column(modifier = Modifier.fillMaxHeight()){
        Row(modifier= Modifier
            .fillMaxWidth()
            .background(Color.Gray)
            .padding(8.dp)
        ) {
                Text(
                    text = stringResource(R.string.username),
                    modifier=Modifier.weight(first_weight),
                    color=Color.White
                )
                Text(
                    text = stringResource(R.string.name),
                    modifier=Modifier.weight(second_weight),
                    color=Color.White
                )
        }

        //Elements list
        var list: List<User> = viewModel.uiState.collectAsState().value.list
        LazyColumn {
            items(list) { item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {

                    Text(
                        text = item.username,
                        modifier = Modifier.weight(first_weight)
                    )

                    Text(
                        text = item.name,
                        modifier = Modifier.weight(second_weight)
                    )

                }
            }
        }
        Row(){
                ShowIOResult(!viewModel.uiIOState.collectAsState().value.goodResult)
        }
    }
}


@Preview
@Composable
fun UsersListPreview() {
    M13DemoTheme {
        UsersListScreen(UsersListViewModel())
    }
}
