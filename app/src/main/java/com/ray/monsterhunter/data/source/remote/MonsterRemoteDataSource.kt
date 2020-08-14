package com.ray.monsterhunter.data.source.remote

import android.icu.util.Calendar
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import com.ray.monsterhunter.MonsterApplication
import com.ray.monsterhunter.R
import com.ray.monsterhunter.data.*
import com.ray.monsterhunter.data.source.MonsterDataSource
import com.ray.monsterhunter.data.source.Result
import com.ray.monsterhunter.util.ImageManger
import com.ray.monsterhunter.util.Logger
import com.ray.monsterhunter.util.UserManager
import java.sql.DataTruncation
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


object MonsterRemoteDataSource : MonsterDataSource {

    private val PATH_CRAWLING = "crawling"
    private val PATH_HISTORY = "history"
    private val PATH_USER = "user"
    private val PATH_MONSTER = "imagemonster"
    private val PATH_ACTIVITY = "activity"
    private val PATH_CHATROOM = "chatRoom"
    private val PATH_MESSAGE = "message"
    private val PATH_LEAVEMESSAGE = "leaveMessage"
    private val PATH_FRIENDLIST = "friendList"
    private val PATH_USERARMSTYPE = "userArmsType"
    private const val KEY_START_TIME = "dateTime"
    private const val KEY_CREAT_TIME = "createTime"


