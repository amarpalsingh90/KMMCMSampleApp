import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import model.ProductDetail
import org.jetbrains.compose.resources.ExperimentalResourceApi

@OptIn(ExperimentalResourceApi::class)
@Composable
fun App() {
    MaterialTheme {
        val productViewModel: ProductsViewModel =
            getViewModel(Unit, viewModelFactory { ProductsViewModel() })
        ProductPage(productViewModel)
    }
}

@Composable
fun ProductPage(
    productViewModel: ProductsViewModel
) {
    val uiState by productViewModel.uiState.collectAsState()
    val isItemSorted = remember { mutableStateOf(false) }
    AnimatedVisibility(uiState.productList.isNotEmpty()) {
        Column(modifier = Modifier.fillMaxSize().padding(5.dp)) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        if (isItemSorted.value.not()) {
                            productViewModel.sortItemByPrice()
                            isItemSorted.value = true
                        }
                    },
                ) {
                    Text(text = if (isItemSorted.value) "Item Is Sorted By Price" else "Sort By Price")
                }
            }
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                items(uiState.productList) {
                    ItemDetail(it)
                }
            }
        }
    }

}

@Composable
fun ItemDetail(productDetail: ProductDetail) {
    Box(
        modifier = Modifier.fillMaxWidth()
            .border(2.dp, color = Color.Magenta, shape = RoundedCornerShape(5.dp))
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            KamelImage(
                modifier = Modifier.fillMaxWidth(0.3f).aspectRatio(1.0f).padding(5.dp),
                resource = asyncPainterResource(productDetail.image),
                contentDescription = "",
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.fillMaxWidth()) {
                Row {
                    Text(
                        text = "Price:",
                        modifier = Modifier.padding(start = 10.dp, top = 10.dp),
                        color = Color.Black,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = productDetail.price.toString(),
                        modifier = Modifier.fillMaxWidth().padding(top = 10.dp),
                        color = Color.Black,
                        fontSize = 18.sp
                    )
                }

                Text(
                    text = "Description:",
                    modifier = Modifier.padding(start = 10.dp, top = 2.dp),
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = productDetail.description,
                    modifier = Modifier.fillMaxWidth()
                        .padding(start = 10.dp, top = 2.dp, end = 5.dp),
                    color = Color.Black,
                    fontSize = 15.sp,
                )
            }
        }
    }
}

expect fun getPlatformName(): String