package com.toletspot.houseforrent

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.net.Uri
import android.provider.Settings
import android.util.DisplayMetrics
import android.view.WindowManager
import androidx.annotation.Dimension
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.amazonaws.services.s3.AmazonS3Client
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.Get_Reels_Data
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.Get_Reels_Property_Data
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.PostPropertyXXX
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.PostUser
import com.toletspot.houseforrent.API.StartUp_API.API_ViewModel
import com.toletspot.houseforrent.Custom_Assets.scaledSp
import com.toletspot.houseforrent.Home_Screen.Common_H_ViewModel
import com.toletspot.houseforrent.Home_Screen.Enquiry_Module.Enquiry_ViewModel
import com.toletspot.houseforrent.Home_Screen.PostProperty_Module.PostProperty_ViewModel
import com.toletspot.houseforrent.Home_Screen.ProfileModule.Profile_ViewModel
import com.toletspot.houseforrent.Home_Screen.Search_Module.Search_ViewModel
import com.toletspot.houseforrent.Home_Screen.Video_Module.Reels_ViewModel
import com.toletspot.houseforrent.Start_Up.Start_Up_ViewModel
import com.toletspot.houseforrent.UI_DataClass.Custom_BottomSheetState
import com.toletspot.houseforrent.UI_DataClass.Custom_PopUpState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.io.File
import java.util.UUID
import kotlin.math.roundToInt

