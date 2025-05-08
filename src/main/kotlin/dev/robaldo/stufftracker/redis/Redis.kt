package dev.robaldo.stufftracker.redis

import dev.robaldo.stufftracker.models.User
import io.ktor.utils.io.core.*
import io.lettuce.core.RedisClient
import io.lettuce.core.api.StatefulRedisConnection
import kotlinx.datetime.Clock
import org.h2.security.SHA3
import kotlin.random.Random

class Redis: Closeable {
    private val tokenNamespace = "token"
    val tokenDuration = 3600 * 24 * 7L // A week

    private val client: RedisClient = RedisClient.create("redis://localhost:6379")

    private fun generateToken(): String {
        val time = Clock.System.now().toEpochMilliseconds().toString().toByteArray()
        val random = Random(Clock.System.now().toEpochMilliseconds()).nextBytes(512)

        return SHA3.getSha3_512().update(time + random).toString()
    }

    suspend fun storeNewToken(user: User): String = storeNewToken(user.uid)

    suspend fun storeNewToken(userUid: String): String = connection { conn ->
        val async = conn.async()
        val token = generateToken()
        async.setex("$tokenNamespace:$token", tokenDuration, userUid)

        return@connection token
    }

    suspend fun validateAndRotateToken(token: String): Pair<String?, String?> = connection { conn ->
        val async = conn.async()

        var newToken: String? = null
        val userUid = async.get("$tokenNamespace:$token").get()

        if(userUid.isNullOrEmpty()) return@connection Pair(null, null)
        async.del("$tokenNamespace:$token")
        return@connection Pair(storeNewToken(userUid), userUid)
    }

    suspend fun invalidateToken(token: String) = connection { conn ->
        val async = conn.async()

        async.del("$tokenNamespace:$token")
    }

    private suspend fun <T> connection(block: suspend (conn: StatefulRedisConnection<String, String>) -> T): T {
        return client.connect().use { connection ->
            block(connection)
        }
    }

    override fun close() {
        client.close()
    }
}