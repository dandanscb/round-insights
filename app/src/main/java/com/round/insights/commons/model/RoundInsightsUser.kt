package com.round.insights.commons.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RoundInsightsUser(
    val name: String,
    val nickname: String,
    val email: String
) : Parcelable
