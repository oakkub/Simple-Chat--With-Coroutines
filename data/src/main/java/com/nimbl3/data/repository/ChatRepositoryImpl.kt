package com.nimbl3.data.repository

import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.experimental.channels.Channel
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(val database: FirebaseDatabase,
                                             val user: FirebaseAuth): ChatRepository {

    override suspend fun sendMessage(message: String): SendMessageResult {
        val key = database.reference.child("chat").push().key
        Tasks.await(database.reference.child("chat").child(key.toString()).setValue(Message(key.toString(), user.currentUser?.email?.split("@")?.get(0) ?: "NO", message)))
        return SendMessageResult.Success(message)
    }

    override suspend fun messages(): Channel<MessagesResult> {
        val chatChannel = Channel<MessagesResult>()
        database.reference.child("chat").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {
                if (chatChannel.isClosedForSend) {
                    return
                }
                chatChannel.offer(MessagesResult.Error(databaseError.toException()))
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (chatChannel.isClosedForSend) {
                    return
                }

                chatChannel.offer(MessagesResult.Success(dataSnapshot.children
                    .mapNotNull { it.getValue(Message::class.java) }))
            }
        })
        return chatChannel
    }
}

data class Message(
    val id: String = "",
    val username: String = "",
    val message: String = ""
)