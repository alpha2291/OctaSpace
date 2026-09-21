package com.toletspot.houseforrent.API.StartUp_API

import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.AppNotifcation.App_Notification
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.ApplyFilter.Apply_Search_Filter
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.ChatMainList.Chat_Main_List
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.Drafts.User_Drafts
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.Get_Blocked_Users_List
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.Get_Interests
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.Get_Main_Comments
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.Get_My_Leads
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.Get_Profile_FF_List
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.Get_Reels
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.Get_Reply_Comments
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.Get_User_Posts
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.NewDraftFlow.New_Draft_Flow
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.Put_Comment_Reply
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.Put_Profile_search
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.Put_Send_Enquiry
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.Save_UnSafe_Property
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.SavedProperties.Saved_Properties
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.SoldOuts.Sold_Outs
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

data class SelfRequest(
    var user_id: Int,
    var filter_type: Int,
    var customer_dates: String,
    var customer_dates_start: String,
    var customer_dates_end: String,
    var search_text : String,
    var page: Int,
)

data class LeadsRequest(
    var user_id: Int,
    var search_type: Int,
    var filter_type: Int,
    var customer_dates: String,
    var customer_dates_start: String,
    var customer_dates_end: String,
    var search_text : String,
    var page: Int,
)


data class ReelsRequest(
    val user_id: Int,
    val user_post_id: String,
    val page: Int
)


data class ChatMainList(
    val user_id: Int,
    val filter_type: String,
    val search_text: String,
    val page: Int
)

data class AppNotiRequest(
    val user_id: Int,
    val filter_type: String,
    val page: Int
)

data class DraftNewFlowRequestRaw(
    val user_id: Int,
    val user_post_id: Int,
    val user_type: String? = null,
    val land_type_id: Int? = null,
    val land_categorie_id: Int? = null,

    // Location
    val country: String? = null,
    val state: String? = null,
    val city: String? = null,
    val locality: String? = null,
    val pincode: String? = null,
    val latitude: String? = null,
    val longitude: String? = null,
    val property_name: String? = null,
    val property_for_rent_or_lease: String? = null,

    // Area measurements
    val property_area: String? = null,
    val property_area_unit: String? = null,
    val carpet_area: String? = null,
    val carpet_area_unit: String? = null,
    val built_up_area: String? = null,
    val built_up_area_unit: String? = null,
    val super_built_up_area: String? = null,
    val super_built_up_area_unit: String? = null,

    val area_length: String? = null,
    val area_length_unit: String? = null,
    val area_width: String? = null,
    val area_width_unit: String? = null,

    val facade_width: String? = null,
    val facade_width_unit: String? = null,
    val facade_height: String? = null,
    val facade_height_unit: String? = null,

    // Property details
    val bhk_type: String? = null,
    val property_facing: String? = null,
    val total_floor: String? = null,
    val rent_floor_no: String? = null,

    // Tenant preferences
    val preferred_tenants: String? = null,
    val availability_from: String? = null,
    val agreement_type: String? = null,
    val food_preferences: String? = null,
    val pets_allowed: String? = null,
    val property_condition: String? = null,

    // Property condition
    val furnishing_status: String? = null,
    val boundary_wall: String? = null,
    val parking_available: String? = null,

    // Amenities and highlights
    val amenities: String? = null,
    val property_highlights: String? = null,

    // Room counts
    val no_of_bedrooms: String? = null,
    val no_of_Bathrooms: String? = null,
    val no_of_Balconies: String? = null,
    val no_of_open_sides: String? = null,
    val no_of_Staircases: String? = null,
    val other_rooms: String? = null,

    // Office specific
    val no_of_cabins: String? = null,
    val no_of_meeting_rooms: String? = null,
    val min_of_seats: String? = null,
    val max_of_seats: String? = null,
    val conference_room: String? = null,

    // Facilities
    val reception_area: String? = null,
    val pantry: String? = null,
    val pantry_size: String? = null,
    val pantry_size_unit: String? = null,
    val central_ac: String? = null,
    val oxygen_duct: String? = null,
    val ups: String? = null,
    val fire_safety_measures: String? = null,
    val lifts: String? = null,

    // Certifications
    val noc_certified: String? = null,
    val occupancy_certificate: String? = null,
    val washroom_details: String? = null,
    val does_local_authority: String? = null,
    val suitable_business_type: String? = null,

    // Rental/Lease details
    val is_this_property_for_rent_or_lease: String? = null,
    val rent: String? = null,
    val rent_negotiable: String? = null,
    val deposit_amount_month_of_rents: String? = null,
    val deposit_amount_month_of_rents_type: String? = null,
    val total_deposit: String? = null,
    val duration_of_agreement: String? = null,
    val duration_of_agreement_type: String? = null,
    val lock_in_period: String? = null,
    val lock_in_period_type: String? = null,
    val notice_period: String? = null,
    val lease_duration_in_years: String? = null,
    val lease_amount: String? = null,
    val lease_negotiable: String? = null,

    // Media
    val post_type: String? = null,
    val video_urls: List<ImageAPIUpload>? = null,
    val image_urls: List<ImageAPIUpload>? = null,
    val thumbnail: String? = null,

    // Status
    val draft: String? = null,
    val preview_model: String? = null
)


