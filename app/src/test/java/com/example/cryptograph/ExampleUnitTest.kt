package com.example.cryptograph

import com.example.cryptograph.session.JwtHelper
import com.example.cryptography.crypto.hybrid.Hybrid
import com.example.cryptography.crypto.hybrid.HybridDecryptor
import com.example.cryptography.crypto.hybrid.HybridEncryptor
import com.example.cryptography.crypto.util.KeySet
import com.example.cryptography.crypto.util.Util
import com.example.cryptography.crypto.util.privateKeysetHandle
import com.example.cryptography.crypto.util.publicKeysetHandle
import com.google.crypto.tink.CleartextKeysetHandle
import com.google.crypto.tink.JsonKeysetReader
import com.google.crypto.tink.config.TinkConfig
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import org.junit.Assert.*
import org.junit.Test


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        TinkConfig.register()
//        val jwtHelper = JwtHelper()
//        (jwtHelper.extractKeySet(
//            "eyJhbGciOiJIUzUxMiJ9.eyJrZXlTZXQiOnsicHJpdmF0ZUtleSI6IntcInByaW1hcnlLZXlJZFwiOjE5MDE0NzAwODgsXCJrZXlcIjpbe1wia2V5RGF0YVwiOntcInR5cGVVcmxcIjpcInR5cGUuZ29vZ2xlYXBpcy5jb20vZ29vZ2xlLmNyeXB0by50aW5rLkVjaWVzQWVhZEhrZGZQcml2YXRlS2V5XCIsXCJ2YWx1ZVwiOlwiRW93QkVrUUtCQWdDRUFNU09oSTRDakIwZVhCbExtZHZiMmRzWldGd2FYTXVZMjl0TDJkdmIyZHNaUzVqY25sd2RHOHVkR2x1YXk1QlpYTkhZMjFMWlhrU0FoQVFHQUVZQWhvaEFMZ2RFMk5CZGxwRWRIdTN4dk05R2pPcVBUZzZXZlY4WDNOSnBoYndBSm5nSWlFQWp5RlkybW1HUERzeHUxUjMrc3lhSGdrWmkweStteGZLNWxmV0w4Z016ZW9hSVFDaTd2UjR5MWhySzgwTVFKSlowbXRoaUhHcDkyeExYdTNXaDdvbktxTHJ5dz09XCIsXCJrZXlNYXRlcmlhbFR5cGVcIjpcIkFTWU1NRVRSSUNfUFJJVkFURVwifSxcInN0YXR1c1wiOlwiRU5BQkxFRFwiLFwia2V5SWRcIjoxOTAxNDcwMDg4LFwib3V0cHV0UHJlZml4VHlwZVwiOlwiVElOS1wifV19XG4iLCJwdWJsaWNLZXkiOiJ7XCJwcmltYXJ5S2V5SWRcIjoxOTAxNDcwMDg4LFwia2V5XCI6W3tcImtleURhdGFcIjp7XCJ0eXBlVXJsXCI6XCJ0eXBlLmdvb2dsZWFwaXMuY29tL2dvb2dsZS5jcnlwdG8udGluay5FY2llc0FlYWRIa2RmUHVibGljS2V5XCIsXCJ2YWx1ZVwiOlwiRWtRS0JBZ0NFQU1TT2hJNENqQjBlWEJsTG1kdmIyZHNaV0Z3YVhNdVkyOXRMMmR2YjJkc1pTNWpjbmx3ZEc4dWRHbHVheTVCWlhOSFkyMUxaWGtTQWhBUUdBRVlBaG9oQUxnZEUyTkJkbHBFZEh1M3h2TTlHak9xUFRnNldmVjhYM05KcGhid0FKbmdJaUVBanlGWTJtbUdQRHN4dTFSMytzeWFIZ2taaTB5K214Zks1bGZXTDhnTXplbz1cIixcImtleU1hdGVyaWFsVHlwZVwiOlwiQVNZTU1FVFJJQ19QVUJMSUNcIn0sXCJzdGF0dXNcIjpcIkVOQUJMRURcIixcImtleUlkXCI6MTkwMTQ3MDA4OCxcIm91dHB1dFByZWZpeFR5cGVcIjpcIlRJTktcIn1dfVxuIn0sInN1YiI6IjViNzMzZjFhLTQwZjItNDRlMC05YjM3LTI2NGQ4OWNiOTBlOSIsImlhdCI6MTY5MzIzNTk1NiwiZXhwIjoxNjk0MDk5OTU2fQ.Fknz75WYiqeoOkg2yZ3fw3MMVpNSk3NMkWE4g3awcNrXyXVtR1QGmsO8bn6Q9bzGf4RqiNjDlxwJ9Utd8jYGgQ"
//        ) as KeySet.ECIESKeySet).let(::println)

