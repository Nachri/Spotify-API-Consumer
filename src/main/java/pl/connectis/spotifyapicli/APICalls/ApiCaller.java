package pl.connectis.spotifyapicli.APICalls;

import org.springframework.core.ParameterizedTypeReference;

import java.util.List;
import java.util.Map;

public interface ApiCaller<T> {
    List<T> getList(ParameterizedTypeReference<Map<String, List<T>>> parameterizedTypeReference, String ids);
}