interface API_Interface {
//    @Multipart
//    @POST("draft_model")
//    suspend fun draftNewFlow(
//
//        // IDs
//        @Part("user_id") userId: RequestBody,
//        @Part("user_post_id") userPostId: RequestBody,
//
//        // Area dimensions
//        @Part("area_length") areaLength: RequestBody?,
//        @Part("area_length_unit") areaLengthUnit: RequestBody?,
//        @Part("area_width") areaWidth: RequestBody?,
//        @Part("area_width_unit") areaWidthUnit: RequestBody?,
//        @Part("account_status") account_status: RequestBody?,
//
//        // Facade details
//        @Part("facade_width") facadeWidth: RequestBody?,
//        @Part("facade_width_unit") facadeWidthUnit: RequestBody?,
//        @Part("facade_height") facadeHeight: RequestBody?,
//        @Part("facade_height_unit") facadeHeightUnit: RequestBody?,
//
//        // Property details
//        @Part("property_facing") propertyFacing: RequestBody?,
//        @Part("total_floor") totalFloor: RequestBody?,
//        @Part("property_floor_no") propertyFloorNo: RequestBody?,
//        @Part("property_ownership") propertyOwnership: RequestBody?,
//        @Part("availability_status") availabilityStatus: RequestBody?,
//        @Part("furnishing_status") furnishingStatus: RequestBody?,
//        @Part("boundary_wall") boundaryWall: RequestBody?,
//        @Part("parking_available") parkingAvailable: RequestBody?,
//        @Part("amenities") amenities: RequestBody?,
//        @Part("property_highlights") propertyHighlights: RequestBody?,
//        @Part("bhk_type") bhkType: RequestBody?,
//
//        // Room details
//        @Part("no_of_bedrooms") noOfBedrooms: RequestBody?,
//        @Part("no_of_bathrooms") noOfBathrooms: RequestBody?,
//        @Part("no_of_balconies") noOfBalconies: RequestBody?,
//        @Part("no_of_open_sides") noOfOpenSides: RequestBody?,
//        @Part("other_rooms") otherRooms: RequestBody?,
//        @Part("no_of_cabins") noOfCabins: RequestBody?,
//        @Part("no_of_meeting_rooms") noOfMeetingRooms: RequestBody?,
//        @Part("min_of_seats") minOfSeats: RequestBody?,
//        @Part("max_of_seats") maxOfSeats: RequestBody?,
//        @Part("conference_room") conferenceRoom: RequestBody?,
//        @Part("no_of_staircases") noOfStaircases: RequestBody?,
//
//        // Amenities
//        @Part("reception_area") receptionArea: RequestBody?,
//        @Part("pantry") pantry: RequestBody?,
//        @Part("pantry_size") pantrySize: RequestBody?,
//        @Part("pantry_size_unit") pantrySizeUnit: RequestBody?,
//        @Part("central_ac") centralAc: RequestBody?,
//        @Part("oxygen_duct") oxygenDuct: RequestBody?,
//        @Part("ups") ups: RequestBody?,
//        @Part("fire_safety_measures") fireSafetyMeasures: RequestBody?,
//        @Part("lifts") lifts: RequestBody?,
//
//        // Pre-lease details
//        @Part("is_it_pre_leased_pre_rented") isItPreLeasedPreRented: RequestBody?,
//        @Part("noc_certified") nocCertified: RequestBody?,
//        @Part("occupancy_certificate") occupancyCertificate: RequestBody?,
//        @Part("office_previously_used_for") officePreviouslyUsedFor: RequestBody?,
//        @Part("washroom_details") washroomDetails: RequestBody?,
//        @Part("which_local_authority") whichLocalAuthority: RequestBody?,
//        @Part("does_local_authority") doesLocalAuthority: RequestBody?,
//        @Part("suitable_business_type") suitableBusinessType: RequestBody?,
//        @Part("draft") draft: RequestBody?,
//
//        // Preview and pricing
//        @Part("preview_model") previewModel: RequestBody?,
//        @Part("price") price: RequestBody?,
//        @Part("price_negotiable") priceNegotiable: RequestBody?,
//
//        // Post type and media
//        @Part("post_type") postType: RequestBody?,
//        @Part("video_url") videoUrl: RequestBody?,
//        @Part("image_urls") image_urls: RequestBody? ,
//
//        // Location details
//        @Part("latitude") latitude: RequestBody?,
//        @Part("longitude") longitude: RequestBody?,
//        @Part("property_name") propertyName: RequestBody?,
//        @Part("property_area") propertyArea: RequestBody?,
//        @Part("property_area_unit") propertyAreaUnit: RequestBody?,
//        @Part("carpet_area") carpetArea: RequestBody?,
//        @Part("carpet_area_unit") carpetAreaUnit: RequestBody?,
//        @Part("built_up_area") builtUpArea: RequestBody?,
//        @Part("built_up_area_unit") builtUpAreaUnit: RequestBody?,
//        @Part("super_built_up_area") superBuiltUpArea: RequestBody?,
//        @Part("super_built_up_area_unit") superBuiltUpAreaUnit: RequestBody?,
//
//        // Address
//        @Part("country") country: RequestBody?,
//        @Part("state") state: RequestBody?,
//        @Part("city") city: RequestBody?,
//        @Part("locality") locality: RequestBody?,
//        @Part("pincode") pincode: RequestBody?,
//
//        // Land IDs and user type
//        @Part("land_type_id") landTypeId: RequestBody?,
//        @Part("land_categorie_id") landCategorieId: RequestBody?,
//        @Part("user_type") userType: RequestBody?
//    ): Response<New_Draft_Flow>

