package com.toletspot.houseforrent.Chat

import android.content.Context
import android.util.Log
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import  com.toletspot.houseforrent.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.ChatMainList.Chat_Main_List_Data
import com.toletspot.houseforrent.API.API_Result_Handling
import com.toletspot.houseforrent.AppPreferences
import com.toletspot.houseforrent.CommonText
import com.toletspot.houseforrent.Custom_Assets.ClickHelper
import com.toletspot.houseforrent.Custom_Assets.Common_Popup
import com.toletspot.houseforrent.Custom_Assets.LottiAnimation
import com.toletspot.houseforrent.Custom_Assets.openDialer
import com.toletspot.houseforrent.Custom_Assets.openGmail
import com.toletspot.houseforrent.Custom_Assets.openWhatsApp
import com.toletspot.houseforrent.Custom_Assets.rememberNotchHeightDp
import com.toletspot.houseforrent.Custom_Assets.toast
import com.toletspot.houseforrent.GlobalSnackbar
import com.toletspot.houseforrent.Home_Screen.Enquiry_Module.EnquiryFlow
import com.toletspot.houseforrent.Home_Screen.ProfileModule.ClickGuard
import com.toletspot.houseforrent.Navigation.EnquiriesFlow
import com.toletspot.houseforrent.Navigation.ProfileScreenFlow
import com.toletspot.houseforrent.NetworkStatus
import com.toletspot.houseforrent.UI_DataClass.Profile_Handle_Back
import com.toletspot.houseforrent.constants
import com.toletspot.houseforrent.forTab
import com.toletspot.houseforrent.noRippleClickable
import com.toletspot.houseforrent.rememberNetworkStatus
import com.toletspot.houseforrent.ui.theme.newBlack
import com.toletspot.houseforrent.ui.theme.newBlue
import com.toletspot.houseforrent.ui.theme.newGray
import com.toletspot.houseforrent.ui.theme.newLightBlue
import com.toletspot.houseforrent.ui.theme.newLightGray
import com.toletspot.houseforrent.ui.theme.newPurpleGradient
import com.toletspot.houseforrent.ui.theme.newRedGradienBg
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale




fun formatTime(timestamp: Long): String {
    return SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date(timestamp))
}



/*@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Msg_UserList(
    propertyId: String,
    sellerId: String,
    loggedInUserId: String,
    viewModel: BuyerListViewModel = viewModel(),
    onBuyerClick: (User) -> Unit,
    onLogout: () -> Unit,
    onBack: () -> Unit
) {
    val buyers by viewModel.buyers.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()


    LaunchedEffect(propertyId, sellerId, loggedInUserId) {
        viewModel.loadBuyers(propertyId, sellerId, loggedInUserId)
    }


    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxSize()
    )
    {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
            , verticalAlignment = Alignment.CenterVertically
            , horizontalArrangement = Arrangement.Start
        ) {
            Backer(
                modifier = Modifier,
                onBackClick = {
                    onBack()
                }
            )

            constants.spacer(4)

            var type =
                when {
                    constants.Enquiry_ViewModel.selected_Msg_Filter.value == "1 , 2" -> "Received Enquiries"
                    constants.Enquiry_ViewModel.selected_Msg_Filter.value == "1" -> "Received Enquiry"
                   else   -> "Self Enquiry"

                }

            Text(type,  color = newBlack,
                fontSize = constants.textUnit(24),
                fontFamily = constants.fontFamily(0))
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(9.5f)
        ) {
            if (isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            else if (buyers.isEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                    , verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LottiAnimation(2)
                }

                */
/*Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = rememberNotchHeightDp().value),
                    contentAlignment = Alignment.Center
                )
                {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {

                        Image(painter = painterResource(R.drawable.empty_messages) ,"")

                        Spacer(Modifier.height(16.dp))

                        Text(
                            "",
                            //style = MaterialTheme.typography.bodyLarge,
                            color = newBlack,
                            fontSize = constants.textUnit(16),
                            fontFamily = constants.fontFamily(0)
                        )

                        Spacer(Modifier.height(8.dp))

                        Text(
                            "We’ll alert you when someone messages you.",
                           // style = MaterialTheme.typography.bodyLarge,
                            color = newBlack,
                            fontSize = constants.textUnit(20),
                            fontFamily = constants.fontFamily(1)
                        )
                    }
                }*/
