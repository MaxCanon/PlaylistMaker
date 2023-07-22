package com.example.playlistmaker.data.mapper

import com.example.playlistmaker.data.dto.TrackDto
import com.example.playlistmaker.domain.models.Track

class MusicTrackMapper {
    fun mapToDto(musicTrack: Track): TrackDto {
        return TrackDto(
            trackName =musicTrack.trackName ,
            artistName =musicTrack.artistName,
            trackTimeMillis = musicTrack.trackTimeMillis,
            artworkUrl100 = musicTrack.artworkUrl100,
            trackId =musicTrack.trackId,
            collectionName = musicTrack.collectionName,
            releaseDate = musicTrack.releaseDate,
            primaryGenreName = musicTrack.primaryGenreName,
            country = musicTrack.country,
            previewUrl = musicTrack.previewUrl
        )
    }

    fun mapFromDto(musicTrackDto: TrackDto): Track {
        return Track(
            trackName =musicTrackDto.trackName ,
            artistName=musicTrackDto.artistName,
            trackTimeMillis = musicTrackDto.trackTimeMillis,
            artworkUrl100 = musicTrackDto.artworkUrl100,
            trackId =musicTrackDto.trackId,
            collectionName = musicTrackDto.collectionName,
            releaseDate = musicTrackDto.releaseDate,
            primaryGenreName = musicTrackDto.primaryGenreName,
            country = musicTrackDto.country,
            previewUrl = musicTrackDto.previewUrl
        )
    }
}