    @Multipart
    @POST("draft_model")
    suspend fun draftNewFlow222(
        @Part("user_id") user_id: RequestBody,
        @Part("user_post_id") user_post_id: RequestBody,
        @Part("user_type") user_type: RequestBody?,
        @Part("land_type_id") land_type_id: RequestBody?,
        @Part("land_categorie_id") land_categorie_id: RequestBody?,

        // Location fields
        @Part("address") address: RequestBody?,
        @Part("country") country: RequestBody?,
        @Part("state") state: RequestBody?,
        @Part("city") city: RequestBody?,
        @Part("locality") locality: RequestBody?,
        @Part("pincode") pincode: RequestBody?,
        @Part("latitude") latitude: RequestBody?,
        @Part("longitude") longitude: RequestBody?,
        @Part("property_name") property_name: RequestBody?,

        // Area measurements
        @Part("property_area") property_area: RequestBody?,
        @Part("property_area_unit") property_area_unit: RequestBody?,
        @Part("carpet_area") carpet_area: RequestBody?,
        @Part("carpet_area_unit") carpet_area_unit: RequestBody?,
        @Part("built_up_area") built_up_area: RequestBody?,
        @Part("built_up_area_unit") built_up_area_unit: RequestBody?,
        @Part("super_built_up_area") super_built_up_area: RequestBody?,
        @Part("super_built_up_area_unit") super_built_up_area_unit: RequestBody?,

        @Part("area_length") area_length: RequestBody?,
        @Part("area_length_unit") area_length_unit: RequestBody?,
        @Part("area_width") area_width: RequestBody?,
        @Part("area_width_unit") area_width_unit: RequestBody?,

        @Part("facade_width") facade_width: RequestBody?,
        @Part("facade_width_unit") facade_width_unit: RequestBody?,
        @Part("facade_height") facade_height: RequestBody?,
        @Part("facade_height_unit") facade_height_unit: RequestBody?,

        // Property details
        @Part("bhk_type") bhk_type: RequestBody?,
        @Part("property_facing") property_facing: RequestBody?,
        @Part("total_floor") total_floor: RequestBody?,
        @Part("rent_floor_no") rent_floor_no: RequestBody?,

        // Tenant preferences
        @Part("preferred_tenants") preferred_tenants: RequestBody?,
        @Part("availability_from") availability_from: RequestBody?,
        @Part("agreement_type") agreement_type: RequestBody?,
        @Part("food_preferences") food_preferences: RequestBody?,
        @Part("pets_allowed") pets_allowed: RequestBody?,

        // Property condition
        @Part("furnishing_status") furnishing_status: RequestBody?,
        @Part("boundary_wall") boundary_wall: RequestBody?,
        @Part("parking_available") parking_available: RequestBody?,

        // Amenities and highlights as strings (not lists)
        @Part("amenities") amenities: RequestBody?,
        @Part("property_highlights") property_highlights: RequestBody?,

        // Room counts
        @Part("no_of_bedrooms") no_of_bedrooms: RequestBody?,
        @Part("no_of_Bathrooms") no_of_Bathrooms: RequestBody?,
        @Part("no_of_Balconies") no_of_Balconies: RequestBody?,
        @Part("no_of_open_sides") no_of_open_sides: RequestBody?,
        @Part("no_of_Staircases") no_of_Staircases: RequestBody?,
        @Part("other_rooms") other_rooms: RequestBody?,

        // Office specific
        @Part("no_of_cabins") no_of_cabins: RequestBody?,
        @Part("no_of_meeting_rooms") no_of_meeting_rooms: RequestBody?,
        @Part("min_of_seats") min_of_seats: RequestBody?,
        @Part("max_of_seats") max_of_seats: RequestBody?,
        @Part("conference_room") conference_room: RequestBody?,

        // Facilities
        @Part("reception_area") reception_area: RequestBody?,
        @Part("pantry") pantry: RequestBody?,
        @Part("pantry_size") pantry_size: RequestBody?,
        @Part("pantry_size_unit") pantry_size_unit: RequestBody?,
        @Part("central_ac") central_ac: RequestBody?,
        @Part("oxygen_duct") oxygen_duct: RequestBody?,
        @Part("ups") ups: RequestBody?,
        @Part("fire_safety_measures") fire_safety_measures: RequestBody?,
        @Part("lifts") lifts: RequestBody?,

        // Certifications
        @Part("noc_certified") noc_certified: RequestBody?,
        @Part("occupancy_certificate") occupancy_certificate: RequestBody?,
        @Part("washroom_details") washroom_details: RequestBody?,
        @Part("does_local_authority") does_local_authority: RequestBody?,
        @Part("suitable_business_type") suitable_business_type: RequestBody?,

        // Rental/Lease details
        @Part("is_this_property_for_rent_or_lease") is_this_property_for_rent_or_lease: RequestBody?,
        @Part("rent") rent: RequestBody?,
        @Part("rent_negotiable") rent_negotiable: RequestBody?,
        @Part("deposit_amount_month_of_rents") deposit_amount_month_of_rents: RequestBody?,
        @Part("deposit_amount_month_of_rents_type") deposit_amount_month_of_rents_type: RequestBody?,
        @Part("total_deposit") total_deposit: RequestBody?,
        @Part("duration_of_agreement") duration_of_agreement: RequestBody?,
        @Part("duration_of_agreement_type") duration_of_agreement_type: RequestBody?,
        @Part("lock_in_period") lock_in_period: RequestBody?,
        @Part("lock_in_period_type") lock_in_period_type: RequestBody?,
        @Part("notice_period") notice_period: RequestBody?,
        @Part("lease_duration_in_years") lease_duration_in_years: RequestBody?,
        @Part("lease_amount") lease_amount: RequestBody?,
        @Part("lease_negotiable") lease_negotiable: RequestBody?,

        // Media
        @Part("post_type") post_type: RequestBody?,
        @Part("video_urls") video_urls: RequestBody?,
        @Part("image_urls") image_urls: RequestBody?,
        @Part("thumbnail") thumbnail: RequestBody?,

        // Status
        @Part("draft") draft: RequestBody?,
        @Part("preview_model") preview_model: RequestBody?
    ): Response<New_Draft_Flow>