/*
            }
            else {
                LazyColumn() {
                    items( buyers,
                        key = { it.user.userId + "_" + it.user.isOnline }
                    )
                    { userWithUnread ->
                        println("USER IMAGE -- ${userWithUnread.user.profileImage} -- ${buyers}")
                        Msg_User_Item(userWithUnread ) { onBuyerClick(userWithUnread.user) }
                    }
                }
            }
        }
    }
}*/


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Msg_UserList(
    propertyId: String,
    sellerId: String,
    loggedInUserId: String,
    viewModel: BuyerListViewModel = viewModel(),
    onBuyerClick: (User) -> Unit,
    onLogout: () -> Unit,
    onBack: () -> Unit
) {


    var network = rememberNetworkStatus()

    val buyers by viewModel.buyers.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    // 👇 Added state to handle 30-second timeout
    var showTimeoutError by remember { mutableStateOf(false) }

    LaunchedEffect(propertyId, sellerId, loggedInUserId) {
        viewModel.loadBuyers(propertyId, sellerId, loggedInUserId)

        // Start 30-second timer
        showTimeoutError = false
        delay(30_000) // 30 seconds
        if (buyers.isEmpty()) {
            showTimeoutError = true
        }
    }

    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxSize()
    ) {
        // 🔙 Header Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {

            Image(painter = painterResource(R.drawable.left_arrow) , "",
                modifier = Modifier.noRippleClickable {
                    //viewModel.onChatClosed()
                    onBack()
                })
            /*Backer(modifier = Modifier, onBackClick = {

            })*/

            constants.spacer(4)

            val type = when {
                constants.Enquiry_ViewModel.selected_Msg_Filter.value == 1 -> "Received Enquiries"
                constants.Enquiry_ViewModel.selected_Msg_Filter.value == 1 -> "Received Enquiry"
                else -> "Self Enquiry"
            }

            Text(
                type,
                color = newBlack,
                fontSize = constants.textUnit(24),
                fontFamily = constants.fontFamily(0)
            )
        }

        // 🧱 Content section
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(9.5f)
        ) {
            when {

                network.value == NetworkStatus.Offline -> {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxSize()
                            .padding(horizontal = 16.dp),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    )
                    {

                        Column (
                            modifier = Modifier
                                .fillMaxSize()
                            , verticalArrangement = Arrangement.Center
                            , horizontalAlignment = Alignment.CenterHorizontally
                        ){
                            Image(
                                painterResource(R.drawable.nointernerdesign), "",
                                modifier = Modifier.align(Alignment.CenterHorizontally)
                            )

                            Spacer(modifier = Modifier.padding(8.dp))

                            Text(
                                constants.activity.getString(R.string.no_Internet),
                                color = newBlack,
                                fontSize = constants.textUnit(16),
                                fontFamily = constants.fontFamily(0),
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(16.dp).align(Alignment.CenterHorizontally)
                            )
                        }

                    }
                }

                isLoading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                buyers.isEmpty() && !showTimeoutError -> {
                    // Show your Lottie animation initially
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        LottiAnimation(2)
                        Spacer(Modifier.height(16.dp))
                        Text(
                            "Loading buyers...",
                            fontSize = constants.textUnit(16),
                            fontFamily = constants.fontFamily(0),
                            color = Color.Gray
                        )
                    }
                }

                buyers.isEmpty() && showTimeoutError -> {
                    // After 30 seconds, show fallback message
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(R.drawable.emptypostsrento),
                            contentDescription = "Error loading"
                        )

                        Spacer(Modifier.height(12.dp))

                        Text(
                            "Error loading data.",
                            color = newBlack,
                            fontSize = constants.textUnit(18),
                            fontFamily = constants.fontFamily(1)
                        )

                        Spacer(Modifier.height(8.dp))

                        Text(
                            "Try again after some time.",
                            color = Color.Gray,
                            fontSize = constants.textUnit(14),
                            fontFamily = constants.fontFamily(0)
                        )
                    }
                }

                else -> {
                    println("USERSSSSSS -- ${buyers}")
                    // 🧾 Show the list
                    LazyColumn {
                        items(
                            buyers,
                            key = { it.user.userId + "_" + it.user.isOnline }
                        ) { userWithUnread ->
                            println("LAST MESSAGE -- ${userWithUnread.lastMessage}")
                            Msg_User_Item(userWithUnread) { onBuyerClick(userWithUnread.user) }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun Msg_User_Item(userWithUnread: UserWithUnread, onClick: () -> Unit) {

    Log.d("DATA IN USERLIST" ,"${userWithUnread.user}")
    ListItem(
        leadingContent = {
            Box(
                modifier = Modifier
                    .size(if (forTab())56.dp else 40.dp)
                , contentAlignment = Alignment.Center
                //.background(Color.DarkGray)
            )
            {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                        .background(newLightBlue)
                        .padding(2.dp)
                    , contentAlignment = Alignment.Center
                ){
                    SubcomposeAsyncImage(
                        model = userWithUnread.user.profileImage,
                        modifier = Modifier
                            .clip(CircleShape)
                            .fillMaxSize()
                            , contentScale = ContentScale.FillBounds
                        , contentDescription = ""
                    )
                    {
                        val state = painter.state
                        if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(8.dp)
                                , contentAlignment = Alignment.Center
                            ){
                                Text(
                                    text = userWithUnread.user.userName.takeIf { it?.isNotEmpty() == true }?.take(1)?.uppercase() ?: "",
                                    fontSize = constants.textUnit(14),
                                    fontFamily = constants.fontFamily(1),
                                    color = Color.Black
                                )
//                                Image(painter = painterResource(id = R.drawable.ic_launcher_foreground),
//                                    contentDescription = "",modifier = Modifier
//                                        .matchParentSize())
                            }
                        } else {
                            SubcomposeAsyncImageContent()
                        }
                    }
                }

//                Box(
//                    modifier = Modifier
//                        .align(Alignment.BottomEnd)
//                        .size(15.dp)
//                        .clip(CircleShape)
//                        .background(
//                            if (userWithUnread.user.isOnline) Color(0xFF4CAF50) else Color.Gray,
//                            CircleShape
//                        )
//                        .padding(start = 4.dp)
//                )
            }
        },
        headlineContent = {
                Text(userWithUnread.user.userName , color = newBlack,
                    fontSize = constants.textUnit(16),
                    fontFamily = constants.fontFamily(1))
        },
        supportingContent = {
            Text(
                if (userWithUnread.isTyping) "typing..."
                else {
                    val preview = userWithUnread.lastMessage.take(40)
                    if (preview.isEmpty()) "No messages" else preview
                },
                style = MaterialTheme.typography.bodySmall,
                color = if (userWithUnread.isTyping) Color(0xFF6200EE) else Color.Gray,
                maxLines = 1,
                fontSize = constants.textUnit(12),
                fontFamily = constants.fontFamily(3)
            )
        },
        trailingContent = {
            Column (
                verticalArrangement = Arrangement.Center
            ) {
                if (userWithUnread.lastMessageTime != 0L) {
                    Text(
                        formatTime(userWithUnread.lastMessageTime),
                        style = MaterialTheme.typography.labelSmall,
                        color = newGray,
                        fontSize = constants.textUnit(8),
                        fontFamily = constants.fontFamily(2)
                    )
                }

                if (userWithUnread.unreadCount > 0) {
                    Box(
                        modifier = Modifier
                            .size(20.dp)
                            .clip(CircleShape)
                            .background(Color(0xffF7F0DC)), contentAlignment = Alignment.Center
                    ) {
                        Text(
                            if (userWithUnread.unreadCount > 99) "99+" else userWithUnread.unreadCount.toString(),
                            color = newBlue,
                            fontSize = constants.textUnit(10),
                            fontFamily = constants.fontFamily(3),
                            lineHeight = 8.sp,
                            modifier = Modifier
                                .padding(2.dp)
                        )
                    }
                }

            }
        }
        , colors = ListItemDefaults.colors(
            containerColor = Color.White
        )
        , modifier = Modifier
            .clickable {
                onClick()
            }

    )
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Msg_ChatScreen(
    currentUserId: String,
    otherUserId: String,
    otherUserLocation: String,
    otherUserName: String,
    otherUserProfile: String,
    propertyId: String,
    sellerId: String,
    onBack: () -> Unit,
    viewModel: ChatViewModel = viewModel(),
    navController: NavHostController
) {


    var showDropdown by remember { mutableStateOf(false) }


    Log.d("PROFILE DETAILS 000 " , "${otherUserName} , ${otherUserProfile} ${otherUserLocation}")
    val network = rememberNetworkStatus()


    var focusManager = LocalFocusManager.current

    val messages by viewModel.messages.collectAsState()
    val isOtherTyping by viewModel.isOtherTyping.collectAsState()

    var isUserBlocked by remember { mutableStateOf(false) }
    var isBlockedByOtherUser by remember { mutableStateOf(false) }
    var userDeleted by remember { mutableStateOf(false) } // 👈 New flag
    var input by remember { mutableStateOf("") }
    var selectedMessageForDelete by remember { mutableStateOf<ChatMessage?>(null) }
    var showBlockDialog by remember { mutableStateOf(false) }

    val sortedMessages = remember(messages) {
        messages.sortedBy { it.timestamp ?: it.time ?: 0L }
    }
    Log.d("PROFILE DETAILS 1111 " , "${otherUserName} , ${otherUserProfile} ${otherUserLocation}")

    val listState = rememberLazyListState()

    // ✅ Initialize chat and mark as active
    LaunchedEffect(Unit) {
        viewModel.initChat(currentUserId, otherUserId, propertyId, sellerId)
        viewModel.setChatActive(true)

        val buyerId = if (currentUserId == sellerId) otherUserId else currentUserId
        val chatId = FirebaseRepository.generateChatId(propertyId, sellerId, buyerId)

        FirebaseRepository.resetUnreadCount(chatId, currentUserId)
        //FirebaseRepository.markMessagesAsSeen(chatId, currentUserId)

        // ✅ Listen for block updates
        FirebaseRepository.getUsersReference()
            .child(currentUserId)
            .child("blocks")
            .child(otherUserId)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    isUserBlocked = snapshot.getValue(Boolean::class.java) ?: false
                }
                override fun onCancelled(error: DatabaseError) {}
            })

        FirebaseRepository.getUsersReference()
            .child(otherUserId)
            .child("blocks")
            .child(currentUserId)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    isBlockedByOtherUser = snapshot.getValue(Boolean::class.java) == true
                }
                override fun onCancelled(error: DatabaseError) {}
            })


        println("BLOCKED -- ${isUserBlocked}")

        // ✅ Listen if other user account is deleted
        FirebaseRepository.getUsersReference()
            .child(otherUserId)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val user = snapshot.getValue(User::class.java)
                    userDeleted = user == null || user.isAccountDeleted // ✅ true if user missing OR deleted
                }

                override fun onCancelled(error: DatabaseError) {}
            }
            )

    }

    // ✅ Mark chat as closed when leaving
    DisposableEffect(Unit) {
        onDispose {
            viewModel.setChatActive(false)
        }
    }

    // ✅ Auto-scroll to bottom when new messages arrive
    LaunchedEffect(messages.size) {
        if (sortedMessages.isNotEmpty()) {
            listState.animateScrollToItem(sortedMessages.lastIndex)
        }
    }

    val topPadding = rememberNotchHeightDp()

    Log.d("PROFILE DETAILS 222" , "${otherUserName} , ${otherUserProfile} ${otherUserLocation}")

    var data = constants.Enquiry_ViewModel.getSelectedMainListData()

    Log.d("PROFILE DETAILS 6666" , "${data}")


    LaunchedEffect(Unit) {
        viewModel.initChat(currentUserId, otherUserId, propertyId, sellerId)
//        val chatId = FirebaseRepository.generateChatId(propertyId, sellerId,
//            if (currentUserId == sellerId) otherUserId else currentUserId
//        )
//        FirebaseRepository.setUserActiveInChat(chatId, currentUserId, true)
    }

    val chatId = FirebaseRepository.generateChatId(propertyId, sellerId,
        if (currentUserId == sellerId) otherUserId else currentUserId
    )

    DisposableEffect(chatId) {

        FirebaseRepository.setUserActiveInChatNew(chatId, currentUserId)

        // ✅ CORRECT: receiver is CURRENT USER
        FirebaseRepository.observeMessagesAndMarkSeen(chatId, currentUserId)

        FirebaseRepository.resetUnreadCount(chatId, currentUserId)

        onDispose {
            FirebaseRepository.clearUserActiveInChatNew(chatId, currentUserId)
            FirebaseRepository.removeSeenListener(chatId)
        }
    }


    val context = LocalContext.current


    Scaffold(
        topBar = {
            Row(modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(Color.White)
                .padding(top = topPadding.value)
                .padding(all = 10.dp),
                verticalAlignment = Alignment.CenterVertically ,
                horizontalArrangement = Arrangement.SpaceBetween)
            {
                Row(verticalAlignment = Alignment.CenterVertically  ,
                    horizontalArrangement = Arrangement.Start)
                {

                    Image(painter = painterResource(R.drawable.left_arrow) , "",
                        modifier = Modifier
                            .noRippleClickable {
                                onBack()
                            })
//                Backer(
//                    modifier = Modifier
//                ) {
//
//                }

                    Spacer(modifier = Modifier.width(10.dp))

                    Box(modifier = Modifier
                        .size(if (forTab()) 46.dp else 36.dp)
                        .noRippleClickable {
                            ClickHelper.getInstance().clickOnce {
                                if (ClickGuard.canClick()) {

                                    constants.Profile_ViewModel.clearSelectedUserProfile()


                                    constants.Profile_ViewModel.add_Selected_User_Name(
                                        data?.user_details?.first()?.username ?: "Profile"
                                    )

                                    constants.Profile_ViewModel.add_BF_Handler(
                                        Profile_Handle_Back(
                                            current_UsedId = AppPreferences.getUserId(),
                                            other_UserId = data?.user_details?.first()?.user_id ?: 0,
                                            ff_User_Name = data?.user_details?.first()?.username ?: "",
                                            ff_Fw_Count = 0,
                                            ff_Fg_Count = 0,
                                        )
                                    )

                                    constants.Profile_ViewModel.addProfile(
                                        data?.user_details?.first()?.user_id ?: 0
                                    )
                                    constants.Profile_ViewModel.add_Selected_Profile_Id(
                                        id = data?.user_details?.first()?.user_id ?: 0
                                    )
                                    constants.Profile_ViewModel.clear_SearchList_FF()
                                    constants.API_Vm.totalPages_Profile_Posts = 1

                                    //isLoadingChange.value = true

                                    navController.navigate(ProfileScreenFlow.Other_Profile_Structure.route)
                                }
                            }
                        }
                        , contentAlignment = Alignment.Center)
                    {

                        SubcomposeAsyncImage(
                            model = otherUserProfile,
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape)
                            , contentDescription = ""
                            , contentScale = ContentScale.FillBounds
                        )
                        {
                            val state = painter.state
                            if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(newLightBlue)
                                    //.padding(8.dp)
                                    , contentAlignment = Alignment.Center
                                ){
                                    Text(
                                        text = otherUserName.takeIf { it?.isNotEmpty() == true }?.take(1)?.uppercase() ?: "",
                                        fontSize = constants.textUnit(14),
                                        fontFamily = constants.fontFamily(1),
                                        color = Color.Black
                                    )
                                }
                            } else {
                                SubcomposeAsyncImageContent()
                            }
                        }


                        var isOnlineOtherUser = remember { mutableStateOf(false) }
                            FirebaseRepository.observeOtherUserOnlineStatus(otherUserId) { isOnline ->
                                isOnlineOtherUser.value = isOnline
                        }

                        Box(modifier = Modifier
                            //.padding(5.dp)
                            .align(Alignment.BottomEnd)
                            .clip(CircleShape)
                            .size(15.dp)
                            .background(if (isOnlineOtherUser.value)newBlue else newGray)
                            .border(2.dp, Color.White, CircleShape)
                        ) { }

                    }

                    Spacer(modifier = Modifier.width(5.dp))

                    Column(verticalArrangement = Arrangement.Center ,
                        horizontalAlignment = Alignment.Start)
                    {
                        Text(otherUserName ,
                            fontSize = 16.sp ,
                            fontWeight = FontWeight.SemiBold ,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = Color.Black)

                        Spacer(modifier = Modifier.height(3.dp))

                        val locationText = otherUserLocation
                            .replace(",", "")
                            .trim()

                        Text(
                            text = if (locationText.isEmpty()) "Location" else otherUserLocation,
                            fontSize = 14.sp ,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = Color.Black
                        )
                    }
                }

                println("BLOBLLKKJKJKJKN -- $isUserBlocked")

                if (!userDeleted && !isUserBlocked) {
                    ChatActionsRow(
                        context = context,
                        data = data,
                        currentUserId = AppPreferences.getUserId().toString(),
                        otherUserId = otherUserId,
                        isUserBlocked = isBlockedByOtherUser,
                        onBlockClick = {
                            showBlockDialog = true
                        }
                    )
                }
            }
        },
        bottomBar = {
            // ✅ If user deleted → disable chat input
            if (userDeleted) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "You can’t send messages. This user is no longer available.",
                        color = Color.Gray,
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center
                    )
                }
            }
            else {
                ChatInputBar(
                    input = input,
                    onInputChange = {
                        input = it
                        if (!isUserBlocked) {
                            viewModel.setTyping(currentUserId, it.isNotBlank())
                        }
                    },
                    onSend = {
                        if (network.value == NetworkStatus.Online)
                        {

                            val buyerId =
                                if (currentUserId == sellerId) otherUserId else currentUserId
                            val chatId =
                                FirebaseRepository.generateChatId(propertyId, sellerId, buyerId)


                            if (!isUserBlocked && input.isNotBlank()) {

                                val messageText = input.trim()

                                viewModel.sendMessage(
                                    messageText,
                                    currentUserId,
                                    otherUserId,
                                    propertyId,
                                    sellerId,
                                    //status = if (isActiveOuter.value) "seen" else "sent"
                                )

                                focusManager.clearFocus()

                                FirebaseRepository.isOtherUserActive(
                                    chatId,
                                    otherUserId
                                )
                                { isActive ->
                                    println("IS ACTIVE OR NOT -- ${isActive}")

                                    if (!isActive) {
                                        constants.API_Vm.send_chat_notification(
                                            user_id = AppPreferences.getUserId(),
                                            receiver_id = otherUserId.toInt(),
                                            message = messageText,
                                            user_post_id = propertyId.toInt(),
                                        )
                                        { apiResultHandling ->
                                            when (apiResultHandling) {
                                                is API_Result_Handling.Loading -> {}

                                                is API_Result_Handling.NoData -> {}

                                                is API_Result_Handling.Error -> {}

                                                is API_Result_Handling.Success -> {}

                                                is API_Result_Handling.Deactivated -> {}
                                            }
                                        }
                                    }
                                }

                                viewModel.clearSetTyping(currentUserId)
                                input = ""
                            }
                        }
                        else {
                            toast("Check your Internet Connection")
                        }
                    },
                    enabled = !isUserBlocked
                )
            }
        },
        content = { paddingValues ->
            Column(modifier = Modifier.padding(top = paddingValues.calculateTopPadding() ,
                bottom = paddingValues.calculateBottomPadding())
                .fillMaxSize())
            {
                when {
                    isUserBlocked -> {
                        // 🔴 Show banner if user is blocked
                        Box(
                            modifier = Modifier
                                .fillMaxHeight()
                                .fillMaxWidth()
                                .background(Color.White)
                                .padding(16.dp)
                        )
                        {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Color.White)
                                , verticalArrangement = Arrangement.Center
                                , horizontalAlignment = Alignment.CenterHorizontally
                            )
                            {
                                AsyncImage(
                                    model = R.drawable.chatblocked,
                                    "",
                                    modifier = Modifier
                                        .size(70.dp)
                                )

                                Spacer(modifier = Modifier.height(16.dp))

                                Text(
                                    text =  "You blocked this user",
                                    color = newBlack,
                                    fontSize = constants.textUnit(16),
                                    fontFamily = constants.fontFamily(1)
                                )

                                Spacer(modifier = Modifier.height(16.dp))

                                Text(
                                    text =  "Unblock to see their property posts and to contact them.",
                                    color = newBlack,
                                    textAlign = TextAlign.Center,
                                    fontSize = constants.textUnit(12),
                                    fontFamily = constants.fontFamily(2)
                                )

                                constants.spacer(8)

                                Box(
                                    modifier = Modifier
                                        .width(80.dp)
                                        .height(36.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(Brush.verticalGradient(newPurpleGradient))
                                        .noRippleClickable {
                                            FirebaseRepository.unblockUser(
                                                currentUserId,
                                                otherUserId
                                            )
//                                            viewModel.unblockUserMessages(
//                                                currentUserId
//                                            )
                                            toast("Successfully Unblocked")
                                        }
                                    , contentAlignment = Alignment.Center
                                ){
                                    CommonText("Unblock",
                                        Color.White,
                                        14,
                                        0
                                    )
                                }


                            }
//                        Row(
//                            modifier = Modifier.fillMaxWidth(),
//                            horizontalArrangement = Arrangement.Center,
//                            verticalAlignment = Alignment.CenterVertically
//                        ) {
////                        Icon(
////                            Icons.Default.Close,
////                            contentDescription = "Blocked",
////                            tint = Color.Red,
////                            modifier = Modifier.size(18.dp)
////                        )
//                            Spacer(Modifier.width(8.dp))
//                            Text(
//                                "You have blocked this user",
//                                color = Color.Red,
//                                style = MaterialTheme.typography.bodySmall
//                            )
//                        }
                        }
                    }
                    /* userDeleted -> {
                         // 🔴 Show banner if user is deleted
                         Box(
                             modifier = Modifier
                                 .fillMaxWidth()
                                 .height(100.dp)
                                 .background(Color(0xFFFFF3E0))
                                 .padding(16.dp)
                         )
                         {
                             Box(
                                 modifier = Modifier
                                     .fillMaxWidth()
                                     .background(Color(0xFFFFF3E0))
                                     .padding(12.dp)
                             ) {
                                 Row(
                                     modifier = Modifier.fillMaxWidth(),
                                     horizontalArrangement = Arrangement.Center,
                                     verticalAlignment = Alignment.CenterVertically
                                 ) {
                                     Image(
                                        painter = painterResource(R.drawable.deactivated),
                                         contentDescription = "User Deleted",
                                         //tint = Color(0xFFFF6F00),
                                         modifier = Modifier.size(18.dp)
                                     )
                                     Spacer(Modifier.width(8.dp))
                                     Text(
                                         "You can’t send messages. This user is no longer available.",
                                         color = Color(0xFFBF360C),
                                         style = MaterialTheme.typography.bodySmall,
                                         fontWeight = FontWeight.Medium
                                     )
                                 }
                             }

                         }
                     }*/
                    else -> {
                        // ✅ Chat messages list
                        if (sortedMessages.isEmpty()){
                            Column(
                                modifier = Modifier
//                                .fillMaxWidth()
                                    .fillMaxSize()
                                    .background(newLightGray)
                                , verticalArrangement = Arrangement.Center
                                , horizontalAlignment = Alignment.CenterHorizontally
                            )
                            {
                                AsyncImage(
                                    model = R.drawable.emptychating,
                                    "",
                                    modifier = Modifier
                                        .size(70.dp)
                                )

//                                Spacer(modifier = Modifier.height(16.dp))
//
//                                Text(
//                                    text =  "Chat looks fresh!",
//                                    color = newBlack,
//                                    fontSize = constants.textUnit(16),
//                                    fontFamily = constants.fontFamily(1)
//                                )

                                Spacer(modifier = Modifier.height(16.dp))

                                Text(
                                    text =  "Start fresh, send your first message!",
                                    color = newBlack,
                                    textAlign = TextAlign.Center,
                                    fontSize = constants.textUnit(12),
                                    fontFamily = constants.fontFamily(2)
                                )


                            }
                        }
                        else {
                            LazyColumn(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxWidth(),
                                state = listState,
                                reverseLayout = false,
                                contentPadding = PaddingValues(vertical = 8.dp)
                            )
                            {
                                item {
                                    Column(
                                        modifier = Modifier
                                            .padding(horizontal = 8.dp)
                                            .clip(RoundedCornerShape(8.dp))
                                            .background(Color(0xffEBEBEB).copy(.5f))
                                            .border(1.dp , Color(0xffCECECE) ,RoundedCornerShape(8.dp))
                                            .padding(horizontal = 16.dp, vertical = 16.dp)
                                            .noRippleClickable {
                                                ClickHelper.getInstance().clickOnce {
                                                    if (data?.delete_post == 1) {
                                                        GlobalSnackbar.show("Property Not Available ")
                                                    } else {
                                                        constants.Reels_ViewModel.clear_view_pro_Details()

                                                        constants.Enquiry_ViewModel.set_EnquiryFlow(EnquiryFlow.CHAT)
                                                        constants.Enquiry_ViewModel.clear_MyleadsEnquiry()
                                                        constants.Enquiry_ViewModel.clear_SelfEnquiry()
                                                        //reels_Show.value = true
                                                        println("content[index]?.post_user --- ${data?.video_model}")
                                                        val postUser = data?.video_model
                                                            //?.toPostUser()

                                                        if (postUser != null) {
                                                            println("Mapped PostUser -> $postUser")

//                                                            val videoJson =
//                                                                Uri.encode(Json.encodeToString(postUser))
//                                                            navController.navigate("${EnquiriesFlow.SingleVideoPlayerEnquiry.route}/$videoJson")

                                                            constants.Reels_ViewModel.clear_view_pro_Details()


                                                            navController.currentBackStackEntry
                                                                ?.savedStateHandle
                                                                ?.set("post_id", postUser.user_post_id)

                                                            navController.navigate(EnquiriesFlow.SingleVideoPlayerEnquiry.route)


                                                        } else {
                                                            Log.e(
                                                                "Mapper",
                                                                "video_model is null — cannot map to PostUser"
                                                            )
                                                        }

                                                    }
                                                }
                                            }
                                    )
                                    {
                                        Row (
                                            modifier = Modifier
                                                .fillMaxWidth()
                                            , verticalAlignment = Alignment.CenterVertically
                                            , horizontalArrangement = Arrangement.SpaceBetween
                                        ){
                                            Text("Enquired for this property")
                                            Row (
                                                verticalAlignment = Alignment.CenterVertically
                                                , horizontalArrangement = Arrangement.spacedBy(2.dp)
                                            ) {
                                                Text("View details"
                                                    , color = newBlack
                                                    , fontSize = constants.textUnit(12)
                                                    , fontFamily = constants.fontFamily(1))
                                                Icon(painter = painterResource(R.drawable.right_arrow) , "",
                                                    tint = newBlue
                                                )
                                            }

                                        }

                                        constants.spacer(4)

                                        ListItem(
                                            headlineContent = {
                                                Text(data?.video_model?.post_property?.property_name ?: "")
                                            }
                                            , supportingContent = {

                                                Row(
                                                    horizontalArrangement = Arrangement.Center
                                                    , verticalAlignment = Alignment.Top
                                                ) {
                                                    SubcomposeAsyncImage(
                                                        model = R.drawable.locationpinenquiry,
                                                        contentDescription = "",
                                                        contentScale = ContentScale.FillBounds,
                                                        modifier = Modifier.size(14.dp)
                                                    )

                                                    Text("${data?.video_model?.post_property?.city ?:""} , ${data?.video_model?.post_property?.state ?:""} ")
                                                }
                                            }
                                            , trailingContent = {
                                                SubcomposeAsyncImage(
                                                    model =  data?.video_model?.post_property?.thumbnail ?:"",
                                                    modifier = Modifier
                                                        .width(90.dp)
                                                        .height(50.dp),
                                                    contentDescription = "",
                                                    contentScale = ContentScale.FillBounds
                                                )
                                                {
                                                    val state = painter.state
                                                    if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {
                                                        Box(
                                                            modifier = Modifier
                                                                .fillMaxSize()
                                                                .background(newLightBlue)

                                                            //.padding(8.dp)
                                                            , contentAlignment = Alignment.Center
                                                        ) {
                                                            Image(painter = painterResource(R.drawable.emptypostsrento) , "")
                                                        }
                                                    } else {
                                                        SubcomposeAsyncImageContent()
                                                    }
                                                }
                                            }
                                            , colors = ListItemDefaults.colors(
                                                containerColor = Color.White
                                            )
                                            , modifier = Modifier
                                                .padding(horizontal = 4.dp)
                                        )
                                    }

                                    constants.spacer(8)
                                }
                                items(sortedMessages,
                                    key = { msg -> msg.messageId.ifBlank { "${msg.senderId}_${msg.timestamp ?: msg.time}" } } // ✅ fallback key
                                    //  key = { it.messageId }
                                ) { message ->
                                    ChatBubble(
                                        msg = message,
                                        currentUserId = currentUserId,
                                        onLongPress = { selectedMessageForDelete = message }
                                    )
                                }

                                println("TYPING INDICATOR -- ${isUserBlocked} __ ${userDeleted} ---- ${isOtherTyping}")

                                if (isOtherTyping && !isUserBlocked && !userDeleted) {
                                    item { TypingIndicator() }
                                }
                            }
                        }

                        HorizontalDivider()
                    }
                }
            }
        } ,
        modifier = Modifier.fillMaxSize()
            //.imePadding()
    )



    // ✅ Delete dialog logic remains unchanged
    DeleteMessageDialog(
        message = selectedMessageForDelete,
        currentUserId = currentUserId,
        onDelete = { option ->
            selectedMessageForDelete?.let {
                when (option) {
                    "me" -> viewModel.deleteForMe(it.messageId, currentUserId)
                    "everyone" -> viewModel.deleteForEveryone(it.messageId, it.time ?: 0L)
                }
            }
            selectedMessageForDelete = null
        },
        onDismiss = { selectedMessageForDelete = null }
    )

    // ✅ Block/unblock dialog unchanged
    if (showBlockDialog) {

        Common_Popup(
            showBlockDialog,
            modifier = Modifier
                .background(Color(0xffF7F0DC))
            , image = otherUserProfile ,
            userName = otherUserName,
            icon = 0 /// or R.drawable
        )
        {
            Column (
                modifier = Modifier
                    .wrapContentHeight()
                    .padding(horizontal = 24.dp)
                , verticalArrangement = Arrangement.spacedBy(12.dp)
                , horizontalAlignment = Alignment.CenterHorizontally
            )
            {

               // Spacer(modifier = Modifier.padding(2.dp))
                constants.spacer(2)

                Image(painter = painterResource(R.drawable.rentoprofileblock) , "",
                    modifier = Modifier.size(64.dp)
                )

                constants.spacer(2)

//                Text(
//                    text = "${if (!isUserBlocked)"Block" else "Unblock"} ${otherUserName} ?",
//                    color = newBlack,
//                    fontSize = constants.textUnit(16),
//                    fontFamily = constants.fontFamily(0)
//                )

                //constants.spacer(2)

                Text(
                    text = "You’ve blocked this user. Unblock to continue the conversation.",
                    color = newBlack,
                    fontSize = constants.textUnit(14),
                    fontFamily = constants.fontFamily(1)
                    , textAlign = TextAlign.Center
                    , modifier = Modifier.padding(horizontal = if (forTab()) 46.dp else 36.dp)
                )

                constants.spacer(2)

                Row (
                    modifier = Modifier
                    , horizontalArrangement = Arrangement.Center
                    , verticalAlignment = Alignment.CenterVertically
                )
                {
                    Box(
                        modifier = Modifier
                            .weight(4f)
                            .height(if (forTab()) 56.dp else 46.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xffE8E8E8))
                            .noRippleClickable {
                                showBlockDialog = false
                            }
                        , contentAlignment = Alignment.Center
                    ){
                        Text(
                            text = "Cancel",
                            color = newBlack,
                            fontSize = constants.textUnit(14),
                            fontFamily = constants.fontFamily(0)
                        )
                    }

                    Spacer(modifier = Modifier.weight(.5f))

                    Box(
                        modifier = Modifier
                            .weight(4f)
                            .height(if (forTab()) 56.dp else 46.dp)
                            .noRippleClickable {
                                ClickHelper.getInstance().clickOnce {
                                    if (ClickGuard.canClick()) {
                                        if (network.value == NetworkStatus.Online) {
                                            println("BLOCKED API CALL HIT STATUS __ ${constants.Profile_ViewModel.get_Block_Status()} -${isUserBlocked}-- ")

                                            viewModel.clearSetTyping(currentUserId)
                                            constants.API_Vm.put_Block_User(
                                                user_id = AppPreferences.getUserId(),
                                                blocker_id = data?.user_details?.firstOrNull()?.user_id
                                                    ?: 0,
                                                //profile_Content.value?.user_id ?: 0,
                                                status = if (!isUserBlocked) 1 else 0
                                                //if (profile_Content.value?.is_blocked == 0) "1" else "0"
                                            )
                                            { apiResultHandling ->
                                                when (apiResultHandling) {
                                                    is API_Result_Handling.Error -> {
                                                        //errror
                                                        toast("Something went wrong")
                                                        //constants.Profile_ViewModel.change_Update_profile(false)
                                                    }

                                                    is API_Result_Handling.Deactivated -> {
                                                        // resultCallback(5)
                                                    }

                                                    is API_Result_Handling.NoData -> {
                                                        // no data
                                                    }

                                                    is API_Result_Handling.Loading -> {
                                                        //loading
                                                        // constants.Profile_ViewModel.change_Update_profile(true)
                                                    }

                                                    is API_Result_Handling.Success -> {


                                                        if (isUserBlocked) {
                                                            FirebaseRepository.unblockUser(
                                                                currentUserId,
                                                                otherUserId
                                                            )
//                                                            viewModel.unblockUserMessages(
//                                                                currentUserId
//                                                            )
                                                            toast("Successfully Unblocked")
                                                        } else {
                                                            FirebaseRepository.blockUser(
                                                                currentUserId,
                                                                otherUserId
                                                            )
                                                            viewModel.blockUserMessages(
                                                                currentUserId
                                                            )
                                                            toast("Successfully Blocked")
                                                        }

                                                        showBlockDialog = false
                                                        //constants.Profile_ViewModel.enable_Edit_Profile()
                                                        //constants.Profile_ViewModel.change_Update_profile(false)
                                                        //success
                                                    }
                                                }
                                            }
                                        } else {
                                            GlobalSnackbar.show(constants.activity.getString(R.string.no_Internet))
                                        }
                                    }
                                }
                            }
                            .clip(RoundedCornerShape(8.dp))
                            .background(Brush.verticalGradient(newRedGradienBg))
                        , contentAlignment = Alignment.Center
                    ){
                        Text(
                            text = if (!isUserBlocked)"Block" else "Unblock",
                            color = Color.White,
                            fontSize = constants.textUnit(16),
                            fontFamily = constants.fontFamily(0)
                        )
                    }
                }

                constants.spacer(2)
            }
        }


       /* AlertDialog(
            onDismissRequest = { showBlockDialog = false },
            title = { Text(if (isUserBlocked) "Unblock User?" else "Block User?") },
            text = {
                Text(
                    if (isUserBlocked)
                        "Unblock this user to send messages again?"
                    else
                        "Block this user? You won't be able to send messages."
                )
            },
            confirmButton = {
                Button(onClick = {
                    if (isUserBlocked) {
                        FirebaseRepository.unblockUser(currentUserId, otherUserId)
                        viewModel.unblockUserMessages(currentUserId)
                    } else {
                        FirebaseRepository.blockUser(currentUserId, otherUserId)
                        viewModel.blockUserMessages(currentUserId)
                    }
                    showBlockDialog = false
                }) {
                    Text(if (isUserBlocked) "Unblock" else "Block")
                }
            },
            dismissButton = {
                TextButton(onClick = { showBlockDialog = false }) {
                    Text("Cancel")
                }
            }
        )*/
    }
}



