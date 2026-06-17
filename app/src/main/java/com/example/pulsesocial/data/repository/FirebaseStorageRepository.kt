package com.example.pulsesocial.data.repository

import android.content.Context
import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.tasks.await
import java.util.UUID
import javax.inject.Inject

class FirebaseStorageRepository @Inject constructor(
){

    private val storage = FirebaseStorage.getInstance()

    suspend fun uploadPostImage(uri: Uri): String {

        val fileName = UUID.randomUUID().toString()
        val ref = storage.reference.child("post_images/$fileName.jpg")

        ref.putFile(uri).await()

        return ref.downloadUrl.await().toString()
    }

    suspend fun uploadProfileImage(uri: Uri): String {

        val fileName = UUID.randomUUID().toString()
        val ref = storage.reference.child("profile_images/$fileName.jpg")

        ref.putFile(uri).await()

        return ref.downloadUrl.await().toString()
    }
}