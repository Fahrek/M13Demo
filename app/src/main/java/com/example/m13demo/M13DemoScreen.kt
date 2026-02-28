/*
 * Adapted from The Android Open Source Project
 */
package com.example.m13demo

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.m13demo.R.dimen
import com.example.m13demo.R.string
import com.example.m13demo.data.DataSource
import com.example.m13demo.ui.AddScreen
import com.example.m13demo.ui.LoginScreen
import com.example.m13demo.ui.MenuScreen
import com.example.m13demo.ui.QueryScreen
import com.example.m13demo.ui.UsersListScreen


enum class Screens(val title: String) {
    Login(title = "Login"),
    Menu(title = "Menu"),
    Query(title = "Query"),
    List(title = "List"),
    Add(title="Add")
}

/**
 * Composable that displays the topBar and displays back button if back navigation is possible.
 */
@Composable
fun M13DemoAppBar(
    currentScreen: Screens,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(stringResource(id = string.app_name)) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(string.back_button)
                    )
                }
            }
        }
    )
}

/*
    App screen. It shows AppBar and, down of it, the other screens
 */

@Preview
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun M13DemoApp(

    navController: NavHostController = rememberNavController()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = Screens.valueOf(
        backStackEntry?.destination?.route ?: Screens.Login.name
    )
    Scaffold(
        topBar = {
            M13DemoAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        }
    ) {  innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screens.Login.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = Screens.Login.name){
                LoginScreen(
                    navigate = {navController.navigate(Screens.Menu.name){// We clear the stack of screens
                        popUpTo(navController.graph.startDestinationId) { inclusive = true }
                        launchSingleTop = true
                        }
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(dimensionResource(dimen.padding_medium))
                )
            }
            composable(route = Screens.Menu.name) {
                val context = LocalContext.current
                MenuScreen(
                    onOptionClicked = { navController.navigate(it) },
                    onLogoutClicked={
                        navController.navigate(Screens.Login.name){// We clear the stack of screens
                            popUpTo(Screens.Menu.name) { inclusive = true }
                            launchSingleTop = true
                    }


                    },
                    options = DataSource.options,
                    modifier = Modifier.fillMaxHeight()
                )
            }
            composable(route = Screens.List.name) {
                UsersListScreen(
                    modifier = Modifier.fillMaxHeight()
                )
            }

            composable(route = Screens.Query.name){
                QueryScreen(
                    modifier = Modifier.fillMaxHeight()
                )

            }

            composable(route = Screens.Add.name){
                AddScreen(
                    modifier = Modifier.fillMaxHeight()
                )
            }

        }
    }


}

/*
    Shows error message if parameter is true
 */

@Composable
fun ShowIOResult(error:Boolean){
    if(error)
    {Text(stringResource(string.data_could_not_be_processed), color= Color.Red)}
}

