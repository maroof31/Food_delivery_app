package database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class CartModel(
    @PrimaryKey(autoGenerate=true) val UId:Int?,
    @ColumnInfo val id:String,
    @ColumnInfo val title:String,
    @ColumnInfo  val price:String,
    @ColumnInfo val link:String,
    @ColumnInfo val quantity:Int,
)