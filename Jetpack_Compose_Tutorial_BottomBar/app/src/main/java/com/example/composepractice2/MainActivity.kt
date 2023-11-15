package com.example.composepractice2

import android.os.Bundle
import android.provider.CalendarContract.Colors
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.composepractice2.ui.theme.ComposePractice2Theme
import com.example.composepractice2.viewModel.MyViewModel


class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // calculateWindowSizeClass : 휴대전화 사이즈 확인 메소드
            MySootheApp(
                windowSize = calculateWindowSizeClass(this)
            )
        }
    }
}

data class Message(val drawable: Int, val text: String)

@Composable
fun MySootheApp(windowSize: WindowSizeClass) {
    when (windowSize.widthSizeClass) {
        // 화면 세로
        WindowWidthSizeClass.Compact -> {
            MySootheAppPortrait()
        }
        // 화면 가로
        WindowWidthSizeClass.Expanded -> {
            MySootheAppLandscape()
        }
    }
}
@Preview
@Composable
//세로모드
fun MySootheAppPortrait() {
    ComposePractice2Theme {
        // Scaffold : 구성 가능한 최상위 수준 컴포저블 제공 / 메테리얼 디자인을 가져다 쓸 수 있음
        // 앱바 +
        // Scaffold에 bottomBar를 제공함
        Scaffold(bottomBar = { SootheBottomNavigation() }, topBar = {SootheTopNavigation()}) { padding ->
            HomeScreen(Modifier.padding(padding))
        }
    }
}

@Composable
//가로모드
fun MySootheAppLandscape() {
    ComposePractice2Theme {
        // Surface에는 bottomBar 제공 안해서 다음과 같이 row로 구성해야함
        Surface(color = MaterialTheme.colorScheme.background) {
            Row {
                SootheNavigationRail()
                HomeScreen()
            }
        }
    }
}

@Preview
@Composable
fun SootheTopNavigation(modifier: Modifier = Modifier) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surfaceTint,
    ) {
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = null) },
            label = {Text("home")},
            selected =true,
            onClick = {  },
        )
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = null) },
            label = {Text("home")},
            selected =true,
            onClick = {  },
        )
    }
}

//세로 모드 바텀 바텀 버튼
@Composable
private fun SootheBottomNavigation(modifier: Modifier = Modifier) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
        modifier = modifier
    ) {
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = null
                )
            },
            label = {
                Text("home")
            },
            selected = true,
            onClick = {}
        )
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = null
                )
            },
            label = {
                Text("profile")
            },
            selected = false,
            onClick = {}
        )
    }
}

//가로 바텀 메뉴
@Composable
private fun SootheNavigationRail(modifier: Modifier = Modifier) {
    NavigationRail(
        containerColor = MaterialTheme.colorScheme.background,
        modifier = modifier.padding(start = 8.dp, end = 8.dp)
    ) {
        Column(
            modifier = modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            NavigationRailItem(
                icon = {
                    Icon(
                        imageVector = Icons.Default.Home,
                        contentDescription = null
                    )
                },
                label = {
                    Text("home")
                },
                selected = true,
                onClick = {}
            )
            Spacer(modifier = Modifier.height(8.dp))
            NavigationRailItem(
                icon = {
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = null
                    )
                },
                label = {
                    Text("profile")
                },
                selected = false,
                onClick = {}
            )
        }
    }
}
@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    Column(
        modifier
            .verticalScroll(rememberScrollState())
    ) {

        val favoriteCollectionsData = listOf(
            Message(R.drawable.honeybee_icon, "one"),
            Message(R.drawable.honeybee_icon, "two"),
            Message(R.drawable.honeybee_icon, "three"),
            Message(R.drawable.honeybee_icon, "four"),
            Message(R.drawable.honeybee_icon, "five"),
            Message(R.drawable.honeybee_icon, "six"),
            Message(R.drawable.honeybee_icon, "seven"),
            Message(R.drawable.honeybee_icon, "eight"),
            Message(R.drawable.honeybee_icon, "nine"),
            Message(R.drawable.honeybee_icon, "ten"),
            Message(R.drawable.honeybee_icon, "11")
        )
        val myViewModel = viewModel<MyViewModel>()
        val data = myViewModel.uiState.collectAsState()
        Log.e("TAG", "HomeScreen: ${data.value}", )

        Spacer(Modifier.height(7.dp))
        SearchBar(Modifier.padding(horizontal = 16.dp))
        HomeSection(title = "hello") {
            AlignYourBodyRow(list = favoriteCollectionsData)
        }
        HomeSection(title = "hello") {
            FavoriteCollectionsGrid(list = favoriteCollectionsData)
        }
        Spacer(Modifier.height(2.dp))

        ViewModelText(myViewModel)
    }
}

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
) {
    Column(modifier = Modifier.padding(16.dp)) {
        //rember
        var name: String by rememberSaveable { mutableStateOf("") }
        if (name.isNotEmpty()) {
            Text(
                text = "Test! $name",
                modifier = Modifier.padding(bottom = 8.dp),
                style = MaterialTheme.typography.titleSmall
            )
        }
        TextField(
            value = name,
            onValueChange = { name = it },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null
                )
            },
            modifier = modifier
                .fillMaxWidth()
                .heightIn(min = 56.dp)
        )
    }

}


