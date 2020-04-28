package pl.connectis.spotifyapicli.APICalls;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;
import pl.connectis.spotifyapicli.dto.Track;

@Slf4j
public class TracksApiCall extends BaseApiCaller<Track> implements ApiCaller {

    public TracksApiCall(RestTemplate restTemplate, HttpHeaders httpHeaders) {
        super(restTemplate, httpHeaders, Track.class, "/tracks/{id}", "/tracks?ids={ids}");
    }
}

