package com.example.cryptography.crypto.streaming

import com.google.crypto.tink.KeysetHandle
import com.google.crypto.tink.StreamingAead
import java.io.*


class StreamingAEAD(keysetHandle: KeysetHandle): IStreamingAEADDecryptor, IStreamingAEADEncryptor {

    val streamingAead = keysetHandle.getPrimitive(StreamingAead::class.java)


     override fun encryptFile(inputFile: File, outputFile: File, aData: ByteArray) {
        streamingAead.newEncryptingStream(FileOutputStream(outputFile), aData).use { ciphertextStream ->
            FileInputStream(inputFile).use { plaintextStream ->
                val chunk = ByteArray(1024)
                var chunkLen = 0
                while (plaintextStream.read(chunk).also { chunkLen = it } != -1) {
                    ciphertextStream.write(chunk, 0, chunkLen)
                }
            }
        }
    }

     override fun decryptFile(inputFile: File, outputFile: File, aData: ByteArray) {
        val ciphertextStream = streamingAead.newDecryptingStream(FileInputStream(inputFile), aData)
        val plaintextStream: OutputStream = FileOutputStream(outputFile)
        val chunk = ByteArray(1024)
        var chunkLen = 0
        while (ciphertextStream.read(chunk).also { chunkLen = it } != -1) {
            plaintextStream.write(chunk, 0, chunkLen)
        }
        ciphertextStream.close()
        plaintextStream.close()
    }


}