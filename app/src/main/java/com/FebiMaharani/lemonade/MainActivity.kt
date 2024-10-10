package com.FebiMaharani.lemonade

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.FebiMaharani.lemonade.ui.theme.LemonadeTheme

// kelas utama yang akan menjalankan aplikasi.
class MainActivity : ComponentActivity() {

    // metode onCreate dipanggil saat aktivitas dimulai.
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge() // untuk mengaktifkan meode edge-tp edge untuk tmpiilan lebih penuh.
        super.onCreate(savedInstanceState) // memanggil metode dari kelas induk.
        setContent {
            LemonadeTheme { // menggunakan tema lemonade
                LemonadeApp() // memanggil fungsi lemonade untuk menampilkan UI
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable // Fungsi comppasable untuk membangun UI
fun LemonadeApp() {

    var currentStep by remember { mutableStateOf(1) }
    var squeezeCount by remember { mutableStateOf(0) }

        // struktur dasar untuk aplikasi yang memiliki bar dan body.
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Lemonade", // judul di bar
                        fontWeight = FontWeight.Bold // mengatur font menjadi tebal
                    )
                },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer // warna app bar
                )
            )
        }
    ) { innerPadding -> // untuk memastikan konten tidak tertutup oleh bar
        Surface(
            modifier = Modifier
                .fillMaxSize() // ukuran layar penuh
                .padding(innerPadding) // menambah padding dari bar
                .background(MaterialTheme.colorScheme.tertiaryContainer), // mengatur background
            color = MaterialTheme.colorScheme.background // mengatur warna ppermukaan
        ) {
            // menentukan tampilan berdasarkan langkah sekarang
            when (currentStep) {
                1 -> {
                    LemonTextAndImage(
                        textLabelResourceId = R.string.lemon_select, // pesan memilih lemon
                        drawableResourceId = R.drawable.lemon_tree, //gambar pohon lemon
                        contentDescriptionResourceId = R.string.lemon_tree_content_description, // deskripsi gambar
                        onImageClick = {
                            currentStep = 2 // pindah ke langkah 2 saat gambar di klik
                            squeezeCount = (2..4).random() // menetapkan jumlah squeeze secara accak
                        }
                    )
                }
                2 -> {
                    LemonTextAndImage(
                        textLabelResourceId = R.string.lemon_squeeze, // pesan squeeze lemon
                        drawableResourceId = R.drawable.lemon_squeeze, // gambar squeeze lemon
                        contentDescriptionResourceId = R.string.lemon_content_description, // deskripsi gambar
                        onImageClick = {
                            squeezeCount-- // mengurangi jumlah squeeze saat gambar di klik
                            // jika jumlah squeeze menjadi 0, pindah ke langkah 3
                            if (squeezeCount == 0) {
                                currentStep = 3
                            }
                        }
                    )
                }

                3 -> {
                    LemonTextAndImage(
                        textLabelResourceId = R.string.lemon_drink, // pesan minuman lemon
                        drawableResourceId = R.drawable.lemon_drink, // gambar minuman lemon
                        contentDescriptionResourceId = R.string.lemonade_content_description, // deskripsi gambar
                        onImageClick = {
                            currentStep = 4 // pindah ke langkah 4 saat gambar di klik
                        }
                    )
                }
                4 -> {
                    LemonTextAndImage(
                        textLabelResourceId = R.string.lemon_empty_glass, // pesan glass kosong
                        drawableResourceId = R.drawable.lemon_restart, // gambar glass kosong
                        contentDescriptionResourceId = R.string.empty_glass_content_description, // deskripsi gambar
                        onImageClick = {
                            currentStep = 1 // pindah ke langkah 1
                        }
                    )
                }
            }
        }
    }
}

// fungsi untuk menampilkan teks dan gambar
@Composable
fun LemonTextAndImage(
    textLabelResourceId: Int, // sumber daya teks
    drawableResourceId: Int, // sumber daya gambar
    contentDescriptionResourceId: Int, // sumber daya deskripsi konten
    onImageClick: () -> Unit, // fungsi yang dipanggil saat gambar di klik
    modifier: Modifier = Modifier // modifikator opsional
) {
    Box(
        modifier = modifier // menambahkan modifikator
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally, // mengatur agar konten berada di tengah
            verticalArrangement = Arrangement.Center, // mengatur agar konten berada di tengah
            modifier = Modifier.fillMaxSize() // mengisi ruang penuh
        ) {
            Button( // tombol untuk menampilkan gambar dan teks
                onClick = onImageClick, // fungsi yang dipanggil saat gambar di klik
                shape = RoundedCornerShape(dimensionResource(R.dimen.button_corner_radius)), // mengatur sudut tombol
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer) // mengatur warna tombol
            ) {
                Image( // menampilkan gambar
                    painter = painterResource(drawableResourceId), // sumber gambar
                    contentDescription = stringResource(contentDescriptionResourceId), // deskripsi konten
                    modifier = Modifier
                        .width(dimensionResource(R.dimen.button_image_width)) // mengatur lebar gambar
                        .height(dimensionResource(R.dimen.button_image_height)) // mengatur tinggi gambar
                        .padding(dimensionResource(R.dimen.button_interior_padding)) // mengatur padding gambar
                )
            }
            // menambahkan jarak antara gambar dan teks
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_vertical))) // menambahkan jarak
            Text( // menampilkan teks
                text = stringResource(textLabelResourceId), // sumber teks
                style = MaterialTheme.typography.bodyLarge // mengatur gaya teks
            )
        }
    }
}

// fungsi untuk menampilkan tampilan
@Preview
@Composable
fun LemonPreview() {
    LemonadeTheme() {
        LemonadeApp()
    }
}