class constants {
    companion object {
         lateinit var activity: MainActivity
          lateinit var Start_Up_ViewModel : Start_Up_ViewModel
          lateinit var Common_H_ViewModel : Common_H_ViewModel
          lateinit var Reels_ViewModel : Reels_ViewModel
          lateinit var Enquiry_ViewModel : Enquiry_ViewModel
          lateinit var Search_ViewModel : Search_ViewModel
          lateinit var PostProperty_ViewModel : PostProperty_ViewModel
          lateinit var Profile_ViewModel : Profile_ViewModel


          const val notificationiconEnable = "notificationiconEnable"
          lateinit var API_Vm : API_ViewModel
        var sharedHelper = sharedHelper()

        val notificationEnabled = "notificationEnabled"
        var REQUEST_CODE = 1001


        const val APP_URL = "https://play.google.com/store/apps/details?id="

        var open_Btm_Sheet by mutableStateOf(Custom_BottomSheetState())

        var open_Popup by mutableStateOf(Custom_PopUpState())



        private var _onHoverEnable = MutableStateFlow(false)
        var onHoverEnable: StateFlow<Boolean> = _onHoverEnable

        fun toggleOnHoverEnable(value : Boolean){
            _onHoverEnable.value = value
        }


        //// profile expire or active default status
        var active = "published"
        var expired = "expired"

        //// AWS

        //AWS
        lateinit var s3Client : AmazonS3Client
        lateinit var fileExt : String
        var requestCodes : Int = 0
        lateinit var filePath: Uri
        lateinit var file: File
        var ACCESS_ID = MainActivity.getAccessId()
        var SECRET_KEY = MainActivity.getSecretKey()
        var BUCKET_NAME = MainActivity.getBucketName()
        var CLOUD_FRONT_URL = MainActivity.getBaseimageUrl()
        var URL_COMPLETED = mutableStateListOf(Pair(0,""))



        var PROFILE_IMAGE_URL = mutableStateOf("")


        val AREA_UNITS = listOf(
            "Sq Ft",
            "Sq M",
            "Sq Yd",
            "Acre",
            "Hectare",
            "Cent",
            "Guntha",
            "Bigha"
        )
        var DeepLinkImageUrl = mutableStateOf("")

        var UpdateVersionCode = mutableStateOf(0)
        var UpdateTimeStamp = mutableStateOf(0L)
        var skipCount = mutableStateOf(0)

        fun DefaultShare(content: String ,value :Int ){
            println("VALUE ___ ${value}")
            val sendIntent: Intent =
                Intent().apply {
                    action =
                        Intent.ACTION_SEND
                    putExtra(
                        Intent.EXTRA_TEXT,
                        when(value) {
                            0 ->  activity.getString(R.string.share_Content_Profile)
                            1 -> activity.getString(R.string.share_Content_Post)
                            else -> activity.getString(R.string.share_Content_App)
                        }

                            .plus("\n${content}")
                    )
                    type =
                        "text/plain"
                }
            val shareIntent =
                Intent.createChooser(
                    sendIntent,
                    null
                )
            activity.startActivity(
                shareIntent
            )
        }


        //// Text Size

//        @Composable
//        fun textUnit(size: Int): TextUnit {
//
//            return size.scaledSp
//        }

        @Composable
        fun textUnit(size: Int): TextUnit {
            val isTablet = forTab() // from your earlier Compose helper

            val final = when (size) {
                24 -> if (isTablet) 28.scaledSp else 24.scaledSp
                20 -> if (isTablet) 24.scaledSp else 20.scaledSp
                15 -> if (isTablet) 18.scaledSp else 15.scaledSp
                12 -> if (isTablet) 15.scaledSp else 12.scaledSp
                14 -> if (isTablet) 17.scaledSp else 14.scaledSp
                16 -> if (isTablet) 20.scaledSp else 16.scaledSp
                else -> size.scaledSp
            }

            return final
        }


        @Composable
        fun spacer(size: Int) {
            val isTablet = forTab() // or forTab()

            val space =  when (size) {
                2 -> if (isTablet) 4.dp else 2.dp
                4 -> if (isTablet) 8.dp else 4.dp
                6 -> if (isTablet) 10.dp else 6.dp
                8 -> if (isTablet) 12.dp else 8.dp
                12 -> if (isTablet) 16.dp else 12.dp
                16 -> if (isTablet) 20.dp else 16.dp
                20 -> if (isTablet) 24.dp else 20.dp
                else -> size.dp
            }

            Spacer(modifier = Modifier.padding(space))
        }



        fun fontFamily(isWhat : Int): FontFamily{
            return when (isWhat){
                0 -> FontFamily(Font(R.font.nunito_bold))
                1 -> FontFamily(Font(R.font.nunito_semibold))
                2 -> FontFamily(Font(R.font.nunito_medium))
                3 -> FontFamily(Font(R.font.nunito_regular))
                else -> {
                    FontFamily(Font((R.font.nunito_regular)))
                }
            }
        }


        @SuppressLint("HardwareIds")
        fun GetDevice_UDID(activity: MainActivity): String {

            val androidId = Settings.Secure.getString(
                activity.contentResolver, Settings.Secure.ANDROID_ID
            )
            val androidId_UUID = UUID.nameUUIDFromBytes(androidId.toByteArray(charset("utf8")))
            val device_UDID = androidId_UUID.toString()

            return device_UDID
        }

        @Dimension(unit = Dimension.DP)
        fun pxToDp(windowManager: WindowManager, @Dimension(unit = Dimension.PX) px: Int): Int {
            val displayMetrics = DisplayMetrics()
            windowManager.defaultDisplay.getRealMetrics(displayMetrics)
            return (px / displayMetrics.densityDpi.toFloat() * DisplayMetrics.DENSITY_DEFAULT).roundToInt()
        }


        fun getScreenWidth(): Int {
            return Resources.getSystem().displayMetrics.widthPixels
        }

        fun getScreenHeight(): Int {
            return Resources.getSystem().displayMetrics.heightPixels
        }


    }
}

@Composable
fun CommonText(
    text: String,
    color: Color,
    fontSize: Int,
    fontFamily: Int,
    modifier: Modifier = Modifier
) {

    Text(
        text = text,
        color = color,
        fontSize = constants.textUnit(fontSize),
        fontFamily = constants.fontFamily(fontFamily),
        modifier = modifier
    )

}


