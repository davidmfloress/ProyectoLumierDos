package com.example.proyectolumier

/**
@author: David Muñoz Flores
@author: Marco Lodeiro Ruiz De La Hermosa
 */

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.proyectolumier.navigation.CineScreen
import com.example.proyectolumier.ui.CineViewModel
import com.example.proyectolumier.ui.screens.CategoriaScreen
import com.example.proyectolumier.ui.screens.DetallePeliScreen
import com.example.proyectolumier.ui.screens.ListaPelisScreen
import com.example.proyectolumier.ui.theme.BebasNeue
import com.example.proyectolumier.ui.theme.NetflixRed
import com.example.proyectolumier.ui.theme.ProyectoLumierTheme
import androidx.compose.ui.platform.LocalConfiguration
import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.SettingsSuggest
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.proyectolumier.ui.MovieOrder
import com.example.proyectolumier.ui.theme.VerdeAzulado
import androidx.compose.ui.res.stringResource
import com.example.proyectolumier.ui.theme.RojoCine


class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class, ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: CineViewModel = viewModel()
            val uiState by viewModel.uiState.collectAsState()
            val systemInDarkTheme = androidx.compose.foundation.isSystemInDarkTheme()
            val darkModeActual = uiState.isDarkMode ?: systemInDarkTheme


            val configuration = LocalConfiguration.current
            val windowSize = calculateWindowSizeClass(this)
            val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE


            ProyectoLumierTheme(darkTheme = darkModeActual) {
                val navController = rememberNavController()






                val useSplitScreen = windowSize.widthSizeClass == WindowWidthSizeClass.Expanded

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {

                        val currentRoute =  navController.currentBackStackEntryAsState().value?.destination?.route

                        val shouldShowTopBar = !(isLandscape && (
                                currentRoute == CineScreen.Details.name ||
                                        currentRoute == CineScreen.Categories.name ||
                                        currentRoute == CineScreen.Titles.name
                                ))

                        if (shouldShowTopBar) {
                            CenterAlignedTopAppBar(

                                navigationIcon = {
                                    val logoRes =
                                        if (darkModeActual) R.drawable.logolumiernigga else R.drawable.logolumiernormal

                                    IconButton(
                                        onClick = {
                                            navController.navigate(CineScreen.Categories.name) {
                                                popUpTo(CineScreen.Categories.name) {
                                                    inclusive = true
                                                }
                                            }
                                        },

                                        modifier = Modifier.size(if (isLandscape && !useSplitScreen) 80.dp else 100.dp)
                                    ) {
                                        Image(
                                            painter = painterResource(id = logoRes),
                                            contentDescription = "Logo Lumier",
                                            modifier = Modifier
                                                .size(130.dp)
                                                .padding(start = 12.dp),
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

                                    val context = LocalContext.current


                                }
                            )
                        }
                    }
                ) { innerPadding ->
                    val modifier = Modifier.padding(innerPadding)

                    NavHost(
                        navController = navController,
                        startDestination = CineScreen.Categories.name,
                        modifier = modifier
                    ) {
                        composable(CineScreen.Categories.name) {

                            CategoriaScreen(
                                categories = uiState.categoryList,
                                onCategoryClick = {
                                    viewModel.updateCurrentGenre(it)
                                    navController.navigate(CineScreen.Titles.name)
                                },
                                isDarkMode = uiState.isDarkMode,
                                onThemeChange = { nuevoTema ->
                                    viewModel.updateTheme(nuevoTema)
                                }
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
                                onSearchChange = { textoABuscar, contexto -> viewModel.onSearchTextChange(textoABuscar, contexto) },
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
                            DetallePeliScreen(
                                movie = uiState.selectedMovie,
                                onBackClick = { navController.popBackStack() },
                                onThemeChange = { viewModel.updateTheme(it) }
                            )
                        }
                    }
                }
            }
        }
    }
}

