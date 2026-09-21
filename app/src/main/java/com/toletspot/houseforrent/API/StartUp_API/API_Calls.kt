package com.toletspot.houseforrent.API.StartUp_API

import androidx.navigation.NavHostController
import com.toletspot.houseforrent.API.API_Result_Handling
import com.toletspot.houseforrent.AppPreferences
import com.toletspot.houseforrent.Custom_Assets.getDeviceId
import com.toletspot.houseforrent.Custom_Assets.getDeviceType
import com.toletspot.houseforrent.Custom_Assets.toast
import com.toletspot.houseforrent.Navigation.UserCredentialsScreenFlow
import com.toletspot.houseforrent.isConnected
import com.toletspot.houseforrent.constants
import com.toletspot.houseforrent.constants.Companion.PROFILE_IMAGE_URL
import com.toletspot.houseforrent.deviceToken


/// register api call

fun register_API_Call (
    resultCallback: (Int) -> Unit
) {


    println("IS THERE USER ID -- ${AppPreferences.getUserId()}")

   // if (isConnected.value) {
        constants.API_Vm.user_Register(
            name = constants.Start_Up_ViewModel.userName,
            phone_num = constants.Start_Up_ViewModel.phoneNumber,
            device_id = getDeviceId(constants.activity),
            device_type = getDeviceType(),
            device_token = deviceToken,
            phone_num_cc = constants.Start_Up_ViewModel.get_Country_Code(),
        )
        { apiResultHandling ->
            when (apiResultHandling) {
                is API_Result_Handling.Loading -> {
                    //state = true
                    resultCallback(2)
                    constants.Common_H_ViewModel.changeStatus(true)
                }

                is API_Result_Handling.NoData -> {
                    //state = false
                    constants.Common_H_ViewModel.changeStatus(false)
                    toast("Something went wrong , No Records found")
                }

                is API_Result_Handling.Error -> {
                    //state = false
                    resultCallback(1)
                    constants.Common_H_ViewModel.changeStatus(false)
                    toast(apiResultHandling.message)
                }

                is API_Result_Handling.Success -> {
                    //state = false
                    resultCallback(0)
                    constants.Common_H_ViewModel.changeStatus(false)
                    toast(constants.Start_Up_ViewModel.get_OTP_Response())
                    constants.Start_Up_ViewModel.updateLoginState(2)
                }

                is API_Result_Handling.Deactivated -> {
                    resultCallback(5)
                }
            }
        }
   // }
}


/// login api call

fun login_API_Call ()  {

   // if (isConnected.value) {

        constants.API_Vm.user_Login(
            phone_num = constants.Start_Up_ViewModel.phoneNumber,
            phone_num_cc = constants.Start_Up_ViewModel.get_Country_Code(),
            device_id = getDeviceId(constants.activity),
            device_type = getDeviceType(),
            device_token = deviceToken,
        )
        { apiResultHandling ->
            when (apiResultHandling) {
                is API_Result_Handling.Loading -> {
                    //state = true
                    constants.Common_H_ViewModel.changeStatus(true)
                }

                is API_Result_Handling.NoData -> {
                    //state = false
                    constants.Common_H_ViewModel.changeStatus(false)
                    toast("Something went wrong , No Records found")
                }

                is API_Result_Handling.Error -> {
                    //state = false
                    constants.Common_H_ViewModel.changeStatus(false)
                    toast(apiResultHandling.message)
                }

                is API_Result_Handling.Success -> {
                    //state = false
                    constants.Common_H_ViewModel.changeStatus(false)
                    toast(constants.Start_Up_ViewModel.get_OTP_Response())
                    constants.Start_Up_ViewModel.updateLoginState(2)
                }

                is API_Result_Handling.Deactivated -> {
                    //resultCallback(5)
                }
            }
        }
   // }
}


/// verify api call

fun  verify_Otp_API_Call (navController: NavHostController)  {

   // if (isConnected.value) {

        constants.API_Vm.verify_OTP(
            user_id = AppPreferences.getUserId(),
            phone_num = constants.Start_Up_ViewModel.phoneNumber,
            whatsapp_num = "",
            email = "",
            otp = constants.Start_Up_ViewModel.get_OTP_Response(),
            phone_num_cc = constants.Start_Up_ViewModel.get_Country_Code(),
            whatsapp_num_cc = "",
            device_id = getDeviceId(constants.activity),
            device_type = "Android",
            device_token = deviceToken,
        )
        { apiResultHandling ->
            when (apiResultHandling) {
                is API_Result_Handling.Loading -> {
                    //state = true
                    constants.Common_H_ViewModel.change_Verify_Status(true)
                }

                is API_Result_Handling.NoData -> {
                    //state = false
                    constants.Common_H_ViewModel.change_Verify_Status(false)
                    toast("Something went wrong , No Records found")
                }

                is API_Result_Handling.Error -> {
                    //state = false
                    constants.Common_H_ViewModel.change_Verify_Status(false)
                    toast(apiResultHandling.message)
                }

                is API_Result_Handling.Success -> {
                    //state = false
                    constants.Common_H_ViewModel.change_Verify_Status(false)
                    toast("Success , verified")
                    if (AppPreferences.get_Interest_Completed() == 0 || AppPreferences.get_Location_Received() == 0){
                       // UserCredentialsScreenFlow.UserInterests.route
                        navController.navigate(UserCredentialsScreenFlow.UserInterests.route)
                    }
                    else {
                        UserCredentialsScreenFlow.Common_Screen.route
                        navController.navigate(UserCredentialsScreenFlow.Common_Screen.route)
                    }

                }

                is API_Result_Handling.Deactivated -> {
                    //resultCallback(5)
                }
            }
        }
    //}
}


/// add user interests api call

fun Put_User_Interests_API_Call(resultCallback: (Int) -> Unit) {
   if (isConnected.value) {
        constants.API_Vm.put_User_Interests(
            user_id = AppPreferences.getUserId(),
            user_interest = constants.Start_Up_ViewModel.selectedCategoryIds.value.joinToString(",")
        )
        { apiResultHandling ->
            when (apiResultHandling) {
                is API_Result_Handling.Loading -> {
                    constants.Common_H_ViewModel.changeStatus(true)

                    resultCallback(2)
                }

                is API_Result_Handling.NoData -> {
                    constants.Common_H_ViewModel.changeStatus(false)
                }

                is API_Result_Handling.Error -> {
                    constants.Common_H_ViewModel.changeStatus(false)

                    resultCallback(1)
                }

                is API_Result_Handling.Success -> {
                    constants.Common_H_ViewModel.changeStatus(false)

                    resultCallback(0)
                }

                is API_Result_Handling.Deactivated -> {
                    resultCallback(5)
                }
            }
        }
   }
}

