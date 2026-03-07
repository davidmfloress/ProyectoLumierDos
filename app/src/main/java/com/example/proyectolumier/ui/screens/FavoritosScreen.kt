package com.example.proyectolumier.ui.screens

/**
 * Pantalla que muestra las películas favoritas del usuario guardadas en Room.
 *
 * @author: David Muñoz Flores
 * @author: Marco Lodeiro Ruiz De La Hermosa
 */

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.proyectolumier.data.db.FavoritoEntity
import com.example.proyectolumier.data.local.LocalMovieDataProvider
import com.example.proyectolumier.ui.theme.RojoCine

@Composable
fun FavoritosScreen(
    favoritos: List<FavoritoEntity>,
    onBackClick: () -> Unit,
    onMovieClick: (Int) -> Unit,
    onEliminarFavorito: (FavoritoEntity) -> Unit,
    modifier: Modifier = Modifier
) {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Barra superior — compacta en landscape
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.surface,
            shadowElevation = 2.dp
        ) {
            Row(
                modifier = Modifier.padding(if (isLandscape) 4.dp else 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBackClick) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Atrás")
                }
                Text(
                    text = "MIS FAVORITOS",
                    style = if (isLandscape) MaterialTheme.typography.titleMedium else MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Light,
                    color = if (isLandscape) RojoCine else MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "${favoritos.size} películas",
                    style = MaterialTheme.typography.bodySmall,
                    color = RojoCine,
                    modifier = Modifier.padding(end = 16.dp)
                )
            }
        }

        if (favoritos.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(imageVector = Icons.Default.FavoriteBorder, contentDescription = null, modifier = Modifier.size(80.dp), tint = MaterialTheme.colorScheme.outline)
                Spacer(modifier = Modifier.height(16.dp))
                Text("No tienes favoritos aún", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f))
                Text("Pulsa el ♥ en cualquier película", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.4f))
            }
        } else if (isLandscape) {
            // LANDSCAPE: grid de 2 columnas para aprovechar el ancho
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(favoritos, key = { it.movieId }) { fav ->
                    val movie = LocalMovieDataProvider.allMovies.find { it.id == fav.movieId }
                    FavoritoCard(
                        fav = fav,
                        movie = movie,
                        onMovieClick = onMovieClick,
                        onEliminarFavorito = onEliminarFavorito,
                        cardHeight = 90.dp
                    )
                }
            }
        } else {
            // PORTRAIT: lista vertical
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(favoritos, key = { it.movieId }) { fav ->
                    val movie = LocalMovieDataProvider.allMovies.find { it.id == fav.movieId }
                    FavoritoCard(
                        fav = fav,
                        movie = movie,
                        onMovieClick = onMovieClick,
                        onEliminarFavorito = onEliminarFavorito,
                        cardHeight = 100.dp
                    )
                }
            }
        }
    }
}

@Composable
private fun FavoritoCard(
    fav: FavoritoEntity,
    movie: com.example.proyectolumier.data.model.Movie?,
    onMovieClick: (Int) -> Unit,
    onEliminarFavorito: (FavoritoEntity) -> Unit,
    cardHeight: androidx.compose.ui.unit.Dp
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(cardHeight)
            .clickable { onMovieClick(fav.movieId) },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(modifier = Modifier.fillMaxSize()) {
            if (movie != null) {
                Image(
                    painter = painterResource(id = movie.imageResourceId),
                    contentDescription = fav.titulo,
                    modifier = Modifier.width(70.dp).fillMaxHeight(),
                    contentScale = ContentScale.Crop
                )
            }
            Column(
                modifier = Modifier.weight(1f).padding(horizontal = 12.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = fav.titulo, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, maxLines = 2)
                Text(text = fav.genero, style = MaterialTheme.typography.bodySmall, color = RojoCine)
            }
            IconButton(onClick = { onEliminarFavorito(fav) }, modifier = Modifier.align(Alignment.CenterVertically)) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Eliminar favorito", tint = MaterialTheme.colorScheme.error)
            }
        }
    }
}
