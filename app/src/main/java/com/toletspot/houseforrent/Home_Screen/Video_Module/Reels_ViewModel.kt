package com.toletspot.houseforrent.Home_Screen.Video_Module

import androidx.lifecycle.ViewModel
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.Get_Main_Comments_Data
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.Get_Reels_Data
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.Get_Reels_Property_Data
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.Get_Reply_Comments_Data
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.LastReply
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.NewDraftFlow.New_Draft_Flow_Data
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.Put_Comment_Reply_Data
import com.toletspot.houseforrent.Home_Screen.PostProperty_Module.RentoDataclass.RentoMediaDC.Image
import com.toletspot.houseforrent.Home_Screen.PostProperty_Module.RentoDataclass.RentoMediaDC.Video
import com.toletspot.houseforrent.R
import com.toletspot.houseforrent.UI_DataClass.Reels_Btm_Sheet_Options
import com.toletspot.houseforrent.UI_DataClass.Reels_Options
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.Instant
import java.time.format.DateTimeParseException

import java.time.temporal.ChronoUnit


class Reels_ViewModel : ViewModel(){

    fun clearAllData_RVM() {
        // Clear all reels/videos
        _videos.value = emptyList()

        // Reset like sense state
        _sense_isLiked.value = false

        // Clear all comments
        _main_Comments.value = emptyList()
        _reply_CommentsMap.value = emptyMap()

        // Reset comment edit state
        _edit_Comment_State.value = false
        _edit_Clicked_Comment_Id.value = 0

        // Reset API trigger state
        _what_Api.value = true

        // Clear new comment data
        _new_Comment.value = null

        // Clear view property details
        _viewProperty_Details.value = null

        // Dismiss send enquiry bottom sheet
        _send_Enquiry_Btm_Sheet.value = false

        println("🧹 Reels_ViewModel cleared successfully")
    }

    private val _videos = MutableStateFlow<List<Get_Reels_Data>>(emptyList())
    val videos: StateFlow<List<Get_Reels_Data>> = _videos.asStateFlow()

    fun get_Reels_Data(): Boolean {
        return if (_videos.value.isEmpty()) false else true
    }

    fun updateMediaReelsSFByPostId(
        postId: Int,
        newImages: List<Image>?,
        newVideos: List<Video>?
    ) {
        _videos.value = _videos.value.map { post ->
            if (post.user_post_id == postId) {
                post.copy(
                    post_property = post.post_property.copy(
                        images = newImages ?: emptyList(),
                        video = newVideos ?: emptyList()
                    )
                )
            } else post
        }
    }


    fun setReelsContent(newReels: List<Get_Reels_Data>) {
        newReels.forEachIndexed { i, item ->
            //println("Index=$i, ID=${item.land_type_id}, Name=${item.name}, Selected=${item.is_Selected}")
        }
        _videos.value = newReels
        //` updateSelectedIds()
    }

    fun updateVideoByPostId(
        postId: Int,
        update: (Get_Reels_Data) -> Get_Reels_Data
    ) {
        _videos.update { current ->
            current.map { video ->
                if (video.user_post_id == postId) {
                    update(video) // apply transformation to the matching video
                } else {
                    video // keep others as is
                }
            }
        }
    }

    fun deleteVideoById_Profile_Post_Reels(postId: Int) {
        _videos.value = _videos.value.filter { it.user_post_id != postId }
    }


    /// comment dropdown bloack

    private var _comment_Btm_Close = MutableStateFlow(false)
    var comment_Btm_Close = _comment_Btm_Close.asStateFlow()

    fun disable_Close_CommentBtm(){
        _comment_Btm_Close.value = false
    }

    fun enable_Close_CommentBtm(){
        _comment_Btm_Close.value = true
    }




    fun clear_All_Reels() {
        _videos.value = emptyList()
    }

    fun toggleLike_Reels(reelId: Int) {
        _videos.update { currentList ->
            currentList.map { reel ->
                if (reel.user_post_id == reelId) {
                    // toggle between 1 and 0
                    val newLikeStatus = if (reel.is_liked == 1) 0 else 1
                    reel.copy(is_liked = newLikeStatus)
                } else reel
            }
        }
    }

    fun setNewTimeStampOnRenew(reelId: Int) {
        val newTimestamp = Instant.now().toString() // 2025-01-01T10:00:00Z

        _videos.update { currentList ->
            currentList.map { reel ->
                if (reel.user_post_id == reelId) {
                    reel.copy(
                        post_property = reel.post_property.copy(
                            created_at = newTimestamp
                        )
                    )
                } else {
                    reel
                }
            }
        }
    }

