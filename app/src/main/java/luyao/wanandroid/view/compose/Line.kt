package luyao.wanandroid.view.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import luyao.wanandroid.R

/**
 * Description:
 * Author: luyao
 * Date: 2022/7/6 22:57
 */
@Composable
fun Line(
    modifier: Modifier = Modifier,
    color: Color = colorResource(R.color.light_gray),
    height: Dp = 1.dp,
) {
    Spacer(
        modifier
            .background(color = color)
            .fillMaxWidth()
            .height(height)
    )
}

@Composable
@Preview
fun LinePreview() {
    Line()
}