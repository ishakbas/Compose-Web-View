package x.configs.composewebview

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.ViewGroup
import android.webkit.*
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import x.configs.composewebview.ui.theme.ComposeWebViewTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        requestPermissions(arrayOf(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO, Manifest.permission.READ_EXTERNAL_STORAGE),1)

        setContent {
            ComposeWebViewTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    MyWebView(context = this)
                }
            }
        }

    }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun MyWebView(context: Context) {

    val url = "https://puzoki.github.io/demo-project/"

    val myWebView = WebViewClient()
    val myWebViewChr = object : WebChromeClient() {

        override fun onPermissionRequest(request: PermissionRequest?) {
            Toast.makeText(context, "Сайт хочет камеры", Toast.LENGTH_LONG).show()
            request?.grant(request.resources)
        }

    }
    Column(modifier = Modifier.fillMaxSize()) {

        AndroidView(factory = {

            WebView(it).apply {

                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                webViewClient = myWebView
                webChromeClient = myWebViewChr

                settings.javaScriptEnabled = true
                settings.allowContentAccess = true
                settings.allowFileAccess = true
                settings.mediaPlaybackRequiresUserGesture = false
                settings.useWideViewPort = true
                settings.domStorageEnabled = true
                loadUrl(url)
            }
        },
            update = { it.loadUrl(url) }
        )
    }
}