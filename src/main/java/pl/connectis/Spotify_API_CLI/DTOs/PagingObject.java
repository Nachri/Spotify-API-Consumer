package pl.connectis.Spotify_API_CLI.DTOs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class PagingObject<T> {
    private String href;
    private T items;
    private Integer limit;
    private String next;
    private Integer offset;
    private String previous;
    private Integer total;
}
