package com.lab4.demo.track;

import com.lab4.demo.book.BookMapper;
import com.lab4.demo.book.model.dto.BookDTO;
import com.lab4.demo.playlist.PlaylistRepository;
import com.lab4.demo.playlist.PlaylistService;
import com.lab4.demo.playlist.model.Playlist;
import com.lab4.demo.playlist.model.dto.PlaylistDTO;
import com.lab4.demo.track.model.Track;
import com.lab4.demo.track.model.TrackDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TrackService {
    private final PlaylistRepository playlistRepository;
    private final TrackRepository trackRepository;
    private final TrackMapper trackMapper;

    public List<TrackDTO> findAll() {
        return trackRepository
                .findAll()
                .stream()
                .map(trackMapper::toDto)
                .collect(Collectors.toList());
    }

    public Track getTrackById(Long id) {
        return trackRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Track not found with id: " + id));
    }

    public TrackDTO create(TrackDTO trackDTO) {
        return trackMapper.toDto(trackRepository.save(
                trackMapper.fromDto(trackDTO)
        ));
    }

    public TrackDTO edit(Long id,TrackDTO trackDTO){
        Track track = getTrackById(id);
        track.setTitle(trackDTO.getTitle());
        track.setLink(trackDTO.getLink());
        track.setDuration(trackDTO.getDuration());
        track.setExplicit_lyrics(trackDTO.getExplicit_lyrics());
        track.setPreview(trackDTO.getPreview());
        track.setArtist(trackDTO.getArtist());
        track.setAlbum(trackDTO.getAlbum());
        return trackMapper.toDto(trackRepository.save(track));
    }

    public void delete(Long id) {
        List<Playlist> playlists = playlistRepository.findAll();
        for(Playlist playlist: playlists){
            List<Track> tracks = new ArrayList<>();
            for(Track track: playlist.getTracks()){
                if(!track.getId().equals(id)){
                    tracks.add(track);
                }
            }
            playlist.setTracks(tracks);
            playlistRepository.save(playlist);
        }
        trackRepository.deleteById(id);
    }

    public List<TrackDTO> findAllByFilter(String filter) {
        int page = 0;
        int pageSize = 10;
        PageRequest pageable = PageRequest.of(page, pageSize);
        return trackRepository.findAllByTitleLikeOrArtistLikeOrAlbumLike("%" + filter + "%", "%" + filter + "%", "%" + filter + "%", pageable)
                .stream()
                .map(trackMapper::toDto)
                .collect(Collectors.toList());
        //return books;
    }

    public Optional<Track> findByTitle(String title){
        return trackRepository.findByTitle(title);
    }

}
