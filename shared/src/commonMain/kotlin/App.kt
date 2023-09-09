import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.icerock.moko.mvvm.compose.ViewModelFactory
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import model.ProductDetail
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

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
    AnimatedVisibility(uiState.productList.isNotEmpty()) {
        Column(modifier = Modifier.fillMaxSize().padding(5.dp)) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Button(modifier = Modifier.fillMaxWidth(),
                    onClick = {productViewModel.sortItemByPrice()},){
                    Text("Sort By Price")
                }
            }
            LazyVerticalGrid(
                modifier = Modifier.fillMaxSize(),
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp)) {
                items(uiState.productList) {
                    ItemDetail(it)
                }
            }
        }
    }

}

@Composable
fun ItemDetail(productDetail: ProductDetail){
    Column(modifier = Modifier.fillMaxWidth()) {
        KamelImage(
            modifier = Modifier.fillMaxWidth().aspectRatio(1.0f),
            resource = asyncPainterResource(productDetail.image),
            contentDescription = "",
            contentScale = ContentScale.Crop
        )
        Text(
            text = "Price: ${productDetail.price}",
            modifier = Modifier.fillMaxWidth().padding(10.dp),
            color = Color.Black,
            fontSize = 20.sp
        )
    }
}

expect fun getPlatformName(): String