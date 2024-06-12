package android.example.newsapp.screens.newswebview

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.example.newsapp.R
import android.example.newsapp.databinding.FragmentNewsWebViewBinding
import android.net.Uri
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation

class NewsWebViewFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentNewsWebViewBinding =
            FragmentNewsWebViewBinding.inflate(inflater, container, false)

        val args = NewsWebViewFragmentArgs.fromBundle(requireArguments()!!)
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.webViewClient = MyWebViewClient()
        binding.webView.loadUrl(args.webPageLink)
        (activity as AppCompatActivity).supportActionBar?.title = args.webPageTitle

        return binding.root
    }

    class MyWebViewClient() : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            view?.loadUrl(url!!)
            return true
        }
    }

    companion object {
    }
}