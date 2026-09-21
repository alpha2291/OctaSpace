package com.toletspot.houseforrent.API.StartUp_API

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.Account_Activate_Deactivate
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.Account_Settings_Contact
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.Activate_RentedOut
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.ChatConfig.ChatConfig
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.ChatNotification.Chat_Notification
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.Delete_Posts_Drafts
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.Enquiry_Decline
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.Enquiry_Decline_UndoDecline
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.Get_Notification_Settings.Get_Notification_Settings
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.Get_User_Profile
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.Location_Storing
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.Login
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.LogoutAPI
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.NewDraftFlow.New_Draft_Flow
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.Notification_Put_Settings.Put_Notification_settings
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.Particular_User_Interest.User_Interests_Particular
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.PopularSellersSearch.Popular_Sellers_Search
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.Popular_Cities_Search
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.PostForm45.PostProperty45
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.PostProperty6.PostProperty6
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.PostPropertyForm3.postproperty3
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.PostPublish.Post_Publish
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.Post_Form2_Land_Types
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.Post_Form_1
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.Post_Form_2
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.Post_Like_Dislike
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.Put_Block_User_Data
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.Put_Comment_Like_Dislike
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.Put_Comment_Reply
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.Put_Send_Enquiry
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.Put_User_Interests
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.Register
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.Report_All
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.Save_UnSafe_Property
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.SearchFilterSort.Search_Filter_Sort_Fields
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.Sold_UnSold_Property
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.Step6.Step6
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.Update_User_Profile
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.UserNameUpdate.UserName_Update
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.Verify_Otp
import com.toletspot.houseforrent.API.API_Result_Handling
import com.toletspot.houseforrent.API.API_Service
import com.toletspot.houseforrent.AppPreferences
import com.toletspot.houseforrent.Chat.FirebaseHelper
import com.toletspot.houseforrent.Chat.User
import com.toletspot.houseforrent.Custom_Assets.toast
import com.toletspot.houseforrent.GlobalSnackbar
import com.toletspot.houseforrent.Home_Screen.PostProperty_Module.CommonFormDataClass.CommonPropertyResponse
import com.toletspot.houseforrent.Home_Screen.PostProperty_Module.PostFlow
import com.toletspot.houseforrent.Home_Screen.PostProperty_Module.RentoDataclass.RentoFormPreview.RentoFormPreview
import com.toletspot.houseforrent.Home_Screen.PostProperty_Module.RentoDataclass.RentoMediaDC.Image
import com.toletspot.houseforrent.Home_Screen.PostProperty_Module.RentoDataclass.RentoMediaDC.RentoMediaApiDC
import com.toletspot.houseforrent.Home_Screen.PostProperty_Module.RentoDataclass.RentoMediaDC.Video
import com.toletspot.houseforrent.Home_Screen.PostProperty_Module.RentoDataclass.RentoPhotouploadheadings.RentoPhotoHeadings
import com.toletspot.houseforrent.Home_Screen.PostProperty_Module.RentoDataclass.RentoPriceForm.RentoPriceForm5
import com.toletspot.houseforrent.Home_Screen.Video_Module.RentoReelsDC.RentoNotInterested.RentoNotInterested
import com.toletspot.houseforrent.Home_Screen.Video_Module.RentoReelsDC.RentoRequestMediaDC.RentoRequestPhotoDC
import com.toletspot.houseforrent.R
import com.toletspot.houseforrent.constants
import com.toletspot.houseforrent.constants.Companion.Enquiry_ViewModel
import com.toletspot.houseforrent.deviceToken
import com.toletspot.houseforrent.profileChangeErrorMessage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException

import kotlinx.serialization.json.Json
import kotlin.collections.first

val jsonParser = Json {
    ignoreUnknownKeys = true
    isLenient = true
    encodeDefaults = true
}
data class InterestRequest(
    val page: Int
)

data class ImageAPIUpload(
    var url : String,
    var heading : String
)

class API_ViewModel : ViewModel()
{

    private val apiService = API_Service.create()

