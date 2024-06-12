package android.example.newsapp.screens.enlargeimage

import android.app.Dialog
import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.example.newsapp.databinding.DialogEnlargeImageBinding
import android.net.Uri
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.Window
import android.widget.Toast
import com.squareup.picasso.Picasso

class EnlargeImageDialog(context: Context, private val imageUrl: String) : Dialog(context) {
    init {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        val binding = DialogEnlargeImageBinding.inflate(LayoutInflater.from(context))
        setContentView(binding.root)

        // Load image into ImageView using Picasso
        Picasso.get().load(imageUrl).into(binding.imageView)

        // Close button click listener
        binding.closeButton.setOnClickListener {
            dismiss() // Dismiss the dialog
        }

        binding.downloadBtn.setOnClickListener {
            // Download the image
            Log.i("EnlargeImageDialog", "Download button clicked")
            downloadImage(imageUrl)
        }
    }

    private fun downloadImage(imageUrl: String) {
        val request = DownloadManager.Request(Uri.parse(imageUrl))
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
        request.setTitle("Downloading Image")
        request.setDescription("Downloading $imageUrl")
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "${System.currentTimeMillis()}.jpg")

        val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        downloadManager.enqueue(request)

        Toast.makeText(context, "Downloading image...", Toast.LENGTH_SHORT).show()

        // after downloading, ask the user which app they want to open the image
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setDataAndType(Uri.parse(imageUrl), "image/*")
        context.startActivity(Intent.createChooser(intent, "Open with"))
    }
}