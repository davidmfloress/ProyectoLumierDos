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

                // Pantallas donde ocultamos la topbar en landscape (tienen su propia barra compacta)
                val shouldShowTopBar = !(isLandscape && (
                    currentRoute == CineScreen.Details.name ||
                    currentRoute == CineScreen.Categories.name ||
                    currentRoute == CineScreen.Titles.name ||
                    currentRoute == CineScreen.Favoritos.name ||
                    currentRoute == CineScreen.Geolocalizacion.name ||
                    currentRoute == CineScreen.PeliculasOnline.name
                )) && currentRoute != CineScreen.Login.name

                // Mostramos la bottom bar en todas las pantallas excepto Login
                // y excepto las pantallas de detalle/lista donde hay botón atrás
                val shouldShowBottomBar = currentRoute != CineScreen.Login.name &&
                    currentRoute != null

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
                                    // Menú usuario solo en topbar
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
                    },
                    bottomBar = {
                        // Bottom navigation — visible siempre excepto en Login
                        // Así los botones se ven tanto en portrait como en landscape
                        if (shouldShowBottomBar) {
                            NavigationBar {
                                NavigationBarItem(
                                    selected = currentRoute == CineScreen.Categories.name,
                                    onClick = {
                                        navController.navigate(CineScreen.Categories.name) {
                                            popUpTo(CineScreen.Categories.name) { inclusive = true }
                                        }
                                    },
                                    icon = { Icon(Icons.Default.Home, contentDescription = "Inicio") },
                                    label = { Text("Inicio") }
                                )
                                NavigationBarItem(
                                    selected = currentRoute == CineScreen.PeliculasOnline.name,
                                    onClick = { navController.navigate(CineScreen.PeliculasOnline.name) },
                                    icon = { Icon(Icons.Default.Public, contentDescription = "Online", tint = VerdeAzulado) },
                                    label = { Text("Online") }
                                )
                                if (uiState.isLoggedIn) {
                                    NavigationBarItem(
                                        selected = currentRoute == CineScreen.Favoritos.name,
                                        onClick = { navController.navigate(CineScreen.Favoritos.name) },
                                        icon = { Icon(Icons.Default.Favorite, contentDescription = "Favoritos", tint = NetflixRed) },
                                        label = { Text("Favoritos") }
                                    )
                                }
                                NavigationBarItem(
                                    selected = currentRoute == CineScreen.Geolocalizacion.name,
                                    onClick = { navController.navigate(CineScreen.Geolocalizacion.name) },
                                    icon = { Icon(Icons.Default.LocationOn, contentDescription = "Cines cercanos") },
                                    label = { Text("Cines") }
                                )
                                if (uiState.isLoggedIn) {
                                    var showUserMenu by remember { mutableStateOf(false) }
                                    NavigationBarItem(
                                        selected = false,
                                        onClick = { showUserMenu = true },
                                        icon = {
                                            Box {
                                                Icon(Icons.Default.AccountCircle, contentDescription = "Perfil")
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
                                        },
                                        label = { Text("Perfil") }
                                    )
                                }
                            }
                        }
                    }
                ) { innerPadding ->
                    val modifier = Modifier.padding(innerPadding)

                    NavHost(
                        navController = navController,
                        startDestination = CineScreen.Login.name,
                        modifier = modifier
                    ) {
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

                        composable(CineScreen.Details.name) {
                            val favoritosActuales by viewModel.favoritos.collectAsState()
                            val isFavorito = favoritosActuales.any { it.movieId == uiState.selectedMovie.id }
                            val context = LocalContext.current
                            DetallePeliScreen(
                                movie = uiState.selectedMovie,
                                onBackClick = { navController.popBackStack() },
                                onThemeChange = { viewModel.updateTheme(it) },
                                isFavorito = isFavorito,
                                onToggleFavorito = { viewModel.toggleFavorito(uiState.selectedMovie, context) }
                            )
                        }

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

                        composable(CineScreen.Geolocalizacion.name) {
                            GeolocalizacionScreen(
                                onBackClick = { navController.popBackStack() },
                                viewModel = viewModel
                            )
                        }

                        composable(CineScreen.PeliculasOnline.name) {
                            PeliculasOnlineScreen(onBackClick = { navController.popBackStack() })
                        }
                    }
                }
            }
        }
    }
}