/// add user interests partocular api call

fun get_User_Interest_Particular_API_Call(resultCallback: (Int) -> Unit) {
   if (isConnected.value) {
        constants.API_Vm.get_User_Interest_Particular(
            user_id = AppPreferences.getUserId(),
        )
        { apiResultHandling ->
            when (apiResultHandling) {
                is API_Result_Handling.Loading -> {
                    constants.Common_H_ViewModel.changeStatus(true)

                    resultCallback(2)
                }

                is API_Result_Handling.NoData -> {
                    constants.Common_H_ViewModel.changeStatus(false)
                }

                is API_Result_Handling.Error -> {
                    constants.Common_H_ViewModel.changeStatus(false)

                    resultCallback(1)
                }

                is API_Result_Handling.Success -> {
                    constants.Common_H_ViewModel.changeStatus(false)

                    resultCallback(0)
                }

                is API_Result_Handling.Deactivated -> {
                    resultCallback(5)
                }
            }
        }
   }
}


//// add user location

fun put_User_Location_API_Call(resultCallback: (Int) -> Unit){
    if (isConnected.value) {
        constants.API_Vm.put_User_Location(
            user_id = AppPreferences.getUserId(),
            country = constants.Start_Up_ViewModel.country.value,
            state = constants.Start_Up_ViewModel.state.value,
            cities = constants.Start_Up_ViewModel.city.value,
            pincode = constants.Start_Up_ViewModel.pincode.value,
            latitude = constants.Start_Up_ViewModel.latitude.value,
            longitude = constants.Start_Up_ViewModel.longitude.value,
        )
        { apiResultHandling ->
            when (apiResultHandling) {
                is API_Result_Handling.Loading -> {
                    resultCallback(2)
                }

                is API_Result_Handling.Success -> {
                    resultCallback(0)
                    AppPreferences.save_Lat_Long(lat = constants.Start_Up_ViewModel.latitude.value , constants.Start_Up_ViewModel.longitude.value)
                }

                is API_Result_Handling.Error -> {
                    resultCallback(1)
                }

                is API_Result_Handling.NoData -> {}

                is API_Result_Handling.Deactivated -> {
                    resultCallback(5)
                }
            }
        }
    }
}


/// get user profile api call

fun Get_User_Profile_API_Call(resultCallback: (Int) -> Unit){

        constants.API_Vm.get_User_Profile(
            user_id = AppPreferences.getUserId(),
            others_id = constants.Profile_ViewModel.get_Other_User_Id()
        )
        { apiResultHandling ->
            when (apiResultHandling) {
                is API_Result_Handling.Loading -> {
                    resultCallback(0)
                }

                is API_Result_Handling.NoData -> {
                    resultCallback(3)
                }
                is API_Result_Handling.Error -> {
                   // apiOnce.value = true
                    resultCallback(2)
                }

                is API_Result_Handling.Success -> {
                   // apiOnce.value = true
                    resultCallback(1)
                }

                is API_Result_Handling.Deactivated -> {
                    resultCallback(5)
                }
            }
        }
}

/// update user profile
fun update_User_Profile_API_Call() {




    constants.API_Vm.update_User_Profile(
        user_id = AppPreferences.getUserId(),
        name = constants.Profile_ViewModel.get_New_Realname(),
        bio = constants.Profile_ViewModel.change_Bio_Content.value,
        profile_image = PROFILE_IMAGE_URL.value.ifEmpty { AppPreferences.get_ProfileImage() },
    ){
        apiResultHandling ->
        when(apiResultHandling){
            is API_Result_Handling.Error -> {
               //errror
                constants.Profile_ViewModel.change_Update_profile(false)
            }
            is API_Result_Handling.NoData -> {
                // no data
            }
            is API_Result_Handling.Loading -> {
               //loading
                constants.Profile_ViewModel.change_Update_profile(true)
            }
            is API_Result_Handling.Success -> {
                constants.Profile_ViewModel.enable_Edit_Profile()
                constants.Profile_ViewModel.change_Update_profile(false)
                constants.Profile_ViewModel.set_From_Profile_Pic_Update(false)

                AppPreferences.save_ProfileImage(PROFILE_IMAGE_URL.value.ifEmpty { AppPreferences.get_ProfileImage() })
                //AppPreferences.save_User_Name(constants.Profile_ViewModel.get_new_username())
                AppPreferences.save_Real_Name(constants.Profile_ViewModel.get_New_Realname())
                AppPreferences.save_ProfileBio(constants.Profile_ViewModel.change_Bio_Content.value)


                println("ADCJBDCDACADC${constants.Profile_ViewModel.change_Bio_Content.value} -${ constants.Profile_ViewModel.get_new_Bio_Content()}-- ${ PROFILE_IMAGE_URL.value} -- ${constants.Profile_ViewModel.get_New_Realname()}")

//                when {
                    if(PROFILE_IMAGE_URL.value.isNotEmpty()) {
                        constants.Profile_ViewModel.updateOwnProfile(profileImage = PROFILE_IMAGE_URL.value)
                        println("DATA CONFIRMATION profile iamge-- ${constants.Profile_ViewModel.get_Content_own_Profile()}")
                    }
                    if(constants.Profile_ViewModel.get_New_Realname().isNotEmpty())  {
                        constants.Profile_ViewModel.updateOwnProfile(name = constants.Profile_ViewModel.get_New_Realname())
                        println("DATA CONFIRMATION name -- ${constants.Profile_ViewModel.get_Content_own_Profile()}")

                    }
                    if(constants.Profile_ViewModel.change_Bio_Content.value.isNotEmpty() ) {
                        println("DATA CONFIRMATION bio-222- ${constants.Profile_ViewModel.change_Bio_Content.value}")

                        constants.Profile_ViewModel.updateOwnProfile(bio = constants.Profile_ViewModel.change_Bio_Content.value)
                        println("DATA CONFIRMATION bio-- ${constants.Profile_ViewModel.get_Content_own_Profile()}")

                    }

//                }

                toast("Profile Updated Successfully")


                println("API STORING PROFILE IMAGE -- ${AppPreferences.get_ProfileImage()} -- ${AppPreferences.get_Real_Name()}")

                //success
            }

            is API_Result_Handling.Deactivated -> {
                //resultCallback(5)
            }
        }
    }
}



/// follow / unfollow / delete