    @Multipart
    @POST("draft_model")
    suspend fun draftNewFlow(
        @Part("user_id") user_id: RequestBody,
        @Part("user_post_id") user_post_id: RequestBody,
        @Part("user_type") user_type: RequestBody?,
        @Part("land_type_id") land_type_id: RequestBody?,
        @Part("land_categorie_id") land_categorie_id: RequestBody?,

        // Location fields
        @Part("country") country: RequestBody?,
        @Part("state") state: RequestBody?,
        @Part("city") city: RequestBody?,
        @Part("locality") locality: RequestBody?,
        @Part("pincode") pincode: RequestBody?,
        @Part("latitude") latitude: RequestBody?,
        @Part("longitude") longitude: RequestBody?,
        @Part("property_name") property_name: RequestBody?,

        // Area measurements
        @Part("property_area") property_area: RequestBody?,
        @Part("property_area_unit") property_area_unit: RequestBody?,
        @Part("carpet_area") carpet_area: RequestBody?,
        @Part("carpet_area_unit") carpet_area_unit: RequestBody?,
        @Part("built_up_area") built_up_area: RequestBody?,
        @Part("built_up_area_unit") built_up_area_unit: RequestBody?,
        @Part("super_built_up_area") super_built_up_area: RequestBody?,
        @Part("super_built_up_area_unit") super_built_up_area_unit: RequestBody?,

        @Part("area_length") area_length: RequestBody?,
        @Part("area_length_unit") area_length_unit: RequestBody?,
        @Part("area_width") area_width: RequestBody?,
        @Part("area_width_unit") area_width_unit: RequestBody?,

        @Part("facade_width") facade_width: RequestBody?,
        @Part("facade_width_unit") facade_width_unit: RequestBody?,
        @Part("facade_height") facade_height: RequestBody?,
        @Part("facade_height_unit") facade_height_unit: RequestBody?,

        // Property details
        @Part("bhk_type") bhk_type: RequestBody?,
        @Part("property_facing") property_facing: RequestBody?,
        @Part("total_floor") total_floor: RequestBody?,
        @Part("rent_floor_no") rent_floor_no: RequestBody?,

        // Tenant preferences
        @Part("preferred_tenants") preferred_tenants: RequestBody?,
        @Part("availability_from") availability_from: RequestBody?,
        @Part("agreement_type") agreement_type: RequestBody?,
        @Part("food_preferences") food_preferences: RequestBody?,
        @Part("pets_allowed") pets_allowed: RequestBody?,
        @Part("property_condition") property_condition: RequestBody?,

        // Property condition
        @Part("furnishing_status") furnishing_status: RequestBody?,
        @Part("boundary_wall") boundary_wall: RequestBody?,
        @Part("parking_available") parking_available: RequestBody?,

        // Amenities and highlights as strings (not lists)
        @Part("amenities") amenities: RequestBody?,
        @Part("property_highlights") property_highlights: RequestBody?,
        @Part("property_for_rent_or_lease") property_for_rent_or_lease: RequestBody?,

        // Room counts
        @Part("no_of_bedrooms") no_of_bedrooms: RequestBody?,
        @Part("no_of_Bathrooms") no_of_Bathrooms: RequestBody?,
        @Part("no_of_Balconies") no_of_Balconies: RequestBody?,
        @Part("no_of_open_sides") no_of_open_sides: RequestBody?,
        @Part("no_of_Staircases") no_of_Staircases: RequestBody?,
        @Part("other_rooms") other_rooms: RequestBody?,

        // Office specific
        @Part("no_of_cabins") no_of_cabins: RequestBody?,
        @Part("no_of_meeting_rooms") no_of_meeting_rooms: RequestBody?,
        @Part("min_of_seats") min_of_seats: RequestBody?,
        @Part("max_of_seats") max_of_seats: RequestBody?,
        @Part("conference_room") conference_room: RequestBody?,

        // Facilities
        @Part("reception_area") reception_area: RequestBody?,
        @Part("pantry") pantry: RequestBody?,
        @Part("pantry_size") pantry_size: RequestBody?,
        @Part("pantry_size_unit") pantry_size_unit: RequestBody?,
        @Part("central_ac") central_ac: RequestBody?,
        @Part("oxygen_duct") oxygen_duct: RequestBody?,
        @Part("ups") ups: RequestBody?,
        @Part("fire_safety_measures") fire_safety_measures: RequestBody?,
        @Part("lifts") lifts: RequestBody?,

        // Certifications
        @Part("noc_certified") noc_certified: RequestBody?,
        @Part("occupancy_certificate") occupancy_certificate: RequestBody?,
        @Part("washroom_details") washroom_details: RequestBody?,
        @Part("does_local_authority") does_local_authority: RequestBody?,
        @Part("suitable_business_type") suitable_business_type: RequestBody?,

        // Rental/Lease details
        @Part("is_this_property_for_rent_or_lease") is_this_property_for_rent_or_lease: RequestBody?,
        @Part("rent") rent: RequestBody?,
        @Part("rent_negotiable") rent_negotiable: RequestBody?,
        @Part("deposit_amount_month_of_rents") deposit_amount_month_of_rents: RequestBody?,
        @Part("deposit_amount_month_of_rents_type") deposit_amount_month_of_rents_type: RequestBody?,
        @Part("total_deposit") total_deposit: RequestBody?,
        @Part("duration_of_agreement") duration_of_agreement: RequestBody?,
        @Part("duration_of_agreement_type") duration_of_agreement_type: RequestBody?,
        @Part("lock_in_period") lock_in_period: RequestBody?,
        @Part("lock_in_period_type") lock_in_period_type: RequestBody?,
        @Part("notice_period") notice_period: RequestBody?,
        @Part("lease_duration_in_years") lease_duration_in_years: RequestBody?,
        @Part("lease_amount") lease_amount: RequestBody?,
        @Part("lease_negotiable") lease_negotiable: RequestBody?,

        // Media
        @Part("post_type") post_type: RequestBody?,
        @Part("video_urls") video_urls: RequestBody?,
        @Part("image_urls") image_urls: RequestBody?,
        @Part("thumbnail") thumbnail: RequestBody?,

        // Status
        @Part("draft") draft: RequestBody?,
        @Part("preview_model") preview_model: RequestBody?
    ): Response<New_Draft_Flow>

    /// draft raw data format

    @POST("draft_model")
    suspend fun draftNewFlowRaw(
        @Body payload: DraftNewFlowRequestRaw
    ): Response<New_Draft_Flow>



    // register

    @POST("register")
    suspend fun user_Register(@Body requestBody: RequestBody): ResponseBody

