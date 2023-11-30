package kr.ac.kumoh.ce.s20210734.s23w1301songlist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage


import kr.ac.kumoh.ce.s20210734.s23w1301songlist.ui.theme.S23W1301SongListTheme

class MainActivity : ComponentActivity() {
    private val viewModel: SongViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
                MainScreen(viewModel)
        }
    }
}

@Composable
fun MainScreen(viewModel: SongViewModel) {
    val songList by viewModel.songList.observeAsState(emptyList())
    S23W1301SongListTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            SongList(songList)
        }
    }
}

@Composable
fun SongList(list: List<Song>) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(horizontal = 8.dp)
    ){
        itemsIndexed(list) { index, song ->
            SongItem(index, song)
        }
    }
}

@Composable
fun SongItem(index: Int, song: Song) {
    var expanded by remember { mutableStateOf(false) }
    Card(
        modifier =Modifier.clickable { expanded = !expanded },
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
                //.background(Color(255, 210, 210))//날씨가 추우니 붉은 색을 쓰자
                .padding(8.dp)
        ) {
            AsyncImage(
                //model = "https://picsum.photos/300/300?random${song.id}",//1부터 시작
                model = "https://picsum.photos/300/300?random$index",//index 0부터 시작
                contentDescription = "노래 앨범 사진",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp)
                    //.clip(CircleShape)
                    .clip(RoundedCornerShape(10)),
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                //.background(Color(0, 210, 210)),
                //.padding(16.dp),
                //verticalArrangement = Arrangement.Center//왜 센터로 가지 않는 거지? A:.height(IntrinsicSize.Min)//이 부분이 있어야
                verticalArrangement = Arrangement.SpaceAround

            ) {
                TextTitle(song.title)
                TextSinger(song.singer)
            }
        }
        AnimatedVisibility(visible = expanded) {
            //사건의 지평선을 누르니까 앱이 죽네? null인 경우 체크
            song.lyrics?.let{Text(it)}
        }
    }
}

@Composable
fun TextTitle(title: String) {
    Text(title, fontSize = 30.sp)
}

@Composable
fun TextSinger(singer: String) {
    Text(singer, fontSize = 20.sp)
}

