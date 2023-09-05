package com.example.rasp.tamperdetection

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Base64
import android.util.Log
import java.security.MessageDigest

enum class Result {
    VALID,
    INVALID,
    UNKNOWN
}

fun Context.validateSignature(expectedSignature: String): Result {
    getAppSignature(this)?.hashedBase64()?.let { currentSignature ->
        Log.d("EXPECTED_SIGNATURE", currentSignature)
        return if (currentSignature == expectedSignature) {
            Result.VALID
        } else {
            Result.INVALID
        }
    }
    return Result.UNKNOWN
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

private fun android.content.pm.Signature.hashedBase64(): String? = try {
    val signatureBytes = this.toString().toByteArray()
    val digest = MessageDigest.getInstance("SHA-512")
    val hash = digest.digest(signatureBytes)
    Base64.encodeToString(hash, Base64.NO_WRAP)
} catch (exception: Exception) {
    null
}
