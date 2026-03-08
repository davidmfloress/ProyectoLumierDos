package com.example.proyectolumier.ui.screens

/**
@author: David Muñoz Flores
@author: Marco Lodeiro Ruiz De La Hermosa
 */

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LifecycleOwner
import com.example.proyectolumier.data.model.Movie
import com.example.proyectolumier.ui.theme.RojoCine
import com.example.proyectolumier.ui.theme.VerdeAzulado
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import android.content.Intent
import android.content.res.Configuration
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.SettingsSuggest
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import com.example.proyectolumier.ui.theme.NetflixRed

import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder

@Composable
fun DetallePeliScreen(
    movie: Movie,
    onBackClick: () -> Unit,
    onThemeChange: (Boolean?) -> Unit,
    isFavorito: Boolean = false,
    onToggleFavorito: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    var isDetailVisibleByClick by remember { mutableStateOf(false) }
    var showMenu by remember { mutableStateOf(false) }
    val isContentVisible = if (isLandscape) true else isDetailVisibleByClick

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
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(if (isLandscape) 4.dp else 8.dp)
            ) {
                IconButton(onClick = onBackClick) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Atrás")
                }
                Text(
                    text = if (isLandscape) stringResource(movie.title).uppercase() else "DETALLES",
                    style = if (isLandscape) MaterialTheme.typography.titleMedium else MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Light,
                    color = if (isLandscape) RojoCine else MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = onToggleFavorito) {
                    Icon(
                        imageVector = if (isFavorito) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = if (isFavorito) "Quitar favorito" else "Añadir favorito",
                        tint = if (isFavorito) NetflixRed else MaterialTheme.colorScheme.onSurface
                    )
                }
                Box {
                    IconButton(onClick = { showMenu = true }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "Opciones")
                    }
                    DropdownMenu(expanded = showMenu, onDismissRequest = { showMenu = false }) {
                        Text("Apariencia", modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp), style = MaterialTheme.typography.labelSmall, color = NetflixRed)
                        DropdownMenuItem(text = { Text("Modo Claro") }, leadingIcon = { Icon(Icons.Default.LightMode, null) }, onClick = { onThemeChange(false); showMenu = false })
                        DropdownMenuItem(text = { Text("Modo Oscuro") }, leadingIcon = { Icon(Icons.Default.DarkMode, null) }, onClick = { onThemeChange(true); showMenu = false })
                        DropdownMenuItem(text = { Text("Sistema") }, leadingIcon = { Icon(Icons.Default.SettingsSuggest, null) }, onClick = { onThemeChange(null); showMenu = false })
                    }
                }
            }
        }

        if (isLandscape) {
            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                val videoHeight = maxHeight * 0.90f
                val videoWidth  = videoHeight * 16f / 9f

                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .width(videoWidth)
                            .height(videoHeight)
                            .border(2.dp, RojoCine)
                    ) {
                        YoutubePlayer(movie.trailerUrlRes, Modifier.fillMaxSize())
                    }
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(text = stringResource(movie.title), style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                        HorizontalDivider(Modifier.padding(vertical = 8.dp).width(40.dp), color = RojoCine)
                        Text(text = stringResource(movie.description), style = MaterialTheme.typography.bodyMedium)
                        Spacer(modifier = Modifier.height(16.dp))
                        Text("DÓNDE VER:", color = VerdeAzulado, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                        PlatformList(movie.platformsIdRes)
                    }
                }
            }
        } else {
            Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
                Box(modifier = Modifier.fillMaxWidth().padding(16.dp).aspectRatio(16f / 9f).border(2.dp, RojoCine)) {
                    YoutubePlayer(movie.trailerUrlRes, Modifier.fillMaxSize())
                }
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = stringResource(id = movie.title), modifier = Modifier.weight(1f), style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.Bold)
                    IconButton(onClick = { isDetailVisibleByClick = !isDetailVisibleByClick }) {
                        Icon(imageVector = if (isDetailVisibleByClick) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown, contentDescription = "Ver más", tint = RojoCine)
                    }
                }
                HorizontalDivider(modifier = Modifier.padding(start = 20.dp, top = 8.dp, bottom = 8.dp).width(50.dp), color = RojoCine)
                AnimatedVisibility(visible = isContentVisible, enter = expandVertically() + fadeIn(), exit = shrinkVertically() + fadeOut()) {
                    Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                        Text(text = stringResource(id = movie.description), style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f))
                        Spacer(modifier = Modifier.height(24.dp))
                        Text(text = "DÓNDE VER:", style = MaterialTheme.typography.titleMedium, color = VerdeAzulado, fontWeight = FontWeight.Bold)
                        PlatformList(platformsIdRes = movie.platformsIdRes)
                        Spacer(modifier = Modifier.height(32.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun YoutubePlayer(
    @StringRes youtubeVideoIdRes: Int,
    modifier: Modifier = Modifier
) {
    val videoId = stringResource(id = youtubeVideoIdRes)
    AndroidView(
        modifier = modifier,
        factory = { context ->
            YouTubePlayerView(context).apply {
                (context as? LifecycleOwner)?.lifecycle?.addObserver(this)
                addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                    override fun onReady(youTubePlayer: YouTubePlayer) {
                        youTubePlayer.cueVideo(videoId, 0f)
                    }
                })
            }
        }
    )
}

@Composable
fun PlatformList(platformsIdRes: Int) {
    val context = LocalContext.current
    val idsString = stringResource(id = platformsIdRes)
    val platformData = mapOf(
        "1" to Pair("Netflix", "https://www.netflix.com"),
        "2" to Pair("HBO Max", "https://www.max.com"),
        "3" to Pair("Amazon Prime", "https://www.primevideo.com"),
        "4" to Pair("Disney+", "https://www.disneyplus.com"),
        "5" to Pair("Apple TV", "https://tv.apple.com"),
        "6" to Pair("Paramount+", "https://www.paramountplus.com"),
        "7" to Pair("Cines / Otros", "https://www.google.com/search?q=cartelera+cine")
    )
    val selectedIds = idsString.split(",").map { it.trim() }
    Column(modifier = Modifier.padding(top = 8.dp)) {
        selectedIds.forEach { id ->
            val data = platformData[id]
            if (data != null) {
                Button(
                    onClick = { context.startActivity(Intent(Intent.ACTION_VIEW, data.second.toUri())) },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surfaceVariant, contentColor = MaterialTheme.colorScheme.onSurfaceVariant),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(text = "Ver en ${data.first}", fontWeight = FontWeight.Medium)
                }
            }
        }
    }
}