//        val jws = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWV9.TJVA95OrM7E2cBab30RMHrHDcEfxjoYZgeFONFh7HgQ";
//        val i = jws.lastIndexOf('.')
//        val withoutSignature = jws.substring(0, i+1);
//
//        val claims : Claims = Jwts.parser().parse(withoutSignature).body as Claims;

//
//        println(keySet)
//        println(keySet.publicKeysetHandle)
//        println(keySet.privateKeysetHandle)
//
//        val eciesP256CompressedKeysetPublic by lazy { keySet.publicKeysetHandle }
//        val eciesP256CompressedKeysetPrivate by lazy { keySet.privateKeysetHandle }
//
//        val ECIES_P256_COMPRESSED_HKDF_HMAC_SHA256_AES128_GCM by lazy {
//            Hybrid(
//                publicKeysetHandle = eciesP256CompressedKeysetPublic,
//                privateKeysetHandle = eciesP256CompressedKeysetPrivate
//            )
//        }
//
//        ECIES_P256_COMPRESSED_HKDF_HMAC_SHA256_AES128_GCM.decryptByPrivateBase64(
//            "AXFWIYgDT32Gk6IWTIsyQhyn12gkccmwSZcdCsQcd+i2xW6gYk8NSSfEmYBeb3B53DYQ2zngbjFH0c3U0X0s4+tFTCM=",
//            Util.EMPTY_ASSOCIATED_DATA
//        ).let { println(it) }


        val keySet = KeySet.ECIESKeySet(
            privateKey = """
                {"primaryKeyId":1901470088,"key":[{"keyData":{"typeUrl":"type.googleapis.com/google.crypto.tink.EciesAeadHkdfPrivateKey","value":"EowBEkQKBAgCEAMSOhI4CjB0eXBlLmdvb2dsZWFwaXMuY29tL2dvb2dsZS5jcnlwdG8udGluay5BZXNHY21LZXkSAhAQGAEYAhohALgdE2NBdlpEdHu3xvM9GjOqPTg6WfV8X3NJphbwAJngIiEAjyFY2mmGPDsxu1R3+syaHgkZi0y+mxfK5lfWL8gMzeoaIQCi7vR4y1hrK80MQJJZ0mthiHGp92xLXu3Wh7onKqLryw==","keyMaterialType":"ASYMMETRIC_PRIVATE"},"status":"ENABLED","keyId":1901470088,"outputPrefixType":"TINK"}]}
                """.trimIndent(),
            publicKey = """
                {"primaryKeyId":1901470088,"key":[{"keyData":{"typeUrl":"type.googleapis.com/google.crypto.tink.EciesAeadHkdfPublicKey","value":"EkQKBAgCEAMSOhI4CjB0eXBlLmdvb2dsZWFwaXMuY29tL2dvb2dsZS5jcnlwdG8udGluay5BZXNHY21LZXkSAhAQGAEYAhohALgdE2NBdlpEdHu3xvM9GjOqPTg6WfV8X3NJphbwAJngIiEAjyFY2mmGPDsxu1R3+syaHgkZi0y+mxfK5lfWL8gMzeo=","keyMaterialType":"ASYMMETRIC_PUBLIC"},"status":"ENABLED","keyId":1901470088,"outputPrefixType":"TINK"}]}
                     """.trimIndent()
        )
        val clientECIES by lazy { HybridEncryptor(publicKeysetHandle = keySet.publicKeysetHandle) }
        val serverECIES by lazy { HybridDecryptor(privateKeysetHandle = keySet.privateKeysetHandle) }

        val gooz = clientECIES.encryptBase64("amir plain",Util.EMPTY_ASSOCIATED_DATA)

        serverECIES.decryptBase64("AXFWIYgDKzWyxqd897Mbnr1aUZ+u8UJU2byuMOhO6eC3NuOYuEBNq84AvOO2wwceFEiSFfmoVrikPSSKl0gKFjOdFzw=",
            Util.EMPTY_ASSOCIATED_DATA)

    }
}