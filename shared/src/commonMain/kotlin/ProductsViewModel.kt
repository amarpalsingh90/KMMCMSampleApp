import dev.icerock.moko.mvvm.viewmodel.ViewModel
import io.ktor.client.HttpClient
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import model.ProductDetail


data class ProductsUiState(
    val productList: List<ProductDetail> = emptyList()
)

class ProductsViewModel : ViewModel() {
    private val _uiState: MutableStateFlow<ProductsUiState> =
        MutableStateFlow<ProductsUiState>(ProductsUiState())
    val uiState = _uiState.asStateFlow()

   private val httpClient: HttpClient = HttpClient {
        install(ContentNegotiation) {
            json(contentType = ContentType.Any)
        }

    }

    init {
        updateProducts()
    }
    private fun updateProducts() {
        viewModelScope.launch() {
            val productList = getProducts()
            _uiState.update { it.copy(productList = productList) }
        }
    }
    private suspend fun getProducts(): List<ProductDetail> {
        return httpClient.get("https://dummyapi.online/api/products").body()
    }

    fun sortItemByPrice(){
       val sortedList= _uiState.value.productList.sortedBy {it.price  }
        _uiState.update { it.copy(productList = sortedList) }
    }

    override fun onCleared() {
        super.onCleared()
        httpClient.close()
    }
}