package pl.connectis.spotifyapicli;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.cli.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import pl.connectis.spotifyapicli.APICalls.AlbumsApiCall;
import pl.connectis.spotifyapicli.APICalls.ArtistsApiCall;
import pl.connectis.spotifyapicli.APICalls.TracksApiCall;
import pl.connectis.spotifyapicli.authorization.AuthorizationStrategy;
import pl.connectis.spotifyapicli.authorization.TokenService;
import pl.connectis.spotifyapicli.dto.Album;
import pl.connectis.spotifyapicli.dto.Artist;
import pl.connectis.spotifyapicli.dto.Track;

import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
@Profile({"!test"})

public class SpotifyAPICLR implements CommandLineRunner {

    private final AuthorizationStrategy authorization;
    private final RestTemplate restTemplate;
    private final HttpHeaders httpHeaders;


    public SpotifyAPICLR(AuthorizationStrategy authorization, RestTemplate restTemplate, TokenService tokenService) {
        this.authorization = authorization;
        this.restTemplate = restTemplate;
        this.httpHeaders = new HttpHeaders();
        this.httpHeaders.setBearerAuth(tokenService.getToken().getAccessToken());
    }

    public void run(String... args) {

        log.info("args: {}", Arrays.toString(args));

        Options options = buildOptions();
        CommandLineParser CLIParser = new DefaultParser();
        CommandLine cmd;
        try {
            cmd = CLIParser.parse(options, args);
        } catch (ParseException ex) {
            log.error("Parse error: {}", ex.getMessage(), ex);
            return;
        }


        log.info("Parsed args: {}", Arrays.toString(cmd.getArgs()));

        Option option = cmd.getOptions()[0];
        if (cmd.hasOption("a")) {
            authorization.authorize();
            log.info("Authorization done. Token generated.");
        } else if (cmd.hasOption("ab")) {
            String ids = cmd.getOptionValue(option.getOpt());
            log.info("Parsed id: {}", ids);
            AlbumsApiCall albumsApiCall = new AlbumsApiCall(restTemplate, httpHeaders);
            if (cmd.getArgs().length > 0) {
                log.info("Found args");
                if (cmd.getArgs()[0].equals("tracks")) {
                    log.info("{}", albumsApiCall.getAlbumTracks(ids));
                }
                log.info("Parsed ids: {}", ids);
            } else {
                log.info("{}", albumsApiCall.getList(new ParameterizedTypeReference<Map<String, List<Album>>>() {
                }, ids));
            }
        } else if (cmd.hasOption("at")) {
            String ids = cmd.getOptionValue(option.getOpt());
            log.info("Parsed id: {}", ids);
            ArtistsApiCall artistsApiCall = new ArtistsApiCall(restTemplate, httpHeaders);
            if (cmd.getArgs().length > 0) {
                switch (cmd.getArgs()[0]) {
                    case "albums":
                        log.info((artistsApiCall.getArtistAlbums(ids).toString()));
                        break;
                    case "top-tracks":
                        String countryCode = cmd.getArgs()[1];
                        log.info("{}", (artistsApiCall.getArtistTracks(ids, countryCode)));
                        break;
                    case "related-artists":
                        log.info("{}", (artistsApiCall.getArtistRelatedArtists(ids)));
                        break;
                }
            } else {
                log.info("{}", artistsApiCall.getList(new ParameterizedTypeReference<Map<String, List<Artist>>>() {
                }, ids));
            }
        } else if (cmd.hasOption("tr")) {
            String ids = cmd.getOptionValue(option.getOpt());
            log.info("Parsed ids: {}", ids);
            log.info("{}", new TracksApiCall(restTemplate, httpHeaders).getList(new ParameterizedTypeReference<Map<String, List<Track>>>() {
            }, ids));
        } else {
            throw new InvalidParameterException("Wrong argument provided.");
        }
        System.exit(0); //for servlet application type
    }

    private Options buildOptions() {
        final Options options = new Options();
        options.addOption("a", "authorize", false, "Authorize with Spotify API");
        options.addOption("tr", "track", true, "Get track/s info. Examples: -tr 11dFghVXANMlKmJXsNCb ," +
                "--track 11dFghVXANMlKmJXsNCb,20I6sIOMTCkB6w7ryavxtO");
        options.addOption("ab", "album", true, "Get album/s info. Optional args: tracks Examples: -ab 41MnTivkwTO3UUJ8DrqEJJ ," +
                "--album 41MnTivkwTO3UUJ8DrqEJJ,6JWc4iAiJ9FjyK0B59ABb4,6UXCm6bOO4gFlDQZV5yL37");
        options.addOption("at", "artist", true, "Get artist/s info. Optional args: albums, top-tracks, related-artists  Examples: -at 0OdUWJ0sBjDrqHygGUXeCF, " +
                "--artist 0oSGxfWSnnOXhD2fKuz2Gy,3dBVyJ7JuOMt4GE9607Qin");
        return options;
    }


}
