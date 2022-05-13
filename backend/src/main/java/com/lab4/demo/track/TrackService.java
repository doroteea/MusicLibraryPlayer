package com.lab4.demo.track;

import com.lab4.demo.book.BookMapper;
import com.lab4.demo.book.model.dto.BookDTO;
import com.lab4.demo.track.model.Track;
import com.lab4.demo.track.model.TrackDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TrackService {
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

}
