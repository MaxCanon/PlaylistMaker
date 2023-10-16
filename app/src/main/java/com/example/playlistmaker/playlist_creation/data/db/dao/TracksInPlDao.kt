package com.example.playlistmaker.playlist_creation.data.db.dao

import androidx.room.*
import com.example.playlistmaker.playlist_creation.data.db.entity.TrackInPlEntity

@Dao
interface TracksInPlaylistDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTrackToPlaylist(track: TrackInPlEntity)

    @Query("DELETE FROM tracks_in_pl_table WHERE trackId = :trackId")
    suspend fun deleteTrack(trackId: Int)

    @Query("SELECT * FROM tracks_in_pl_table WHERE trackId in (:tracksIds)")
    suspend fun getTracks(tracksIds: List<Int>): List<TrackInPlEntity>
}