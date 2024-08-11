package uz.mediasolutions.barterlybackend.controller.user.abs;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.mediasolutions.barterlybackend.payload.interfaceDTO.user.CatalogDTO;
import uz.mediasolutions.barterlybackend.utills.constants.Rest;

import java.util.List;

@RequestMapping(Rest.BASE_PATH + "catalog")
public interface CatalogController {

    @GetMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CatalogDTO.class))})
    })
    ResponseEntity<List<?>> getParents(@RequestHeader(name = "Accept-Language", defaultValue = "uz") String lang);

    @GetMapping("/{parentId}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CatalogDTO.class))})
    })
    ResponseEntity<List<?>> getByParentId(@RequestHeader(name = "Accept-Language", defaultValue = "uz") String lang,
                                          @PathVariable Long parentId);

}
