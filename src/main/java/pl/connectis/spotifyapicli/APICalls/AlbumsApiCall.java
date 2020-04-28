package pl.connectis.spotifyapicli.APICalls;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import pl.connectis.spotifyapicli.dto.Album;
import pl.connectis.spotifyapicli.dto.PagingObject;
import pl.connectis.spotifyapicli.dto.Track;

@Slf4j
public class AlbumsApiCall extends BaseApiCaller<Album> implements ApiCaller {

    private final RestTemplate restTemplate;
    private final HttpHeaders httpHeaders;

    public AlbumsApiCall(RestTemplate restTemplate, HttpHeaders httpHeaders) {
        super(restTemplate, httpHeaders, Album.class, "/albums/{id}", "/albums?ids={ids}");
        this.restTemplate = restTemplate;
        this.httpHeaders = httpHeaders;
    }


    public PagingObject<Track> getManyTracksFromOneAlbum(String id) {
        String uriManyTracksFromOneAlbum = "https://api.spotify.com/v1/albums/{id}/tracks";
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity<PagingObject<Track>> response = restTemplate.exchange(
                uriManyTracksFromOneAlbum,
                HttpMethod.GET,
                httpEntity,
                new ParameterizedTypeReference<PagingObject<Track>>() {
                }, id);
        return response.getStatusCode() == HttpStatus.OK ? response.getBody() : null;
    }
}