fun follow_Unfollow_Delete_API_Call(resultCallback: (Int) -> Unit){
    constants.API_Vm.follow_Unfollow(
        user_id = AppPreferences.getUserId(),
        following_id = constants.Profile_ViewModel.get_Following_Id() ,
        status = constants.Profile_ViewModel.get_follow_unfollow_Status(),
    )
    {
            apiResultHandling ->
        when(apiResultHandling){
            is API_Result_Handling.Error -> {
                resultCallback(1)
                //errror
                //constants.Profile_ViewModel.change_Update_profile(false)
            }
            is API_Result_Handling.NoData -> {
                // no data
            }
            is API_Result_Handling.Loading -> {
                resultCallback(2)
                //loading
               // constants.Profile_ViewModel.change_Update_profile(true)
            }
            is API_Result_Handling.Success -> {
                resultCallback(0)
               // constants.Profile_ViewModel.enable_Edit_Profile()
               // constants.Profile_ViewModel.change_Update_profile(false)
                //success
            }

            is API_Result_Handling.Deactivated -> {
                resultCallback(5)
            }
        }
    }
}





/// post form 1

fun post_Form_1_API_Call(resultCallback: (Int) -> Unit){
    constants.API_Vm.put_Post_Form1(
        user_id = AppPreferences.getUserId(),
        user_post_id = AppPreferences.get_Post_Id(),
        user_type = constants.PostProperty_ViewModel.selected_User_Type_1PF.value
    )
    {
        apiResultHandling ->

        when(apiResultHandling){
            is API_Result_Handling.Loading -> {
                // loading
                constants.PostProperty_ViewModel.change_Status_PFs(true)
            }
            is API_Result_Handling.Error -> {
                // fail
                resultCallback(0)
                constants.PostProperty_ViewModel.change_Status_PFs(false)
                toast("OOPs! Something went wrong , Try again later")
            }
            is API_Result_Handling.Success -> {
                // success
                resultCallback(1)
                constants.PostProperty_ViewModel.change_Status_PFs(false)
            }
            is API_Result_Handling.NoData -> {
                // no data
                constants.PostProperty_ViewModel.change_Status_PFs(false)
            }

            is API_Result_Handling.Deactivated -> {
                resultCallback(5)
            }
        }
    }
}


// get land categories post form 2



fun get_Land_Categories_PF2_API_Call(resultCallback: (Int) -> Unit){
    constants.API_Vm.get_Post_Form2_Land_Types(
        status = constants.PostProperty_ViewModel.selected_Land_Type_PF2.value
    ){
            apiResultHandling ->

        println("LAND CATEGORY TYPE --- ${constants.PostProperty_ViewModel.selected_Land_Type_PF2.value}")

        when(apiResultHandling){
            is API_Result_Handling.Loading -> {
                // loading
                constants.PostProperty_ViewModel.change_Status_Land_Types(true)
            }
            is API_Result_Handling.Error -> {
                // fail
                resultCallback(0)
                constants.PostProperty_ViewModel.change_Status_Land_Types(false)
                toast("OOPs! Something went wrong , Try again later")
            }
            is API_Result_Handling.Success -> {
                // success
                resultCallback(1)
                constants.PostProperty_ViewModel.change_Status_Land_Types(false)
            }
            is API_Result_Handling.NoData -> {
                // no data
                constants.PostProperty_ViewModel.change_Status_Land_Types(false)
            }

            is API_Result_Handling.Deactivated -> {
                resultCallback(5)
            }
        }
    }
}


//  post form 2

fun post_Form_2_API_Call(resultCallback: (Int) -> Unit){
    constants.API_Vm.put_Post_Form2(
        user_id = AppPreferences.getUserId(),
        user_post_id = AppPreferences.get_Post_Id(),
        status = constants.PostProperty_ViewModel.selected_Land_Type_PF2.value,
        land_categorie_id = constants.PostProperty_ViewModel.selected_Land_Cat_Id.value
    ){
            apiResultHandling ->

        when(apiResultHandling){
            is API_Result_Handling.Loading -> {
                // loading
                constants.PostProperty_ViewModel.change_Status_PFs(true)
            }
            is API_Result_Handling.Error -> {
                // fail
                resultCallback(0)
                constants.PostProperty_ViewModel.change_Status_PFs(false)
                toast("OOPs! Something went wrong , Try again later")
            }
            is API_Result_Handling.Success -> {
                // success
                resultCallback(1)
                constants.PostProperty_ViewModel.change_Status_PFs(false)
            }
            is API_Result_Handling.NoData -> {
                // no data
                constants.PostProperty_ViewModel.change_Status_PFs(false)
            }

            is API_Result_Handling.Deactivated -> {
                resultCallback(5)
            }
        }
    }
}


// post form 3

fun post_Form_3_API_Call(resultCallback: (Int) -> Unit){
    constants.API_Vm.put_Post_Form3(
        user_id = AppPreferences.getUserId(),
        user_post_id = AppPreferences.get_Post_Id(),
        country = constants.PostProperty_ViewModel.get_pp3_Data()?.country ?:"",
        state = constants.PostProperty_ViewModel.get_pp3_Data()?.state ?:"",
        city = constants.PostProperty_ViewModel.get_pp3_Data()?.city ?:"",
        pincode = constants.PostProperty_ViewModel.get_pp3_Data()?.pincode ?:"",
        locality = constants.PostProperty_ViewModel.get_pp3_Data()?.locality ?:"",
        map_config = if(constants.PostProperty_ViewModel.form3ShowMap.value) "1" else "0",
        latitude = constants.PostProperty_ViewModel.pinned_Lat_Long.value?.latitude.toString() ?:"",
        longitude = constants.PostProperty_ViewModel.pinned_Lat_Long.value?.longitude.toString() ?:"",
    )
    {
            apiResultHandling ->

        when(apiResultHandling){
            is API_Result_Handling.Loading -> {
                // loading
                constants.PostProperty_ViewModel.change_Status_PFs(true)
            }
            is API_Result_Handling.Error -> {
                // fail
                resultCallback(0)
                constants.PostProperty_ViewModel.change_Status_PFs(false)
                toast("OOPs! Something went wrong , Try again later")
            }
            is API_Result_Handling.Success -> {
                // success
                resultCallback(1)
                constants.PostProperty_ViewModel.change_Status_PFs(false)
            }
            is API_Result_Handling.NoData -> {
                // no data
                constants.PostProperty_ViewModel.change_Status_PFs(false)
            }

            is API_Result_Handling.Deactivated -> {
                resultCallback(5)
            }
        }
    }
}


// post form 4 get fields residential

