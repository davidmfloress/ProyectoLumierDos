package com.example.proyectolumier.ui.screens

/**
 * @author: David Muñoz Flores
 * @author: Marco Lodeiro Ruiz De La Hermosa
 */

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SettingsSuggest
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.proyectolumier.data.model.Movie
import com.example.proyectolumier.ui.CineViewModel
import com.example.proyectolumier.ui.MovieOrder
import com.example.proyectolumier.ui.theme.NetflixRed
import com.example.proyectolumier.ui.theme.RojoCine
import com.example.proyectolumier.ui.theme.VerdeAzulado

@Composable
fun ListaPelisScreen(
    movies: List<Movie>,
    onMovieClick: (Movie) -> Unit,
    onBackClick: () -> Unit,
    isExpanded: Boolean,
    onSearchChange: (String, Context) -> Unit,
    searchText: String,
    viewModel: CineViewModel,
    onThemeChange: (Boolean?) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()
    var showMenu by remember { mutableStateOf(false) }

    Column(modifier = modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.surface,
            shadowElevation = 4.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = if (isExpanded) 2.dp else 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Atrás",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }


                Text(
                    text = if (isExpanded) "TÍTULOS" else "LISTADO",
                    style = if (isExpanded) MaterialTheme.typography.titleMedium else MaterialTheme.typography.titleLarge,
                    color = if (isExpanded) RojoCine else MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(end = 8.dp)
                )


                OutlinedTextField(
                    value = searchText,
                    onValueChange = { onSearchChange(it, context) },
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = if (isExpanded) 4.dp else 8.dp),
                    placeholder = { Text("Buscar...", style = MaterialTheme.typography.bodyMedium) },
                    leadingIcon = { Icon(Icons.Default.Search, null, modifier = Modifier.size(20.dp)) },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = RojoCine,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline
                    )
                )


                Box(modifier = Modifier.padding(start = 4.dp)) {
                    IconButton(onClick = { showMenu = true }) {
                        Icon(Icons.Default.MoreVert, "Opciones")
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

                        HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))


                        Text(
                            "Ordenar por",
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                            style = MaterialTheme.typography.labelSmall,
                            color = NetflixRed
                        )
                        DropdownMenuItem(
                            text = { Text("A - Z") },
                            onClick = { viewModel.updateOrder(MovieOrder.AZ, context); showMenu = false }
                        )
                        DropdownMenuItem(
                            text = { Text("Más recientes") },
                            onClick = { viewModel.updateOrder(MovieOrder.YearNewest, context); showMenu = false }
                        )

                        HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))

                        Text(
                            "Filtrar por Edad",
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                            style = MaterialTheme.typography.labelSmall,
                            color = RojoCine
                        )
                        val edades = listOf("Todas", "TP", "7+", "12+", "16+", "18+")
                        edades.forEach { edad ->
                            DropdownMenuItem(
                                text = {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(edad)
                                        if (uiState.selectedAgeFilter == edad) {
                                            Spacer(Modifier.weight(1f))
                                            Icon(Icons.Default.Check, null, modifier = Modifier.size(16.dp), tint = VerdeAzulado)
                                        }
                                    }
                                },
                                onClick = { viewModel.updateAgeFilter(edad); showMenu = false }
                            )
                        }
                    }
                }
            }
        }


        LazyVerticalGrid(
            columns = GridCells.Fixed(if (isExpanded) 4 else 2),
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(movies) { movie ->
                Card(
                    modifier = Modifier
                        .height(if (isExpanded) 200.dp else 260.dp)
                        .clickable { onMovieClick(movie) },
                    border = BorderStroke(1.dp, RojoCine.copy(alpha = 0.5f)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Image(
                            painter = painterResource(id = movie.imageResourceId),
                            contentDescription = stringResource(movie.title),
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                        Text(
                            text = stringResource(movie.title),
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .fillMaxWidth()
                                .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.85f))
                                .padding(8.dp),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1
                        )
                    }
                }
            }
        }
    }
}