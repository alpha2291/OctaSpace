package com.toletspot.houseforrent.UI_DataClass

import android.net.Uri
import com.toletspot.houseforrent.R
import kotlinx.serialization.Serializable
import java.time.LocalDate
import java.util.UUID


data class Start_Up_DataClass(
    var title : String,
    var description : String,
    var cont_No : String,
    var user_Name : String = ""
)

data class User_Interest_DataClass(
    var title: String,
    var image: String,
    var isSelected : Boolean
)

data class ReelItem(
    val id: Int,
    val uri: String,
    val title: String,
    val description: String,
    val isLiked: Boolean = false
)


@Serializable
data class Bottom_Bar_Items(
    var title : String,
    var unSelectedIcon : Int,
    var selectedIcon : Int = 0,
    var onSelected : Boolean,
    val route: String // 👈 NEW
)

data class Comment_Item(
    val profile: Int = 0,
    val name: String = "@user",
    val own_Comment: String,
    val time: String = "Just now",
    val like: Boolean = false,
    val like_Count: Int = 0,
    val other_Commments: List<Comment_Item> = emptyList()
)


data class Enquiry_Content(
    var isWhich: Int,
    var isWhich_Content : String = "",
    var profile_Pic : Int = 0,
    var user_Name: String = "@User",
    var time: String = "Just Now",
    var cont_No: String = "9876543210",
    var mail_Id : String = "qwerty@gmail.com",
    var location : String = "Here , Coimbatore",
    var message : String = "This is the message",
    var type_Of_Land : Int = 0,
    var name_Of_Land : String,
    var land_Location : String,
    var SI_property_Type: String = "",
    var SI_price_Range: String =  ""
)

data class Custom_BottomSheetState(
    val type: String = "",
    val isVisible: Boolean = false
)


data class Selected_Dates_Calender(
    var start_Date: LocalDate? = null,
    var end_Date: LocalDate? = null,

    var timestamp_Start : String,

    var timeStamp_End : String
)

//24.7

data class Custom_PopUpState(
    val type : String = "",
    val isVisible: Boolean = false
)

data class Reels_Options(
    var isLiked : Boolean = false,
    var icon : Int ,
    var enabled_Icon : Int = 0,
    var counts : String = "",
    var isSaved :Boolean = false
)

data class Reels_Btm_Sheet_Options(
    var id : Int,
    var icon : Int,
    var title : String,
    var onClick  :()-> Unit = {}
)

//25.7

data class Search_Main_Options(
    var id : Int,
    var title: String,
    var onSelected : Boolean = false
)


//29.7

data class PostPro_Second_Form_Options_DC(
    var id : Int,
    var title : String,
    var onSelected : Boolean
)

//31.7
data class UploadPropertyMedia(
    val id: String = UUID.randomUUID().toString(),
    val localUri: Uri? = null ,                    // local file URI (picked by user)
    val isVideo: Boolean = false,
    val heading: String = "",             // assigned default or user chosen
    val isCover: Boolean = false,         // only for images
    val uploadedUrl: String? = null,      // S3 URL after upload
    val isUploading: Boolean = true,
    val videoThumbail : String = "",
    val uploadProgress: Int = 0
)



data class ApiMediaItem(
    val url: String,
    val heading: String
)

data class UploadPostRequest(
    val video_urls: List<ApiMediaItem>,
    val image_urls: List<ApiMediaItem>,
    val thumbnail: String
)

data class UploadPropertyMedia22(
    val id: String = UUID.randomUUID().toString(),
    val localUri: Uri? = null,
    val uploadedUrl: String? = null,
    val isUploading: Boolean = true,
    val progress: Int = 0,
    val isVideo: Boolean = false,
    var heading: String = "Default Heading",
    var isCover: Boolean = false
)





//1.8
data class CountryCodeHandler_DC(
    val id : Int,
    val country_Name: String,
    val country_Code: String,
    val limit : Int
)

