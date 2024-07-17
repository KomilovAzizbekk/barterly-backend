package uz.mediasolutions.barterlybackend.geocoding;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class GeocodingService {

    private static final String GEOCODING_API_URL = "https://maps.googleapis.com/maps/api/geocode/json";

    @Value("${google.cloud.geocoding.api-key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    public GeocodingService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getCountryCityAndDistrict(double latitude, double longitude) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(GEOCODING_API_URL)
                .queryParam("latlng", latitude + "," + longitude)
                .queryParam("key", apiKey);

        GeocodingResponse response = restTemplate.getForObject(uriBuilder.toUriString(), GeocodingResponse.class);
        if (response != null && !response.getResults().isEmpty()) {
            for (GeocodingResult result : response.getResults()) {
                String country = null;
                String city = null;
                String district = null;
                for (AddressComponent component : result.getAddressComponents()) {
                    if (component.getTypes().contains("country")) {
                        country = component.getLongName();
                    } else if (component.getTypes().contains("locality")) {
                        city = component.getLongName();
                    } else if (component.getTypes().contains("sublocality") || component.getTypes().contains("administrative_area_level_2")) {
                        district = component.getLongName();
                    }
                }
                if (country != null && city != null && district != null) {
                    return "Country: " + country + ", City: " + city + ", District: " + district;
                }
            }
        }
        return "Location not found";
    }

}
