package co.naji.tosse.cryptography.crypto.core.templates

class AEADTemplate {

    // AeadKeyTemplates
    companion object {
        val AES128_EAX = "AES128_EAX"
        val AES256_EAX = "AES256_EAX"
        val AES128_CTR_HMAC_SHA256 = "AES128_CTR_HMAC_SHA256"
        val AES256_CTR_HMAC_SHA256 = "AES256_CTR_HMAC_SHA256"
        val AES128_GCM = "AES128_GCM"
        val AES256_GCM = "AES256_GCM"
        val CHACHA20_POLY1305 = "CHACHA20_POLY1305"
        val XCHACHA20_POLY1305 = "XCHACHA20_POLY1305"
   }
}