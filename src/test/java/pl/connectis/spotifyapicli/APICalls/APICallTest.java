package pl.connectis.spotifyapicli.APICalls;


import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import pl.connectis.spotifyapicli.authorization.AuthorizationStrategy;
import pl.connectis.spotifyapicli.dto.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class APICallTest {

    @Qualifier("clientCredentialsAuthorization")
    @Autowired
    private static AuthorizationStrategy authorization;
    @Autowired
    private ApiCallerService apiCallerService;

    @BeforeAll
    public static void setUp() {
        authorization.authorize();
    }

    @Test
    public void TestIfSpecifiedByIDAlbumGetsReturned() {

        final AlbumsApiCall albumsApiCall = apiCallerService.getAlbumApiCaller();
        String albumID = "41MnTivkwTO3UUJ8DrqEJJ";

        Album album = albumsApiCall.getList(new ParameterizedTypeReference<AlbumList>() {
        }, albumID).getAlbums().get(0);

        assertEquals(albumID, album.getId());
    }

    @Test
    public void TestIfSpecifiedByIDArtistGetsReturned() {

        final ArtistsApiCall artistsApiCall = apiCallerService.getArtistApiCaller();
        String artistID = "0OdUWJ0sBjDrqHygGUXeCF";

        Artist artist = artistsApiCall.getList(new ParameterizedTypeReference<ArtistList>() {
        }, artistID).getArtists().get(0);

        assertEquals(artistID, artist.getId());
    }

    @Test
    public void TestIfSpecifiedByIDTrackGetsReturned() {

        final TracksApiCall tracksApiCall = apiCallerService.getTracksApiCaller();
        String trackID = "11dFghVXANMlKmJXsNCbNl";

        Track track = tracksApiCall.getList(new ParameterizedTypeReference<TrackList>() {
        }, trackID).getTracks().get(0);

        assertEquals(trackID, track.getId());
    }

}