    fun toggleRequestPhotos(reelId: Int) {
        _videos.update { currentList ->
            currentList.map { reel ->
                if (reel.user_post_id == reelId) {
                    // toggle between 1 and 0
                    val newLikeStatus = if (reel.post_interest == 1) 0 else 1
                    reel.copy(post_interest = newLikeStatus)
                } else reel
            }
        }
    }

    fun toggleLike_Reelsrento(reelId: Int) {
        _videos.update { currentList ->
            currentList.map { reel ->
                if (reel.user_post_id == reelId) {

                    val wasLiked = reel.is_liked == 1
                    val newLikeStatus = if (wasLiked) 0 else 1

                    reel.copy(
                        is_liked = newLikeStatus,
                        total_likes = when {
                            wasLiked && reel.total_likes > 0 ->
                                reel.total_likes - 1

                            !wasLiked ->
                                reel.total_likes + 1

                            else -> reel.total_likes
                        }
                    )
                } else reel
            }
        }
    }

    fun increaseLikeCount_Reels(reelId: Int) {
        _videos.update { currentList ->
            currentList.map { reel ->
                var likeSize = reel.total_likes
                if (reel.user_post_id == reelId) {
                    if (likeSize <= likeSize + 1 && reel.is_liked == 0) {
                        reel.copy(
                            total_likes = reel.total_likes + 1,
                            //is_liked = 1 // optional: mark it as liked
                        )
                    } else reel
                } else reel
            }
        }
    }

    fun decreaseLikeCount_Reels(reelId: Int) {
        _videos.update { currentList ->
            currentList.map { reel ->
                var likeSize = reel.total_likes
                if (reel.user_post_id == reelId) {
                    if (likeSize <= likeSize + 1 ) {
                        reel.copy(
                            total_likes = reel.total_likes - 1 ,
                            //is_liked = 1 // optional: mark it as liked
                        )
                    } else reel
                } else reel
            }
        }
    }


    // Even better - with logging for debugging:
    fun toggle_is_Enquired(postId: Int) {
        _videos.update { currentList ->
            currentList.map { reel ->
                if (reel.user_post_id == postId) {
                    println("✅ Updating enquiry for post: $postId")
                    reel.copy(enquiry = 1)
                } else {
                    reel
                }
            }
        }
    }
    fun toggleLike_Report(reelId: Int) {
        _videos.update { currentList ->
            currentList.map { reel ->
                if (reel.user_post_id == reelId) {
                    val newReportStatus = if (reel.post_property.is_report == 1) 0 else 1
                    reel.copy(
                        post_property = reel.post_property.copy(is_report = newReportStatus)
                    )
                } else reel
            }
        }
    }



    fun toggleSave_Reels(reelId: Int) {
        _videos.update { currentList ->
            currentList.map { reel ->
                if (reel.user_post_id == reelId) {
                    // toggle between 1 and 0
                    val newLikeStatus = if (reel.is_saved == 1) 0 else 1
                    reel.copy(is_saved = newLikeStatus)
                } else reel
            }
        }
    }


    fun increaseCommentCount_Reels(reelId: Int) {
        println("DATA UPDATEDDDDDDD -- ${_videos.value}")
        _videos.update { list ->
            list.map { reel ->
                if (reel.user_post_id == reelId) {
                    reel.copy(total_comments = (reel.total_comments ?: 0) + 1)
                } else reel
            }
        }
    }

    fun decreaseCommentCount_Reels(reelId: Int) {
        _videos.update { list ->
            list.map { reel ->
                if (reel.user_post_id == reelId) {
                    reel.copy(
                        total_comments = (reel.total_comments - 1).coerceAtLeast(0)
                    )
                } else reel
            }
        }
    }


    fun decreaseCommentCount_Reels_More(reelId: Int, count: Int) {
        _videos.update { list ->
            list.map { reel ->
                if (reel.user_post_id == reelId) {
                    reel.copy(
                        total_comments = (reel.total_comments - count).coerceAtLeast(0)
                    )
                } else reel
            }
        }
        println("")
    }



    private var _sense_isLiked = MutableStateFlow<Boolean>(false)
    var sense_isLiked : StateFlow<Boolean> = _sense_isLiked.asStateFlow()

    fun add_Sense_isLiked (sensed : Boolean){
        _sense_isLiked.update { sensed }
    }


