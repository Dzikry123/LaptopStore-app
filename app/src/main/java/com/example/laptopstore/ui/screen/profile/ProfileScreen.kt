package com.example.laptopstore.ui.screen.profile
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.laptopstore.R
import com.example.laptopstore.ui.theme.LaptopStoreTheme


@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 50.dp)
        ,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        // Foto pengguna
        Image(
            painter = painterResource(id = R.drawable.ai_generated), // Ganti dengan foto pengguna
            contentDescription = "Foto Pengguna",
            modifier = Modifier
                .padding(vertical = 32.dp)
                .size(150.dp)
                .clip(RoundedCornerShape(24.dp))
                .align(Alignment.CenterHorizontally)
        )

        // Nama
        Text(
            text = "Dzikry Habibie Muchtar", // Ganti dengan nama pengguna
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            fontWeight = FontWeight.ExtraBold
        )

        // Email
        Text(
            text = "dzikryhabibie85@gmail.com", // Ganti dengan email pengguna
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            fontWeight = FontWeight.Medium
        )

        // Deskripsi
        Text(
            text = "Halo perkenalkan nama saya Dzikry saya merupakan programmer pemula di Android Developmemnt", // Ganti dengan deskripsi pengguna
            modifier = Modifier
                .padding(horizontal = 50.dp, vertical = 16.dp)
                .align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center
        )
    }
}


@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    LaptopStoreTheme() {
        ProfileScreen()
    }
}