fun get_Post_Property_Form4_Fields_API(resultCallback: (Int) -> Unit){
    constants.API_Vm.get_post_Form4_Residential(
        land_categorie_id = constants.PostProperty_ViewModel.get_Selected_Land_Cat_Id()
    ){
            apiResultHandling ->

        when(apiResultHandling){
            is API_Result_Handling.Loading -> {
                // loading
                resultCallback(2)
               // constants.PostProperty_ViewModel.change_Status_PFs(true)
            }
            is API_Result_Handling.Error -> {
                // fail
                resultCallback(0)
                ///constants.PostProperty_ViewModel.change_Status_PFs(false)
            }
            is API_Result_Handling.Success -> {
                // success
                resultCallback(1)
               // constants.PostProperty_ViewModel.change_Status_PFs(false)
            }
            is API_Result_Handling.NoData -> {
                // no data
               // constants.PostProperty_ViewModel.change_Status_PFs(false)
            }
            is API_Result_Handling.Deactivated -> {
                resultCallback(5)
            }
        }
    }
}

// post form 5

fun post_Form_5_API_Call(resultCallback: (Int) -> Unit){
    constants.API_Vm.put_Post_Form5(
        user_id = AppPreferences.getUserId(),
        user_post_id = AppPreferences.get_Post_Id(),
        price = constants.PostProperty_ViewModel.budget_Price_PF5.value,
        price_negotiable = constants.PostProperty_ViewModel.get_price_negotiation()
    )
    {
            apiResultHandling ->

        when(apiResultHandling){
            is API_Result_Handling.Loading -> {
                // loading
                constants.PostProperty_ViewModel.change_Status_PFs(true)
            }
            is API_Result_Handling.Error -> {
                // fail
                resultCallback(0)
                constants.PostProperty_ViewModel.change_Status_PFs(false)
                toast("OOPs! Something went wrong , Try again later")
            }
            is API_Result_Handling.Success -> {
                // success
                resultCallback(1)
                constants.PostProperty_ViewModel.change_Status_PFs(false)
            }
            is API_Result_Handling.NoData -> {
                // no data
                constants.PostProperty_ViewModel.change_Status_PFs(false)
            }
            is API_Result_Handling.Deactivated -> {
                resultCallback(5)
            }
        }
    }
}
// post form 5

fun post_Form5Rento_APICALL(resultCallback: (Int) -> Unit){

    val selectedForm = constants.PostProperty_ViewModel.get_Selected_Fields_Form()

    constants.API_Vm.post_Form5Rento(
        user_id = AppPreferences.getUserId(),
        user_post_id = AppPreferences.get_Post_Id(),
        property_for_rent_or_lease = selectedForm.property_for_rent_or_lease,
        rent = selectedForm.rent ,
        rent_negotiable = if(selectedForm.rent_negotiable) "1" else "0",
        deposit_amount_month_of_rents = selectedForm.deposit_amount_month_of_rents  ,
        deposit_amount_month_of_rents_type = selectedForm.deposit_amount_month_of_rents_type,
        total_deposit = selectedForm.total_deposit,
        duration_of_agreement = selectedForm.duration_of_agreement,
        duration_of_agreement_type = selectedForm.duration_of_agreement_type,
        lock_in_period = selectedForm.lock_in_period ,
        lock_in_period_type = selectedForm.lock_in_period_type ,
        notice_period = selectedForm.notice_period ,
       // notice_period_type = selectedForm.notice_period_type,
        lease_duration_in_years = selectedForm.lease_duration_in_years ,
        lease_duration_in_years_type = selectedForm.lease_duration_in_years_type,
        lease_amount = selectedForm.lease_amount ,
        lease_negotiable = if(selectedForm.lease_negotiable) "1" else "0",
    )
    {
            apiResultHandling ->

        when(apiResultHandling){
            is API_Result_Handling.Loading -> {
                // loading
                constants.PostProperty_ViewModel.change_Status_PFs(true)
            }
            is API_Result_Handling.Error -> {
                // fail
                resultCallback(0)
                constants.PostProperty_ViewModel.change_Status_PFs(false)
                toast("OOPs! Something went wrong , Try again later")
            }
            is API_Result_Handling.Success -> {
                // success
                resultCallback(1)
                constants.PostProperty_ViewModel.change_Status_PFs(false)
            }
            is API_Result_Handling.NoData -> {
                // no data
                constants.PostProperty_ViewModel.change_Status_PFs(false)
            }
            is API_Result_Handling.Deactivated -> {
                resultCallback(5)
            }
        }
    }
}


