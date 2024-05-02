package com.rsddm.marketplace.designSystem.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PhotoGallery(images: List<Int>) {
    val state = rememberPagerState(pageCount = { images.size })

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        HorizontalPager(state) { page ->
            GalleryItem(images[page])
        }

        Text("${state.currentPage + 1} de ${state.pageCount}")
    }
}

@Composable
fun GalleryItem(image: Int, modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = image),
        contentDescription = null,
        modifier = modifier
            .fillMaxWidth()
            .height(220.dp),
        contentScale = ContentScale.FillHeight
    )
}