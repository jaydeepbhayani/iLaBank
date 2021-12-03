package com.jaydeepbhayani.ilabank.data.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class HomePagerResponse (
    var image: String? = "",
    var query: String? = ""
)