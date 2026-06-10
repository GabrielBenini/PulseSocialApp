package com.example.pulsesocial.feature.components

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pulsesocial.data.datastore.UserPreferencesRepository
import com.example.pulsesocial.data.session.SessionManager
import com.example.pulsesocial.domain.response.PostResponse
import com.example.pulsesocial.domain.response.UserSummary
import com.example.pulsesocial.ui.theme.PulseSocialTheme

@Composable
fun PostCard(
    post: PostResponse,
    onLikeClick: () -> Unit,
    onDeletePostClick: () -> Unit,
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ),
        elevation = CardDefaults.elevatedCardElevation(10.dp)
    ) {

        Column(

            modifier = Modifier.padding(16.dp)
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {

                Surface(
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .size(50.dp),
                    color = MaterialTheme.colorScheme.background,
                    shape = RoundedCornerShape(60)

                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Avatar",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }

                Text(
                    text = post.author.username
                )

                Spacer(Modifier.weight(1f))

                IconButton(onClick = { onDeletePostClick()}) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }

            }

            Text(
                modifier = Modifier.padding(top = 16.dp),
                text = post.content
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row {

                TextButton(onClick = onLikeClick) {

                    Icon(
                        imageVector = if (post.likedByUser) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Like",
                        tint = if (post.likedByUser) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onPrimary
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f),
                        text = "${post.likesCount}"
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                TextButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.Default.ChatBubbleOutline,
                        contentDescription = "Comment",
                        tint = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f)
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f),
                        text = "${post.commentsCount}"
                    )
                }
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
        likedByUser = false,
        author = UserSummary(
            1,
            "Gabriel",
            "img"
        )
    )

    PulseSocialTheme(
        darkTheme = false
    ) {
        PostCard(
            post = mockPost,
            onLikeClick = {},
            onDeletePostClick = {}
        )
    }
}