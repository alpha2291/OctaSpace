package com.toletspot.houseforrent

import android.content.Context
import android.net.Uri
import android.webkit.MimeTypeMap
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.regions.Region
import com.amazonaws.regions.Regions
import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream


import com.amazonaws.event.ProgressListener
import com.amazonaws.event.ProgressEvent





class S3Uploader(
    private val bucket: String,
    private val cloudFront: String,
    accessId: String,
    secretKey: String
)
{

    private val s3Client = AmazonS3Client(BasicAWSCredentials(accessId, secretKey)).apply {
        setRegion(Region.getRegion(Regions.AP_SOUTH_1))
    }

    enum class MediaType { IMAGE, VIDEO, OTHER , THUMBNAIL}

    data class UploadResult(
        val originalUri: Uri,
        val url: String,
        val type: MediaType
    )

    suspend fun uploadFiles(
        context: Context,
        userId: String,
        uris: List<Uri>,
        onProgress: (uri: Uri, progress: Int) -> Unit
    ): List<UploadResult> = coroutineScope {



        uris.map { uri ->
            async(Dispatchers.IO) {
                uploadSingle(context, userId, uri , false,"") { p -> onProgress(uri, p) }
            }
        }.awaitAll()
    }

    suspend fun uploadSingle2(
        context: Context,
        userId: String,
        uri: Uri,
        onProgress: (Int) -> Unit
    ): UploadResult = withContext(Dispatchers.IO) {



        // Skip upload if already URL
        if (uri.toString().startsWith("https")) {
            return@withContext UploadResult(uri, uri.toString(), MediaType.OTHER)
        }

        val ext = getFileExtension(context, uri) ?: "jpg"
        val type = if (ext.equals("mp4", true)) MediaType.VIDEO else MediaType.IMAGE

        val folder = when (type) {
            MediaType.IMAGE -> "postImages"
            MediaType.VIDEO -> "postVideos"
            else -> "files"
        }

        val key = "$userId/post/$folder/${System.currentTimeMillis()}.$ext"
        val temp = createTempFile(context, uri, ext)

        val metadata = ObjectMetadata().apply {
            contentType = if (type == MediaType.VIDEO) "video/mp4" else "image/jpeg"
        }



        val request = PutObjectRequest(bucket, key, temp)
        request.metadata = metadata

        request.generalProgressListener = ProgressListener { progressEvent: ProgressEvent ->
            val transferred = progressEvent.bytesTransferred
            if (transferred > 0) {
                val percent = ((transferred.toDouble() / temp.length()) * 100).toInt()
                onProgress(percent)
            }
        }

        s3Client.putObject(request)
        temp.delete()



        UploadResult(
            originalUri = uri,
            url = "$cloudFront/$key",
            type = type
        )
    }

    suspend fun uploadSingle(
        context: Context,
        userId: String,
        uri: Uri,
        thumbail : Boolean,
        profile : String,
        onProgress: (Int) -> Unit
    ): UploadResult = withContext(Dispatchers.IO) {


        // Skip upload if already URL but detect proper media type
        if (uri.toString().startsWith("https") || uri.toString().startsWith("http")) {
            val url = uri.toString()
            val type = detectMediaTypeFromUrl(url)

            return@withContext UploadResult(uri, url, type)
        }

        val ext = getFileExtension(context, uri) ?: "jpg"
        val type = if (ext.equals("mp4", true)) MediaType.VIDEO  else if (thumbail )MediaType.THUMBNAIL else if (profile.isNotEmpty()) MediaType.OTHER  else  MediaType.IMAGE

        val folder = when (type) {
            MediaType.IMAGE -> "postImages"
            MediaType.VIDEO -> "postVideos"
            MediaType.THUMBNAIL   -> "videoThumbnail"
            else -> "${userId}/profileimage"
        }

        val key = "$userId/post/$folder/${System.currentTimeMillis()}.$ext"
        val temp = createTempFile(context, uri, ext)

        val metadata = ObjectMetadata().apply {
            contentType = if (type == MediaType.VIDEO) "video/mp4" else "image/jpeg"
        }

        val request = PutObjectRequest(bucket, key, temp)
        request.metadata = metadata

        request.generalProgressListener = ProgressListener { progressEvent: ProgressEvent ->
            val transferred = progressEvent.bytesTransferred
            if (transferred > 0) {
                val percent = ((transferred.toDouble() / temp.length()) * 100).toInt()
                onProgress(percent)
            }
        }

        s3Client.putObject(request)
        temp.delete()

        UploadResult(
            originalUri = uri,
            url = "$cloudFront/$key",
            type = type
        )
    }

    private fun detectMediaTypeFromUrl(url: String): MediaType {
        val lower = url.lowercase()
        return when {
            lower.contains(".mp4") || lower.contains(".mov") ||
                    lower.contains(".mkv") || lower.contains(".avi") ||
                    lower.contains("postvideo") -> MediaType.VIDEO

            lower.contains(".jpg") || lower.contains(".jpeg") ||
                    lower.contains(".png") || lower.contains(".webp") ||
                    lower.contains(".gif") || lower.contains("postimage") -> MediaType.IMAGE

            else -> MediaType.IMAGE
        }
    }

    private fun getFileExtension(context: Context, uri: Uri): String? =
        if (uri.scheme == "content") {
            MimeTypeMap.getSingleton()
                .getExtensionFromMimeType(context.contentResolver.getType(uri))
        } else {
            MimeTypeMap.getFileExtensionFromUrl(uri.toString())
        }

    private fun createTempFile(context: Context, uri: Uri, ext: String): File {
        val file = File.createTempFile("upload_", ".$ext", context.cacheDir)
        context.contentResolver.openInputStream(uri)?.use { input ->
            FileOutputStream(file).use { output -> input.copyTo(output) }
        }
        return file
    }
}


/*fun handlePostUpload(mediaList: List<Uri>, navController: NavHostController) {

    val uploader = S3Uploader(
        bucket = constants.BUCKET_NAME,
        cloudFront = constants.CLOUD_FRONT_URL,
        accessId = constants.ACCESS_ID,
        secretKey = constants.SECRET_KEY
    )

    lifecycleScope.launch {

        val results = uploader.uploadFiles(
            context = this@MainActivity,
            userId = AppPreferences.getUserId()!!,
            uris = mediaList
        ) { uri, progress ->
            println("Uploading $uri = $progress%")
        }

        val videoUrl = results.firstOrNull { it.type == S3Uploader.MediaType.VIDEO }?.url ?: ""
        val imageUrls = results.filter { it.type == S3Uploader.MediaType.IMAGE }.map { it.url }
        val type = if (videoUrl.isEmpty()) "2" else "1"

        constants.API_Vm.put_post_Form6(
            user_id = AppPreferences.getUserId(),
            user_post_id = AppPreferences.get_Post_Id(),
            post_type = type,
            video_url = videoUrl,
            image_urls = imageUrls
        ) {
            navController.navigate(PostPropertyFlow.ViewPropertyStructure.route)
        }
    }
}


enum class PostFlow { NONE, NEW, DRAFT, REPOST, EDIT }

private val _postFlow = MutableStateFlow(PostFlow.NONE)
val postFlow = _postFlow.asStateFlow()

fun setPostFlow(flow: PostFlow) { _postFlow.value = flow }*/



