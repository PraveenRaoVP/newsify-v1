package android.example.newsapp.screens.newsdetails

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.example.newsapp.R
import android.example.newsapp.databinding.FragmentNewsDetailsBinding
import android.example.newsapp.screens.enlargeimage.EnlargeImageDialog
import android.example.newsapp.utils.ImageClickListener
import android.net.Uri
import android.text.method.ScrollingMovementMethod
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ShareCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.squareup.picasso.Picasso


class NewsDetailsFragment : Fragment(), ImageClickListener {

    private lateinit var arguments: NewsDetailsFragmentArgs
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentNewsDetailsBinding =
            FragmentNewsDetailsBinding.inflate(inflater, container, false)

        arguments = NewsDetailsFragmentArgs.fromBundle(requireArguments()!!)

        val application = requireNotNull(this.activity).application
        val viewModelFactory = NewsDetailsViewModelFactory(application)

        val newsDetailsViewModel =
            ViewModelProvider(this, viewModelFactory)[NewsDetailsViewModel::class.java]

        binding.newsDetailsViewModel = newsDetailsViewModel


        newsDetailsViewModel.title.value = arguments.newsTitle
        newsDetailsViewModel.content.value = arguments.newsContent
        newsDetailsViewModel.imageUrl.value = arguments.imageUrl
        Picasso.get().load(newsDetailsViewModel.imageUrl.value).into(binding.newsImage)
        binding.titleText.text = newsDetailsViewModel.title.value
        binding.contentText.text = newsDetailsViewModel.content.value
        binding.lifecycleOwner = this

        binding.contentText.movementMethod = ScrollingMovementMethod()

        newsDetailsViewModel.isImageClicked.observe(viewLifecycleOwner) {
            if (it) {
                onImageClicked(arguments.imageUrl)
                newsDetailsViewModel.onImageClickComplete()
            }
        }

        newsDetailsViewModel.isShareClicked.observe(viewLifecycleOwner) {
            if (it) {
                startActivity(getShareIntent())
                newsDetailsViewModel.onShareComplete()
            }
        }

        newsDetailsViewModel.isLinkClicked.observe(viewLifecycleOwner) {
            if (it) {

                // if the links are youtube or amazon or twitter links, open them in their respective applications
                if (isExternalAppUrl(arguments.readMoreUrl)) {
                    Log.i("NewsDetailsFragment", "External app url")
                    if (openExternalApp(arguments.readMoreUrl)) {
                        Log.i("NewsDetailsFragment", "Opened external app")
                        return@observe
                    }
                }

                val action =
                    NewsDetailsFragmentDirections.actionNewsDetailsFragmentToNewsWebViewFragment(
                        arguments.readMoreUrl,
                        arguments.newsTitle
                    )
                findNavController().navigate(action)
                newsDetailsViewModel.onLinkComplete()
            }
        }

        newsDetailsViewModel.title.observe(viewLifecycleOwner) { title ->
            (requireActivity() as AppCompatActivity).supportActionBar?.title = title
        }

        return binding.root
    }

    private fun getShareIntent(): Intent {
        return ShareCompat.IntentBuilder.from(requireActivity())
            .setText("Check out this article: ${arguments.newsTitle} \n ${arguments.readMoreUrl}")
            .setType("text/plain")
            .intent
    }

    private fun isExternalAppUrl(url: String): Boolean {
        return url.startsWith("https://twitter.com") ||
                url.startsWith("https://www.youtube.com") ||
                url.startsWith("https://www.amazon.in") ||
                url.startsWith("https://www.instagram.com") ||
                url.startsWith("https://www.facebook.com") ||
                url.startsWith("https://www.linkedin.com") ||
                url.startsWith("https://x.com")
    }

    private fun openExternalApp(url: String): Boolean {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        when {
            url.startsWith("https://twitter.com") -> {
                Log.i("NewsDetailsFragment", "Twitter link")
                intent.setPackage("com.twitter.android")
            }

            url.startsWith("https://www.youtube.com") -> {
                Log.i("NewsDetailsFragment", "Youtube link")
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.setPackage("com.google.android.youtube")
            }

            url.startsWith("https://www.amazon.in") -> {
                Log.i("NewsDetailsFragment", "Amazon link")
                intent.setPackage("in.amazon.mShop.android.shopping")
            }

            url.startsWith("https://www.instagram.com") -> {
                Log.i("NewsDetailsFragment", "Instagram link")
                intent.setPackage("com.instagram.android")
            }

            url.startsWith("https://www.facebook.com") -> {
                Log.i("NewsDetailsFragment", "Facebook link")
                intent.setPackage("com.facebook.katana")
            }

            url.startsWith("https://www.linkedin.com") -> {
                Log.i("NewsDetailsFragment", "LinkedIn link")
                intent.setPackage("com.linkedin.android")
            }

            url.startsWith("https://x.com") -> {
                Log.i("NewsDetailsFragment", "X link")
                intent.setPackage("com.twitter.android")
            }

            else -> return false
        }

        // check if the app is present in the phone
        Log.i("NewsDetailsFragment", requireActivity().packageManager.toString())
        try {
            startActivity(intent)
            return true
        } catch (e: Exception) {
            Log.e("NewsDetailsFragment", "Error opening external app: ${e.message}")
            return false
        }
    }

    companion object {
    }

    override fun onImageClicked(imageUrl: String) {
        val dialog = EnlargeImageDialog(requireContext(), imageUrl)
        dialog.show()
    }
}