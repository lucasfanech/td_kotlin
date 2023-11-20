package fr.unilasalle.fanech.td_android

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    @Insert
    suspend fun insert(user: User)

    @Query("SELECT * FROM user_table")
    suspend fun getAllUsers(): List<User>

    @Delete
    suspend fun delete(user: User)

    // Delete all users
    @Query("DELETE FROM user_table")
    suspend fun deleteAllUsers()

}
