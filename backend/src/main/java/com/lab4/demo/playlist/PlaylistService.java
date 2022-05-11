package com.lab4.demo.playlist;

import com.lab4.demo.playlist.model.Playlist;
import com.lab4.demo.playlist.model.dto.PlaylistDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlaylistService {

    private final PlaylistRepository playlistRepository;
    private final PlaylistMapper playlistMapper;

    private Playlist findById(Long id) {
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
        actPlaylist.setTracks(playlistDTO.getTracks());
        actPlaylist.setDuration();
        return playlistMapper.toDTO(
                playlistRepository.save(actPlaylist)
        );
    }

    public void delete(Long id) {
        playlistRepository.deleteById(id);
    }
}