@Composable
fun ChatActionsRow(
    context: Context,
    viewModel: ChatViewModel = viewModel(),
    data: Chat_Main_List_Data?,
    currentUserId: String,
    otherUserId: String,
    isUserBlocked: Boolean,
    onBlockClick : () -> Unit
) {
    var showDropdown by remember { mutableStateOf(false) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        // 📧 Email Icon
        Image(
            painter = painterResource(R.drawable.rentochatemail),
            contentDescription = "Send Email",
            modifier = Modifier
                .size(25.dp)
                .noRippleClickable {
                    if (isUserBlocked){
                        GlobalSnackbar.show("You can’t send emails right now")
                    }
                    else {
                        openGmail(
                            context = context,
                            to = data?.user_details?.firstOrNull()?.email ?: "",
                            cc = null
                        )
                    }
                }
        )

        constants.spacer(4)

        // 📞 Call Icon
        Image(
            painter = painterResource(R.drawable.rentochatcall),
            contentDescription = "Call User",
            modifier = Modifier
                .size(25.dp)
                .noRippleClickable {
                    if (isUserBlocked){
                        GlobalSnackbar.show("You can’t make calls right now")
                    }
                    else {
                        openDialer(
                            context,
                            data?.user_details?.firstOrNull()?.phone_num ?: ""
                        )
                    }
                }
        )

        constants.spacer(4)

        // ⋮ More Icon with Dropdown
        Box {
            Image(
                painter = painterResource(R.drawable.rentochatmore),
                contentDescription = "More Options",
                modifier = Modifier
                    .size(25.dp)
                    .noRippleClickable {
                        showDropdown = true
                    }
            )

            DropdownMenu(
                expanded = showDropdown,
                onDismissRequest = { showDropdown = false }
                , containerColor = Color.White
            ) {
                DropdownMenuItem(
                    text = { Text("Clear Chat") },
                    onClick = {
                        viewModel.clearChatForMe(currentUserId)
                        showDropdown = false
                    }
                )

                DropdownMenuItem(
                    text = {
                        Text(if (isUserBlocked) "Unblock User" else "Block User")
                    },
                    onClick = {
                        onBlockClick()
//                        if (isUserBlocked) {
//                            FirebaseRepository.unblockUser(currentUserId, otherUserId)
//                            viewModel.unblockUserMessages(currentUserId)
//                        } else {
//                            FirebaseRepository.blockUser(currentUserId, otherUserId)
//                            viewModel.blockUserMessages(currentUserId)
//                        }
                        showDropdown = false
                    }
                )

                DropdownMenuItem(
                    text = { Text("Chat in WhatsApp") },
                    onClick = {
                        val phone = data?.user_details?.firstOrNull()?.phone_num ?: ""
                        openWhatsApp(context, phone)
                        showDropdown = false
                    }
                )
            }
        }
    }
}


