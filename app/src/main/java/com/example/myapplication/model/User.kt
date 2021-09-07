package com.example.myapplication.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class User (
    @SerializedName("id") val id: Int,
    @SerializedName("name") val fullName: String,
    @SerializedName("username") val username: String,
    @SerializedName("email") val email: String,
    @SerializedName("address") val address: Address,
    @SerializedName("phone") val phone: String,
    @SerializedName("website") val website: String,
    @SerializedName("company") val company: Company
) : Parcelable {
    val namePosted: String
        get() {
            return "Posted by: ${fullName}"
        }
}

@Parcelize
class Address(
    @SerializedName("street") val addressName: String,
    @SerializedName("suite") val suite: String,
    @SerializedName("city") val city: String,
    @SerializedName("zipcode") val zipcode: String
) : Parcelable {

    val fullAddress: String
    get() {
        return "${addressName} ${suite} ${city} ${zipcode}"
    }
}

@Parcelize
class Company(
    @SerializedName("name") val companyName: String,
    @SerializedName("catchPhrase") val companyDetail: String,
    @SerializedName("bs") val companyTagline: String
) : Parcelable {
    val companyFull: String
        get() {
            return "${companyName} (${companyDetail})"
        }
}