
package org.example.myapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = White,
    background = Black,
    surface = Black,
    onPrimary = Black,
    onBackground = White,
    onSurface = White,
    surfaceVariant = DarkGrey,
    onSurfaceVariant = White
)

private val LightColorScheme = lightColorScheme(
    primary = Black,
    background = White,
    surface = White,
    onPrimary = White,
    onBackground = Black,
    onSurface = Black,
    surfaceVariant = LightGrey,
    onSurfaceVariant = Black
)

@Composable
fun InfinitiumTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
