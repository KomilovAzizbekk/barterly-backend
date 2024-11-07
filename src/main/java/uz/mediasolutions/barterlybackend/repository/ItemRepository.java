package uz.mediasolutions.barterlybackend.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import uz.mediasolutions.barterlybackend.entity.Item;
import uz.mediasolutions.barterlybackend.payload.interfaceDTO.user.ItemDTO;

import java.util.Optional;
import java.util.UUID;

public interface ItemRepository extends JpaRepository<Item, UUID> {

    @Query(value = "SELECT i.id,\n" +
            "       i.title ->> :lang                       as title,\n" +
            "       u.username,\n" +
            "       c.translations ->> :lang                as category,\n" +
            "       array_agg(ii.url)                       as imageUrls,\n" +
            "       i.updated_at                            as updatedTime,\n" +
            "       null                                    as liked,\n" +
            "       i.premium,\n" +
            "       i.temporary\n" +
            "FROM items i\n" +
            "         INNER JOIN users u ON i.user_id = u.id\n" +
            "         LEFT JOIN favorites f ON f.user_id = u.id AND f.item_id = i.id\n" +
            "         INNER JOIN categories c ON i.category_id = c.id\n" +
            "         LEFT JOIN item_images ii ON i.id = ii.item_id\n" +
            "WHERE c.translations ->> :lang IS NOT NULL\n" +
            "  AND i.title ->> :lang IS NOT NULL\n" +
            "  AND i.deleted = false\n" +
            "  AND i.active = true\n" +
            "GROUP BY i.id, u.username, c.translations, i.updated_at\n" +
            "ORDER BY i.updated_at DESC", nativeQuery = true)
    Page<ItemDTO> findAllForHomeForAllUsers(@Param("lang") String lang,
                                            Pageable pageable);

    @Query(value = "SELECT i.id,\n" +
            "       i.title ->> :lang        as title,\n" +
            "       u.username,\n" +
            "       c.translations ->> :lang as category,\n" +
            "       array_agg(ii.url)        as imageUrls,\n" +
            "       i.updated_at             as updatedTime,\n" +
            "       null                     as liked,\n" +
            "       i.premium,\n" +
            "       i.temporary\n" +
            "FROM items i\n" +
            "         INNER JOIN users u ON i.user_id = u.id\n" +
            "         LEFT JOIN favorites f ON f.user_id = u.id AND f.item_id = i.id\n" +
            "         INNER JOIN categories c ON i.category_id = c.id\n" +
            "         LEFT JOIN item_images ii ON i.id = ii.item_id\n" +
            "WHERE c.translations ->> :lang IS NOT NULL\n" +
            "  AND i.title ->> :lang IS NOT NULL\n" +
            "  AND i.deleted = false\n" +
            "  AND i.active = true\n" +
            "  AND i.premium = :premium\n" +
            "GROUP BY i.id, u.username, c.translations, i.updated_at\n" +
            "ORDER BY i.updated_at DESC", nativeQuery = true)
    Page<ItemDTO> findAllForHomeForAllUsers1(@Param("lang") String lang,
                                             @Param("premium") Boolean premium,
                                             Pageable pageable);


    @Modifying
    @Transactional
    @Query(value = "UPDATE items SET deleted = true, category_id = null WHERE category_id = :categoryId", nativeQuery = true)
    void deleteAllByCategoryId(Long categoryId);

    Optional<Item> findByIdAndActiveIsTrue(UUID id);
}
