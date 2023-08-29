package com.example.cryptography.crypto.streaming

import java.io.File

interface IStreamingAEADDecryptor {
    fun decryptFile(inputFile: File, outputFile: File, aData: ByteArray)

    fun decryptFile(inputFile: File, outputFile: File, aData: String) =
        decryptFile(inputFile = inputFile, outputFile = outputFile, aData = aData.toByteArray())
}