@Composable
fun ChatBubble(
    msg: ChatMessage,
    currentUserId: String,
    onLongPress: () -> Unit
) {
    val displayText = if (msg.deletedForEveryone) "This message was deleted" else msg.message
    val isCurrentUser = msg.senderId == currentUserId
    val alignment = if (isCurrentUser) Arrangement.End else Arrangement.Start

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 8.dp),
        horizontalArrangement = alignment
    ) {
        Column() {
            Box(
                modifier = Modifier
                    .combinedClickable(
                        onClick = {},
                        onLongClick = onLongPress
                    )
                    .widthIn(max = 280.dp)
                    .clip(RoundedCornerShape(topStart = 16.dp , topEnd = 16.dp , bottomEnd = if(isCurrentUser)0.dp else 16.dp, bottomStart = if(isCurrentUser)16.dp else 0.dp))
                    .background(  when {
                    msg.deletedForEveryone -> Brush.verticalGradient(listOf(Color.White , Color.White))
                        //Color(0xFFF0F0F0)
                    isCurrentUser -> Brush.verticalGradient(newPurpleGradient)
                        //Color(0xffD4AF37)
                    else -> Brush.verticalGradient(listOf(Color(0xffEBEBEB).copy(.5f) , Color(0xffEBEBEB).copy(.5f)))
                        //Color(0xffEBEBEB).copy(.5f)
                    //Color(0xFFE3F2FD)
                    //0xFFF5F5F5)
                })
                //shape = RoundedCornerShape(topStart = 16.dp , topEnd = 16.dp , bottomEnd = if(isCurrentUser)0.dp else 16.dp, bottomStart = if(isCurrentUser)16.dp else 0.dp)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        text = displayText,
                        style = MaterialTheme.typography.bodyMedium,
                        color =
                            when {
                                msg.deletedForEveryone -> Color.Gray
                                isCurrentUser -> Color.White
                                else -> Color.Black
                            }
                        , modifier = Modifier
                            .padding(horizontal = 4.dp)
                    )

                }
            }
            Spacer(Modifier.height(4.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = formatTime(msg.time ?: 0L),
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.Gray
                )

                if (isCurrentUser && !msg.deletedForEveryone) {
                    Spacer(Modifier.width(6.dp))
                    MessageStatusTick(msg.status)
                }
            }
        }
    }
}



