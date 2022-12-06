package ru.practicum.ewmmainservice.client;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.ewmmainservice.model.Dto.stats.EndpointHitDtoEMW;

import java.util.Map;

@Component
public class EventClient extends BaseClient {
    private static final String API_PREFIX = "";

    @Autowired
    public EventClient(@Value("${ewm-stat.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    public ResponseEntity<Object> createEndpointHit(EndpointHitDtoEMW endpointHitDtoEMW) {
        return post("/hit", null, null, endpointHitDtoEMW);
    }

    public ResponseEntity<Object> getEndpointHits(String start, String end, String uris, Boolean unique) {
        Map<String, Object> parameters = Map.of(
                "start", start,
                "end", end,
                "uris", uris,
                "unique", unique
        );
        return get("/stats" + "?start={start}&end={end}&uris={uris}&unique={unique}", null, parameters);
    }
}
