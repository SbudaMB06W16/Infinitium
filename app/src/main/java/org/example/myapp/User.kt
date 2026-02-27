package org.example.myapp

import androidx.annotation.DrawableRes

// A data class to represent a user on the leaderboard.
// The profilePic property is a drawable resource ID.
// You can add your own user profile images to the res/drawable folder
// and reference them here. For example: R.drawable.user_avatar

data class User(
    val name: String, 
    val score: Int, 
    @DrawableRes val profilePic: Int = R.drawable.ic_launcher_foreground
)
