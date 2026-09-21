package com.toletspot.houseforrent.Navigation


sealed class BottomBarScreen(val route: String, val title: String) {
    object Videos : BottomBarScreen("videos", "Videos")
    object Search : BottomBarScreen("search", "Search")
    object Post : BottomBarScreen("post", "Post")
    object Enquiry : BottomBarScreen("enquiry", "Enquiry")
    object Profile : BottomBarScreen("profile", "Profile")


    companion object {
        val allTabs = listOf(Videos, Search, Post, Enquiry, Profile)
    }
}

/*
sealed class UserCredentialsScreenFlow(val route: String, val title: String) {
    object UserCredentials : UserCredentialsScreenFlow("user_Credentials", "User Credentials")
    object UserInterests : UserCredentialsScreenFlow("userInterests", "User Interests")
    object Common_Screen : UserCredentialsScreenFlow("Common_Screen", "Common Screen")
    object In_App_Notification : UserCredentialsScreenFlow("In_App_Notification" , "In_App_Notification" )

    object Onboarding : UserCredentialsScreenFlow("Onboarding", "Onboarding")

    object Other_Profile_Structure : UserCredentialsScreenFlow("Other_Profile_Structure" , "Other_Profile_Structure")
    object Justify : UserCredentialsScreenFlow("Justify" , "Justify")

}

sealed class VideosScreenFlow(val route: String) {

    object Common_Screen : VideosScreenFlow("Common_Screen")

    object ReelsView : VideosScreenFlow("videos_main")
    object ViewPropertyStructure : VideosScreenFlow("ViewPropertyStructure")

    ///
    object Profile_Structure : VideosScreenFlow("Profile_Structure")

    object Profile_FF_Structure : VideosScreenFlow("Profile_FF_Structure")
    object Other_Profile_Structure : VideosScreenFlow("Other_Profile_Structure")
    object Edit_Property_Option : VideosScreenFlow("Edit_Property_Option")
    object Repost_Property : VideosScreenFlow("Repost_Property")
    object Profile_FF_Structure_Followings : VideosScreenFlow("Profile_FF_Structure_Followings")

    object ReelsView_Search_Flow : VideosScreenFlow("ReelsView_Search_Flow" )
    object In_App_Notification : VideosScreenFlow("In_App_Notification" )



}

sealed class ProfileScreenFlow(val route: String , val title: String) {

    object Common_Screen : ProfileScreenFlow("Common_Screen" , "Common_Screen")

    object Profile_Structure : ProfileScreenFlow("Profile_Structure", "Profile Structure")
    object Profile_FF_Structure : ProfileScreenFlow("Profile_FF_Structure", "Profile FF Structure")
    object Other_Profile_Structure : ProfileScreenFlow("Other_Profile_Structure", "Other Profile Structure")

    object ReelsView_Search_Flow : ProfileScreenFlow("ReelsView_Search_Flow" , "ReelsView_Search_Flow")

    object ViewPropertyStructure : ProfileScreenFlow("ViewPropertyStructure" , "ViewPropertyStructure")
    object Edit_Property_Option : ProfileScreenFlow("Edit_Property_Option" , "Edit_Property_Option")
    object Repost_Property : ProfileScreenFlow("Repost_Property" , "Repost_Property")
    object Post_Property_Forms : ProfileScreenFlow("Post_Property_Forms" , "Post_Property_Forms")

    object SingleVideoPlayerEnquiry : ProfileScreenFlow("SingleVideoPlayerEnquiry" , "SingleVideoPlayerEnquiry")
    object SingleVideoPlayerEnquiry2 : ProfileScreenFlow("SingleVideoPlayerEnquiry2" , "SingleVideoPlayerEnquiry2")

    object Profile_FF_Structure_Followings : ProfileScreenFlow("Profile_FF_Structure_Followings", "Profile_FF_Structure_Followings" )
    object Sold_Outs : ProfileScreenFlow("Sold_Outs", "Sold_Outs" )
    object UserCredentials : ProfileScreenFlow("user_Credentials", "User Credentials")


}

sealed class SearchScreenFlow(val route: String){

    object Common_Screen : SearchScreenFlow("Common_Screen")

    object Search_Main_Screen : SearchScreenFlow("Search_Main_Screen")
    object ReelsView_Search_Flow : SearchScreenFlow("ReelsView_Search_Flow")


    object ReelsView : SearchScreenFlow("videos_main")
    object ViewPropertyStructure : SearchScreenFlow("ViewPropertyStructure")


    object Profile_Structure : SearchScreenFlow("Profile_Structure")
    object Other_Profile_Structure : SearchScreenFlow("Other_Profile_Structure")
    object Profile_FF_Structure : SearchScreenFlow("Profile_FF_Structure")
    object Edit_Property_Option : SearchScreenFlow("Edit_Property_Option")
    object Repost_Property : SearchScreenFlow("Repost_Property")
    object Search_Filter_Sort : SearchScreenFlow("Search_Filter_Sort")

    object Profile_FF_Structure_Followings : SearchScreenFlow("Profile_FF_Structure_Followings",  )






}

sealed class PostPropertyFlow(val route: String){
    object Post_Property_Forms : PostPropertyFlow("Post_Property_Forms")
    object Common_Screen : PostPropertyFlow("Common_Screen")
    object PP_Fourth_Form : PostPropertyFlow("PP_Fourth_Form")

    object ViewPropertyStructure : PostPropertyFlow("ViewPropertyStructure" )

    object Profile_Structure : PostPropertyFlow("Profile_Structure")

    object ReelsView_Search_Flow : PostPropertyFlow("ReelsView_Search_Flow")


}

sealed class EnquiriesFlow(val route: String){

    object Common_Screen : EnquiriesFlow("Common_Screen")

    object SingleVideoPlayerEnquiry : EnquiriesFlow("SingleVideoPlayerEnquiry")
    object Enquiry_Home_Screen : EnquiriesFlow("Enquiry_Home_Screen")

    object ViewPropertyStructure : EnquiriesFlow("ViewPropertyStructure")
    object Profile_Structure : EnquiriesFlow("Profile_Structure")
    object Profile_FF_Structure : EnquiriesFlow("Profile_FF_Structure")
    object Other_Profile_Structure : EnquiriesFlow("Other_Profile_Structure")
    object ReelsView_Search_Flow : EnquiriesFlow("ReelsView_Search_Flow" )


    object Profile_FF_Structure_Followings : EnquiriesFlow("Profile_FF_Structure_Followings" )


}



*/



