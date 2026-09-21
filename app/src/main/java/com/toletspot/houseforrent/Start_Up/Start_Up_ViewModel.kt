package com.toletspot.houseforrent.Start_Up

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.Get_Interest_Data
import com.toletspot.houseforrent.R
import com.toletspot.houseforrent.UI_DataClass.CountryCodeHandler_DC
import com.toletspot.houseforrent.UI_DataClass.Start_Up_DataClass
import com.toletspot.houseforrent.constants
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

enum class UserCredintialState{
    NONE,
    LOGIN,
    REGISTER
}

class Start_Up_ViewModel : ViewModel(){

    fun clearAllData_STVM() {

        _isLoginOrSignVerify.value = 0

        _selectedCountry.value = "Select Country"
        _expandedCountry.value = false
        _selectedState.value = "Select State"
        _expandedState.value = false
        _selectedCity.value = ""
        _expandedCity.value = false
        _searchCity.value = ""
        _showFullCityList.value = false

        searchQuery.value = ""
        showFullList.value = false

        _locationDeniedSwitch.value = true

        phoneNumber = ""
        WaNumber = ""
        countryCode = "+91"
        userName = ""
        otp = ""
        email = ""

        _user_Interests.value = emptyList()
        _selectedCategoryIds.value = emptyList()
        _user_Interests_Selection_List.value = emptyList()

        _showGpsDialog.value = false
        _showLocationSettings.value = false
        _getCurrrentLocation_View.value = false

        _OTP_response.value = ""
        _error_OTP.value = false

        _country_Code_Selected.value = "+91"

        _latitude.value = ""
        _longitude.value = ""
        _country.value = ""
        _state.value = ""
        _city.value = ""
        _pincode.value = ""

    }

    var currentCredintialState = mutableStateOf(UserCredintialState.LOGIN)

    private var  _selectedCountryVm = MutableStateFlow<Country?>(null)

    var selectedCountryVm : StateFlow<Country?> = _selectedCountryVm.asStateFlow()

    fun add_selectedCountry(data : Country){
        _selectedCountryVm.value = data
    }

    fun reset_selectedCountry(){
        _selectedCountryVm.value = Country(
            name ="India",
            dial_code = "+91",
            code = "IN",
            emoji = "\uD83C\uDDEE\uD83C\uDDF3",
            limit  = 10
        )
    }

    private val _isLoginOrSignVerify = MutableStateFlow(0)
    val isLoginOrSignVerify: StateFlow<Int> = _isLoginOrSignVerify

    fun updateLoginState(value: Int) {
        _isLoginOrSignVerify.value = value
    }

    val stateList = listOf(
        "Tamil Nadu", "Kerala", "Karnataka", "Assam", "Jammu", "Meghalaya",
        "Maharashtra", "Uttar Pradesh", "Gujarat", "Rajasthan"
    )

    val cityList = listOf(
        "Chennai", "Kochi", "Bangalore", "Guwahati", "Jammu", "Shillong",
        "Mumbai", "Lucknow", "Ahmedabad", "Jaipur"
    )

    val countryList = listOf(
        "India" , "America" , "China" , "Korea" , "Japan" , "SwitzerLand"
    )

    private val _selectedCountry = MutableStateFlow("Select Country")
    val selectedCountry: StateFlow<String> = _selectedCountry

    fun updateSelectedCountry(value: String) {
        _selectedState.value = value
    }

    private val _expandedCountry = MutableStateFlow(false)
    val expandedCountry: StateFlow<Boolean> = _expandedCountry

    fun setExpandedCountry(value: Boolean) {
        _expandedCountry.value = value
    }

    private val _selectedState = MutableStateFlow("Select State")
    val selectedState: StateFlow<String> = _selectedState

    fun updateSelectedState(value: String) {
        _selectedState.value = value
    }

    private val _expandedState = MutableStateFlow(false)
    val expandedState: StateFlow<Boolean> = _expandedState

    fun setExpandedState(value: Boolean) {
        _expandedState.value = value
    }

    private val _expandedCity = MutableStateFlow(false)
    val expandedCity: StateFlow<Boolean> = _expandedCity

    private val _searchCity = MutableStateFlow("")
    val searchCity: StateFlow<String> = _searchCity

    private val _showFullCityList = MutableStateFlow(false)
    val showFullCityList: StateFlow<Boolean> = _showFullCityList

    private val _selectedCity = MutableStateFlow("")
    val selectedCity: StateFlow<String> = _selectedCity

