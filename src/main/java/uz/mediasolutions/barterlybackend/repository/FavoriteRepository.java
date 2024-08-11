package uz.mediasolutions.barterlybackend.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.mediasolutions.barterlybackend.entity.Favorite;
import uz.mediasolutions.barterlybackend.payload.interfaceDTO.user.ItemDTO;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    Favorite findByUserIdAndItemId(UUID userId, UUID itemId);

    Page<Favorite> findByUserId(UUID userId, Pageable pageable);

    @Query(value = "SELECT i.id, i.title, u.username, c.translations ->> :lang as category, json_agg(ii.url) as imageUrls, true as liked\n" +
            "FROM favorites f\n" +
            "         LEFT JOIN items i ON f.item_id = i.id\n" +
            "         LEFT JOIN users u ON i.user_id = u.id\n" +
            "         LEFT JOIN categories c ON i.category_id = c.id\n" +
            "         LEFT JOIN item_images ii ON i.id = ii.item_id\n" +
            "WHERE f.user_id = :userId AND c.translations ->> :lang IS NOT NULL\n" +
            "ORDER BY f.created_at DESC\n" +
            "GROUP BY i.id, i.title, u.username, c.translations", nativeQuery = true)
    Page<ItemDTO> findAllByUserId(@Param("userId") UUID userId,
                                  @Param("lang") String lang,
                                  Pageable pageable);

    @Query(value = "SELECT i.id, i.title, u.username, c.translations ->> :lang as category, json_agg(ii.url) as imageUrls, true as liked\n" +
            "FROM items i\n" +
            "         LEFT JOIN users u ON i.user_id = u.id\n" +
            "         LEFT JOIN categories c ON i.category_id = c.id\n" +
            "         LEFT JOIN item_images ii ON i.id = ii.item_id\n" +
            "WHERE i.id IN :itemIds AND c.translations ->> :lang IS NOT NULL\n" +
            "GROUP BY i.id, i.title, u.username, c.translations", nativeQuery = true)
    Page<ItemDTO> findAllByItemIds(@Param("itemIds") List<UUID> itemIds,
                                   @Param("lang") String lang,
                                   Pageable pageable);

}
