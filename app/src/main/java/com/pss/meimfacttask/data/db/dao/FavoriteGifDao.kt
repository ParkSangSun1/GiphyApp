package com.pss.meimfacttask.data.db.dao

import androidx.room.*
import com.pss.meimfacttask.data.db.entity.FavoriteGif

@Dao
interface FavoriteGifDao {
    @Insert
    fun favoriteGifInsert(favoriteGif: FavoriteGif)

    @Delete
    fun favoriteGifDelete(favoriteGif: FavoriteGif)

    @Query("SELECT * FROM FavoriteGif WHERE gifId = :id")
    fun favoriteGifSelect(id : String) : List<FavoriteGif>
}