    fun setExpandedCity(value: Boolean) {
        _expandedCity.value = value
    }

    fun setSearchCity(value: String) {
        _searchCity.value = value
    }

    fun setShowFullCityList(value: Boolean) {
        _showFullCityList.value = value
    }

    fun updateSelectedCity(city: String) {
        _selectedCity.value = city
    }

    fun clearCitySearch() {
        _searchCity.value = ""
        _selectedCity.value = ""
    }

    val searchQuery = MutableStateFlow("")

    val showFullList = MutableStateFlow(false)

    fun setSearchQuery(query: String) { searchQuery.value = query }
    fun setShowFullList(show: Boolean) { showFullList.value = show }

    private val _locationDeniedSwitch = MutableStateFlow(true)
    val locationDenied: StateFlow<Boolean> = _locationDeniedSwitch

    fun setLocationDenied(value: Boolean) {
        _locationDeniedSwitch.value = value
    }

    var _isLoginOrSign = mutableStateListOf(
        Start_Up_DataClass(
            title = constants.activity.getString(R.string.login),
            description = constants.activity.getString(R.string.login_Desc),
            cont_No = constants.activity.getString(R.string.mobile_no)
        ),
        Start_Up_DataClass(
            title = constants.activity.getString(R.string.sign),
            description = constants.activity.getString(R.string.sign_Desc),
            cont_No = constants.activity.getString(R.string.mobile_no),
            user_Name = constants.activity.getString(R.string.sign_Name)
        )

    )

    var phoneNumber by  mutableStateOf("")

    var WaNumber by  mutableStateOf("")
    var countryCode by  mutableStateOf("+91")

    var userName by mutableStateOf("")

    var otp by mutableStateOf("")
    var email by mutableStateOf("")

    private val _user_Interests = MutableStateFlow<List<Get_Interest_Data>>(emptyList())
    val user_Interests: StateFlow<List<Get_Interest_Data>> = _user_Interests.asStateFlow()

    fun clear_Interests(){
        _user_Interests.value = emptyList()
    }

    private val _selectedCategoryIds = MutableStateFlow<List<Int>>(emptyList())
    val selectedCategoryIds: StateFlow<List<Int>> = _selectedCategoryIds.asStateFlow()

    fun setInterests(interests: List<Get_Interest_Data>) {
        interests.forEachIndexed { i, item ->
        }
        _user_Interests.value = interests
        updateSelectedIds()
    }

    fun selectInterestIfNotSelected(id: Int) {
        val alreadySelected = _user_Interests.value.any { it.land_categorie_id == id && it.is_Selected }
        if (!alreadySelected) {
            toggleInterestSelection(id)
        }
    }

    fun restoreUserInterestsold(selectedIds: List<Int>) {
        _user_Interests.update { list ->
            list.map { category ->
                if (category.land_categorie_id in selectedIds) {
                    category.copy(is_Selected = true)
                } else category
            }
        }
    }

    fun toggleInterestSelectionold(categoryId: Int) {
        _user_Interests.value = _user_Interests.value.map { item ->
            if (item.land_categorie_id == categoryId) {
                item.copy(is_Selected = !item.is_Selected)
            } else item
        }
        updateSelectedIds()
    }

    fun toggleInterestSelection_True(categoryId: Int) {
        _user_Interests.value = _user_Interests.value.map { item ->
            if (item.land_categorie_id == categoryId) {
                item.copy(is_Selected = true)
            } else item
        }
        updateSelectedIds()
    }

    fun updateSelectedIds() {
        _selectedCategoryIds.value = _user_Interests.value
            .filter { it.is_Selected }
            .map { it.land_categorie_id }
    }

    fun toggleInterestSelection(categoryId: Int) {
        _user_Interests.update { currentList ->
            currentList.map { category ->
                if (category.land_categorie_id == categoryId) {
                    category.copy(is_Selected = !category.is_Selected)
                } else {
                    category
                }
            }
        }

        updateSelectedIds()
    }

    fun restoreUserInterests(selectedIds: List<Int>) {
        _user_Interests.update { currentList ->
            currentList.map { category ->
                category.copy(is_Selected = selectedIds.contains(category.land_categorie_id))
            }
        }
    }

