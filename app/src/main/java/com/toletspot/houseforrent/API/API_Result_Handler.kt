package com.toletspot.houseforrent.API


// --- Sealed class for API result handling ---
sealed class API_Result_Handling<out T> {
    data class Success<out T>(val data: T) : API_Result_Handling<T>()
    data class Error(val message: String) : API_Result_Handling<Nothing>()
    object Loading : API_Result_Handling<Nothing>()
    object NoData : API_Result_Handling<Nothing>()
    data class Deactivated(val code: String) :  API_Result_Handling<Nothing>()
}
