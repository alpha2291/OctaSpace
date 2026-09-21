package com.toletspot.houseforrent.Home_Screen.PostProperty_Module

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.toletspot.houseforrent.API.API_Result_Handling
import com.toletspot.houseforrent.API.StartUp_API.DraftNewFlowRequestRaw
import com.toletspot.houseforrent.API.StartUp_API.ImageAPIUpload
import com.toletspot.houseforrent.API.StartUp_API.get_Form_Preview_API_CALL
import com.toletspot.houseforrent.API.StartUp_API.post_Form5Rento_APICALL
import com.toletspot.houseforrent.API.StartUp_API.post_Form_1_API_Call
import com.toletspot.houseforrent.API.StartUp_API.post_Form_2_API_Call
import com.toletspot.houseforrent.API.StartUp_API.post_Form_3_API_Call
import com.toletspot.houseforrent.API.StartUp_API.put_post_Form4_API_CALL
import com.toletspot.houseforrent.AppPreferences
import com.toletspot.houseforrent.Custom_Assets.Static_Bottom
import com.toletspot.houseforrent.Custom_Assets.toast
import com.toletspot.houseforrent.GlobalSnackbar
import com.toletspot.houseforrent.Home_Screen.Video_Module.toGetReelsData_FDfs
import com.toletspot.houseforrent.Navigation.PostPropertyFlow
import com.toletspot.houseforrent.NetworkStatus
import com.toletspot.houseforrent.R
import com.toletspot.houseforrent.UI_DataClass.UploadPostRequest
import com.toletspot.houseforrent.UI_DataClass.UploadPropertyMedia
import com.toletspot.houseforrent.constants
import com.toletspot.houseforrent.noRippleClickable
import com.toletspot.houseforrent.rememberNetworkStatus
import com.toletspot.houseforrent.ui.theme.newBlue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private fun submitForm6(onComplete: () -> Unit) {
    if (constants.PostProperty_ViewModel.mediaList.value.isNotEmpty()) {
        constants.PostProperty_ViewModel.onNextPPForm()
        onComplete()
    } else {
        toast("Add images or videos to continue")
        onComplete()
    }
}

@Composable
fun PP_Forms_Next_Clicker(
    current_Form: State<Int>,
    isLoading: State<Boolean>,
    navController: NavHostController,
    apiError: MutableState<Boolean>
) {
    val network = rememberNetworkStatus()
    val media = constants.PostProperty_ViewModel.mediaList.collectAsState()
    val context = LocalContext.current
    val state = constants.PostProperty_ViewModel.status_PFs.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()
    val postFlow = constants.PostProperty_ViewModel.postFlow.collectAsStateWithLifecycle()

    var isProcessing by remember { mutableStateOf(false) }

    LaunchedEffect(current_Form.value) {
        isProcessing = false
    }

    val proceedWithoutMedia = constants.PostProperty_ViewModel.proceedWithoutMedia.collectAsState()

    LaunchedEffect(proceedWithoutMedia.value) {
        if (proceedWithoutMedia.value) {

            handleFormSubmission(
                currentForm = current_Form.value,
                postFlow = postFlow.value,
                navController = navController,
                scope = scope,
                media = media.value,
                onComplete = { isProcessing = false }
            )
        }
    }

    Box {
        Static_Bottom(
            modifier = Modifier.fillMaxSize(),
            content = {
                if (current_Form.value != 0) {
                    NavigationButtons(
                        isLoading = isLoading.value,
                        isProcessing = isProcessing,
                        apiError = apiError.value,
                        state = state.value,
                        onBack = {
                            constants.PostProperty_ViewModel.onPreviousPPForm()
                        },
                        onContinue = {
                            if (!isProcessing) {
                                isProcessing = true

                                if (network.value != NetworkStatus.Online) {
                                    GlobalSnackbar.show(context.getString(R.string.no_Internet))
                                    isProcessing = false
                                    return@NavigationButtons
                                }

                                handleFormSubmission(
                                    currentForm = current_Form.value,
                                    postFlow = postFlow.value,
                                    navController = navController,
                                    scope = scope,
                                    media = media.value,
                                    onComplete = { isProcessing = false }
                                )
                            }
                        }
                    )
                } else {
                    InitialFormButton(
                        isLoading = isLoading.value,
                        isProcessing = isProcessing,
                        apiError = apiError.value,
                        state = state.value,
                        network = network.value,
                        onSubmit = {
                            if (network.value != NetworkStatus.Online) {
                                GlobalSnackbar.show(context.getString(R.string.no_Internet))
                                isProcessing = false
                                constants.PostProperty_ViewModel.change_Status_PFs(false)
                                return@InitialFormButton
                            }

                            if (!isProcessing) {
                                isProcessing = true
                                handleFirstForm(onComplete = { isProcessing = false })
                            }
                        }
                    )
                }
            }
        )
    }
}

