package uz.mediasolutions.barterlybackend.service.user.abs;

import uz.mediasolutions.barterlybackend.payload.interfaceDTO.user.CatalogDTO;
import uz.mediasolutions.barterlybackend.payload.response.CatalogResDTO;
import uz.mediasolutions.barterlybackend.payload.response.CatalogResDTO2;

import java.util.List;

public interface CatalogService {

    List<CatalogDTO> getParents(String lang);

    List<CatalogDTO> getByParentId(String lang, Long parentId);

    List<CatalogResDTO> getAllByParentId(String lang, Long parentId);

    CatalogResDTO2 getHierarchyById(String lang, Long id);
}
