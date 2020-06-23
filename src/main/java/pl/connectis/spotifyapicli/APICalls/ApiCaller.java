package pl.connectis.spotifyapicli.APICalls;

import org.springframework.core.ParameterizedTypeReference;

public interface ApiCaller<T> {
    T getList(ParameterizedTypeReference<T> parameterizedTypeReference, String ids);
}
