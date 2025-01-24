package com.example.ktorclient

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ktorclient.model.Post
import com.example.ktorclient.network.KtorClient
import com.example.ktorclient.ui.theme.KtorClientTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var posts by remember { mutableStateOf(emptyList<Post>()) }

            LaunchedEffect(Unit) {
                posts = KtorClient().getPosts()
            }

            KtorClientTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainContent(
                        modifier = Modifier.padding(innerPadding)
                            .fillMaxSize(),
                        list = posts
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Composable
fun MainContent(modifier: Modifier = Modifier, list: List<Post>) {
    LazyColumn(modifier.fillMaxSize()) {
        items(list) {
            Column(
                modifier = Modifier.padding(12.dp)
                    .padding(12.dp)
                    .fillMaxWidth()
            ) {
                Text(text = it.id.toString())
                Spacer(Modifier.height(8.dp))
                Text(text = it.title, style = MaterialTheme.typography.bodyLarge)
                Spacer(Modifier.height(8.dp))
                Text(text = it.body, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    KtorClientTheme {
        Greeting("Android")
    }
}