    //login
    @POST("login")
    suspend fun user_Login(@Body requestBody: RequestBody): ResponseBody


    //verify otp
    @POST("verify")
    suspend fun verify_OTP(@Body requestBody: RequestBody): ResponseBody

    //contact otp
    @POST("contact")
    suspend fun contact(@Body requestBody: RequestBody): ResponseBody


    //put user interest
    @POST("user_interest")
    suspend fun put_User_Interests(@Body requestBody: RequestBody): ResponseBody

    //put user interest
    @POST("get_user_interest")
    suspend fun get_User_Interest_Particular(@Body requestBody: RequestBody): ResponseBody

    @POST("getInterest")
    suspend fun get_User_Interests(
        @Body request: InterestRequest
    ): Response<Get_Interests>


    //put user locatin
    @POST("location")
    suspend fun put_User_Location(@Body requestBody: RequestBody): ResponseBody


    //update user profile
    @POST("update_profile")
    suspend fun update_User_Profile(@Body requestBody: RequestBody): ResponseBody



    //like unlike
    @POST("follow")
    suspend fun follow_Unfollow_Delete_Users(@Body requestBody: RequestBody): ResponseBody




    @POST("location")
    suspend fun location_Storing(@Body requestBody: RequestBody) : ResponseBody


    @POST("profile_status")
    suspend fun get_User_Profile(@Body requestBody: RequestBody) : ResponseBody

    data class FollowRequest(
        val user_id: Int,
        val others_id : String,
        val status: Int,
        val nxtpage: Int
    )


    @POST("getFollowData")
    suspend fun get_profile_FF_List(
        @Body request: FollowRequest
    ): Response<Get_Profile_FF_List>




    @POST("block")
    suspend fun put_Block_User(@Body requestBody: RequestBody) : ResponseBody




    @POST("update_notification")
    suspend fun put_Notification_Settings(@Body requestBody: RequestBody) : ResponseBody



    @POST("get_notification")
    suspend fun get_Notification_Settings(@Body requestBody: RequestBody) : ResponseBody



    /// get blocked user list
    data class BlockedUserRequest(
        val user_id: Int,
        val nxtpage: Int
    )

    @POST("blocked_list")
    suspend fun get_Blocked_Users(
        @Body request: BlockedUserRequest
    ): Response<Get_Blocked_Users_List>

    //suspend fun get_Blocked_Users(@Body requestBody: RequestBody) : ResponseBody


    data class GetReelsRequest(
        val user_id: Int,
        val nxtpage: Int
    )







    @POST("get_reels")
    suspend fun get_Reels(
        @Body request: ReelsRequest
    ): Response<Get_Reels>


    @POST("chat_list")
    suspend fun get_Chat_Main_List(
        @Body request: ChatMainList
    ): Response<Chat_Main_List>



    @POST("notification_list")
    suspend fun app_Notification(@Body requestBody: AppNotiRequest) : Response<App_Notification>


    @POST("message_config")
    suspend fun message_config(@Body requestBody: RequestBody) : ResponseBody



    @POST("activate_post")
    suspend fun activate_RentedOut(@Body requestBody: RequestBody): ResponseBody

    @POST("post_like")
    suspend fun post_Like_Dislike(@Body requestBody: RequestBody) : ResponseBody


    @POST("add_firstcomment")
    suspend fun post_Comment_Reply(@Body requestBody: RequestBody) : ResponseBody

    @POST("likeComment")
    suspend fun put_Comment_Like_Dislike(@Body requestBody: RequestBody) : ResponseBody

    // get main comment
    @POST("getcomment")
    suspend fun get_Comment(@Body requestBody: RequestBody) : ResponseBody

    @FormUrlEncoded
    @POST("getcomment")
    suspend fun get_Main_Comment(
        @Field("user_id") user_id :Int,
        @Field("user_post_id") user_post_id :Int,
        @Field("page") status :Int
    ) : Response<Get_Main_Comments>


    data class FollowRequestSearch(
        val user_id: Int,
        val others_id : String,
        val status: Int,
        val search: String,
        val nxtpage: Int
    )

    @POST("searchfollower")
    suspend fun get_profile_Search_FF_List(
        @Body request: FollowRequestSearch
    ): Response<Get_Profile_FF_List>

    // get reply comment
    @FormUrlEncoded
    @POST("getreplay_comment")
    suspend fun get_Reply_Comment(
        @Field("user_id") user_id :Int,
        @Field("user_post_id") user_post_id :Int,
        @Field("comment_id") comment_id :Int,
        @Field("page") page :Int
    ) : Response<Get_Reply_Comments>


    /// put comment

    @FormUrlEncoded
    @POST("add_firstcomment")
    suspend fun put_Comment(
        @Field("user_id") user_id :Int,
        @Field("user_post_id") user_post_id :Int,
        @Field("comment_id") comment_id :Int,
        @Field("status") status :String,
        @Field("comment") comment :String,
        @Field("mention_id") mention_id :Int,
        @Field("replies_comment_id") replies_comment_id :Int
    ) : Response<Put_Comment_Reply>



    /// save /unsave  property
    @FormUrlEncoded
    @POST("save_property")
    suspend fun put_Save_Unsave_Post(
        @Field("user_id") user_id :Int,
        @Field("user_post_id") user_post_id :Int,
        @Field("status") status :Int
    ) : Response<Save_UnSafe_Property>

    /// send enquiry


    @FormUrlEncoded
    @POST("enquire")
    suspend fun put_Send_Enquiry(
        @Field("user_id") user_id :Int,
        @Field("recever_posts_id") recever_posts_id :Int,
        @Field("land_type_id") land_type_id :Int,
        @Field("land_categorie_id") land_categorie_id :Int,
        @Field("name") name :String,
        @Field("phone_num") phone_num :String,
        @Field("whatsapp_num") whatsapp_num :String,
        @Field("email") email :String,
        @Field("land_category_para") land_category_para :String,
    ) : Response<Put_Send_Enquiry>



