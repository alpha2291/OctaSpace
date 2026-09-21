package com.toletspot.houseforrent

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.toletspot.houseforrent.Start_Up.Country
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Extension property to create DataStore
//
//class DataStoreManager(private val context: Context) {
//
//    companion object {
//        private val USER_ID_KEY = intPreferencesKey("user_Id")
//    }
//
//    // Save data
//    suspend fun save_User_Id(userId: Int) {
//        context.dataStore.edit { prefs ->
//            prefs[USER_ID_KEY] = userId
//        }
//    }
//
//    // Read data as Flow
//    val get_User_Id: Flow<Int?> = context.dataStore.data.map { prefs ->
//        prefs[USER_ID_KEY]
//    }
//
//}

class MyApplication : Application() {
    companion object {
        lateinit var instance: MyApplication
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}

val android.content.Context.dataStore by preferencesDataStore(name = "app_prefs")



object AppPreferences {

    private const val PREFS_NAME = "app_prefs"
    private const val KEY_USER_ID = "user_id"
    private const val KEY_USER_TOKEN = "user_Token"
    private const val KEY_INTEREST_COMPLETED = "interest_Completed"
    private const val KEY_LOCATION_RECEIVED = "location_Received"
    private const val KEY_POST_ID = "post_Id"

    private const val KEY_ONBOARDING_COMPLETED = "onboarding_Completed"
    private const val KEY_USER_LOCATION = "user_Location"

    private const val KEY_WA_NUMBER = "wa_Number"

    private const val KEY_NUMBER = "number"
    private const val KEY_OTP = "verify_OTP"
    private const val KEY_Name = "user_Name"
    private const val KEY_VERSION = "KEY_VERSION"
    private const val KEY_TimeStamp = "KEY_TimeStamp"
    private const val KEY_SkipCount= "KEY_SkipCount"

    private const val KEY_Verify_Complete = "verify_Complete"
    private const val KEY_Profile_Image = "profile_Image"

    private const val KEY_REAL_NAME = "real_name"

    private const val KEY_PINCODE = "pincode"

    private const val KEY_COUNTRY = "country"

    private const val KEY_STATE = "state"
    private const val KEY_BIO = "KEY_BIO"
    private const val KEY_EMAIL = "email"
    private const val KEY_NOTI_ID = "noti_post"
    private const val KEY_Email_Address = "KEY_Email_Address"
    private const val KEY_LAT = "KEY_LAT"
    private const val KEY_LON = "KEY_LON"
    private const val KEY_COUNTRY_DC = "KEY_COUNTRY_DC"


    /// update popup flow

    private const val LAST_UPDATE_POPUP_DISMISS_TIME = "last_update_popup_dismiss_time"
    private const val UPDATE_POPUP_DISMISS_COUNT = "update_popup_dismiss_count"

    private val prefs: SharedPreferences by lazy {
        MyApplication.instance.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }
//    private fun getPrefs(): SharedPreferences {
//        return MyApplication.instance.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
//    }

    // ✅ Save the time when user dismisses update popup
    fun saveUpdatePopupDismissTime() {
        val currentTime = System.currentTimeMillis()
        prefs.edit().putLong(LAST_UPDATE_POPUP_DISMISS_TIME, currentTime).apply()
        println("💾 Saved update popup dismiss time: $currentTime")
    }

    // ✅ Get the last time user dismissed the popup
    fun getLastUpdatePopupDismissTime(): Long {
        return prefs.getLong(LAST_UPDATE_POPUP_DISMISS_TIME, 0L)
    }

    // ✅ Check if 5 days have passed since last dismiss
    fun shouldShowUpdatePopup(): Boolean {
        val lastDismissTime = getLastUpdatePopupDismissTime()
        if (lastDismissTime == 0L) {
            // First time showing popup
            return true
        }

        val currentTime = System.currentTimeMillis()
        val fiveDaysInMillis = 5 * 24 * 60 * 60 * 1000L // 5 days
        val timeSinceDismiss = currentTime - lastDismissTime

        val shouldShow = timeSinceDismiss >= fiveDaysInMillis
        println("⏰ Time since dismiss: ${timeSinceDismiss / (24 * 60 * 60 * 1000)}d | Should show: $shouldShow")
        return shouldShow
    }

