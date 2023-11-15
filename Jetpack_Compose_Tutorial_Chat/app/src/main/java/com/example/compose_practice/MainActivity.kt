package com.example.compose_practice

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose_practice.ui.theme.Compose_PracticeTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 구성 가능한 함수가 호출되는 활동의 레이아웃을 정의
        setContent {
            Compose_PracticeTheme {
                Surface(Modifier.fillMaxSize()) {
                    MakingCard(
                        listOf(
                            MyMessage("이푸름", "123123\n123123\n123123\n123123\n123123"),
                            MyMessage("강푸름", "123123\n123123\n123123\n123123\n123123"),
                            MyMessage("서푸름", "123123\n123123\n123123\n123123\n123123"),
                            MyMessage("독고푸름", "123123\n123123\n123123\n123123\n123123"),
                            MyMessage("박푸름", "123123\n123123\n123123\n123123\n123123"),
                        )
                    )
                }
            }
        }
    }
}

data class MyMessage(val title: String, val content: String)

@Composable
fun MakingCard(messages: List<MyMessage>) {
    LazyColumn {
        items(messages) { message ->
            MessageCard(name = message)
        }
    }
}

@Composable
fun MessageCard(name: MyMessage) {
    Row(Modifier.padding(all = 8.dp)) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = "",
            Modifier
                .size(40.dp)
                .clip(CircleShape)
                .border(1.5.dp, Color.Black, CircleShape)
        )

        Spacer(modifier = Modifier.width(8.dp))

        var isExpanded by remember { mutableStateOf(false) }
        val surfaceColor by animateColorAsState(
            targetValue = if (isExpanded) Color.LightGray else Color.White,
            label = ""
        )

        Column(Modifier.clickable { isExpanded = !isExpanded }) {
            Text(
                text = name.title,
                color = Color.Gray,
                style = MaterialTheme.typography.titleSmall
            )

            Spacer(modifier = Modifier.height(4.dp))

            // shape 굴곡
            Surface(
                shape = MaterialTheme.shapes.medium,
                shadowElevation = 2.dp,
                color = surfaceColor,
                modifier = Modifier
                    .animateContentSize()
                    .padding(1.dp)
            ) {
                Text(
                    text = name.content,
                    Modifier.padding(all = 4.dp),
                    maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                    style = MaterialTheme.typography.titleMedium

                )
            }
        }
    }
}