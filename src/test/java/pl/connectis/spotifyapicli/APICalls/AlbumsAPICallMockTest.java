package pl.connectis.spotifyapicli.APICalls;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import pl.connectis.spotifyapicli.authorization.TokenService;
import pl.connectis.spotifyapicli.dto.Album;
import pl.connectis.spotifyapicli.dto.AlbumList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RunWith(SpringRunner.class)
@RestClientTest({ApiCallerService.class, TokenService.class})
@AutoConfigureWebClient(registerRestTemplate = true)
@ActiveProfiles("test")
public class AlbumsAPICallMockTest {

    @Autowired
    private MockRestServiceServer server;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ApiCallerService apiCallerService;


    @Before
    public void setUp() throws Exception {

        Album albumMock = new Album();
        albumMock.setId("41MnTivkwTO3UUJ8DrqEJJ");

        String response = objectMapper.writeValueAsString(albumMock);

        this.server.expect(requestTo("/albums?ids=41MnTivkwTO3UUJ8DrqEJJ")).andRespond(withSuccess(response, MediaType.APPLICATION_JSON));
    }

    @Test
    public void givenRestTemplateMocked_WhenGetAlbumsInvoked_ThenMockValueReturned() {

        AlbumList albums = this.apiCallerService.getAlbumApiCaller().getList(new ParameterizedTypeReference<AlbumList>() {
        }, "41MnTivkwTO3UUJ8DrqEJJ");

        assertEquals(albums.getAlbums().get(0).getId(), "41MnTivkwTO3UUJ8DrqEJJ");
    }

}