    var reels_Options_List = mutableListOf(
        Reels_Options(
            icon = R.drawable.likereelsrento,
            counts = "10k",
            enabled_Icon = R.drawable.likefillrento
        ),
        Reels_Options(
            icon = R.drawable.commentreelsrento,
            counts = "12k"
        ),
        Reels_Options(
            icon = R.drawable.savereelsrento,
            enabled_Icon = R.drawable.saveedrento
        ),
        Reels_Options(
            icon = R.drawable.morereelsrento
        ),
    )


    var ownIdOptions = listOf(0 ,7, 5 ,6, 8)
    var otherIdOptions = listOf(0,1,2,7,3,8)
    var pviewotherIdOptions = listOf(0,1,2,7,3,6,8)
    var soldoutIdOptions = listOf(0, 2, 4,5,6 , 8)
    var expiredIdOptions = listOf(6, 4 ,5 , 7 ,1,2, 8)
    var renewpostOptions = listOf(0, 5, 6, 7)

    private val _reelsBTMSOptions = MutableStateFlow(
        listOf(
            Reels_Btm_Sheet_Options(
                id = 0,
                icon = R.drawable.repostbtmrento,
                title = "Repost Property"
            ),
            Reels_Btm_Sheet_Options(
                id = 1,
                icon = R.drawable.editpropbtmrento,
                title = "Edit Property"
            ),
            Reels_Btm_Sheet_Options(
                id = 2,
                icon = R.drawable.markassoldbtmrento,
                title = "Mark as Rentedout"
            ),
            Reels_Btm_Sheet_Options(
                id = 3,
                icon = R.drawable.deletebtmrento,
                title = "Delete Property"
            ),
            Reels_Btm_Sheet_Options(
                id = 4,
                icon = R.drawable.sharebtmrento,
                title = "Share",
            ),
            Reels_Btm_Sheet_Options(
                id = 5,
                icon = R.drawable.reportbtmrento,
                title = "Report",
            ),
            Reels_Btm_Sheet_Options(
                id = 6,
                icon = R.drawable.notinterestbtmrento,
                title = "Not Interested",
            ),
            Reels_Btm_Sheet_Options(
                id = 7,
                icon = R.drawable.reelsoptionsactivate,
                title = "Activate",
            ),
            Reels_Btm_Sheet_Options(
                id = 8,
                icon = R.drawable.renewpostrento,
                title = "Renew",
            ),
        )
    )


    val defaultOptionsList = listOf(
        Reels_Btm_Sheet_Options(
            id = 0,
            icon = R.drawable.repostbtmrento,
            title = "Repost Property"
        ),
        Reels_Btm_Sheet_Options(
            id = 1,
            icon = R.drawable.editpropbtmrento,
            title = "Edit Property"
        ),
        Reels_Btm_Sheet_Options(
            id = 2,
            icon = R.drawable.markassoldbtmrento,
            title = "Mark as Rentedout"
        ),
        Reels_Btm_Sheet_Options(
            id = 3,
            icon = R.drawable.deletebtmrento,
            title = "Delete Property"
        ),
        Reels_Btm_Sheet_Options(
            id = 4,
            icon = R.drawable.sharebtmrento,
            title = "Share",
        ),
        Reels_Btm_Sheet_Options(
            id = 5,
            icon = R.drawable.reportbtmrento,
            title = "Report",
        ),
        Reels_Btm_Sheet_Options(
            id = 6,
            icon = R.drawable.notinterestbtmrento,
            title = "Not Interested",
        ),
        Reels_Btm_Sheet_Options(
            id = 7,
            icon = R.drawable.reelsoptionsactivate,
            title = "Activate",
        ),
    )

    val reelsBTMSOptions: StateFlow<List<Reels_Btm_Sheet_Options>> = _reelsBTMSOptions.asStateFlow()

    fun removeReelsBTMSOptions(removalIds: List<Int>) {
        _reelsBTMSOptions.value =
            _reelsBTMSOptions.value.filterNot { it.id in removalIds }
    }



    fun resetReelsBTMSOptions() {
        _reelsBTMSOptions.value = defaultOptionsList // original full list
    }





    /// main comments reels
    private val _main_Comments = MutableStateFlow<List<Get_Main_Comments_Data>>(emptyList())
    val main_Comments: StateFlow<List<Get_Main_Comments_Data>> = _main_Comments.asStateFlow()

    fun set_MComments_Content(newComments: List<Get_Main_Comments_Data>) {
        _main_Comments.value = newComments
    }

    fun clear_MCommentList(){
        _main_Comments.value = emptyList()
    }