    /// sold / unsold property
    @POST("sold_status")
    suspend fun sold_Unsold_Property(@Body requestBody: RequestBody): ResponseBody


    @POST("poststep1")
    suspend fun post_Form1(@Body requestBody: RequestBody) : ResponseBody


    @POST("land_categories")
    suspend fun get_Post_Form2_Land_Data(@Body requestBody: RequestBody) : ResponseBody

    @POST("poststep2")
    suspend fun post_Form2(@Body requestBody: RequestBody) : ResponseBody

    @POST("poststep3")
    suspend fun post_Form3(@Body requestBody: RequestBody): ResponseBody

    @POST("poststep5")
    suspend fun post_Form5(@Body requestBody: RequestBody) : ResponseBody

    @POST("poststep6")
    suspend fun post_Form6(@Body requestBody: RequestBody) : ResponseBody

    @POST("poststep7")
    suspend fun post_Form7(@Body requestBody: RequestBody) : ResponseBody


    @POST("laststep")
    suspend fun post_Form_Publish(@Body requestBody: RequestBody) : ResponseBody



    @POST("poststep4")
    suspend fun post_Form4(@Body requestBody: RequestBody) : ResponseBody


    @POST("poststep5")
    suspend fun post_Form5Rento(@Body requestBody: RequestBody) : ResponseBody

    /// request media

    @POST("request_post")
    suspend fun requestMedia(@Body requestBody: RequestBody) : ResponseBody


    //// not interested

    @POST("not_interest")
    suspend fun notInterested(@Body requestBody: RequestBody) : ResponseBody

    @POST("getform_details_residential")
    suspend fun get_post_Form4_Residential(@Body requestBody: RequestBody) : ResponseBody


    @POST("getform_details_commercial")
    suspend fun get_post_Form4_Commercial(@Body requestBody: RequestBody) : ResponseBody

    @POST("getform_details_agriculture")
    suspend fun get_post_Form4_Agriculture(@Body requestBody: RequestBody) : ResponseBody

    @POST("chat_module_notification")
    suspend fun send_chat_notification(@Body requestBody: RequestBody) : ResponseBody


    @POST("my_leads")
    suspend fun get_Enquiry_MyLeads(@Body requestBody: RequestBody) : ResponseBody


    @POST("report_users")
    suspend fun put_Report_All(@Body requestBody: RequestBody) : ResponseBody


    @POST("deactivate_or_restore_user")
    suspend fun account_Activate_Deactivate(@Body requestBody: RequestBody) : ResponseBody


    @POST("get_filter")
    suspend fun get_Filter_Sort_Search_Fields(@Body requestBody: RequestBody) : ResponseBody


    @POST("post_declined_status")
    suspend fun put_Enquiry_Decline_Undodecline(@Body requestBody: RequestBody) : ResponseBody

    @POST("decline")
    suspend fun put_Enquiry_Decline(@Body requestBody: RequestBody) : ResponseBody


    @POST("popular_city")
    suspend fun get_Popular_Cities_Saerch(@Body requestBody: RequestBody) : ResponseBody


    @POST("delete_post")
    suspend fun delete_Post_SM_Drafts(@Body requestBody: RequestBody) : ResponseBody


    @POST("draft_model")
    suspend fun draft_New_Flow2(@Body requestBody: RequestBody) : ResponseBody

    @FormUrlEncoded
    @POST("draft_model")
    suspend fun draft_New_Flow(
        @Field("user_id") userId: Int,
        @Field("user_post_id") userPostId: Int,

        // Area dimensions
        @Field("area_length") areaLength: String?,
        @Field("area_length_unit") areaLengthUnit: String?,
        @Field("area_width") areaWidth: String?,
        @Field("area_width_unit") areaWidthUnit: String?,

        // Facade details
        @Field("facade_width") facadeWidth: String?,
        @Field("facade_width_unit") facadeWidthUnit: String?,
        @Field("facade_height") facadeHeight: String?,
        @Field("facade_height_unit") facadeHeightUnit: String?,

        // Property details
        @Field("property_facing") propertyFacing: String?,
        @Field("total_floor") totalFloor: String?,
        @Field("property_floor_no") propertyFloorNo: String?,
        @Field("property_ownership") propertyOwnership: String?,
        @Field("availability_status") availabilityStatus: String?,
        @Field("furnishing_status") furnishingStatus: String?,
        @Field("boundary_wall") boundaryWall: String?,
        @Field("parking_available") parkingAvailable: String?,
        @Field("amenities") amenities: String?,
        @Field("property_highlights") propertyHighlights: String?,
        @Field("bhk_type") bhkType: String?,

        // Room details
        @Field("no_of_bedrooms") noOfBedrooms: String?,
        @Field("no_of_bathrooms") noOfBathrooms: String?,
        @Field("no_of_balconies") noOfBalconies: String?,
        @Field("no_of_open_sides") noOfOpenSides: String?,
        @Field("other_rooms") otherRooms: String?,
        @Field("no_of_cabins") noOfCabins: String?,
        @Field("no_of_meeting_rooms") noOfMeetingRooms: String?,
        @Field("min_of_seats") minOfSeats: String?,
        @Field("max_of_seats") maxOfSeats: String?,
        @Field("conference_room") conferenceRoom: String?,
        @Field("no_of_staircases") noOfStaircases: String?,

        // Amenities
        @Field("reception_area") receptionArea: String?,
        @Field("pantry") pantry: String?,
        @Field("pantry_size") pantrySize: String?,
        @Field("pantry_size_unit") pantrySizeUnit: String?,
        @Field("central_ac") centralAc: String?,
        @Field("oxygen_duct") oxygenDuct: String?,
        @Field("ups") ups: String?,
        @Field("fire_safety_measures") fireSafetyMeasures: String?,
        @Field("lifts") lifts: String?,

        // Pre-lease details
        @Field("is_it_pre_leased_pre_rented") isItPreLeasedPreRented: String?,
        @Field("noc_certified") nocCertified: String?,
        @Field("occupancy_certificate") occupancyCertificate: String?,
        @Field("office_previously_used_for") officePreviouslyUsedFor: String?,
        @Field("washroom_details") washroomDetails: String?,
        @Field("which_local_authority") whichLocalAuthority: String?,
        @Field("does_local_authority") doesLocalAuthority: String?,
        @Field("suitable_business_type") suitableBusinessType: String?,
        @Field("draft") draft: String?,

        // Preview and pricing
        @Field("preview_model") previewModel: Int?,
        @Field("price") price: String?,
        @Field("price_negotiable") priceNegotiable: String?,

        // Post type and media
        @Field("post_type") postType: Int?,
        @Field("video_url") videoUrl: String?,
        @Field("image_urls") imageUrls: String?,

        // Location details
        @Field("latitude") latitude: String?,
        @Field("longitude") longitude: String?,
        @Field("property_name") propertyName: String?,
        @Field("property_area") propertyArea: String?,
        @Field("property_area_unit") propertyAreaUnit: String?,
        @Field("carpet_area") carpetArea: String?,
        @Field("carpet_area_unit") carpetAreaUnit: String?,
        @Field("built_up_area") builtUpArea: String?,
        @Field("built_up_area_unit") builtUpAreaUnit: String?,
        @Field("super_built_up_area") superBuiltUpArea: String?,
        @Field("super_built_up_area_unit") superBuiltUpAreaUnit: String?,

        // Address
        @Field("country") country: String?,
        @Field("state") state: String?,
        @Field("city") city: String?,
        @Field("locality") locality: String?,
        @Field("pincode") pincode: String?,

        // IDs
        @Field("land_type_id") landTypeId: Int?,
        @Field("land_categorie_id") landCategorieId: Int?,
        @Field("user_type") userType: String?
    ): Response<New_Draft_Flow>


