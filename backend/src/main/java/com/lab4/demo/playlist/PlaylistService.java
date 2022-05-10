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
                .orElseThrow(() -> new EntityNotFoundException("Book not found: " + id));
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
        Playlist playlist = findById(id);
        playlist.setTitle(playlistDTO.getTitle());
        playlist.setTracks(playlistDTO.getTracks());
        playlist.setDuration();
        return playlistMapper.toDTO(
                playlistRepository.save(playlist));
    }

    public void delete(Long id) {
        playlistRepository.deleteById(id);
    }
}