data class Sort_Filter_Field_DC(
    var user_Id: Int = AppPreferences.getUserId(),
    var short_by: Int? = 0,
    var recently_posted_date: String? = "",
    var land_categorie_id:  List<Int?> = emptyList(),
    var land_type_id: Int? = 0,
    var property_area_unit: String? = "",
    var property_area_from: String? = "",
    var property_area_to: String? = "",
    var budget_from: String? = "",
    var budget_to: String? = "",
    var posted_by: List<Int?> = emptyList(),
    var ownership: List<String?> = emptyList(),
   // var availability_status: List<String?> = emptyList(),
    var floor_plan: List<String?> = emptyList(),
    var furnishing_status: List<String?> = emptyList(),
    var parking_available: List<String?> = emptyList(),
    var no_of_open_sides: List<String?> = emptyList(),
    var floor_preferences: List<Int?> = emptyList(),
    var property_facing: List<String?> = emptyList(),
    var amenities: List<String?> = emptyList(),
    var property_highlights: List<String?> = emptyList(),
    var business_type: List<String?> = emptyList(),
    //var authority_approved: List<String?> = emptyList(),
    var search_Text: String? = "",   // your own field for search string


    var available_from : List<Int?> = emptyList(),
    var available_for : List<String?> = emptyList(),
    var food_preference : List<String?> = emptyList(),
    var pets_allowed : List<String?> = emptyList(),
    var agreement_type : List<String?> = emptyList(),
    var with_photos : String? = "",
    var bedrooms : List<String?> = emptyList(),
    var approved : List<String?> = emptyList(),
    var posted_date : List<Int?> = emptyList(),
    var rent_type : List<String?> = emptyList()


)

//, colors = ListItemColors(
//containerColor = newWhite,
//headlineColor = Color.Black,
//leadingIconColor = Color.DarkGray,
//overlineColor = Color.Gray,
//supportingTextColor = Color.Gray,
//trailingIconColor = Color.LightGray,
//disabledHeadlineColor = Color.Gray.copy(alpha = 0.5f),
//disabledLeadingIconColor = Color.Gray.copy(alpha = 0.5f),
//disabledTrailingIconColor = Color.Gray.copy(alpha = 0.5f)
//)



var profileChangeErrorMessage = mutableStateOf("")


@SuppressLint("LocalContextConfigurationRead")
@Composable
fun forTab(): Boolean {
    val context = LocalContext.current
    val configuration = context.resources.configuration
    val screenLayout = configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK
    return screenLayout >= Configuration.SCREENLAYOUT_SIZE_LARGE
}



fun Get_Reels_Data.toPostUser(): PostUser {
    return PostUser(
        cities = this.cities,
        country = this.country,
        email = this.email,
        enquiry = this.enquiry,
        is_liked = this.is_liked,
        is_saved = this.is_saved,
        name = this.name,
        phone_num = this.phone_num,
        phone_num_cc = this.phone_num_cc,
        post_property = this.post_property.toPostPropertyXXX(),
        profile_image = this.profile_image,
        state = this.state,
        thumbnail = this.thumbnail,
        total_comments = this.total_comments,
        total_likes = this.total_likes,
        user_id = this.user_id,
        user_post_id = this.user_post_id,
        username = this.username,
        whatsapp_num = this.whatsapp_num,
        whatsapp_num_cc = this.whatsapp_num_cc,
        post_interest = this.post_interest
    )
}