    val _country_Code_Handler = MutableStateFlow(
        listOf(
            CountryCodeHandler_DC(id = 0, country_Name = "Afghanistan", country_Code = "+93", limit = 9),
            CountryCodeHandler_DC(id = 1, country_Name = "Albania", country_Code = "+355", limit = 9),
            CountryCodeHandler_DC(id = 2, country_Name = "Algeria", country_Code = "+213", limit = 9),
            CountryCodeHandler_DC(id = 3, country_Name = "Andorra", country_Code = "+376", limit = 6),
            CountryCodeHandler_DC(id = 4, country_Name = "Angola", country_Code = "+244", limit = 9),
            CountryCodeHandler_DC(id = 5, country_Name = "Antigua and Barbuda", country_Code = "+1-268", limit = 10),
            CountryCodeHandler_DC(id = 6, country_Name = "Argentina", country_Code = "+54", limit = 10),
            CountryCodeHandler_DC(id = 7, country_Name = "Armenia", country_Code = "+374", limit = 8),
            CountryCodeHandler_DC(id = 8, country_Name = "Australia", country_Code = "+61", limit = 9),
            CountryCodeHandler_DC(id = 9, country_Name = "Austria", country_Code = "+43", limit = 10),
            CountryCodeHandler_DC(id = 10, country_Name = "Azerbaijan", country_Code = "+994", limit = 9),
            CountryCodeHandler_DC(id = 11, country_Name = "Bahamas", country_Code = "+1-242", limit = 10),
            CountryCodeHandler_DC(id = 12, country_Name = "Bahrain", country_Code = "+973", limit = 8),
            CountryCodeHandler_DC(id = 13, country_Name = "Bangladesh", country_Code = "+880", limit = 10),
            CountryCodeHandler_DC(id = 14, country_Name = "Barbados", country_Code = "+1-246", limit = 10),
            CountryCodeHandler_DC(id = 15, country_Name = "Belarus", country_Code = "+375", limit = 9),
            CountryCodeHandler_DC(id = 16, country_Name = "Belgium", country_Code = "+32", limit = 9),
            CountryCodeHandler_DC(id = 17, country_Name = "Belize", country_Code = "+501", limit = 7),
            CountryCodeHandler_DC(id = 18, country_Name = "Benin", country_Code = "+229", limit = 8),
            CountryCodeHandler_DC(id = 19, country_Name = "Bhutan", country_Code = "+975", limit = 8),
            CountryCodeHandler_DC(id = 20, country_Name = "Bolivia", country_Code = "+591", limit = 8),
            CountryCodeHandler_DC(id = 21, country_Name = "Bosnia and Herzegovina", country_Code = "+387", limit = 8),
            CountryCodeHandler_DC(id = 22, country_Name = "Botswana", country_Code = "+267", limit = 7),
            CountryCodeHandler_DC(id = 23, country_Name = "Brazil", country_Code = "+55", limit = 11),
            CountryCodeHandler_DC(id = 24, country_Name = "Brunei", country_Code = "+673", limit = 7),
            CountryCodeHandler_DC(id = 25, country_Name = "Bulgaria", country_Code = "+359", limit = 9),
            CountryCodeHandler_DC(id = 26, country_Name = "Burkina Faso", country_Code = "+226", limit = 8),
            CountryCodeHandler_DC(id = 27, country_Name = "Burundi", country_Code = "+257", limit = 8),
            CountryCodeHandler_DC(id = 28, country_Name = "Cabo Verde", country_Code = "+238", limit = 7),
            CountryCodeHandler_DC(id = 29, country_Name = "Cambodia", country_Code = "+855", limit = 9),
            CountryCodeHandler_DC(id = 30, country_Name = "Cameroon", country_Code = "+237", limit = 9),
            CountryCodeHandler_DC(id = 31, country_Name = "Canada", country_Code = "+1", limit = 10),
            CountryCodeHandler_DC(id = 32, country_Name = "Central African Republic", country_Code = "+236", limit = 8),
            CountryCodeHandler_DC(id = 33, country_Name = "Chad", country_Code = "+235", limit = 8),
            CountryCodeHandler_DC(id = 34, country_Name = "Chile", country_Code = "+56", limit = 9),
            CountryCodeHandler_DC(id = 35, country_Name = "China", country_Code = "+86", limit = 11),
            CountryCodeHandler_DC(id = 36, country_Name = "Colombia", country_Code = "+57", limit = 10),
            CountryCodeHandler_DC(id = 37, country_Name = "Comoros", country_Code = "+269", limit = 7),
            CountryCodeHandler_DC(id = 38, country_Name = "Congo (Brazzaville)", country_Code = "+242", limit = 9),
            CountryCodeHandler_DC(id = 39, country_Name = "Congo (Kinshasa)", country_Code = "+243", limit = 9),
            CountryCodeHandler_DC(id = 40, country_Name = "Costa Rica", country_Code = "+506", limit = 8),
            CountryCodeHandler_DC(id = 41, country_Name = "Croatia", country_Code = "+385", limit = 9),
            CountryCodeHandler_DC(id = 42, country_Name = "Cuba", country_Code = "+53", limit = 8),
            CountryCodeHandler_DC(id = 43, country_Name = "Cyprus", country_Code = "+357", limit = 8),
            CountryCodeHandler_DC(id = 44, country_Name = "Czech Republic", country_Code = "+420", limit = 9),
            CountryCodeHandler_DC(id = 45, country_Name = "Denmark", country_Code = "+45", limit = 8),
            CountryCodeHandler_DC(id = 46, country_Name = "Djibouti", country_Code = "+253", limit = 8),
            CountryCodeHandler_DC(id = 47, country_Name = "Dominica", country_Code = "+1-767", limit = 10),
            CountryCodeHandler_DC(id = 48, country_Name = "Dominican Republic", country_Code = "+1-809", limit = 10),
            CountryCodeHandler_DC(id = 49, country_Name = "Ecuador", country_Code = "+593", limit = 9),
            CountryCodeHandler_DC(id = 50, country_Name = "Egypt", country_Code = "+20", limit = 10),
            CountryCodeHandler_DC(id = 51, country_Name = "El Salvador", country_Code = "+503", limit = 8),
            CountryCodeHandler_DC(id = 52, country_Name = "Equatorial Guinea", country_Code = "+240", limit = 9),
            CountryCodeHandler_DC(id = 53, country_Name = "Eritrea", country_Code = "+291", limit = 7),
            CountryCodeHandler_DC(id = 54, country_Name = "Estonia", country_Code = "+372", limit = 8),
            CountryCodeHandler_DC(id = 55, country_Name = "Eswatini", country_Code = "+268", limit = 8),
            CountryCodeHandler_DC(id = 56, country_Name = "Ethiopia", country_Code = "+251", limit = 9),
            CountryCodeHandler_DC(id = 57, country_Name = "Fiji", country_Code = "+679", limit = 7),
            CountryCodeHandler_DC(id = 58, country_Name = "Finland", country_Code = "+358", limit = 9),
            CountryCodeHandler_DC(id = 59, country_Name = "France", country_Code = "+33", limit = 9),
            CountryCodeHandler_DC(id = 60, country_Name = "Gabon", country_Code = "+241", limit = 8),
            CountryCodeHandler_DC(id = 61, country_Name = "Gambia", country_Code = "+220", limit = 7),
            CountryCodeHandler_DC(id = 62, country_Name = "Georgia", country_Code = "+995", limit = 9),
            CountryCodeHandler_DC(id = 63, country_Name = "Germany", country_Code = "+49", limit = 11),
            CountryCodeHandler_DC(id = 64, country_Name = "Ghana", country_Code = "+233", limit = 9),
            CountryCodeHandler_DC(id = 65, country_Name = "Greece", country_Code = "+30", limit = 10),
            CountryCodeHandler_DC(id = 66, country_Name = "Grenada", country_Code = "+1-473", limit = 10),
            CountryCodeHandler_DC(id = 67, country_Name = "Guatemala", country_Code = "+502", limit = 8),
            CountryCodeHandler_DC(id = 68, country_Name = "Guinea", country_Code = "+224", limit = 9),
            CountryCodeHandler_DC(id = 69, country_Name = "Guinea-Bissau", country_Code = "+245", limit = 7),
            CountryCodeHandler_DC(id = 70, country_Name = "Guyana", country_Code = "+592", limit = 7),
            CountryCodeHandler_DC(id = 71, country_Name = "Haiti", country_Code = "+509", limit = 8),
            CountryCodeHandler_DC(id = 72, country_Name = "Honduras", country_Code = "+504", limit = 8),
            CountryCodeHandler_DC(id = 73, country_Name = "Hungary", country_Code = "+36", limit = 9),
            CountryCodeHandler_DC(id = 74, country_Name = "Iceland", country_Code = "+354", limit = 7),
            CountryCodeHandler_DC(id = 75, country_Name = "India", country_Code = "+91", limit = 10),
            CountryCodeHandler_DC(id = 76, country_Name = "Indonesia", country_Code = "+62", limit = 12),
            CountryCodeHandler_DC(id = 77, country_Name = "Iran", country_Code = "+98", limit = 10),
            CountryCodeHandler_DC(id = 78, country_Name = "Iraq", country_Code = "+964", limit = 10),
            CountryCodeHandler_DC(id = 79, country_Name = "Ireland", country_Code = "+353", limit = 9),
            CountryCodeHandler_DC(id = 80, country_Name = "Israel", country_Code = "+972", limit = 9),
            CountryCodeHandler_DC(id = 81, country_Name = "Italy", country_Code = "+39", limit = 10),
            CountryCodeHandler_DC(id = 82, country_Name = "Jamaica", country_Code = "+1-876", limit = 10),
            CountryCodeHandler_DC(id = 83, country_Name = "Japan", country_Code = "+81", limit = 11),
            CountryCodeHandler_DC(id = 84, country_Name = "Jordan", country_Code = "+962", limit = 9),
            CountryCodeHandler_DC(id = 85, country_Name = "Kazakhstan", country_Code = "+7", limit = 10),
            CountryCodeHandler_DC(id = 86, country_Name = "Kenya", country_Code = "+254", limit = 9),
            CountryCodeHandler_DC(id = 87, country_Name = "Kiribati", country_Code = "+686", limit = 5),
            CountryCodeHandler_DC(id = 88, country_Name = "Kuwait", country_Code = "+965", limit = 8),
            CountryCodeHandler_DC(id = 89, country_Name = "Kyrgyzstan", country_Code = "+996", limit = 9),
            CountryCodeHandler_DC(id = 90, country_Name = "Laos", country_Code = "+856", limit = 9),
            CountryCodeHandler_DC(id = 91, country_Name = "Latvia", country_Code = "+371", limit = 8),
            CountryCodeHandler_DC(id = 92, country_Name = "Lebanon", country_Code = "+961", limit = 8),
            CountryCodeHandler_DC(id = 93, country_Name = "Lesotho", country_Code = "+266", limit = 8),
            CountryCodeHandler_DC(id = 94, country_Name = "Liberia", country_Code = "+231", limit = 8),
            CountryCodeHandler_DC(id = 95, country_Name = "Libya", country_Code = "+218", limit = 9),
            CountryCodeHandler_DC(id = 96, country_Name = "Liechtenstein", country_Code = "+423", limit = 7),
            CountryCodeHandler_DC(id = 97, country_Name = "Lithuania", country_Code = "+370", limit = 8),
            CountryCodeHandler_DC(id = 98, country_Name = "Luxembourg", country_Code = "+352", limit = 9),
            CountryCodeHandler_DC(id = 99, country_Name = "Madagascar", country_Code = "+261", limit = 9),
            CountryCodeHandler_DC(id = 100, country_Name = "Malawi", country_Code = "+265", limit = 9),
            CountryCodeHandler_DC(id = 101, country_Name = "Malaysia", country_Code = "+60", limit = 10),
            CountryCodeHandler_DC(id = 102, country_Name = "Maldives", country_Code = "+960", limit = 7),
            CountryCodeHandler_DC(id = 103, country_Name = "Mali", country_Code = "+223", limit = 8),
            CountryCodeHandler_DC(id = 104, country_Name = "Malta", country_Code = "+356", limit = 8),
            CountryCodeHandler_DC(id = 105, country_Name = "Marshall Islands", country_Code = "+692", limit = 7),
            CountryCodeHandler_DC(id = 106, country_Name = "Mauritania", country_Code = "+222", limit = 8),
            CountryCodeHandler_DC(id = 107, country_Name = "Mauritius", country_Code = "+230", limit = 7),
            CountryCodeHandler_DC(id = 108, country_Name = "Mexico", country_Code = "+52", limit = 10),
            CountryCodeHandler_DC(id = 109, country_Name = "Micronesia", country_Code = "+691", limit = 7),
            CountryCodeHandler_DC(id = 110, country_Name = "Moldova", country_Code = "+373", limit = 8),
            CountryCodeHandler_DC(id = 111, country_Name = "Monaco", country_Code = "+377", limit = 8),
            CountryCodeHandler_DC(id = 112, country_Name = "Mongolia", country_Code = "+976", limit = 8),
            CountryCodeHandler_DC(id = 113, country_Name = "Montenegro", country_Code = "+382", limit = 8),
            CountryCodeHandler_DC(id = 114, country_Name = "Morocco", country_Code = "+212", limit = 9),
            CountryCodeHandler_DC(id = 115, country_Name = "Mozambique", country_Code = "+258", limit = 12),
            CountryCodeHandler_DC(id = 116, country_Name = "Myanmar", country_Code = "+95", limit = 9),
            CountryCodeHandler_DC(id = 117, country_Name = "Namibia", country_Code = "+264", limit = 9),
            CountryCodeHandler_DC(id = 118, country_Name = "Nauru", country_Code = "+674", limit = 7),
            CountryCodeHandler_DC(id = 119, country_Name = "Nepal", country_Code = "+977", limit = 10),
            CountryCodeHandler_DC(id = 120, country_Name = "Netherlands", country_Code = "+31", limit = 9),
            CountryCodeHandler_DC(id = 121, country_Name = "New Zealand", country_Code = "+64", limit = 9),
            CountryCodeHandler_DC(id = 122, country_Name = "Nicaragua", country_Code = "+505", limit = 8),
            CountryCodeHandler_DC(id = 123, country_Name = "Niger", country_Code = "+227", limit = 8),
            CountryCodeHandler_DC(id = 124, country_Name = "Nigeria", country_Code = "+234", limit = 11),
            CountryCodeHandler_DC(id = 125, country_Name = "North Korea", country_Code = "+850", limit = 9),
            CountryCodeHandler_DC(id = 126, country_Name = "North Macedonia", country_Code = "+389", limit = 8),
            CountryCodeHandler_DC(id = 127, country_Name = "Norway", country_Code = "+47", limit = 8),
            CountryCodeHandler_DC(id = 128, country_Name = "Oman", country_Code = "+968", limit = 8),
            CountryCodeHandler_DC(id = 129, country_Name = "Pakistan", country_Code = "+92", limit = 10),
            CountryCodeHandler_DC(id = 130, country_Name = "Palau", country_Code = "+680", limit = 7),
            CountryCodeHandler_DC(id = 131, country_Name = "Panama", country_Code = "+507", limit = 8),
            CountryCodeHandler_DC(id = 132, country_Name = "Papua New Guinea", country_Code = "+675", limit = 8),
            CountryCodeHandler_DC(id = 133, country_Name = "Paraguay", country_Code = "+595", limit = 9),
            CountryCodeHandler_DC(id = 134, country_Name = "Peru", country_Code = "+51", limit = 9),
            CountryCodeHandler_DC(id = 135, country_Name = "Philippines", country_Code = "+63", limit = 10),
            CountryCodeHandler_DC(id = 136, country_Name = "Poland", country_Code = "+48", limit = 9),
            CountryCodeHandler_DC(id = 137, country_Name = "Portugal", country_Code = "+351", limit = 9),
            CountryCodeHandler_DC(id = 138, country_Name = "Qatar", country_Code = "+974", limit = 8),
            CountryCodeHandler_DC(id = 139, country_Name = "Romania", country_Code = "+40", limit = 9),
            CountryCodeHandler_DC(id = 140, country_Name = "Russia", country_Code = "+7", limit = 10),
            CountryCodeHandler_DC(id = 141, country_Name = "Rwanda", country_Code = "+250", limit = 9),
            CountryCodeHandler_DC(id = 142, country_Name = "Saint Kitts and Nevis", country_Code = "+1-869", limit = 10),
            CountryCodeHandler_DC(id = 143, country_Name = "Saint Lucia", country_Code = "+1-758", limit = 10),
            CountryCodeHandler_DC(id = 144, country_Name = "Saint Vincent and the Grenadines", country_Code = "+1-784", limit = 10),
            CountryCodeHandler_DC(id = 145, country_Name = "Samoa", country_Code = "+685", limit = 7),
            CountryCodeHandler_DC(id = 146, country_Name = "San Marino", country_Code = "+378", limit = 10),
            CountryCodeHandler_DC(id = 147, country_Name = "Sao Tome and Principe", country_Code = "+239", limit = 7),
            CountryCodeHandler_DC(id = 148, country_Name = "Saudi Arabia", country_Code = "+966", limit = 9),
            CountryCodeHandler_DC(id = 149, country_Name = "Senegal", country_Code = "+221", limit = 9),
            CountryCodeHandler_DC(id = 150, country_Name = "Serbia", country_Code = "+381", limit = 9),
            CountryCodeHandler_DC(id = 151, country_Name = "Seychelles", country_Code = "+248", limit = 7),
            CountryCodeHandler_DC(id = 152, country_Name = "Sierra Leone", country_Code = "+232", limit = 8),
            CountryCodeHandler_DC(id = 153, country_Name = "Singapore", country_Code = "+65", limit = 8),
            CountryCodeHandler_DC(id = 154, country_Name = "Slovakia", country_Code = "+421", limit = 9),
            CountryCodeHandler_DC(id = 155, country_Name = "Slovenia", country_Code = "+386", limit = 9),
            CountryCodeHandler_DC(id = 156, country_Name = "Solomon Islands", country_Code = "+677", limit = 7),
            CountryCodeHandler_DC(id = 157, country_Name = "Somalia", country_Code = "+252", limit = 9),
            CountryCodeHandler_DC(id = 158, country_Name = "South Africa", country_Code = "+27", limit = 9),
            CountryCodeHandler_DC(id = 159, country_Name = "South Korea", country_Code = "+82", limit = 10),
            CountryCodeHandler_DC(id = 160, country_Name = "South Sudan", country_Code = "+211", limit = 9),
            CountryCodeHandler_DC(id = 161, country_Name = "Spain", country_Code = "+34", limit = 9),
            CountryCodeHandler_DC(id = 162, country_Name = "Sri Lanka", country_Code = "+94", limit = 9),
            CountryCodeHandler_DC(id = 163, country_Name = "Sudan", country_Code = "+249", limit = 9),
            CountryCodeHandler_DC(id = 164, country_Name = "Suriname", country_Code = "+597", limit = 7),
            CountryCodeHandler_DC(id = 165, country_Name = "Sweden", country_Code = "+46", limit = 9),
            CountryCodeHandler_DC(id = 166, country_Name = "Switzerland", country_Code = "+41", limit = 9),
            CountryCodeHandler_DC(id = 167, country_Name = "Syria", country_Code = "+963", limit = 9),
            CountryCodeHandler_DC(id = 168, country_Name = "Taiwan", country_Code = "+886", limit = 9),
            CountryCodeHandler_DC(id = 169, country_Name = "Tajikistan", country_Code = "+992", limit = 9),
            CountryCodeHandler_DC(id = 170, country_Name = "Tanzania", country_Code = "+255", limit = 9),
            CountryCodeHandler_DC(id = 171, country_Name = "Thailand", country_Code = "+66", limit = 9),
            CountryCodeHandler_DC(id = 172, country_Name = "Timor-Leste", country_Code = "+670", limit = 7),
            CountryCodeHandler_DC(id = 173, country_Name = "Togo", country_Code = "+228", limit = 8),
            CountryCodeHandler_DC(id = 174, country_Name = "Tonga", country_Code = "+676", limit = 5),
            CountryCodeHandler_DC(id = 175, country_Name = "Trinidad and Tobago", country_Code = "+1-868", limit = 10),
            CountryCodeHandler_DC(id = 176, country_Name = "Tunisia", country_Code = "+216", limit = 8),
            CountryCodeHandler_DC(id = 177, country_Name = "Turkey", country_Code = "+90", limit = 10),
            CountryCodeHandler_DC(id = 178, country_Name = "Turkmenistan", country_Code = "+993", limit = 8),
            CountryCodeHandler_DC(id = 179, country_Name = "Tuvalu", country_Code = "+688", limit = 5),
            CountryCodeHandler_DC(id = 180, country_Name = "Uganda", country_Code = "+256", limit = 9),
            CountryCodeHandler_DC(id = 181, country_Name = "Ukraine", country_Code = "+380", limit = 9),
            CountryCodeHandler_DC(id = 182, country_Name = "United Arab Emirates", country_Code = "+971", limit = 9),
            CountryCodeHandler_DC(id = 183, country_Name = "United Kingdom", country_Code = "+44", limit = 10),
            CountryCodeHandler_DC(id = 184, country_Name = "United States", country_Code = "+1", limit = 10),
            CountryCodeHandler_DC(id = 185, country_Name = "Uruguay", country_Code = "+598", limit = 8),
            CountryCodeHandler_DC(id = 186, country_Name = "Uzbekistan", country_Code = "+998", limit = 9),
            CountryCodeHandler_DC(id = 187, country_Name = "Vanuatu", country_Code = "+678", limit = 7),
            CountryCodeHandler_DC(id = 188, country_Name = "Vatican City", country_Code = "+379", limit = 10),
            CountryCodeHandler_DC(id = 189, country_Name = "Venezuela", country_Code = "+58", limit = 10),
            CountryCodeHandler_DC(id = 190, country_Name = "Vietnam", country_Code = "+84", limit = 10),
            CountryCodeHandler_DC(id = 191, country_Name = "Yemen", country_Code = "+967", limit = 9),
            CountryCodeHandler_DC(id = 192, country_Name = "Zambia", country_Code = "+260", limit = 9),
            CountryCodeHandler_DC(id = 193, country_Name = "Zimbabwe", country_Code = "+263", limit = 9)
        )
    )

