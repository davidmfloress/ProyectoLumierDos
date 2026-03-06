package com.example.proyectolumier.ui.screens

/**
@author: David Muñoz Flores
@author: Marco Lodeiro Ruiz De La Hermosa
 */

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.SettingsSuggest
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.proyectolumier.data.model.MovieGenre
import com.example.proyectolumier.ui.theme.RojoCine
import com.example.proyectolumier.R
import com.example.proyectolumier.ui.theme.NetflixRed

@Composable
fun CategoriaScreen(
    categories: List<MovieGenre>,
    onCategoryClick: (MovieGenre) -> Unit,
    isDarkMode: Boolean?,
    onThemeChange: (Boolean?) -> Unit,
    modifier: Modifier = Modifier
) {
    var showMenu by remember { mutableStateOf(false) }
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.surface,
            shadowElevation = 2.dp
        ) {
            Row(
                modifier = Modifier.padding(if (isLandscape) 4.dp else 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "CATEGORÍAS",
                    style = if (isLandscape) MaterialTheme.typography.titleMedium else MaterialTheme.typography.headlineSmall,
                    color = if (isLandscape) RojoCine else MaterialTheme.colorScheme.onSurface,
                    fontWeight = if (isLandscape) FontWeight.Bold else FontWeight.Light,
                    modifier = Modifier.padding(start = 16.dp).weight(1f)
                )

                Box {
                    IconButton(onClick = { showMenu = true }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "Menú")
                    }
                    DropdownMenu(
                        expanded = showMenu,
                        onDismissRequest = { showMenu = false }
                    ) {
                        Text(
                            "Apariencia",
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                            style = MaterialTheme.typography.labelSmall,
                            color = NetflixRed
                        )
                        DropdownMenuItem(
                            text = { Text("Modo Claro") },
                            leadingIcon = { Icon(Icons.Default.LightMode, null) },
                            onClick = { onThemeChange(false); showMenu = false }
                        )
                        DropdownMenuItem(
                            text = { Text("Modo Oscuro") },
                            leadingIcon = { Icon(Icons.Default.DarkMode, null) },
                            onClick = { onThemeChange(true); showMenu = false }
                        )
                        DropdownMenuItem(
                            text = { Text("Sistema") },
                            leadingIcon = { Icon(Icons.Default.SettingsSuggest, null) },
                            onClick = { onThemeChange(null); showMenu = false }
                        )
                    }
                }
            }
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(if (isLandscape) 4 else 2),
            modifier = Modifier.fillMaxSize().padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalArrangement = Arrangement.spacedBy(24.dp),
            contentPadding = PaddingValues(bottom = 32.dp)
        ) {
            items(categories) { category ->
                CategoryItem(
                    category = category,
                    onCategoryClick = onCategoryClick
                )
            }
        }
    }
}

@Composable
fun CategoryItem(
    category: MovieGenre,
    onCategoryClick: (MovieGenre) -> Unit,
    modifier: Modifier = Modifier
) {
    val imageRes = when (category) {
        MovieGenre.Terror -> R.drawable.logoterror
        MovieGenre.Amor -> R.drawable.logoamor
        MovieGenre.Accion -> R.drawable.logoaccion
        MovieGenre.Comedia -> R.drawable.logocomedia
    }


    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onCategoryClick(category) },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = "Logo ${category.name}",
            modifier = Modifier
                .aspectRatio(1f)
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp)),
            contentScale = ContentScale.Crop

        )

        Spacer(modifier = Modifier.height(1.dp))



        Text(
            text = category.name.uppercase(),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center
        )
    }
}