// ✅ Unified Screen Flow Definitions
// Every sealed class now has all 62 screen objects (same routes across all)

sealed class UserCredentialsScreenFlow(val route: String, val title: String) {

    object Common_Screen : UserCredentialsScreenFlow("Common_Screen", "Common_Screen")
    object UserCredentials : UserCredentialsScreenFlow("user_Credentials", "User Credentials")
    object UserInterests : UserCredentialsScreenFlow("userInterests", "User Interests")
    object Onboarding : UserCredentialsScreenFlow("Onboarding", "Onboarding")
    object Justify : UserCredentialsScreenFlow("Justify", "Justify")

    object Profile_Structure : UserCredentialsScreenFlow("Profile_Structure", "Profile_Structure")
    object Profile_FF_Structure : UserCredentialsScreenFlow("Profile_FF_Structure", "Profile_FF_Structure")
    object Other_Profile_Structure : UserCredentialsScreenFlow("Other_Profile_Structure", "Other_Profile_Structure")
    object Profile_FF_Structure_Followings : UserCredentialsScreenFlow("Profile_FF_Structure_Followings", "Profile_FF_Structure_Followings")

    object ReelsView : UserCredentialsScreenFlow("videos_main", "ReelsView")
    object ReelsView_Search_Flow : UserCredentialsScreenFlow("ReelsView_Search_Flow", "ReelsView_Search_Flow")

    object ViewPropertyStructure : UserCredentialsScreenFlow("ViewPropertyStructure", "ViewPropertyStructure")
    object Edit_Property_Option : UserCredentialsScreenFlow("Edit_Property_Option", "Edit_Property_Option")
    object Repost_Property : UserCredentialsScreenFlow("Repost_Property", "Repost_Property")
    object Post_Property_Forms : UserCredentialsScreenFlow("Post_Property_Forms", "Post_Property_Forms")
    object PP_Fourth_Form : UserCredentialsScreenFlow("PP_Fourth_Form", "PP_Fourth_Form")
    object Sold_Outs : UserCredentialsScreenFlow("Sold_Outs", "Sold_Outs")