@Composable
fun MessageStatusTick(status: String?) {
    when (status) {
        "sent" -> {
            // single gray tick
            Image(painter = painterResource(R.drawable.chat_single_gray_tick) ,""
            , modifier = Modifier.size(16.dp))
//            Icon(
//                imageVector = Icons.Default.Done,
//                contentDescription = "Sent",
//                tint = Color.Gray,
//                modifier = Modifier.size(16.dp)
//            )
        }

        "delivered" -> {
            // double gray tick
            Image(painter = painterResource(R.drawable.chat_double_gray_tick) ,""
                , modifier = Modifier.size(16.dp))
//            Row(
//                horizontalArrangement = Arrangement.spacedBy((-6).dp)
//            ) {
//                Icon(
//                    imageVector = Icons.Default.Done,
//                    contentDescription = "Delivered",
//                    tint = Color.Gray,
//                    modifier = Modifier.size(16.dp)
//                )
//                Icon(
//                    imageVector = Icons.Default.Done,
//                    contentDescription = "Delivered",
//                    tint = Color.Gray,
//                    modifier = Modifier.size(16.dp)
//                )
//            }
        }

        "seen" -> {
            // double blue tick
            Image(painter = painterResource(R.drawable.chat_double_blue_tick) ,""
                , colorFilter = ColorFilter.tint(newBlue)
                , modifier = Modifier.size(16.dp))
//            Row(
//                horizontalArrangement = Arrangement.spacedBy((-6).dp)
//            ) {
//                Icon(
//                    imageVector = Icons.Default.Done,
//                    contentDescription = "Seen",
//                    tint = Color(0xFF2196F3),
//                    modifier = Modifier.size(16.dp)
//                )
//                Icon(
//                    imageVector = Icons.Default.Done,
//                    contentDescription = "Seen",
//                    tint = Color(0xFF2196F3),
//                    modifier = Modifier.size(16.dp)
//                )
//            }
        }

        "blocked" -> {
            // no tick for deleted messages
            Image(painter = painterResource(R.drawable.chat_single_gray_tick) ,""
                , modifier = Modifier.size(16.dp))
        }

        else -> {
            // fallback: single gray tick
            Image(painter = painterResource(R.drawable.chat_single_gray_tick) ,""
                , modifier = Modifier.size(16.dp))
//            Icon(
//                imageVector = Icons.Default.Done,
//                contentDescription = "Sent",
//                tint = Color.Gray,
//                modifier = Modifier.size(16.dp)
//            )
        }
    }
}



