package pl.connectis.spotifyapicli.APICalls;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Map;

@Slf4j
public abstract class BaseApiCaller<T> implements ApiCaller {

    private final RestTemplate restTemplate;
    private final HttpHeaders httpHeaders;
    private final Class<T> clazz;
    private final String uriOne;
    private final String uriMany;

    protected BaseApiCaller(RestTemplate restTemplate, HttpHeaders httpHeaders, Class<T> clazz, String uriOne, String uriMany) {
        this.restTemplate = restTemplate;
        this.httpHeaders = httpHeaders;
        this.clazz = clazz;
        this.uriOne = uriOne;
        this.uriMany = uriMany;
    }

    @Override
    public void call(String ids) {
        if (ids.contains(",")) {
            Map<String, T[]> objects = getMany(ids);
            log.info("{} array toString: {}", clazz.getName(), Arrays.toString(objects.values().toArray()));
        } else {
            T object = getOne(ids);
            log.info("{} toString: {}", clazz.getName(), object);
        }
    }

    public T getOne(String id) {
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity<T> response = restTemplate.exchange(
                uriOne,
                HttpMethod.GET,
                httpEntity,
                clazz
                , id);
        return response.getStatusCode() == HttpStatus.OK ? response.getBody() : null;
    }

    public Map<String, T[]> getMany(String ids) {
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity<Map<String, T[]>> response = restTemplate.exchange(
                uriMany,
                HttpMethod.GET,
                httpEntity,
                new ParameterizedTypeReference<Map<String, T[]>>() {
                }, ids);
        return response.getStatusCode() == HttpStatus.OK ? response.getBody() : null;
    }
}
