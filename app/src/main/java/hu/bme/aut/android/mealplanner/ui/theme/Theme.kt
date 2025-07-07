package hu.bme.aut.android.mealplanner.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import hu.bme.aut.android.mealplanner.R

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40,
    background = Color(0xFFFFCDDC),
    onBackground = Color(0xFFFF78A8),
    surface = Color.White,
    onSurface = Color(0xFFFF78A8),
    onPrimary = Color.White,
    onSecondary = Color(0xFFc0b9a6)
)

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80,
    background = Color(0xFFD9447E),
    onBackground = Color(0xFFFF78A8),
    surface = Color.White,
    onSurface = Color(0xFFFF78A8),
    onPrimary = Color.White,
    onSecondary = Color.White
)

val LobsterFont = FontFamily(Font(R.font.lobsterregular))

val PatrickHandFont = FontFamily(Font(R.font.patrickhand))

val AppTypography = Typography(
    titleLarge = TextStyle(
        fontFamily = LobsterFont,
        fontSize = 28.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = PatrickHandFont,
        fontSize = 22.sp
    )
)

@Composable
fun MealPlannerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        content = content
    )
}