    // ✅ Clear the saved time (after user updates)
    fun clearUpdatePopupDismissTime() {
        prefs.edit().remove(LAST_UPDATE_POPUP_DISMISS_TIME).apply()
        println("🗑️ Cleared update popup dismiss time")
    }


    private val gson = Gson()

    fun saveCountry(country: Country) {
        val json = gson.toJson(country)
        prefs.edit().putString(KEY_COUNTRY_DC, json).apply()
    }

    fun getCountry(): Country {
        val json = prefs.getString(KEY_COUNTRY_DC, null)
        return if (json != null) {
            gson.fromJson(json, Country::class.java)
        } else {
            Country() // default India
        }
    }


    /// update popup flow end


    fun save_Lat_Long(lat: String, lon: String) {
        prefs.edit()
            .putString("KEY_LAT", lat)
            .putString("KEY_LON", lon)
            .apply()
    }

    fun get_Lat_Long(): Pair<String, String> {
        val lat = prefs.getString("KEY_LAT", "") ?: ""
        val lon = prefs.getString("KEY_LON", "") ?: ""
        return Pair(lat, lon)
    }


    fun saveUserId(userId: Int) {
        prefs.edit().putInt(KEY_USER_ID, userId).apply()
    }

    fun getUserId(): Int {
        return prefs.getInt(KEY_USER_ID, -1) // -1 = default if not saved
    }
    //app update popup
    ///
    fun save_version(userId: Int) {
        prefs.edit().putInt(KEY_VERSION, userId).apply()
    }

    fun get_version(): Int {
        return prefs.getInt(KEY_VERSION, -1) // -1 = default if not saved
    } //app update popup
    ///

    fun save_timestamp(userId: Long) {
        prefs.edit().putLong(KEY_TimeStamp, userId).apply()
    }

    fun get_timestamp(): Long {
        return prefs.getLong(KEY_TimeStamp, 0L) // -1 = default if not saved
    }
    ///

    fun save_skipcount(userId: Int) {
        prefs.edit().putInt(KEY_SkipCount, userId).apply()
    }

    fun get_skipcount(): Int {
        return prefs.getInt(KEY_SkipCount, -1) // -1 = default if not saved
    }

    fun save_Verify_Complete(verify: Int) {
        prefs.edit().putInt(KEY_Verify_Complete, verify).apply()
    }

    fun get_Verify_Complete(): Int {
        return prefs.getInt(KEY_Verify_Complete, -1) // -1 = default if not saved
    }



    /// profile image

    fun save_ProfileImage(image : String){
        prefs.edit().putString(KEY_Profile_Image , image).apply()
    }

    fun get_ProfileImage() : String {
        return prefs.getString(KEY_Profile_Image , "") ?: ""
    }

    /// noti post id

    fun save_Noti_Post_Id(image : String){
        prefs.edit().putString(KEY_NOTI_ID , image).apply()
    }

    fun get_Noti_Post_Id() : String {
        return prefs.getString(KEY_NOTI_ID , "") ?: ""
    }




    /// user token

    fun save_UserToken(userToken : String){
        prefs.edit().putString(KEY_USER_TOKEN , userToken).apply()
    }

    fun get_UserToken() : String {
        return prefs.getString(KEY_USER_TOKEN , "") ?: ""
    }


    /// user email

    fun save_Email(userToken : String){
        prefs.edit().putString(KEY_EMAIL , userToken).apply()
    }

    fun get_Email() : String {
        return prefs.getString(KEY_EMAIL , "") ?: ""
    }

    /// user token

    fun save_ph_number(userToken : String){
        prefs.edit().putString(KEY_NUMBER , userToken).apply()
    }

    fun get_ph_number() : String {
        return prefs.getString(KEY_NUMBER , "") ?: ""
    }


    fun save_Interest_Completed(isCompleted: Int){
        prefs.edit().putInt(KEY_INTEREST_COMPLETED, isCompleted).apply()
    }

    fun get_Interest_Completed(): Int{
        return prefs.getInt(KEY_INTEREST_COMPLETED, 0)
    }

