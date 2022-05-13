package com.lab4.demo.playlist;

import com.lab4.demo.playlist.model.Playlist;
import com.lab4.demo.playlist.model.dto.PlaylistDTO;
import com.lab4.demo.track.TrackMapper;
import com.lab4.demo.track.TrackService;
import com.lab4.demo.track.model.Track;
import com.lab4.demo.track.model.TrackDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlaylistService {

    private final PlaylistRepository playlistRepository;
    private final PlaylistMapper playlistMapper;
    private final TrackService trackService;
    private final TrackMapper trackMapper;

    public Playlist findById(Long id) {
        return playlistRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Playlist not found: " + id));
    }

    public List<PlaylistDTO> findAll() {
        return playlistRepository.findAll().stream()
                .map(playlistMapper::toDTO)
                .collect(Collectors.toList());
    }

    public PlaylistDTO create(PlaylistDTO playlistDTO) {
        playlistDTO.setDuration();

        return playlistMapper.toDTO(playlistRepository.save(
                playlistMapper.fromDTO(playlistDTO)
        ));
    }

    public PlaylistDTO update(Long id, PlaylistDTO playlistDTO) {
        Playlist actPlaylist = findById(id);
        actPlaylist.setTitle(playlistDTO.getTitle());
        actPlaylist.setTracks(playlistDTO.getTracks().stream().map(trackMapper::fromDto).collect(Collectors.toList()));
        actPlaylist.setDuration();
        return playlistMapper.toDTO(
                playlistRepository.save(actPlaylist)
        );
    }

    public void delete(Long id) {
        playlistRepository.deleteById(id);
    }

    public List<TrackDTO> viewTracksFromPlaylist(Long playlist_id){
        Playlist actPlaylist = findById(playlist_id);
        return actPlaylist.
                getTracks()
                .stream()
                .map(trackMapper::toDto)
                .collect(Collectors.toList());

    }

    public PlaylistDTO addTrackInPlaylist(Long playlist_id,TrackDTO trackDTO){
        Playlist actPlaylist = findById(playlist_id);
        TrackDTO trackDTO1 = trackService.create(trackDTO);
        actPlaylist.getTracks().add(trackMapper.fromDto(trackDTO1));
        return playlistMapper.toDTO(playlistRepository.save(actPlaylist));
    }

    public PlaylistDTO editTrackInPlaylist(Long playlist_id,TrackDTO trackDTO){
        Playlist actPlaylist = findById(playlist_id);
        trackService.edit(trackDTO.getId(),trackDTO);
        actPlaylist.setDuration();
        return playlistMapper.toDTO(playlistRepository.save(actPlaylist));
    }

    public PlaylistDTO deleteTrackFromPlaylist(Long playlist_id,Long track_id){
        Playlist actPlaylist = findById(playlist_id);
        List<Track> actPlaylistTracks = actPlaylist.getTracks();
        int id = 0;
        for (Track track: actPlaylistTracks){
            if(track.getId().equals(track_id)){
                break;
            }
            id++;
        }
        actPlaylist.getTracks().remove(id);
        trackService.delete(track_id);
        return playlistMapper.toDTO(
                playlistRepository.save(actPlaylist)
        );
    }

    public PlaylistDTO getPlaylist(Long id){
        return playlistMapper.toDTO(findById(id));
    }


}
