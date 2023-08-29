package com.example.network

import co.naji.tosse.cryptography.crypto.spring.type.DecryptionType
import co.naji.tosse.cryptography.crypto.spring.type.EncryptionType
import com.example.cryptography.crypto.aead.AEAD
import com.example.cryptography.crypto.hybrid.Hybrid
import com.example.cryptography.crypto.util.KeySet
import com.example.cryptography.crypto.util.Util
import com.example.cryptography.crypto.util.privateKeysetHandle
import com.example.cryptography.crypto.util.publicKeysetHandle
import com.example.cryptography.crypto.util.secretKeysetHandle
import com.google.crypto.tink.KeysetHandle
import com.google.crypto.tink.config.TinkConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn

class NetworkCryptography constructor(
    coroutineScope: CoroutineScope,
    keySet: StateFlow<KeySet?>
) {

//    private fun loadKeysetJson(path: String, keysetName: String) = CleartextKeysetHandle
//        .read(JsonKeysetReader.withInputStream(context.assets.open("$path${File.separator}$keysetName.json")))

    private val eciesKeySet = keySet.map {
        if (it is KeySet.ECIESKeySet)
            it
        else null
    }
        .stateIn(coroutineScope, SharingStarted.Lazily, null)

    private val eciesPrivateKey = eciesKeySet.map { it?.privateKeysetHandle }
        .stateIn(coroutineScope, SharingStarted.Lazily, null)

    private val eciesPublicKey = eciesKeySet.map { it?.publicKeysetHandle }
        .stateIn(coroutineScope, SharingStarted.Lazily, null)

    private val symmetricKeySet = keySet.map {
        if (it is KeySet.SymmetricKeySet)
            it
        else null
    }
        .stateIn(coroutineScope, SharingStarted.Lazily, null)

    private val symmetricSecretKey = symmetricKeySet.map { it?.secretKeysetHandle }
        .stateIn(coroutineScope, SharingStarted.Lazily, null)

    private val aes128GCMKeyset = symmetricSecretKey
    private val aes256GCMKeyset = symmetricSecretKey
    private val chacha20Poly1305Keyset = symmetricSecretKey
    private val xChacha20Poly1305Keyset = symmetricSecretKey
    private val aes256SIVKeyset = symmetricSecretKey

    private val eciesP256KeysetPublic = eciesPublicKey
    private val eciesP256KeysetPrivate = eciesPrivateKey
    private val serverKeyset = eciesPrivateKey
    private val clientKeyset = eciesPublicKey
    private val eciesP256CompressedKeysetPublic = eciesPublicKey
    private val eciesP256CompressedKeysetPrivate = eciesPrivateKey

    private val AES128GCM =
        aes128GCMKeyset.map { if (it != null) AEAD(keysetHandle = it) else null }
            .stateIn(coroutineScope, SharingStarted.Lazily, null)
    private val AES256GCM =
        aes256GCMKeyset.map { if (it != null) AEAD(keysetHandle = it) else null }
            .stateIn(coroutineScope, SharingStarted.Lazily, null)
    private val CHACHA20POLY1305 =
        chacha20Poly1305Keyset.map { if (it != null) AEAD(keysetHandle = it) else null }
            .stateIn(coroutineScope, SharingStarted.Lazily, null)
    private val XCHACHA20POLY1305 =
        xChacha20Poly1305Keyset.map { if (it != null) AEAD(keysetHandle = it) else null }
            .stateIn(coroutineScope, SharingStarted.Lazily, null)
    private val ECIES_P256_HKDF_HMAC_SHA256_AES128_GCM: StateFlow<Hybrid?> =
        eciesP256KeysetPublic.combine(eciesP256KeysetPrivate) { public: KeysetHandle?, private: KeysetHandle? ->
            if (public != null && private != null)
                Hybrid(
                    publicKeysetHandle = public,
                    privateKeysetHandle = private
                )
            else
                null
        }.stateIn(coroutineScope, SharingStarted.Eagerly, null)

    private val ECIES_P256_COMPRESSED_HKDF_HMAC_SHA256_AES128_GCM: StateFlow<Hybrid?> =
        eciesP256CompressedKeysetPublic.combine(eciesP256CompressedKeysetPrivate) { public: KeysetHandle?, private: KeysetHandle? ->
            if (public != null && private != null)
                Hybrid(
                    publicKeysetHandle = public,
                    privateKeysetHandle = private
                )
            else
                null
        }.stateIn(coroutineScope, SharingStarted.Eagerly, null)

    private val AES256SIV =
        aes256SIVKeyset.map { if (it != null) AEAD(keysetHandle = it) else null }
            .stateIn(coroutineScope, SharingStarted.Lazily, null)

    // private val clientECIES by lazy { if(clientKeyset != null) HybridEncryptor(publicKeysetHandle = clientKeyset!!) else null}
    //private val serverECIES by lazy { if(serverKeyset != null && clientKeyset!=null) Hybrid(privateKeysetHandle = serverKeyset!!, publicKeysetHandle = clientKeyset!!) else null}

    private val ECIES: StateFlow<Hybrid?> =
        serverKeyset.combine(clientKeyset) { public: KeysetHandle?, private: KeysetHandle? ->
            if (public != null && private != null)
                Hybrid(
                    publicKeysetHandle = public,
                    privateKeysetHandle = private
                )
            else
                null
        }.stateIn(coroutineScope, SharingStarted.Lazily, null)

    fun encrypt(plainBody: String, method: EncryptionType): String? = when (method) {
        EncryptionType.NONE -> {
            plainBody
        }

        EncryptionType.AES128_GCM -> {
            AES128GCM.value?.encryptBase64(plainBody, Util.EMPTY_ASSOCIATED_DATA)
        }

        EncryptionType.AES256_GCM -> {
            AES256GCM.value?.encryptBase64(plainBody, Util.EMPTY_ASSOCIATED_DATA)
        }

        EncryptionType.CHACHA20_POLY1305 -> {
            CHACHA20POLY1305.value?.encryptBase64(plainBody, Util.EMPTY_ASSOCIATED_DATA)
        }

        EncryptionType.XCHACHA20_POLY1305 -> {
            XCHACHA20POLY1305.value?.encryptBase64(plainBody, Util.EMPTY_ASSOCIATED_DATA)
        }

        EncryptionType.AES256_SIV -> {
            AES256SIV.value?.encryptBase64(plainBody, Util.EMPTY_ASSOCIATED_DATA)
        }

        EncryptionType.ECIES_P256_HKDF_HMAC_SHA256_AES128_GCM -> {
            ECIES_P256_HKDF_HMAC_SHA256_AES128_GCM.value?.encryptByPublicBase64(
                plainBody,
                Util.EMPTY_ASSOCIATED_DATA
            )
        }

        EncryptionType.ECIES_P256_COMPRESSED_HKDF_HMAC_SHA256_AES128_GCM -> {
            ECIES_P256_COMPRESSED_HKDF_HMAC_SHA256_AES128_GCM.value?.encryptByPublicBase64(
                plainBody,
                Util.EMPTY_ASSOCIATED_DATA
            )
        }

        EncryptionType.ECIES_CLIENT -> {
            ECIES.value?.encryptByPublicBase64(plainBody, Util.EMPTY_ASSOCIATED_DATA)
        }

    }

    fun decrypt(cipherBody: String, method: DecryptionType): String? = when (method) {
        DecryptionType.AES128_GCM -> {
            AES128GCM.value?.decryptBase64(cipherBody, Util.EMPTY_ASSOCIATED_DATA)
        }

        DecryptionType.AES256_GCM -> {
            AES256GCM.value?.decryptBase64(cipherBody, Util.EMPTY_ASSOCIATED_DATA)
        }

        DecryptionType.CHACHA20_POLY1305 -> {
            CHACHA20POLY1305.value?.decryptBase64(cipherBody, Util.EMPTY_ASSOCIATED_DATA)
        }

        DecryptionType.XCHACHA20_POLY1305 -> {
            XCHACHA20POLY1305.value?.decryptBase64(cipherBody, Util.EMPTY_ASSOCIATED_DATA)
        }

        DecryptionType.AES256_SIV -> {
            AES256SIV.value?.decryptBase64(cipherBody, Util.EMPTY_ASSOCIATED_DATA)
        }

        DecryptionType.ECIES_P256_HKDF_HMAC_SHA256_AES128_GCM -> {
            ECIES_P256_HKDF_HMAC_SHA256_AES128_GCM.value?.decryptByPrivateBase64(
                cipherBody,
                Util.EMPTY_ASSOCIATED_DATA
            )
        }

        DecryptionType.ECIES_P256_COMPRESSED_HKDF_HMAC_SHA256_AES128_GCM -> {
            ECIES_P256_COMPRESSED_HKDF_HMAC_SHA256_AES128_GCM.value
                ?.decryptByPrivateBase64(
                    cipherBody,
                    Util.EMPTY_ASSOCIATED_DATA
                )
        }

        DecryptionType.ECIES_SERVER -> {
            ECIES.value?.decryptByPrivateBase64(cipherBody, Util.EMPTY_ASSOCIATED_DATA)
        }

    }

    init {
        TinkConfig.register()
    }
}