//4.8
data class Common_DropDown2Options_DC(
    var icon: Int = 0,
    var title: String
)

data class Profile_Report_Options_DC(
    var id:Int,
    var option_title: String,
    var isSelected: Boolean
)

data class NotInterest_Options_DC(
    var id:Int,
    var option_title: String,
    var isSelected: Boolean
)

data class NotInterested_Options_DC(
    var id:Int,
    var option_title: String,
    var isSelected: Boolean
)

//5.8
data class Profile_FF_DC(
    var id: Int,
    var title : String,
    var count: String,
    var isSelected : Boolean,
    var no_follwers : Int = 0,
    var no_following :Int = 0
)

data class Users_Profiles_List_DC(
    var id : Int,
    var user_Name:String,
    var real_Name:String,
    var profile_Image: String,
    var type: Int,
    var about_User: String,
    var num_of_Fwers : String = "200",
    var num_of_Fwing : String = "300",
    var isBlocked: Boolean = false,
)

data class Profile_List_Back_Handler(
    val profiles: List<Int> = emptyList()

)
// Add this enum before the data class
enum class ScreenType {
    PROFILE,
    FF_LIST
}

data class Profile_Handle_Back(
    val id: Int = 0,
    val current_UsedId: Int,
    val other_UserId: Int,
    val selected_Tab: Int = 0,
    val ff_User_Name: String = "",
    val ff_Fw_Count: Int = 0,
    val ff_Fg_Count: Int = 0,
    val is_Search_Enabled: Boolean = false,
    val search_Text: String = "",
    val screenType: ScreenType = ScreenType.PROFILE
)


data class Settings_DC(
    var id: Int,
    var icon:Int,
    var title:String
)

//6.8
data class Notication_Subs_DC(
    var id : Int,
    var title : String,
    var isSelected: Boolean
)


data class FAQ_Details_DC(
    var id : Int,
    var title : String,
    var desc : String,
    var isSelected: Boolean
)



data class Rate_Us_DC(
    var id : Int,
    var image: Int,
    var desc : String,
    var isSelected : Boolean,
    var selected_Image : Int = R.drawable.rentogoldstar,
    var unselected_Image : Int = R.drawable.rentograystar
)

//8.8
data class Search_Popular_Cities_DC(
    var id : Int,
    var title: String,
    var isSelected : Boolean
)

//11.8

data class Chat_Property_Structure_DC(
    var id : Int,
    var enquiry_Type : Int,
    var property_Name : String,
    var property_Location : String,
)

data class Chat_User_Structure_Content_DC(
    var id : Int,
    var user_Name : String,
    var profile_Pic: Int,
    var last_Done_Message :String,
    var timing : String,
    var count_of_Message : Int
)

data class Own_Profile_DC(
    var id : Int,
    var user_Name:String,
    var real_Name:String,
    var profile_Image: String,
    var type: Int,
    var about_User: String,
    var num_of_Fwers : String = "200",
    var num_of_Fwing : String = "300",
//    var isBlocked: Boolean = false,
)

//data class Profile_Content_DC(
//    var id : Int,
//    var user_type :Int,
//    var profile_Image: String,
//    var user_Name: String,
//    var real_Name: String,
//    var about_User : String,
//    var num_of_Fwers :Int,
//    var num_of_Fwing : Int
//)


data class Search_Result_Item(
    var id : Int,
    var user_Name: String,
    var profile_Image: String,
    var posted_Time: String,
    var video_Uri: String,
    var type_of_Property : String,
    var property_Name: String,
    var property_Location: String,
    var property_Price: String,
    var reviews : String,
    var rating : String,
    var likes_Count : String,
    var comments_Count: String ,
    var isLiked : Boolean,
    var isSaved : Boolean
)


// 26.9
data class Flw_UnFlw_Content_DC(
    var user_Id :Int,
    var user_Name: String,
    var user_Image: String,
    var status  : Int,
    var noti_Id : Int = 0
)



