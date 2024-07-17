package uz.mediasolutions.barterlybackend.geocoding;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class GeocodingResponse {

    @JsonProperty("results")
    private List<GeocodingResult> results;

}
