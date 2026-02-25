package org.example.myapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.example.myapp.ui.theme.InfinitiumTheme

@Composable
fun MyProjectButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.secondary,
            contentColor = MaterialTheme.colorScheme.onSecondary
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Text(text)
    }
}

// To add a new button, just add a new label to this list!
val mainScreenButtonLabels = listOf(
    "Button 1",
    "Button 2",
    "Button 3",
    "Button 4",
    "Button 5",
    "Button 6",
    "Button 7",
    "Button 8",
    "Button 9",
    "Button 10"
)

@Composable
fun MainScreen() {
    // LazyColumn is great for performance with long lists.
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(mainScreenButtonLabels) { label ->
            MyProjectButton(
                // You can define actions for each button here,
                // for example by using a when(label) { ... } block
                onClick = { /* TODO: Handle button click */ },
                text = label
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    InfinitiumTheme {
        MainScreen()
    }
}