    override suspend fun getCrawlings(): Result<List<Crawling>> = suspendCoroutine { continuation ->
        FirebaseFirestore.getInstance()
            .collection(PATH_CRAWLING)
            .orderBy(KEY_CREAT_TIME, Query.Direction.DESCENDING)
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

    override suspend fun getActivitys(): Result<List<Activity>> = suspendCoroutine { continuation ->
        FirebaseFirestore.getInstance()
            .collection(PATH_ACTIVITY)
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

    override suspend fun getAllUser(searchText: String): Result<List<User>> =
        suspendCoroutine { continuation ->
            FirebaseFirestore.getInstance()
                .collection(PATH_USER)
                .orderBy("email")
                .startAt(searchText)
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val list = mutableListOf<User>()
                        for (document in task.result!!) {
                            val user = document.toObject(User::class.java)
                            list.add(user)
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


    override suspend fun getMyUser(document: String): Result<List<User>> =
        suspendCoroutine { continuation ->
            FirebaseFirestore.getInstance()
                .collection(PATH_USER)
                .document(document)
                .collection(PATH_FRIENDLIST)
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val list = mutableListOf<User>()
                        for (document in task.result!!) {
                            val user = document.toObject(User::class.java)
                            list.add(user)
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
            .orderBy(KEY_CREAT_TIME, Query.Direction.DESCENDING)
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

    override fun getLiveHistory(): MutableLiveData<List<History>> {
        var historys = FirebaseFirestore.getInstance().collection(PATH_USER)
        var document =
            UserManager.userData.email?.let { historys.document(it).collection(PATH_HISTORY) }
        val liveData = MutableLiveData<List<History>>()

        if (document != null) {
            document
                .orderBy(KEY_CREAT_TIME, Query.Direction.DESCENDING)
                .addSnapshotListener { snapshot, exception ->

                    val list = mutableListOf<History>()
                    for (document in snapshot!!) {
                        Logger.d(document.id + " => " + document.data)

                        val history = document.toObject(History::class.java)
                        list.add(history)
                    }

                    liveData.value = list
                }
        }
        return liveData
    }


    override fun getLiveMessage(document: String): MutableLiveData<List<Message>> {

        val liveData = MutableLiveData<List<Message>>()

        FirebaseFirestore.getInstance()
            .collection(PATH_CHATROOM)
            .document(document)
            .collection(PATH_MESSAGE)
            .orderBy(KEY_CREAT_TIME, Query.Direction.ASCENDING)
            .addSnapshotListener { snapshot, exception ->
                val list = mutableListOf<Message>()
                for (document in snapshot!!) {
                    Logger.d(document.id + " => " + document.data)
                    val message = document.toObject(Message::class.java)
                    list.add(message)
                }
                liveData.value = list
            }
        return liveData
    }

    override fun getLiveLeaveMessage(document: String): MutableLiveData<List<Message>> {

        val liveData = MutableLiveData<List<Message>>()

        FirebaseFirestore.getInstance()
            .collection(PATH_CRAWLING)
            .document(document)
            .collection(PATH_LEAVEMESSAGE)
            .orderBy(KEY_CREAT_TIME, Query.Direction.ASCENDING)
            .addSnapshotListener { snapshot, exception ->
                val list = mutableListOf<Message>()
                for (document in snapshot!!) {
                    Logger.d(document.id + " => " + document.data)
                    val message = document.toObject(Message::class.java)
                    list.add(message)
                }
                liveData.value = list
            }
        return liveData
    }

    override fun getLiveChatRoomScore(document: String): MutableLiveData<ChatRoom> {
        val liveDataChatRoom = MutableLiveData<ChatRoom>()

        FirebaseFirestore.getInstance()
            .collection(PATH_CHATROOM)
            .document(document)
            .addSnapshotListener { snapshot, exception ->
                Logger.d(snapshot?.id + " => " + snapshot?.data)
                val chatRooms = snapshot?.toObject(ChatRoom::class.java)
                liveDataChatRoom.value = chatRooms
            }
        return liveDataChatRoom
    }

    override fun getLiveUserOneScore(teammate: String): MutableLiveData<User> {
        val liveDataOne = MutableLiveData<User>()

        FirebaseFirestore.getInstance()
            .collection(PATH_USER)
            .document(teammate)
            .addSnapshotListener { snapshot, exception ->
                val users = snapshot?.toObject(User::class.java)
                liveDataOne.value = users
            }
        return liveDataOne

    }

    override fun getLiveUserTwoScore(teammate: String): MutableLiveData<User> {
        val liveDataOne = MutableLiveData<User>()

        FirebaseFirestore.getInstance()
            .collection(PATH_USER)
            .document(teammate)
            .addSnapshotListener { snapshot, exception ->
                Logger.d(snapshot?.id + " => " + snapshot?.data)
                val users = snapshot?.toObject(User::class.java)
                liveDataOne.value = users
            }
        return liveDataOne

    }

    override fun getLiveUserThreeScore(teammate: String): MutableLiveData<User> {
        val liveDataOne = MutableLiveData<User>()

        FirebaseFirestore.getInstance()
            .collection(PATH_USER)
            .document(teammate)
            .addSnapshotListener { snapshot, exception ->
                Logger.d(snapshot?.id + " => " + snapshot?.data)
                val users = snapshot?.toObject(User::class.java)
                liveDataOne.value = users
            }
        return liveDataOne

    }

    override fun getLiveUserFourScore(teammate: String): MutableLiveData<User> {
        val liveDataOne = MutableLiveData<User>()

        FirebaseFirestore.getInstance()
            .collection(PATH_USER)
            .document(teammate)
            .addSnapshotListener { snapshot, exception ->
                Logger.d(snapshot?.id + " => " + snapshot?.data)
                val users = snapshot?.toObject(User::class.java)
                liveDataOne.value = users
            }
        return liveDataOne

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

    override suspend fun getUserOneArms(document: String, teammate: String): Result<UserArms> =
        suspendCoroutine { continuation ->
            FirebaseFirestore.getInstance()
                .collection(PATH_CHATROOM)
                .document(document)
                .collection(PATH_USERARMSTYPE)
                .whereEqualTo("email", teammate)
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        var user1 = UserArms()
                        for (document in task.result!!) {

                            var user = document.toObject(UserArms::class.java)
                            user1 = user
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

    override suspend fun getUserTwoArms(document: String, teammate: String): Result<UserArms> =
        suspendCoroutine { continuation ->
            FirebaseFirestore.getInstance()
                .collection(PATH_CHATROOM)
                .document(document)
                .collection(PATH_USERARMSTYPE)
                .whereEqualTo("email", teammate)
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        var user1 = UserArms()
                        for (document in task.result!!) {

                            var user = document.toObject(UserArms::class.java)
                            user1 = user

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

    override suspend fun getUserThreeArms(document: String, teammate: String): Result<UserArms> =
        suspendCoroutine { continuation ->
            FirebaseFirestore.getInstance()
                .collection(PATH_CHATROOM)
                .document(document)
                .collection(PATH_USERARMSTYPE)
                .whereEqualTo("email", teammate)
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        var user1 = UserArms()
                        for (document in task.result!!) {

                            var user = document.toObject(UserArms::class.java)
                            user1 = user

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

    override suspend fun getUserFourArms(document: String, teammate: String): Result<UserArms> =
        suspendCoroutine { continuation ->
            FirebaseFirestore.getInstance()
                .collection(PATH_CHATROOM)
                .document(document)
                .collection(PATH_USERARMSTYPE)
                .whereEqualTo("email", teammate)
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        var user1 = UserArms()
                        for (document in task.result!!) {

                            var user = document.toObject(UserArms::class.java)
                            user1 = user

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


    override suspend fun getImageMonster(): Result<MonsterUri> = suspendCoroutine { continuation ->
        FirebaseFirestore.getInstance()
            .collection(PATH_MONSTER)
            .whereEqualTo("id", "monster")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    var monsterUri1 = MonsterUri()
                    for (document in task.result!!) {
                        var monster = document.toObject(MonsterUri::class.java)
                        monsterUri1 = monster
                        var monster2 = MonsterUri(
                            monsterFireDragon = monster.monsterFireDragon,
                            monsterIcehit = monster.monsterIcehit,
                            monsterIceteeth = monster.monsterIceteeth,
                            monsterRoomPost = monster.monsterRoomPost,
                            monsterUnico = monster.monsterUnico,
                            monsterYellowBlack = monster.monsterYellowBlack
                        )
                        ImageManger.imageData = monster2
                    }
                    continuation.resume(Result.Success(monsterUri1))

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
            crawling.createTime = Calendar.getInstance().timeInMillis
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


    @RequiresApi(Build.VERSION_CODES.N)
    override suspend fun pushHistory1(history: History, email: String): Result<Boolean> =
        suspendCoroutine { continuation ->
            val historys = FirebaseFirestore.getInstance().collection(PATH_USER)
            val document = historys.document(email).collection(PATH_HISTORY).document()

            history.createTime = Calendar.getInstance().timeInMillis
            document
                .set(history)
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


    @RequiresApi(Build.VERSION_CODES.N)
    override suspend fun pushHistory2(history: History, email: String): Result<Boolean> =
        suspendCoroutine { continuation ->
            val historys = FirebaseFirestore.getInstance().collection(PATH_USER)
            val document = historys.document(email).collection(PATH_HISTORY).document()

            history.createTime = Calendar.getInstance().timeInMillis
            document
                .set(history)
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

    @RequiresApi(Build.VERSION_CODES.N)
    override suspend fun pushHistory3(history: History, email: String): Result<Boolean> =
        suspendCoroutine { continuation ->
            val historys = FirebaseFirestore.getInstance().collection(PATH_USER)
            val document = historys.document(email).collection(PATH_HISTORY).document()

            history.createTime = Calendar.getInstance().timeInMillis
            document
                .set(history)
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

    @RequiresApi(Build.VERSION_CODES.N)
    override suspend fun pushHistory4(history: History, email: String): Result<Boolean> =
        suspendCoroutine { continuation ->
            val historys = FirebaseFirestore.getInstance().collection(PATH_USER)
            val document = historys.document(email).collection(PATH_HISTORY).document()

            history.createTime = Calendar.getInstance().timeInMillis
            document
                .set(history)
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
        val document = UserManager.userData.email?.let { users.document(it) }

        user.documentId = document?.id
        users
            .whereEqualTo("email", UserManager.userData.email)
            .get()
            .addOnSuccessListener { task ->
                if (task.isEmpty) {
                    if (document != null) {
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
                    }
                } else {

                }
            }
    }


    @RequiresApi(Build.VERSION_CODES.N)
    override suspend fun pushChatRoom(chatRoom: ChatRoom): Result<Boolean> =
        suspendCoroutine { continuation ->
            val chatRooms = FirebaseFirestore.getInstance().collection(PATH_CHATROOM)
            val document = chatRooms.document()

            chatRoom.documentId = document.id
            chatRoom.createTime = Calendar.getInstance().timeInMillis

            document
                .set(chatRoom)
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
        }


    @RequiresApi(Build.VERSION_CODES.N)
    override suspend fun sentMessage(message: Message, document: String): Result<Boolean> =
        suspendCoroutine { continuation ->
            val messages = FirebaseFirestore.getInstance().collection(PATH_CHATROOM)
            val documentMessage = messages.document(document).collection(PATH_MESSAGE).document()

            message.createTime = Calendar.getInstance().timeInMillis

            documentMessage
                .set(message)
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
        }


    @RequiresApi(Build.VERSION_CODES.N)
    override suspend fun leaveMessage(
        message: Message, document: String
    ): Result<Boolean> =
        suspendCoroutine { continuation ->
            val messages = FirebaseFirestore.getInstance().collection(PATH_CRAWLING)
            val documentMessage =
                messages.document(document).collection(PATH_LEAVEMESSAGE).document()

            message.createTime = Calendar.getInstance().timeInMillis

            documentMessage
                .set(message)
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
        }

    @RequiresApi(Build.VERSION_CODES.N)
    override suspend fun postFriend(user: User): Result<Boolean> =
        suspendCoroutine { continuation ->
            val users = FirebaseFirestore.getInstance().collection(PATH_USER)
            val documentFriendList =
                UserManager.userData.email?.let {
                    user.email?.let { it1 ->
                        users.document(it).collection(PATH_FRIENDLIST).document(
                            it1
                        )
                    }
                }
            user.track = "true"
            if (documentFriendList != null) {
                documentFriendList
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
            }
        }

    override suspend fun cancelFriend(
        user: User
    ): Result<Boolean> =
        suspendCoroutine { continuation ->
            val cancelFriend1 =
                FirebaseFirestore.getInstance().collection(PATH_USER)
            val documentCancelUser = UserManager.userData.email?.let {
                user.email?.let { it1 ->
                    cancelFriend1.document(it)
                        .collection(PATH_FRIENDLIST).document(it1)
                }
            }
            if (documentCancelUser != null) {
                documentCancelUser
                    .delete()
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
            }
        }


    @RequiresApi(Build.VERSION_CODES.N)
    override suspend fun getUserArms(userArmsType: UserArms, document: String): Result<Boolean> =
        suspendCoroutine { continuation ->
            val userArmsTypes = FirebaseFirestore.getInstance().collection(PATH_CHATROOM)
            val documentMessage = userArmsTypes.document(document)
                .collection(PATH_USERARMSTYPE).document(userArmsType.email)

            userArmsType.createTime = Calendar.getInstance().timeInMillis

            documentMessage
                .set(userArmsType)
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
        }


    @RequiresApi(Build.VERSION_CODES.N)
    override suspend fun update1(teamList: List<String>, document: String): Result<Boolean> =
        suspendCoroutine { continuation ->
            val userUpdate1 = FirebaseFirestore.getInstance().collection(PATH_CHATROOM)
//            val document = messages.document()

            userUpdate1
                .document(document)
                .update("teammate", teamList)
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
        }

    @RequiresApi(Build.VERSION_CODES.N)
    override suspend fun updateChatRoomInfo(
        chatRoom: LiveData<ChatRoom>,
        document: String
    ): Result<Boolean> =
        suspendCoroutine { continuation ->
            val updateChatRoom = FirebaseFirestore.getInstance().collection(PATH_CHATROOM)

            updateChatRoom
                .document(document)
                .update(
                    "finishTime",
                    chatRoom.value?.finishTime,
                    "startTime",
                    chatRoom.value?.startTime, "speaker", chatRoom.value?.speaker
                )
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        updateChatRoom
                            .document(document)
                            .update("missionResult", chatRoom.value?.missionResult)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    updateChatRoom
                                        .document(document)
                                        .update("endToScore", chatRoom.value?.endToScore)
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

                                }
                            }
                    }
                }
        }

    @RequiresApi(Build.VERSION_CODES.N)
    override suspend fun updateUserOne(
        userId: String,
        userOneScore: ArmsType,
        allFight: Long
    ): Result<Boolean> =
        suspendCoroutine { continuation ->
            val updateUserOne =
                FirebaseFirestore.getInstance().collection(PATH_USER)

            updateUserOne
                .document(userId)
                .update("armsType", userOneScore, "allFight", allFight)
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
        }

    @RequiresApi(Build.VERSION_CODES.N)
    override suspend fun updateUserTwo(
        userId: String,
        userTwoScore: ArmsType,
        allFight: Long
    ): Result<Boolean> =
        suspendCoroutine { continuation ->
            val updateUserOne =
                FirebaseFirestore.getInstance().collection(PATH_USER)

            updateUserOne
                .document(userId)
                .update("armsType", userTwoScore, "allFight", allFight)
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
        }

    @RequiresApi(Build.VERSION_CODES.N)
    override suspend fun updateUserThree(
        userId: String,
        userThreeScore: ArmsType,
        allFight: Long
    ): Result<Boolean> =
        suspendCoroutine { continuation ->
            val updateUserOne =
                FirebaseFirestore.getInstance().collection(PATH_USER)

            updateUserOne
                .document(userId)
                .update("armsType", userThreeScore, "allFight", allFight)
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
        }

    @RequiresApi(Build.VERSION_CODES.N)
    override suspend fun updateUserFour(
        userId: String,
        userFourScore: ArmsType,
        allFight: Long
    ): Result<Boolean> =
        suspendCoroutine { continuation ->
            val updateUserOne =
                FirebaseFirestore.getInstance().collection(PATH_USER)

            updateUserOne
                .document(userId)
                .update("armsType", userFourScore, "allFight", allFight)
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
        }

    override suspend fun cencelUser(
        userArmsType: UserArms,
        document: String
    ): Result<Boolean> =
        suspendCoroutine { continuation ->
            val canceluser1 =
                FirebaseFirestore.getInstance().collection(PATH_CHATROOM)
            val documentCancelUser = canceluser1.document(document)
                .collection(PATH_USERARMSTYPE).document(userArmsType.email)

            documentCancelUser
                .delete()
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
        }

    override suspend fun deleteRoom(document: String): Result<Boolean> =
        suspendCoroutine { continuation ->
            val deleteRooms =
                FirebaseFirestore.getInstance().collection(PATH_CHATROOM)
            val document = deleteRooms.document(document)

            document
                .delete()
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
        }
}