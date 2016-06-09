package com.safechat.firebase.auth

import com.google.firebase.auth.FirebaseAuth

fun myUid() = FirebaseAuth.getInstance().currentUser!!.uid