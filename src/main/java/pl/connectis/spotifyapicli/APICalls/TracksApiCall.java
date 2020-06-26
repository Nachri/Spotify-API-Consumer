package pl.connectis.spotifyapicli.APICalls;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;
import pl.connectis.spotifyapicli.dto.TrackList;

@Slf4j
public class TracksApiCall extends BaseApiCaller<TrackList> {

    public static final String URI_TRACKS = "/tracks?ids={ids}";

    public TracksApiCall(RestTemplate restTemplate, HttpHeaders httpHeaders) {
        super(restTemplate, httpHeaders, URI_TRACKS);
    }
}

