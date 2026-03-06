package com.example.proyectolumier.ui.theme

/**
@author: David Muñoz Flores
@author: Marco Lodeiro Ruiz De La Hermosa
 */

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.proyectolumier.R



val BebasNeue = FontFamily(
    Font(R.font.bebas_neue, FontWeight.Normal)
)

 val DarkColorScheme = darkColorScheme(
    primary = RojoCine,
    secondary = VerdeAzulado,
    surface = GrisOscuro,
    background = Color(0xFF121212),
    onPrimary = BlancoNuclear,
    onSurface = BlancoNuclear,
    onBackground = BlancoNuclear
)

 val LightColorScheme = lightColorScheme(
    primary = RojoCine,
    secondary = VerdeAzulado,
    surface = Color(0xFFBDB4B4),
    background = Color(0xFFFDFDFD),
    onPrimary = Color.White,
    onSurface = Color(0xFF1C1B1F),
    onBackground = Color(0xFF1C1B1F)
)

@Composable
fun ProyectoLumierTheme(

    darkTheme: Boolean = true,
    content: @Composable () -> Unit
) {

    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = com.example.proyectolumier.ui.theme.Typography,
        content = content
    )
}