package com.safechat.firebase.conversations

import com.google.firebase.database.*
import com.safechat.firebase.conversation.FirebaseMessage
import rx.Observable
import java.util.*

fun getConversationsList(myUid: String) = Observable.create<List<UserConversation>> { subscriber ->
    subscriber.onStart()
    FirebaseDatabase
            .getInstance()
            .reference
            .child("conversations")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot?) {
                    if (dataSnapshot == null) {
                        subscriber.onNext(emptyList())
                        subscriber.onCompleted()
                    } else {
                        val conversationsMap = dataSnapshot.getValue(object : GenericTypeIndicator<HashMap<String, HashMap<String, FirebaseMessage>>>() {})
                        val privateConversations = conversationsMap.filterConversationsByUid(myUid)
                        subscriber.onNext(privateConversations.map { UserConversation(it.key, it.value.message) })
                        subscriber.onCompleted()
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    subscriber.onError(databaseError.toException())
                }
            })
}

internal fun Map<String, Map<String, FirebaseMessage>>.filterConversationsByUid(myUid: String): Map<String, FirebaseMessage> {
    val notNestedPart = filterKeys { it == myUid }.values.firstOrNull() ?: emptyMap()
    val nestedPart = filterKeys { it != myUid }
            .filterValues { it.containsKey(myUid) }
            .map { it.key to it.value.filterKeys { it == myUid } }
            .map { it.first to it.second.values.last() }.toMap()
    return notNestedPart + nestedPart
}

data class UserConversation(val userUid: String,
                            val lastMessage: String?)