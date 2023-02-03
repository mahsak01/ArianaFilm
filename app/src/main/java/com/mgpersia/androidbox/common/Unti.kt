import android.annotation.SuppressLint
import android.app.Activity
import android.app.DownloadManager
import android.content.ContentValues
import android.content.Context
import android.content.res.Resources
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings.Global.putString
import android.util.DisplayMetrics
import android.view.Gravity
import android.widget.TextView
import android.widget.Toast
import androidx.core.net.toUri
import androidx.work.*
import com.mgpersia.androidbox.R
import com.mgpersia.androidbox.common.FileDownloadWorker
import com.mgpersia.androidbox.data.model.*
import com.mgpersia.androidbox.data.model.enum.ToastStatus
import java.io.File
import java.io.FileOutputStream
import java.net.URL
import java.util.concurrent.TimeUnit

fun Toast.showCustomToast(
    message: String,
    activity: Activity,
    status: ToastStatus
) {


    val layout = if (status == ToastStatus.ERROR)
        activity.layoutInflater.inflate(
            R.layout.layout_custom_toast_error,
            activity.findViewById(R.id.layoutCustomToast_toastCl)
        )
    else
        activity.layoutInflater.inflate(
            R.layout.layout_custom_toast_ok,
            activity.findViewById(R.id.layoutCustomToast_toastCl)
        )


    // set the text of the TextView of the message
    val textView = layout.findViewById<TextView>(R.id.layoutCustomToast_messageTv)
    textView.text = message

    // use the application extension function
    this.apply {
        setGravity(Gravity.TOP, 0, 40)
        duration = Toast.LENGTH_SHORT
        view = layout
        show()
    }
}

object FileParams {
    const val KEY_FILE_URL = "key_file_url"
    const val KEY_FILE_NAME = "key_file_name"
    const val KEY_FILE_URI = "key_file_uri"
    const val KEY_FILE_TYPE = "key_file_uri"

}

object NotificationConstants {
    const val CHANNEL_NAME = "download_file_worker_demo_channel"
    const val CHANNEL_DESCRIPTION = "download_file_worker_demo_description"
    const val CHANNEL_ID = "download_file_worker_demo_channel_123456"
    const val NOTIFICATION_ID = 1
}

public fun getSavedFileUri(
    fileName: String,
    fileType: String,
    fileUrl: String,
    context: Context
): Uri? {
    val mimeType = when (fileType) {
        "PDF" -> "application/pdf"
        "PNG" -> "image/png"
        "mp4" -> "video/mp4"
        "mkv" -> "video/mkv"
        else -> ""
    } // different types of files will have different mime type

    if (mimeType.isEmpty()) return null

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
            put(MediaStore.MediaColumns.MIME_TYPE, mimeType)
            put(MediaStore.MediaColumns.RELATIVE_PATH, "Download/DownloaderDemo")
        }

        val resolver = context.contentResolver

        val uri = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)

        return if (uri != null) {
            URL(fileUrl).openStream().use { input ->
                resolver.openOutputStream(uri).use { output ->
                    input.copyTo(output!!, DEFAULT_BUFFER_SIZE)
                }
            }
            uri
        } else {
            null
        }
    } else {
        val target = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
            fileName
        )
        URL(fileUrl).openStream().use { input ->
            FileOutputStream(target).use { output ->
                input.copyTo(output)
            }
        }
        return target.toUri()
    }
}

@SuppressLint("Range")
fun downloadFile(url: String, outputFileName: String, manager: DownloadManager, context: Context) {

    Thread {
        val request = DownloadManager.Request(Uri.parse(url))
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
        request.setTitle("دانلود")
        request.setDescription("دانلود فایل پیوست تیکت")
        request.allowScanningByMediaScanner()
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, outputFileName)

        val downloadId = manager.enqueue(request)

        var downloading = true
        while (downloading) {
            val query = DownloadManager.Query()
            query.setFilterById(downloadId)

            val cursor = manager.query(query)
            if (cursor.moveToFirst()) {
                val bytesDownloaded =
                    cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR))
                val bytesTotal =
                    cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES))

                if (cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)) == DownloadManager.STATUS_SUCCESSFUL) {
                    downloading = false
                }

                val progress = ((bytesDownloaded * 100L) / bytesTotal).toInt()
                cursor.close()
            }
        }

    }.start()

}

fun convertDpToPixel(dp: Float, context: Context?): Float {
    return if (context != null) {
        val resources = context.resources
        val metrics = resources.displayMetrics
        dp * (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    } else {
        val metrics = Resources.getSystem().displayMetrics
        dp * (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }
}

