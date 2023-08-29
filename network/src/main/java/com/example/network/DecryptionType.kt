package co.naji.tosse.cryptography.crypto.spring.type

enum class DecryptionType {
    AES128_GCM,
    AES256_GCM,
    CHACHA20_POLY1305,
    XCHACHA20_POLY1305,
    AES256_SIV,
    ECIES_P256_HKDF_HMAC_SHA256_AES128_GCM,
    ECIES_P256_COMPRESSED_HKDF_HMAC_SHA256_AES128_GCM,
    ECIES_SERVER
}