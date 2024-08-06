package uz.mediasolutions.barterlybackend.service.user.abs;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CatalogService {

    ResponseEntity<List<?>> getParents(String lang);

    ResponseEntity<List<?>> getByParentId(String lang, Long parentId);
}