    @GET("popular_user")
    suspend fun getPopularUsers(): Response<ResponseBody>


//// photo headings
    @GET("photo_heading")
    suspend fun getPhotoHeadings(): Response<ResponseBody>





    @FormUrlEncoded
    @POST("searchProperty")
    suspend fun put_Property_Search(
        @Field("user_id") userId: Int,
        @Field("search_type") search_type: Int,
        @Field("search_text") search_text: String,
        @Field("min_price") min_price: Int,
        @Field("max_price") max_price: Int,
        @Field("page") page: Int,
    ): Response<Get_Reels>


//    @FormUrlEncoded
//    @POST("my_leads")
//    suspend fun get_My_Leads(
//        @Field("user_id") userId: Int,
//        @Field("search_type") search_type: Int,
//        @Field("filter_type") filter_type: Int,
//        @Field("customer_dates") customer_dates: String,
//        @Field("customer_dates_start") customer_dates_start: String,
//        @Field("customer_dates_end") customer_dates_end: String,
//        @Field("page") page: Int,
//    ): Response<Get_My_Leads>



    @POST("my_leads")
    suspend fun get_My_Leads(
        @Body request: LeadsRequest
    ): Response<Get_My_Leads>

//    search_type : Int , filter_type : Int , customer_dates : String , customer_dates_start : String , customer_dates_end : String , page: Int  )

//    @FormUrlEncoded
//    @POST("self_enquiry")
//    suspend fun get_Self_Enquiry(
//        @Field("user_id") userId: Int,
//        @Field("filter_type") filter_type: Int,
//        @Field("customer_dates") customer_dates: String,
//        @Field("customer_dates_start") customer_dates_start: String,
//        @Field("customer_dates_start") customer_dates_end: String,
//        @Field("page") page: Int,
//    ): Response<Get_My_Leads>


    @POST("self_enquiry")
    suspend fun get_Self_Enquiry(
        @Body request: SelfRequest
    ): Response<Get_My_Leads>

//    , filter_type : Int , customer_dates : String , customer_dates_start : String , customer_dates_end : String , page: Int  ){


    @FormUrlEncoded
    @POST("searchProfile")
    suspend fun put_Profile_Search(
        @Field("user_id") userId: Int,
        @Field("name") name: String,
        @Field("page") page: Int,
    ): Response<Put_Profile_search>


    @FormUrlEncoded
    @POST("getpost_property")
    suspend fun get_User_Posts(
        @Field("user_id") user_id: Int,
        @Field("others_id") others_id: Int,
        @Field("status") status: String,
        @Field("page") page: Int,
    ): Response<Get_User_Posts>


    @FormUrlEncoded
    @POST("getDraftPosts")
    suspend fun get_User_Drafts(
        @Field("user_id") user_id: Int,
        @Field("page") page: Int,
    ): Response<User_Drafts>


    // soldout
    @FormUrlEncoded
    @POST("getsold_status")
    suspend fun get_User_SoldOuts(
        @Field("user_id") user_id: Int,
        @Field("page") page: Int,
    ): Response<Sold_Outs>

    // saved properties
    @FormUrlEncoded
    @POST("saved_properties")
    suspend fun get_Saved_Properties(
        @Field("user_id") user_id: Int,
        @Field("page") page: Int,
    ): Response<Saved_Properties>



