#include <jni.h>

// Secrets are injected at build time from local.properties via Gradle/CMake.
// See app/build.gradle.kts and README.md. Never hardcode credentials here.
#ifndef AWS_ACCESS_ID
#define AWS_ACCESS_ID ""
#endif
#ifndef AWS_SECRET_KEY
#define AWS_SECRET_KEY ""
#endif
#ifndef AWS_BUCKET_NAME
#define AWS_BUCKET_NAME ""
#endif
#ifndef CLOUDFRONT_URL
#define CLOUDFRONT_URL ""
#endif

// Write C++ code here.
//
// Do not forget to dynamically load the C++ library into your application.
//
// For instance,
//
// In MainActivity.java:
//    static {
//       System.loadLibrary("propreelz");
//    }
//
// Or, in MainActivity.kt:
//    companion object {
//      init {
//         System.loadLibrary("propreelz")
//      }
//    }    https://landsales.skyraantech.com  /// https://land4sales.com // rental url
extern "C"
JNIEXPORT jstring JNICALL
Java_com_toletspot_houseforrent_MainActivity_00024Companion_getLiveUrl(JNIEnv *env, jobject thiz) {
//    return (*env).NewStringUTF("https://rent.skyraantech.com/backend/api/auth/");
//    return (*env).NewStringUTF("https://rent.land4sales.com/backend/api/auth/");
    return (*env).NewStringUTF("https://toletspot.com/backend/api/auth/");
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_toletspot_houseforrent_MainActivity_00024Companion_getDemoUrl(JNIEnv *env, jobject thiz) {
    return (*env).NewStringUTF("https://rent.skyraantech.com/backend/api/auth/");
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_toletspot_houseforrent_MainActivity_00024Companion_getprofileReportUrl(JNIEnv *env,
                                                                               jobject thiz) {
//    return (*env).NewStringUTF( "https://rent.skyraantech.com/backend/profile_justify.html?");
    return (*env).NewStringUTF( "https://toletspot.com/backend/profile_justify.html");

//    https://rent.skyraantech.com/backend/profile_justify.html?phone_num=1144770088&phone_num_cc=91&device_type=2
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_toletspot_houseforrent_MainActivity_00024Companion_getPostReportUrl(JNIEnv *env,
                                                                            jobject thiz) {
//    return (*env).NewStringUTF("https://land4sales.com/backend/post_justify?");
    return (*env).NewStringUTF("https://toletspot.com/backend/post_justify.html");
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_toletspot_houseforrent_MainActivity_00024Companion_getSecretKey(JNIEnv *env, jobject thiz) {
    return (*env).NewStringUTF(AWS_SECRET_KEY);
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_toletspot_houseforrent_MainActivity_00024Companion_getAccessId(JNIEnv *env, jobject thiz) {
    return (*env).NewStringUTF(AWS_ACCESS_ID);
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_toletspot_houseforrent_MainActivity_00024Companion_getBucketName(JNIEnv *env,
                                                                         jobject thiz) {
    return (*env).NewStringUTF(AWS_BUCKET_NAME);
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_toletspot_houseforrent_MainActivity_00024Companion_getBaseimageUrl(JNIEnv *env,
                                                                           jobject thiz) {
    return (*env).NewStringUTF(CLOUDFRONT_URL);
}
