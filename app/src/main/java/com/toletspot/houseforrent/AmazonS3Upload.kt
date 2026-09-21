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


/*class S3Uploader(
    accessKey: String,
    secretKey: String,
    private val bucketName: String,
    region: Regions
) {

    private val s3Client: AmazonS3Client = AmazonS3Client(
        BasicAWSCredentials(accessKey, secretKey)
    ).apply {
        setRegion(Region.getRegion(region))
    }

    data class UploadResult(
        val uri: Uri,
        val url: String?,
        val success: Boolean,
        val error: String? = null
    )

    suspend fun uploadFiles(
        context: Context,
        uris: List<Uri>,
        folder: String,
        onProgress: (uri: Uri, progress: Int) -> Unit
    ): List<UploadResult> = coroutineScope {

        uris.map { uri ->
            async(Dispatchers.IO) {
                uploadSingleFile(context, uri, folder, onProgress)
            }
        }.awaitAll()
    }

    private suspend fun uploadSingleFile(
        context: Context,
        uri: Uri,
        folder: String,
        onProgress: (uri: Uri, progress: Int) -> Unit
    ): UploadResult {

        // If already a URL -> just return
        if (uri.scheme?.startsWith("http") == true) {
            return UploadResult(uri, uri.toString(), true)
        }

        val ext = getFileExtension(context, uri) ?: return UploadResult(uri, null, false, "No extension")

        val tempFile = File.createTempFile("upload_", ".$ext", context.cacheDir)
        context.contentResolver.openInputStream(uri)?.use { input ->
            FileOutputStream(tempFile).use { output ->
                val buffer = ByteArray(4096)
                var totalBytes = 0L
                val size = input.available().toLong()

                var read: Int
                while (input.read(buffer).also { read = it } != -1) {
                    output.write(buffer, 0, read)
                    totalBytes += read
                    val progress = ((totalBytes * 100) / size).toInt()
                    onProgress(uri, progress)
                }
            }
        }

        val key = "$folder/${System.currentTimeMillis()}.$ext"

        val metadata = ObjectMetadata().apply {
            contentType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(ext)
        }

        return try {
            s3Client.putObject(
                PutObjectRequest(bucketName, key, tempFile)
                    .withGeneralProgressListener {
                        val pct = ((it.bytesTransferred * 100) / it.bytesTransferred).toInt()
                        onProgress(uri, pct)
                    }
            )

            val url = "https://$bucketName.s3.amazonaws.com/$key"
            tempFile.delete() // auto clean
            UploadResult(uri, url, true)

        } catch (e: Exception) {
            tempFile.delete()
            UploadResult(uri, null, false, e.message)
        }
    }

    private fun getFileExtension(context: Context, uri: Uri): String? {
        val resolver = context.contentResolver
        return if (uri.scheme == ContentResolver.SCHEME_CONTENT) {
            resolver.getType(uri)?.let { MimeTypeMap.getSingleton().getExtensionFromMimeType(it) }
        } else {
            MimeTypeMap.getFileExtensionFromUrl(uri.toString())
        }
    }
}

val uploader = S3Uploader(
    accessKey = constants.ACCESS_ID,
    secretKey = constants.SECRET_KEY,
    bucketName = constants.BUCKET_NAME,
    region = Regions.AP_SOUTH_1
)*/

//lifecycleScope.launch {
//    val results = uploader.uploadFiles(
//        context = mainActivity,
//        uris = selectedUris,
//        folder = "user_uploads",
//        onProgress = { uri, progress ->
//            println("Progress for $uri -> $progress%")
//        }
//    )
//
//    results.forEach {
//        if (it.success) println("Uploaded: ${it.url}")
//        else println("Error: ${it.error}")
//    }
//
//    val uploadedUrls = results.mapNotNull { it.url }
//    println("FINAL URL LIST: $uploadedUrls")
//
//    // Do something after all complete:
//    // example: call API, navigate UI, update ViewModel, etc.
//}


