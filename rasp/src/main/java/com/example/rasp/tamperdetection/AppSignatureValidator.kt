package com.example.rasp.tamperdetection

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Base64
import android.util.Log
import java.security.MessageDigest
import java.security.Signature

enum class Result {
    VALID,
    INVALID,
    UNKNOWN
}

//fun Context.validateSignature(expectedSignature: String): Result {
//    return Result.VALID
//}

//@SuppressLint("PackageManagerGetSignatures")
//fun Context.getSignature(): String {
//    val signature: android.content.pm.Signature? = if (Build.VERSION.SDK_INT < 28) {
//        packageManager.getPackageInfo(
//            packageName,
//            PackageManager.GET_SIGNATURES
//        ).signatures.firstOrNull()
//    } else {
//        packageManager.getPackageInfo(
//            packageName,
//            PackageManager.GET_SIGNING_CERTIFICATES
//        ).signingInfo.apkContentsSigners.firstOrNull()
//    }
//    var signatureString = ""
//    signature?.let {
//        val signatureBytes = signature.toByteArray()
//        val digest = MessageDigest.getInstance("SHA")
//        val hash = digest.digest(signatureBytes)
//        signatureString = Base64.encodeToString(hash, Base64.NO_WRAP)
//    }
//    return signatureString
//}


//fun Context.validateSignature(expectedSignature: String): Result {
//    getAppSignature(this)?.string()?.let { currentSignature ->
//        Log.d("EXPECTED_SIGNATURE", currentSignature)
//        return if (currentSignature == expectedSignature) {
//            Result.VALID
//        } else {
//            Result.INVALID
//        }
//    }
//    return Result.UNKNOWN
//}

fun Context.getSignature(): String {
    throw RuntimeException("This method should only be called in the debug version")
}

@SuppressLint("PackageManagerGetSignatures")
private fun getAppSignature(context: Context): android.content.pm.Signature? = if (Build.VERSION.SDK_INT < 28) {
    context.packageManager.getPackageInfo(
        context.packageName,
        PackageManager.GET_SIGNATURES
    ).signatures.firstOrNull()
} else {
    context.packageManager.getPackageInfo(
        context.packageName,
        PackageManager.GET_SIGNING_CERTIFICATES
    ).signingInfo.apkContentsSigners.firstOrNull()
}

private fun Signature.string(): String? = try {
    val signatureBytes = this.toString().toByteArray()
    val digest = MessageDigest.getInstance("SHA")
    val hash = digest.digest(signatureBytes)
    Base64.encodeToString(hash, Base64.NO_WRAP)
} catch (exception: Exception) {
    null
}
