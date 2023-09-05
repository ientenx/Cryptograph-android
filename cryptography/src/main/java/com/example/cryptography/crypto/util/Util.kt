package com.example.cryptography.crypto.util

import com.google.crypto.tink.*
import java.io.FileInputStream
import java.io.FileOutputStream
import java.nio.file.Files
import java.nio.file.Paths

class Util {

    companion object {
        final val EMPTY_ASSOCIATED_DATA = ByteArray(0)

        fun generateKeyset(templateName: String) = KeysetHandle.generateNew(KeyTemplates.get(templateName))

        fun saveKeysetJson(keyset: KeysetHandle, keysetName: String) {
            FileOutputStream("$keysetName.json").use { stream ->
                CleartextKeysetHandle.write(
                    keyset,
                    JsonKeysetWriter.withOutputStream(stream)
                )
            }
        }

        fun saveKeysetBinary(keyset: KeysetHandle, keysetName: String) {
            FileOutputStream("$keysetName.proto").use { stream ->
                CleartextKeysetHandle.write(
                    keyset,
                    BinaryKeysetWriter.withOutputStream(stream)
                )
            }
        }

        fun saveKeysetJSONEncrypted(keyset: KeysetHandle, keysetName: String, primitive: Aead) {
            FileOutputStream("$keysetName.json").use { stream ->
                keyset.write(
                    JsonKeysetWriter.withOutputStream(stream),
                    primitive
                )
            }
        }

        fun saveKeysetBinaryEncrypted(keyset: KeysetHandle, keysetName: String, primitive: Aead) {
            FileOutputStream("$keysetName.proto").use { stream ->
                keyset.write(
                    BinaryKeysetWriter.withOutputStream(stream),
                    primitive
                )
            }
        }

        fun loadKeysetJson(keysetName: String) = FileInputStream("$keysetName.json").use { stream ->
            CleartextKeysetHandle.read(
                JsonKeysetReader.withInputStream(stream)
            )
        }

        fun loadKeysetBinary(keysetName: String) = FileInputStream("$keysetName.proto").use { stream ->
            CleartextKeysetHandle.read(
                BinaryKeysetReader.withInputStream(stream)
            )
        }

        fun loadKeysetJsonEncrypted(keysetName: String, primitive: Aead): KeysetHandle {
            FileInputStream("$keysetName.json").use { stream ->
                return KeysetHandle.read(
                    JsonKeysetReader.withInputStream(stream),
                    primitive
                )
            }
        }

        fun loadKeysetBinaryEncrypted(keysetName: String, primitive: Aead): KeysetHandle {
            FileInputStream("$keysetName.proto").use { stream ->
                return KeysetHandle.read(
                    BinaryKeysetReader.withInputStream(stream),
                    primitive
                )
            }
        }

    }

}