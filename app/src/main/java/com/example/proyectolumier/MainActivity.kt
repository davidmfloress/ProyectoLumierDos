package com.example.proyectolumier

/**
@author: David Muñoz Flores
@author: Marco Lodeiro Ruiz De La Hermosa
 */

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.proyectolumier.navigation.CineScreen
import com.example.proyectolumier.ui.CineViewModel
import com.example.proyectolumier.ui.screens.*
import com.example.proyectolumier.ui.theme.*

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class, ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: CineViewModel = viewModel()
            val uiState by viewModel.uiState.collectAsState()
            val favoritos by viewModel.favoritos.collectAsState()

            val systemInDarkTheme = isSystemInDarkTheme()
            val darkModeActual = uiState.isDarkMode ?: systemInDarkTheme

            val configuration = LocalConfiguration.current
            val windowSize = calculateWindowSizeClass(this)
            val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
            val useSplitScreen = windowSize.widthSizeClass == WindowWidthSizeClass.Expanded

            ProyectoLumierTheme(darkTheme = darkModeActual) {
                val navController = rememberNavController()
                val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

                val shouldShowTopBar = !(isLandscape && (
                    currentRoute == CineScreen.Details.name ||
                    currentRoute == CineScreen.Categories.name ||
                    currentRoute == CineScreen.Titles.name
                )) && currentRoute != CineScreen.Login.name

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        if (shouldShowTopBar) {
                            CenterAlignedTopAppBar(
                                navigationIcon = {
                                    val logoRes = if (darkModeActual) R.drawable.logolumiernigga else R.drawable.logolumiernormal
                                    IconButton(
                                        onClick = {
                                            navController.navigate(CineScreen.Categories.name) {
                                                popUpTo(CineScreen.Categories.name) { inclusive = true }
                                            }
                                        },
                                        modifier = Modifier.size(if (isLandscape && !useSplitScreen) 80.dp else 100.dp)
                                    ) {
                                        Image(
                                            painter = painterResource(id = logoRes),
                                            contentDescription = "Logo Lumier",
                                            modifier = Modifier.size(130.dp).padding(start = 12.dp),
                                            contentScale = ContentScale.Fit
                                        )
                                    }
                                },
                                title = {
                                    Text(
                                        text = "LUMIER",
                                        fontFamily = BebasNeue,
                                        fontSize = if (isLandscape && !useSplitScreen) 24.sp else 38.sp,
                                        fontWeight = FontWeight.Normal,
                                        letterSpacing = 2.sp,
                                        style = MaterialTheme.typography.headlineLarge,
                                        color = NetflixRed
                                    )
                                },
                                actions = {
                                    // Botón películas online (Retrofit + Coil)
                                    IconButton(onClick = {
                                        navController.navigate(CineScreen.PeliculasOnline.name)
                                    }) {
                                        Icon(Icons.Default.Public, contentDescription = "Online", tint = VerdeAzulado)
                                    }

                                    // Botón Favoritos
                                    if (uiState.isLoggedIn) {
                                        IconButton(onClick = {
                                            navController.navigate(CineScreen.Favoritos.name)
                                        }) {
                                            Icon(Icons.Default.Favorite, contentDescription = "Favoritos", tint = NetflixRed)
                                        }
                                    }

                                    // Botón Geo
                                    IconButton(onClick = {
                                        navController.navigate(CineScreen.Geolocalizacion.name)
                                    }) {
                                        Icon(Icons.Default.LocationOn, contentDescription = "Cines cercanos", tint = VerdeAzulado)
                                    }

                                    // Menú usuario
                                    if (uiState.isLoggedIn) {
                                        var showUserMenu by remember { mutableStateOf(false) }
                                        Box {
                                            IconButton(onClick = { showUserMenu = true }) {
                                                Icon(Icons.Default.AccountCircle, contentDescription = "Usuario")
                                            }
                                            DropdownMenu(expanded = showUserMenu, onDismissRequest = { showUserMenu = false }) {
                                                Text(
                                                    text = uiState.usuarioEmail ?: "",
                                                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                                                    style = MaterialTheme.typography.labelSmall,
                                                    color = NetflixRed
                                                )
                                                HorizontalDivider()
                                                DropdownMenuItem(
                                                    text = { Text("Cerrar sesión") },
                                                    leadingIcon = { Icon(Icons.Default.Logout, null) },
                                                    onClick = {
                                                        viewModel.cerrarSesion()
                                                        showUserMenu = false
                                                        navController.navigate(CineScreen.Login.name) {
                                                            popUpTo(0) { inclusive = true }
                                                        }
                                                    }
                                                )
                                            }
                                        }
                                    }
                                }
                            )
                        }
                    }
                ) { innerPadding ->
                    val modifier = Modifier.padding(innerPadding)

                    NavHost(
                        navController = navController,
                        startDestination = CineScreen.Login.name,
                        modifier = modifier
                    ) {
                        // LOGIN
                        composable(CineScreen.Login.name) {
                            LoginScreen(
                                isDarkMode = darkModeActual,
                                onLoginSuccess = { email ->
                                    viewModel.setUsuarioLogado(email)
                                    navController.navigate(CineScreen.Categories.name) {
                                        popUpTo(CineScreen.Login.name) { inclusive = true }
                                    }
                                }
                            )
                        }

                        // CATEGORÍAS
                        composable(CineScreen.Categories.name) {
                            CategoriaScreen(
                                categories = uiState.categoryList,
                                onCategoryClick = {
                                    viewModel.updateCurrentGenre(it)
                                    navController.navigate(CineScreen.Titles.name)
                                },
                                isDarkMode = uiState.isDarkMode,
                                onThemeChange = { viewModel.updateTheme(it) }
                            )
                        }

                        // LISTADO
                        composable(CineScreen.Titles.name) {
                            val listaFiltradaPorEdad = if (uiState.selectedAgeFilter == "Todas") {
                                uiState.moviesOfSelectedGenre
                            } else {
                                uiState.moviesOfSelectedGenre.filter { movie ->
                                    stringResource(movie.ageRes) == uiState.selectedAgeFilter
                                }
                            }
                            ListaPelisScreen(
                                movies = listaFiltradaPorEdad,
                                searchText = uiState.searchText,
                                onSearchChange = { texto, ctx -> viewModel.onSearchTextChange(texto, ctx) },
                                onMovieClick = { movie ->
                                    viewModel.updateDetailsScreenState(movie)
                                    navController.navigate(CineScreen.Details.name)
                                },
                                onBackClick = { navController.popBackStack() },
                                isExpanded = isLandscape,
                                viewModel = viewModel,
                                onThemeChange = { viewModel.updateTheme(it) }
                            )
                        }

                        // DETALLE
                        composable(CineScreen.Details.name) {
                            val context = LocalContext.current
                            val favoritosActuales by viewModel.favoritos.collectAsState()
                            val isFavorito = favoritosActuales.any { it.movieId == uiState.selectedMovie.id }
                            DetallePeliScreen(
                                movie = uiState.selectedMovie,
                                onBackClick = { navController.popBackStack() },
                                onThemeChange = { viewModel.updateTheme(it) },
                                isFavorito = isFavorito,
                                onToggleFavorito = { viewModel.toggleFavorito(uiState.selectedMovie, context) }
                            )
                        }

                        // FAVORITOS
                        composable(CineScreen.Favoritos.name) {
                            FavoritosScreen(
                                favoritos = favoritos,
                                onBackClick = { navController.popBackStack() },
                                onMovieClick = { movieId ->
                                    val movie = com.example.proyectolumier.data.local.LocalMovieDataProvider.allMovies.find { it.id == movieId }
                                    movie?.let {
                                        viewModel.updateDetailsScreenState(it)
                                        navController.navigate(CineScreen.Details.name)
                                    }
                                },
                                onEliminarFavorito = { fav -> viewModel.eliminarFavorito(fav) }
                            )
                        }

                        // GEOLOCALIZACIÓN
                        composable(CineScreen.Geolocalizacion.name) {
                            GeolocalizacionScreen(onBackClick = { navController.popBackStack() })
                        }

                        // PELÍCULAS ONLINE — Retrofit + Coil
                        composable(CineScreen.PeliculasOnline.name) {
                            PeliculasOnlineScreen(onBackClick = { navController.popBackStack() })
                        }
                    }
                }
            }
        }
    }
}
