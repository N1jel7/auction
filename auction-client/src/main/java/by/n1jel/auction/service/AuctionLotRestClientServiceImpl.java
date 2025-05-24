package by.n1jel.auction.service;

import by.n1jel.auction.config.ClientProperties;
import by.n1jel.auction.dto.LotCreateRequestDto;
import by.n1jel.auction.dto.LotResponseDto;
import by.n1jel.auction.dto.LotUpdateRequestDto;
import by.n1jel.auction.exception.EmptyFieldException;
import by.n1jel.auction.exception.UiAlertException;
import javafx.scene.control.TextField;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuctionLotRestClientServiceImpl implements AuctionLotClientService {

    private final RestTemplate restTemplate;
    private final ClientProperties properties;

    @Override
    public List<LotResponseDto> findAll() {
        ResponseEntity<List<LotResponseDto>> response = restTemplate.exchange(
                properties.getBaseUrl() + "/api/v1/lots",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<LotResponseDto>>() {
                }
        );

        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            log.error("Can't retrieve lots from the server: {}", response);
            return null;
        }
    }

    @Override
    public LotResponseDto findById(Long id) {
        ResponseEntity<LotResponseDto> response = restTemplate.exchange(
                properties.getBaseUrl() + "/api/v1/lots/{id}", HttpMethod.GET,
                null,
                new ParameterizedTypeReference<LotResponseDto>() {
                },
                id
        );

        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            log.error("Can't retrieve lot with id={} from the server: {}", id, response);
            return null;
        }
    }

    @Override
    public LotResponseDto create(LotCreateRequestDto lotCreateRequestDto) {
        ResponseEntity<LotResponseDto> response = null;
        try {
            response = restTemplate.exchange(
                    properties.getBaseUrl() + "/api/v1/lots", HttpMethod.POST,
                    new HttpEntity<>(lotCreateRequestDto),
                    new ParameterizedTypeReference<LotResponseDto>() {
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
    public LotResponseDto updateById(Long id, LotUpdateRequestDto lotUpdateRequestDto) {
        ResponseEntity<LotResponseDto> response = null;
        try {
            response = restTemplate.exchange(
                    properties.getBaseUrl() + "/api/v1/lots/{id}", HttpMethod.PATCH,
                    new HttpEntity<>(lotUpdateRequestDto),
                    new ParameterizedTypeReference<LotResponseDto>() {
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
    public LotResponseDto deleteById(Long id) {
        ResponseEntity<LotResponseDto> response = restTemplate.exchange(
                properties.getBaseUrl() + "/api/v1/lots/{id}",
                HttpMethod.DELETE,
                null,
                new ParameterizedTypeReference<LotResponseDto>() {
                },
                id
        );

        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            log.error("Can't delete lot with id={} from the server: {}", id, response);
            return null;
        }
    }

    @Override
    public LotCreateRequestDto mapFieldsToCreateDto(TextField name, TextField price, TextField type) {
        if (
                !name.getText().trim().isEmpty() &&
                        !price.getText().trim().isEmpty() &&
                        !type.getText().trim().isEmpty()) {
            BigDecimal bigDecimal = BigDecimal.valueOf(Double.parseDouble(price.getText()));
            return new LotCreateRequestDto(name.getText(), type.getText(), bigDecimal);

        } else {
            throw new EmptyFieldException("Some fields are missing");
        }
    }

    @Override
    public boolean isAddressAlive(String address) {
        ResponseEntity<List<LotResponseDto>> response = null;
        try {
            response = restTemplate.exchange(
                    address + "/api/v1/lots",
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<LotResponseDto>>() {
                    }
            );
        } catch (Exception e) {
            log.error("Server with address '{}' not responding", address);
            return false;
        }
        properties.setBaseUrl(address);
        return true;

    }
}
