package com.example.jetweatherforecast.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.jetweatherforecast.model.Favourite
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavourite(favourite: Favourite)

    @Delete
    suspend fun deleteFavourite(favourite: Favourite)

    @Query("Select * from fav_tbl")
    fun getAllFavourites(): Flow<List<Favourite>>

    @Query("Delete from fav_tbl")
    suspend fun deleteAllFavourites()

    @Query("Select * from fav_tbl where city=:city")
    suspend fun getFavouriteByCity(city:String):Favourite
}