package com.ray.monsterhunter.data.source.remote

import android.icu.util.Calendar
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.ray.monsterhunter.MonsterApplication
import com.ray.monsterhunter.R
import com.ray.monsterhunter.data.Activity
import com.ray.monsterhunter.data.ChatRoom
import com.ray.monsterhunter.data.Crawling
import com.ray.monsterhunter.data.User
import com.ray.monsterhunter.data.source.MonsterDataSource
import com.ray.monsterhunter.data.source.Result
import com.ray.monsterhunter.util.Logger
import com.ray.monsterhunter.util.UserManager
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


object MonsterRemoteDataSource : MonsterDataSource {

    private val PATH_CRAWLING = "crawling"
    private val PATH_USER = "user"
    private val PATH_ACTIVITY = "activity"
    private val PATH_CHATROOM = "chatRoom"
    private const val KEY_START_TIME = "dateTime"


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

    override fun getLiveChatRoom(): MutableLiveData<List<ChatRoom>> {

        val liveData = MutableLiveData<List<ChatRoom>>()

        FirebaseFirestore.getInstance()
            .collection(PATH_CHATROOM)
//           .orderBy(KEY_START_TIME, Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, exception ->

                val list = mutableListOf<ChatRoom>()
                for (document in snapshot!!) {
                    Logger.d(document.id + " => " + document.data)

                    val chatroom = document.toObject(ChatRoom::class.java)
                    list.add(chatroom)
                }

                liveData.value = list
            }
        return liveData
    }

    override suspend fun getActivitys(): Result<List<Activity>> = suspendCoroutine { continuation ->
        FirebaseFirestore.getInstance()
            .collection(PATH_ACTIVITY)
//            .orderBy(KEY_START_TIME, Query.Direction.DESCENDING)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val list = mutableListOf<Activity>()
                    for (document in task.result!!) {

                        Logger.d(document.id + " => " + document.data)
                        val activity = document.toObject(Activity::class.java)
                        list.add(activity)
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
    override suspend fun publish(crawling: Crawling): Result<Boolean> =
        suspendCoroutine { continuation ->
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

    override suspend fun pushUser(user: User): Result<Boolean> = suspendCoroutine { continuation ->
        val users = FirebaseFirestore.getInstance().collection(PATH_USER)
        val document = users.document()

        user.documentId = document.id
        users
            .whereEqualTo("email", UserManager.userData.email)
            .get()
            .addOnSuccessListener { task ->
                if (task.isEmpty) {
                    Logger.d("wwwwwwwwww${task}")
                    document
                        .set(user)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                continuation.resume(Result.Success(true))
                            } else {
                                task.exception?.let {
                                    continuation.resume(Result.Error(it))
                                    return@addOnCompleteListener
                                }
                                continuation.resume(
                                    Result.Fail(
                                        MonsterApplication.instance.getString(
                                            R.string.notGood
                                        )
                                    )
                                )
                            }
                        }
                } else {
                    document
                        .update("emiil", UserManager.userData.email)
                }
            }
    }

    override suspend fun getUser(): Result<User> = suspendCoroutine { continuation ->
        FirebaseFirestore.getInstance()
            .collection(PATH_USER)
            .whereEqualTo("email", UserManager.userData.email)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    var user1 = User()
                    for (document in task.result!!) {

                        var user = document.toObject(User::class.java)
                        user1 = user
                        Logger.d("geeeeeeeeetttt ${user}")

                    }
                    continuation.resume(Result.Success(user1))

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