    fun MCommentsReport(id: Int) {
        _main_Comments.update { list ->
            list.map { comment ->
                if (comment.comment_id == id) {
                    comment.copy(is_report = 1)
                } else {
                    comment
                }
            }
        }
    }



//    fun set_MComments_Content(newcomments: List<Get_Main_Comments_Data>) {
//        newcomments.forEachIndexed { i, item ->
//            //println("Index=$i, ID=${item.land_type_id}, Name=${item.name}, Selected=${item.is_Selected}")
//        }
//        _main_Comments.value = newcomments
//        //` updateSelectedIds()
//    }

    fun add_MComments(newComments: List<Get_Main_Comments_Data>) {
        _main_Comments.update { current ->
            current + newComments.filter { new ->
                current.none { it.comment_id == new.comment_id }
            }
        }
    }

    fun addReplyToMainComment(parentId: Int, newReply: LastReply) {
        // update last_reply in main comment
        _main_Comments.update { current ->
            current.map { mainComment ->
                if (mainComment.comment_id == parentId) {
                    mainComment.copy(   last_reply = mainComment.last_reply + listOf(newReply)
                        //last_reply = listOf(newReply)
                    )
                } else mainComment
            }
        }

        // also update replyCommentsMap
        _reply_CommentsMap.update { current ->
            val existingReplies = current[parentId] ?: emptyList()
            current + (parentId to ( existingReplies + listOf(Get_Reply_Comments_Data(
                    author = newReply.author ?: 0,
                    comment = newReply.comment ?: "",
                    comment_id = newReply.comment_id ?: 0,
                    created_at = newReply.created_at ?: "",
                    is_liked = newReply.is_liked ?: 0,
                    parent_comment_id = newReply.parent_comment_id ?: 0,
                    like_count = newReply.like_count ?: 0,
                    profile_image = newReply.profile_image ?: "",
                    user_id = newReply.user_id ?: 0,
                    username = newReply.username ?: "",
                is_report = newReply.is_report ?: 0,
                mention_username = newReply.mention_username ?: ""
            )
            ) ))
        }
    }



   /* fun toggleLike_MComments(cmtId: Int) {
        // 1️⃣ Update top-level comments
        _main_Comments.update { currentList ->
            currentList.map { comment ->
                // Toggle parent if matches
                val updatedComment = if (comment.comment_id == cmtId) {
                    comment.copy(is_liked = if (comment.is_liked == 1) 0 else 1)
                } else comment

                // Toggle replies inside last_reply
                val updatedReplies = updatedComment.last_reply.map { reply ->
                    if (reply.comment_id == cmtId) {
                        reply.copy(is_liked = if (reply.is_liked == 1) 0 else 1)
                    } else reply
                }

                updatedComment.copy(last_reply = updatedReplies)
            }
        }

        // 2️⃣ Update replies inside _reply_CommentsMap
        _reply_CommentsMap.update { currentMap ->
            currentMap.mapValues { entry ->
                entry.value.map { reply ->
                    if (reply.comment_id == cmtId) {
                        reply.copy(is_liked = if (reply.is_liked == 1) 0 else 1)
                    } else reply
                }
            }
        }
    }*/

    fun toggleLike_MComments(cmtId: Int) {
        // 1️⃣ Update top-level comments
        _main_Comments.update { currentList ->
            currentList.map { comment ->
                // If parent comment matches
                val updatedComment = if (comment.comment_id == cmtId) {
                    val newLiked = if (comment.is_liked == 1) 0 else 1
                    val newCount = comment.like_count + if (newLiked == 1) 1 else -1
                    comment.copy(
                        is_liked = newLiked,
                        like_count = newCount.coerceAtLeast(0) // avoid negative
                    )
                } else comment

                println("LIKE COUNT INCREASEE COMMENT-- ${comment.like_count}")

                // Update replies inside last_reply
                val updatedReplies = updatedComment.last_reply.map { reply ->
                    if (reply.comment_id == cmtId) {
                        val newLiked = if (reply.is_liked == 1) 0 else 1
                        val newCount = ((reply.like_count?:0) + if (newLiked == 1) 1 else -1)
                            .coerceAtLeast(0)
                        reply.copy(
                            is_liked = newLiked,
                            like_count = newCount
                        )
                    } else reply
                }

                println("LIKE COUNT INCREASEE Reply-- ${updatedComment.last_reply.map { it.like_count }}")
                updatedComment.copy(last_reply = updatedReplies)
            }
        }

        // 2️⃣ Update replies inside _reply_CommentsMap
        _reply_CommentsMap.update { currentMap ->
            currentMap.mapValues { entry ->
                entry.value.map { reply ->
                    if (reply.comment_id == cmtId) {
                        val newLiked = if (reply.is_liked == 1) 0 else 1
                        val newCount = (reply.like_count + if (newLiked == 1) 1 else -1)
                            .coerceAtLeast(0)
                        reply.copy(
                            is_liked = newLiked,
                            like_count = newCount
                        )
                    } else reply

                }
            }
        }
    }




