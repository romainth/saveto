package at.xa1.saveto.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import at.xa1.saveto.navigation.Destination

@Composable
@Preview
fun Loading(modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier
            .then(modifier),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
    }
}

val LoadingDestination = Destination<Unit> {
    Loading(modifier = Modifier.fillMaxSize())
}