@Composable
fun HomeSection(
    title: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Column(modifier) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .paddingFromBaseline(top = 60.dp, bottom = 16.dp)
                .padding(horizontal = 16.dp, vertical = 16.dp)
        )
        content()
    }
}
@Composable
fun ViewModelText(myViewModel: MyViewModel){
    val myText = myViewModel.uiState.collectAsState()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(25.dp)
    ) {
        Text(text = myText.value.toString())
        ListWithBug(listOf("a","b","c","d","e"))
    }
}

@Composable
fun ListWithBug(myList: List<String>) {
    var items = 0

    Row(horizontalArrangement = Arrangement.SpaceBetween) {
        Column {
            for (item in myList) {
                Text("Item: $item")
                items++ // Avoid! Side-effect of the column recomposing.
            }
        }
        Text("Count: $items")
    }
}

@Composable
fun AlignYourBodyRow(
    modifier: Modifier = Modifier,
    list: List<Message>,
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
        modifier = modifier
    ) {
        items(list) { item ->
            AlignYourBodyElement(drawable = item.drawable, text = item.text)
        }
    }
}

@Composable
fun AlignYourBodyElement(
    @DrawableRes drawable: Int,
    text: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val gradientColors = listOf(
            Color.Red,
            Color.Magenta,
            Color.Blue,
            Color.Cyan,
            Color.Green,
            Color.Yellow,
            Color.Red
        )
        val brush = Brush.sweepGradient(gradientColors)

        Image(
            painter = painterResource(drawable),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(88.dp)
                .clip(CircleShape)
                .border(1.5.dp, Brush.horizontalGradient(colors = gradientColors), CircleShape)
        )
        Text(
            text = text,
            modifier = Modifier.paddingFromBaseline(top = 24.dp, bottom = 8.dp),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun FavoriteCollectionsGrid(
    list: List<Message>,
    modifier: Modifier = Modifier,
) {
    LazyHorizontalGrid(
        rows = GridCells.Fixed(4),
        //가로, 세로로 얼마나 패딩?
        contentPadding = PaddingValues(horizontal = 16.dp),
        //항목 간의 간격
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        //각 항목 높이 수정
        verticalArrangement = Arrangement.spacedBy(16.dp),
        //컴포넌트 전체 높이
        modifier = modifier.height(250.dp)
    ) {
        items(list) { item ->
            FavoriteCollectionCard(item.drawable, item.text, Modifier.height(80.dp))
        }
    }
}

@Composable
fun FavoriteCollectionCard(
    @DrawableRes drawable: Int,
    text: String,
    modifier: Modifier = Modifier,
) {
    Surface(
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.surfaceVariant,
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(drawable),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(80.dp)
            )
            Text(
                text = "안녕",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }
}