package com.lab4.demo.playlist;

import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.playlist.model.Playlist;
import com.lab4.demo.playlist.model.dto.PlaylistDTO;
import com.lab4.demo.track.TrackMapper;
import com.lab4.demo.track.TrackService;
import com.lab4.demo.track.model.Track;
import com.lab4.demo.track.model.TrackDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.lab4.demo.UrlMapping.TRACKS;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PlaylistServiceTest {

    @InjectMocks
    private PlaylistService playlistService;

    @Mock
    private PlaylistMapper playlistMapper;

    @Mock
    private TrackMapper trackMapper;

    @Mock
    private PlaylistRepository  playlistRepository;

    @Mock
    private TrackService trackService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        playlistService = new PlaylistService(playlistRepository,playlistMapper,trackService,trackMapper);
    }

    @Test
    void findAll() {
        List<Playlist> playlists = TestCreationFactory.listOf(Playlist.class);
        when(playlistRepository.findAll()).thenReturn(playlists);

        List<PlaylistDTO> all = playlistService.findAll();

        Assertions.assertEquals(playlists.size(), all.size());
    }

    @Test
    void create() {
        PlaylistDTO playlistDTO = PlaylistDTO.builder()
                .id(1L)
                .title("Playlist")
                .tracks(new ArrayList<>())
                .duration(100)
                .build();

        when(playlistMapper.toDTO(playlistRepository.save(playlistMapper.fromDTO(playlistDTO)))).thenReturn(playlistDTO);

        PlaylistDTO playlistDTO1 = playlistService.create(playlistDTO);

        Assertions.assertEquals(playlistDTO,playlistDTO1);
    }

    @Test
    void update() {
        PlaylistDTO playlistDTO = PlaylistDTO.builder()
                .id(1L)
                .title("Playlist")
                .tracks(new ArrayList<>())
                .duration(100)
                .build();

        Playlist playlist = Playlist.builder()
                .id(1L)
                .title("Playlist")
                .tracks(new ArrayList<>())
                .duration(100)
                .build();

        when(playlistRepository.findById(playlist.getId())).thenReturn(Optional.of(playlist));
        when(playlistMapper.toDTO(playlistRepository.save(playlist))).thenReturn(playlistDTO);


        PlaylistDTO newPlaylist = playlistService.update(playlist.getId(), playlistDTO);
        Assertions.assertEquals(playlistDTO,newPlaylist);
    }

    @Test
    void delete() {
        Playlist playlist = Playlist.builder()
                .id(1L)
                .title("Playlist")
                .tracks(new ArrayList<>())
                .duration(100)
                .build();

        when(playlistRepository.save(playlist)).thenReturn(playlist);
        when(playlistRepository.existsById(playlist.getId())).thenReturn(false);

        playlistService.delete(playlist.getId());
        Assertions.assertFalse(playlistRepository.existsById(playlist.getId()));
    }

    @Test
    void addTrack(){
        Playlist playlist = Playlist.builder()
                .id(1L)
                .title("Playlist")
                .tracks(new ArrayList<>())
                .duration(100)
                .build();
        PlaylistDTO playlistDTO = PlaylistDTO.builder()
                .id(1L)
                .title("Playlist")
                .tracks(new ArrayList<>())
                .duration(100)
                .build();
        when(playlistMapper.toDTO(playlistRepository.save(playlistMapper.fromDTO(playlistDTO)))).thenReturn(playlistDTO);

        PlaylistDTO playlistDTO1 = playlistService.create(playlistDTO);
        System.out.println(playlistDTO1.getTitle());

        TrackDTO track1 = TrackDTO.builder()
                .title("title song1")
                .link("link1")
                .preview("preview1")
                .duration(122)
                .explicit_lyrics(true)
                .artist("name1")
                .album("title album1")
                .build();
        Track track = Track.builder()
                .title("title song1")
                .link("link1")
                .preview("preview1")
                .duration(122)
                .explicit_lyrics(true)
                .artist("name1")
                .album("title album1")
                .build();
        playlistDTO1.getTracks().add(track1);

        when(playlistRepository.findById(playlistDTO.getId())).thenReturn(Optional.ofNullable(playlist));
        when(trackService.create(track1)).thenReturn(track1);
        when(playlistMapper.toDTO(playlistRepository.save(playlistMapper.fromDTO(playlistDTO)))).thenReturn(playlistDTO);

        PlaylistDTO playlistDTO2 = playlistService.addTrackInPlaylist(playlistDTO1.getId(),track1);

        System.out.println(playlistDTO2.getTracks().get(0).getTitle());
        Assertions.assertEquals(playlistDTO2.getTracks().get(0).getTitle(), track1.getTitle());

    }

    @Test
    void editTrackInPlaylist(){
        Playlist playlist = Playlist.builder()
                .id(1L)
                .title("Playlist")
                .tracks(new ArrayList<>())
                .duration(100)
                .build();
        PlaylistDTO playlistDTO = PlaylistDTO.builder()
                .id(1L)
                .title("Playlist")
                .tracks(new ArrayList<>())
                .duration(100)
                .build();
        when(playlistMapper.toDTO(playlistRepository.save(playlistMapper.fromDTO(playlistDTO)))).thenReturn(playlistDTO);
        PlaylistDTO playlistDTO1 = playlistService.create(playlistDTO);

        TrackDTO track1 = TrackDTO.builder()
                .id(1L)
                .title("title song1")
                .link("link1")
                .preview("preview1")
                .duration(122)
                .explicit_lyrics(true)
                .artist("name1")
                .album("title album1")
                .build();
        Track track = Track.builder()
                .id(1L)
                .title("title song1")
                .link("link1")
                .preview("preview1")
                .duration(122)
                .explicit_lyrics(true)
                .artist("name1")
                .album("title album1")
                .build();
        playlistDTO1.getTracks().add(track1);
        when(playlistRepository.findById(playlistDTO.getId())).thenReturn(Optional.of(playlist));
        when(trackService.create(track1)).thenReturn(track1);
        when(playlistMapper.toDTO(playlistRepository.save(playlistMapper.fromDTO(playlistDTO)))).thenReturn(playlistDTO);
        PlaylistDTO playlistDTO2 = playlistService.addTrackInPlaylist(playlistDTO1.getId(),track1);

        System.out.println(playlistDTO1.getTracks().get(0).getArtist());
        track1.setTitle("vue");
        track.setTitle("vue");
        playlist.getTracks().add(track);

        when(playlistRepository.findById(playlistDTO.getId())).thenReturn(Optional.of(playlist));
        when(playlistMapper.toDTO(playlistRepository.save(playlistMapper.fromDTO(playlistDTO)))).thenReturn(playlistDTO);
        when(trackService.create(track1)).thenReturn(track1);
        when(playlistService.editTrackInPlaylist(playlistDTO2.getId(),track1)).thenReturn(playlistDTO2);
        PlaylistDTO playlistDTO3 = playlistService.editTrackInPlaylist(playlistDTO2.getId(),track1);

        System.out.println(playlistDTO3.getTracks().get(0).getTitle());
        Assertions.assertEquals(playlistDTO3.getTracks().get(0).getTitle(), track1.getTitle());

    }

    @Test
    void deleteTrackInPlaylist(){
        Playlist playlist = Playlist.builder()
                .id(1L)
                .title("Playlist")
                .tracks(new ArrayList<>())
                .duration(100)
                .build();
        PlaylistDTO playlistDTO = PlaylistDTO.builder()
                .id(1L)
                .title("Playlist")
                .tracks(new ArrayList<>())
                .duration(100)
                .build();
        when(playlistMapper.toDTO(playlistRepository.save(playlistMapper.fromDTO(playlistDTO)))).thenReturn(playlistDTO);
        PlaylistDTO playlistDTO1 = playlistService.create(playlistDTO);

        TrackDTO track1 = TrackDTO.builder()
                .id(1L)
                .title("title song1")
                .link("link1")
                .preview("preview1")
                .duration(122)
                .explicit_lyrics(true)
                .artist("name1")
                .album("title album1")
                .build();
        Track track = Track.builder()
                .id(1L)
                .title("title song1")
                .link("link1")
                .preview("preview1")
                .duration(122)
                .explicit_lyrics(true)
                .artist("name1")
                .album("title album1")
                .build();
        playlistDTO1.getTracks().add(track1);
        List<Track> tracks = new ArrayList<>();
        tracks.add(track);
        playlist.setTracks(tracks);
        when(playlistRepository.findById(playlistDTO.getId())).thenReturn(Optional.of(playlist));
        when(trackService.create(track1)).thenReturn(track1);
        when(playlistMapper.toDTO(playlistRepository.save(playlistMapper.fromDTO(playlistDTO)))).thenReturn(playlistDTO);
        PlaylistDTO playlistDTO2 = playlistService.addTrackInPlaylist(playlistDTO1.getId(),track1);

        when(playlistRepository.findById(playlistDTO.getId())).thenReturn(Optional.of(playlist));
        when(playlistMapper.toDTO(playlistRepository.save(playlistMapper.fromDTO(playlistDTO)))).thenReturn(playlistDTO);
        doNothing().when(trackService).delete(track1.getId());
        PlaylistDTO playlistDTO3 = playlistService.deleteTrackFromPlaylist(playlistDTO2.getId(),track.getId());

        System.out.println(playlistDTO3.getTracks().get(0).getTitle());
        Assertions.assertEquals(playlistDTO3.getTracks().get(0).getTitle(), track1.getTitle());

    }
}