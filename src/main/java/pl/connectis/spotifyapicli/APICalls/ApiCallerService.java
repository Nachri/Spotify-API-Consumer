package pl.connectis.spotifyapicli.APICalls;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.connectis.spotifyapicli.authorization.TokenService;

@Service
public class ApiCallerService {

    private final RestTemplate restTemplate;
    private final HttpHeaders httpHeaders;
    private final TokenService tokenService;

    public ApiCallerService(RestTemplate restTemplate, TokenService tokenService) {
        this.restTemplate = restTemplate;
        this.tokenService = tokenService;
        this.httpHeaders = new HttpHeaders();
        this.httpHeaders.setBearerAuth(this.tokenService.getToken().getAccessToken());
    }

    public AlbumsApiCall getAlbumApiCaller() {
        return new AlbumsApiCall(restTemplate, httpHeaders);
    }

    public ArtistsApiCall getArtistApiCaller() {
        return new ArtistsApiCall(restTemplate, httpHeaders);
    }

    public TracksApiCall getTracksApiCaller() {
        return new TracksApiCall(restTemplate, httpHeaders);
    }


}
