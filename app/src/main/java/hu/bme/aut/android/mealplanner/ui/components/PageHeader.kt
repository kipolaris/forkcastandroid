package hu.bme.aut.android.mealplanner.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hu.bme.aut.android.mealplanner.R
import hu.bme.aut.android.mealplanner.ui.theme.LobsterFont

@Composable
fun PageHeader(
    title: String,
    onPreviousClick: (() -> Unit)? = null,
    onNextClick: (() -> Unit)? = null
) {
    Box(
        modifier = Modifier
            .height(70.dp)
            .width(280.dp)
            .padding(bottom = 24.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.paper),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = { onPreviousClick?.invoke() }, enabled = onPreviousClick != null) {
                Image(
                    painter = painterResource(id = R.drawable.arrowleft),
                    contentDescription = "Previous",
                    modifier = Modifier.size(32.dp)
                )
            }
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontFamily = LobsterFont,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFFFF78A8)
                )
            )
            IconButton(onClick = { onNextClick?.invoke() }, enabled = onNextClick != null) {
                Image(
                    painter = painterResource(id = R.drawable.arrowright),
                    contentDescription = "Next",
                    modifier = Modifier.size(32.dp)
                )
            }
        }

        Image(
            painter = painterResource(id = R.drawable.pinkflowertape),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .rotate(45f)
                .offset(x = (-8).dp, y = (8).dp)
        )
        Image(
            painter = painterResource(id = R.drawable.pinkflowertape),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .rotate(45f)
                .offset(x = (8).dp, y = (-8).dp)
        )
    }
}