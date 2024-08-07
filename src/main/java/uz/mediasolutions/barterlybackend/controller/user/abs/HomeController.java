package uz.mediasolutions.barterlybackend.controller.user.abs;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.mediasolutions.barterlybackend.utills.constants.Rest;

@RequestMapping(Rest.BASE_PATH + "home")
public interface HomeController {

    @GetMapping
    ResponseEntity<?> getHeaderDetails(@RequestHeader(name = "Accept-Language", defaultValue = "uz") String lang);

}
