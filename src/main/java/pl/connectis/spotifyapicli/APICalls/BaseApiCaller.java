package pl.connectis.spotifyapicli.APICalls;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import pl.connectis.spotifyapicli.dto.PagingObject;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
public abstract class BaseApiCaller<T> implements ApiCaller<T> {

    private final RestTemplate restTemplate;
    private final HttpEntity<MultiValueMap<String, String>> httpEntity;
    private final String uri;

    protected BaseApiCaller(RestTemplate restTemplate, HttpHeaders httpHeaders, String uri) {
        this.restTemplate = restTemplate;
        this.httpEntity = new HttpEntity<>(httpHeaders);
        this.uri = uri;
    }

    public List<T> getList(ParameterizedTypeReference<Map<String, List<T>>> parameterizedTypeReference, String ids) {
        return getMany(uri, parameterizedTypeReference, ids);
    }

    public <S> PagingObject<S> getPage(String url, Object... variables) {
        return Optional.of(restTemplate.exchange(url, HttpMethod.GET, httpEntity, new ParameterizedTypeReference<PagingObject<S>>() {
        }, variables))
                .filter(response -> response.getStatusCode() == HttpStatus.OK)
                .map(ResponseEntity::getBody)
                .orElse(null);
    }

    public <S> List<S> getMany(String url, ParameterizedTypeReference<Map<String, List<S>>> parameterizedTypeReference, Object... variables) {
        return Optional.of(restTemplate.exchange(url, HttpMethod.GET, httpEntity, parameterizedTypeReference, variables))
                .filter(response -> response.getStatusCode() == HttpStatus.OK)
                .map(ResponseEntity::getBody)
                .map(this::extractList)
                .orElse(null);
    }

    private <S> List<S> extractList(Map<String, List<S>> map) {
        return map.values()
                .stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }
}
