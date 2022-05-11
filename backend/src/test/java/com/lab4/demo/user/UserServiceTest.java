package com.lab4.demo.user;
import com.lab4.demo.playlist.PlaylistMapper;
import com.lab4.demo.playlist.model.Playlist;
import com.lab4.demo.playlist.model.dto.PlaylistDTO;
import com.lab4.demo.report.ReportServiceFactory;
import com.lab4.demo.track.TrackMapper;
import com.lab4.demo.track.model.Track;
import com.lab4.demo.track.model.TrackDTO;
import com.lab4.demo.user.dto.UserListDTO;
import com.lab4.demo.user.mapper.UserMapper;
import com.lab4.demo.user.model.ERole;
import com.lab4.demo.user.model.Role;
import com.lab4.demo.user.model.User;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static com.lab4.demo.user.model.ERole.EMPLOYEE;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private TrackMapper trackMapper;

    @Mock
    private PlaylistMapper playlistMapper;

    @Mock
    private ReportServiceFactory reportServiceFactory;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    RoleRepository roleRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService(userRepository,roleRepository, userMapper,passwordEncoder,trackMapper,playlistMapper);
    }

    @Test
    void createTest() {
     UserListDTO userDTO = UserListDTO.builder()
                .id(1L)
                .name("test")
                .password("test")
                .roles(new HashSet<>(Set.of(EMPLOYEE.name())))
                .email("email@yahoo.com")
                .build();
        when(roleRepository.findByName(EMPLOYEE)).thenReturn(Optional.of(Role.builder().name(ERole.EMPLOYEE).build()));
        when(userService.createUser(userDTO)).thenReturn(userDTO);
        UserListDTO createdUser = userService.createUser(userDTO);
        Assertions.assertEquals(createdUser,userDTO);
    }

    @SneakyThrows
    @Test
    void updateTest() {
        Role defaultRole = Role.builder().id(358).name(ERole.EMPLOYEE).build();
        User user = User.builder()
                .id(1L)
                .username("doro")
                .email("doro@yahoo.com")
                .password("password1")
                .roles(Set.of(defaultRole))
                .build();
        UserListDTO userDTO = UserListDTO.builder()
                .id(1L)
                .name("doro")
                .email("doro@yahoo.com")
                .password("password1")
                .roles(Set.of(EMPLOYEE.name()))
                .build();

        when(roleRepository.findByName(EMPLOYEE)).thenReturn(Optional.of(Role.builder().name(ERole.EMPLOYEE).build()));
        when(userMapper.userListDtoFromUser(userRepository.save(userMapper.userfromUserListDto(userDTO)))).thenReturn(userDTO);

        UserListDTO createdUser = userService.createUser(userDTO);
        createdUser.setName("new username");

        when(userRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(user));
        when(userMapper.userListDtoFromUser(userRepository.save(userMapper.userfromUserListDto(userDTO)))).thenReturn(userDTO);

        UserListDTO editedUser = userService.updateUser(createdUser.getId(),createdUser);
        Assertions.assertNotEquals("doro" ,editedUser.getName());
    }

    @Test
    void delete(){
        UserListDTO userDTO = UserListDTO.builder()
                .id(1L)
                .name("test")
                .password("test")
                .roles(new HashSet<>(Set.of(EMPLOYEE.name())))
                .email("email@yahoo.com")
                .build();
        userRepository.save(userMapper.userfromUserListDto(userDTO));
        userService.deleteUser(userDTO.getId());
        Assertions.assertEquals(0,userRepository.findAll().size());
    }

    @Test
    void buyTrack(){
        UserListDTO userListDTO = UserListDTO.builder()
                .id(1L)
                .name("test")
                .password("test")
                .roles(new HashSet<>(Set.of(EMPLOYEE.name())))
                .email("email@yahoo.com")
                .purchasedTracks(new ArrayList<>())
                .playlistList(new ArrayList<>())
                .build();

        Role defaultRole = Role.builder().id(358).name(ERole.EMPLOYEE).build();
        User user = User.builder()
                .id(1L)
                .username("doro")
                .email("doro@yahoo.com")
                .password("password1")
                .roles(Set.of(defaultRole))
                .purchasedTracks(new HashSet<>())
                .playlistList(new ArrayList<>())
                .build();

        TrackDTO trackDTO = TrackDTO.builder()
                .id(1L)
                .title("A track")
                .album("An album")
                .artist("an artist")
                .duration(100)
                .explicit_lyrics(true)
                .link("no")
                .preview("no")
                .build();

        Track track = Track.builder()
                .id(1L)
                .title("A track")
                .album("An album")
                .artist("an artist")
                .duration(100)
                .explicit_lyrics(true)
                .link("no")
                .preview("no")
                .build();

        when(userMapper.userfromUserListDto(userListDTO)).thenReturn(user);
        when(trackMapper.fromDto(trackDTO)).thenReturn(track);
        when(userMapper.userListDtoFromUser(userRepository.save(user))).thenReturn(userListDTO);

        List<Track> tracks = userListDTO.getPurchasedTracks();
        tracks.add(track);
        userListDTO.setPurchasedTracks(tracks);

        userService.buyTrack(userListDTO,trackDTO);

        Assertions.assertEquals(track,userListDTO.getPurchasedTracks().get(0));

    }

    @Test
    void deletePlaylist(){
        Playlist playlist = Playlist.builder()
                .id(1L)
                .title("Playlist")
                .tracks(new ArrayList<>())
                .build();

        PlaylistDTO playlistDTO = PlaylistDTO.builder()
                .id(1L)
                .title("Playlist")
                .tracks(new ArrayList<>())
                .build();

        List<Playlist> playlists = new ArrayList<>();
        playlists.add(playlist);

        UserListDTO userListDTO = UserListDTO.builder()
                .id(1L)
                .name("test")
                .password("test")
                .roles(new HashSet<>(Set.of(EMPLOYEE.name())))
                .email("email@yahoo.com")
                .purchasedTracks(new ArrayList<>())
                .playlistList(playlists)
                .build();

        Role defaultRole = Role.builder().id(358).name(ERole.EMPLOYEE).build();
        User user = User.builder()
                .id(1L)
                .username("doro")
                .email("doro@yahoo.com")
                .password("password1")
                .roles(Set.of(defaultRole))
                .purchasedTracks(new HashSet<>())
                .playlistList(playlists)
                .build();


        when(userMapper.userfromUserListDto(userListDTO)).thenReturn(user);
        when(playlistMapper.fromDTO(playlistDTO)).thenReturn(playlist);
        when(userMapper.userListDtoFromUser(userRepository.save(user))).thenReturn(userListDTO);


        userListDTO.setPlaylistList(new ArrayList<>());

        userService.deletePlaylist(userListDTO,playlistDTO);

        Assertions.assertEquals(0,userListDTO.getPlaylistList().size());
    }

    @Test
    void createPlaylist(){
        UserListDTO userListDTO = UserListDTO.builder()
                .id(1L)
                .name("test")
                .password("test")
                .roles(new HashSet<>(Set.of(EMPLOYEE.name())))
                .email("email@yahoo.com")
                .purchasedTracks(new ArrayList<>())
                .playlistList(new ArrayList<>())
                .build();

        Role defaultRole = Role.builder().id(358).name(ERole.EMPLOYEE).build();
        User user = User.builder()
                .id(1L)
                .username("doro")
                .email("doro@yahoo.com")
                .password("password1")
                .roles(Set.of(defaultRole))
                .purchasedTracks(new HashSet<>())
                .playlistList(new ArrayList<>())
                .build();

        Playlist playlist = Playlist.builder()
                .id(1L)
                .title("Playlist")
                .tracks(new ArrayList<>())
                .build();

        PlaylistDTO playlistDTO = PlaylistDTO.builder()
                .id(1L)
                .title("Playlist")
                .tracks(new ArrayList<>())
                .build();

        when(userMapper.userfromUserListDto(userListDTO)).thenReturn(user);
        when(playlistMapper.fromDTO(playlistDTO)).thenReturn(playlist);
        when(userMapper.userListDtoFromUser(userRepository.save(user))).thenReturn(userListDTO);

        List<Playlist> playlists = userListDTO.getPlaylistList();
        playlists.add(playlist);
        userListDTO.setPlaylistList(playlists);

        userService.createPlaylist(userListDTO,playlistDTO);

        Assertions.assertEquals(playlist,userListDTO.getPlaylistList().get(0));
    }
}