    fun editCommentById(cmtId: Int, newText: String) {
        // Update top-level comments
        _main_Comments.update { currentList ->
            currentList.map { comment ->
                val updatedComment = if (comment.comment_id == cmtId) {
                    comment.copy(comment = newText)
                } else comment

                // Update inside replies
                val updatedReplies = updatedComment.last_reply.map { reply ->
                    if (reply.comment_id == cmtId) {
                        reply.copy(comment = newText)
                    } else reply
                }

                updatedComment.copy(last_reply = updatedReplies)
            }
        }

        // Update replies inside reply map
        _reply_CommentsMap.update { currentMap ->
            currentMap.mapValues { entry ->
                entry.value.map { reply ->
                    if (reply.comment_id == cmtId) {
                        reply.copy(comment = newText)
                    } else reply
                }
            }
        }
    }

    fun deleteCommentById(cmtId: Int) {
        // Remove from top-level comments
        _main_Comments.update { currentList ->
            currentList
                .filter { it.comment_id != cmtId } // remove parent if matches
                .map { comment ->
                    val updatedReplies = comment.last_reply.filter { it.comment_id != cmtId }
                    comment.copy(last_reply = updatedReplies)
                }
        }

        // Remove from reply map completely if parent or reply deleted
        _reply_CommentsMap.update { currentMap ->
            currentMap
                .filterKeys { key -> key != cmtId } // drop parent entry
                .mapValues { entry ->
                    entry.value.filter { reply -> reply.comment_id != cmtId }
                }
        }
    }




    private var _edit_Comment_State = MutableStateFlow(false)
    var edit_Comment_State :StateFlow<Boolean> = _edit_Comment_State.asStateFlow()


    fun getCommentTextById(cmtId: Int): String? {
        // 1️⃣ Search in top-level comments
        _main_Comments.value.forEach { comment ->
            if (comment.comment_id == cmtId) return comment.comment

            // Search inside replies
            comment.last_reply.forEach { reply ->
                if (reply.comment_id == cmtId) return reply.comment
            }
        }

        // 2️⃣ Search in reply map
        _reply_CommentsMap.value.forEach { (_, replies) ->
            replies.forEach { reply ->
                if (reply.comment_id == cmtId) return reply.comment
            }
        }

        return null // not found
    }




    fun edit_Comment_Enable(){
        _edit_Comment_State.value = true
    }
    fun edit_Comment_Disable(){
        _edit_Comment_State.value = false
    }

    fun get_Edit_Comment_State(): Boolean {
        return _edit_Comment_State.value
    }

    /// edit tapped comment id
    private var _edit_Clicked_Comment_Id = MutableStateFlow<Int>(0)
    var edit_Clicked_Comment_Id : StateFlow<Int> = _edit_Clicked_Comment_Id.asStateFlow()


    fun add_Edit_Clicked_Comment_Id(id :Int){
        _edit_Clicked_Comment_Id.update { id }
    }

    fun get_Edit_Clicked_Comment_Id():Int {
        return _edit_Clicked_Comment_Id.value
    }

    ///// main or reply comment api trigger state
    private var _what_Api = MutableStateFlow<Boolean>(true)
    var what_Api : StateFlow<Boolean> = _what_Api.asStateFlow()


    fun enable_what_api(){
        _what_Api.value = true
    }

    fun disable_what_api(){
        _what_Api.value = false
    }

    fun get_what_api() :Boolean{
        return _what_Api.value
    }




    // reply cmts reels

    // ReelsViewModel
//    private val _reply_CommentsMap = MutableStateFlow<Map<Int, List<Get_Reply_Comments_Data>>>(emptyMap())
//    val reply_CommentsMap: StateFlow<Map<Int, List<Get_Reply_Comments_Data>>> = _reply_CommentsMap
//
//    fun set_RComments_Content(commentId: Int, newReplies: List<Get_Reply_Comments_Data>, append: Boolean) {
//        _reply_CommentsMap.update { current ->
//            val existing = current[commentId] ?: emptyList()
//            val updated = if (append) {
//                existing + newReplies.filter { new -> existing.none { it.commentId == new.commentId } }
//            } else {
//                newReplies
//            }
//            current + (commentId to updated)
//        }
//    }

