package com.example.cryptography.crypto.streaming

import java.io.File

interface IStreamingAEADEncryptor {
    fun encryptFile(inputFile: File, outputFile: File, aData: ByteArray)

    fun encryptFile(inputFile: File, outputFile: File, aData: String) =
        encryptFile(inputFile = inputFile, outputFile = outputFile, aData = aData.toByteArray())

}