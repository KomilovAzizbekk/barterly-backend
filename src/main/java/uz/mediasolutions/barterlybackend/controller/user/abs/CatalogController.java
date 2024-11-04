package uz.mediasolutions.barterlybackend.controller.user.abs;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.mediasolutions.barterlybackend.payload.interfaceDTO.user.CatalogDTO;
import uz.mediasolutions.barterlybackend.payload.response.CatalogResDTO;
import uz.mediasolutions.barterlybackend.utills.constants.Rest;

import java.util.List;

@RequestMapping(Rest.BASE_PATH + "app/catalog")
public interface CatalogController {

    @GetMapping
    ResponseEntity<List<CatalogDTO>> getParents(@RequestHeader(name = "Accept-Language", defaultValue = "uz") String lang);

    @GetMapping("/{parentId}")
    ResponseEntity<List<CatalogDTO>> getByParentId(@RequestHeader(name = "Accept-Language", defaultValue = "uz") String lang,
                                                   @PathVariable Long parentId);

    @GetMapping("/all/{parentId}")
    ResponseEntity<List<CatalogResDTO>> getAllByParentId(@RequestHeader(name = "Accept-Language", defaultValue = "uz") String lang,
                                                         @PathVariable Long parentId);

}
