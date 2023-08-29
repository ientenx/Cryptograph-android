package com.example.cryptograph.session

import com.example.cryptography.crypto.util.KeySet
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class JwtHelper @Inject constructor() {

    fun extractKeySet(token: String?): KeySet? {
        if (token == null)
            return null

        val keySetBody = extractClaimsBody(token)

        val keySetMap = keySetBody.get(BODY_KEY_SET, LinkedHashMap::class.java)

        val symmetricSecretKey: String? = keySetMap[SYMMETRIC_SECRET_KEY] as? String

        val eciesPrivateKey: String? = keySetMap[ECIES_PRIVATE_KEY] as? String
        val eciesPublicKey: String? = keySetMap[ECIES_PUBLIC_KEY] as? String

        if (symmetricSecretKey != null)
            return KeySet.SymmetricKeySet(secretKey = symmetricSecretKey)

        if (eciesPrivateKey != null && eciesPublicKey != null)
            return KeySet.ECIESKeySet(
                privateKey = eciesPrivateKey,
                publicKey = eciesPublicKey
            )

        return null
    }

    private fun extractClaimsBody(token: String): Claims {
        val splitToken = token.split(".")
        return Jwts.parser().parse(splitToken[0] + "." + splitToken[1] + ".").body as Claims
    }

    companion object {
        const val BODY_KEY_SET: String = "keySet"
        const val SYMMETRIC_SECRET_KEY = "asymmetric"
        const val ECIES_PUBLIC_KEY = "publicKey"
        const val ECIES_PRIVATE_KEY = "privateKey"
    }
}