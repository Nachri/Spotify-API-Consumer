package pl.connectis.spotifyapicli.APICalls;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import pl.connectis.spotifyapicli.authorization.Token;
import pl.connectis.spotifyapicli.authorization.TokenService;
import pl.connectis.spotifyapicli.dto.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RunWith(SpringRunner.class)
@RestClientTest({ApiCallerService.class})
@AutoConfigureWebClient(registerRestTemplate = true)
@ActiveProfiles("test")
public class APICallMockTest {

    @Autowired
    private MockRestServiceServer server;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ApiCallerService apiCallerService;

    @Test
    public void givenRestServiceMocked_WhenAlbumGetListInvoked_ThenMockValueReturned() throws Exception {

        List<Album> albumsMock = new ArrayList<>();
        AlbumList albumsListMock = new AlbumList();
        Album albumMock = new Album();
        albumMock.setId("mockedAlbumIDUJ8DrqEJJ");
        albumsMock.add(albumMock);
        albumsListMock.setAlbums(albumsMock);

        String response = objectMapper.writeValueAsString(albumsListMock);

        this.server.expect(requestTo("/albums?ids=mockedAlbumIDUJ8DrqEJJ")).andRespond(withSuccess(response, MediaType.APPLICATION_JSON));

        AlbumList albums = this.apiCallerService.getAlbumApiCaller().getList(new ParameterizedTypeReference<AlbumList>() {
        }, "mockedAlbumIDUJ8DrqEJJ");

        assertEquals(albums.getAlbums().get(0).getId(), "mockedAlbumIDUJ8DrqEJJ");
    }

    @Test
    public void givenRestServiceMocked_WhenArtistGetListInvoked_ThenMockValueReturned() throws Exception {

        List<Artist> artistsMock = new ArrayList<>();
        ArtistList artistsListMock = new ArtistList();
        Artist artistMock = new Artist();
        artistMock.setId("mockedArtistIDxhaskw4");
        artistsMock.add(artistMock);
        artistsListMock.setArtists(artistsMock);

        String response = objectMapper.writeValueAsString(artistsListMock);

        this.server.expect(requestTo("/artists?ids=mockedArtistIDxhaskw4")).andRespond(withSuccess(response, MediaType.APPLICATION_JSON));

        ArtistList artists = this.apiCallerService.getArtistApiCaller().getList(new ParameterizedTypeReference<ArtistList>() {
        }, "mockedArtistIDxhaskw4");

        assertEquals(artists.getArtists().get(0).getId(), "mockedArtistIDxhaskw4");
    }

    @Test
    public void givenRestServiceMocked_WhenTrackGetListInvoked_ThenMockValueReturned() throws Exception {

        List<Track> tracksMock = new ArrayList<>();
        TrackList tracksListMock = new TrackList();
        Track trackMock = new Track();
        trackMock.setId("mockedTrackIDxhaskw4");
        tracksMock.add(trackMock);
        tracksListMock.setTracks(tracksMock);

        String response = objectMapper.writeValueAsString(tracksListMock);

        this.server.expect(requestTo("/tracks?ids=mockedTrackIDxhaskw4")).andRespond(withSuccess(response, MediaType.APPLICATION_JSON));

        TrackList tracks = this.apiCallerService.getTracksApiCaller().getList(new ParameterizedTypeReference<TrackList>() {
        }, "mockedTrackIDxhaskw4");

        assertEquals(tracks.getTracks().get(0).getId(), "mockedTrackIDxhaskw4");
    }

    @TestConfiguration
    static class MyMockConfig {
        @Bean
        public TokenService tokenService() {
            TokenService tokenService = mock(TokenService.class);
            Token mockTocken = new Token();
            mockTocken.setAccessToken("mockedAccessToken");
            when(tokenService.getToken()).thenReturn(mockTocken);
            return tokenService;
        }
    }


}