fun put_post_Form4_API_CALL(resultCallback: (Int) -> Unit) {
    val selectedForm = constants.PostProperty_ViewModel.get_Selected_Fields_Form()

    println("STEP 4 FORM VALUE PASSING  preferred_tenants-- ${ selectedForm.property_preferred_tenants}")
    println("STEP 4 FORM VALUE PASSING -- ${ selectedForm.property_preferred_tenants}")

    println("========== STEP 4 API SEND DATA ==========")
    println("user_id = ${AppPreferences.getUserId()}")
    println("user_post_id = ${AppPreferences.get_Post_Id()}")
    println("property_name = ${selectedForm.property_Name}")
    println("property_area = ${selectedForm.property_Name}")
    println("property_area_unit = ${selectedForm.property_area_unit}")
    println("carpet_area = ${selectedForm.property_Carpet_Area}")
    println("carpet_area_unit = ${selectedForm.carpet_area_unit}")
    println("built_up_area = ${selectedForm.property_Builtup_Area}")
    println("built_up_area_unit = ${selectedForm.built_up_area_unit}")
    println("super_built_up_area = ${selectedForm.property_Super_Builtup_Area}")
    println("super_built_up_area_unit = ${selectedForm.super_built_up_area_unit}")
    println("area_length = ${selectedForm.property_Area_Dimension_Length}")
    println("area_length_unit = ${selectedForm.property_Area_Dimension_Length_Unit}")
    println("area_width = ${selectedForm.property_Area_Dimension_Width}")
    println("area_width_unit = ${selectedForm.property_Area_Dimension_Width_Unit}")
    println("property_facing = ${selectedForm.property_Facing}")
    println("total_floor = ${selectedForm.property_Floor_Det_Total}")
    println("rent_floor_no = ${selectedForm.property_Floor_Det_Which}")
    println("preferred_tenants = ${selectedForm.property_preferred_tenants}")
    println("availability_from = ${selectedForm.property_availability_from}")
    println("agreement_type = ${selectedForm.property_agreement_type}")
    println("food_preferences = ${selectedForm.property_food_preferences}")
    println("pets_allowed = ${selectedForm.property_pets_allowed}")
    println("furnishing_status = ${selectedForm.property_Furnished}")
    println("boundary_wall = ${selectedForm.property_Boundary_Wall}")
    println("parking_available = ${selectedForm.property_Parking}")
    println("amenities = ${selectedForm.property_Amenities}")
    println("property_highlights = ${selectedForm.property_Highlights}")
    println("bhk_type = ${selectedForm.property_Floor_Plan_Bhk}")
    println("no_of_bedrooms = ${selectedForm.property_No_of_Beds}")
    println("no_of_Bathrooms = ${selectedForm.property_No_of_Baths}")
    println("no_of_Balconies = ${selectedForm.property_No_of_Balconies}")
    println("no_of_open_sides = ${selectedForm.property_No_Of_OpenSides}")
    println("other_rooms = ${selectedForm.property_Other_Rooms}")
    println("facade_width = ${selectedForm.property_Facade_Width}")
    println("facade_width_unit = ${selectedForm.facade_width_unit}")
    println("facade_height = ${selectedForm.property_Facade_Height}")
    println("facade_height_unit = ${selectedForm.facade_height_unit}")
    println("property_condition = ${selectedForm.property_condition}")
    println("no_of_cabins = ${selectedForm.property_No_Of_Cabins}")
    println("no_of_meeting_rooms = ${selectedForm.property_No_Of_Meeting_Rooms}")
    println("min_of_seats = ${selectedForm.property_Min_No_Of_Seats}")
    println("max_of_seats = ${selectedForm.property_Max_No_Of_Seats}")
    println("conference_room = ${selectedForm.property_Conference_Room}")
    println("no_of_Staircases = ${selectedForm.property_No_Of_Stairs}")
    println("reception_area = ${selectedForm.property_Reception}")
    println("pantry = ${selectedForm.property_Pantry}")
    println("pantry_size = ${selectedForm.property_Pantry_Size}")
    println("pantry_size_unit = ${selectedForm.pantry_size_unit}")
    println("central_ac = ${selectedForm.property_Central_AC}")
    println("oxygen_duct = ${selectedForm.property_Oxygen_Duct}")
    println("ups = ${selectedForm.property_UPS}")
    println("fire_safety_measures = ${selectedForm.property_Fire_Safety}")
    println("lifts = ${selectedForm.property_Lifts}")
    println("noc_certified = ${selectedForm.property_NOC_Certified}")
    println("occupancy_certificate = ${selectedForm.property_Occupancy}")
    println("washroom_details = ${selectedForm.property_WashRoom}")
    println("does_local_authority = ${selectedForm.property_Authority_Approved}")
    println("suitable_business_type = ${selectedForm.property_Suitable_Business_Type}")
    println("rent = ${selectedForm.rent}")
    println("rent_negotiable = ${selectedForm.rent_negotiable}")
    println("deposit_amount_month_of_rents = ${selectedForm.deposit_amount_month_of_rents}")
    println("deposit_amount_month_of_rents_type = ${selectedForm.deposit_amount_month_of_rents_type}")
    println("total_deposit = ${selectedForm.total_deposit}")
    println("duration_of_agreement = ${selectedForm.duration_of_agreement}")
    println("duration_of_agreement_type = ${selectedForm.duration_of_agreement_type}")
    println("lock_in_period = ${selectedForm.lock_in_period}")
    println("lock_in_period_type = ${selectedForm.lock_in_period_type}")
    println("notice_period = ${selectedForm.notice_period}")
    println("lease_duration_in_years = ${selectedForm.lease_duration_in_years}")
    println("lease_amount = ${selectedForm.lease_amount}")
    println("lease_negotiable = ${selectedForm.lease_negotiable}")
    println("draft = ${selectedForm.draft}")
    println("=====================================================")


    constants.API_Vm.put_post_Form4(
        user_id = AppPreferences.getUserId(),
        user_post_id = AppPreferences.get_Post_Id(),
        property_name = selectedForm.property_Name,
        property_area = selectedForm.property_Name,
        property_area_unit = selectedForm.property_area_unit,
        carpet_area = selectedForm.property_Carpet_Area,
        carpet_area_unit = selectedForm.carpet_area_unit,
        built_up_area = selectedForm.property_Builtup_Area,
        built_up_area_unit = selectedForm.built_up_area_unit,
        super_built_up_area = selectedForm.property_Super_Builtup_Area,
        super_built_up_area_unit = selectedForm.super_built_up_area_unit,

        area_length = selectedForm.property_Area_Dimension_Length,
        area_length_unit = selectedForm.property_Area_Dimension_Length_Unit,
        area_width = selectedForm.property_Area_Dimension_Width,
        area_width_unit = selectedForm.property_Area_Dimension_Width_Unit,

        property_facing = selectedForm.property_Facing,
        total_floor = selectedForm.property_Floor_Det_Total,
        rent_floor_no = selectedForm.property_Floor_Det_Which,

        preferred_tenants = selectedForm.property_preferred_tenants,
        availability_from = selectedForm.property_availability_from,
        agreement_type = selectedForm.property_agreement_type,
        food_preferences = selectedForm.property_food_preferences,
        pets_allowed = selectedForm.property_pets_allowed,



        furnishing_status = selectedForm.property_Furnished,
        boundary_wall = selectedForm.property_Boundary_Wall,
        parking_available = selectedForm.property_Parking,
        amenities = selectedForm.property_Amenities.joinToString(","),
        property_highlights = selectedForm.property_Highlights.joinToString(","),
        bhk_type = selectedForm.property_Floor_Plan_Bhk, // You don’t have BHK type in Form3 DC, leave empty or add mapping if available
        no_of_bedrooms = selectedForm.property_No_of_Beds,
        no_of_Bathrooms = selectedForm.property_No_of_Baths,
        no_of_Balconies = selectedForm.property_No_of_Balconies,
        no_of_open_sides = selectedForm.property_No_Of_OpenSides,
        other_rooms = selectedForm.property_Other_Rooms.joinToString(","),

        facade_width = selectedForm.property_Facade_Width, // Not in Form4
        facade_width_unit = selectedForm.facade_width_unit,
        facade_height = selectedForm.property_Facade_Height,
        facade_height_unit = selectedForm.facade_height_unit,


        property_condition = selectedForm.property_condition,

        no_of_cabins = selectedForm.property_No_Of_Cabins,
        no_of_meeting_rooms = selectedForm.property_No_Of_Meeting_Rooms,
        min_of_seats = selectedForm.property_Min_No_Of_Seats,
        max_of_seats = selectedForm.property_Max_No_Of_Seats,
        conference_room = selectedForm.property_Conference_Room,
        no_of_Staircases = selectedForm.property_No_Of_Stairs,
        reception_area = selectedForm.property_Reception,

        pantry = selectedForm.property_Pantry,
        pantry_size = selectedForm.property_Pantry_Size,
        pantry_size_unit = selectedForm.pantry_size_unit,


        central_ac = selectedForm.property_Central_AC,
        oxygen_duct = selectedForm.property_Oxygen_Duct,
        ups = selectedForm.property_UPS,
        fire_safety_measures = selectedForm.property_Fire_Safety.joinToString(","),
        lifts = selectedForm.property_Lifts,
        noc_certified = selectedForm.property_NOC_Certified,
        occupancy_certificate = selectedForm.property_Occupancy,
        washroom_details = selectedForm.property_WashRoom.joinToString(","),
        does_local_authority = selectedForm.property_Authority_Approved,
        suitable_business_type = selectedForm.property_Suitable_Business_Type.joinToString(","), // Not in Form3 DC, set empty or extend DC
        draft = selectedForm.draft,

    ) { apiResultHandling ->
        when (apiResultHandling) {
            is API_Result_Handling.Loading -> {
                constants.PostProperty_ViewModel.change_Status_PFs(true)
            }
            is API_Result_Handling.Error -> {
                resultCallback(0)
                constants.PostProperty_ViewModel.change_Status_PFs(false)
                toast("OOPs! Something went wrong , Try again later")
            }
            is API_Result_Handling.Success -> {
                resultCallback(1)
                constants.PostProperty_ViewModel.change_Status_PFs(false)
            }
            is API_Result_Handling.NoData -> {
                constants.PostProperty_ViewModel.change_Status_PFs(false)
            }
            is API_Result_Handling.Deactivated -> {
                resultCallback(5)
            }
        }
    }
}