    private val _reply_CommentsMap =
        MutableStateFlow<Map<Int, List<Get_Reply_Comments_Data>>>(emptyMap())
    val reply_CommentsMap: StateFlow<Map<Int, List<Get_Reply_Comments_Data>>> = _reply_CommentsMap

    fun set_RComments_Content(
        parentId: Int,
        newReplies: List<Get_Reply_Comments_Data>,
        append: Boolean
    ) {
        _reply_CommentsMap.update { currentMap ->
            val existingReplies = currentMap[parentId] ?: emptyList()
            val updated = if (append) existingReplies + newReplies else newReplies
            currentMap + (parentId to updated)
        }
    }

    fun clear_Reply_Map(){
        _reply_CommentsMap.value = emptyMap()
    }

    fun RCommentsReport(parentId: Int, replyId: Int) {
        _reply_CommentsMap.update { currentMap ->
            val replies = currentMap[parentId] ?: return@update currentMap

            val updatedReplies = replies.map { reply ->
                if (reply.comment_id == replyId) {
                    reply.copy(is_report = 1)
                } else {
                    reply
                }
            }

            currentMap + (parentId to updatedReplies)
        }
    }



//    fun set_RComments_Content(parentId: Int, newComments: List<Get_Reply_Comments_Data>) {
//        _reply_CommentsMap.update { currentMap ->
//            val existingReplies = currentMap[parentId] ?: emptyList()
//            currentMap + (parentId to (existingReplies + newComments))
//        }
//    }




    /// put comment var

    private var _new_Comment = MutableStateFlow<Put_Comment_Reply_Data?>(null)
    var new_Comments : StateFlow<Put_Comment_Reply_Data?> = _new_Comment.asStateFlow()
//    var new_Comment = mutableStateListOf<Put_Comment_Reply_Data>()

    fun add_new_Comment(data: Put_Comment_Reply_Data){
        _new_Comment.value =   data
    }

    fun get_new_Comment(): Put_Comment_Reply_Data? {
        return _new_Comment.value
    }



    /// reels view property details data

    private var _viewProperty_Details = MutableStateFlow<Get_Reels_Data?>(null)
    var viewProperty_Details : StateFlow<Get_Reels_Data?> = _viewProperty_Details.asStateFlow()

    fun add_View_Property_Details(data : Get_Reels_Data){
        _viewProperty_Details.update { data }
    }
    fun update_View_Property_Detail(update: (Get_Reels_Data) -> Get_Reels_Data) {
        _viewProperty_Details.value?.let {
            _viewProperty_Details.value = update(it)
        }
    }

    fun toggle_ReportViewDetails(reelId: Int) {
        _viewProperty_Details.update { reel ->
                if (reel?.user_post_id == reelId) {
                    val newReportStatus = if (reel.post_property.is_report == 1) 0 else 1
                    reel.copy(
                        post_property = reel.post_property.copy(is_report = newReportStatus)
                    )
                } else reel
            }
    }


    fun get_View_Property_Details(): Get_Reels_Data? {
       return _viewProperty_Details.value
    }

    fun clear_view_pro_Details(){
        _viewProperty_Details.value = null
    }




    // send enquiry bottom sheet var
    private var _send_Enquiry_Btm_Sheet = MutableStateFlow<Boolean>(false)
    var send_Enquiry_Btm_Sheet : StateFlow<Boolean> = _send_Enquiry_Btm_Sheet.asStateFlow()

    fun enable_Send_Eq_Btm_Sheet(){
        _send_Enquiry_Btm_Sheet.value = true
    }

    fun dismiss_Send_Eq_Btm_Sheet(){
        _send_Enquiry_Btm_Sheet.value = false
    }

}

