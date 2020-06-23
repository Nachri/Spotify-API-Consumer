package pl.connectis.spotifyapicli.APICalls;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import pl.connectis.spotifyapicli.authorization.AuthorizationStrategy;
import pl.connectis.spotifyapicli.dto.Album;
import pl.connectis.spotifyapicli.dto.AlbumList;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class AlbumsAPICallTest {

    @Qualifier("clientCredentialsAuthorization")
    @Autowired
    private AuthorizationStrategy authorization;
    @Autowired
    private ApiCallerService apiCallerService;


    private String ids1 = "41MnTivkwTO3UUJ8DrqEJJ";
    private String ids2 = "41MnTivkwTO3UUJ8DrqEJJ,6JWc4iAiJ9FjyK0B59ABb4,6UXCm6bOO4gFlDQZV5yL37";


    @Test
    public void TestIfSpecifiedByIDAlbumGetsReturned() {
        final HttpHeaders httpHeaders = new HttpHeaders();
        authorization.authorize();
        final AlbumsApiCall albumsApiCall = apiCallerService.getAlbumApiCaller();
        Album album = albumsApiCall.getList(new ParameterizedTypeReference<AlbumList>() {
        }, ids1).getAlbums().get(0);
        assertEquals(ids1, album.getId());
    }

}