data class PP3_API_DC(
    var pincode : String,
   var  country : String,
   var state : String,
   var city : String,
    var locality : String,

)


data class PropertyType_Form2(
    val id: Int,
    val title: String,
    val imageRes: Int,
    val selectedImageRes : Int
)

data class Selected_Options_Form4_DC(
    var property_Name: String = "",
    var property_Land_Area : String = "",
    var property_area_unit : String = "sq",

    var property_Carpet_Area : String = "",


    var carpet_area_unit: String = "sq",
    var property_Builtup_Area : String = "",
    var built_up_area_unit : String = "sq",
    var property_Super_Builtup_Area : String = "",
    var super_built_up_area_unit : String = "sq",


    var property_Area_Dimension_Length : String = "",
    var property_Area_Dimension_Length_Unit : String = "sq",
    var property_Area_Dimension_Width : String = "",
    var property_Area_Dimension_Width_Unit : String = "sq",
//    var area_width_unit : String = "",


//    var property_Super_Builtup_Area_Unit : String = "sq",

//    var area_width : String = "",

    var property_Facing : String = "",

    var property_Floor_Det_Total : String = "",
    var property_Floor_Det_Which : String = "",

    var property_preferred_tenants : List<String> = emptyList(),
    var property_availability_from : String = "",
    var property_agreement_type : String = "",
    var property_food_preferences : String = "",
    var property_pets_allowed : String = "",

    var property_Furnished : String = "",
    var property_Boundary_Wall : String = "",
    var property_Parking : String = "",
    var property_Amenities : List<String> = emptyList(),
    var property_Highlights : List<String> = emptyList(),

    var property_Floor_Plan_Bhk : String = "",


    var property_No_of_Beds : String = "",
    var property_No_of_Baths : String = "",
    var property_No_of_Balconies : String = "",
    var property_No_Of_OpenSides : String = "",
    var property_Other_Rooms : List<String> = emptyList(),



    var property_Facade_Height : String = "",
    var facade_height_unit : String = "",
    var property_Facade_Width : String = "",
    var facade_width_unit : String = "",


    var property_condition : String = "",

    var property_No_Of_Cabins : String = "",
    var property_No_Of_Meeting_Rooms : String = "",

    var property_Min_No_Of_Seats : String = "",
    var property_Max_No_Of_Seats : String = "",

    var property_Conference_Room : String = "",
    var property_No_Of_Stairs : String = "",

    var property_Reception : String = "",


    var property_Pantry : String = "",
    var property_Pantry_Size : String = "",
    var pantry_size_unit : String = "sq ft",


    var property_Central_AC : String = "",
    var property_Oxygen_Duct : String = "",
    var property_UPS : String = "",
    var property_Fire_Safety : List<String> = emptyList(),
    var property_Lifts : String = "",


    var property_NOC_Certified : String = "",
    var property_Occupancy : String = "",

    var property_WashRoom : List<String> = emptyList(),

    var property_Authority_Approved : String = "",


    var property_Suitable_Business_Type: List<String> = emptyList(),
    var draft : Int = 0,



    // step 666666 rentoo

    var property_for_rent_or_lease : String = "",
    var rent: String = "",
    var rent_negotiable : Boolean = false,
    var deposit_amount_month_of_rents : String = "",
    var deposit_amount_month_of_rents_type : String = "",
    var total_deposit : String = "",
    var duration_of_agreement : String = "",
    var duration_of_agreement_type : String = "",
    var lock_in_period : String = "",
    var lock_in_period_type : String = "",
    var notice_period : String = "",
//    var notice_period_type : String = "",
    var lease_duration_in_years : String = "",
    var lease_duration_in_years_type : String = "",
    var lease_amount : String = "",
    var lease_negotiable : Boolean = false

//    var property_Leased_Rented : String = "",
//    var property_Previously_Used_For : String = "",



    )


