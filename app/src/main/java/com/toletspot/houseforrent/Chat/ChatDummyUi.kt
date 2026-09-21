package com.toletspot.houseforrent.Chat


/*
// ============== UI HELPER FUNCTIONS ==============
fun formatTime(timestamp: Long): String {
    return SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date(timestamp))
}

@Composable
fun MessageStatusIcon(message: ChatMessage, currentUserId: String) {
    if (message.senderId != currentUserId) return // Only show for sent messages

    when {
        message.status == "deleted" || message.deletedForEveryone -> {
            // No status for deleted messages
        }
        message.isRead -> {
            // Double blue tick for seen
            Icon(
                imageVector = Icons.Default.Done,
                contentDescription = "Seen",
                tint = Color(0xFF2196F3),
                modifier = Modifier.size(16.dp)
            )
            Icon(
                imageVector = Icons.Default.Done,
                contentDescription = "Seen",
                tint = Color(0xFF2196F3),
                modifier = Modifier
                    .size(16.dp)
                    .offset(x = (-6).dp)
            )
        }
        message.isDelivered || message.status == "delivered" -> {
            // Double gray tick for delivered
            Icon(
                imageVector = Icons.Default.Done,
                contentDescription = "Delivered",
                tint = Color.Gray,
                modifier = Modifier.size(16.dp)
            )
            Icon(
                imageVector = Icons.Default.Done,
                contentDescription = "Delivered",
                tint = Color.Gray,
                modifier = Modifier
                    .size(16.dp)
                    .offset(x = (-6).dp)
            )
        }
        else -> {
            // Single gray tick for sent
            Icon(
                imageVector = Icons.Default.Done,
                contentDescription = "Sent",
                tint = Color.Gray,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

// ============== UI COMPOSABLES ==============
@Composable
fun LoginScreen(onLogin: (String) -> Unit) {
    var userId by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.AddCircle,
            contentDescription = "Chat App",
            modifier = Modifier
                .size(80.dp)
                .padding(bottom = 24.dp),
            tint = Color(0xFF6200EE)
        )

        Text(
            "Welcome to Chat",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            "Enter your User ID to continue",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        OutlinedTextField(
            value = userId,
            onValueChange = { userId = it },
            label = { Text("User ID") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            leadingIcon = { Icon(Icons.Default.Person, "User ID") },
            shape = RoundedCornerShape(12.dp)
        )
        Spacer(Modifier.height(24.dp))

        Button(
            onClick = { onLogin(userId) },
            enabled = userId.isNotBlank(),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Continue")
        }

        Spacer(Modifier.height(16.dp))

        Text(
            "Example IDs: 7, 8, 9, 12",
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun PropertySetupScreen(onContinue: (String, String) -> Unit) {
    var propertyId by remember { mutableStateOf("") }
    var sellerId by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.Home,
            contentDescription = "Property",
            modifier = Modifier
                .size(80.dp)
                .padding(bottom = 24.dp),
            tint = Color(0xFF6200EE)
        )

        Text(
            "Property Details",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            "Enter property and seller information",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        OutlinedTextField(
            value = propertyId,
            onValueChange = { propertyId = it },
            label = { Text("Property ID") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            leadingIcon = { Icon(Icons.Default.Home, "Property ID") },
            shape = RoundedCornerShape(12.dp)
        )
        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = sellerId,
            onValueChange = { sellerId = it },
            label = { Text("Seller ID") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            leadingIcon = { Icon(Icons.Default.Person, "Seller ID") },
            shape = RoundedCornerShape(12.dp)
        )
        Spacer(Modifier.height(32.dp))

        Button(
            onClick = { onContinue(propertyId, sellerId) },
            enabled = propertyId.isNotBlank() && sellerId.isNotBlank(),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Continue")
        }

        Spacer(Modifier.height(16.dp))

        Text(
            "Example: Property 127, 61 | Seller 7, 12",
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun EnquirySetupScreen(
    onContinue: (String, String) -> Unit,
    onSkip: () -> Unit,
    onLogout: () -> Unit
) {
    var propertyId by remember { mutableStateOf("") }
    var sellerId by remember { mutableStateOf("") }
    var sellerName by remember { mutableStateOf("") }
    var buyerId by remember { mutableStateOf("") }
    var buyerName by remember { mutableStateOf("") }
    var buyerMobile by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.Edit,
            contentDescription = "Setup Enquiry",
            modifier = Modifier
                .size(80.dp)
                .padding(bottom = 24.dp),
            tint = Color(0xFF6200EE)
        )

        Text(
            "Setup Enquiry",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            "Create a new property enquiry chat",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Property & Seller Section
        Text(
            "Property & Seller Details",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = propertyId,
            onValueChange = { propertyId = it },
            label = { Text("Property ID *") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            leadingIcon = { Icon(Icons.Default.Home, "Property ID") },
            shape = RoundedCornerShape(12.dp),
            enabled = !isLoading
        )
        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = sellerId,
            onValueChange = { sellerId = it },
            label = { Text("Seller ID *") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            leadingIcon = { Icon(Icons.Default.Person, "Seller ID") },
            shape = RoundedCornerShape(12.dp),
            enabled = !isLoading
        )
        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = sellerName,
            onValueChange = { sellerName = it },
            label = { Text("Seller Name") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            leadingIcon = { Icon(Icons.Default.Person, "Seller Name") },
            shape = RoundedCornerShape(12.dp),
            enabled = !isLoading
        )

        Spacer(Modifier.height(24.dp))

        // Buyer Section
        Text(
            "Buyer Details",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = buyerId,
            onValueChange = { buyerId = it },
            label = { Text("Buyer ID *") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            leadingIcon = { Icon(Icons.Default.Person, "Buyer ID") },
            shape = RoundedCornerShape(12.dp),
            enabled = !isLoading
        )
        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = buyerName,
            onValueChange = { buyerName = it },
            label = { Text("Buyer Name") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            leadingIcon = { Icon(Icons.Default.Person, "Buyer Name") },
            shape = RoundedCornerShape(12.dp),
            enabled = !isLoading
        )
        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = buyerMobile,
            onValueChange = { buyerMobile = it },
            label = { Text("Buyer Mobile") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            leadingIcon = { Icon(Icons.Default.Phone, "Buyer Mobile") },
            shape = RoundedCornerShape(12.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            enabled = !isLoading
        )

        if (errorMessage.isNotBlank()) {
            Spacer(Modifier.height(16.dp))
            Text(
                errorMessage,
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center
            )
        }

        Spacer(Modifier.height(32.dp))

        // Action Buttons
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(40.dp),
                color = Color(0xFF6200EE)
            )
        } else {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = onLogout,
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE53935))
                ) {
                    Icon(Icons.Default.ExitToApp, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(4.dp))
                    Text("Logout")
                }

                Button(
                    onClick = onSkip,
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
                ) {
                    Text("Skip")
                }
            }

            Spacer(Modifier.height(12.dp))

            Button(
                onClick = {
                    errorMessage = ""
                    when {
                        propertyId.isBlank() -> errorMessage = "Property ID is required"
                        sellerId.isBlank() -> errorMessage = "Seller ID is required"
                        buyerId.isBlank() -> errorMessage = "Buyer ID is required"
                        else -> {
                            isLoading = true

                            // Add users only if they don't exist
                            FirebaseHelper.addUser(
                                User(
                                    userId = sellerId,
                                    name = sellerName.ifBlank { "Seller_$sellerId" },
                                    mobileNumber = ""
                                )
                            ) { sellerAdded ->
                                FirebaseHelper.addUser(
                                    User(
                                        userId = buyerId,
                                        name = buyerName.ifBlank { "Buyer_$buyerId" },
                                        mobileNumber = buyerMobile
                                    )
                                ) { buyerAdded ->
                                    // Create chat
                                    FirebaseHelper.createPropertyChat(
                                        propertyId = propertyId,
                                        sellerId = sellerId,
                                        buyerId = buyerId,
                                        initialMessage = "Hi! I'm interested in this property.",
                                        onSuccess = {
                                            isLoading = false
                                            onContinue(propertyId, sellerId)
                                        },
                                        onFailure = { err ->
                                            isLoading = false
                                            errorMessage = "Failed to create chat: $err"
                                            Log.e("EnquirySetup", "❌ $err")
                                        }
                                    )
                                }
                            }
                        }
                    }
                },
                enabled = propertyId.isNotBlank() && sellerId.isNotBlank() && buyerId.isNotBlank(),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(Modifier.width(8.dp))
                Text("Create & Continue")
            }
        }

        Spacer(Modifier.height(16.dp))

        Text(
            "* Required fields",
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BuyersListScreen(
    propertyId: String,
    sellerId: String,
    loggedInUserId: String,
    viewModel: BuyerListViewModel = viewModel(),
    onBuyerClick: (User) -> Unit,
    onLogout: () -> Unit
) {
    val buyers by viewModel.buyers.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()




    LaunchedEffect(propertyId, sellerId, loggedInUserId) {
        viewModel.loadBuyers(propertyId, sellerId, loggedInUserId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("Chats", fontWeight = FontWeight.Bold)
                        Text(
                            "Property $propertyId",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.Gray
                        )
                    }
                },
                actions = {
                    IconButton(onClick = onLogout) {
                        Icon(Icons.Default.ExitToApp, "Logout")
                    }
                }
            )
        }
    ) { padding ->
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else if (buyers.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        Icons.Default.AddCircle,
                        contentDescription = "No Chats",
                        modifier = Modifier.size(64.dp),
                        tint = Color.LightGray
                    )
                    Spacer(Modifier.height(16.dp))
                    Text(
                        "No chats yet",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Gray
                    )
                }
            }
        } else {
            LazyColumn(contentPadding = padding) {
                items(
                    buyers,
                    key = { it.user.userId + "_" + it.user.isOnline }
                ) { userWithUnread ->
                    BuyerListItem(userWithUnread) { onBuyerClick(userWithUnread.user) }
                }
            }
        }
    }
}

@Composable
fun BuyerListItem(userWithUnread: UserWithUnread, onClick: () -> Unit) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onClick)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .background(
                            if (userWithUnread.user.isOnline) Color(0xFF4CAF50) else Color.Gray,
                            CircleShape
                        )
                )
                Spacer(Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        userWithUnread.user.name,
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 1,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        if (userWithUnread.isTyping) "typing..." else {
                            val preview = userWithUnread.lastMessage.take(40)
                            if (preview.isEmpty()) "No messages" else preview
                        },
                        style = MaterialTheme.typography.bodySmall,
                        color = if (userWithUnread.isTyping) Color(0xFF6200EE) else Color.Gray,
                        maxLines = 1
                    )
                }
            }

            Column(horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.Center) {
                if (userWithUnread.lastMessageTime != 0L) {
                    Text(
                        formatTime(userWithUnread.lastMessageTime),
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.Gray
                    )
                }
                if (userWithUnread.unreadCount > 0) {
                    Box(
                        modifier = Modifier
                            .padding(top = 4.dp)
                            .background(Color(0xFFFF5252), CircleShape)
                            .padding(horizontal = 6.dp, vertical = 2.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            if (userWithUnread.unreadCount > 99) "99+" else userWithUnread.unreadCount.toString(),
                            color = Color.White,
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
        Divider(thickness = 1.dp, modifier = Modifier.padding(horizontal = 16.dp))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    currentUserId: String,
    otherUserId: String,
    otherUserName: String,
    propertyId: String,
    sellerId: String,
    onBack: () -> Unit,
    viewModel: ChatViewModel = viewModel()
) {


    var showDropdown by remember { mutableStateOf(false) }



    val messages by viewModel.messages.collectAsState()
    val isOtherTyping by viewModel.isOtherTyping.collectAsState()

    var isUserBlocked by remember { mutableStateOf(false) }
    var userDeleted by remember { mutableStateOf(false) } // 👈 New flag
    var input by remember { mutableStateOf("") }
    var selectedMessageForDelete by remember { mutableStateOf<ChatMessage?>(null) }
    var showBlockDialog by remember { mutableStateOf(false) }

    val sortedMessages = remember(messages) {
        messages.sortedBy { it.timestamp ?: it.time ?: 0L }
    }

    val listState = rememberLazyListState()

    // ✅ Initialize chat and mark as active
    LaunchedEffect(Unit) {
        viewModel.initChat(currentUserId, otherUserId, propertyId, sellerId)
        viewModel.setChatActive(true)

        val buyerId = if (currentUserId == sellerId) otherUserId else currentUserId
        val chatId = FirebaseRepository.generateChatId(propertyId, sellerId, buyerId)

        FirebaseRepository.resetUnreadCount(chatId, currentUserId)
        FirebaseRepository.markMessagesAsSeen(chatId, currentUserId)

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

        // ✅ Listen if other user account is deleted
        FirebaseRepository.getUsersReference()
            .child(otherUserId)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val user = snapshot.getValue(User::class.java)
                    userDeleted = user == null || user.isAccountDeleted // ✅ true if user missing OR deleted
                }

                override fun onCancelled(error: DatabaseError) {}
            })

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

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(otherUserName, fontWeight = FontWeight.Bold)
                        Text(
                            "User ID: $otherUserId",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.Gray
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
                    }
                },
                actions = {
                    Box {
                        IconButton(onClick = { showDropdown = true }) {
                            Icon(Icons.Default.MoreVert, contentDescription = "Options")
                        }

                        DropdownMenu(
                            expanded = showDropdown,
                            onDismissRequest = { showDropdown = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("Clear Chat") },
                                onClick = {
                                    viewModel.clearChatForMe(currentUserId)
                                    showDropdown = false
                                }
                            )
                            DropdownMenuItem(
                                text = { Text(if (isUserBlocked) "Unblock User" else "Block User") },
                                onClick = {
                                    if (isUserBlocked) {
                                        FirebaseRepository.unblockUser(currentUserId, otherUserId)
                                        viewModel.unblockUserMessages(currentUserId)
                                    } else {
                                        FirebaseRepository.blockUser(currentUserId, otherUserId)
                                        viewModel.blockUserMessages(currentUserId)
                                    }
                                    showDropdown = false
                                }
                            )
                        }
                    }

//                    IconButton(onClick = { showBlockDialog = true }) {
//                        Icon(Icons.Default.MoreVert, "Options")
//                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // 🔴 Show banner if user is blocked
            if (isUserBlocked) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFFFEBEE))
                        .padding(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.Close,
                            contentDescription = "Blocked",
                            tint = Color.Red,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(
                            "You have blocked this user",
                            color = Color.Red,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }

            // 🔴 Show banner if user is deleted
            if (userDeleted) {
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
                        Icon(
                            Icons.Default.Info,
                            contentDescription = "User Deleted",
                            tint = Color(0xFFFF6F00),
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

            // ✅ Chat messages list
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                state = listState,
                reverseLayout = false,
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
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

                if (isOtherTyping && !isUserBlocked && !userDeleted) {
                    item { TypingIndicator() }
                }
            }

            Divider()

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
            } else {
                ChatInputBar(
                    input = input,
                    onInputChange = {
                        input = it
                        if (!isUserBlocked) {
                            viewModel.setTyping(currentUserId, it.isNotBlank())
                        }
                    },
                    onSend = {
                        if (!isUserBlocked && input.isNotBlank()) {
                            viewModel.sendMessage(input, currentUserId, otherUserId, propertyId, sellerId)
                            input = ""
                        }
                    },
                    enabled = !isUserBlocked
                )
            }
        }
    }

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
        AlertDialog(
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
        )
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
        Surface(
            modifier = Modifier
                .combinedClickable(
                    onClick = {},
                    onLongClick = onLongPress
                )
                .widthIn(max = 280.dp),
            color = when {
                msg.deletedForEveryone -> Color(0xFFF0F0F0)
                isCurrentUser -> Color(  0xffD4AF37 )
                else -> Color.White
                //Color(0xFFE3F2FD)
                //0xFFF5F5F5)
            },
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = displayText,
                    style = MaterialTheme.typography.bodyMedium,
                    color =
                        when {
                            msg.deletedForEveryone -> Color.Gray
                            isCurrentUser -> Color.White
                            else ->  Color.Black
                        }
                )
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
}

@Composable
fun MessageStatusTick(status: String?) {
    when (status) {
        "sent" -> {
            // single gray tick
            Icon(
                imageVector = Icons.Default.Done,
                contentDescription = "Sent",
                tint = Color.Gray,
                modifier = Modifier.size(16.dp)
            )
        }

        "delivered" -> {
            // double gray tick
            Row(
                horizontalArrangement = Arrangement.spacedBy((-6).dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Done,
                    contentDescription = "Delivered",
                    tint = Color.Gray,
                    modifier = Modifier.size(16.dp)
                )
                Icon(
                    imageVector = Icons.Default.Done,
                    contentDescription = "Delivered",
                    tint = Color.Gray,
                    modifier = Modifier.size(16.dp)
                )
            }
        }

        "seen" -> {
            // double blue tick
            Row(
                horizontalArrangement = Arrangement.spacedBy((-6).dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Done,
                    contentDescription = "Seen",
                    tint = Color(0xFF2196F3),
                    modifier = Modifier.size(16.dp)
                )
                Icon(
                    imageVector = Icons.Default.Done,
                    contentDescription = "Seen",
                    tint = Color(0xFF2196F3),
                    modifier = Modifier.size(16.dp)
                )
            }
        }

        "deleted" -> {
            // no tick for deleted messages
        }

        else -> {
            // fallback: single gray tick
            Icon(
                imageVector = Icons.Default.Done,
                contentDescription = "Sent",
                tint = Color.Gray,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}


@Composable
fun TypingIndicator() {
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
    ) {
        OutlinedTextField(
            value = input,
            onValueChange = onInputChange,
            label = { Text("Message") },
            modifier = Modifier
                .weight(1f)
                .heightIn(min = 48.dp, max = 120.dp),
            singleLine = false,
            maxLines = 3,
            enabled = enabled,
            shape = RoundedCornerShape(24.dp)
        )
        Spacer(Modifier.width(8.dp))

        Button(
            onClick = onSend,
            enabled = input.isNotBlank() && enabled,
            modifier = Modifier
                .size(48.dp)
                .padding(bottom = 4.dp),
            shape = CircleShape,
            contentPadding = PaddingValues(0.dp)
        ) {
            Icon(Icons.AutoMirrored.Filled.Send, contentDescription = "Send", modifier = Modifier.size(20.dp))
        }
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
        title = { Text("Delete message?") },
        text = { Text(message.message.take(100)) },
        confirmButton = {
            Column(Modifier.padding(8.dp)) {
                Button(onClick = {
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
                }
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}


@Composable
fun ChatAppNavigation(
    onUserLoggedIn: (String) -> Unit
) {
    var currentScreen by remember { mutableStateOf("login") }
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
        "login" -> LoginScreen { userId ->
            onUserLoggedIn(userId)
            loggedInUserId = userId
            currentScreen = "enquiry"
        }

        // ============================================================
        // ENQUIRY SCREEN (create users/property or skip)
        // ============================================================
        "enquiry" -> EnquirySetupScreen(
            onContinue = { propId, sellId ->
                selectedPropertyId = propId
                selectedSellerId = sellId
                currentScreen = "propertySelect"
            },
            onSkip = {
                // ✅ Skip creating anything and go directly to next screen
                currentScreen = "propertySelect"
            },
            onLogout = {
                FirebasePresence.setOfflineNow(loggedInUserId)
                loggedInUserId = ""
                currentScreen = "login"
            }
        )

        // ============================================================
        // PROPERTY SELECTION SCREEN
        // ============================================================
        "propertySelect" -> PropertySetupScreen { propId, sellId ->
//            FirebaseRepository.getTotalUnreadCountForProperty(
//                propertyId = propId,
//                currentUserId = sellId
//            ) { totalUnread ->
//                Log.d("UnreadCount", "Total unread messages: $totalUnread")
//            }
            selectedPropertyId = propId
            selectedSellerId = sellId
            currentScreen = "buyersList"


        }

        // ============================================================
        // BUYERS LIST SCREEN
        // ============================================================
        "buyersList" -> BuyersListScreen(
            propertyId = selectedPropertyId,
            sellerId = selectedSellerId,
            loggedInUserId = loggedInUserId,
            onBuyerClick = { user ->
                selectedBuyer = user
                currentScreen = "chat"
            },
            onLogout = {
                FirebasePresence.setOfflineNow(loggedInUserId)
                loggedInUserId = ""
                currentScreen = "login"
            }
        )

        // ============================================================
        // CHAT SCREEN
        // ============================================================
        "chat" -> selectedBuyer?.let {
            ChatScreen(
                currentUserId = loggedInUserId,
                otherUserId = it.userId,
                otherUserName = it.name,
                propertyId = selectedPropertyId,
                sellerId = selectedSellerId,
                onBack = {
                    currentScreen = "buyersList"
                    selectedBuyer = null

                }
            )
        }
    }
}
*/