@Composable
fun TypingIndicatorold() {
    Row(
        modifier = Modifier.padding(start = 16.dp, top = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(3) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .background(Color(0xFF6200EE), CircleShape)
                    .padding(2.dp)
            )
            if (it < 2) Spacer(Modifier.width(4.dp))
        }
        Spacer(Modifier.width(8.dp))
        Text(
            "typing...",
            color = Color.Gray,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
fun TypingIndicator() {
    val transition = rememberInfiniteTransition(label = "typing")

    val colors = listOf(
        newBlue, // New Blue
        newGray,
        Color.LightGray
    )

    Row(
        modifier = Modifier.padding(start = 16.dp, top = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Spacer(Modifier.width(8.dp))

        Text(
            text = "Typing ",
            fontSize = constants.textUnit(14),
            color = Color.Gray,
            style = MaterialTheme.typography.bodySmall
        )

        repeat(3) { index ->
            val animatedColor by transition.animateColor(
                initialValue = colors[index % colors.size],
                targetValue = colors[(index + 1) % colors.size],
                animationSpec = infiniteRepeatable(
                    animation = tween(
                        durationMillis = 600,
                        delayMillis = index * 200,
                        easing = LinearEasing
                    ),
                    repeatMode = RepeatMode.Restart
                ),
                label = "dotColor$index"
            )

            Box(
                modifier = Modifier
                    .size(5.dp)
                    .background(animatedColor, CircleShape)
            )

            if (index < 2) Spacer(modifier = Modifier.width(3.dp))
        }
    }
}




@Composable
fun ChatInputBar(
    input: String,
    onInputChange: (String) -> Unit,
    onSend: () -> Unit,
    enabled: Boolean = true
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.Bottom
        , horizontalArrangement = Arrangement.Center
    ) {

        OutlinedTextField(
            value = input,
            onValueChange = onInputChange,
            modifier = Modifier
                .weight(1f)
                .heightIn(min = 48.dp, max = 110.dp),
            placeholder = {
                Text("Type message here ....")
            },
            singleLine = false,
            maxLines = 3,
            enabled = enabled,
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(Modifier.width(8.dp))

        Image(painter = painterResource(R.drawable.rentosend) , "",
            modifier = Modifier
                .size(if (forTab()) 62.dp else 56.dp)
                .noRippleClickable(input.isNotBlank() && enabled) {
                    onSend()
                }
        )
//        Button(
//            onClick = onSend,
//            enabled = input.isNotBlank() && enabled,
//            modifier = Modifier
//                .size(48.dp)
//                .padding(bottom = 4.dp),
//            shape = CircleShape,
//            contentPadding = PaddingValues(0.dp)
//        ) {
//            //Icon(Icons.AutoMirrored.Filled.Send, contentDescription = "Send", modifier = Modifier.size(20.dp))
//        }
    }
}



@Composable
fun DeleteMessageDialog(
    message: ChatMessage?,
    currentUserId: String,
    onDelete: (String) -> Unit,
    onDismiss: () -> Unit
) {
    if (message == null) return

    val withinFiveMinutes = System.currentTimeMillis() - (message.time ?: 0L) <= 5 * 60 * 1000
    val isSender = message.senderId == currentUserId

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center
                , horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(painter = painterResource(R.drawable.deletepopupicon) , "",
                    modifier = Modifier.size(64.dp))

                constants.spacer(8)

                CommonText("Delete Message?",
                    newBlack,
                    18,
                    1)

            }
                },

        confirmButton = {
            Column(Modifier.padding(8.dp)) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(Brush.verticalGradient(newRedGradienBg))
                        .noRippleClickable {
                            onDelete("me")
                            onDismiss()
                        }
                    , contentAlignment = Alignment.Center
                ){
                    CommonText("Delete for me",
                        Color.White,
                        14,
                        0)
                }

                constants.spacer(8)

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(Color.White)
                        .border(1.dp , Brush.linearGradient(newRedGradienBg) , RoundedCornerShape(6.dp))
                        .noRippleClickable {
                            if (isSender && withinFiveMinutes) {
                                onDelete("everyone")
                                onDismiss()
                            }
                        }
                    , contentAlignment = Alignment.Center
                ){
                    CommonText("Delete for Everyone",
                        Color.Red,
                        14,
                        0)
                }

                constants.spacer(8)

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(Color(0xffEBEBEB))
                        .noRippleClickable {
                            onDismiss()
                        }
                    , contentAlignment = Alignment.Center
                ){
                    CommonText("Cancel",
                        newBlack,
                        14,
                        0)
                }


         /*       Button(onClick = {
                    onDelete("me")
                    onDismiss()
                }) {
                    Text("Delete for Me")
                }
                if (isSender && withinFiveMinutes) {
                    Spacer(Modifier.height(8.dp))
                    Button(
                        onClick = {
                            onDelete("everyone")
                            onDismiss()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF5252))
                    ) {
                        Text("Delete for Everyone")
                    }
                }*/
            }
        },
        dismissButton = {
//            TextButton(onClick = onDismiss) {
//                Text("Cancel")
//            }
        }
        , containerColor = Color.White
    )
}




