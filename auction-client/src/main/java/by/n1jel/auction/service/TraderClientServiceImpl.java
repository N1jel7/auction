package by.n1jel.auction.service;

import by.n1jel.auction.config.ClientProperties;
import by.n1jel.auction.dto.LotResponseDto;
import by.n1jel.auction.dto.TraderCreateRequest;
import by.n1jel.auction.dto.TraderResponseDto;
import by.n1jel.auction.dto.TraderUpdateRequest;
import by.n1jel.auction.exception.UiAlertException;
import by.n1jel.auction.utils.CustomPageImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TraderClientServiceImpl implements TraderClientService {

    private final RestTemplate restTemplate;
    private final ClientProperties properties;

    @Override
    public CustomPageImpl<TraderResponseDto> findAllTraders(int pageNumber, int pageSize) {
        String url = properties.getBaseUrl() + "/api/v1/traders/page/" + pageNumber + "/" + pageSize;
        ResponseEntity<CustomPageImpl<TraderResponseDto>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<CustomPageImpl<TraderResponseDto>>() {
                }
        );

        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            log.error("Can't retrieve traders from the server: {}", response);
            return null;
        }
    }

    @Override
    public TraderResponseDto createTrader(TraderCreateRequest traderCreateRequest) {
        ResponseEntity<TraderResponseDto> response = null;
        try {
            response = restTemplate.exchange(
                    properties.getBaseUrl() + "/api/v1/traders",
                    HttpMethod.POST,
                    new HttpEntity<>(traderCreateRequest),
                    new ParameterizedTypeReference<TraderResponseDto>() {
                    }
            );
        } catch (Exception e) {
            throw new UiAlertException(e.getMessage(), e.getLocalizedMessage());
        }

        if(response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            return null;
        }

    }

    @Override
    public TraderResponseDto updateTrader(Long id, TraderUpdateRequest traderUpdateRequest) {
        ResponseEntity<TraderResponseDto> response = null;
        try {
            response = restTemplate.exchange(
                    properties.getBaseUrl() + "/api/v1/traders/{id}",
                    HttpMethod.PATCH,
                    new HttpEntity<>(traderUpdateRequest),
                    new ParameterizedTypeReference<TraderResponseDto>() {
                    },
                    id
            );
        } catch (Exception e) {
            throw new UiAlertException(e.getMessage(), e.getLocalizedMessage());
        }

        if(response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            return null;
        }
    }

    @Override
    public TraderResponseDto deleteTrader(Long id) {
        ResponseEntity<TraderResponseDto> response = restTemplate.exchange(
                properties.getBaseUrl() + "/api/v1/traders/{id}",
                HttpMethod.DELETE,
                null,
                new ParameterizedTypeReference<TraderResponseDto>() {
                },
                id
        );

        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            log.error("Can't delete trader with id={} from the server: {}", id, response);
            return null;
        }
    }
}