fun get_Form_Preview_API_CALL(resultCallback: (Int) -> Unit){
    constants.API_Vm.put_Post_Form7(
        user_id = AppPreferences.getUserId(),
            //AppPreferences.getUserId(),
        user_post_id = AppPreferences.get_Post_Id()
            //AppPreferences.get_Post_Id(),
    )
    {
            result_Handling ->
        when (result_Handling){
            is API_Result_Handling.Loading -> {
                resultCallback(0)
            }
            is API_Result_Handling.Error -> {
                resultCallback(1)
                toast("OOPs! Something went wrong , Try again later")
            }
            is API_Result_Handling.NoData -> {
                resultCallback(2)
            }
            is API_Result_Handling.Success -> {
                resultCallback(3)
            }
            is API_Result_Handling.Deactivated -> {
                resultCallback(5)
            }
        }
    }

}


fun get_Form_Publish_API_CALL(resultCallback: (Int) -> Unit){
    constants.API_Vm.put_Post_Form_Publish(
        user_id = AppPreferences.getUserId(),
            //AppPreferences.getUserId(),
        user_post_id = AppPreferences.get_Post_Id()
            //AppPreferences.get_Post_Id(),
    )
    {
            result_Handling ->
        when (result_Handling){
            is API_Result_Handling.Loading -> {
                resultCallback(0)
            }
            is API_Result_Handling.Error -> {
                resultCallback(1)
                toast("OOPs! Something went wrong , Try again later")
            }
            is API_Result_Handling.NoData -> {
                resultCallback(2)
            }
            is API_Result_Handling.Success -> {
                resultCallback(3)
            }
            is API_Result_Handling.Deactivated -> {
                resultCallback(5)
            }
        }
    }

}

