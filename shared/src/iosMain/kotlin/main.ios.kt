import androidx.compose.ui.window.ComposeUIViewController

actual fun getPlatformName(): String = "iOS Amar Pal Singh"

fun MainViewController() = ComposeUIViewController { App() }