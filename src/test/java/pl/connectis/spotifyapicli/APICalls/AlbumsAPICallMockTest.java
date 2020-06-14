package pl.connectis.spotifyapicli.APICalls;


import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;
import pl.connectis.spotifyapicli.dto.Album;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@RestClientTest(AlbumsApiCall.class)
@ActiveProfiles("test")
public class AlbumsAPICallMockTest {

    @MockBean
    private RestTemplate restTemplate;
    @MockBean
    private HttpHeaders httpHeaders;
    private final AlbumsApiCall albumsAPICall = new AlbumsApiCall(restTemplate, httpHeaders);

    @Test
    public void givenRestTemplateMocked_WhenGetAlbumsInvoked_ThenMockValueReturned() {
        Album albumMock = new Album();
        albumMock.setId("41MnTivkwTO3UUJ8DrqEJJ");
        ResponseEntity<Album> mockResponse = mock(ResponseEntity.class);
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(HttpEntity.class), eq(Album.class), anyString()))
                .thenReturn(mockResponse);
        when(mockResponse.toString()).thenReturn("MockedResponse");
        when(mockResponse.getStatusCode()).thenReturn(HttpStatus.OK);
        when(mockResponse.getBody()).thenReturn(albumMock);


        List<Album> albums = albumsAPICall.getList(new ParameterizedTypeReference<Map<String, List<Album>>>() {
        }, "41MnTivkwTO3UUJ8DrqEJJ");

        Mockito.verify(mockResponse).getStatusCode();
        Mockito.verify(restTemplate).exchange(anyString(), eq(HttpMethod.GET), any(HttpEntity.class), eq(Album.class), anyString());

        assertEquals(albums.get(0).getId(), "41MnTivkwTO3UUJ8DrqEJJ");
    }

}
