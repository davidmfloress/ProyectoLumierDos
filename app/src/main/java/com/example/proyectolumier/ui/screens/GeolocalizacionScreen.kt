package com.example.proyectolumier.ui.screens

/**
 * @author: David Muñoz Flores
 * @author: Marco Lodeiro Ruiz De La Hermosa
 */

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.filled.OpenInNew
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.proyectolumier.ui.CineViewModel
import com.example.proyectolumier.ui.theme.NetflixRed
import com.example.proyectolumier.ui.theme.RojoCine
import com.example.proyectolumier.ui.theme.VerdeAzulado
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import kotlin.math.*

data class CineCercano(
    val nombre: String,
    val latitud: Double,
    val longitud: Double,
    val direccion: String
)

private val cinesReferencia = listOf(
    CineCercano("Kinépolis Madrid", 40.4891, -3.7226, "Parque Ocio, Pozuelo de Alarcón"),
    CineCercano("Yelmo Cines Ideal", 40.4151, -3.7046, "C/ Doctor Cortezo, 6 - Madrid Centro"),
    CineCercano("Cines Renoir Princesa", 40.4265, -3.7134, "C/ Martín de los Heros, 12 - Argüelles"),
    CineCercano("Cines Palacio de la Prensa", 40.4198, -3.7031, "Plaza del Callao, 4 - Gran Vía"),
    CineCercano("Yelmo Cines Islazul", 40.3768, -3.7387, "C/ Leganés, 2 - Carabanchel"),
    CineCercano("Cinesa Diversia", 40.5264, -3.6313, "Diversia, Alcobendas"),
    CineCercano("Yelmo Cines Rio Shopping", 40.3442, -3.7215, "Rio Shopping, Getafe"),
    CineCercano("Cinesa La Gavia", 40.3880, -3.6274, "C.C. La Gavia - Vallecas"),
    CineCercano("UCI Kineplex Arturo Soria", 40.4601, -3.6509, "Arturo Soria Plaza - Hortaleza"),
    CineCercano("Yelmo Cines Meridiana", 40.4053, -3.6891, "C.C. Meridiana - Puente de Vallecas")
)

fun distanciaKm(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
    val r = 6371.0
    val dLat = Math.toRadians(lat2 - lat1)
    val dLon = Math.toRadians(lon2 - lon1)
    val a = sin(dLat / 2).pow(2) + cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2)) * sin(dLon / 2).pow(2)
    return r * 2 * atan2(sqrt(a), sqrt(1 - a))
}