/*

fun put_Draft_New_Flow_API_CALL(resultCallback: (Int) -> Unit) {
    val selectedForm = constants.PostProperty_ViewModel.get_Selected_Fields_Form()

    val videoUrl = URL_COMPLETED.find { it.first == 1 }?.second ?: ""
    val imageUrls = URL_COMPLETED.filter { it.first == 0 }.map { it.second }.distinct()


    var type =  if (videoUrl.isEmpty()) 2 else 1

    println("LOCATIN ON PAASIN GTO API -- ${constants.PostProperty_ViewModel.get_pp3_Data()} -1234${imageUrls}-- ${URL_COMPLETED}")
    fun checkPrint(label: String, value: Any?) {
        if (value == null) {
            println("$label = null")
        } else if (value is String && value.isEmpty()) {
            println("$label = empty")
        } else {
            println("$label = $value")
        }
    }

// Example usage for your fields
    checkPrint("user_id", AppPreferences.getUserId())
    checkPrint("user_post_id", AppPreferences.get_Post_Id())

    checkPrint("area_length", selectedForm.property_Area_Dimension_Length)
    checkPrint("area_length_unit", selectedForm.property_Area_Dimension_Length_Unit)
    checkPrint("area_width", selectedForm.property_Area_Dimension_Width)
    checkPrint("area_width_unit", selectedForm.property_Area_Dimension_Width_Unit)

    checkPrint("facade_width", selectedForm.property_Facade_Width)
    checkPrint("facade_width_unit", selectedForm.facade_width_unit)
    checkPrint("facade_height", selectedForm.property_Facade_Height)
    checkPrint("facade_height_unit", selectedForm.facade_height_unit)

    checkPrint("property_facing", selectedForm.property_Facing)
    checkPrint("total_floor", selectedForm.property_Floor_Det_Total)
    checkPrint("property_floor_no", selectedForm.property_Floor_Det_Which)
    checkPrint("property_preferred_tenants", selectedForm.property_preferred_tenants)
    checkPrint("property_food_preferences", selectedForm.property_food_preferences)
    checkPrint("property_availability_from", selectedForm.property_availability_from)
    checkPrint("furnishing_status", selectedForm.property_Furnished)
    checkPrint("boundary_wall", selectedForm.property_Boundary_Wall)
    checkPrint("parking_available", selectedForm.property_Parking)
    checkPrint("amenities", selectedForm.property_Amenities.joinToString(","))
    checkPrint("property_highlights", selectedForm.property_Highlights.joinToString(","))
    checkPrint("bhk_type", selectedForm.property_Floor_Plan_Bhk)

    checkPrint("no_of_bedrooms", selectedForm.property_No_of_Beds)
    checkPrint("no_of_bathrooms", selectedForm.property_No_of_Baths)
    checkPrint("no_of_balconies", selectedForm.property_No_of_Balconies)
    checkPrint("no_of_open_sides", selectedForm.property_No_Of_OpenSides)
    checkPrint("other_rooms", selectedForm.property_Other_Rooms.joinToString(","))
    checkPrint("no_of_cabins", selectedForm.property_No_Of_Cabins)
    checkPrint("no_of_meeting_rooms", selectedForm.property_No_Of_Meeting_Rooms)
    checkPrint("min_of_seats", selectedForm.property_Min_No_Of_Seats)
    checkPrint("max_of_seats", selectedForm.property_Max_No_Of_Seats)
    checkPrint("conference_room", selectedForm.property_Conference_Room)
    checkPrint("no_of_staircases", selectedForm.property_No_Of_Stairs)

    checkPrint("reception_area", selectedForm.property_Reception)
    checkPrint("pantry", selectedForm.property_Pantry)
    checkPrint("pantry_size", selectedForm.property_Pantry_Size)
    checkPrint("pantry_size_unit", selectedForm.pantry_size_unit)
    checkPrint("central_ac", selectedForm.property_Central_AC)
    checkPrint("oxygen_duct", selectedForm.property_Oxygen_Duct)
    checkPrint("ups", selectedForm.property_UPS)
    checkPrint("fire_safety_measures", selectedForm.property_Fire_Safety.joinToString(","))
    checkPrint("lifts", selectedForm.property_Lifts)

//    checkPrint("is_it_pre_leased_pre_rented", selectedForm.property_Leased_Rented)
    checkPrint("noc_certified", selectedForm.property_NOC_Certified)
    checkPrint("occupancy_certificate", selectedForm.property_Occupancy)
//    checkPrint("office_previously_used_for", selectedForm.property_Previously_Used_For)
    checkPrint("washroom_details", selectedForm.property_WashRoom.joinToString(","))
    checkPrint("which_local_authority", selectedForm.property_Authority_Approved)
    checkPrint("does_local_authority", selectedForm.property_Authority_Approved)
    checkPrint("suitable_business_type", selectedForm.property_Suitable_Business_Type.joinToString(","))
    checkPrint("draft", selectedForm.draft)

    checkPrint("preview_model", constants.PostProperty_ViewModel.save_Changes_Draft.value)
    checkPrint("price", constants.PostProperty_ViewModel.get_budget_Price_PF5())
    checkPrint("price_negotiable", constants.PostProperty_ViewModel.get_price_negotiation())
    checkPrint("post_type", type)
    checkPrint("video_url", videoUrl)
    checkPrint("image_urls", imageUrls.joinToString(","))

    checkPrint("country", constants.PostProperty_ViewModel.get_pp3_Data()?.country)
    checkPrint("state", constants.PostProperty_ViewModel.get_pp3_Data()?.state)
    checkPrint("city", constants.PostProperty_ViewModel.get_pp3_Data()?.city)
    checkPrint("pincode", constants.PostProperty_ViewModel.get_pp3_Data()?.pincode)
    checkPrint("locality", constants.PostProperty_ViewModel.get_pp3_Data()?.locality)
    checkPrint("latitude", constants.PostProperty_ViewModel.pinned_Lat_Long.value?.latitude)
    checkPrint("longitude", constants.PostProperty_ViewModel.pinned_Lat_Long.value?.longitude)

    checkPrint("property_name", selectedForm.property_Name)
    checkPrint("property_area", selectedForm.property_Land_Area)
    checkPrint("property_area_unit", selectedForm.property_area_unit)
    checkPrint("carpet_area", selectedForm.property_Carpet_Area)
    checkPrint("carpet_area_unit", selectedForm.carpet_area_unit)
    checkPrint("built_up_area", selectedForm.property_Builtup_Area)
    checkPrint("built_up_area_unit", selectedForm.built_up_area_unit)
    checkPrint("super_built_up_area", selectedForm.property_Super_Builtup_Area)
    checkPrint("super_built_up_area_unit", selectedForm.super_built_up_area_unit)

    checkPrint("land_type_id", constants.PostProperty_ViewModel.selected_Land_Type_PF2.value)
    checkPrint("land_categorie_id", constants.PostProperty_ViewModel.selected_Land_Cat_Id.value)
    checkPrint("user_type", constants.PostProperty_ViewModel.selected_User_Type_1PF.value)

    constants.API_Vm.draftNewFlow(
        user_id = AppPreferences.getUserId(),
        user_post_id = AppPreferences.get_Post_Id(),
        user_type = constants.PostProperty_ViewModel.selected_User_Type_1PF.value.toString(),
        land_type_id = constants.PostProperty_ViewModel.selected_Land_Type_PF2.value,
        land_categorie_id = constants.PostProperty_ViewModel.selected_Land_Cat_Id.value,
        country = constants.PostProperty_ViewModel.get_pp3_Data()?.country ?: "",
        state = constants.PostProperty_ViewModel.get_pp3_Data()?.state ?: "",
        city = constants.PostProperty_ViewModel.get_pp3_Data()?.city ?: "",
        locality = constants.PostProperty_ViewModel.get_pp3_Data()?.locality ?: "",
        pincode = constants.PostProperty_ViewModel.get_pp3_Data()?.pincode ?: "",
        latitude = constants.PostProperty_ViewModel.pinned_Lat_Long.value?.latitude.toString(),
        longitude = constants.PostProperty_ViewModel.pinned_Lat_Long.value?.longitude.toString(),
        property_name = selectedForm.property_Name,

        bhk_type = selectedForm.property_Floor_Plan_Bhk,
        property_area = selectedForm.property_Land_Area,
        property_area_unit = selectedForm.property_area_unit,
        carpet_area = selectedForm.property_Carpet_Area,
        carpet_area_unit = selectedForm.carpet_area_unit,
        built_up_area = selectedForm.property_Builtup_Area,
        built_up_area_unit = selectedForm.built_up_area_unit,
        super_built_up_area = selectedForm.property_Super_Builtup_Area,
        super_built_up_area_unit = selectedForm.super_built_up_area_unit,

        area_length = selectedForm.property_Area_Dimension_Length,
        area_length_unit = selectedForm.property_Area_Dimension_Length_Unit,
        area_width = selectedForm.property_Area_Dimension_Width,
        area_width_unit = selectedForm.property_Area_Dimension_Width_Unit,

        property_facing = selectedForm.property_Facing,
        total_floor = selectedForm.property_Floor_Det_Total,
        rent_floor_no = selectedForm.property_Floor_Det_Which,

        preferred_tenants = selectedForm.property_preferred_tenants.joinToString(","),
        availability_from = selectedForm.property_availability_from,
        agreement_type = selectedForm.property_agreement_type,
        food_preferences = selectedForm.property_food_preferences,
        pets_allowed = selectedForm.property_pets_allowed,
        property_condition = selectedForm.property_condition,
        furnishing_status = selectedForm.property_Furnished,
        boundary_wall = selectedForm.property_Boundary_Wall,
        parking_available = selectedForm.property_Parking,
        amenities = selectedForm.property_Amenities.joinToString { "," },
        property_highlights = selectedForm.property_Highlights.joinToString { "," },

        facade_width = selectedForm.property_Facade_Width,
        facade_width_unit = selectedForm.facade_width_unit,
        facade_height = selectedForm.property_Facade_Height,
        facade_height_unit = selectedForm.facade_height_unit,

        no_of_Bathrooms = selectedForm.property_No_of_Baths,
        no_of_Balconies = selectedForm.property_No_of_Balconies,
        no_of_open_sides = selectedForm.property_No_Of_OpenSides,
        other_rooms = selectedForm.property_Other_Rooms.joinToString(","),
        no_of_bedrooms = selectedForm.property_No_of_Beds,
        no_of_cabins = selectedForm.property_No_Of_Cabins,
        no_of_meeting_rooms = selectedForm.property_No_Of_Meeting_Rooms,
        min_of_seats = selectedForm.property_Min_No_Of_Seats,
        max_of_seats = selectedForm.property_Max_No_Of_Seats,
        conference_room = selectedForm.property_Conference_Room,
        no_of_Staircases = selectedForm.property_No_Of_Stairs,
        reception_area = selectedForm.property_Reception,
        pantry = selectedForm.property_Pantry,
        pantry_size = selectedForm.property_Pantry_Size,
        pantry_size_unit = selectedForm.pantry_size_unit,
        central_ac = selectedForm.property_Central_AC,
        oxygen_duct = selectedForm.property_Oxygen_Duct,
        ups = selectedForm.property_UPS,
        fire_safety_measures = selectedForm.property_Fire_Safety.joinToString(","),
        lifts = selectedForm.property_Lifts,
        noc_certified = selectedForm.property_NOC_Certified,
        occupancy_certificate = selectedForm.property_Occupancy,
        washroom_details = selectedForm.property_WashRoom.joinToString(","),
        does_local_authority = selectedForm.property_Authority_Approved,
        suitable_business_type = selectedForm.property_Suitable_Business_Type.joinToString(","),

        property_for_rent_or_lease = selectedForm.property_for_rent_or_lease,
        rent = constants.PostProperty_ViewModel.get_budget_Price_PF5(),
        rent_negotiable = constants.PostProperty_ViewModel.get_price_negotiation(),
        deposit_amount_month_of_rents = selectedForm.deposit_amount_month_of_rents,
//        deposit_amount_months_or_amount = selectedForm.property_Deposit_Amount,
        duration_of_agreement = selectedForm.duration_of_agreement_type,
        notice_period = selectedForm.notice_period,
        lock_in_period = selectedForm.lock_in_period,
        lease_duration_in_years = selectedForm.lease_duration_in_years,
        lease_amount = selectedForm.lease_amount,
        lease_negotiable = if(selectedForm.lease_negotiable)"1" else "0",

        post_type = type.toString(),
        video = videoUrl,                     // LIST instead of string
        images = imageUrls,                     // LIST object type
        thumbnail = ,
        draft = selectedForm.draft.toString(),
        preview_model = constants.PostProperty_ViewModel.save_Changes_Draft.value.toString(),
        address = selectedForm,
        is_this_property_for_rent_or_lease = selectedForm.property_for_rent_or_lease,
        deposit_amount_month_of_rents_type = selectedForm.deposit_amount_month_of_rents_type,
        total_deposit = selectedForm.total_deposit,
        duration_of_agreement_type = selectedForm.duration_of_agreement_type,
        lock_in_period_type = selectedForm.lock_in_period_type,
    )
    { result ->
        when (result) {
            is API_Result_Handling.Loading -> {
                println("aesfdghjhgfcdxszxfcgm0987654324567890-333333")
                resultCallback(3)
                constants.PostProperty_ViewModel.change_Status_PFs(true)
            }
            is API_Result_Handling.Error -> {
                println("aesfdghjhgfcdxszxfcgm0987654324567890-0000---${result.message}")
                resultCallback(0)
                constants.PostProperty_ViewModel.change_Status_PFs(false)
            }
            is API_Result_Handling.Success -> {
                resultCallback(1)
                println("aesfdghjhgfcdxszxfcgm0987654324567890-1111111111")
                constants.PostProperty_ViewModel.change_Status_PFs(false)
            }
            is API_Result_Handling.NoData -> {
                println("aesfdghjhgfcdxszxfcgm0987654324567890-")
                constants.PostProperty_ViewModel.change_Status_PFs(false)
            }
            is API_Result_Handling.Deactivated -> {
                resultCallback(5)
            }
        }
    }




}

*/