/*
fun New_Draft_Flow_Data.toGetReelsData_FDfs2(): Get_Reels_Data {
    return Get_Reels_Data(
        cities = this.city,
        country = this.country,
        latitude = this.latitude.toDoubleOrNull() ?: 0.0,
        longitude = this.longitude.toDoubleOrNull() ?: 0.0,
        name = this.propertyName,
        phone_num = "",
        phone_num_cc = "",
        whatsapp_num_cc = "",
        whatsapp_num = "",
        email = "",
        post_property = this.toGetReelsPropertyData(),
        profile_image = "",
        state = this.state,
        user_id = this.uID,
        user_post_id = this.userPostId,
        land_type_id = this.landTypeId,
        username = "",
        is_liked = 0,
        is_saved = 0,
        video = this.video,
        thumbnail = this.thumbnail,
        total_likes = 0,
        total_comments = 0,
        enquiry = 0
    )
}

fun New_Draft_Flow_Data.toGetReelsPropertyData2(): Get_Reels_Property_Data {
    return Get_Reels_Property_Data(
        address = this.address,
        amenities = this.amenities,
        area_length = this.areaLength,
        area_width = this.areaWidth,
        availability_status = this.availabilityStatus,
        bhk_type = this.bhkType,
        boundary_wall = this.boundaryWall,
        built_up_area = this.builtUpArea,
        carpet_area = this.carpetArea,
        central_ac = this.centralAc,
        city = this.city,
        conference_room = this.conferenceRoom,
        country = this.country,
        created_at = "",
        facade_height = this.facadeHeight,
        facade_width = this.facadeWidth,
        fire_safety_measures = this.fireSafetyMeasures,
        furnishing_status = this.furnishingStatus,
        image_urls = this.images,
        land_categorie_id = this.landCategorieId,
        land_type_id = this.landTypeId,
        latitude = this.latitude,
        lifts = this.lifts,
        which_local_authority = this.whichLocalAuthority,
        does_local_authority = this.doesLocalAuthority,
        locality = this.locality,
        longitude = this.longitude,
        max_of_seats = this.maxOfSeats,
        min_of_seats = this.minOfSeats,
        no_of_balconies = this.noOfBalconies,
        no_of_bathrooms = this.noOfBathrooms,
        no_of_bedrooms = this.noOfBedrooms,
        no_of_cabins = this.noOfCabins,
        no_of_meeting_rooms = this.noOfMeetingRooms,
        no_of_open_sides = this.noOfOpenSides,
        no_of_staircases = this.noOfStaircases,
        noc_certified = this.nocCertified,
        occupancy_certificate = this.occupancyCertificate,
        office_previously_used_for = this.officePreviouslyUsedFor,
        other_rooms = this.otherRooms,
        oxygen_duct = this.oxygenDuct,
        pantry = this.pantry,
        pantry_size = this.pantrySize,
        parking_available = this.parkingAvailable,
        is_it_pre_leased_pre_rented = this.isItPreLeasedPreRented,
        price = this.price,
        property_area = this.propertyArea,
        property_facing = this.propertyFacing,
        property_floor_no = this.propertyFloorNo,
        property_highlights = this.propertyHighlights,
        property_name = this.propertyName,
        property_ownership = this.propertyOwnership,
        reception_area = this.receptionArea,
        state = this.state,
        suitable_business_type = this.suitableBusinessType,
        super_built_up_area = this.superBuiltUpArea,
        thumbnail = this.thumbnail,
        total_floor = this.totalFloor,
        ups = this.ups,
        user_post_id = this.userPostId,
        is_report = 0,
        landCategoryText = this.landCategoryText,
        landTypeText = this.landTypeText,
        user_type = this.userType,
        video = this.video,
        washroom_details = this.washroomDetails,
        is_sold = 0
    )
}
*/


fun New_Draft_Flow_Data.toGetReelsData_FDfs(): Get_Reels_Data {
    return Get_Reels_Data(
        cities = this.city.orEmpty(),
        country = this.country.orEmpty(),


        name = this.property_name.orEmpty(),
        phone_num = "",
        phone_num_cc = "",
        whatsapp_num_cc = "",
        whatsapp_num = "",
        email = "",
        post_property = this.toGetReelsPropertyData(),
        profile_image = "",
        state = this.state.orEmpty(),
        user_id = this.user_id ?: 0,
        user_post_id = this.user_post_id ?: 0,
        username = "",
        is_liked = 0,
        is_saved = 0,
        thumbnail = this.thumbnail.orEmpty(),
        total_likes = 0,
        total_comments = 0,
        enquiry = 0,
        post_interest = 0
    )
}

