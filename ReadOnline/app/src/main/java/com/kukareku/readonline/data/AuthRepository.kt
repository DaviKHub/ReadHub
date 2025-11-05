package com.kukareku.readonline.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await

class AuthRepository {
    private val auth = FirebaseAuth.getInstance()

    fun currentUser(): FirebaseUser? = auth.currentUser

    suspend fun signIn(email: String, password: String): Result<FirebaseUser?> = try {
        val res = auth.signInWithEmailAndPassword(email, password).await()
        Result.success(res.user)
    } catch (e: Exception) {
        Result.failure(e)
    }

    suspend fun register(email: String, password: String): Result<FirebaseUser?> = try {
        val res = auth.createUserWithEmailAndPassword(email, password).await()
        Result.success(res.user)
    } catch (e: Exception) {
        Result.failure(e)
    }

    fun signOut() = auth.signOut()
}