    object Enquiry_Home_Screen : UserCredentialsScreenFlow("Enquiry_Home_Screen", "Enquiry_Home_Screen")
    object SingleVideoPlayerEnquiry : UserCredentialsScreenFlow("SingleVideoPlayerEnquiry", "SingleVideoPlayerEnquiry")
    object SingleVideoPlayerEnquiry2 : UserCredentialsScreenFlow("SingleVideoPlayerEnquiry2", "SingleVideoPlayerEnquiry2")

    object Search_Main_Screen : UserCredentialsScreenFlow("Search_Main_Screen", "Search_Main_Screen")
    object Search_Filter_Sort : UserCredentialsScreenFlow("Search_Filter_Sort", "Search_Filter_Sort")

    object In_App_Notification : UserCredentialsScreenFlow("In_App_Notification", "In_App_Notification")
    object Justify_Post : UserCredentialsScreenFlow("Justify_Post" , "Justify_Post")
}

// ----------------------------

sealed class VideosScreenFlow(val route: String) {

    object Common_Screen : VideosScreenFlow("Common_Screen")
    object UserCredentials : VideosScreenFlow("user_Credentials")
    object UserInterests : VideosScreenFlow("userInterests")
    object Onboarding : VideosScreenFlow("Onboarding")
    object Justify : VideosScreenFlow("Justify")

    object Profile_Structure : VideosScreenFlow("Profile_Structure")
    object Profile_FF_Structure : VideosScreenFlow("Profile_FF_Structure")
    object Other_Profile_Structure : VideosScreenFlow("Other_Profile_Structure")
    object Profile_FF_Structure_Followings : VideosScreenFlow("Profile_FF_Structure_Followings")

    object ReelsView : VideosScreenFlow("videos_main")
    object ReelsView_Search_Flow : VideosScreenFlow("ReelsView_Search_Flow")
    object Justify_Post : VideosScreenFlow("Justify_Post")

    object ViewPropertyStructure : VideosScreenFlow("ViewPropertyStructure")
    object Edit_Property_Option : VideosScreenFlow("Edit_Property_Option")
    object Repost_Property : VideosScreenFlow("Repost_Property")
    object Post_Property_Forms : VideosScreenFlow("Post_Property_Forms")
    object PP_Fourth_Form : VideosScreenFlow("PP_Fourth_Form")
    object Sold_Outs : VideosScreenFlow("Sold_Outs")

    object Enquiry_Home_Screen : VideosScreenFlow("Enquiry_Home_Screen")
    object SingleVideoPlayerEnquiry : VideosScreenFlow("SingleVideoPlayerEnquiry")
    object SingleVideoPlayerEnquiry2 : VideosScreenFlow("SingleVideoPlayerEnquiry2")

    object Search_Main_Screen : VideosScreenFlow("Search_Main_Screen")
    object Search_Filter_Sort : VideosScreenFlow("Search_Filter_Sort")

    object In_App_Notification : VideosScreenFlow("In_App_Notification")
}

// ----------------------------

sealed class ProfileScreenFlow(val route: String, val title: String) {

    object Common_Screen : ProfileScreenFlow("Common_Screen", "Common_Screen")
    object UserCredentials : ProfileScreenFlow("user_Credentials", "User Credentials")
    object UserInterests : ProfileScreenFlow("userInterests", "User Interests")
    object Onboarding : ProfileScreenFlow("Onboarding", "Onboarding")
    object Justify : ProfileScreenFlow("Justify", "Justify")

    object Profile_Structure : ProfileScreenFlow("Profile_Structure", "Profile_Structure")
    object Profile_FF_Structure : ProfileScreenFlow("Profile_FF_Structure", "Profile_FF_Structure")
    object Other_Profile_Structure : ProfileScreenFlow("Other_Profile_Structure", "Other_Profile_Structure")
    object Profile_FF_Structure_Followings : ProfileScreenFlow("Profile_FF_Structure_Followings", "Profile_FF_Structure_Followings")

