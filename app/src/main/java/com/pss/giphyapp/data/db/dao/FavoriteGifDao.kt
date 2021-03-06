package com.pss.giphyapp.data.db.dao

import androidx.room.*
import com.pss.giphyapp.data.db.entity.FavoriteGif

@Dao
interface FavoriteGifDao {
    @Insert
    fun favoriteGifInsert(favoriteGif: FavoriteGif)

    @Query("DELETE FROM FavoriteGif WHERE gifId = :gifId")
    fun favoriteGifDelete(gifId : String)

    @Query("SELECT * FROM FavoriteGif WHERE gifId = :gifId")
    fun favoriteGifSelect(gifId : String) : List<FavoriteGif>

    @Query("SELECT * FROM FavoriteGif")
    fun favoriteGifAllSelectAndGet() : List<FavoriteGif>
}