package com.example.proyectolumier.ui.screens

/**
 * Pantalla de geolocalización que obtiene la ubicación del usuario
 * y muestra cines cercanos con su distancia aproximada.
 *
 * @author: David Muñoz Flores
 * @author: Marco Lodeiro Ruiz De La Hermosa
 */

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
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

// Lista de cines de referencia (España)
private val cinesReferencia = listOf(
    CineCercano("Cines Yelmo", 40.4168, -3.7038, "Madrid Centro"),
    CineCercano("Cinesa La Maquinista", 41.4454, 2.1985, "Barcelona Norte"),
    CineCercano("Odeón Multicines", 37.3891, -5.9845, "Sevilla"),
    CineCercano("Cinesa Diagonal Mar", 41.4104, 2.2166, "Barcelona Este"),
    CineCercano("Multicines Norte", 43.3623, -8.4115, "A Coruña"),
    CineCercano("Kinépolis Madrid", 40.4890, -3.7226, "Madrid Norte"),
    CineCercano("Cines Renoir", 40.4300, -3.7000, "Madrid Retiro"),
    CineCercano("Cines Gran Casa", 41.6488, -0.8891, "Zaragoza"),
    CineCercano("Cinesa Puerto Venecia", 41.6290, -0.9012, "Zaragoza Sur"),
    CineCercano("Yelmo Cines Nervión", 37.3800, -5.9600, "Sevilla Este")
)

fun distanciaKm(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
    val r = 6371.0
    val dLat = Math.toRadians(lat2 - lat1)
    val dLon = Math.toRadians(lon2 - lon1)
    val a = sin(dLat / 2).pow(2) +
            cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2)) * sin(dLon / 2).pow(2)
    val c = 2 * atan2(sqrt(a), sqrt(1 - a))
    return r * c
}

@SuppressLint("MissingPermission")
@Composable
fun GeolocalizacionScreen(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }

    var latitudUsuario by remember { mutableStateOf<Double?>(null) }
    var longitudUsuario by remember { mutableStateOf<Double?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMsg by remember { mutableStateOf<String?>(null) }
    var permisoConcedido by remember { mutableStateOf(false) }

    val permisosLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permisos ->
        permisoConcedido = permisos[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                permisos[Manifest.permission.ACCESS_COARSE_LOCATION] == true
        if (permisoConcedido) {
            isLoading = true
            val cancelToken = CancellationTokenSource()
            fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, cancelToken.token)
                .addOnSuccessListener { location ->
                    isLoading = false
                    if (location != null) {
                        latitudUsuario = location.latitude
                        longitudUsuario = location.longitude
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
                Pair(cine, distanciaKm(latitudUsuario!!, longitudUsuario!!, cine.latitud, cine.longitud))
            }.sortedBy { it.second }
        } else emptyList()
    }

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
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Atrás")
                }
                Text(
                    text = "CINES CERCANOS",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Light,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Tarjeta de ubicación
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.MyLocation,
                        contentDescription = null,
                        tint = RojoCine,
                        modifier = Modifier.size(40.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    if (latitudUsuario != null) {
                        Text(
                            text = "📍 Ubicación obtenida",
                            style = MaterialTheme.typography.titleMedium,
                            color = VerdeAzulado,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Lat: ${"%.4f".format(latitudUsuario)}  |  Lon: ${"%.4f".format(longitudUsuario)}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    } else {
                        Text(
                            text = "Pulsa para obtener tu ubicación",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = {
                            errorMsg = null
                            permisosLauncher.launch(
                                arrayOf(
                                    Manifest.permission.ACCESS_FINE_LOCATION,
                                    Manifest.permission.ACCESS_COARSE_LOCATION
                                )
                            )
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = NetflixRed),
                        shape = RoundedCornerShape(10.dp),
                        enabled = !isLoading
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(18.dp),
                                strokeWidth = 2.dp,
                                color = MaterialTheme.colorScheme.onPrimary
                            )
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

            Spacer(modifier = Modifier.height(16.dp))

            if (cinesOrdenados.isNotEmpty()) {
                Text(
                    text = "Cines más cercanos a ti",
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
                            onClick = {
                                // Abre Google Maps con las coordenadas del cine
                                val uri = Uri.parse("geo:${cine.latitud},${cine.longitud}?q=${Uri.encode(cine.nombre)}")
                                val intent = Intent(Intent.ACTION_VIEW, uri)
                                context.startActivity(intent)
                            }
                        )
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
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Ícono distancia
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(RojoCine.copy(alpha = 0.1f), shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = null,
                    tint = RojoCine,
                    modifier = Modifier.size(28.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = cine.nombre,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = cine.direccion,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }

            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "${"%.1f".format(distanciaKm)} km",
                    style = MaterialTheme.typography.labelLarge,
                    color = VerdeAzulado,
                    fontWeight = FontWeight.Bold
                )
                Icon(
                    imageVector = Icons.Default.OpenInNew,
                    contentDescription = "Abrir mapa",
                    tint = MaterialTheme.colorScheme.outline,
                    modifier = Modifier.size(14.dp)
                )
            }
        }
    }
}
