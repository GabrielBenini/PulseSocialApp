package com.example.pulsesocial.feature.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.Comment
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.ModeComment
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pulsesocial.domain.response.PostResponse
import com.example.pulsesocial.ui.theme.PulseSocialTheme
import java.time.LocalDateTime

@Composable
fun PostCard(
    post: PostResponse,
    onLikeClick: () -> Unit,
    onCommentClick: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                text = post.content
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row {

                TextButton(onClick = onLikeClick) {

                    Icon(
                        imageVector = if (post.likedByUser) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Like",
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Text("${post.likesCount}")
                }

                Spacer(modifier = Modifier.width(16.dp))

                TextButton(onClick = onCommentClick) {
                    Icon(
                        imageVector = Icons.Default.ChatBubbleOutline,
                        contentDescription = "Comment",
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Text("${post.commentsCount}")                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun PostCardPreview() {

    val mockPost = PostResponse(
        id = 1,
        content = "Esse é um post de teste no Pulse",
        imageUrl = "",
        createdAt = "",
        userId = 1,
        likesCount = 12,
        commentsCount = 4,
        likedByUser = false
    )

    PulseSocialTheme {
        PostCard(
            post = mockPost,
            onLikeClick = {},
            onCommentClick = {}
        )
    }
}