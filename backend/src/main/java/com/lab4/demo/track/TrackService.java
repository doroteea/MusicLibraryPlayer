package com.lab4.demo.track;

import com.lab4.demo.book.BookMapper;
import com.lab4.demo.track.model.TrackDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TrackService {
    private final TrackRepository trackRepository;
    private final TrackMapper trackMapper;

    public List<TrackDTO> getAllTracks() {
        return trackRepository
                .findAll()
                .stream()
                .map(trackMapper::toDto)
                .collect(Collectors.toList());
    }
}