    @Multipart
    @POST("post_filter_residential")
    suspend fun put_search_sortfilter22(
        @Part("user_id") user_id: RequestBody,
        @Part("short_by") short_by: RequestBody?,
        @Part("recently_posted_date") recently_posted_date: RequestBody?,
        @Part("land_categorie_id") land_categorie_id: RequestBody?,
        @Part("land_type_id") land_type_id: RequestBody?,
        @Part("property_area_unit") property_area_unit: RequestBody?,
        @Part("property_area_from") property_area_from: RequestBody?,
        @Part("property_area_to") property_area_to: RequestBody?,
        @Part("budget_from") budget_from: RequestBody?,
        @Part("budget_to") budget_to: RequestBody?,
        @Part("posted_by") posted_by: RequestBody?,
        @Part("ownership") ownership: RequestBody?,
        @Part("availability_status") availability_status: RequestBody?,
        @Part("floor_plan") floor_plan: RequestBody?,
        @Part("furnishing_status") furnishing_status: RequestBody?,
        @Part("parking_available") parking_available: RequestBody?,
        @Part("no_of_open_sides") no_of_open_sides: RequestBody?,
        @Part("floor_preferences") floor_preferences: RequestBody?,
        @Part("property_facing") property_facing: RequestBody?,
        @Part("amenities") amenities: RequestBody?,
        @Part("property_highlights") property_highlights: RequestBody?,
        @Part("business_type") business_type: RequestBody?,
        @Part("authority_approved") authority_approved: RequestBody?,
        @Part("page") page: Int,
    ): Response<Apply_Search_Filter>

//    @FormUrlEncoded
//    @POST("post_filter_residential")
//    suspend fun put_search_sortfilter(
//        @Field("user_id") user_id: Int,
//        @Field("search_text") search_text: String?,
//        @Field("short_by") short_by: Int?,
//        @Field("recently_posted_date") recently_posted_date: String?,
//        @Field("land_categorie_id") land_categorie_id: String?,
//        @Field("land_type_id") land_type_id: Int?,
//        @Field("property_area_unit") property_area_unit: String?,
//        @Field("property_area_from") property_area_from: String?,
//        @Field("property_area_to") property_area_to: String?,
//        @Field("budget_from") budget_from: String?,
//        @Field("budget_to") budget_to: String?,
//        @Field("posted_by") posted_by: String?,
//        @Field("ownership") ownership: String?,
//        //@Field("availability_status") availability_status: String?,
//        @Field("floor_plan") floor_plan: String?,
//        @Field("furnishing_status") furnishing_status: String?,
//        @Field("parking_available") parking_available: String?,
//        @Field("no_of_open_sides") no_of_open_sides: String?,
//        @Field("floor_preferences") floor_preferences: String?,
//        @Field("property_facing") property_facing: String?,
//        @Field("amenities") amenities: String?,
//        @Field("property_highlights") property_highlights: String?,
//        @Field("business_type") business_type: String?,
//        @Field("authority_approved") authority_approved: String?,
//
//        @Field("rent_type") rent_type: String?,
//        @Field("posted_date_start") posted_date_start: String?,
//        @Field("posted_date_end") posted_date_end: String?,
//        @Field("availability_from_start") availability_from_start: String?,
//        @Field("availability_from_end") availability_from_end: String?,
//        @Field("availability_for") availability_for: String?,
//        @Field("food_preferences") food_preferences: String?,
//
//        @Field("page") page: Int,
//    ): Response<Apply_Search_Filter>

    @FormUrlEncoded
    @POST("post_filter_residential")
    suspend fun put_search_sortfilter(
        @Field("user_id") user_id: Int,
        @Field("short_by") short_by: Int?,
        @Field("page") page: Int,
        @Field("limit") limit: Int?,

        @Field("land_categorie_id") land_categorie_id: String?,
        @Field("land_type_id") land_type_id: String?,

        @Field("property_area_unit") property_area_unit: String?,
        @Field("property_area_from") property_area_from: String?,
        @Field("property_area_to") property_area_to: String?,

        @Field("rent_type") rent_type: String?,

        @Field("budget_from") budget_from: String?,
        @Field("budget_to") budget_to: String?,

        @Field("posted_by") posted_by: String?,

//        @Field("posted_date_start") posted_date_start: String?,
//        @Field("posted_date_end") posted_date_end: String?,

        @Field("availability_for") availability_for: String?,
//        @Field("availability_from_start") availability_from_start: String?,
//        @Field("availability_from_end") availability_from_end: String?,
        @Field("posted_date_id") posted_date_id: String?,
        @Field("posted_date_time") posted_date_time: String?,
        @Field("availability_from_id") availability_from_id: String?,
        @Field("availability_from_time") availability_from_time: String?,


        @Field("floor_plan") floor_plan: String?,
        @Field("floor_preferences") floor_preferences: String?,

        @Field("property_facing") property_facing: String?,

        @Field("food_preferences") food_preferences: String?,
        @Field("pets_allowed") pets_allowed: String?,

        @Field("furnishing_status") furnishing_status: String?,
        @Field("agreement") agreement: String?,

        @Field("with_photo") with_photo: String?,

        @Field("parking_available") parking_available: String?,
        @Field("amenities") amenities: String?,
        @Field("property_highlights") property_highlights: String?,

        @Field("no_of_open_sides") no_of_open_sides: String?,
        @Field("no_of_bathrooms") no_of_bathrooms: String?,

        @Field("authority_approved") authority_approved: String?,

        @Field("search_text") search_text: String?
    ): Response<Apply_Search_Filter>




    @POST("logout")
    suspend fun logout_Api(@Body requestBody: RequestBody) : ResponseBody


    @POST("update_username")
    suspend fun username_update(@Body requestBody: RequestBody) : ResponseBody



}


data class Apply_Filter_API(
    val user_id: String,
    val short_by: String? = null,
    val recently_posted_date: String? = null,
    val land_categorie_id: String? = null,
    val land_type_id: String? = null,
    val property_area_unit: String? = null,
    val property_area_from: String? = null,
    val property_area_to: String? = null,
    val budget_from: String? = null,
    val budget_to: String? = null,
    val posted_by: String? = null,
    val ownership: String? = null,
    val availability_status: String? = null,
    val floor_plan: String? = null,
    val furnishing_status: String? = null,
    val parking_available: String? = null,
    val no_of_open_sides: String? = null,
    val floor_preferences: String? = null,
    val property_facing: String? = null,
    val amenities: String? = null,
    val property_highlights: String? = null,
    val business_type: String? = null,
    val authority_approved: String? = null,
    val page: Int
)