@Composable
fun ChatAppNavigation(
    onUserLoggedIn: (String) -> Unit
) {
    var currentScreen by remember { mutableStateOf("buyersList") }
    var loggedInUserId by remember { mutableStateOf("") }
    var selectedPropertyId by remember { mutableStateOf("") }
    var selectedSellerId by remember { mutableStateOf("") }

    var selectedBuyer by remember { mutableStateOf<User?>(null) }

    // ✅ Presence tracking
    LaunchedEffect(loggedInUserId) {
        if (loggedInUserId.isNotBlank()) {
            FirebasePresence.startListening(loggedInUserId)
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            if (loggedInUserId.isNotBlank()) {
                FirebasePresence.setOfflineNow(loggedInUserId)
            }
        }
    }

    when (currentScreen) {
        // ============================================================
        // LOGIN SCREEN
        // ============================================================
//        "login" -> LoginScreen { userId ->
//            onUserLoggedIn(userId)
//            loggedInUserId = userId
//            currentScreen = "enquiry"
//        }

        // ============================================================
        // ENQUIRY SCREEN (create users/property or skip)
        // ============================================================
//        "enquiry" -> EnquirySetupScreen(
//            onContinue = { propId, sellId ->
//                selectedPropertyId = propId
//                selectedSellerId = sellId
//                currentScreen = "propertySelect"
//            },
//            onSkip = {
//                // ✅ Skip creating anything and go directly to next screen
//                currentScreen = "propertySelect"
//            },
//            onLogout = {
//                FirebasePresence.setOfflineNow(loggedInUserId)
//                loggedInUserId = ""
//                currentScreen = "login"
//            }
//        )

        // ============================================================
        // PROPERTY SELECTION SCREEN
        // ============================================================
//        "propertySelect" -> PropertySetupScreen { propId, sellId ->
////            FirebaseRepository.getTotalUnreadCountForProperty(
////                propertyId = propId,
////                currentUserId = sellId
////            ) { totalUnread ->
////                Log.d("UnreadCount", "Total unread messages: $totalUnread")
////            }
//            selectedPropertyId = propId
//            selectedSellerId = sellId
//            currentScreen = "buyersList"
//
//
//        }

        // ============================================================
        // BUYERS LIST SCREEN
        // ============================================================
//        "buyersList" -> Msg_UserList(
//            propertyId = selectedPropertyId,
//            sellerId = selectedSellerId,
//            loggedInUserId = loggedInUserId,
//            onBuyerClick = { user ->
//                selectedBuyer = user
//                currentScreen = "chat"
//            },
//            onLogout = {
//                FirebasePresence.setOfflineNow(loggedInUserId)
//                loggedInUserId = ""
//                currentScreen = "login"
//            }
//        )

        // ============================================================
        // CHAT SCREEN
        // ============================================================
//        "chat" -> selectedBuyer?.let {
//            Msg_ChatScreen(
//                currentUserId = loggedInUserId,
//                otherUserId = it.userId,
//                otherUserLocation = "${it.city} , ${it.state}",
//                otherUserName = it.name,
//                otherUserProfile = it.profileImage,
//                propertyId = selectedPropertyId,
//                sellerId = selectedSellerId,
//                onBack = {
//                    currentScreen = "buyersList"
//                    selectedBuyer = null
//
//                },
//            )
//        }
    }
}

