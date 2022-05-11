package com.lab4.demo.track;

import com.lab4.demo.track.model.Track;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrackRepository extends JpaRepository<Track, Long> {
    Page<Track> findAllByTitleLikeOrArtistLikeOrAlbumLike(String title, String artist, String album, Pageable pageable);

}