    fun draftNewFlowRaw(
        request: DraftNewFlowRequestRaw,
        resultCallback: (API_Result_Handling<New_Draft_Flow>) -> Unit
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            resultCallback(API_Result_Handling.Loading)
            try {
                val response = apiService.draftNewFlowRaw(request)
                if (response.isSuccessful) {
                    constants.PostProperty_ViewModel.add_previewFormData(response.body()?.data?.firstOrNull())
                    resultCallback(API_Result_Handling.Success(response.body()!!))
                } else {
                    resultCallback(API_Result_Handling.Error("Error ${response.code()}"))
                }
            } catch (e: Exception) {
                resultCallback(API_Result_Handling.Error(e.message ?: "Unexpected error"))
            }
        }
    }

    fun draftNewFlow(
        user_id: Int,
        user_post_id: Int,
        user_type: String?,
        land_type_id: Int?,
        land_categorie_id: Int?,

        country: String?,
        state: String?,
        city: String?,
        locality: String?,
        pincode: String?,
        latitude: String?,
        longitude: String?,
        property_name: String?,

        property_area: String?,
        property_area_unit: String?,
        carpet_area: String?,
        carpet_area_unit: String?,
        built_up_area: String?,
        built_up_area_unit: String?,
        super_built_up_area: String?,
        super_built_up_area_unit: String?,

        area_length: String?,
        area_length_unit: String?,
        area_width: String?,
        area_width_unit: String?,

        facade_width: String?,
        facade_width_unit: String?,
        facade_height: String?,
        facade_height_unit: String?,

        bhk_type: String?,
        property_facing: String?,
        total_floor: String?,
        rent_floor_no: String?,

        preferred_tenants: String?,
        availability_from: String?,
        agreement_type: String?,
        food_preferences: String?,
        pets_allowed: String?,
        property_condition: String?,

        furnishing_status: String?,
        boundary_wall: String?,
        parking_available: String?,

        amenities: String?,
        property_highlights: String?,

        no_of_bedrooms: String?,
        no_of_Bathrooms: String?,
        no_of_Balconies: String?,
        no_of_open_sides: String?,
        no_of_Staircases: String?,
        other_rooms: String?,

        no_of_cabins: String?,
        no_of_meeting_rooms: String?,
        min_of_seats: String?,
        max_of_seats: String?,
        conference_room: String?,

        reception_area: String?,
        pantry: String?,
        pantry_size: String?,
        pantry_size_unit: String?,
        central_ac: String?,
        oxygen_duct: String?,
        ups: String?,
        fire_safety_measures: String?,
        lifts: String?,

        noc_certified: String?,
        occupancy_certificate: String?,
        washroom_details: String?,
        does_local_authority: String?,
        suitable_business_type: String?,

        is_this_property_for_rent_or_lease: String?,
        rent: String?,
        rent_negotiable: String?,
        deposit_amount_month_of_rents: String?,
        deposit_amount_month_of_rents_type: String?,
        total_deposit: String?,
        duration_of_agreement: String?,
        duration_of_agreement_type: String?,
        lock_in_period: String?,
        lock_in_period_type: String?,
        notice_period: String?,
        lease_duration_in_years: String?,
        lease_amount: String?,
        lease_negotiable: String?,

        post_type: String?,
        video: List<ImageAPIUpload>?,
        images: List<ImageAPIUpload>?,
        thumbnail: String?,
        property_for_rent_or_lease: String?,

        draft: String?,
        preview_model: String?,

        resultCallback: (API_Result_Handling<New_Draft_Flow>) -> Unit
    )
    {
        viewModelScope.launch(Dispatchers.IO) {
            resultCallback(API_Result_Handling.Loading)

            try {
                fun String?.toRB(): RequestBody? =
                    if (this.isNullOrEmpty()) null else this.toRequestBody("text/plain".toMediaTypeOrNull())

                fun Int?.toRB(): RequestBody? =
                    this?.toString()?.toRequestBody("text/plain".toMediaTypeOrNull())

                val gson = Gson()

                val videoJson = gson.toJson(video ?: emptyList<Video>())
                val imageJson = gson.toJson(images ?: emptyList<Image>())

                val videoBody = videoJson.toRequestBody("application/json".toMediaTypeOrNull())
                val imageBody = imageJson.toRequestBody("application/json".toMediaTypeOrNull())

                val response = apiService.draftNewFlow(
                    user_id = user_id.toRB()!!,
                    user_post_id = user_post_id.toRB()!!,
                    user_type = user_type.toRB(),
                    land_type_id = land_type_id.toRB(),
                    land_categorie_id = land_categorie_id.toRB(),

                    country = country.toRB(),
                    state = state.toRB(),
                    city = city.toRB(),
                    locality = locality.toRB(),
                    pincode = pincode.toRB(),
                    latitude = latitude.toRB(),
                    longitude = longitude.toRB(),
                    property_name = property_name.toRB(),
                    property_for_rent_or_lease = property_for_rent_or_lease.toRB(),

                    property_area = property_area.toRB(),
                    property_area_unit = property_area_unit.toRB(),
                    carpet_area = carpet_area.toRB(),
                    carpet_area_unit = carpet_area_unit.toRB(),
                    built_up_area = built_up_area.toRB(),
                    built_up_area_unit = built_up_area_unit.toRB(),
                    super_built_up_area = super_built_up_area.toRB(),
                    super_built_up_area_unit = super_built_up_area_unit.toRB(),

                    area_length = area_length.toRB(),
                    area_length_unit = area_length_unit.toRB(),
                    area_width = area_width.toRB(),
                    area_width_unit = area_width_unit.toRB(),

                    facade_width = facade_width.toRB(),
                    facade_width_unit = facade_width_unit.toRB(),
                    facade_height = facade_height.toRB(),
                    facade_height_unit = facade_height_unit.toRB(),

                    bhk_type = bhk_type.toRB(),
                    property_facing = property_facing.toRB(),
                    total_floor = total_floor.toRB(),
                    rent_floor_no = rent_floor_no.toRB(),

                    preferred_tenants = preferred_tenants.toRB(),
                    availability_from = availability_from.toRB(),
                    agreement_type = agreement_type.toRB(),
                    food_preferences = food_preferences.toRB(),
                    pets_allowed = pets_allowed.toRB(),
                    property_condition = property_condition.toRB(),

                    furnishing_status = furnishing_status.toRB(),
                    boundary_wall = boundary_wall.toRB(),
                    parking_available = parking_available.toRB(),

                    amenities = amenities.toRB(),
                    property_highlights = property_highlights.toRB(),

                    no_of_bedrooms = no_of_bedrooms.toRB(),
                    no_of_Bathrooms = no_of_Bathrooms.toRB(),
                    no_of_Balconies = no_of_Balconies.toRB(),
                    no_of_open_sides = no_of_open_sides.toRB(),
                    no_of_Staircases = no_of_Staircases.toRB(),
                    other_rooms = other_rooms.toRB(),

                    no_of_cabins = no_of_cabins.toRB(),
                    no_of_meeting_rooms = no_of_meeting_rooms.toRB(),
                    min_of_seats = min_of_seats.toRB(),
                    max_of_seats = max_of_seats.toRB(),
                    conference_room = conference_room.toRB(),

                    reception_area = reception_area.toRB(),
                    pantry = pantry.toRB(),
                    pantry_size = pantry_size.toRB(),
                    pantry_size_unit = pantry_size_unit.toRB(),
                    central_ac = central_ac.toRB(),
                    oxygen_duct = oxygen_duct.toRB(),
                    ups = ups.toRB(),
                    fire_safety_measures = fire_safety_measures.toRB(),
                    lifts = lifts.toRB(),

                    noc_certified = noc_certified.toRB(),
                    occupancy_certificate = occupancy_certificate.toRB(),
                    washroom_details = washroom_details.toRB(),
                    does_local_authority = does_local_authority.toRB(),
                    suitable_business_type = suitable_business_type.toRB(),

                    is_this_property_for_rent_or_lease = is_this_property_for_rent_or_lease.toRB(),
                    rent = rent.toRB(),
                    rent_negotiable = rent_negotiable.toRB(),
                    deposit_amount_month_of_rents = deposit_amount_month_of_rents.toRB(),
                    deposit_amount_month_of_rents_type = deposit_amount_month_of_rents_type.toRB(),
                    total_deposit = total_deposit.toRB(),
                    duration_of_agreement = duration_of_agreement.toRB(),
                    duration_of_agreement_type = duration_of_agreement_type.toRB(),
                    lock_in_period = lock_in_period.toRB(),
                    lock_in_period_type = lock_in_period_type.toRB(),
                    notice_period = notice_period.toRB(),
                    lease_duration_in_years = lease_duration_in_years.toRB(),
                    lease_amount = lease_amount.toRB(),
                    lease_negotiable = lease_negotiable.toRB(),

                    post_type = post_type.toRB(),
                    video_urls = videoBody,
                    image_urls = imageBody,
                    thumbnail = thumbnail.toRB(),

                    draft = draft.toRB(),
                    preview_model = preview_model.toRB()
                )

                if (response.isSuccessful) {
                    resultCallback(API_Result_Handling.Success(response.body()!!))
                } else {
                    resultCallback(API_Result_Handling.Error("Error ${response.code()}"))
                }

            } catch (e: Exception) {
                resultCallback(API_Result_Handling.Error(e.message ?: "Unexpected error"))
            }
        }
    }

    fun user_Register(
        name: String,
        phone_num_cc: String,
        phone_num: String,
        device_id: String,
        device_type: String,
        device_token: String,
        resultCallback: (API_Result_Handling<Register>) -> Unit) {
        viewModelScope.launch {

            resultCallback(API_Result_Handling.Loading)

            val jsonObject = JSONObject()
            jsonObject.put("name", name)
            jsonObject.put("phone_num_cc", phone_num_cc)
            jsonObject.put("phone_num", phone_num)
            jsonObject.put("device_id", device_id)
            jsonObject.put("device_type", device_type)
            jsonObject.put("device_token", device_token)

            val requestBody: RequestBody = jsonObject.toString()
                .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

            try {
                val response = apiService.user_Register(requestBody)
                val responseString = response.string()

                if (responseString.isEmpty()){
                    resultCallback(API_Result_Handling.NoData)
                }
                else {

                    val register_Response = Gson().fromJson(responseString, Register::class.java)

                    if (register_Response.result == "0") {
                        resultCallback(API_Result_Handling.Error(register_Response.error))
                    } else {

                        AppPreferences.saveUserId(register_Response.data.first().user_id)

                        constants.Start_Up_ViewModel.put_OTP_Response(register_Response.data.first().otp)
                        AppPreferences.save_User_Verify_Otp(register_Response.data.first().otp)
                        resultCallback(API_Result_Handling.Success(register_Response))
                    }
                }

            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                val errorMessage = try {
                    val errorJson = Gson().fromJson(errorBody, Register::class.java)
                    errorJson.error.ifEmpty { "HTTP ${e.code()} ${e.message()}" }
                } catch (ex: Exception) {
                    "HTTP ${e.code()} ${e.message()}"
                }

                resultCallback(API_Result_Handling.Error(errorMessage))
            } catch (e: IOException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "NetWork Error"))
            } catch (e: JsonSyntaxException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "Parsing Error"))
            } catch (e: Exception) {
                resultCallback(API_Result_Handling.Error(e.message ?: "Unexpected Error"))
            }
        }
    }

    fun user_Login(
        phone_num_cc: String,
        phone_num: String,
        device_id: String,
        device_type: String,
        device_token: String = deviceToken,
        resultCallback: (API_Result_Handling<Login>) -> Unit) {
        viewModelScope.launch {

            resultCallback(API_Result_Handling.Loading)

            val jsonObject = JSONObject()
            jsonObject.put("phone_num_cc", phone_num_cc)
            jsonObject.put("phone_num", phone_num)
            jsonObject.put("device_id", device_id)
            jsonObject.put("device_type", device_type)
            jsonObject.put("device_token", device_token)

            val requestBody: RequestBody = jsonObject.toString()
                .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

            try {
                val response = apiService.user_Login(requestBody)
                val responseString = response.string()

                if (responseString.isEmpty()){
                    resultCallback(API_Result_Handling.NoData)
                }
                else {

                    val login_Response = Gson().fromJson(responseString, Login::class.java)

                    if (login_Response.result == "2" || login_Response.result == "3") {
                        resultCallback(API_Result_Handling.Deactivated(login_Response.result))
                    } else {

                        if (login_Response.data.isEmpty()){
                            resultCallback(API_Result_Handling.Error(login_Response.error))
                        }

                            AppPreferences.saveUserId(login_Response.data.first().user_id)
                            constants.Start_Up_ViewModel.put_OTP_Response(login_Response.data.first().otp)

                            AppPreferences.save_User_Verify_Otp(login_Response.data.first().otp)
                            resultCallback(API_Result_Handling.Success(login_Response))

                    }
                }

            } catch (e: HttpException) {

                val errorBody = e.response()?.errorBody()?.string()
                val errorMessage = try {
                    val errorJson = Gson().fromJson(errorBody, Login::class.java)
                    errorJson.error.ifEmpty { "HTTP ${e.code()} ${e.message()}" }
                } catch (ex: Exception) {
                    "HTTP ${e.code()} ${e.message()}"
                }

                resultCallback(API_Result_Handling.Error(errorMessage))
            } catch (e: IOException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "NetWork Error"))
            } catch (e: JsonSyntaxException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "Parsing Error"))
            }
            catch (e: Exception) {

            }
        }
    }

    fun verify_OTP(
        user_id :Int,
        phone_num_cc: String,
        phone_num: String,
        whatsapp_num_cc: String,
        whatsapp_num: String,
        email: String,
        otp: String,
        device_id: String,
        device_type: String,
        device_token: String,
        resultCallback: (API_Result_Handling<Verify_Otp>) -> Unit) {
        viewModelScope.launch {

            resultCallback(API_Result_Handling.Loading)

            val jsonObject = JSONObject()
            jsonObject.put("phone_num_cc", phone_num_cc)
            jsonObject.put("user_id", user_id)
            jsonObject.put("phone_num", phone_num)
            jsonObject.put("whatsapp_num_cc", whatsapp_num_cc)
            jsonObject.put("whatsapp_num", whatsapp_num)
            jsonObject.put("email", email)
            jsonObject.put("otp", otp)
            jsonObject.put("device_id", device_id)
            jsonObject.put("device_type", device_type)
            jsonObject.put("device_token", device_token)

            val requestBody: RequestBody = jsonObject.toString()
                .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

            try {
                val response = apiService.verify_OTP(requestBody)
                val responseString = response.string()

                if (responseString.isEmpty()){
                    resultCallback(API_Result_Handling.NoData)
                }

                    val verify_Response = Gson().fromJson(responseString, Verify_Otp::class.java)

                if (verify_Response.result == "0") {

                    constants.Start_Up_ViewModel.is_Error_OTP()
                    resultCallback(API_Result_Handling.Error(verify_Response.error))
                } else {

                    AppPreferences.saveUserId(verify_Response.data.first().user_id)

                    AppPreferences.save_UserToken(verify_Response.data.first().token)

                    AppPreferences.save_Interest_Completed(verify_Response.data.first().interest_page)

                    AppPreferences.save_Location_Received(verify_Response.data.first().location_page)

                    AppPreferences.save_User_Name(verify_Response.data.first().username)
                    AppPreferences.save_Real_Name(verify_Response.data.first().name)

                    AppPreferences.save_Pincode(verify_Response.data.first().pincode)
                    AppPreferences.save_Country(verify_Response.data.first().country)
                    AppPreferences.save_State(verify_Response.data.first().state)
                    AppPreferences.save_User_Lcation(verify_Response.data.first().city)

                    constants.Start_Up_ViewModel.set_Pincode(verify_Response.data.first().pincode)
                    constants.Start_Up_ViewModel.set_City(verify_Response.data.first().city)
                    constants.Start_Up_ViewModel.set_State(verify_Response.data.first().state)
                    constants.Start_Up_ViewModel.set_Country(verify_Response.data.first().country)

                    FirebaseHelper.addOrUpdateUser(
                        User(
                          userId = verify_Response.data.first().user_id.toString(),
                            userName = verify_Response.data.first().username,
                            name = verify_Response.data.first().name,
                            mobileNumber = verify_Response.data.first().phone_num,
                            mobileNumberCC = verify_Response.data.first().phone_num_cc,
                            deviceToken = deviceToken,
                            isOnline = true,
                            isAccountDeleted = false,
                            lastSeen = System.currentTimeMillis(),
                            whatsappNumber = verify_Response.data.first().whatsapp_num,
                            whatsappNumberCC = verify_Response.data.first().whatsapp_num_cc,
                            state = verify_Response.data.first().state,
                            city = verify_Response.data.first().city,
                            email = verify_Response.data.first().email
                        ),
                        onComplete = {
                        }
                    )

                    resultCallback(API_Result_Handling.Success(verify_Response))
                }

            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                val errorMessage = try {
                    val errorJson = Gson().fromJson(errorBody, Verify_Otp::class.java)
                    errorJson.error.ifEmpty { "HTTP ${e.code()} ${e.message()}" }
                } catch (ex: Exception) {
                    "HTTP ${e.code()} ${e.message()}"
                }

                constants.Start_Up_ViewModel.is_Error_OTP()
                resultCallback(API_Result_Handling.Error(errorMessage))
            } catch (e: IOException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "NetWork Error"))
            } catch (e: JsonSyntaxException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "Parsing Error"))
            } catch (e: Exception) {
                resultCallback(API_Result_Handling.Error(e.message ?: "Unexpected Error"))
            }
        }
    }

    fun put_User_Interests(
        user_id: Int,
        user_interest: String,
        resultCallback: (API_Result_Handling<Put_User_Interests>) -> Unit) {
        viewModelScope.launch {

            resultCallback(API_Result_Handling.Loading)

            val jsonObject = JSONObject()
            jsonObject.put("user_id", user_id)
            jsonObject.put("user_interest", user_interest)

            val requestBody: RequestBody = jsonObject.toString()
                .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

            try {
                val response = apiService.put_User_Interests(requestBody)
                val responseString = response.string()

                if (responseString.isEmpty()){
                    resultCallback(API_Result_Handling.NoData)
                }
                else {

                    val put_user_interest_response = Gson().fromJson(responseString, Put_User_Interests::class.java)

                    resultCallback(API_Result_Handling.Success(put_user_interest_response))
                }

            } catch (e: HttpException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "HTTP Error"))
            } catch (e: IOException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "NetWork Error"))
            } catch (e: JsonSyntaxException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "Parsing Error"))
            } catch (e: Exception) {
                resultCallback(API_Result_Handling.Error(e.message ?: "Unexpected Error"))
            }
        }
    }

    fun get_User_Interest_Particular(
        user_id: Int,
        resultCallback: (API_Result_Handling<User_Interests_Particular>) -> Unit) {
        viewModelScope.launch {

            resultCallback(API_Result_Handling.Loading)

            val jsonObject = JSONObject()
            jsonObject.put("user_id", user_id)

            val requestBody: RequestBody = jsonObject.toString()
                .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

            try {
                val response = apiService.get_User_Interest_Particular(requestBody)
                val responseString = response.string()

                if (responseString.isEmpty()){
                    resultCallback(API_Result_Handling.NoData)
                }
                else {

                    val put_user_interest_response = Gson().fromJson(responseString, User_Interests_Particular::class.java)

                    constants.Profile_ViewModel.add_user_Interests_Particular(put_user_interest_response.data)
                    resultCallback(API_Result_Handling.Success(put_user_interest_response))
                }

            } catch (e: HttpException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "HTTP Error"))
            } catch (e: IOException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "NetWork Error"))
            } catch (e: JsonSyntaxException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "Parsing Error"))
            } catch (e: Exception) {
                resultCallback(API_Result_Handling.Error(e.message ?: "Unexpected Error"))
            }
        }
    }

    fun put_User_Location(
        user_id: Int,
        country: String,
        state: String,
        cities: String,
        pincode: String,
        latitude: String,
        longitude: String,
        resultCallback: (API_Result_Handling<Location_Storing>) -> Unit) {
        viewModelScope.launch {

            resultCallback(API_Result_Handling.Loading)

            val jsonObject = JSONObject()
            jsonObject.put("user_id", user_id)
            jsonObject.put("country", country)
            jsonObject.put("state", state)
            jsonObject.put("cities", cities)
            jsonObject.put("pincode", pincode)
            jsonObject.put("latitude", latitude)
            jsonObject.put("longitude", longitude)

            val requestBody: RequestBody = jsonObject.toString()
                .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

            try {
                val response = apiService.put_User_Location(requestBody)
                val responseString = response.string()

                if (responseString.isEmpty()){
                    resultCallback(API_Result_Handling.NoData)
                }
                else {

                    val location_Storing = Gson().fromJson(responseString, Location_Storing::class.java)

                    resultCallback(API_Result_Handling.Success(location_Storing))
                }

            } catch (e: HttpException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "HTTP Error"))
            } catch (e: IOException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "NetWork Error"))
            } catch (e: JsonSyntaxException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "Parsing Error"))
            } catch (e: Exception) {
                resultCallback(API_Result_Handling.Error(e.message ?: "Unexpected Error"))
            }
        }
    }

    fun contact(
        user_id: String,
        whatsapp_num: String,
        email: String,
        resultCallback: (API_Result_Handling<Verify_Otp>) -> Unit) {
        viewModelScope.launch {

            resultCallback(API_Result_Handling.Loading)

            val jsonObject = JSONObject()
            jsonObject.put("user_id", user_id)
            jsonObject.put("whatsapp_num", whatsapp_num)
            jsonObject.put("email", email)

            val requestBody: RequestBody = jsonObject.toString()
                .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

            try {
                val response = apiService.verify_OTP(requestBody)
                val responseString = response.string()

                if (responseString.isEmpty()){
                    resultCallback(API_Result_Handling.NoData)
                }
                else {

                    val verify_Response = Gson().fromJson(responseString, Verify_Otp::class.java)

                    resultCallback(API_Result_Handling.Success(verify_Response))
                }

            } catch (e: HttpException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "HTTP Error"))
            } catch (e: IOException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "NetWork Error"))
            } catch (e: JsonSyntaxException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "Parsing Error"))
            } catch (e: Exception) {
                resultCallback(API_Result_Handling.Error(e.message ?: "Unexpected Error"))
            }
        }
    }

    fun follow_Unfollow(
        user_id: Int,
        following_id: Int,
        status: Int ,
        resultCallback: (API_Result_Handling<Verify_Otp>) -> Unit) {
        viewModelScope.launch {

            resultCallback(API_Result_Handling.Loading)

            val jsonObject = JSONObject()
            jsonObject.put("user_id", user_id)
            jsonObject.put("following_id", following_id)
            jsonObject.put("status", status)

            val requestBody: RequestBody = jsonObject.toString()
                .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

            try {
                val response = apiService.follow_Unfollow_Delete_Users(requestBody)
                val responseString = response.string()

                if (responseString.isEmpty()){
                    resultCallback(API_Result_Handling.NoData)
                }
                else {

                    val flw_unflw_users_response = Gson().fromJson(responseString, Verify_Otp::class.java)

                    resultCallback(API_Result_Handling.Success(flw_unflw_users_response))
                }

            } catch (e: HttpException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "HTTP Error"))
            } catch (e: IOException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "NetWork Error"))
            } catch (e: JsonSyntaxException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "Parsing Error"))
            } catch (e: Exception) {
                resultCallback(API_Result_Handling.Error(e.message ?: "Unexpected Error"))
            }
        }
    }

    var isLoading by mutableStateOf(true)

    var errorMessage by mutableStateOf<String?>(null)

    var currentPage by mutableStateOf(1)

    var totalPages by mutableStateOf(1)

    fun loadCategories(page: Int) {

        if (

            page > totalPages) return

        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                    val response = apiService.get_User_Interests(InterestRequest(page))
                if (response.isSuccessful) {
                    response.body()?.let { body ->
                        if (page == 1) {
                            constants.Start_Up_ViewModel.setInterests(body.data)
                        } else {
                            constants.Start_Up_ViewModel.setInterests(constants.Start_Up_ViewModel.user_Interests.value + body.data)
                        }
                        currentPage = page
                        totalPages = body.totalPages
                    }
                } else {
                    errorMessage = "Error ${response.code()}: ${response.message()}"
                }
            } catch (e: Exception) {
                errorMessage = e.localizedMessage ?: "Unknown error"
            } finally {
                isLoading = false
            }
        }
    }

    var isLoading_FF by mutableStateOf(true)

    var errorMessage_FF by mutableStateOf<String?>(null)

    var currentPage_FF by mutableStateOf(1)

    var totalPages_FF by mutableStateOf(1)

    fun load_Profile_FF_List(user_id: Int , others_id : String, status: Int, page: Int) {
        if (

            page > totalPages_FF) return
        viewModelScope.launch {
            isLoading_FF = true
            errorMessage_FF = null
            try {
                val response = apiService.get_profile_FF_List(
                    API_Interface.FollowRequest(user_id, others_id , status, page)
                )
                if (response.isSuccessful) {
                    response.body()?.let { body ->

                        val targetUserId = if (others_id.isEmpty()) user_id else others_id.toInt()
                        val targetTab = status - 1

                        if (page == 1) {

                            constants.Profile_ViewModel.setUserFFList(targetUserId, targetTab, body.data)
                        } else {

                            constants.Profile_ViewModel.appendUserFFList(targetUserId, targetTab, body.data)
                        }

                        currentPage_FF = page
                        totalPages_FF = body.totalPages
                    }
                } else {

                    errorMessage_FF = "Error ${response.code()}: ${response.message()}"
                }
            } catch (e: Exception) {

                errorMessage_FF = constants.activity.getString(R.string.no_Internet)
            } finally {
                isLoading_FF = false
            }
        }
    }

    var isLoading_Reels by mutableStateOf(true)
    var errorMessage_Reels by mutableStateOf<String?>(null)
    var currentPage_Reels by mutableStateOf(1)
    var totalPages_Reels by mutableStateOf(1)
    var nextPage_Reels by mutableStateOf(0)

    var result_Reels by mutableStateOf("")

    fun load_Reels(user_id: Int, user_post_id : String,page: Int) {

        if (
            page > totalPages_Reels || page <= 0) return

        viewModelScope.launch {
            isLoading_Reels = true
            errorMessage_Reels = null
            try {
                val response = apiService.get_Reels(
                    ReelsRequest(user_id, user_post_id, page)
                )

                if (response.isSuccessful) {

                    response.body()?.let { body ->
                        result_Reels =  body.result

                            if (page == 1) {

                                constants.Reels_ViewModel.setReelsContent(body.data)

                            } else {

                                val currentList = constants.Reels_ViewModel.videos.value
                                val newItems = body.data.filter { new ->
                                    currentList.none { it.user_post_id == new.user_post_id }
                                }
                                constants.Reels_ViewModel.setReelsContent(currentList + newItems)

                            }


                            currentPage_Reels = page
                            totalPages_Reels = body.totalPages
                            nextPage_Reels = body.nxtpage
                            isLoading_Reels = false
                    }
                } else {
                    errorMessage_Reels = "Error ${response.code()}: ${response.message()}"
                }
            } catch (e: Exception) {
                errorMessage_Reels = "${constants.activity.getString(R.string.no_Internet)} : ${e.localizedMessage}"
            } finally {
                isLoading_Reels = false
            }
        }
    }

    var isLoading_CML by mutableStateOf(true)

    var errorMessage_CML by mutableStateOf<String?>(null)

    var currentPage_CML by mutableStateOf(1)

    var totalPages_CML by mutableStateOf(1)

    var nextPage_CML by mutableStateOf(0)

    var result_CML by mutableStateOf("")

    fun load_ChatMainList(user_id: Int, filter_type : String, search_text : String ,page: Int) {

        if (
            page > totalPages_CML || page <= 0) return

        viewModelScope.launch {
            isLoading_CML = true
            errorMessage_CML = null
            try {
                val response = apiService.get_Chat_Main_List(
                    ChatMainList(user_id, filter_type, search_text, page)
                )

                if (response.isSuccessful) {

                    response.body()?.let { body ->
                        result_CML =  body.result

                        if (page == 1) {

                            Enquiry_ViewModel.set_ChatMainList_Content(body.data)

                        } else {

                            val currentList = Enquiry_ViewModel.chatMainList.value

                            Enquiry_ViewModel.set_ChatMainList_Content( currentList + body.data)

                        }


                        currentPage_CML = page
                        totalPages_CML = body.totalPages
                        nextPage_CML = body.nxtpage
                        isLoading_CML = false
                    }
                } else {
                    errorMessage_CML = "Error ${response.code()}: ${response.message()}"
                }
            } catch (e: HttpException) {
                errorMessage_CML = "HTTP error ${e.code()}: ${e.message()}"
                Log.e("ChatMainListError", "HTTP exception", e)
            } catch (e: IOException) {
                errorMessage_CML = "Network error: ${e.message}"
                Log.e("ChatMainListError", "Network exception", e)
            } catch (e: Exception) {
                errorMessage_CML = "Unexpected error: ${e.localizedMessage ?: e.message}"
                Log.e("ChatMainListError", "Unknown exception", e)
                e.printStackTrace()
            }
            finally {
                isLoading_CML = false
            }
        }
    }

    fun reload_CML_API(user_id: Int, filter_type : String, search_text : String ,page: Int){
        Log.d("CHAT MAIL LIST RELOAD" , "WORKING")
        isLoading_CML = true
        errorMessage_CML = null
        totalPages_CML = 1
        load_ChatMainList(user_id ,filter_type , search_text = search_text ,page)
    }

    var isLoading_MComments by mutableStateOf(true)
    var errorMessage_MComments by mutableStateOf<String?>(null)
    var currentPage_MComments by mutableStateOf(1)
    var totalPages_MComments by mutableStateOf(1)
    var nextPage_MComments by mutableStateOf(0)

    fun load_Reels_MComments(user_id: Int, user_post_id: Int,  page: Int) {

        if (

            page > totalPages_MComments || page <= 0) return

        viewModelScope.launch {
            isLoading_MComments = true
            errorMessage_MComments = null
            try {
                val response = apiService.get_Main_Comment(user_id,user_post_id, page)
                if (response.isSuccessful) {
                    response.body()?.let { body ->
                        if (page == 1) {

                            constants.Reels_ViewModel.set_MComments_Content(body.data)
                        } else {

                            val currentList = constants.Reels_ViewModel.main_Comments.value
                            val newItems = body.data.filter { new ->
                                currentList.none { it.comment_id == new.comment_id }
                            }
                            constants.Reels_ViewModel. set_MComments_Content(currentList + newItems)
                        }


                        currentPage_MComments = page
                        totalPages_MComments = body.totalPages
                        nextPage_MComments = body.nxtpage
                    }
                } else {
                    errorMessage_MComments = "Error ${response.code()}: ${response.message()}"
                }
            } catch (e: Exception) {
                errorMessage_MComments = "${constants.activity.getString(R.string.no_Internet) }: ${e.localizedMessage}"
            } finally {
                isLoading_MComments = false
            }
        }
    }

    var currentLoadingCommentId by mutableStateOf<Int?>(null)
    var isLoading_RComments by mutableStateOf(false)
    var errorMessage_RComments by mutableStateOf<String?>(null)
    var currentPage_RComments by mutableStateOf(1)
    var totalPages_RComments by mutableStateOf(1)
    var nextPage_RComments by mutableStateOf(0)

    fun load_Reels_RComments(user_id: Int, user_post_id: Int, comment_id: Int, page: Int) {

        if (isLoading_RComments && currentLoadingCommentId == comment_id) {
            return
        }

        if (page > totalPages_RComments || page <= 0) return

        viewModelScope.launch {
            isLoading_RComments = true
            currentLoadingCommentId = comment_id
            errorMessage_RComments = null

            try {
                val response = apiService.get_Reply_Comment(user_id, user_post_id, comment_id, page)
                if (response.isSuccessful) {
                    response.body()?.let { body ->

                        if (page == 1) {
                            constants.Reels_ViewModel.set_RComments_Content(comment_id, body.data, append = false)
                        } else {
                            constants.Reels_ViewModel.set_RComments_Content(comment_id, body.data, append = true)
                        }

                        currentPage_RComments = page
                        totalPages_RComments = body.totalPages
                        nextPage_RComments = body.nxtpage
                    }
                } else {
                    GlobalSnackbar.show("Something went wrong")
                    errorMessage_RComments = "Error ${response.code()}: ${response.message()}"
                    toast("Something went wrong while loading replies")
                }
            } catch (e: Exception) {
                GlobalSnackbar.show("Something went wrong")
                errorMessage_RComments = "${constants.activity.getString(R.string.no_Internet)} : ${e.localizedMessage}"
                toast("Something went wrong while loading replies")
            } finally {
                isLoading_RComments = false
                currentLoadingCommentId = null
            }
        }
    }

    fun resetReplyPagination() {
        currentPage_RComments = 0
        totalPages_RComments = 1
        nextPage_RComments = 0
        errorMessage_RComments = null
    }

    fun update_User_Profile(
        user_id: Int,
        name: String,
        bio: String,
        profile_image: String,
        resultCallback: (API_Result_Handling<Update_User_Profile>) -> Unit) {
        viewModelScope.launch {

            resultCallback(API_Result_Handling.Loading)

            val jsonObject = JSONObject()
            jsonObject.put("user_id", user_id)
            jsonObject.put("name", name)
            jsonObject.put("bio", bio)
            jsonObject.put("profile_image", profile_image)

            val requestBody: RequestBody = jsonObject.toString()
                .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

            try {
                val response = apiService.update_User_Profile(requestBody)
                val responseString = response.string()

                if (responseString.isEmpty()){
                    resultCallback(API_Result_Handling.NoData)
                }
                else {

                    val update_profile = Gson().fromJson(responseString, Update_User_Profile::class.java)

                    resultCallback(API_Result_Handling.Success(update_profile))
                }

            } catch (e: HttpException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "HTTP Error"))
            } catch (e: IOException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "NetWork Error"))
            } catch (e: JsonSyntaxException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "Parsing Error"))
            } catch (e: Exception) {
                resultCallback(API_Result_Handling.Error(e.message ?: "Unexpected Error"))
            }
        }
    }

    fun put_Comment111(
        user_id: Int,
        user_post_id: Int,
        status: String,
        comment_id: Int,
        comment: String,
        replies_comment_id: Int,
        mention_id : Int,
        resultCallback: (API_Result_Handling<Put_Comment_Reply>) -> Unit
    )
    {
        viewModelScope.launch {
            resultCallback(API_Result_Handling.Loading)

            try {
                val response = apiService.put_Comment(
                    user_id = user_id,
                    user_post_id = user_post_id,
                    status = status,
                    comment = comment,
                    comment_id = comment_id,
                    replies_comment_id = replies_comment_id,
                    mention_id = mention_id
                )

                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        resultCallback(API_Result_Handling.Success(body))
                        constants.Reels_ViewModel.add_new_Comment(body.data.first())
                    } else {
                        resultCallback(API_Result_Handling.NoData)
                    }
                } else {
                    resultCallback(API_Result_Handling.Error("Error Code: ${response.code()}"))
                }

            } catch (e: HttpException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "HTTP Error"))
            } catch (e: IOException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "Network Error"))
            } catch (e: Exception) {
                resultCallback(API_Result_Handling.Error(e.message ?: "Unexpected Error"))
            }
        }
    }

    fun put_Comment(
        user_id: Int,
        user_post_id: Int,
        status: String,
        comment_id: Int,
        comment: String,
        replies_comment_id: Int,
        mention_id :Int,
        resultCallback: (API_Result_Handling<Put_Comment_Reply>) -> Unit) {
        viewModelScope.launch {

            resultCallback(API_Result_Handling.Loading)

            val jsonObject = JSONObject()
            jsonObject.put("user_id", user_id)
            jsonObject.put("user_post_id", user_post_id)
            jsonObject.put("status", status)
            jsonObject.put("comment_id", comment_id)
            jsonObject.put("comment", comment)
            jsonObject.put("replies_comment_id", replies_comment_id)
            jsonObject.put("mention_id", mention_id)

            val requestBody: RequestBody = jsonObject.toString()
                .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

            try {
                val response = apiService.post_Comment_Reply(requestBody)
                val responseString = response.string()

                if (responseString.isEmpty()){
                    resultCallback(API_Result_Handling.NoData)
                }
                else {

                    val block_user_response = Gson().fromJson(responseString, Put_Comment_Reply::class.java)

                    if (block_user_response.data.isNotEmpty()) {

                       constants.Reels_ViewModel.add_new_Comment(block_user_response.data.first())

                        resultCallback(API_Result_Handling.Success(block_user_response))
                    } else {
                        resultCallback(API_Result_Handling.NoData)
                    }

                }
            } catch (e: HttpException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "HTTP Error"))
            } catch (e: IOException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "NetWork Error"))
            } catch (e: JsonSyntaxException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "Parsing Error"))
            } catch (e: Exception) {
                resultCallback(API_Result_Handling.Error(e.message ?: "Unexpected Error"))
            }
        }
    }

    fun get_User_Profile(
        user_id: Int,
        others_id:Int,
        resultCallback: (API_Result_Handling<Get_User_Profile>) -> Unit) {
        viewModelScope.launch {

            resultCallback(API_Result_Handling.Loading)

            val jsonObject = JSONObject()
            jsonObject.put("user_id", user_id)
            jsonObject.put("others_id", others_id)

            val requestBody: RequestBody = jsonObject.toString()
                .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

            try {
                val response = apiService.get_User_Profile(requestBody)
                val responseString = response.string()

                    val profile_Response = Gson().fromJson(responseString, Get_User_Profile::class.java)

                    if (profile_Response.result == "2" || profile_Response.result == "3") {
                        resultCallback(API_Result_Handling.Deactivated(profile_Response.result))

                    }
                    else {
                        if (profile_Response.data.isEmpty()) {
                            resultCallback(API_Result_Handling.NoData)
                        }
                        else {

                            if (profile_Response.data.first().user_id == AppPreferences.getUserId()) {
                                constants.Profile_ViewModel.save_new_name_edit_profile(profile_Response.data.first().username)
                                constants.Profile_ViewModel.save_New_Realname(profile_Response.data.first().name)
                                constants.Profile_ViewModel.save_new_Bio_Content(profile_Response.data.first().bio)
                                AppPreferences.save_User_Name(profile_Response.data.first().username)
                                AppPreferences.save_ProfileBio(profile_Response.data.first().bio)
                            }
                            constants.Profile_ViewModel.put_Followers_Count_BGAPIC(profile_Response.data.first().followers)
                            constants.Profile_ViewModel.put_Following_Count_BGAPIC(profile_Response.data.first().following)

                            if (constants.Profile_ViewModel.selected_Profile_Id.value != 0) {
                                constants.Profile_ViewModel.setSelectedUser(profile_Response.data.first())
                            } else {
                                constants.Profile_ViewModel.set_Content_Own_Profile(profile_Response.data.first())
                            }

                            resultCallback(API_Result_Handling.Success(profile_Response))
                        }
                    }

            }
            catch (e: Exception) {

                errorMessage_FF = constants.activity.getString(R.string.no_Internet)
            }
            catch (e: HttpException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "HTTP Error"))
            } catch (e: IOException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "NetWork Error"))
            } catch (e: JsonSyntaxException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "Parsing Error"))
            } catch (e: Exception) {
                resultCallback(API_Result_Handling.Error(e.message ?: "Unexpected Error"))
            }
        }
    }

    fun put_Enquiry_Decline_Undodecline(
        enquire_id: Int,
        status: String,
        resultCallback: (API_Result_Handling<Enquiry_Decline_UndoDecline>) -> Unit) {
        viewModelScope.launch {

            resultCallback(API_Result_Handling.Loading)

            val jsonObject = JSONObject()
            jsonObject.put("enquire_id", enquire_id)
            jsonObject.put("status", status)

            val requestBody: RequestBody = jsonObject.toString()
                .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

            try {
                val response = apiService.put_Enquiry_Decline_Undodecline(requestBody)
                val responseString = response.string()

                if (responseString.isEmpty()){
                    resultCallback(API_Result_Handling.NoData)
                }
                else {

                    val profile_Response = Gson().fromJson(responseString, Enquiry_Decline_UndoDecline::class.java)

                    resultCallback(API_Result_Handling.Success(profile_Response))
                }

            }
            catch (e: Exception) {

                errorMessage_FF = constants.activity.getString(R.string.no_Internet)
            }
            catch (e: HttpException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "HTTP Error"))
            } catch (e: IOException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "NetWork Error"))
            } catch (e: JsonSyntaxException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "Parsing Error"))
            } catch (e: Exception) {
                resultCallback(API_Result_Handling.Error(e.message ?: "Unexpected Error"))
            }
        }
    }

    fun put_Enquiry_Decline(
        enquire_id: Int,
        user_posts_id: Int,
        sentence: String,
        custom_para: String,
        resultCallback: (API_Result_Handling<Enquiry_Decline>) -> Unit) {
        viewModelScope.launch {

            resultCallback(API_Result_Handling.Loading)

            val jsonObject = JSONObject()
            jsonObject.put("enquire_id", enquire_id)
            jsonObject.put("user_posts_id", user_posts_id)
            jsonObject.put("sentence", sentence)
            jsonObject.put("custom_para", custom_para)

            val requestBody: RequestBody = jsonObject.toString()
                .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

            try {
                val response = apiService.put_Enquiry_Decline(requestBody)
                val responseString = response.string()

                if (responseString.isEmpty()){
                    resultCallback(API_Result_Handling.NoData)
                }
                else {

                    val profile_Response = Gson().fromJson(responseString, Enquiry_Decline::class.java)

                    resultCallback(API_Result_Handling.Success(profile_Response))
                }

            }
            catch (e: Exception) {

                errorMessage_FF = constants.activity.getString(R.string.no_Internet)
            }
            catch (e: HttpException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "HTTP Error"))
            } catch (e: IOException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "NetWork Error"))
            } catch (e: JsonSyntaxException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "Parsing Error"))
            } catch (e: Exception) {
                resultCallback(API_Result_Handling.Error(e.message ?: "Unexpected Error"))
            }
        }
    }

    fun get_Popular_Cities_Saerch(
        user_id: Int,
        resultCallback: (API_Result_Handling<Popular_Cities_Search>) -> Unit) {
        viewModelScope.launch {

            resultCallback(API_Result_Handling.Loading)

            val jsonObject = JSONObject()
            jsonObject.put("user_id", user_id)

            val requestBody: RequestBody = jsonObject.toString()
                .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

            try {
                val response = apiService.get_Popular_Cities_Saerch(requestBody)
                val responseString = response.string()

                if (responseString.isEmpty()){
                    resultCallback(API_Result_Handling.NoData)
                }
                else {

                    val popular_cities = Gson().fromJson(responseString, Popular_Cities_Search::class.java)

                    constants.Search_ViewModel.add_Popular_Cities(popular_cities.data)

                    resultCallback(API_Result_Handling.Success(popular_cities))
                }

            }
            catch (e: Exception) {

                errorMessage_FF = constants.activity.getString(R.string.no_Internet)
            }
            catch (e: HttpException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "HTTP Error"))
            } catch (e: IOException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "NetWork Error"))
            } catch (e: JsonSyntaxException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "Parsing Error"))
            } catch (e: Exception) {
                resultCallback(API_Result_Handling.Error(e.message ?: "Unexpected Error"))
            }
        }
    }

    fun delete_Post_SM_Drafts(
        user_id: Int,
        select_all: Int,
        user_post_id: String,
        resultCallback: (API_Result_Handling<Delete_Posts_Drafts>) -> Unit) {
        viewModelScope.launch {

            resultCallback(API_Result_Handling.Loading)

            val jsonObject = JSONObject()
            jsonObject.put("user_id", user_id)
            jsonObject.put("select_all", select_all)
            jsonObject.put("user_post_id", user_post_id)

            val requestBody: RequestBody = jsonObject.toString()
                .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

            try {
                val response = apiService.delete_Post_SM_Drafts(requestBody)
                val responseString = response.string()

                if (responseString.isEmpty()){
                    resultCallback(API_Result_Handling.NoData)
                }
                else {

                    val popular_cities = Gson().fromJson(responseString, Delete_Posts_Drafts::class.java)

                    resultCallback(API_Result_Handling.Success(popular_cities))
                }

            }
            catch (e: Exception) {

                errorMessage_FF = constants.activity.getString(R.string.no_Internet)
            }
            catch (e: HttpException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "HTTP Error"))
            } catch (e: IOException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "NetWork Error"))
            } catch (e: JsonSyntaxException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "Parsing Error"))
            } catch (e: Exception) {
                resultCallback(API_Result_Handling.Error(e.message ?: "Unexpected Error"))
            }
        }
    }

    fun get_Popular_Users(resultCallback: (API_Result_Handling<Popular_Sellers_Search>) -> Unit) {
        viewModelScope.launch {
            resultCallback(API_Result_Handling.Loading)

            try {
                val response = apiService.getPopularUsers()

                if (response.isSuccessful) {
                    val responseString = response.body()?.string().orEmpty()

                    if (responseString.isEmpty()) {
                        resultCallback(API_Result_Handling.NoData)
                    } else {
                        val popularSellers = Gson().fromJson(responseString, Popular_Sellers_Search::class.java)

                        constants.Search_ViewModel.add_Popular_Sellers(popularSellers.data)

                        resultCallback(API_Result_Handling.Success(popularSellers))
                    }
                } else {
                    resultCallback(API_Result_Handling.Error("HTTP ${response.code()}"))
                }

            } catch (e: IOException) {
                resultCallback(API_Result_Handling.Error("Network Error: ${e.message}"))
            } catch (e: JsonSyntaxException) {
                resultCallback(API_Result_Handling.Error("Parsing Error: ${e.message}"))
            } catch (e: Exception) {
                resultCallback(API_Result_Handling.Error("Unexpected Error: ${e.message}"))
            }
        }
    }

    fun getPhotoHeadings(resultCallback: (API_Result_Handling<RentoPhotoHeadings>) -> Unit) {
        viewModelScope.launch {
            resultCallback(API_Result_Handling.Loading)

            try {
                val response = apiService.getPhotoHeadings()

                if (response.isSuccessful) {
                    val responseString = response.body()?.string().orEmpty()

                    if (responseString.isEmpty()) {
                        resultCallback(API_Result_Handling.NoData)
                    } else {
                        val popularSellers = Gson().fromJson(responseString, RentoPhotoHeadings::class.java)

                        constants.PostProperty_ViewModel.add_form7PhotoHeadings(popularSellers)
                        constants.PostProperty_ViewModel.onPhotoHeadingApiSuccess(popularSellers)

                        resultCallback(API_Result_Handling.Success(popularSellers))
                    }
                } else {
                    resultCallback(API_Result_Handling.Error("HTTP ${response.code()}"))
                }

            } catch (e: IOException) {
                resultCallback(API_Result_Handling.Error("Network Error: ${e.message}"))
            } catch (e: JsonSyntaxException) {
                resultCallback(API_Result_Handling.Error("Parsing Error: ${e.message}"))
            } catch (e: Exception) {
                resultCallback(API_Result_Handling.Error("Unexpected Error: ${e.message}"))
            }
        }
    }

    fun put_Block_User(
        user_id: Int,
        blocker_id: Int,
        status: Int,
        resultCallback: (API_Result_Handling<Put_Block_User_Data>) -> Unit) {
        viewModelScope.launch {

            resultCallback(API_Result_Handling.Loading)

            val jsonObject = JSONObject()
            jsonObject.put("user_id", user_id)
            jsonObject.put("blocker_id", blocker_id)
            jsonObject.put("status", status)

            val requestBody: RequestBody = jsonObject.toString()
                .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

            try {
                val response = apiService.put_Block_User(requestBody)
                val responseString = response.string()

                if (responseString.isEmpty()){
                    resultCallback(API_Result_Handling.NoData)
                }
                else {

                    val block_user_response = Gson().fromJson(responseString, Put_Block_User_Data::class.java)

                    resultCallback(API_Result_Handling.Success(block_user_response))
                }

            } catch (e: HttpException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "HTTP Error"))
            } catch (e: IOException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "NetWork Error"))
            } catch (e: JsonSyntaxException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "Parsing Error"))
            } catch (e: Exception) {
                resultCallback(API_Result_Handling.Error(e.message ?: "Unexpected Error"))
            }
        }
    }

    fun put_Notification_Settings(
        user_id: Int,
        allow_notification: Int,
        notification_ids: String,
        resultCallback: (API_Result_Handling<Put_Notification_settings>) -> Unit) {
        viewModelScope.launch {

            resultCallback(API_Result_Handling.Loading)

            val jsonObject = JSONObject()
            jsonObject.put("user_id", user_id)
            jsonObject.put("allow_notification", allow_notification)
            jsonObject.put("notification_ids", notification_ids)

            val requestBody: RequestBody = jsonObject.toString()
                .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

            try {
                val response = apiService.put_Notification_Settings(requestBody)
                val responseString = response.string()

                if (responseString.isEmpty()){
                    resultCallback(API_Result_Handling.NoData)
                }
                else {

                    val block_user_response = Gson().fromJson(responseString, Put_Notification_settings::class.java)

                    resultCallback(API_Result_Handling.Success(block_user_response))
                }

            } catch (e: HttpException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "HTTP Error"))
            } catch (e: IOException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "NetWork Error"))
            } catch (e: JsonSyntaxException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "Parsing Error"))
            } catch (e: Exception) {
                resultCallback(API_Result_Handling.Error(e.message ?: "Unexpected Error"))
            }
        }
    }

    fun get_Notification_Settings(
        user_id: Int,
        resultCallback: (API_Result_Handling<Get_Notification_Settings>) -> Unit) {
        viewModelScope.launch {

            resultCallback(API_Result_Handling.Loading)

            val jsonObject = JSONObject()
            jsonObject.put("user_id", user_id)

            val requestBody: RequestBody = jsonObject.toString()
                .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

            try {
                val response = apiService.get_Notification_Settings(requestBody)
                val responseString = response.string()

                if (responseString.isEmpty()){
                    resultCallback(API_Result_Handling.NoData)
                }
                else {

                    val block_user_response = Gson().fromJson(responseString, Get_Notification_Settings::class.java)

                    val firstData = block_user_response.data?.firstOrNull()

                    if (firstData != null) {
                            constants.Profile_ViewModel.api_NS_Ids_StringList.value =
                            Pair(firstData.allow_notification, firstData.notification_ids ?: emptyList())

                        resultCallback(API_Result_Handling.Success(block_user_response))
                    } else {
                        resultCallback(API_Result_Handling.NoData)
                    }

                }

            } catch (e: HttpException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "HTTP Error"))
            } catch (e: IOException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "NetWork Error"))
            } catch (e: JsonSyntaxException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "Parsing Error"))
            } catch (e: Exception) {
                resultCallback(API_Result_Handling.Error(e.message ?: "Unexpected Error"))
            }
        }
    }

    var isLoading_BUL by mutableStateOf(false)

    var errorMessage_BUL by mutableStateOf<String?>(null)

    var currentPage_BUL by mutableStateOf(1)

    var totalPages_BUL by mutableStateOf(1)

    fun load_Blocked_Users_List(user_id: Int , page: Int) {
        if (page > totalPages_BUL) return

        viewModelScope.launch {
            isLoading_BUL = true
            errorMessage_BUL = null
            try {
                val response = apiService.get_Blocked_Users(
                    API_Interface.BlockedUserRequest(user_id, page)
                )
                if (response.isSuccessful) {
                    response.body()?.let { body ->
                        if (page == 1) {

                            constants.Profile_ViewModel.set_Blocked_Users_List(body.data)
                        } else {

                            constants.Profile_ViewModel.set_Blocked_Users_List(
                                constants.Profile_ViewModel.get_Blocked_Users_List.value + body.data
                            )
                        }
                        currentPage_BUL = page
                        totalPages_BUL = body.totalPages
                    }
                } else {

                    errorMessage_BUL = "Error ${response.code()}: ${response.message()}"
                }
            } catch (e: Exception) {

                errorMessage_BUL = constants.activity.getString(R.string.no_Internet)
            } finally {
                isLoading_BUL = false
            }
        }
    }

    fun like_Dislike(
        user_id: Int,
        user_post_id: Int,
        status: Int,
        resultCallback: (API_Result_Handling<Post_Like_Dislike>) -> Unit) {
        viewModelScope.launch {

            resultCallback(API_Result_Handling.Loading)

            val jsonObject = JSONObject()
            jsonObject.put("user_id", user_id)
            jsonObject.put("user_post_id", user_post_id)
            jsonObject.put("status", status)

            val requestBody: RequestBody = jsonObject.toString()
                .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

            try {
                val response = apiService.post_Like_Dislike(requestBody)
                val responseString = response.string()

                if (responseString.isEmpty()){
                    resultCallback(API_Result_Handling.NoData)
                }
                else {

                    val verify_Response = Gson().fromJson(responseString, Post_Like_Dislike::class.java)

                    resultCallback(API_Result_Handling.Success(verify_Response))
                    constants.Reels_ViewModel.add_Sense_isLiked(verify_Response.liked)
                }

            } catch (e: HttpException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "HTTP Error"))
            } catch (e: IOException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "NetWork Error"))
            } catch (e: JsonSyntaxException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "Parsing Error"))
            } catch (e: Exception) {
                resultCallback(API_Result_Handling.Error(e.message ?: "Unexpected Error"))
            }
        }
    }

    fun chat_Config(
        user_id: Int,
        user_post_id: Int,
        resultCallback: (API_Result_Handling<ChatConfig>) -> Unit) {
        viewModelScope.launch {

            resultCallback(API_Result_Handling.Loading)

            val jsonObject = JSONObject()
            jsonObject.put("user_id", user_id)
            jsonObject.put("user_post_id", user_post_id)

            val requestBody: RequestBody = jsonObject.toString()
                .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

            try {
                val response = apiService.message_config(requestBody)
                val responseString = response.string()

                if (responseString.isEmpty()){
                    resultCallback(API_Result_Handling.NoData)
                }
                else {

                    val chatconfig = Gson().fromJson(responseString, ChatConfig::class.java)

                    when (chatconfig.result){
                         "0" -> {
                             resultCallback(API_Result_Handling.Error(chatconfig.error))
                         }
                        "1" -> {
                            toast("Chat Has been created")
                            resultCallback(API_Result_Handling.Success(chatconfig))
                        }
                        "2" -> {
                            toast("Chat Already Exists")
                            resultCallback(API_Result_Handling.Success(chatconfig))
                        }
                    }

                }

            } catch (e: HttpException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "HTTP Error"))
            } catch (e: IOException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "NetWork Error"))
            } catch (e: JsonSyntaxException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "Parsing Error"))
            } catch (e: Exception) {
                resultCallback(API_Result_Handling.Error(e.message ?: "Unexpected Error"))
            }
        }
    }

    fun activate_RentedOut(
        user_id: Int,
        user_post_id: Int,
        resultCallback: (API_Result_Handling<Activate_RentedOut>) -> Unit
    ) {
        viewModelScope.launch {

            resultCallback(API_Result_Handling.Loading)

            val jsonObject = JSONObject().apply {
                put("user_id", user_id)
                put("user_post_id", user_post_id)
            }

            val requestBody = jsonObject.toString()
                .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

            try {
                val response = apiService.activate_RentedOut(requestBody)
                val responseString = response.string()

                if (responseString.isEmpty()) {
                    resultCallback(API_Result_Handling.NoData)
                    return@launch
                }

                val result =
                    Gson().fromJson(responseString, Activate_RentedOut::class.java)

                resultCallback(API_Result_Handling.Success(result))

            } catch (e: HttpException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "HTTP Error"))
            } catch (e: IOException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "Network Error"))
            } catch (e: JsonSyntaxException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "Parsing Error"))
            } catch (e: Exception) {
                resultCallback(API_Result_Handling.Error(e.message ?: "Unexpected Error"))
            }
        }
    }

    fun put_Cmt_Like_Dislike(
        user_id : Int,
        user_post_id:Int,
        comment_id :Int,
        status:Int,
        resultCallback: (API_Result_Handling<Put_Comment_Like_Dislike>) -> Unit) {
        viewModelScope.launch {

            resultCallback(API_Result_Handling.Loading)

            val jsonObject = JSONObject()
            jsonObject.put("user_id", user_id)
            jsonObject.put("user_post_id", user_post_id)
            jsonObject.put("comment_id", comment_id)
            jsonObject.put("status", status)

            val requestBody: RequestBody = jsonObject.toString()
                .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

            try {
                val response = apiService.put_Comment_Like_Dislike(requestBody)
                val responseString = response.string()

                if (responseString.isEmpty()){
                    resultCallback(API_Result_Handling.NoData)
                }
                else {

                    val cmt_Like_Dislike = Gson().fromJson(responseString, Put_Comment_Like_Dislike::class.java)

                    resultCallback(API_Result_Handling.Success(cmt_Like_Dislike))
                }

            } catch (e: HttpException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "HTTP Error"))
            } catch (e: IOException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "NetWork Error"))
            } catch (e: JsonSyntaxException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "Parsing Error"))
            } catch (e: Exception) {
                resultCallback(API_Result_Handling.Error(e.message ?: "Unexpected Error"))
            }
        }
    }

    fun put_save_UnSave_Property(
        user_id: Int,
        user_post_id: Int,
        status: Int,
        resultCallback: (API_Result_Handling<Save_UnSafe_Property>) -> Unit
    ) {
        viewModelScope.launch {
            resultCallback(API_Result_Handling.Loading)

            try {
                val response = apiService.put_Save_Unsave_Post(user_id, user_post_id, status)

                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        resultCallback(API_Result_Handling.Success(body))
                    } else {
                        resultCallback(API_Result_Handling.NoData)
                    }
                } else {
                    resultCallback(API_Result_Handling.Error("Error Code: ${response.code()}"))
                }

            } catch (e: HttpException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "HTTP Error"))
            } catch (e: IOException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "Network Error"))
            } catch (e: Exception) {
                resultCallback(API_Result_Handling.Error(e.message ?: "Unexpected Error"))
            }
        }
    }

    fun put_Send_Enquiry(
        user_id: Int,
        recever_posts_id: Int,
        land_type_id: Int,
        land_categorie_id: Int,
        name: String,
        phone_num: String,
        whatsapp_num: String,
        email	: String,
        land_category_para	: String,
        resultCallback: (API_Result_Handling<Put_Send_Enquiry>) -> Unit
    )
    {
        viewModelScope.launch {
            resultCallback(API_Result_Handling.Loading)

            try {
                val response = apiService.put_Send_Enquiry(
                    user_id,
                    recever_posts_id,
                    land_type_id,
                    land_categorie_id,
                    name,
                    phone_num,
                    whatsapp_num,
                    email ,
                    land_category_para
                )

                if (response.isSuccessful) {
                    val body = response.body()

                    if ( body?.result == "1") {
                        if (body != null) {
                            resultCallback(API_Result_Handling.Success(body))
                        } else {
                            resultCallback(API_Result_Handling.NoData)
                        }
                    }
                    else {
                        resultCallback(API_Result_Handling.Error("Error Code: ${response.code()}"))
                    }
                } else {
                    resultCallback(API_Result_Handling.Error("Error Code: ${response.code()}"))
                }

            } catch (e: HttpException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "HTTP Error"))
            } catch (e: IOException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "Network Error"))
            } catch (e: Exception) {
                resultCallback(API_Result_Handling.Error(e.message ?: "Unexpected Error"))
            }
        }
    }

    fun draft_New_Flow(
        user_id: Int,
        user_post_id: Int,

        area_length: String?,
        area_length_unit: String?,
        area_width: String?,
        area_width_unit: String?,

        facade_width: String?,
        facade_width_unit: String?,
        facade_height: String?,
        facade_height_unit: String?,

        property_facing: String?,
        total_floor: String?,
        property_floor_no: String?,
        property_ownership: String?,
        availability_status: String?,
        furnishing_status: String?,
        boundary_wall: String?,
        parking_available: String?,
        amenities: String?,
        property_highlights: String?,
        bhk_type: String?,

        no_of_bedrooms: String?,
        no_of_bathrooms: String?,
        no_of_balconies: String?,
        no_of_open_sides: String?,
        other_rooms: String?,
        no_of_cabins: String?,
        no_of_meeting_rooms: String?,
        min_of_seats: String?,
        max_of_seats: String?,
        conference_room: String?,
        no_of_staircases: String?,

        reception_area: String?,
        pantry: String?,
        pantry_size: String?,
        pantry_size_unit: String?,
        central_ac: String?,
        oxygen_duct: String?,
        ups: String?,
        fire_safety_measures: String?,
        lifts: String?,

        is_it_pre_leased_pre_rented: String?,
        noc_certified: String?,
        occupancy_certificate: String?,
        office_previously_used_for: String?,
        washroom_details: String?,
        which_local_authority: String?,
        does_local_authority: String?,
        suitable_business_type: String?,
        draft: String?,

        preview_model: Int?,
        price: String?,
        price_negotiable: String?,

        post_type: Int?,
        video_url: String?,
        image_urls: String?,

        latitude: String?,
        longitude: String?,
        property_name: String?,
        property_area: String?,
        property_area_unit: String?,
        carpet_area: String?,
        carpet_area_unit: String?,
        built_up_area: String?,
        built_up_area_unit: String?,
        super_built_up_area: String?,
        super_built_up_area_unit: String?,

        country: String?,
        state: String?,
        city: String?,
        locality: String?,
        pincode: String?,

        land_type_id: Int?,
        land_categorie_id: Int?,
        user_type: String?,

        resultCallback: (API_Result_Handling<New_Draft_Flow>) -> Unit
    )
    {
       viewModelScope.launch(Dispatchers.IO) {
               resultCallback(API_Result_Handling.Loading)

               try {
                   val response = apiService.draft_New_Flow(
                       user_id,
                       user_post_id,
                       area_length,
                       area_length_unit,
                       area_width,
                       area_width_unit,
                       facade_width,
                       facade_width_unit,
                       facade_height,
                       facade_height_unit,
                       property_facing,
                       total_floor,
                       property_floor_no,
                       property_ownership,
                       availability_status,
                       furnishing_status,
                       boundary_wall,
                       parking_available,
                       amenities = amenities,
                       property_highlights,
                       bhk_type,
                       no_of_bedrooms,
                       no_of_bathrooms,
                       no_of_balconies,
                       no_of_open_sides,
                       other_rooms,
                       no_of_cabins,
                       no_of_meeting_rooms,
                       min_of_seats,
                       max_of_seats,
                       conference_room,
                       no_of_staircases,
                       reception_area,
                       pantry = pantry,
                       pantry_size,
                       pantry_size_unit,
                       central_ac,
                       oxygen_duct,
                       ups = ups,
                       fire_safety_measures,
                       lifts = lifts,
                       is_it_pre_leased_pre_rented,
                       noc_certified,
                       occupancy_certificate,
                       office_previously_used_for,
                       washroom_details,
                       which_local_authority,
                       does_local_authority,
                       suitable_business_type,
                       draft = draft,
                       preview_model,
                       price = price,
                       price_negotiable,
                       post_type,
                       video_url,
                       image_urls,
                       latitude = latitude,
                       longitude = longitude,
                       property_name,
                       property_area,
                       property_area_unit,
                       carpet_area,
                       carpet_area_unit,
                       built_up_area,
                       built_up_area_unit,
                       super_built_up_area,
                       super_built_up_area_unit,
                       country = country,
                       state = state,
                       city = city,
                       locality = locality,
                       pincode = pincode,
                       land_type_id,
                       land_categorie_id,
                       user_type
                   )
                   if (response.isSuccessful) {
                       val body = response.body()
                       if (body != null) {

                           resultCallback(API_Result_Handling.Success(body))
                       } else {
                           resultCallback(API_Result_Handling.NoData)
                       }
                   } else {
                       resultCallback(API_Result_Handling.Error("Error Code: ${response.code()}"))
                   }

               } catch (e: HttpException) {
                   resultCallback(API_Result_Handling.Error(e.message ?: "HTTP Error"))
               } catch (e: IOException) {
                   resultCallback(API_Result_Handling.Error(e.message ?: "Network Error"))
               } catch (e: JsonSyntaxException) {
                   resultCallback(API_Result_Handling.Error(e.message ?: "Parsing Error"))
               } catch (e: Exception) {
                   resultCallback(API_Result_Handling.Error(e.message ?: "Unexpected Error"))
               }
       }
    }

    var isLoading_Search_FF by mutableStateOf(false)

    var errorMessage_Search_FF by mutableStateOf<String?>(null)

    var currentPage_Search_FF by mutableStateOf(0)

    var totalPages_Search_FF by mutableStateOf(1)

    fun load_Search_FF(user_id: Int , others_id : String, status: Int, search: String , page: Int) {
        if (

        page > totalPages_FF
            ) return

        viewModelScope.launch {
            isLoading_FF = true
            errorMessage_FF = null
            try {
                val response = apiService.get_profile_Search_FF_List(
                    API_Interface.FollowRequestSearch(user_id, others_id , status, search, page)
                )
                if (response.isSuccessful) {
                    response.body()?.let { body ->
                        val targetUserId = if (others_id.isEmpty()) user_id else others_id.toInt()
                        val targetTab = status - 1
                        if (page == 1) {

                            constants.Profile_ViewModel.setUserFFSearchList(targetUserId, targetTab, body.data)

                        } else {

                            constants.Profile_ViewModel.setUserFFSearchList(targetUserId, targetTab, body.data)

                        }

                        currentPage_FF = page
                        totalPages_FF = body.totalPages
                    }
                } else {

                    errorMessage_FF = "Error ${response.code()}: ${response.message()}"
                }
            } catch (e: Exception) {

                errorMessage_FF = constants.activity.getString(R.string.no_Internet)
            } finally {
                isLoading_FF = false
            }
        }
    }

    fun put_Contact_Details(
        user_id:Int,
        whatsapp_num_cc:String,
        whatsapp_num:String,
        email:String,
        resultCallback: (API_Result_Handling<Account_Settings_Contact>) -> Unit) {
        viewModelScope.launch {

            resultCallback(API_Result_Handling.Loading)

            val jsonObject = JSONObject()
            jsonObject.put("user_id", user_id)
            jsonObject.put("whatsapp_num_cc", whatsapp_num_cc)
            jsonObject.put("whatsapp_num", whatsapp_num)
            jsonObject.put("email", email)

            val requestBody: RequestBody = jsonObject.toString()
                .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

            try {
                val response = apiService.contact(requestBody)
                val responseString = response.string()

                if (responseString.isEmpty()){
                    resultCallback(API_Result_Handling.NoData)
                }
                else {

                    val contact_response = Gson().fromJson(responseString, Account_Settings_Contact::class.java)

                    if (contact_response.result == "1") {

                        resultCallback(API_Result_Handling.Success(contact_response))
                    }
                    else {
                        resultCallback(API_Result_Handling.Error("User Details Already Exists"))
                        toast("User Details Already Exists")
                    }
                }

            } catch (e: HttpException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "HTTP Error"))
            } catch (e: IOException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "NetWork Error"))
            } catch (e: JsonSyntaxException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "Parsing Error"))
            } catch (e: Exception) {
                resultCallback(API_Result_Handling.Error(e.message ?: "Unexpected Error"))
            }
        }
    }

    fun put_Sold_Unsold_Property(
        user_id:Int,
        user_post_id:Int,
        status:Int,
        resultCallback: (API_Result_Handling<Sold_UnSold_Property>) -> Unit) {
        viewModelScope.launch {

            resultCallback(API_Result_Handling.Loading)

            val jsonObject = JSONObject()
            jsonObject.put("user_id", user_id)
            jsonObject.put("user_post_id", user_post_id)
            jsonObject.put("status", status)

            val requestBody: RequestBody = jsonObject.toString()
                .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

            try {
                val response = apiService.sold_Unsold_Property(requestBody)
                val responseString = response.string()

                if (responseString.isEmpty()){
                    resultCallback(API_Result_Handling.NoData)
                }
                else {

                    val sold_Unsold_Response = Gson().fromJson(responseString, Sold_UnSold_Property::class.java)

                    resultCallback(API_Result_Handling.Success(sold_Unsold_Response))
                }

            } catch (e: HttpException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "HTTP Error"))
            } catch (e: IOException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "NetWork Error"))
            } catch (e: JsonSyntaxException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "Parsing Error"))
            } catch (e: Exception) {
                resultCallback(API_Result_Handling.Error(e.message ?: "Unexpected Error"))
            }
        }
    }

    fun logout_Api(
        user_id:Int,
        device_id: String,
        device_type: String,
        device_token: String,
        resultCallback: (API_Result_Handling<LogoutAPI>) -> Unit) {
        viewModelScope.launch {

            resultCallback(API_Result_Handling.Loading)

            val jsonObject = JSONObject()
            jsonObject.put("user_id", user_id)
            jsonObject.put("device_id", device_id)
            jsonObject.put("device_type", device_type)
            jsonObject.put("device_token", device_token)

            val requestBody: RequestBody = jsonObject.toString()
                .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

            try {
                val response = apiService.logout_Api(requestBody)
                val responseString = response.string()

                if (responseString.isEmpty()){
                    resultCallback(API_Result_Handling.NoData)
                }
                else {

                    val sold_Unsold_Response = Gson().fromJson(responseString, LogoutAPI::class.java)

                    resultCallback(API_Result_Handling.Success(sold_Unsold_Response))
                }

            } catch (e: HttpException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "HTTP Error"))
            } catch (e: IOException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "NetWork Error"))
            } catch (e: JsonSyntaxException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "Parsing Error"))
            } catch (e: Exception) {
                resultCallback(API_Result_Handling.Error(e.message ?: "Unexpected Error"))
            }
        }
    }

    fun username_update(
        user_id:Int,
        username : String,
        resultCallback: (API_Result_Handling<UserName_Update>) -> Unit) {
        viewModelScope.launch {

            resultCallback(API_Result_Handling.Loading)

            val jsonObject = JSONObject()
            jsonObject.put("user_id", user_id)
            jsonObject.put("username", username)

            val requestBody: RequestBody = jsonObject.toString()
                .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

            try {
                val response = apiService.username_update(requestBody)
                val responseString = response.string()

                if (responseString.isEmpty()){
                    resultCallback(API_Result_Handling.NoData)
                }
                else {

                    val sold_Unsold_Response = Gson().fromJson(responseString, UserName_Update::class.java)

                    profileChangeErrorMessage.value = sold_Unsold_Response.error
                    constants.Profile_ViewModel.save_new_name_edit_profile(sold_Unsold_Response.data.first().username)

                    resultCallback(API_Result_Handling.Success(sold_Unsold_Response))
                }

            } catch (e: HttpException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "HTTP Error"))
            } catch (e: IOException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "NetWork Error"))
            } catch (e: JsonSyntaxException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "Parsing Error"))
            } catch (e: Exception) {
                resultCallback(API_Result_Handling.Error(e.message ?: "Unexpected Error"))
            }
        }
    }

    fun put_Post_Form1(
        user_id:Int,
        user_post_id:Int,
        user_type:Int,
        resultCallback: (API_Result_Handling<Post_Form_1>) -> Unit) {
        viewModelScope.launch {

            resultCallback(API_Result_Handling.Loading)

            val jsonObject = JSONObject()
            jsonObject.put("user_id", user_id)
            jsonObject.put("user_post_id", user_post_id)
            jsonObject.put("user_type", user_type)

            val requestBody: RequestBody = jsonObject.toString()
                .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

            try {
                val response = apiService.post_Form1(requestBody)
                val responseString = response.string()

                if (responseString.isEmpty()){
                    resultCallback(API_Result_Handling.NoData)
                }
                else {

                    val post_Form_1_Response = Gson().fromJson(responseString, Post_Form_1::class.java)

                    AppPreferences.save_Post_Id(post_Form_1_Response.data.post_id)
                    resultCallback(API_Result_Handling.Success(post_Form_1_Response))
                }

            } catch (e: HttpException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "HTTP Error"))
            } catch (e: IOException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "NetWork Error"))
            } catch (e: JsonSyntaxException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "Parsing Error"))
            } catch (e: Exception) {
                resultCallback(API_Result_Handling.Error(e.message ?: "Unexpected Error"))
            }
        }
    }

    fun get_Post_Form2_Land_Types(
        status:Int,
        resultCallback: (API_Result_Handling<Post_Form2_Land_Types>) -> Unit) {
        viewModelScope.launch {

            resultCallback(API_Result_Handling.Loading)

            val jsonObject = JSONObject()
            jsonObject.put("status", status)

            val requestBody: RequestBody = jsonObject.toString()
                .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

            try {
                val response = apiService.get_Post_Form2_Land_Data(requestBody)
                val responseString = response.string()

                if (responseString.isEmpty()){
                    resultCallback(API_Result_Handling.NoData)
                }
                else {

                    val postform2_Land_response = Gson().fromJson(responseString, Post_Form2_Land_Types::class.java)

                    constants.PostProperty_ViewModel.clear_LandType_Data()
                    constants.PostProperty_ViewModel.add_Data_PP_2_Options(postform2_Land_response.data)
                    resultCallback(API_Result_Handling.Success(postform2_Land_response))
                }

            } catch (e: HttpException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "HTTP Error"))
            } catch (e: IOException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "NetWork Error"))
            } catch (e: JsonSyntaxException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "Parsing Error"))
            } catch (e: Exception) {
                resultCallback(API_Result_Handling.Error(e.message ?: "Unexpected Error"))
            }
        }
    }

    fun put_Post_Form2(
        user_id:Int,
        user_post_id:Int,
        status:Int,
        land_categorie_id:Int,
        resultCallback: (API_Result_Handling<Post_Form_2>) -> Unit) {
        viewModelScope.launch {

            resultCallback(API_Result_Handling.Loading)

            val jsonObject = JSONObject()
            jsonObject.put("user_id", user_id)
            jsonObject.put("user_post_id", user_post_id)
            jsonObject.put("status", status)
            jsonObject.put("land_categorie_id", land_categorie_id)

            val requestBody: RequestBody = jsonObject.toString()
                .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

            try {
                val response = apiService.post_Form2(requestBody)
                val responseString = response.string()

                if (responseString.isEmpty()){
                    resultCallback(API_Result_Handling.NoData)
                }
                else {

                    val post_Form_2_Response = Gson().fromJson(responseString, Post_Form_2::class.java)

                    resultCallback(API_Result_Handling.Success(post_Form_2_Response))
                }

            } catch (e: HttpException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "HTTP Error"))
            } catch (e: IOException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "NetWork Error"))
            } catch (e: JsonSyntaxException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "Parsing Error"))
            } catch (e: Exception) {
                resultCallback(API_Result_Handling.Error(e.message ?: "Unexpected Error"))
            }
        }
    }

    fun put_Post_Form3(
        user_id:Int,
        user_post_id:Int,
        country:String,
        state:String,
        city:String,
        pincode:String,
        locality:String,
        map_config: String,
        latitude:String,
        longitude:String,
        resultCallback: (API_Result_Handling<postproperty3>) -> Unit) {
        viewModelScope.launch {

            resultCallback(API_Result_Handling.Loading)

            val jsonObject = JSONObject()
            jsonObject.put("user_id", user_id)
            jsonObject.put("user_post_id", user_post_id)
            jsonObject.put("country", country)
            jsonObject.put("state", state)
            jsonObject.put("city", city)
            jsonObject.put("pincode", pincode)
            jsonObject.put("locality", locality)
            jsonObject.put("map_config", map_config)
            jsonObject.put("latitude", latitude)
            jsonObject.put("longitude", longitude)

            val requestBody: RequestBody = jsonObject.toString()
                .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

            try {
                val response = apiService.post_Form3(requestBody)
                val responseString = response.string()

                if (responseString.isEmpty()){
                    resultCallback(API_Result_Handling.NoData)
                }
                else {

                    val post_Form_3_Response = Gson().fromJson(responseString, postproperty3::class.java)

                    if (post_Form_3_Response.data.isNotEmpty()) {
                        resultCallback(API_Result_Handling.Success(post_Form_3_Response))
                    }
                    else {
                        resultCallback(API_Result_Handling.NoData)
                    }
                }

            } catch (e: HttpException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "HTTP Error"))
            } catch (e: IOException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "NetWork Error"))
            } catch (e: JsonSyntaxException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "Parsing Error"))
            } catch (e: Exception) {
                resultCallback(API_Result_Handling.Error(e.message ?: "Unexpected Error"))
            }
        }
    }

    fun put_post_Form4(
        user_id:Int,
        user_post_id:Int,
        property_name:String,
        property_area:String,
        property_area_unit:String,
        carpet_area:String,
        carpet_area_unit:String,
        built_up_area:String,
        built_up_area_unit:String,
        super_built_up_area:String,
        super_built_up_area_unit:String,
        area_length:String,
        area_length_unit:String,
        area_width:String,
        area_width_unit:String,
        property_facing:String,
        total_floor:String,
        rent_floor_no:String,
        preferred_tenants: List<String>,
        availability_from:String,
        agreement_type:String,
        food_preferences:String,
        pets_allowed:String,
        furnishing_status:String,
        boundary_wall:String,
        parking_available:String,
        amenities:String,
        property_highlights:String,
        bhk_type:String,
        no_of_bedrooms:String,
        no_of_Bathrooms:String,
        no_of_Balconies:String,
        no_of_open_sides:String,
        other_rooms:String,
        facade_width:String,
        facade_width_unit:String,
        facade_height:String,
        facade_height_unit:String,
        property_condition:String,
        no_of_cabins:String,
        no_of_meeting_rooms:String,
        min_of_seats:String,
        max_of_seats:String,
        conference_room:String,
        no_of_Staircases:String,
        reception_area:String,
        pantry:String,
        pantry_size:String,
        pantry_size_unit:String,
        central_ac:String,
        oxygen_duct:String,
        ups:String,
        fire_safety_measures:String,
        lifts:String,

        noc_certified:String,
        occupancy_certificate:String,

        washroom_details:String,
        does_local_authority:String,
        suitable_business_type:String,
        draft : Int,

        resultCallback: (API_Result_Handling<PostProperty45>) -> Unit) {
        viewModelScope.launch {

            resultCallback(API_Result_Handling.Loading)

            val jsonObject = JSONObject()
            jsonObject.put("user_id", user_id)
            jsonObject.put("user_post_id", user_post_id)
            jsonObject.put("property_name", property_name)
            jsonObject.put("property_area", property_area)
            jsonObject.put("property_area_unit", property_area_unit)
            jsonObject.put("carpet_area", carpet_area)
            jsonObject.put("carpet_area_unit", carpet_area_unit)
            jsonObject.put("built_up_area", built_up_area)
            jsonObject.put("built_up_area_unit", built_up_area_unit)
            jsonObject.put("super_built_up_area", super_built_up_area)
            jsonObject.put("super_built_up_area_unit", super_built_up_area_unit)
            jsonObject.put("area_length", area_length)
            jsonObject.put("area_length_unit", area_length_unit)
            jsonObject.put("area_width", area_width)
            jsonObject.put("area_width_unit", area_width_unit)
            jsonObject.put("property_facing", property_facing)
            jsonObject.put("total_floor", total_floor)
            jsonObject.put("rent_floor_no", rent_floor_no)
            jsonObject.put("preferred_tenants", preferred_tenants)
            jsonObject.put("availability_from", availability_from)
            jsonObject.put("agreement_type", agreement_type)
            jsonObject.put("food_preferences", food_preferences)
            jsonObject.put("pets_allowed", pets_allowed)
            jsonObject.put("furnishing_status", furnishing_status)
            jsonObject.put("boundary_wall", boundary_wall)
            jsonObject.put("parking_available", parking_available)
            jsonObject.put("amenities", amenities)
            jsonObject.put("property_highlights", property_highlights)
            jsonObject.put("bhk_type", bhk_type)
            jsonObject.put("no_of_bedrooms", no_of_bedrooms)
            jsonObject.put("no_of_Bathrooms", no_of_Bathrooms)
            jsonObject.put("no_of_Balconies", no_of_Balconies)
            jsonObject.put("no_of_open_sides", no_of_open_sides)
            jsonObject.put("other_rooms", other_rooms)
            jsonObject.put("facade_width", facade_width)
            jsonObject.put("facade_width_unit", facade_width_unit)
            jsonObject.put("facade_height", facade_height)
            jsonObject.put("facade_height_unit", facade_height_unit)
            jsonObject.put("property_condition", property_condition)
            jsonObject.put("no_of_cabins", no_of_cabins)
            jsonObject.put("no_of_meeting_rooms", no_of_meeting_rooms)
            jsonObject.put("min_of_seats", min_of_seats)
            jsonObject.put("max_of_seats", max_of_seats)
            jsonObject.put("conference_room", conference_room)
            jsonObject.put("no_of_Staircases", no_of_Staircases)
            jsonObject.put("reception_area", reception_area)
            jsonObject.put("pantry", pantry)
            jsonObject.put("pantry_size", pantry_size)
            jsonObject.put("pantry_size_unit", pantry_size_unit)
            jsonObject.put("central_ac", central_ac)
            jsonObject.put("oxygen_duct", oxygen_duct)
            jsonObject.put("ups", ups)
            jsonObject.put("fire_safety_measures", fire_safety_measures)
            jsonObject.put("lifts", lifts)
            jsonObject.put("noc_certified", noc_certified)
            jsonObject.put("occupancy_certificate", occupancy_certificate)
            jsonObject.put("washroom_details", washroom_details)
            jsonObject.put("does_local_authority", does_local_authority)
            jsonObject.put("suitable_business_type", suitable_business_type)
            jsonObject.put("draft", draft)

            val requestBody: RequestBody = jsonObject.toString()
                .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

            try {
                val response = apiService.post_Form4(requestBody)
                val responseString = response.string()

                if (responseString.isEmpty()){
                    resultCallback(API_Result_Handling.NoData)
                }
                else {

                    val post_Form_3_Response = Gson().fromJson(responseString, PostProperty45::class.java)

                    resultCallback(API_Result_Handling.Success(post_Form_3_Response))
                }

            } catch (e: HttpException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "HTTP Error"))
            } catch (e: IOException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "NetWork Error"))
            } catch (e: JsonSyntaxException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "Parsing Error"))
            } catch (e: Exception) {
                resultCallback(API_Result_Handling.Error(e.message ?: "Unexpected Error"))
            }
        }
    }

    fun get_post_Form4_Residential(
        land_categorie_id:Int,
        resultCallback: (API_Result_Handling<CommonPropertyResponse>) -> Unit) {

        viewModelScope.launch {

            resultCallback(API_Result_Handling.Loading)

            val jsonObject = JSONObject()
            jsonObject.put("land_categorie_id", land_categorie_id)

            val requestBody: RequestBody = jsonObject.toString()
                .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

            try {
                val response = apiService.get_post_Form4_Residential(requestBody)
                val responseString = response.string()

                if (responseString.isEmpty()){
                    resultCallback(API_Result_Handling.NoData)
                }
                else {

                    val get_Form_5_Response = Gson().fromJson(responseString, CommonPropertyResponse::class.java)

                    constants.PostProperty_ViewModel.clear_PP_Fields_Data_Res()

                    constants.PostProperty_ViewModel.clear_postFormCommon()
                    constants.PostProperty_ViewModel.add_postFormCommon(get_Form_5_Response.data?.firstOrNull()!!)

                    resultCallback(API_Result_Handling.Success(get_Form_5_Response))
                }

            } catch (e: HttpException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "HTTP Error"))
            } catch (e: IOException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "NetWork Error"))
            } catch (e: JsonSyntaxException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "Parsing Error"))
            } catch (e: Exception) {
                resultCallback(API_Result_Handling.Error(e.message ?: "Unexpected Error"))
            }
        }
    }

    fun get_post_Form4_Commercial(
        land_categorie_id:Int,

        resultCallback: (API_Result_Handling<CommonPropertyResponse>) -> Unit) {
        viewModelScope.launch {

            resultCallback(API_Result_Handling.Loading)

            val jsonObject = JSONObject()
            jsonObject.put("land_categorie_id", land_categorie_id)

            val requestBody: RequestBody = jsonObject.toString()
                .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

            try {
                val response = apiService.get_post_Form4_Commercial(requestBody)
                val responseString = response.string()

                if (responseString.isEmpty()) {
                    resultCallback(API_Result_Handling.NoData)
                } else {

                    val get_Form_4_Response_Com = Gson().fromJson(responseString, CommonPropertyResponse::class.java)

                    constants.PostProperty_ViewModel.clear_PP_Fields_Data_Com()
                    constants.PostProperty_ViewModel.clear_postFormCommon()
                    constants.PostProperty_ViewModel.add_postFormCommon(get_Form_4_Response_Com.data?.firstOrNull()!!)

                    resultCallback(API_Result_Handling.Success(get_Form_4_Response_Com))
                }

            } catch (e: HttpException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "HTTP Error"))
            } catch (e: IOException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "NetWork Error"))
            } catch (e: JsonSyntaxException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "Parsing Error"))
            } catch (e: Exception) {
                resultCallback(API_Result_Handling.Error(e.message ?: "Unexpected Error"))
            }
        }
    }

    fun get_post_Form4_Agriculture(
            land_categorie_id: Int,

            resultCallback: (API_Result_Handling<CommonPropertyResponse>) -> Unit
    ) {
            viewModelScope.launch {

                resultCallback(API_Result_Handling.Loading)

                val jsonObject = JSONObject()
                jsonObject.put("land_categorie_id", land_categorie_id)

                val requestBody: RequestBody = jsonObject.toString()
                    .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

                try {
                    val response = apiService.get_post_Form4_Agriculture(requestBody)
                    val responseString = response.string()

                    if (responseString.isEmpty()) {
                        resultCallback(API_Result_Handling.NoData)
                    } else {

                        val get_Form_4_Response_Ag = Gson().fromJson(responseString, CommonPropertyResponse::class.java)

                        constants.PostProperty_ViewModel.clear_PP_Fields_Data_Agri()
                        constants.PostProperty_ViewModel.clear_postFormCommon()
                        constants.PostProperty_ViewModel.add_postFormCommon( get_Form_4_Response_Ag.data?.first()!!)

                        resultCallback(API_Result_Handling.Success(get_Form_4_Response_Ag))
                    }

                } catch (e: HttpException) {
                    resultCallback(API_Result_Handling.Error(e.message ?: "HTTP Error"))
                } catch (e: IOException) {
                    resultCallback(API_Result_Handling.Error(e.message ?: "NetWork Error"))
                } catch (e: JsonSyntaxException) {
                    resultCallback(API_Result_Handling.Error(e.message ?: "Parsing Error"))
                } catch (e: Exception) {
                    resultCallback(API_Result_Handling.Error(e.message ?: "Unexpected Error"))
                }
            }
        }

    fun put_Post_Form5(
        user_id:Int,
        user_post_id:Int,
        price:String,
        price_negotiable:String,
        resultCallback: (API_Result_Handling<PostProperty6>) -> Unit) {
        viewModelScope.launch {

            resultCallback(API_Result_Handling.Loading)

            val jsonObject = JSONObject()
            jsonObject.put("user_id", user_id)
            jsonObject.put("user_post_id", user_post_id)
            jsonObject.put("price", price)
            jsonObject.put("price_negotiable", price_negotiable)

            val requestBody: RequestBody = jsonObject.toString()
                .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

            try {
                val response = apiService.post_Form5(requestBody)
                val responseString = response.string()

                if (responseString.isEmpty()){
                    resultCallback(API_Result_Handling.NoData)
                }
                else {

                    val post_Form_5_Response = Gson().fromJson(responseString, PostProperty6::class.java)

                    resultCallback(API_Result_Handling.Success(post_Form_5_Response))
                }

            } catch (e: HttpException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "HTTP Error"))
            } catch (e: IOException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "NetWork Error"))
            } catch (e: JsonSyntaxException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "Parsing Error"))
            } catch (e: Exception) {
                resultCallback(API_Result_Handling.Error(e.message ?: "Unexpected Error"))
            }
        }
    }

    fun post_Form5Rento(
        user_id:Int,
        user_post_id:Int,
        property_for_rent_or_lease: String,
        rent:String,
        rent_negotiable:String,
        deposit_amount_month_of_rents:String,
        deposit_amount_month_of_rents_type:String,
        total_deposit:String,
        duration_of_agreement:String,
        duration_of_agreement_type:String,
        lock_in_period:String,
        lock_in_period_type:String,
        notice_period:String,

        lease_duration_in_years:String,
        lease_duration_in_years_type:String,
        lease_amount:String,
        lease_negotiable:String,
        resultCallback: (API_Result_Handling<RentoPriceForm5>) -> Unit) {
        viewModelScope.launch {

            resultCallback(API_Result_Handling.Loading)

            val jsonObject = JSONObject()
            jsonObject.put("user_id", user_id)
            jsonObject.put("user_post_id", user_post_id)
            jsonObject.put("property_for_rent_or_lease", property_for_rent_or_lease)
            jsonObject.put("rent", rent)
            jsonObject.put("rent_negotiable", rent_negotiable)
            jsonObject.put("deposit_amount_month_of_rents", deposit_amount_month_of_rents)
            jsonObject.put("deposit_amount_month_of_rents_type", deposit_amount_month_of_rents_type)
            jsonObject.put("total_deposit", total_deposit)
            jsonObject.put("duration_of_agreement", duration_of_agreement)
            jsonObject.put("duration_of_agreement_type", duration_of_agreement_type)
            jsonObject.put("lock_in_period", lock_in_period)
            jsonObject.put("lock_in_period_type", lock_in_period_type)
            jsonObject.put("notice_period", notice_period)

            jsonObject.put("lease_duration_in_years", lease_duration_in_years)
            jsonObject.put("lease_duration_in_years_type", lease_duration_in_years_type)
            jsonObject.put("lease_amount", lease_amount)
            jsonObject.put("lease_negotiable", lease_negotiable)

            val requestBody: RequestBody = jsonObject.toString()
                .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

            try {
                val response = apiService.post_Form5(requestBody)
                val responseString = response.string()

                if (responseString.isEmpty()){
                    resultCallback(API_Result_Handling.NoData)
                }
                else {

                    val post_Form_5_Response = Gson().fromJson(responseString, RentoPriceForm5::class.java)

                    resultCallback(API_Result_Handling.Success(post_Form_5_Response))
                }

            } catch (e: HttpException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "HTTP Error"))
            } catch (e: IOException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "NetWork Error"))
            } catch (e: JsonSyntaxException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "Parsing Error"))
            } catch (e: Exception) {
                resultCallback(API_Result_Handling.Error(e.message ?: "Unexpected Error"))
            }
        }
    }

    fun post_Form6Rento(
        user_id:Int,
        user_post_id:Int,
        property_for_rent_or_lease: String,
        rent:String,
        rent_negotiable:String,
        deposit_amount_month_of_rents:String,
        deposit_amount_month_of_rents_type:String,
        total_deposit:String,
        duration_of_agreement:String,
        duration_of_agreement_type:String,
        lock_in_period:String,
        lock_in_period_type:String,
        notice_period:String,

        lease_duration_in_years:String,
        lease_duration_in_years_type:String,
        lease_amount:String,
        lease_negotiable:String,
        resultCallback: (API_Result_Handling<RentoPriceForm5>) -> Unit) {
        viewModelScope.launch {

            resultCallback(API_Result_Handling.Loading)

            val jsonObject = JSONObject()
            jsonObject.put("user_id", user_id)
            jsonObject.put("user_post_id", user_post_id)
            jsonObject.put("property_for_rent_or_lease", property_for_rent_or_lease)
            jsonObject.put("rent", rent)
            jsonObject.put("rent_negotiable", rent_negotiable)
            jsonObject.put("deposit_amount_month_of_rents", deposit_amount_month_of_rents)
            jsonObject.put("deposit_amount_month_of_rents_type", deposit_amount_month_of_rents_type)
            jsonObject.put("total_deposit", total_deposit)
            jsonObject.put("duration_of_agreement", duration_of_agreement)
            jsonObject.put("duration_of_agreement_type", duration_of_agreement_type)
            jsonObject.put("lock_in_period", lock_in_period)
            jsonObject.put("lock_in_period_type", lock_in_period_type)
            jsonObject.put("notice_period", notice_period)

            jsonObject.put("lease_duration_in_years", lease_duration_in_years)
            jsonObject.put("lease_duration_in_years_type", lease_duration_in_years_type)
            jsonObject.put("lease_amount", lease_amount)
            jsonObject.put("lease_negotiable", lease_negotiable)

            val requestBody: RequestBody = jsonObject.toString()
                .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

            try {
                val response = apiService.post_Form5(requestBody)
                val responseString = response.string()

                if (responseString.isEmpty()){
                    resultCallback(API_Result_Handling.NoData)
                }
                else {

                    val post_Form_5_Response = Gson().fromJson(responseString, RentoPriceForm5::class.java)

                    resultCallback(API_Result_Handling.Success(post_Form_5_Response))
                }

            } catch (e: HttpException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "HTTP Error"))
            } catch (e: IOException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "NetWork Error"))
            } catch (e: JsonSyntaxException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "Parsing Error"))
            } catch (e: Exception) {
                resultCallback(API_Result_Handling.Error(e.message ?: "Unexpected Error"))
            }
        }
    }

    fun put_post_Form6(
        user_id: Int,
        user_post_id: Int,
        post_type: String,
        video_url: String?,
        image_urls: List<String>?,
        resultCallback: (API_Result_Handling<Step6>) -> Unit
    ) {
        viewModelScope.launch {

            resultCallback(API_Result_Handling.Loading)

            try {

                val request = mapOf(
                    "user_id" to user_id,
                    "user_post_id" to user_post_id,
                    "post_type" to post_type,
                    "video_url" to (video_url ?: ""),
                    "image_urls" to (image_urls ?: emptyList<String>())
                )

                val jsonString = Gson().toJson(request)

                val requestBody: RequestBody = jsonString
                    .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

                val response = apiService.post_Form6(requestBody)
                val responseString = response.string()

                if (responseString.isEmpty()) {
                    resultCallback(API_Result_Handling.NoData)
                } else {
                    val post_Form_7_Response = Gson().fromJson(responseString, Step6::class.java)

                    constants.Reels_ViewModel.clear_view_pro_Details()
                    constants.Reels_ViewModel.add_View_Property_Details(post_Form_7_Response.video_model.first())
                    resultCallback(API_Result_Handling.Success(post_Form_7_Response))
                }

            } catch (e: HttpException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "HTTP Error"))
            } catch (e: IOException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "Network Error"))
            } catch (e: JsonSyntaxException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "Parsing Error"))
            } catch (e: Exception) {
                resultCallback(API_Result_Handling.Error(e.message ?: "Unexpected Error"))
            }
        }
    }

    fun put_post_Form6rento(
        user_id: Int,
        user_post_id: Int,
        post_type: String,

        video_urls: List<ImageAPIUpload>?,

        image_urls: List<ImageAPIUpload>?,
        thumbnail : String,
        resultCallback: (API_Result_Handling<RentoMediaApiDC>) -> Unit
    ) {
        viewModelScope.launch {

            resultCallback(API_Result_Handling.Loading)

            try {

                val request = mapOf(
                    "user_id" to user_id,
                    "user_post_id" to user_post_id,
                    "post_type" to post_type,
                    "video_urls" to (video_urls ?: emptyList<ImageAPIUpload>()),
                    "image_urls" to (image_urls ?: emptyList<ImageAPIUpload>()),
                    "thumbnail" to thumbnail
                )

                val jsonString = Gson().toJson(request)

                val requestBody: RequestBody = jsonString
                    .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

                val response = apiService.post_Form6(requestBody)
                val responseString = response.string()

                if (responseString.isEmpty()) {
                    resultCallback(API_Result_Handling.NoData)
                } else {
                    val post_Form_7_Response = Gson().fromJson(responseString, RentoMediaApiDC::class.java)

                    if (constants.PostProperty_ViewModel.postFlow.value == PostFlow.REQUESTMEDIA) {
                        GlobalSnackbar.show("Changes Successfully Saved")
                        constants.Profile_ViewModel.updateMediaProfilePostByPostId(
                            postId =post_Form_7_Response.video_model.firstOrNull()?.user_post_id ?: 0,
                            newImages = post_Form_7_Response.video_model.firstOrNull()?.post_property?.images,
                            newVideos = post_Form_7_Response.video_model.firstOrNull()?.post_property?.video
                        )
                        constants.Reels_ViewModel.updateMediaReelsSFByPostId(
                            postId = post_Form_7_Response.video_model.firstOrNull()?.user_post_id ?: 0,
                            newImages = post_Form_7_Response.video_model.firstOrNull()?.post_property?.images,
                            newVideos = post_Form_7_Response.video_model.firstOrNull()?.post_property?.video
                        )
                        constants.PostProperty_ViewModel.indexClicked = -1
                    }

                    resultCallback(API_Result_Handling.Success(post_Form_7_Response))
                }

            } catch (e: HttpException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "HTTP Error"))
            } catch (e: IOException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "Network Error"))
            } catch (e: JsonSyntaxException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "Parsing Error"))
            } catch (e: Exception) {
                resultCallback(API_Result_Handling.Error(e.message ?: "Unexpected Error"))
            }
        }
    }

    fun put_Post_Form7(
        user_id:Int,
        user_post_id:Int,
        resultCallback: (API_Result_Handling<RentoFormPreview>) -> Unit) {
        viewModelScope.launch {

            resultCallback(API_Result_Handling.Loading)

            val jsonObject = JSONObject()
            jsonObject.put("user_id", user_id)
            jsonObject.put("user_post_id", user_post_id)

            val requestBody: RequestBody = jsonObject.toString()
                .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

            try {
                val response = apiService.post_Form7(requestBody)
                val responseString = response.string()

                if (responseString.isEmpty()){
                    resultCallback(API_Result_Handling.NoData)
                }
                else {

                    val post_Form_7_Response = Gson().fromJson(responseString, RentoFormPreview::class.java)

                    constants.PostProperty_ViewModel.add_previewFormData(post_Form_7_Response.data.first())
                    resultCallback(API_Result_Handling.Success(post_Form_7_Response))
                }

            } catch (e: HttpException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "HTTP Error"))
            } catch (e: IOException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "NetWork Error"))
            } catch (e: JsonSyntaxException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "Parsing Error"))
            } catch (e: Exception) {
                resultCallback(API_Result_Handling.Error(e.message ?: "Unexpected Error"))
            }
        }
    }

    fun put_Post_Form_Publish(
        user_id:Int,
        user_post_id:Int,
        resultCallback: (API_Result_Handling<Post_Publish>) -> Unit) {
        viewModelScope.launch {

            resultCallback(API_Result_Handling.Loading)

            val jsonObject = JSONObject()
            jsonObject.put("user_id", user_id)
            jsonObject.put("user_post_id", user_post_id)

            val requestBody: RequestBody = jsonObject.toString()
                .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

            try {
                val response = apiService.post_Form_Publish(requestBody)
                val responseString = response.string()

                if (responseString.isEmpty()){
                    resultCallback(API_Result_Handling.NoData)
                }
                else {

                    val post_publish_Response = Gson().fromJson(responseString, Post_Publish::class.java)

                    resultCallback(API_Result_Handling.Success(post_publish_Response))
                }

            } catch (e: HttpException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "HTTP Error"))
            } catch (e: IOException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "NetWork Error"))
            } catch (e: JsonSyntaxException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "Parsing Error"))
            } catch (e: Exception) {
                resultCallback(API_Result_Handling.Error(e.message ?: "Unexpected Error"))
            }
        }
    }

    fun requestMedia(
        user_id:Int,
        receiver_post_id:Int,
        resultCallback: (API_Result_Handling<RentoRequestPhotoDC>) -> Unit) {
        viewModelScope.launch {

            resultCallback(API_Result_Handling.Loading)

            val jsonObject = JSONObject()
            jsonObject.put("user_id", user_id)
            jsonObject.put("receiver_post_id", receiver_post_id)

            val requestBody: RequestBody = jsonObject.toString()
                .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

            try {
                val response = apiService.requestMedia(requestBody)
                val responseString = response.string()

                if (responseString.isEmpty()){
                    resultCallback(API_Result_Handling.NoData)
                }
                else {

                    val post_publish_Response = Gson().fromJson(responseString, RentoRequestPhotoDC::class.java)

                    if (post_publish_Response.result == "1"){
                        resultCallback(API_Result_Handling.Success(post_publish_Response))
                    }
                    else {
                        GlobalSnackbar.show("Request already sent", iconRes = R.drawable.requestsenttoasticonrento)
                    }
                }

            } catch (e: HttpException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "HTTP Error"))
            } catch (e: IOException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "NetWork Error"))
            } catch (e: JsonSyntaxException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "Parsing Error"))
            } catch (e: Exception) {
                resultCallback(API_Result_Handling.Error(e.message ?: "Unexpected Error"))
            }
        }
    }

    fun notInterested(
        user_id:Int,
        user_post_id:Int,
        land_type_id:Int,
        land_categorie_id:Int,
        statement: String,
        resultCallback: (API_Result_Handling<RentoNotInterested>) -> Unit) {
        viewModelScope.launch {

            resultCallback(API_Result_Handling.Loading)

            val jsonObject = JSONObject()
            jsonObject.put("user_id", user_id)
            jsonObject.put("user_post_id", user_post_id)
            jsonObject.put("land_type_id", land_type_id)
            jsonObject.put("land_categorie_id", land_categorie_id)
            jsonObject.put("statement", statement)

            val requestBody: RequestBody = jsonObject.toString()
                .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

            try {
                val response = apiService.notInterested(requestBody)
                val responseString = response.string()

                if (responseString.isEmpty()){
                    resultCallback(API_Result_Handling.NoData)
                }
                else {

                    val post_publish_Response = Gson().fromJson(responseString, RentoNotInterested::class.java)

                    resultCallback(API_Result_Handling.Success(post_publish_Response))
                }

            } catch (e: HttpException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "HTTP Error"))
            } catch (e: IOException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "NetWork Error"))
            } catch (e: JsonSyntaxException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "Parsing Error"))
            } catch (e: Exception) {
                resultCallback(API_Result_Handling.Error(e.message ?: "Unexpected Error"))
            }
        }
    }

    fun send_chat_notification(
        user_id:Int,
        receiver_id:Int,
        message: String,
        user_post_id: Int,
        resultCallback: (API_Result_Handling<Chat_Notification>) -> Unit) {
        viewModelScope.launch {

            resultCallback(API_Result_Handling.Loading)

            val jsonObject = JSONObject()
            jsonObject.put("user_id", user_id)
            jsonObject.put("receiver_id", receiver_id)
            jsonObject.put("message", message)
            jsonObject.put("user_post_id", user_post_id)

            val requestBody: RequestBody = jsonObject.toString()
                .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

            try {
                val response = apiService.send_chat_notification(requestBody)
                val responseString = response.string()

                if (responseString.isEmpty()){
                    resultCallback(API_Result_Handling.NoData)
                }
                else {

                    val chatnotification = Gson().fromJson(responseString, Chat_Notification::class.java)

                    resultCallback(API_Result_Handling.Success(chatnotification))
                }

            } catch (e: HttpException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "HTTP Error"))
            } catch (e: IOException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "NetWork Error"))
            } catch (e: JsonSyntaxException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "Parsing Error"))
            } catch (e: Exception) {
                resultCallback(API_Result_Handling.Error(e.message ?: "Unexpected Error"))
            }
        }
    }

    fun put_Report_All(
        user_id:Int,
        receiver_id: String,
        user_post_id:String,
        comment_id:String,
        report_sentence_id:Int,
        report_sentence: String,
        status:Int,
        resultCallback: (API_Result_Handling<Report_All>) -> Unit) {
        viewModelScope.launch {

            resultCallback(API_Result_Handling.Loading)

            val jsonObject = JSONObject()
            jsonObject.put("user_id", user_id)
            jsonObject.put("receiver_id", receiver_id)
            jsonObject.put("user_post_id", user_post_id)
            if (comment_id.isNotEmpty()){
            jsonObject.put("comment_id", comment_id)
            }
            jsonObject.put("report_sentence_id", report_sentence_id)
            jsonObject.put("report_sentence", report_sentence)
            jsonObject.put("status", status)

            val requestBody: RequestBody = jsonObject.toString()
                .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

            try {
                val response = apiService.put_Report_All(requestBody)
                val responseString = response.string()

                if (responseString.isEmpty()){
                    resultCallback(API_Result_Handling.NoData)
                }
                else {

                    val post_Form_7_Response = Gson().fromJson(responseString, Report_All::class.java)

                    resultCallback(API_Result_Handling.Success(post_Form_7_Response))
                }

            } catch (e: HttpException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "HTTP Error"))
            } catch (e: IOException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "NetWork Error"))
            } catch (e: JsonSyntaxException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "Parsing Error"))
            } catch (e: Exception) {
                resultCallback(API_Result_Handling.Error(e.message ?: "Unexpected Error"))
            }
        }
    }

    fun Put_account_Activate_Deactivate(
        user_id:Int,
        account_delete_type : String,
        account_delete_sentence : String,
        status:Int,
        resultCallback: (API_Result_Handling<Account_Activate_Deactivate>) -> Unit) {
        viewModelScope.launch {

            resultCallback(API_Result_Handling.Loading)

            val jsonObject = JSONObject()
            jsonObject.put("user_id", user_id)
            jsonObject.put("status", status)
            jsonObject.put("account_delete_type", account_delete_type)
            jsonObject.put("account_delete_sentence", account_delete_sentence)

            val requestBody: RequestBody = jsonObject.toString()
                .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

            try {
                val response = apiService.account_Activate_Deactivate(requestBody)
                val responseString = response.string()

                if (responseString.isEmpty()){
                    resultCallback(API_Result_Handling.NoData)
                }
                else {

                    val activateDeactivate = Gson().fromJson(responseString, Account_Activate_Deactivate::class.java)

                    resultCallback(API_Result_Handling.Success(activateDeactivate))
                }

            } catch (e: HttpException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "HTTP Error"))
            } catch (e: IOException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "NetWork Error"))
            } catch (e: JsonSyntaxException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "Parsing Error"))
            } catch (e: Exception) {
                resultCallback(API_Result_Handling.Error(e.message ?: "Unexpected Error"))
            }
        }
    }

    fun get_Filter_Sort_Search_Fields(
        land_type_id : Int,
        resultCallback: (API_Result_Handling<Search_Filter_Sort_Fields>) -> Unit) {
        viewModelScope.launch {

            resultCallback(API_Result_Handling.Loading)

            val jsonObject = JSONObject()
            jsonObject.put("land_type_id", land_type_id)

            val requestBody: RequestBody = jsonObject.toString()
                .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

            try {
                val response = apiService.get_Filter_Sort_Search_Fields(requestBody)
                val responseString = response.string()

                if (responseString.isEmpty()){
                    resultCallback(API_Result_Handling.NoData)
                }
                else {

                    val filterSortFields = Gson().fromJson(responseString, Search_Filter_Sort_Fields::class.java)

                    if (!filterSortFields.data.isNullOrEmpty()) {
                        constants.Search_ViewModel.add_sort_filter_data(filterSortFields.data!!.first())
                        resultCallback(API_Result_Handling.Success(filterSortFields))
                    } else {

                        resultCallback(API_Result_Handling.NoData)
                    }

                }

            } catch (e: HttpException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "HTTP Error"))
            } catch (e: IOException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "NetWork Error"))
            } catch (e: JsonSyntaxException) {
                resultCallback(API_Result_Handling.Error(e.message ?: "Parsing Error"))
            } catch (e: Exception) {
                resultCallback(API_Result_Handling.Error(e.message ?: "Unexpected Error"))
            }
        }
    }

    var isLoading_PS_FF by mutableStateOf(true)

    var errorMessage_PS_FF by mutableStateOf<String?>(null)

    var currentPage_PS_FF by mutableStateOf(1)

    var totalPages_PS_FF by mutableStateOf(1)

    var nextPage_PS_FF by mutableStateOf(0)

    fun load_Property_Search(user_id: Int, search_type : Int, search_text : String, min_price : Int, max_price : Int, page: Int) {

        if (

            page > totalPages_PS_FF || page <= 0) return

        viewModelScope.launch {
            isLoading_PS_FF = true
            errorMessage_PS_FF = null
            try {
                val response = apiService.put_Property_Search(user_id, search_type,search_text,min_price,max_price,page)
                if (response.isSuccessful) {
                    response.body()?.let { body ->
                        if (page == 1) {

                            constants.Search_ViewModel.clear_Search_Results()
                            constants.Search_ViewModel.setSearchResultContent(body.data)
                            constants.Search_ViewModel.total_SearchResults_Counts.value = body.recCnt
                            constants.Search_ViewModel.trigger_Search_Again.value = 0

                        } else {

                            val currentList = constants.Search_ViewModel.search_Results.value
                            val newItems = body.data.filter { new ->
                                currentList.none { it.user_post_id == new.user_post_id }
                            }
                            constants.Search_ViewModel.setSearchResultContent(currentList + newItems)
                            constants.Search_ViewModel.total_SearchResults_Counts.value = body.recCnt
                            constants.Search_ViewModel.trigger_Search_Again.value = 0

                        }


                        currentPage_PS_FF = page
                        totalPages_PS_FF = body.totalPages
                        nextPage_PS_FF = body.nxtpage
                    }
                } else {
                    errorMessage_PS_FF = "Error ${response.code()}: ${response.message()}"
                }
            } catch (e: Exception) {
                errorMessage_PS_FF = "${constants.activity.getString(R.string.no_Internet)} : ${e.localizedMessage}"
            } finally {
                isLoading_PS_FF = false
            }
        }
    }

    var isLoading_Leads by mutableStateOf(true)

    var errorMessage_Leads by mutableStateOf<String?>(null)

    var currentPage_Leads by mutableStateOf(1)

    var totalPages_Leads by mutableStateOf(1)

    var nextPage_Leads by mutableStateOf(0)

    fun load_My_Leads(
        user_id: Int,
        search_type: Int,
        filter_type: Int,
        customer_dates: String,
        customer_dates_start: String,
        customer_dates_end: String,
        search_text: String,
        page: Int
    ) {
        if (page > totalPages_Leads || page <= 0) {
            return
        }

        viewModelScope.launch {
            isLoading_Leads = true
            errorMessage_Leads = null

            try {
                val response = apiService.get_My_Leads(
                    LeadsRequest(user_id, search_type, filter_type, customer_dates, customer_dates_start, customer_dates_end,search_text,page)
                )

                if (response.isSuccessful) {
                    response.body()?.let { body ->

                        if (page == 1) {

                            Enquiry_ViewModel.setMyLeads(body.data)

                        } else {
                            val currentList = Enquiry_ViewModel.my_Leads.value
                            val newItems = body.data.filter { new ->
                                currentList.none { it.enquiry_details?.enquire_id == new.enquiry_details?.enquire_id }
                            }

                            Enquiry_ViewModel.setMyLeads(currentList + newItems)
                        }

                        currentPage_Leads = page
                        totalPages_Leads = body.totalPages
                        nextPage_Leads = body.nxtpage

                    }
                } else {
                    errorMessage_Leads = "Error ${response.code()}: ${response.message()}"
                }
            } catch (e: Exception) {
                errorMessage_Leads = " ${e.localizedMessage}"
            } finally {
                isLoading_Leads = false
            }
        }
    }

    var isLoading_Self_Enquiry by mutableStateOf(true)

    var errorMessage_Self_Enquiry by mutableStateOf<String?>(null)

    var currentPage_Self_Enquiry by mutableStateOf(1)

    var totalPages_Self_Enquiry by mutableStateOf(1)

    var nextPage_Self_Enquiry by mutableStateOf(0)

    fun load_Self_Enquiry(
        user_id: Int, filter_type : Int , customer_dates : String
        , customer_dates_start : String , customer_dates_end : String
        ,search_text : String , page: Int
    ){

        val viewModel = ViewModelProvider(constants.activity)[Enquiry_ViewModel::class.java]
        if (page > totalPages_Self_Enquiry || page <= 0) return

        viewModelScope.launch {
            isLoading_Self_Enquiry = true
            errorMessage_Self_Enquiry = null
            try {
                val response = apiService.get_Self_Enquiry(SelfRequest(user_id ,filter_type , customer_dates , customer_dates_start , customer_dates_end,search_text ,page))
                if (response.isSuccessful) {
                    val body = response.body()
                    body?.let {
                        val newList = it.data
                        if (page == 1) {

                                viewModel.setSelfLeads(newList)

                        } else {

                            val currentList = viewModel.self_Enquiry.value.filterNotNull()
                            val uniqueNewItems = newList.filter { new ->
                                currentList.none { it.enquiry_details?.enquire_id == new.enquiry_details?.enquire_id }
                            }

                                viewModel.setSelfLeads(currentList + uniqueNewItems)

                        }

                        currentPage_Self_Enquiry = page
                        totalPages_Self_Enquiry = it.totalPages
                        nextPage_Self_Enquiry = it.nxtpage
                    }
                } else {
                    errorMessage_Self_Enquiry = "Error ${response.code()}: ${response.message()}"
                }

            } catch (e: Exception) {
                errorMessage_Self_Enquiry = "constants.activity.getString(R.string.no_Internet) : ${e.localizedMessage}"
            } finally {
                isLoading_Self_Enquiry = false
            }
        }
    }

    var isLoading_ProfileS by mutableStateOf(true)

    var errorMessage_ProfileS by mutableStateOf<String?>(null)

    var currentPage_ProfileS by mutableStateOf(0)

    var totalPages_ProfileS by mutableStateOf(1)

    var nextPage_ProfileS by mutableStateOf(0)

    fun load_Profile_Search(user_id: Int, name:String ,page: Int) {

        if (
            isLoading_ProfileS ||
            page > totalPages_ProfileS || page <= 0) return

        viewModelScope.launch {
            isLoading_ProfileS = true
            errorMessage_ProfileS = null
            try {
                val response = apiService.put_Profile_Search(user_id ,name,page)
                if (response.isSuccessful) {
                    response.body()?.let { body ->
                        if (page == 1) {

                            constants.Search_ViewModel.setProfileSearchResultContent(body.data)
                        } else {

                            val currentList = constants.Search_ViewModel.profile_Search_Results.value
                            val newItems = body.data.filter { new ->
                                currentList.none { it.user_id == new.user_id }
                            }
                            constants.Search_ViewModel.setProfileSearchResultContent(currentList + newItems)
                        }


                        currentPage_ProfileS = page
                        totalPages_ProfileS = body.totalPages
                        nextPage_ProfileS = body.nxtpage
                    }
                } else {
                    errorMessage_ProfileS = "Error ${response.code()}: ${response.message()}"
                }
            } catch (e: Exception) {
                errorMessage_ProfileS = "constants.activity.getString(R.string.no_Internet) : ${e.localizedMessage}"
            } finally {
                isLoading_ProfileS = false
            }
        }
    }

    var isLoading_Profile_Posts by mutableStateOf(false)
    var errorMessage_Profile_Posts by mutableStateOf<String?>(null)
    var currentPage_Profile_Posts by mutableStateOf(0)
    var totalPages_Profile_Posts by mutableStateOf(1)
    var nextPage_Profile_Posts by mutableStateOf(1)

    fun load_Profile_Posts(user_id: Int, others_id: Int,status: String, page: Int) {

        if (

            page > totalPages_Profile_Posts || page <= 0) {
            return
        }

        viewModelScope.launch {
            isLoading_Profile_Posts = true
            errorMessage_Profile_Posts = null

            try {
                val response = apiService.get_User_Posts(user_id, others_id, status ,page)

                if (response.isSuccessful) {
                    response.body()?.let { body ->

                        if (page == 1) {

                            constants.Profile_ViewModel.set_profile_Posts_data(body.data)
                        } else {

                            val currentList = constants.Profile_ViewModel.profile_Posts.value
                            val newItems = body.data.filterNot { newItem ->
                                currentList.any { it.user_post_id == newItem.user_post_id }
                            }
                            constants.Profile_ViewModel.set_profile_Posts_data(currentList + newItems)
                        }

                        currentPage_Profile_Posts = page
                        totalPages_Profile_Posts = body.totalPages
                        nextPage_Profile_Posts = body.nxtpage
                    }
                } else {
                    errorMessage_Profile_Posts = "Error ${response.code()}: ${response.message()}"
                }
            } catch (e: Exception) {
                errorMessage_Profile_Posts = "constants.activity.getString(R.string.no_Internet): ${e.localizedMessage}"
                e.printStackTrace()
            } finally {
                isLoading_Profile_Posts = false
            }
        }
    }

    var isLoading_Profile_Drafts by mutableStateOf(true)

    var errorMessage_Profile_Drafts by mutableStateOf<String?>(null)

    var currentPage_Profile_Drafts by mutableStateOf(1)

    var totalPages_Profile_Drafts by mutableStateOf(1)

    var nextPage_Profile_Drafts by mutableStateOf(0)

    fun load_Profile_Drafts(user_id: Int ,page: Int) {

        if (

            page > totalPages_Profile_Drafts || page <= 0) return

        viewModelScope.launch {
            isLoading_Profile_Drafts = true
            errorMessage_Profile_Drafts = null
            try {
                val response = apiService.get_User_Drafts(user_id ,page)
                if (response.isSuccessful) {
                    response.body()?.let { body ->
                        if (page == 1) {

                            constants.Profile_ViewModel.set_profile_Drafts_data(body.data)
                        } else {

                            val currentList = constants.Profile_ViewModel.profile_Drafts.value
                            val newItems = body.data.filter { new ->
                                currentList.none { it.post_property.user_post_id == new.post_property.user_post_id }
                            }

                            constants.Profile_ViewModel.set_profile_Drafts_data(currentList + newItems)
                        }


                        currentPage_Profile_Drafts = page
                        totalPages_Profile_Drafts = body.totalPages
                        nextPage_Profile_Drafts = body.nxtpage
                    }
                } else {
                    errorMessage_Profile_Drafts = "Error ${response.code()}: ${response.message()}"
                }
            }
            catch (e: Exception) {
                errorMessage_Profile_Drafts = "constants.activity.getString(R.string.no_Internet) : ${e.localizedMessage}"
            } finally {
                isLoading_Profile_Drafts = false
            }
        }
    }

    var isLoading_Profile_SoldOuts by mutableStateOf(true)

    var errorMessage_Profile_SoldOuts by mutableStateOf<String?>(null)

    var currentPage_Profile_SoldOuts by mutableStateOf(1)

    var totalPages_Profile_SoldOuts by mutableStateOf(1)

    var nextPage_Profile_SoldOuts by mutableStateOf(0)

    fun load_Profile_SoldOuts(user_id: Int ,page: Int) {

        if (

            page > totalPages_Profile_SoldOuts || page <= 0) return

        viewModelScope.launch {
            isLoading_Profile_SoldOuts = true
            errorMessage_Profile_SoldOuts = null
            try {
                val response = apiService.get_User_SoldOuts(user_id ,page)
                if (response.isSuccessful) {
                    response.body()?.let { body ->
                        if (page == 1) {

                            constants.Profile_ViewModel.set_profile_SoldOuts_data(body.data)
                        } else {

                            val currentList = constants.Profile_ViewModel.profile_SoldOuts.value
                            val newItems = body.data.filter { new ->
                                currentList.none { it.post_property.user_post_id == new.post_property.user_post_id }
                            }

                            constants.Profile_ViewModel.set_profile_SoldOuts_data(currentList + newItems)
                        }


                        currentPage_Profile_SoldOuts = page
                        totalPages_Profile_SoldOuts = body.totalPages
                        nextPage_Profile_SoldOuts = body.nxtpage
                    }
                } else {
                    errorMessage_Profile_SoldOuts = "Error ${response.code()}: ${response.message()}"
                }
            }
            catch (e: Exception) {
                errorMessage_Profile_SoldOuts = "constants.activity.getString(R.string.no_Internet) : ${e.localizedMessage}"
            } finally {
                isLoading_Profile_SoldOuts = false
            }
        }
    }

    var isLoading_Profile_SavedP by mutableStateOf(true)

    var errorMessage_Profile_SavedP by mutableStateOf<String?>(null)

    var currentPage_Profile_SavedP by mutableStateOf(1)

    var totalPages_Profile_SavedP by mutableStateOf(1)

    var nextPage_Profile_SavedP by mutableStateOf(0)

    fun load_Profile_Saved_Properties(user_id: Int ,page: Int) {

        if (

                page > totalPages_Profile_SavedP || page <= 0) return

        viewModelScope.launch {
            isLoading_Profile_SavedP = true
            errorMessage_Profile_SavedP = null
            try {
                val response = apiService.get_Saved_Properties(user_id ,page)
                if (response.isSuccessful) {
                    response.body()?.let { body ->
                        if (page == 1) {

                            constants.Profile_ViewModel.set_profile_SavedP_data(body.data)
                        } else {

                            val currentList = constants.Profile_ViewModel.profile_SavedP.value
                            val newItems = body.data.filter { new ->
                                currentList.none { it.post_property.user_post_id == new.post_property.user_post_id }
                            }

                            constants.Profile_ViewModel.set_profile_SavedP_data(currentList + newItems)
                        }


                        currentPage_Profile_SavedP = page
                        totalPages_Profile_SavedP = body.totalPages
                        nextPage_Profile_SavedP = body.nxtpage
                    }
                } else {
                    errorMessage_Profile_SavedP = "Error ${response.code()}: ${response.message()}"
                }
            }
            catch (e: Exception) {
                errorMessage_Profile_SavedP = "constants.activity.getString(R.string.no_Internet) : ${e.localizedMessage}"
            } finally {
                isLoading_Profile_SavedP = false
            }
        }
    }

    fun load_Search_SF(
        user_id: Int,
        short_by: Int? = null,
        page: Int,
        limit: Int? = null,

        land_categorie_id: String? = null,
        land_type_id: String? = null,

        property_area_unit: String? = null,
        property_area_from: String? = null,
        property_area_to: String? = null,

        rent_type: String? = null,

        budget_from: String? = null,
        budget_to: String? = null,

        posted_by: String? = null,

    posted_date_id: String? = null,
    posted_date_time: String? = null,
    availability_from_id: String? = null,
    availability_from_time: String? = null,

        availability_for: String? = null,

        floor_plan: String? = null,
        floor_preferences: String? = null,

        property_facing: String? = null,

        food_preferences: String? = null,
        pets_allowed: String? = null,

        furnishing_status: String? = null,
        agreement: String? = null,

        with_photo: String? = null,

        parking_available: String? = null,
        amenities: String? = null,
        property_highlights: String? = null,

        no_of_open_sides: String? = null,
        no_of_bathrooms: String? = null,

        authority_approved: String? = null,

        search_text: String? = null
    ) {
        if (

            page > totalPages_PS_FF
        ) return

        viewModelScope.launch {
            isLoading_PS_FF = true
            errorMessage_PS_FF = null
            try {

                val response = apiService.put_search_sortfilter(
                    user_id = user_id,
                    short_by = short_by,
                    page = page,
                    limit = limit,

                    land_categorie_id = land_categorie_id,
                    land_type_id = land_type_id,

                    property_area_unit = property_area_unit,
                    property_area_from = property_area_from,
                    property_area_to = property_area_to,

                    rent_type = rent_type,

                    budget_from = budget_from,
                    budget_to = budget_to,

                    posted_by = posted_by,

                    posted_date_id = posted_date_id,
                    posted_date_time = posted_date_time,

                    availability_for = availability_for,
                    availability_from_id = availability_from_id,
                    availability_from_time = availability_from_time,

                    floor_plan = floor_plan,
                    floor_preferences = floor_preferences,

                    property_facing = property_facing,

                    food_preferences = food_preferences,
                    pets_allowed = pets_allowed,

                    furnishing_status = furnishing_status,
                    agreement = agreement,

                    with_photo = with_photo,

                    parking_available = parking_available,
                    amenities = amenities,
                    property_highlights = property_highlights,

                    no_of_open_sides = no_of_open_sides,
                    no_of_bathrooms = no_of_bathrooms,

                    authority_approved = authority_approved,

                    search_text = search_text
                )

                if (response.isSuccessful) {
                    response.body()?.let { body ->
                        if (body.result == "1") {
                            if (page == 1) {

                                if (body.data.isNotEmpty()) {

                                    constants.Search_ViewModel.setSearchResultContent(body.data)
                                    constants.Search_ViewModel.total_SearchResults_Counts.value = body.recCnt
                                } else {
                                    isLoading_PS_FF = false
                                    constants.Search_ViewModel.clear_Search_Results()
                                    constants.Search_ViewModel.total_SearchResults_Counts.value = 0

                                    currentPage_PS_FF = page
                                    totalPages_PS_FF = body.totalPages

                                }
                            } else {
                                if (body.data.isNotEmpty()) {

                                    val currentList =
                                        constants.Search_ViewModel.search_Results.value
                                    val newItems = body.data.filter { new ->
                                        currentList.none { it.user_post_id == new.user_post_id }
                                    }
                                    constants.Search_ViewModel.setSearchResultContent(currentList + newItems)
                                    constants.Search_ViewModel.total_SearchResults_Counts.value = body.recCnt

                                }
                                else {
                                    constants.Search_ViewModel.clear_Search_Results()
                                    constants.Search_ViewModel.total_SearchResults_Counts.value = 0

                                    isLoading_PS_FF = false
                                    currentPage_PS_FF = page
                                    totalPages_PS_FF = body.totalPages

                                }
                            }
                        }
                        else {
                            constants.Search_ViewModel.clear_Search_Results()
                            constants.Search_ViewModel.total_SearchResults_Counts.value = 0

                            isLoading_PS_FF = false
                            currentPage_PS_FF = page
                            totalPages_PS_FF = body.totalPages
                        }
                        isLoading_PS_FF = false
                        currentPage_PS_FF = page
                        totalPages_PS_FF = body.totalPages
                    }
                } else {

                    errorMessage_PS_FF = "Error ${response.code()}: ${response.message()}"
                }
            } catch (e: Exception) {

                errorMessage_PS_FF = constants.activity.getString(R.string.no_Internet)
            } finally {
                isLoading_PS_FF = false
            }
        }
    }

    var isLoading_AN by mutableStateOf(true)
    var errorMessage_AN by mutableStateOf<String?>(null)
    var currentPage_AN by mutableStateOf(1)
    var totalPages_AN by mutableStateOf(1)
    var nextPage_AN by mutableStateOf(0)

    var result_AN by mutableStateOf("")

    fun load_AN(user_id: Int, filter_type : String ,page: Int) {

        if (
            page > totalPages_AN || page <= 0) return

        viewModelScope.launch {
            isLoading_AN = true
            errorMessage_AN = null
            try {
                val response = apiService.app_Notification(
                    AppNotiRequest(user_id, filter_type, page)
                )

                if (response.isSuccessful) {

                    response.body()?.let { body ->
                        result_AN =  body.result
                        if (page == 1) {

                            constants.Common_H_ViewModel.set_App_Notification_Content(body.data)

                        } else {

                            val currentList = constants.Common_H_ViewModel.appNotification.value
                            val newItems = body.data.filter { new ->
                                currentList.none { it.user_post_id == new.user_post_id }
                            }
                            constants.Common_H_ViewModel.set_App_Notification_Content(currentList + newItems)

                        }


                        currentPage_AN = page
                        totalPages_AN = body.totalPages
                        nextPage_AN = body.nxtpage
                        isLoading_AN = false
                    }
                } else {
                    errorMessage_AN = "Error ${response.code()}: ${response.message()}"
                }
            } catch (e: Exception) {
                errorMessage_AN = "constants.activity.getString(R.string.no_Internet) : ${e.localizedMessage}"
            } finally {
                isLoading_AN = false
            }
        }
    }

}
