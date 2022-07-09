package database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CartDao {
    @Query("SELECT * FROM CartModel")
    fun fetchAll():LiveData<List<CartModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg item:CartModel)

    //delete a task
    @Query("DELETE FROM CartModel WHERE UId=:uid")
    suspend fun deteleTask(uid: Int?)

    //delete all
    @Query("DELETE FROM CartModel")
    fun deleteAll()

    //update quantity
    @Query("UPDATE CartModel SET quantity=:quant WHERE UId = :uid")
    suspend fun updatequantity(quant: Int, uid: Int?)


}