    fun save_Location_Received(isReceived: Int){
        prefs.edit().putInt(KEY_LOCATION_RECEIVED, isReceived).apply()
    }

    fun get_Location_Received(): Int{
        return prefs.getInt(KEY_LOCATION_RECEIVED, 0)
    }

    fun save_Post_Id(id :Int){
        prefs.edit().putInt(KEY_POST_ID , id).apply()
    }

    fun get_Post_Id() :Int {
        return prefs.getInt(KEY_POST_ID , 0)
    }

    // onboarding

    fun save_Onboarding_Completed(done : Boolean){
        prefs.edit().putBoolean(KEY_ONBOARDING_COMPLETED , done).apply()
    }

    fun get_Onboarding_Completed() : Boolean {
        return prefs.getBoolean(KEY_ONBOARDING_COMPLETED , false)
    }

    /// user location

    fun save_User_Lcation(location : String){
        prefs.edit().putString( KEY_USER_LOCATION, location).apply()
    }



    fun get_User_Location() : String {
        return prefs.getString(KEY_USER_LOCATION , "") ?: ""
    }
    /// user wa number

    fun save_User_WaNumber(location : String){
        prefs.edit().putString( KEY_WA_NUMBER, location).apply()
    }

    fun get_User_WaNumber() : String {
        return prefs.getString(KEY_WA_NUMBER , "") ?: ""
    }

    /// user wa number

    fun save_User_Verify_Otp(location : String){
        prefs.edit().putString( KEY_OTP, location).apply()
    }

    fun get_User_Verify_Otp() : String {
        return prefs.getString(KEY_OTP , "") ?: ""
    }
    /// user name

    fun save_User_Name(name : String){
        prefs.edit().putString( KEY_Name, name).apply()
    }

    fun get_User_Name() : String {
        return prefs.getString(KEY_Name , "") ?: ""
    }

    fun save_ProfileBio(image : String){
        prefs.edit().putString(KEY_BIO , image).apply()
    }

    fun get_ProfileBio() : String {
        return prefs.getString(KEY_BIO , "") ?: ""
    }

    /// userreal name

    fun save_Real_Name(name : String){
        prefs.edit().putString( KEY_REAL_NAME, name).apply()
    }

    fun get_Real_Name() : String {
        return prefs.getString(KEY_REAL_NAME , "") ?: ""
    }

    /// user Email Address

    fun save_Email_Address(email : String){
        prefs.edit().putString( KEY_Email_Address, email).apply()
    }

    fun get_Email_Address() : String {
        return prefs.getString(KEY_Email_Address , "") ?: ""
    }





    fun clearAll() {
        save_User_Name("")
        save_User_Verify_Otp("")
        save_User_WaNumber("")
        save_Onboarding_Completed(true)
        save_Post_Id(0)
        save_Location_Received(0)
        save_Interest_Completed(0)
        save_UserToken("")
        //saveUserId(-1)
        save_Verify_Complete(-1)
        save_Real_Name("")
        save_ProfileImage("")

    }

    fun clearAllDeactivate() {
        save_User_Name("")
        save_User_Verify_Otp("")
        save_User_WaNumber("")
        save_Onboarding_Completed(true)
        save_Post_Id(0)
        save_Location_Received(0)
        save_Interest_Completed(0)
        save_UserToken("")
       // saveUserId(-1)
        save_Verify_Complete(-1)
        save_Real_Name("")
        save_ProfileImage("")

    }


    //
    fun save_Pincode(isReceived: String){
        prefs.edit().putString(KEY_PINCODE, isReceived).apply()
    }

    fun get_Pincode(): String{
        return prefs.getString(KEY_PINCODE, "")?: ""
    }


    fun save_State(isReceived: String){
        prefs.edit().putString(KEY_STATE, isReceived).apply()
    }

    fun get_State(): String{
        return prefs.getString(KEY_STATE, "")?: ""
    }


    fun save_Country(isReceived: String){
        prefs.edit().putString(KEY_COUNTRY, isReceived).apply()
    }

    fun get_Country(): String{
        return prefs.getString(KEY_COUNTRY, "")?: ""
    }

}