@Composable
private fun NavigationButtons(
    isLoading: Boolean,
    isProcessing: Boolean,
    apiError: Boolean,
    state: Boolean,
    onBack: () -> Unit,
    onContinue: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Spacer(modifier = Modifier.weight(.5f))

        Box(
            modifier = Modifier
                .height(44.dp)
                .weight(4f)
                .clip(RoundedCornerShape(8.dp))
                .clickable(enabled = !isProcessing && !isLoading) { onBack() }
                .background(Color(0xffE8E8E8)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                "Go Back",
                color = Color(0xff666666),
                fontSize = constants.textUnit(14),
                fontFamily = constants.fontFamily(0)
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Box(
            modifier = Modifier
                .height(44.dp)
                .weight(4f)
                .clip(RoundedCornerShape(8.dp))
                .background(newBlue)
                .noRippleClickable(enabled = !isLoading && !isProcessing && !apiError && !state) {
                    onContinue()
                }
                .padding(horizontal = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            if (isLoading || isProcessing) {
                CircularProgressIndicator(
                    color = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            } else {
                Text(
                    "Continue",
                    color = Color.White,
                    fontSize = constants.textUnit(14),
                    fontFamily = constants.fontFamily(0)
                )
            }
        }
        Spacer(modifier = Modifier.weight(.5f))
    }
}

@Composable
private fun InitialFormButton(
    isLoading: Boolean,
    isProcessing: Boolean,
    apiError: Boolean,
    state: Boolean,
    network: NetworkStatus,
    onSubmit: () -> Unit
) {
    Box(
        modifier = Modifier
            .height(44.dp)
            .fillMaxWidth(.9f)
            .clip(RoundedCornerShape(8.dp))
            .background(newBlue)
            .clickable(enabled = !isLoading && !isProcessing && !apiError && !state) {
                onSubmit()
            }
            .padding(horizontal = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        if (isLoading || isProcessing) {
            CircularProgressIndicator(
                color = Color.White,
                modifier = Modifier.size(20.dp)
            )
        } else {
            Text(
                "Continue",
                color = Color.White,
                fontSize = constants.textUnit(14),
                fontFamily = constants.fontFamily(0)
            )
        }
    }
}

private fun handleFirstForm(onComplete: () -> Unit) {
    if (constants.PostProperty_ViewModel.get_FirstForm_Selected_PP() != -1) {
        constants.PostProperty_ViewModel.change_Status_PFs(true)
        AppPreferences.save_Post_Id(0)
        post_Form_1_API_Call { result ->
            constants.PostProperty_ViewModel.set1formError(false)
            onComplete()
            if (result == 1) {
                constants.PostProperty_ViewModel.onNextPPForm()
            } else {
                toast("Something went wrong")
            }
        }
    } else {
        constants.PostProperty_ViewModel.set1formError(true)
        onComplete()
        toast("Select options to continue")
    }
}

private fun handleFormSubmission(
    currentForm: Int,
    postFlow: PostFlow,
    navController: NavHostController,
    scope: CoroutineScope,
    media: List<UploadPropertyMedia>,
    onComplete: () -> Unit,

) {

    when (postFlow) {
        PostFlow.DRAFT, PostFlow.EDIT -> handleEditFlow(
            currentForm = currentForm,
            navController = navController,
            scope = scope,
            media = media,
            onComplete = onComplete
        )
        PostFlow.NEW, PostFlow.REPOST -> handleNewFlow(
            currentForm = currentForm,
            navController = navController,
            scope = scope,
            media = media,
            onComplete = onComplete
        )
        PostFlow.NONE -> {
            onComplete()
            toast("Invalid flow state")
        }

        else -> {
            onComplete()
            toast("Invalid flow state")
        }

    }
}

private fun handleEditFlow(
    currentForm: Int,
    navController: NavHostController,
    scope: CoroutineScope,
    media: List<UploadPropertyMedia>,
    onComplete: () -> Unit
) {
    when (currentForm) {
        1 -> validateForm1(onComplete)
        2 -> validateForm2(onComplete)
        3 -> validateForm3(onComplete)
        4 -> validateForm4(onComplete)
        5 -> validateForm5(onComplete)
        6 -> handleForm6Upload(navController, scope, onComplete, isDraft = true)
    }
}

private fun handleNewFlow(
    currentForm: Int,
    navController: NavHostController,
    scope: CoroutineScope,
    media: List<UploadPropertyMedia>,
    onComplete: () -> Unit
) {

    when (currentForm) {
        1 -> submitForm1(onComplete)
        2 -> submitForm2(onComplete)
        3 -> submitForm3(onComplete)
        4 -> submitForm4(onComplete)
        5 -> submitForm5(onComplete)

        6 ->  handleForm6Upload(
            navController = navController,
            scope = scope,
            onComplete = onComplete,
            isDraft = false
        )

        7 -> goToPreview(onComplete)
    }
}

private fun validateForm1(onComplete: () -> Unit) {
    if (constants.PostProperty_ViewModel.get_Selected_Land_Cat_Id() != -1 &&
        constants.PostProperty_ViewModel.get_LandSubType_Selected_Click() != -1
    ) {
        constants.PostProperty_ViewModel.onNextPPForm()
        onComplete()
    } else {
        toast("Select type of property")
        onComplete()
    }
}

private fun validateForm2(onComplete: () -> Unit) {
    val pp3Data = constants.PostProperty_ViewModel.get_pp3_Data()
    val locationMissing = listOf(
        pp3Data?.locality,
        pp3Data?.pincode,
        pp3Data?.city,
        pp3Data?.state,
        pp3Data?.country
    ).any { it.isNullOrEmpty() }

    if (!locationMissing) {
        limitGoingIn.value = true
        constants.PostProperty_ViewModel.onNextPPForm()
        onComplete()
    } else {
        toast("Enter the location or pin on map")
        onComplete()
    }
}

private fun validateForm3(onComplete: () -> Unit) {
    constants.PostProperty_ViewModel.update_Selected_Field_Form4 {
        it.copy(draft = 4)
    }

    val hasNoErrors = if (next_Active_Fields.isEmpty()) {
        true
    } else {
        constants.PostProperty_ViewModel.check_Errors4(next_Active_Fields)
    }

    if (hasNoErrors) {
        constants.PostProperty_ViewModel.onNextPPForm()
        onComplete()
    } else {
        onComplete()
    }
}

private fun validateForm4(onComplete: () -> Unit) {
    constants.PostProperty_ViewModel.update_Selected_Field_Form4 {
        it.copy(draft = 5)
    }

    val hasNoErrors = if (next_Active_Fields5.isEmpty()) {
        true
    } else {
        constants.PostProperty_ViewModel.check_Errors5(next_Active_Fields5)
    }

    if (hasNoErrors) {
        constants.PostProperty_ViewModel.onNextPPForm()
        onComplete()

    } else {
        onComplete()
    }
}

private fun validateForm5(onComplete: () -> Unit) {

    val hasNoErrors = if (next_Active_Fields6.isEmpty()) {
        true
    } else {
        constants.PostProperty_ViewModel.check_Errors6(next_Active_Fields6)
    }

    if (hasNoErrors) {
        constants.PostProperty_ViewModel.onNextPPForm()
        onComplete()

    } else {
        onComplete()
    }
}

private fun submitForm1(onComplete: () -> Unit) {
    if (constants.PostProperty_ViewModel.get_Selected_Land_Cat_Id() != -1 &&
        constants.PostProperty_ViewModel.get_LandSubType_Selected_Click() != -1
    ) {
        constants.PostProperty_ViewModel.change_Status_PFs(true)
        post_Form_2_API_Call { result ->
            constants.PostProperty_ViewModel.set2formError(false)
            onComplete()
            if (result == 1) {
                constants.PostProperty_ViewModel.onNextPPForm()
            } else {
                constants.PostProperty_ViewModel.change_Status_PFs(false)
                toast("Something went wrong")
            }
        }
    } else {
        constants.PostProperty_ViewModel.set2formError(true)
        constants.PostProperty_ViewModel.set_onSelected_ProType(-1)

        toast("Select type of property")
        onComplete()
    }
}

private fun submitForm2(onComplete: () -> Unit) {
    val pp3Data = constants.PostProperty_ViewModel.get_pp3_Data()
    val locationMissing = listOf(
        pp3Data?.locality,
        pp3Data?.pincode,
        pp3Data?.city,
        pp3Data?.state,
        pp3Data?.country
    ).any { it.isNullOrEmpty() }

    if (!locationMissing) {
        constants.PostProperty_ViewModel.change_Status_PFs(true)
        post_Form_3_API_Call { result ->
            onComplete()
            if (result == 1) {
                limitGoingIn.value = true
                constants.PostProperty_ViewModel.onNextPPForm()
            } else {
                constants.PostProperty_ViewModel.change_Status_PFs(false)
                toast("Something went wrong")
            }
        }
    } else {
        toast("Enter the location or pin on map")
        onComplete()
    }
}

private fun submitForm3(onComplete: () -> Unit) {
    constants.PostProperty_ViewModel.update_Selected_Field_Form4 {
        it.copy(draft = 4)
    }

    val hasNoErrors = if (next_Active_Fields.isEmpty()) {
        true
    } else {
        constants.PostProperty_ViewModel.check_Errors4(next_Active_Fields)
    }

    if (hasNoErrors) {
        constants.PostProperty_ViewModel.change_Status_PFs(true)
        put_post_Form4_API_CALL { result ->
            onComplete()
            if (result == 1) {
                constants.PostProperty_ViewModel.onNextPPForm()
            } else {
                constants.PostProperty_ViewModel.change_Status_PFs(false)
                toast("Something went wrong")
            }
        }
    } else {
        onComplete()
    }
}

private fun submitForm4(onComplete: () -> Unit) {
    constants.PostProperty_ViewModel.update_Selected_Field_Form4 {
        it.copy(draft = 5)
    }

    val hasNoErrors = if (next_Active_Fields5.isEmpty()) {
        true
    } else {
        constants.PostProperty_ViewModel.check_Errors5(next_Active_Fields5)
    }

    if (hasNoErrors) {
        constants.PostProperty_ViewModel.change_Status_PFs(true)
        put_post_Form4_API_CALL { result ->
            onComplete()
            if (result == 1) {
                constants.PostProperty_ViewModel.onNextPPForm()
            } else {
                constants.PostProperty_ViewModel.change_Status_PFs(false)
                toast("Something went wrong")
            }
        }
    } else {
        onComplete()
    }
}

private fun submitForm5(onComplete: () -> Unit) {
    constants.PostProperty_ViewModel.update_Selected_Field_Form4 {
        it.copy(draft = 6)
    }

    val hasNoErrors = if (next_Active_Fields6.isEmpty()) {
        true
    } else {
        constants.PostProperty_ViewModel.check_Errors6(next_Active_Fields6)
    }

    if (hasNoErrors) {
        constants.PostProperty_ViewModel.change_Status_PFs(true)
        constants.PostProperty_ViewModel.change_Status_PFs(true)
        post_Form5Rento_APICALL { result ->
            onComplete()
            if (result == 1) {
                constants.PostProperty_ViewModel.onNextPPForm()
            } else {
                constants.PostProperty_ViewModel.change_Status_PFs(false)
                toast("Something went wrong")
            }
        }
    } else {
        onComplete()
    }
}

private fun goToPreview(onComplete: () -> Unit) {

}

fun handleForm6Upload(
    navController: NavHostController,
    scope: CoroutineScope,
    onComplete: () -> Unit,
    isDraft: Boolean
) {
    val media = constants.PostProperty_ViewModel.mediaList.value

    if (!constants.PostProperty_ViewModel.proceedWithoutMedia.value) {

        if (media.none { it.uploadedUrl != null }) {
            constants.PostProperty_ViewModel.set_True_emptyMediaBtm()

            onComplete()
            return
        }
    }
    else {
        get_Form_Preview_API_CALL { result ->
            onComplete()
            if (result == 3) {
                navController.navigate(PostPropertyFlow.PreviewScreen.route)
                constants.PostProperty_ViewModel.proceedWithoutMedia.value = false
                constants.PostProperty_ViewModel.change_Status_PFs(false)

            } else if (result == 1){
                constants.PostProperty_ViewModel.change_Status_PFs(false)
                toast("Something went wrong")
            }
        }
    }

    scope.launch {
        try {
            val requestBody = constants.PostProperty_ViewModel.buildUploadRequestBody()

            val imageApiMapped = requestBody.image_urls.map { ImageAPIUpload(it.url, it.heading) }
            val videoApiMapped = requestBody.video_urls.map { ImageAPIUpload(it.url, it.heading) }

            if (isDraft) {
                constants.PostProperty_ViewModel.setPostFlow(PostFlow.DRAFT)
                handleDraftSubmission(requestBody , navController, "0",onComplete)
            } else {
                handlePostSubmission(requestBody, navController, onComplete)
            }

        } catch (e: Exception) {
            e.printStackTrace()
            toast("Upload failed")
            onComplete()
        }
    }
}

private fun handleDraftSubmission2(
    requestBody : UploadPostRequest,
    navController: NavHostController,
    onComplete: () -> Unit
) {

    constants.PostProperty_ViewModel.save_Changes_Draft.value = 0
    constants.PostProperty_ViewModel.update_Selected_Field_Form4 {
        it.copy(draft = 6)
    }

    val selectedForm = constants.PostProperty_ViewModel.get_Selected_Fields_Form()

    fun checkPrint(label: String, value: Any?) {
        if (value == null) {
        } else if (value is String && value.isEmpty()) {
        } else {
        }
    }

    val postType = when {
        requestBody.image_urls.isEmpty() && requestBody.video_urls.isNotEmpty() -> "1"
        requestBody.image_urls.isNotEmpty() && requestBody.video_urls.isEmpty() -> "2"
        requestBody.image_urls.isNotEmpty() && requestBody.video_urls.isNotEmpty() -> "3"
        else -> "4"
    }

    val imageApiMapped = requestBody.image_urls .map {
        ImageAPIUpload(url = it.url, heading = it.heading)
    }

    val videoApiMapped = requestBody.video_urls.map {
        ImageAPIUpload(url = it.url, heading = it.heading)
    }

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

    checkPrint("noc_certified", selectedForm.property_NOC_Certified)
    checkPrint("occupancy_certificate", selectedForm.property_Occupancy)

    checkPrint("washroom_details", selectedForm.property_WashRoom.joinToString(","))
    checkPrint("which_local_authority", selectedForm.property_Authority_Approved)
    checkPrint("does_local_authority", selectedForm.property_Authority_Approved)
    checkPrint("suitable_business_type", selectedForm.property_Suitable_Business_Type.joinToString(","))
    checkPrint("draft", selectedForm.draft)

    checkPrint("preview_model", constants.PostProperty_ViewModel.save_Changes_Draft.value)
    checkPrint("price", constants.PostProperty_ViewModel.get_budget_Price_PF5())
    checkPrint("price_negotiable", constants.PostProperty_ViewModel.get_price_negotiation())
    checkPrint("post_type", postType)
    checkPrint("video_url", videoApiMapped)
    checkPrint("image_urls", imageApiMapped)

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

        duration_of_agreement = selectedForm.duration_of_agreement_type,
        notice_period = selectedForm.notice_period,
        lock_in_period = selectedForm.lock_in_period,
        lease_duration_in_years = selectedForm.lease_duration_in_years,
        lease_amount = selectedForm.lease_amount,
        lease_negotiable = if(selectedForm.lease_negotiable)"1" else "0",

        post_type = postType,
        video = videoApiMapped,
        images = imageApiMapped,
        thumbnail = requestBody.thumbnail,
        draft = selectedForm.draft.toString(),
        preview_model = constants.PostProperty_ViewModel.save_Changes_Draft.value.toString(),
        is_this_property_for_rent_or_lease = selectedForm.property_for_rent_or_lease,
        deposit_amount_month_of_rents_type = selectedForm.deposit_amount_month_of_rents_type,
        total_deposit = selectedForm.total_deposit,
        duration_of_agreement_type = selectedForm.duration_of_agreement_type,
        lock_in_period_type = selectedForm.lock_in_period_type,
    )
    { result ->

        onComplete()
        when (result) {
            is API_Result_Handling.Loading -> {

                constants.PostProperty_ViewModel.change_Status_PFs(true)
            }
            is API_Result_Handling.Error -> {

                constants.PostProperty_ViewModel.change_Status_PFs(false)
            }
            is API_Result_Handling.Success -> {

                constants.Reels_ViewModel.clear_view_pro_Details()
                constants.PostProperty_ViewModel.setPostFlow(PostFlow.DRAFT)

                val data = constants.PostProperty_ViewModel.get_new_Draft_Data()
                data?.toGetReelsData_FDfs()?.let {
                    constants.Reels_ViewModel.add_View_Property_Details(it)
                }

                CoroutineScope(Dispatchers.Main).launch {
                    constants.PostProperty_ViewModel.setPostFlow(PostFlow.NONE)
                    constants.PostProperty_ViewModel.change_Status_PFs(false)
                    navController.navigate(PostPropertyFlow.ViewPropertyStructure.route)
                }
            }
            is API_Result_Handling.NoData -> {
                constants.PostProperty_ViewModel.change_Status_PFs(false)
            }
            is API_Result_Handling.Deactivated -> {

            }
        }
    }

}

fun handleDraftSubmission(
    requestBody: UploadPostRequest,
    navController: NavHostController,
    draftPreview : String,
    onComplete: () -> Unit
) {
    constants.PostProperty_ViewModel.save_Changes_Draft.value = 0

    val selectedForm = constants.PostProperty_ViewModel.get_Selected_Fields_Form()

    val postType = when {
        requestBody.image_urls.isEmpty() && requestBody.video_urls.isNotEmpty() -> "1"
        requestBody.image_urls.isNotEmpty() && requestBody.video_urls.isEmpty() -> "2"
        requestBody.image_urls.isNotEmpty() && requestBody.video_urls.isNotEmpty() -> "3"
        else -> "4"
    }

    val imageApiMapped = requestBody.image_urls.map { ImageAPIUpload(url = it.url, heading = it.heading) }
    val videoApiMapped = requestBody.video_urls.map { ImageAPIUpload(url = it.url, heading = it.heading) }

    val draftRequest = DraftNewFlowRequestRaw(
        user_id = AppPreferences.getUserId(),
        user_post_id = AppPreferences.get_Post_Id(),
        user_type = constants.PostProperty_ViewModel.selected_User_Type_1PF.value.toString(),
        land_type_id = constants.PostProperty_ViewModel.selected_Land_Type_PF2.value,
        land_categorie_id = constants.PostProperty_ViewModel.selected_Land_Cat_Id.value,

        country = constants.PostProperty_ViewModel.get_pp3_Data()?.country,
        state = constants.PostProperty_ViewModel.get_pp3_Data()?.state,
        city = constants.PostProperty_ViewModel.get_pp3_Data()?.city,
        locality = constants.PostProperty_ViewModel.get_pp3_Data()?.locality,
        pincode = constants.PostProperty_ViewModel.get_pp3_Data()?.pincode,
        latitude = constants.PostProperty_ViewModel.pinned_Lat_Long.value?.latitude.toString(),
        longitude = constants.PostProperty_ViewModel.pinned_Lat_Long.value?.longitude.toString(),
        property_name = selectedForm.property_Name,
        property_for_rent_or_lease = selectedForm.property_for_rent_or_lease,

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
        facade_width = selectedForm.property_Facade_Width,
        facade_width_unit = selectedForm.facade_width_unit,
        facade_height = selectedForm.property_Facade_Height,
        facade_height_unit = selectedForm.facade_height_unit,
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
        amenities = selectedForm.property_Amenities.joinToString(","),
        property_highlights = selectedForm.property_Highlights.joinToString(","),
        no_of_bedrooms = selectedForm.property_No_of_Beds,
        no_of_Bathrooms = selectedForm.property_No_of_Baths,
        no_of_Balconies = selectedForm.property_No_of_Balconies,
        no_of_open_sides = selectedForm.property_No_Of_OpenSides,
        no_of_Staircases = selectedForm.property_No_Of_Stairs,
        other_rooms = selectedForm.property_Other_Rooms.joinToString(","),
        no_of_cabins = selectedForm.property_No_Of_Cabins,
        no_of_meeting_rooms = selectedForm.property_No_Of_Meeting_Rooms,
        min_of_seats = selectedForm.property_Min_No_Of_Seats,
        max_of_seats = selectedForm.property_Max_No_Of_Seats,
        conference_room = selectedForm.property_Conference_Room,
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
        rent = selectedForm.rent,
        rent_negotiable = if(selectedForm.rent_negotiable)"1" else "0",
        deposit_amount_month_of_rents = selectedForm.deposit_amount_month_of_rents,
        deposit_amount_month_of_rents_type = selectedForm.deposit_amount_month_of_rents_type,
        total_deposit = selectedForm.total_deposit,
        duration_of_agreement = selectedForm.duration_of_agreement,
        duration_of_agreement_type = selectedForm.duration_of_agreement_type,
        lock_in_period = selectedForm.lock_in_period,
        lock_in_period_type = selectedForm.lock_in_period_type,
        notice_period = selectedForm.notice_period,
        lease_duration_in_years = selectedForm.lease_duration_in_years,
        lease_amount = selectedForm.lease_amount,
        lease_negotiable = if (selectedForm.lease_negotiable) "1" else "0",
        post_type = postType,
        video_urls = videoApiMapped,
        image_urls = imageApiMapped,
        thumbnail = requestBody.thumbnail,
        draft = selectedForm.draft.toString(),
        preview_model = draftPreview
    )

    constants.API_Vm.draftNewFlowRaw(draftRequest) { result ->
        onComplete()
        when (result) {
            is API_Result_Handling.Loading -> {
                constants.PostProperty_ViewModel.change_Status_PFs(true)
            }
            is API_Result_Handling.Error -> {
                constants.PostProperty_ViewModel.change_Status_PFs(false)
            }
            is API_Result_Handling.Success -> {
                constants.Reels_ViewModel.clear_view_pro_Details()
                constants.PostProperty_ViewModel.setPostFlow(PostFlow.DRAFT)

                if (draftPreview == "1"){

                    onComplete()
                }
                else {
                    CoroutineScope(Dispatchers.Main).launch {
                        constants.PostProperty_ViewModel.setPostFlow(PostFlow.NONE)
                        constants.PostProperty_ViewModel.change_Status_PFs(false)
                        navController.navigate(PostPropertyFlow.PreviewScreen.route)

                    }
                }
            }
            is API_Result_Handling.NoData -> {
                constants.PostProperty_ViewModel.change_Status_PFs(false)
            }
            is API_Result_Handling.Deactivated -> {

            }
        }
    }
}

private fun handlePostSubmission(
    requestBody : UploadPostRequest,
    navController: NavHostController,
    onComplete: () -> Unit
) {

    val postType = when {
            requestBody.image_urls.isEmpty() && requestBody.video_urls.isNotEmpty() -> "1"
            requestBody.image_urls.isNotEmpty() && requestBody.video_urls.isEmpty() -> "2"
            requestBody.image_urls.isNotEmpty() && requestBody.video_urls.isNotEmpty() -> "3"
            else -> "4"
        }

    val imageApiMapped = requestBody.image_urls .map {
        ImageAPIUpload(url = it.url, heading = it.heading)
    }

    val videoApiMapped = requestBody.video_urls.map {
        ImageAPIUpload(url = it.url, heading = it.heading)
    }

    if (!requestBody.image_urls.isEmpty() && requestBody.image_urls.size >= 3 || requestBody.image_urls.isEmpty()) {
        constants.API_Vm.put_post_Form6rento(
            user_id = AppPreferences.getUserId(),
            user_post_id = AppPreferences.get_Post_Id(),
            post_type = postType,
            video_urls = videoApiMapped,
            image_urls = imageApiMapped,
            thumbnail = requestBody.thumbnail
        )
        { result ->

            when (result) {
                is API_Result_Handling.Success -> {

                    if (constants.PostProperty_ViewModel.postFlow.value == PostFlow.REQUESTMEDIA || constants.PostProperty_ViewModel.postFlow.value == PostFlow.EDIT) {

                        GlobalSnackbar.show("Changes Successfully Saved")

                        constants.PostProperty_ViewModel.loadOriginalToCopy()

                        constants.PostProperty_ViewModel.indexClicked  = -1

                    } else {
                        get_Form_Preview_API_CALL { result ->
                            onComplete()
                            if (result == 3) {

                                navController.navigate(PostPropertyFlow.PreviewScreen.route)
                                constants.PostProperty_ViewModel.change_Status_PFs(false)

                            } else if (result == 1) {
                                constants.PostProperty_ViewModel.change_Status_PFs(false)
                                toast("Something went wrong")
                            }
                        }
                    }

                }

                is API_Result_Handling.Error -> {
                    toast("Something went wrong")
                    constants.PostProperty_ViewModel.change_Status_PFs(false)
                }

                is API_Result_Handling.Loading -> {
                }

                else -> {

                    constants.PostProperty_ViewModel.change_Status_PFs(false)
                }
            }
        }
    } else {
        onComplete()
        constants.PostProperty_ViewModel.change_Status_PFs(false)
        GlobalSnackbar.show("Upload At least 3 Images")
    }
}
