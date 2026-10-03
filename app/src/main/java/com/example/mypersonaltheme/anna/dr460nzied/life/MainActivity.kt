package com.example.mypersonaltheme.anna.dr460nized.life

import android.app.WallpaperManager
import android.content.Intent
import android.net.Uri
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.RadialGradient
import android.graphics.Shader
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SportelloGarudaTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    GarudaThemeHubScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

// =========================================================
// COLORI DAL CSS (FONDAMENTA GARUDA)
// =========================================================
val Bg0 = Color(0xFF050010)
val Bg1 = Color(0xFF0B0018)
val Bg2 = Color(0xFF1A0030)
val Txt = Color(0xFFFDFDFF)

val Cyan = Color(0xFF00F7FF)
val Pink = Color(0xFFFF1FB6)
val Pink2 = Color(0xFFFF4FE0)
val Yellow = Color(0xFFFFE600)

@Composable
fun SportelloGarudaTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = darkColorScheme(
            background = Bg0,
            surface = Bg1,
            primary = Cyan,
            secondary = Pink,
            tertiary = Yellow
        ),
        content = content
    )
}

// =========================================================
// SFONDO GARUDA (4 RADIAL GRADIENTS)
// =========================================================
@Composable
fun GarudaPageBackground(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Bg0)
            .background(
                Brush.linearGradient(
                    colors = listOf(Bg1, Bg2, Color(0xFF070017))
                )
            )
            .background(
                Brush.radialGradient(
                    colors = listOf(Color(0x5900FFC8), Color.Transparent),
                    radius = 1000f
                )
            )
            .background(
                Brush.radialGradient(
                    colors = listOf(Color(0x59FF00B4), Color.Transparent),
                    radius = 1000f
                )
            )
    ) {
        content()
    }
}

