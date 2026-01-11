package com.example.tapplecompanion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.tooling.preview.Preview
import com.example.tapplecompanion.ui.theme.TappleCompanionTheme
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.sp


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TappleCompanionTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(modifier: Modifier = Modifier) {
    val tappleAiheet = listOf(
        "Eläin",
        "Hedelmä",
        "Vihannes",
        "Maa",
        "Kaupunki",
        "Urheilulaji",
        "Harrastus",
        "Ammatti",
        "Asia keittiössä",
        "Asia kylpyhuoneessa",
        "Asia makuuhuoneessa",
        "Asia koulussa",
        "Asia toimistossa",
        "Huonekalu",
        "Kodinkone",
        "Ruokalaji",
        "Jälkiruoka",
        "Juoma",
        "Asia, jota syödään aamupalalla",
        "Asia, jota löytyy jääkaapista",
        "Asia rannalla",
        "Asia metsässä",
        "Asia kaupungissa",
        "Asia maaseudulla",
        "Asia lomalla",
        "Asia lentokentällä",
        "Ajoneuvo",
        "Auton merkki",
        "Asia, joka liikkuu nopeasti",
        "Asia, joka on hidas",
        "Jotain kylmää",
        "Jotain kuumaa",
        "Jotain pyöreää",
        "Jotain pehmeää",
        "Jotain kovaa",
        "Jotain äänekästä",
        "Jotain hiljaista",
        "Jotain kallista",
        "Jotain halpaa",
        "Jotain vanhaa",
        "Jotain uutta",
        "Jotain rikkinäistä",
        "Jotain hyödyllistä",
        "Jotain turhaa",
        "Jotain, mikä tekee ihmiset iloisiksi",
        "Jotain, mikä ärsyttää",
        "Jotain, mikä pelottaa",
        "Jotain, mikä naurattaa",
        "Jotain, mikä haisee pahalta",
        "Jotain, mikä tuoksuu hyvältä",
        "Jotain, mitä ei saisi tehdä",
        "Jotain, mitä tehdään viikonloppuna",
        "Jotain, mitä tehdään aamulla",
        "Jotain, mitä tehdään illalla",
        "Jotain, mitä tehdään lomalla",
        "Jotain ulkona",
        "Jotain sisällä",
        "Jotain taskuun mahtuvaa",
        "Jotain raskasta",
        "Jotain kevyttä",
        "Jotain ilmastoystävällistä",
        "Jotain ympäristölle haitallista",
        "Jotain kierrätettävää",
        "Jotain muovista",
        "Jotain metallista",
        "Jotain puusta",
        "Jotain, joka kuluttaa sähköä",
        "Jotain, joka säästää energiaa",
        "Jotain, joka tarvitsee paristoja",
        "Jotain, joka toimii ilman sähköä",
        "Jotain, mikä löytyy taskusta",
        "Jotain, mikä löytyy laukusta",
        "Jotain, mikä löytyy laatikosta",
        "Jotain, mikä unohtuu helposti",
        "Jotain, mikä katoaa helposti",
        "Jotain, mitä käytetään päivittäin",
        "Jotain, mitä käytetään harvoin",
        "Jotain, mikä maksaa alle euron",
        "Jotain, mikä maksaa paljon",
        "Jotain, mikä on kiellettyä lapsilta",
        "Jotain, mikä on vaarallista",
        "Jotain, mikä on turvallista",
        "Jotain, mikä liittyy teknologiaan",
        "Jotain, mikä liittyy luontoon",
        "Jotain, mikä liittyy juhliin",
        "Jotain, mikä liittyy urheiluun",
        "Jotain, mikä liittyy musiikkiin",
        "Jotain, mikä liittyy elokuviin",
        "Jotain, mikä liittyy matkustamiseen",
        "Jotain, mitä löytyy Suomesta"
    )

    var topic1 by remember { mutableStateOf(tappleAiheet.random()) }
    var topic2 by remember { mutableStateOf(tappleAiheet.random()) }

    while (topic1 == topic2) {
        topic2 = tappleAiheet.random()
    }

    Surface(color = Color(0xFF000000)) {
        Box(Modifier.fillMaxSize()) {
            Text(
                text = "$topic1\n\n$topic2",
                modifier = modifier.align(Alignment.Center),
                color = Color.White,
                fontSize = 20.sp
            )
            Button(onClick = {
                topic1 = tappleAiheet.random()
                topic2 = tappleAiheet.random()
                while (topic1 == topic2) {
                    topic2 = tappleAiheet.random()
                } }, content = { Text(text = "Seuraava", fontSize = 24.sp) }, modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 64.dp).size(width = 150.dp, height = 75.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TappleCompanionTheme {
        Greeting()
    }
}