    var country_Code_Handler : StateFlow<List<CountryCodeHandler_DC>> = _country_Code_Handler.asStateFlow()

    fun getLimitForCountryCode(
        code: String,
        countryList: List<CountryCodeHandler_DC>
    ): Int? {
        return countryList.find { it.country_Code == code }?.limit
    }

    private val _showGpsDialog = MutableStateFlow(false)
    val showGpsDialog: StateFlow<Boolean> = _showGpsDialog

    private val _showLocationSettings = MutableStateFlow(false)
    val showLocationSettings: StateFlow<Boolean> = _showLocationSettings

    fun setShowGpsDialog(value: Boolean) {
        _showGpsDialog.value = value
    }

    fun setShowLocationSettings(value: Boolean) {
        _showLocationSettings.value = value
    }

    private val _getCurrrentLocation_View= MutableStateFlow(false)
    val getCurrrentLocation_View: StateFlow<Boolean> = _getCurrrentLocation_View

    fun setShowLocationView(value: Boolean) {
        _getCurrrentLocation_View.update { value }
    }

    private var _OTP_response = MutableStateFlow<String> ("")
    var OTP_Response : StateFlow<String> = _OTP_response.asStateFlow()

    fun put_OTP_Response(otp : String){
        _OTP_response.update { otp }
    }