    object ReelsView : ProfileScreenFlow("videos_main", "ReelsView")
    object ReelsView_Search_Flow : ProfileScreenFlow("ReelsView_Search_Flow", "ReelsView_Search_Flow")

    object ViewPropertyStructure : ProfileScreenFlow("ViewPropertyStructure", "ViewPropertyStructure")
    object Edit_Property_Option : ProfileScreenFlow("Edit_Property_Option", "Edit_Property_Option")
    object Repost_Property : ProfileScreenFlow("Repost_Property", "Repost_Property")
    object Post_Property_Forms : ProfileScreenFlow("Post_Property_Forms", "Post_Property_Forms")
    object PP_Fourth_Form : ProfileScreenFlow("PP_Fourth_Form", "PP_Fourth_Form")
    object Sold_Outs : ProfileScreenFlow("Sold_Outs", "Sold_Outs")

    object Enquiry_Home_Screen : ProfileScreenFlow("Enquiry_Home_Screen", "Enquiry_Home_Screen")
    object SingleVideoPlayerEnquiry : ProfileScreenFlow("SingleVideoPlayerEnquiry", "SingleVideoPlayerEnquiry")
    object SingleVideoPlayerEnquiry2 : ProfileScreenFlow("SingleVideoPlayerEnquiry2", "SingleVideoPlayerEnquiry2")

    object Search_Main_Screen : ProfileScreenFlow("Search_Main_Screen", "Search_Main_Screen")
    object Search_Filter_Sort : ProfileScreenFlow("Search_Filter_Sort", "Search_Filter_Sort")

    object In_App_Notification : ProfileScreenFlow("In_App_Notification", "In_App_Notification")
    object Justify_Post : ProfileScreenFlow("Justify_Post" , "Justify_Post")

    object PreviewScreen : PostPropertyFlow( "PreviewScreen")


}

// ----------------------------

sealed class SearchScreenFlow(val route: String) {

    object Common_Screen : SearchScreenFlow("Common_Screen")
    object UserCredentials : SearchScreenFlow("user_Credentials")
    object UserInterests : SearchScreenFlow("userInterests")
    object Onboarding : SearchScreenFlow("Onboarding")
    object Justify : SearchScreenFlow("Justify")

    object Profile_Structure : SearchScreenFlow("Profile_Structure")
    object Profile_FF_Structure : SearchScreenFlow("Profile_FF_Structure")
    object Other_Profile_Structure : SearchScreenFlow("Other_Profile_Structure")
    object Profile_FF_Structure_Followings : SearchScreenFlow("Profile_FF_Structure_Followings")

    object ReelsView : SearchScreenFlow("videos_main")
    object ReelsView_Search_Flow : SearchScreenFlow("ReelsView_Search_Flow")

    object ViewPropertyStructure : SearchScreenFlow("ViewPropertyStructure")
    object Edit_Property_Option : SearchScreenFlow("Edit_Property_Option")
    object Repost_Property : SearchScreenFlow("Repost_Property")
    object Post_Property_Forms : SearchScreenFlow("Post_Property_Forms")
    object PP_Fourth_Form : SearchScreenFlow("PP_Fourth_Form")
    object Sold_Outs : SearchScreenFlow("Sold_Outs")

    object Enquiry_Home_Screen : SearchScreenFlow("Enquiry_Home_Screen")
    object SingleVideoPlayerEnquiry : SearchScreenFlow("SingleVideoPlayerEnquiry")
    object SingleVideoPlayerEnquiry2 : SearchScreenFlow("SingleVideoPlayerEnquiry2")

    object Search_Main_Screen : SearchScreenFlow("Search_Main_Screen")
    object Search_Filter_Sort : SearchScreenFlow("Search_Filter_Sort")

    object In_App_Notification : SearchScreenFlow("In_App_Notification")
    object Justify_Post : SearchScreenFlow("Justify_Post")


}

// ----------------------------

sealed class PostPropertyFlow(val route: String) {

