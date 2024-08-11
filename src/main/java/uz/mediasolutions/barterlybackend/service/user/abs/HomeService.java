package uz.mediasolutions.barterlybackend.service.user.abs;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

public interface HomeService {

    ResponseEntity<?> getHeaderDetails(HttpServletRequest request, HttpSession session);

    ResponseEntity<Page<?>> getItems(String lang, int page, int size, HttpServletRequest request, HttpSession session);

}