data class All_Form_Handler(
    // User
    var user_id: Int? = 0,
    var user_post_id: Int? = 0,

    // Area dimensions
    var area_length: Int? = 0,
    var area_length_unit: String? = "",
    var area_width: Int? = 0,
    var area_width_unit: String? = "",

    // Facade details
    var facade_width: Int? = 0,
    var facade_width_unit: String? = "",
    var facade_height: Int? = 0,
    var facade_height_unit: String? = "",

    // Property details
    var property_facing: String? = "",
    var total_floor: Int? = 0,
    var property_floor_no: Int? = 0,
    var property_ownership: String? = "",
    var availability_status: String? = "",
    var furnishing_status: String? = "",
    var boundary_wall: String? = "",
    var parking_available: String? = "",
    var amenities: String? = "",
    var property_highlights: String? = "",
    var bhk_type: String? = "",

    // Room details
    var no_of_bedrooms: Int? = 0,
    var no_of_bathrooms: Int? = 0,
    var no_of_balconies: Int? = 0,
    var no_of_open_sides: Int? = 0,
    var other_rooms: String? = "",
    var no_of_cabins: Int? = 0,
    var no_of_meeting_rooms: Int? = 0,
    var min_of_seats: Int? = 0,
    var max_of_seats: Int? = 0,
    var conference_room: Int? = 0,
    var no_of_staircases: Int? = 0,

    // Amenities
    var reception_area: String? = "",
    var pantry: String? = "",
    var pantry_size: Int? = 0,
    var pantry_size_unit: String? = "",
    var central_ac: String? = "",
    var oxygen_duct: String? = "",
    var ups: String? = "",
    var fire_safety_measures: String? = "",
    var lifts: String? = "",

    // Pre-lease details
    var is_it_pre_leased_pre_rented: String? = "",
    var noc_certified: String? = "",
    var occupancy_certificate: String? = "",
    var office_previously_used_for: String? = "",
    var washroom_details: String? = "",
    var which_local_authority: String? = "",
    var does_local_authority: String? = "",
    var suitable_business_type: String? = "",
    var draft: String? = "",

    // Preview and pricing
    var preview_model: String? = "",
    var price: String? = "",
    var price_negotiable: String? = "",

    // Post type and media
    var post_type: Int? = 0,
    var video_url: String? = "",
    var image_urls: String? = "",

    // Location details
    var latitude: Double? = 0.0,
    var longitude: Double? = 0.0,
    var property_name: String? = "",
    var property_area: Int? = 0,
    var property_area_unit: String? = "",
    var carpet_area: Int? = 0,
    var carpet_area_unit: String? = "",
    var built_up_area: Int? = 0,
    var built_up_area_unit: String? = "",
    var super_built_up_area: Int? = 0,
    var super_built_up_area_unit: String? = "",

    // Address
    var country: String? = "",
    var state: String? = "",
    var city: String? = "",
    var locality: String? = "",
    var pincode: String? = "",

    // IDs
    var land_type_id: Int? = 0,
    var land_categorie_id: Int? = 0,
    var user_type: String? = ""
)



/// rental

data class ImageNamingOptions(
    var id : Int = 0,
    var title : String = "",
    var isCover : Boolean = false
)


/// rentall


data class RentalForm5(
    var property_for_rent_or_lease : String = "",
    var rent: String = "",
    var rent_negotiable : String = "",
    var deposit_amount_month_of_rents : String = "",
    var deposit_amount_month_of_rents_type : String = "",
    var total_deposit : String = "",
    var duration_of_agreement : String = "",
    var duration_of_agreement_type : String = "",
    var lock_in_period : String = "",
    var lock_in_period_type : String = "",
    var notice_period : String = "",
//    var notice_period_type : String = "",
    var lease_duration_in_years : String = "",
    var lease_duration_in_years_type : String = "",
    var lease_amount : String = "",
    var lease_negotiable : String = ""
)