    object Common_Screen : PostPropertyFlow("Common_Screen")
    object UserCredentials : PostPropertyFlow("user_Credentials")
    object UserInterests : PostPropertyFlow("userInterests")
    object Onboarding : PostPropertyFlow("Onboarding")
    object Justify : PostPropertyFlow("Justify")

    object Profile_Structure : PostPropertyFlow("Profile_Structure")
    object Profile_FF_Structure : PostPropertyFlow("Profile_FF_Structure")
    object Other_Profile_Structure : PostPropertyFlow("Other_Profile_Structure")
    object Profile_FF_Structure_Followings : PostPropertyFlow("Profile_FF_Structure_Followings")

    object ReelsView : PostPropertyFlow("videos_main")
    object ReelsView_Search_Flow : PostPropertyFlow("ReelsView_Search_Flow")

    object ViewPropertyStructure : PostPropertyFlow("ViewPropertyStructure")
    object Edit_Property_Option : PostPropertyFlow("Edit_Property_Option")
    object Repost_Property : PostPropertyFlow("Repost_Property")
    object Post_Property_Forms : PostPropertyFlow("Post_Property_Forms")
    object PP_Fourth_Form : PostPropertyFlow("PP_Fourth_Form")
    object Sold_Outs : PostPropertyFlow("Sold_Outs")

    object Enquiry_Home_Screen : PostPropertyFlow("Enquiry_Home_Screen")
    object SingleVideoPlayerEnquiry : PostPropertyFlow("SingleVideoPlayerEnquiry")
    object SingleVideoPlayerEnquiry2 : PostPropertyFlow("SingleVideoPlayerEnquiry2")

    object Search_Main_Screen : PostPropertyFlow("Search_Main_Screen")
    object Search_Filter_Sort : PostPropertyFlow("Search_Filter_Sort")

    object In_App_Notification : PostPropertyFlow("In_App_Notification")
    object Justify_Post : PostPropertyFlow( "Justify_Post")
    object PreviewScreen : PostPropertyFlow( "PreviewScreen")

}

// ----------------------------

sealed class EnquiriesFlow(val route: String) {

    object Common_Screen : EnquiriesFlow("Common_Screen")
    object UserCredentials : EnquiriesFlow("user_Credentials")
    object UserInterests : EnquiriesFlow("userInterests")
    object Onboarding : EnquiriesFlow("Onboarding")
    object Justify : EnquiriesFlow("Justify")

    object Profile_Structure : EnquiriesFlow("Profile_Structure")
    object Profile_FF_Structure : EnquiriesFlow("Profile_FF_Structure")
    object Other_Profile_Structure : EnquiriesFlow("Other_Profile_Structure")
    object Profile_FF_Structure_Followings : EnquiriesFlow("Profile_FF_Structure_Followings")

    object ReelsView : EnquiriesFlow("videos_main")
    object ReelsView_Search_Flow : EnquiriesFlow("ReelsView_Search_Flow")

    object ViewPropertyStructure : EnquiriesFlow("ViewPropertyStructure")
    object Edit_Property_Option : EnquiriesFlow("Edit_Property_Option")
    object Repost_Property : EnquiriesFlow("Repost_Property")
    object Post_Property_Forms : EnquiriesFlow("Post_Property_Forms")
    object PP_Fourth_Form : EnquiriesFlow("PP_Fourth_Form")
    object Sold_Outs : EnquiriesFlow("Sold_Outs")

    object Enquiry_Home_Screen : EnquiriesFlow("Enquiry_Home_Screen")
    object SingleVideoPlayerEnquiry : EnquiriesFlow("SingleVideoPlayerEnquiry")
    object SingleVideoPlayerEnquiry2 : EnquiriesFlow("SingleVideoPlayerEnquiry2")

    object Search_Main_Screen : EnquiriesFlow("Search_Main_Screen")
    object Search_Filter_Sort : EnquiriesFlow("Search_Filter_Sort")

    object In_App_Notification : EnquiriesFlow("In_App_Notification")
    object Justify_Post : EnquiriesFlow( "Justify_Post")

    object Msg_UserList : EnquiriesFlow( "Msg_UserList")
    object Msg_ChatScreen : EnquiriesFlow( "Msg_ChatScreen")

}