fun Get_Reels_Property_Data.toPostPropertyXXX(): PostPropertyXXX {
    return PostPropertyXXX(
        address = this.address,
        pincode = this.pincode ?: "",
        is_sold = this.is_sold,
        parking_available = this.parking_available,
        amenities = this.amenities,
        area_length = this.area_length,
        area_width = this.area_width,
        //availability_status = this.availability_status,
        bhk_type = this.bhk_type,
        boundary_wall = this.boundary_wall,
        built_up_area = this.built_up_area,
        carpet_area = this.carpet_area,
        central_ac = this.central_ac,
        city = this.city,
        conference_room = this.conference_room,
        country = this.country,
        created_at = this.created_at,
        does_local_authority = this.does_local_authority,
        facade_height = this.facade_height,
        facade_width = this.facade_width,
        fire_safety_measures = this.fire_safety_measures,
        furnishing_status = this.furnishing_status,
        //is_it_pre_leased_pre_rented = this.is_it_pre_leased_pre_rented,
        is_report = this.is_report,
        land_categorie_id = this.land_categorie_id,
        landCategoryText = this.landCategoryText ?: "",
        land_type_id = this.land_type_id,
        landTypeText = this.landTypeText ?: "",
        latitude = this.latitude,
        lifts = this.lifts,
        locality = this.locality,
        longitude = this.longitude,
        max_of_seats = this.max_of_seats,
        min_of_seats = this.min_of_seats,
        no_of_balconies = this.no_of_Balconies,
        no_of_bathrooms = this.no_of_Bathrooms,
        no_of_bedrooms = this.no_of_bedrooms,
        no_of_cabins = this.no_of_cabins,
        no_of_meeting_rooms = this.no_of_meeting_rooms,
        no_of_open_sides = this.no_of_open_sides,
        no_of_staircases = this.no_of_Staircases,
        noc_certified = this.noc_certified,
        occupancy_certificate = this.occupancy_certificate,
        //office_previously_used_for = this.office_previously_used_for,
        other_rooms = this.other_rooms,
        oxygen_duct = this.oxygen_duct,
        pantry = this.pantry,
        pantry_size = this.pantry_size,
        //price = this.price,
        //price_negotiable = this.price_negotiable,
        property_area = this.property_area,
        property_facing = this.property_facing,
        //property_floor_no = this.property_floor_no,
        property_highlights = this.property_highlights,
        property_name = this.property_name,
        //property_ownership = this.property_ownership,
        reception_area = this.reception_area,
        state = this.state,
        suitable_business_type = this.suitable_business_type,
        super_built_up_area = this.super_built_up_area,
        thumbnail = this.thumbnail,
        total_floor = this.total_floor,
        ups = this.ups,
        user_post_id = this.user_post_id,
        user_type = this.user_type,
        // video = this.video,
        washroom_details = this.washroom_details,
        //which_local_authority = this.which_local_authority,
        video = this.video,
        draft = this.draft,
        agreement_type = this.agreement_type,
        availability_from = this.availability_from,
        deposit_amount_month_of_rents = this.deposit_amount_month_of_rents,
        deposit_amount_month_of_rents_type = this.deposit_amount_month_of_rents_type,
        duration_of_agreement = this.duration_of_agreement,
        duration_of_agreement_type = this.duration_of_agreement_type,
        food_preferences = this.food_preferences,
        is_this_property_for_rent_or_lease = this.is_this_property_for_rent_or_lease,
        lease_amount = this.lease_amount,
        lease_duration_in_years = this.lease_duration_in_years,
        lease_negotiable = this.lease_negotiable,
        lock_in_period = this.lock_in_period,
        lock_in_period_type = this.lock_in_period_type,
        map_config = this.map_config,
        notice_period = this.notice_period,
        pets_allowed = this.pets_allowed,
        post_type = this.post_type,
        preferred_tenants = this.preferred_tenants,
        property_area_unit = this.property_area_unit,
        rent = this.rent,
        rent_floor_no = this.rent_floor_no,
        rent_negotiable = this.rent_negotiable,
        total_deposit = this.total_deposit,
        images = this.images,
        facade_height_unit = this.facade_height_unit,
        area_length_unit = this.area_length_unit,
        area_width_unit = this.area_width_unit,
        built_up_area_unit = this.built_up_area_unit,
        carpet_area_unit = this.carpet_area_unit,
        facade_width_unit = this.facade_width_unit,
        pantry_size_unit = this.pantry_size_unit,
        super_built_up_area_unit = this.super_built_up_area_unit,
        U_ID = this.U_ID,
        status = this.status,
    )
}


val grayscaleMatrix = ColorMatrix().apply {
    setToSaturation(0f)
}