/*class S3Uploader(
    accessKey: String,
    secretKey: String,
    private val bucketName: String,
    private val cloudFrontUrl: String,
    region: Regions
) {

    private val s3Client = AmazonS3Client(
        BasicAWSCredentials(accessKey, secretKey)
    ).apply {
        setRegion(Region.getRegion(region))
    }

    enum class UploadType { PROFILE, POST_VIDEO, POST_IMAGE }

    data class UploadResult(
        val uri: Uri,
        val url: String?,
        val success: Boolean,
        val error: String? = null
    )

    // ---------------------- SINGLE FILE HANDLER ----------------------
    private suspend fun uploadSingle(
        context: Context,
        uri: Uri,
        userId: String,
        type: UploadType,
        onProgress: (uri: Uri, progress: Int) -> Unit
    ): UploadResult {

        // If URL already, just return it
        if (uri.scheme?.startsWith("http") == true) {
            return UploadResult(uri, uri.toString(), true)
        }

        val ext = getFileExtension(context, uri)
            ?: return UploadResult(uri, null, false, "Invalid file extension")

        val timestamp = System.currentTimeMillis()

        val folder = when (type) {
            UploadType.PROFILE     -> "$userId/profileImage"
            UploadType.POST_VIDEO  -> "$userId/post/postVideos"
            UploadType.POST_IMAGE  -> "$userId/post/postImages"
        }

        val key = "$folder/$timestamp.$ext"

        // Create temp file
        val tempFile = File.createTempFile("upload_", ".$ext", context.cacheDir)

        context.contentResolver.openInputStream(uri)?.use { input ->
            FileOutputStream(tempFile).use { output ->
                val buffer = ByteArray(4096)
                var totalBytes = 0L
                val size = input.available().toLong()

                var read: Int
                while (input.read(buffer).also { read = it } != -1) {
                    output.write(buffer, 0, read)
                    totalBytes += read

                    val progress = ((totalBytes * 100) / size).toInt()
                    onProgress(uri, progress)
                }
            }
        }

        return try {
            s3Client.putObject(PutObjectRequest(bucketName, key, tempFile))
            tempFile.delete()

            UploadResult(uri, "${constants.CLOUD_FRONT_URL}/$key", true)

        } catch (e: Exception) {
            tempFile.delete()
            UploadResult(uri, null, false, e.message)
        }
    }

    // ---------------------- MULTIPLE PARALLEL UPLOAD ----------------------
    suspend fun uploadFiles(
        context: Context,
        uris: List<Uri>,
        userId: String,
        type: UploadType,
        onProgress: (uri: Uri, progress: Int) -> Unit
    ): List<UploadResult> = coroutineScope {
        uris.map { uri ->
            async<UploadResult>(Dispatchers.IO) {
                uploadSingle(context, uri, userId, type, onProgress)
            }
        }.awaitAll()
    }

    // ---------------------- EXTENSION HELPERS ----------------------
    private fun getFileExtension(context: Context, uri: Uri): String? {
        val resolver = context.contentResolver
        return if (uri.scheme == ContentResolver.SCHEME_CONTENT) {
            resolver.getType(uri)?.let { MimeTypeMap.getSingleton().getExtensionFromMimeType(it) }
        } else {
            MimeTypeMap.getFileExtensionFromUrl(uri.toString())
        }
    }
}


val uploader = S3Uploader(
    accessKey = constants.ACCESS_ID,
    secretKey = constants.SECRET_KEY,
    bucketName = constants.BUCKET_NAME,
    cloudFrontUrl = constants.CLOUD_FRONT_URL,
    region = Regions.AP_SOUTH_1
)*/



//lifecycleScope.launch {
//    val results = uploader.uploadFiles(
//        context = this@MainActivity,
//        uris = selectedUris,
//        userId = AppPreferences.getUserId() ?: "",
//        type = S3Uploader.UploadType.POST_IMAGE,
//        onProgress = { uri, progress ->
//            println("Progress for $uri : $progress%")
//        }
//    )
//
//    val urls = results.mapNotNull { it.url }
//    println("UPLOAD COMPLETED: $urls")
//}



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

        println("MEDIAITEMSS ONCLICK CHECK 888 URI S3  -- ${uris}")


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
        println("MEDIAITEMSS ONCLICK CHECK 9999  -- ${uri}")


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
        println("MEDIAITEMSS ONCLICK CHECK 9999  -- ${uri}")

        // Skip upload if already URL but detect proper media type
        if (uri.toString().startsWith("https") || uri.toString().startsWith("http")) {
            val url = uri.toString()
            val type = detectMediaTypeFromUrl(url)
            println("✅ Already uploaded: $url (type: $type)")
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



