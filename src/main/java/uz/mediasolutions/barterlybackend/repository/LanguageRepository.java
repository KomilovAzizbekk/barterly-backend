package uz.mediasolutions.barterlybackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.mediasolutions.barterlybackend.entity.Language;

public interface LanguageRepository extends JpaRepository<Language, Long> {

}
