package com.ray.monsterhunter.data.source.remote

import android.icu.util.Calendar
import android.os.Build
import androidx.annotation.RequiresApi
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.ray.monsterhunter.MonsterApplication
import com.ray.monsterhunter.R
import com.ray.monsterhunter.data.Crawling
import com.ray.monsterhunter.data.source.MonsterDataSource
import com.ray.monsterhunter.data.source.Result
import com.ray.monsterhunter.util.Logger
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


object MonsterRemoteDataSource : MonsterDataSource {

    private val PATH_CRAWLING = "crawling"
    private const val KEY_START_TIME = "startTime"

    override suspend fun getCrawlings(): Result<List<Crawling>> = suspendCoroutine { continuation ->
        FirebaseFirestore.getInstance()
            .collection(PATH_CRAWLING)
            .orderBy(KEY_START_TIME, Query.Direction.DESCENDING)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val list = mutableListOf<Crawling>()
                    for (document in task.result!!) {

                        Logger.d(document.id + " => " + document.data)
                        val crawling = document.toObject(Crawling::class.java)
                        list.add(crawling)
                    }
                    continuation.resume(Result.Success(list))
                } else {
                    task.exception?.let {
                        continuation.resume(Result.Error(it))
                        return@addOnCompleteListener
                    }
                    continuation.resume(Result.Fail(MonsterApplication.instance.getString(R.string.notGood)))
                }
            }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override suspend fun publish(crawling: Crawling): Result<Boolean> = suspendCoroutine { continuation ->
        val crawlings = FirebaseFirestore.getInstance().collection(PATH_CRAWLING)
        val document = crawlings.document()

        crawling.id = document.id
        crawling.createTime = Calendar.getInstance().timeInMillis.toString()
        document
            .set(crawling)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    continuation.resume(Result.Success(true))
                } else {
                    task.exception?.let {
                        continuation.resume(Result.Error(it))
                        return@addOnCompleteListener
                    }
                    continuation.resume(Result.Fail(MonsterApplication.instance.getString(R.string.notGood)))
                }
            }
    }

}