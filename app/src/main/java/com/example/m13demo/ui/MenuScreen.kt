
package com.example.m13demo.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.m13demo.R
import com.example.m13demo.ui.theme.M13DemoTheme
import androidx.lifecycle.viewmodel.compose.viewModel

/**
 * Composable that displays the list of of app options as buttons
 * [options] map between button text and parameter to pass to onOptionClicked function
 * [onOptionClicked] lambda that triggers a button action
 * [onLogoutClicked] lambda that triggers the logout action
 */
@Composable
fun MenuScreen(
    modifier: Modifier = Modifier,
    options: Map<String, String>,
    onOptionClicked: (String) -> Unit = {},
    onLogoutClicked: (MenuViewModel) -> Unit = {}
) {
    var viewModel:MenuViewModel = viewModel()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(16.dp)
        ) {

            // Title

            Text(stringResource(R.string.choose_action),fontSize=25.sp)
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))

            // Display options as buttons: two buttons in each row (chunked 2), one in the last if necessary

            options.entries.chunked(2).forEach { pair ->
                if (pair.size == 2) {
                    val (first, second) = pair
                    Row() {
                        FilledTonalButton(onClick = { onOptionClicked(first.value)},
                               modifier=Modifier.padding(8.dp)
                        ) {
                            Text(first.key)  //little separation
                        }

                        FilledTonalButton(onClick = { onOptionClicked(second.value) },
                                modifier=Modifier.padding(8.dp)
                        ) {
                            Text(second.key)
                        }
                    }
                } else {
                    val (first) = pair
                    FilledTonalButton(onClick = { onOptionClicked(first.value) },
                        modifier=Modifier.padding(8.dp)
                    ) {
                        Text(first.key)
                    }

                }
            }

            // Logout button
            Spacer(modifier=Modifier.padding(16.dp))
            Button(
                onClick = {
                            viewModel.logout()
                            onLogoutClicked(viewModel)
                          },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp)
            ) {
                Text("Logout")
            }
        }
    }
}

    @Preview
    @Composable
    fun SelectOptionPreview() {
        M13DemoTheme {
            MenuScreen(
                options = mapOf(
                    "Option 1" to "1",
                    "Option 2" to "2",
                    "Option 3" to "3",
                    "Option 4" to "4"
                ),
                modifier = Modifier.fillMaxHeight()
            )
        }

}