    fun get_OTP_Response(): String{
        return _OTP_response.value
    }

    private var _error_OTP = MutableStateFlow(false)
    var error_OTP : StateFlow<Boolean> = _error_OTP.asStateFlow()

    fun is_Error_OTP(){
        _error_OTP.value = true
    }

    fun is_Error_OTP_Reset(){
        _error_OTP.value = false
    }

    private val _user_Interests_Selection_List = MutableStateFlow<List<Int>>(emptyList())
    val user_Interests_Selected: StateFlow<List<Int>> = _user_Interests_Selection_List.asStateFlow()

    fun toggle_User_Selections(index: Int) {
        val current = _user_Interests_Selection_List.value.toMutableList()

        if (current.contains(index)) {
            current.remove(index)
        } else {
            current.add(index)
        }

        _user_Interests_Selection_List.value = current
    }

    private var _country_Code_Selected = MutableStateFlow<String>("+91")
    var country_Code_Selected : StateFlow<String> = _country_Code_Selected.asStateFlow()

    fun get_Country_Code():String{
        _country_Code_Selected.update { it }
        return country_Code_Selected.value
    }

    fun put_Country_Code(code : String){
        _country_Code_Selected.update { code }
    }

    private var _latitude = MutableStateFlow<String>("")
    var latitude : StateFlow<String> = _latitude.asStateFlow()

