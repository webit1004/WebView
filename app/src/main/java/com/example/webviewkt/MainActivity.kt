package com.example.webviewkt

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.webkit.*
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.webviewkt.databinding.ActivityMainBinding

/* IntentAct (btn click) -> MainAct(webview 처리) -> IntentAct(주소 검색결과 받아옴) */
class MainActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityMainBinding
    private val binding get() = mBinding
    private val TAG : String = "로그"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d(TAG, "MainActivity - setting webview")

        binding.webView.apply{
            settings.javaScriptEnabled = true // js 활성화
            settings.javaScriptCanOpenWindowsAutomatically = true // javascript로 window.open 허용
            settings.domStorageEnabled = true

            /* webView에서 새 창이 뜨지 않도록 방지하는 구문 */
            webViewClient = WebViewClient()
            webChromeClient = WebChromeClient()

            addJavascriptInterface(WebAppInterface(),"Android") // js에서 Android 호출


            loadUrl("http://192.168.35.186/android_asset/search_address.html") //local web server > 주소검색 파일
            Log.d(TAG, "MainActivity - searching address")
        }

    }

    class WebAppInterface(){
        @JavascriptInterface
        fun setting(text : String){
            Log.d("로그", "받아온 값 : $text")

        }
    }

    override fun onBackPressed() {
        if(binding.webView.canGoBack()){
            //만약 웹사이트에서 뒤로 갈 페이지가 존재한다면
            binding.webView.goBack()
        }else{
            super.onBackPressed()
        }
    }
}