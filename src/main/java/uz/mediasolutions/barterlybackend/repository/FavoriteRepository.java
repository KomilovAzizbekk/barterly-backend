package uz.mediasolutions.barterlybackend.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import uz.mediasolutions.barterlybackend.entity.Favorite;
import uz.mediasolutions.barterlybackend.payload.interfaceDTO.user.ItemDTO;

import java.util.UUID;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    @Query(value = "SELECT i.id,\n" +
            "       c.translations ->> :lang                as title,\n" +
            "       u.username,\n" +
            "       c.translations ->> :lang                as category,\n" +
            "       json_agg(ii.url)                        as imageUrls,\n" +
            "       true                                    as liked,\n" +
            "       i.updated_at                            as updatedTime,\n" +
            "       i.premium,\n" +
            "       i.temporary\n" +
            "FROM favorites f\n" +
            "         INNER JOIN items i ON f.item_id = i.id\n" +
            "         INNER JOIN users u ON i.user_id = u.id\n" +
            "         INNER JOIN categories c ON i.category_id = c.id\n" +
            "         LEFT JOIN item_images ii ON i.id = ii.item_id\n" +
            "WHERE f.user_id = :userId\n" +
            "  AND c.translations ->> :lang IS NOT NULL\n" +
            "GROUP BY i.id, i.title, u.username, c.translations, f.updated_at\n" +
            "ORDER BY f.updated_at DESC", nativeQuery = true)
    Page<ItemDTO> findAllByUserId(@Param("userId") UUID userId,
                                  @Param("lang") String lang,
                                  Pageable pageable);

    @Query(value = "SELECT count(f.id) FROM favorites f WHERE f.user_id = :userId", nativeQuery = true)
    Integer findCountByUserId(UUID userId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM favorites WHERE user_id = :userId AND item_id = :itemId", nativeQuery = true)
    void deleteFavoritesByUserIdAndItemIdCustom(@Param("userId") UUID userId,
                                                @Param("itemId") UUID itemId);

    boolean existsByUserIdAndItemId(UUID userId, UUID itemId);

}