fun New_Draft_Flow_Data.toGetReelsPropertyData(): Get_Reels_Property_Data {
    return Get_Reels_Property_Data(
        address = this.address ?: "",
        amenities = this.amenities ?: "",
        area_length = this.area_length.orEmpty(),
        area_width = this.area_width ?: "",
        //availability_status = this.availability_status ?: "",
        bhk_type = this.bhk_type.orEmpty(),
        boundary_wall = this.boundary_wall.orEmpty(),
        built_up_area = this.built_up_area.orEmpty(),
        carpet_area = this.carpet_area.orEmpty(),
        central_ac = this.central_ac.orEmpty(),
        city = this.city.orEmpty(),
        conference_room = this.conference_room.orEmpty(),
        country = this.country.orEmpty(),
        created_at = "",
        facade_height = this.facade_height.orEmpty(),
        facade_width = this.facade_width.orEmpty(),
        fire_safety_measures = this.fire_safety_measures.orEmpty(),
        furnishing_status = this.furnishing_status.orEmpty(),
        land_categorie_id = this.land_categorie_id ?: 0,
        land_type_id = this.land_type_id ?: 0,
        latitude = this.latitude.orEmpty(),
        lifts = this.lifts.orEmpty(),
        //which_local_authority = this.which_local_authority.orEmpty(),
        does_local_authority = this.does_local_authority.orEmpty(),
        locality = this.locality.orEmpty(),
        longitude = this.longitude.orEmpty(),
        max_of_seats = this.max_of_seats.orEmpty(),
        min_of_seats = this.min_of_seats.orEmpty(),
        no_of_Balconies = this.no_of_balconies.orEmpty(),
        no_of_Bathrooms = this.no_of_bathrooms.orEmpty(),
        no_of_bedrooms = this.no_of_bedrooms.orEmpty(),
        no_of_cabins = this.no_of_cabins.orEmpty(),
        no_of_meeting_rooms = this.no_of_meeting_rooms.orEmpty(),
        no_of_open_sides = this.no_of_open_sides.orEmpty(),
        no_of_Staircases = this.no_of_staircases.orEmpty(),
        noc_certified = this.noc_certified.orEmpty(),
        occupancy_certificate = this.occupancy_certificate.orEmpty(),
        //office_previously_used_for = this.office_previously_used_for.orEmpty(),
        other_rooms = this.other_rooms.orEmpty(),
        oxygen_duct = this.oxygen_duct.orEmpty(),
        pantry = this.pantry.orEmpty(),
        pantry_size = this.pantry.orEmpty(),
        parking_available = this.parking_available.orEmpty(),
        //is_it_pre_leased_pre_rented = this.is_it_pre_leased_pre_rented.orEmpty(),
        //price = this.price.orEmpty(),
        property_area = this.property_area.orEmpty(),
        property_facing = this.property_facing.orEmpty(),
        //property_floor_no = this.property_floor_no.orEmpty(),
        property_highlights = this.property_highlights.orEmpty(),
        property_name = this.property_name.orEmpty(),
        //property_ownership = this.property_ownership.orEmpty(),
        reception_area = this.reception_area.orEmpty(),
        state = this.state.orEmpty(),
        suitable_business_type = this.suitable_business_type.orEmpty(),
        super_built_up_area = this.super_built_up_area.orEmpty(),
        thumbnail = this.thumbnail.orEmpty(),
        total_floor = this.total_floor.orEmpty(),
        ups = this.ups.orEmpty(),
        user_post_id = this.user_post_id ?: 0,
        is_report = 0,
        landCategoryText = this.landCategoryText.orEmpty(),
        landTypeText = this.landTypeText.orEmpty(),
        user_type = this.user_type.orEmpty(),
        video = this.video.orEmpty(),
        washroom_details = this.washroom_details.orEmpty(),
        is_sold = 0,
        //price_negotiable = price_negotiable ?: "",
        pincode = this.pincode ?: "",
        draft = this.draft ?: "",
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

fun isWithinLast5DaysOfValidity1(
    timestamp: String,
    validityDays: Long = 90
): Boolean {
    val createdAt = Instant.parse(timestamp)
    val expiryDate = createdAt.plus(validityDays, ChronoUnit.DAYS)
    val fiveDaysBeforeExpiry = expiryDate.minus(5, ChronoUnit.DAYS)

    val now = Instant.now()

    return now.isAfter(fiveDaysBeforeExpiry) && now.isBefore(expiryDate)
}

//fun isWithinLast5DaysOfValidity(
//    createdAt: String,
//    validityDays: Long = 90
//): Boolean {
//    val createdInstant = Instant.parse(createdAt)
//    val expiryInstant = createdInstant.plus(validityDays, ChronoUnit.DAYS)
//    val now = Instant.now()
//
//    return now.isBefore(expiryInstant)
//}

fun isWithinLast5DaysOfValidity(
    createdAt: String?,
    validityDays: Long = 90
): Boolean {

    if (createdAt.isNullOrBlank()) return false

    val createdInstant = try {
        Instant.parse(createdAt)
    } catch (e: DateTimeParseException) {
        return false
    }

    val expiryInstant = createdInstant.plus(validityDays, ChronoUnit.DAYS)
    val last5DaysStart = expiryInstant.minus(5, ChronoUnit.DAYS)
    val now = Instant.now()

    return now.isAfter(last5DaysStart) && now.isBefore(expiryInstant)
}