// =========================================================
// DASHBOARD PRINCIPALE
// =========================================================
@Composable
fun GarudaThemeHubScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var isApplying by remember { mutableStateOf(false) }

    fun openUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context.startActivity(intent)
    }

    GarudaPageBackground(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            // LOGO CIRCLE
            Box(
                modifier = Modifier
                    .size(110.dp)
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(Color(0xE600FFC8), Color(0x4000B496))
                        ),
                        shape = CircleShape
                    )
                    .border(2.dp, Cyan, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "SPO",
                    color = Bg0,
                    fontWeight = FontWeight.Black,
                    fontSize = 28.sp
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = "DR460NIZED HUB",
                color = Cyan,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif
            )

            Text(
                text = "Control Center & Theme 🐉",
                color = Pink,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(28.dp))

            // SCHEDA TEMA SISTEMA
            Card(
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(containerColor = Bg1.copy(alpha = 0.85f)),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 1.5.dp,
                        brush = Brush.horizontalGradient(listOf(Cyan, Pink, Yellow)),
                        shape = RoundedCornerShape(22.dp)
                    )
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "GESTIONE TEMA GARUDA",
                        color = Yellow,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        letterSpacing = 1.sp
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // BOTTONE APPLICA TEMA COMPLETO
                    Button(
                        onClick = {
                            isApplying = true
                            scope.launch {
                                val success = applyGarudaWallpaper(context)
                                isApplying = false
                                if (success) {
                                    Toast.makeText(
                                        context,
                                        "Tema e Sfondo Garuda applicati con successo! 🐉✨",
                                        Toast.LENGTH_LONG
                                    ).show()
                                } else {
                                    Toast.makeText(
                                        context,
                                        "Errore durante l'applicazione del tema",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        },
                        enabled = !isApplying,
                        colors = ButtonDefaults.buttonColors(containerColor = Pink),
                        shape = CircleShape,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                    ) {
                        if (isApplying) {
                            CircularProgressIndicator(color = Txt, modifier = Modifier.size(22.dp))
                        } else {
                            Text(
                                text = "🎨 APPLICA TEMA GARUDA",
                                color = Txt,
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 15.sp
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // SEZIONE LINK RAPIDI
            Text(
                text = "SITI & SERVER",
                color = Cyan,
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                letterSpacing = 1.sp,
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // 1. SPORTELLO PRATICHE ONLINE
            Button(
                onClick = { openUrl("https://www.sportellopratiche.online") },
                colors = ButtonDefaults.buttonColors(containerColor = Bg1),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .border(1.dp, Cyan, RoundedCornerShape(16.dp))
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "🌐 ", fontSize = 18.sp)
                    Text(
                        text = "Sportello Pratiche Online",
                        color = Txt,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 2. ANNA DR460NIZED LIFE
            Button(
                onClick = { openUrl("https://anna.dr460nized.life") },
                colors = ButtonDefaults.buttonColors(containerColor = Bg1),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .border(1.dp, Pink, RoundedCornerShape(16.dp))
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "🐉 ", fontSize = 18.sp)
                    Text(
                        text = "Anna Dr460nized Life",
                        color = Txt,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 3. AKKOMA SERVER
            Button(
                onClick = { openUrl("https://anna.dr460nized.life") },
                colors = ButtonDefaults.buttonColors(containerColor = Bg1),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .border(1.dp, Yellow, RoundedCornerShape(16.dp))
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "💬 ", fontSize = 18.sp)
                    Text(
                        text = "Akkoma Server",
                        color = Txt,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            // STATUS TERMINALE
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xD9050519)),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Cyan.copy(alpha = 0.4f), RoundedCornerShape(14.dp))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "# spo:// › theme_status",
                        color = Cyan,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "System Theme: Garuda Cyberpunk\nMaterial You Engine Active",
                        color = Txt.copy(alpha = 0.8f),
                        fontFamily = FontFamily.Monospace,
                        fontSize = 11.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

// =========================================================
// FUNZIONE PER CREARE LO SFONDO DI SISTEMA + PALETTE NEON
// =========================================================
suspend fun applyGarudaWallpaper(context: android.content.Context): Boolean = withContext(Dispatchers.IO) {
    try {
        val width = 1080
        val height = 2400
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)

        // Sfondo base scuro Garuda
        val bgPaint = Paint().apply {
            shader = LinearGradient(
                0f, 0f, width.toFloat(), height.toFloat(),
                intArrayOf(0xFF0B0018.toInt(), 0xFF1A0030.toInt(), 0xFF070017.toInt()),
                null, Shader.TileMode.CLAMP
            )
        }
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), bgPaint)

        // Luce 1: Ciano (#00F7FF)
        val cyanPaint = Paint().apply {
            shader = RadialGradient(
                width * 0.2f, height * 0.05f, 900f,
                intArrayOf(0x6600FFC8.toInt(), 0x00000000),
                null, Shader.TileMode.CLAMP
            )
        }
        canvas.drawCircle(width * 0.2f, height * 0.05f, 900f, cyanPaint)

        // Luce 2: Fucsia (#FF1FB6)
        val pinkPaint = Paint().apply {
            shader = RadialGradient(
                width * 0.8f, height * 0.15f, 900f,
                intArrayOf(0x66FF00B4.toInt(), 0x00000000),
                null, Shader.TileMode.CLAMP
            )
        }
        canvas.drawCircle(width * 0.8f, height * 0.15f, 900f, pinkPaint)

        // Applicazione dello sfondo tramite WallpaperManager (attiva il motore di colori Android)
        val wallpaperManager = WallpaperManager.getInstance(context)
        wallpaperManager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_SYSTEM or WallpaperManager.FLAG_LOCK)
        true
    } catch (e: Exception) {
        e.printStackTrace()
        false
    }
}

// =========================================================
// PREVIEW
// =========================================================
@Preview(showBackground = true, widthDp = 360, heightDp = 740)
@Composable
fun GarudaHubPreview() {
    SportelloGarudaTheme {
        GarudaThemeHubScreen()
    }
}