    fun set_Latitude(lat:Double){
        _latitude.update { lat.toString() }
    }

    private var _longitude = MutableStateFlow<String>("")
    var longitude : StateFlow<String> = _longitude.asStateFlow()

    fun set_Longitude(long:Double){
        _longitude.update { long.toString() }
    }

    private var _country = MutableStateFlow<String>("")
    var country : StateFlow<String> = _country.asStateFlow()

    fun set_Country(country:String){
        _country.update { country }
    }

    private var _state = MutableStateFlow<String>("")
    var state : StateFlow<String> = _state.asStateFlow()

    fun set_State(state:String){
        _state.update { state }
    }

    private var _city = MutableStateFlow<String>("")
    var city : StateFlow<String> = _city.asStateFlow()

    fun set_City(city:String){
        _city.update { city }
    }

    private var _pincode = MutableStateFlow<String>("")
    var pincode : StateFlow<String> = _pincode.asStateFlow()

    fun set_Pincode(city:String){
        _pincode.update { city }
    }

    fun Location_Manual_IsNotEmpty() : Boolean{
        if (_country.value.isNotEmpty() && _state.value.isNotEmpty() && _city.value.isNotEmpty() && _pincode.value.isNotEmpty() ){
            return true
        }
        else {
            return false
        }
    }

}
