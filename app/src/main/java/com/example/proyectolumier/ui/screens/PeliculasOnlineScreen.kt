package com.example.proyectolumier.ui.screens

/**
 * Pantalla que carga películas populares desde la API de TMDB usando Retrofit,
 * y muestra sus posters con Coil (AsyncImage).
 * Unidad 5 - Pathway 1 (Retrofit) + Pathway 2 (Coil)
 *
 * @author: David Muñoz Flores
 * @author: Marco Lodeiro Ruiz De La Hermosa
 */

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import com.example.proyectolumier.data.network.TmdbMovie
import com.example.proyectolumier.data.repository.NetworkUiState
import com.example.proyectolumier.data.repository.TmdbRepository
import com.example.proyectolumier.ui.theme.NetflixRed
import com.example.proyectolumier.ui.theme.RojoCine
import com.example.proyectolumier.ui.theme.VerdeAzulado
import kotlinx.coroutines.launch

// ViewModel específico para esta pantalla
class PeliculasOnlineViewModel : ViewModel() {
    private val repository = TmdbRepository()

    var networkState by mutableStateOf<NetworkUiState>(NetworkUiState.Loading)
        private set

    var modoActual by mutableStateOf("popular")
        private set

    init {
        cargarPeliculas()
    }

    fun cargarPeliculas(modo: String = "popular") {
        modoActual = modo
        networkState = NetworkUiState.Loading
        viewModelScope.launch {
            networkState = when (modo) {
                "popular" -> repository.getPopularMovies()
                "top" -> repository.getTopRatedMovies()
                else -> repository.getPopularMovies()
            }
        }
    }
}

@Composable
fun PeliculasOnlineScreen(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val vm: PeliculasOnlineViewModel = viewModel()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // TopBar
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.surface,
            shadowElevation = 2.dp
        ) {
            Row(
                modifier = Modifier.padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBackClick) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, "Atrás")
                }
                Text(
                    text = "PELÍCULAS ONLINE",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Light,
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = { vm.cargarPeliculas(vm.modoActual) }) {
                    Icon(Icons.Default.Refresh, "Recargar", tint = RojoCine)
                }
            }
        }

        // Selector popular / mejor valoradas
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FilterChip(
                selected = vm.modoActual == "popular",
                onClick = { vm.cargarPeliculas("popular") },
                label = { Text("Populares") },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = NetflixRed,
                    selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                )
            )
            FilterChip(
                selected = vm.modoActual == "top",
                onClick = { vm.cargarPeliculas("top") },
                label = { Text("Mejor valoradas") },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = NetflixRed,
                    selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }

        // Contenido según estado de red
        when (val state = vm.networkState) {
            is NetworkUiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        CircularProgressIndicator(color = NetflixRed)
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            "Cargando desde internet...",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                        )
                    }
                }
            }

            is NetworkUiState.Error -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(32.dp)) {
                        Icon(
                            Icons.Default.BrokenImage,
                            null,
                            modifier = Modifier.size(64.dp),
                            tint = MaterialTheme.colorScheme.error
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            "Error de conexión",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            state.message,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = { vm.cargarPeliculas(vm.modoActual) },
                            colors = ButtonDefaults.buttonColors(containerColor = NetflixRed)
                        ) {
                            Text("Reintentar")
                        }
                    }
                }
            }

            is NetworkUiState.Success -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(state.movies) { movie ->
                        TmdbMovieCard(movie = movie)
                    }
                }
            }
        }
    }
}

@Composable
fun TmdbMovieCard(movie: TmdbMovie) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(260.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {

            // Coil AsyncImage — carga el poster desde internet (Unidad 5 Pathway 2)
            SubcomposeAsyncImage(
                model = movie.posterUrl,
                contentDescription = movie.title,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                loading = {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.surfaceVariant),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(32.dp),
                            color = NetflixRed,
                            strokeWidth = 2.dp
                        )
                    }
                },
                error = {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.surfaceVariant),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Default.Movie,
                            null,
                            modifier = Modifier.size(48.dp),
                            tint = MaterialTheme.colorScheme.outline
                        )
                    }
                }
            )

            // Título y puntuación sobre el poster
            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.9f))
                    .padding(8.dp)
            ) {
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.Star,
                        null,
                        tint = VerdeAzulado,
                        modifier = Modifier.size(12.dp)
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        text = "${"%.1f".format(movie.voteAverage)} · ${movie.releaseDate.take(4)}",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                }
            }
        }
    }
}