@SuppressLint("MissingPermission")
@Composable
fun GeolocalizacionScreen(
    onBackClick: () -> Unit,
    viewModel: CineViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }

    var isLoading by remember { mutableStateOf(false) }
    var errorMsg by remember { mutableStateOf<String?>(null) }

    val latitudUsuario = uiState.geoLatitud
    val longitudUsuario = uiState.geoLongitud

    val permisosLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permisos ->
        val concedido = permisos[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                permisos[Manifest.permission.ACCESS_COARSE_LOCATION] == true
        if (concedido) {
            isLoading = true
            val cancelToken = CancellationTokenSource()
            fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, cancelToken.token)
                .addOnSuccessListener { location ->
                    isLoading = false
                    if (location != null) {
                        viewModel.updateUbicacion(location.latitude, location.longitude)
                    } else {
                        errorMsg = "No se pudo obtener la ubicación. Activa el GPS."
                    }
                }
                .addOnFailureListener {
                    isLoading = false
                    errorMsg = "Error al obtener ubicación: ${it.message}"
                }
        } else {
            errorMsg = "Permiso de ubicación denegado"
        }
    }

    val cinesOrdenados = remember(latitudUsuario, longitudUsuario) {
        if (latitudUsuario != null && longitudUsuario != null) {
            cinesReferencia.map { cine ->
                Pair(cine, distanciaKm(latitudUsuario, longitudUsuario, cine.latitud, cine.longitud))
            }.sortedBy { it.second }
        } else emptyList()
    }

    val ubicacionCard: @Composable () -> Unit = {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(
                modifier = Modifier.padding(if (isLandscape) 12.dp else 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(imageVector = Icons.Default.MyLocation, contentDescription = null, tint = RojoCine, modifier = Modifier.size(if (isLandscape) 28.dp else 40.dp))
                Spacer(modifier = Modifier.height(8.dp))

                if (latitudUsuario != null) {
                    Text("Ubicación obtenida", style = MaterialTheme.typography.titleMedium, color = VerdeAzulado, fontWeight = FontWeight.Bold)
                    Text(
                        "Lat: ${"%.4f".format(latitudUsuario)}  |  Lon: ${"%.4f".format(longitudUsuario)}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                } else {
                    Text("Pulsa para obtener tu ubicación", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = {
                        errorMsg = null
                        permisosLauncher.launch(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION))
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = NetflixRed),
                    shape = RoundedCornerShape(10.dp),
                    enabled = !isLoading
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(modifier = Modifier.size(18.dp), strokeWidth = 2.dp, color = MaterialTheme.colorScheme.onPrimary)
                    } else {
                        Icon(Icons.Default.LocationOn, null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(if (latitudUsuario != null) "Actualizar ubicación" else "Obtener ubicación")
                    }
                }

                errorMsg?.let {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Surface(modifier = Modifier.fillMaxWidth(), color = MaterialTheme.colorScheme.surface, shadowElevation = 2.dp) {
            Row(
                modifier = Modifier.padding(if (isLandscape) 4.dp else 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBackClick) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Atrás")
                }
                Text(
                    text = "CINES CERCANOS",
                    style = if (isLandscape) MaterialTheme.typography.titleMedium else MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Light,
                    color = if (isLandscape) RojoCine else MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        if (isLandscape && cinesOrdenados.isNotEmpty()) {
            Row(
                modifier = Modifier.fillMaxSize().padding(12.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    ubicacionCard()
                }
                Column(modifier = Modifier.weight(1.5f)) {
                    Text(
                        "Cines más cercanos a ti",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(cinesOrdenados) { (cine, distancia) ->
                            CineCercanoItem(
                                cine = cine,
                                distanciaKm = distancia,
                                isCompact = true,
                                onClick = {
                                    val uri = Uri.parse("geo:${cine.latitud},${cine.longitud}?q=${Uri.encode(cine.nombre)}")
                                    context.startActivity(Intent(Intent.ACTION_VIEW, uri))
                                }
                            )
                        }
                    }
                }
            }
        } else {
            Column(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ubicacionCard()
                Spacer(modifier = Modifier.height(16.dp))

                if (cinesOrdenados.isNotEmpty()) {
                    Text(
                        "Cines más cercanos a ti",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(cinesOrdenados) { (cine, distancia) ->
                            CineCercanoItem(
                                cine = cine,
                                distanciaKm = distancia,
                                isCompact = false,
                                onClick = {
                                    val uri = Uri.parse("geo:${cine.latitud},${cine.longitud}?q=${Uri.encode(cine.nombre)}")
                                    context.startActivity(Intent(Intent.ACTION_VIEW, uri))
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CineCercanoItem(
    cine: CineCercano,
    distanciaKm: Double,
    isCompact: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier.padding(if (isCompact) 8.dp else 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.size(if (isCompact) 36.dp else 48.dp).background(RojoCine.copy(alpha = 0.1f), shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.LocationOn, contentDescription = null, tint = RojoCine, modifier = Modifier.size(if (isCompact) 20.dp else 28.dp))
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = cine.nombre, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, maxLines = 1)
                if (!isCompact) Text(text = cine.direccion, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(text = "${"%.1f".format(distanciaKm)} km", style = MaterialTheme.typography.labelLarge, color = VerdeAzulado, fontWeight = FontWeight.Bold)
                Icon(Icons.Default.OpenInNew, contentDescription = null, tint = MaterialTheme.colorScheme.outline, modifier = Modifier.size(14.dp))
            }
        }
    }
}