data class DraftNewFlowRequest(
    val user_id: Int,
    val user_post_id: Int,

    val area_length: String?,
    val area_length_unit: String?,
    val area_width: String?,
    val area_width_unit: String?,

    val facade_width: String?,
    val facade_width_unit: String?,
    val facade_height: String?,
    val facade_height_unit: String?,

    val property_facing: String?,
    val total_floor: String?,
    val property_floor_no: String?,
    val property_ownership: String?,
    val availability_status: String?,
    val furnishing_status: String?,
    val boundary_wall: String?,
    val parking_available: String?,
    val amenities: String?,
    val property_highlights: String?,
    val bhk_type: String?,

    val no_of_bedrooms: String?,
    val no_of_bathrooms: String?,
    val no_of_balconies: String?,
    val no_of_open_sides: String?,
    val other_rooms: String?,
    val no_of_cabins: String?,
    val no_of_meeting_rooms: String?,
    val min_of_seats: String?,
    val max_of_seats: String?,
    val conference_room: String?,
    val no_of_staircases: String?,

    val reception_area: String?,
    val pantry: String?,
    val pantry_size: String?,
    val pantry_size_unit: String?,
    val central_ac: String?,
    val oxygen_duct: String?,
    val ups: String?,
    val fire_safety_measures: String?,
    val lifts: String?,

    val noc_certified: String?,
    val occupancy_certificate: String?,
    val washroom_details: String?,
    val which_local_authority: String?,
    val does_local_authority: String?,
    val suitable_business_type: String?,
    val draft: String?,
    val account_status: Int?,

    val preview_model: String?,
    val price: String?,
    val price_negotiable: String?,

    val post_type: String?,
    val video_urls: List<String>?,
    val image_urls: List<String>?,

    val latitude: String?,
    val longitude: String?,
    val property_name: String?,
    val property_area: String?,
    val property_area_unit: String?,
    val carpet_area: String?,
    val carpet_area_unit: String?,
    val built_up_area: String?,
    val built_up_area_unit: String?,
    val super_built_up_area: String?,
    val super_built_up_area_unit: String?,

    val country: String?,
    val state: String?,
    val city: String?,
    val locality: String?,
    val pincode: String?,

    val land_type_id: Int?,
    val land_categorie_id: Int?,
    val user_type: String?
)






