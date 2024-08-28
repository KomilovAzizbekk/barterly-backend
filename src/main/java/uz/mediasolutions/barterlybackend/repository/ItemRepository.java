package uz.mediasolutions.barterlybackend.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.mediasolutions.barterlybackend.entity.Item;
import uz.mediasolutions.barterlybackend.payload.interfaceDTO.user.Item2DTO;
import uz.mediasolutions.barterlybackend.payload.interfaceDTO.user.ItemDTO;

import java.util.List;
import java.util.UUID;

public interface ItemRepository extends JpaRepository<Item, UUID> {

    @Query(value = "SELECT i.id,\n" +
            "       i.title ->> :lang    as title,\n" +
            "       u.username,\n" +
            "       c.translations ->> :lang    as category,\n" +
            "       array_agg(ii.url)           as imageUrls,\n" +
            "       COALESCE(f1.liked, 'false') as liked,\n" +
            "       count(s.id)                 as swaps\n" +
            "FROM items i\n" +
            "         LEFT JOIN users u ON i.user_id = u.id\n" +
            "         LEFT JOIN favorites f ON f.user_id = u.id AND f.item_id = i.id\n" +
            "         LEFT JOIN categories c ON i.category_id = c.id\n" +
            "         LEFT JOIN item_images ii ON i.id = ii.item_id\n" +
            "         LEFT JOIN swaps s ON u.id = s.responder_user_id OR u.id = s.requester_user_id\n" +
            "         LEFT JOIN (SELECT f1.item_id,\n" +
            "                           CASE WHEN f1.item_id IS NOT NULL THEN 'true' ELSE 'false' END AS liked\n" +
            "                    FROM favorites f1\n" +
            "                    WHERE f1.user_id = :userId) f1 ON i.id = f1.item_id\n" +
            "WHERE c.translations ->> :lang IS NOT NULL\n" +
            "  AND i.title ->> :lang IS NOT NULL\n" +
            "GROUP BY i.id, u.username, c.translations, f1.liked", nativeQuery = true)
    Page<ItemDTO> findAllForHomeForAuthenticatedUser(@Param("lang") String lang,
                                                     @Param("userId") UUID userId,
                                                     Pageable pageable);

    @Query(value = "SELECT i.id,\n" +
            "       i.title ->> :lang    as title,\n" +
            "       u.username,\n" +
            "       c.translations ->> :lang    as category,\n" +
            "       array_agg(ii.url)           as imageUrls,\n" +
            "       COALESCE(f1.liked, 'false') as liked,\n" +
            "       count(s.id)                 as swaps\n" +
            "FROM items i\n" +
            "         LEFT JOIN users u ON i.user_id = u.id\n" +
            "         LEFT JOIN favorites f ON f.user_id = u.id AND f.item_id = i.id\n" +
            "         LEFT JOIN categories c ON i.category_id = c.id\n" +
            "         LEFT JOIN item_images ii ON i.id = ii.item_id\n" +
            "         LEFT JOIN swaps s ON u.id = s.responder_user_id OR u.id = s.requester_user_id\n" +
            "         LEFT JOIN (SELECT f1.item_id,\n" +
            "                           CASE WHEN f1.item_id IS NOT NULL THEN 'true' ELSE 'false' END AS liked\n" +
            "                    FROM favorites f1\n" +
            "                    WHERE f1.item_id IN :itemIds) f1 ON i.id = f1.item_id\n" +
            "WHERE c.translations ->> :lang IS NOT NULL\n" +
            "  AND i.title ->> :lang IS NOT NULL\n" +
            "GROUP BY i.id, u.username, c.translations, f1.liked", nativeQuery = true)
    Page<ItemDTO> findAllForHomeForNotAuthenticatedUser(@Param("lang") String lang,
                                                        @Param("itemIds") List<UUID> itemIds,
                                                        Pageable pageable);

    @Query(value = "SELECT i.id,\n" +
            "       u.id                                            as userId,\n" +
            "       i.description,\n" +
            "       i.title ->> :lang                               as title,\n" +
            "       array_agg(DISTINCT im.url)                      as imageUrls,\n" +
            "       json_agg(\n" +
            "       DISTINCT CASE\n" +
            "                    WHEN c.translations ->> :lang IS NOT NULL AND\n" +
            "                         (cv.translations ->> :lang IS NOT NULL OR ic.text_value IS NOT NULL) AND c.required IS NOT NULL\n" +
            "                        THEN\n" +
            "                        jsonb_build_object(\n" +
            "                                'characteristic', c.translations ->> :lang,\n" +
            "                                'value', COALESCE(cv.translations ->> :lang, ic.text_value),\n" +
            "                                'required', c.required\n" +
            "                        )\n" +
            "                    ELSE NULL\n" +
            "           END\n" +
            "               ) FILTER (WHERE c.translations ->> :lang IS NOT NULL AND\n" +
            "                               (cv.translations ->> :lang IS NOT NULL OR ic.text_value IS NOT NULL) AND\n" +
            "                               c.required IS NOT NULL) as characteristics\n" +
            "FROM items i\n" +
            "         LEFT JOIN item_images im ON i.id = im.item_id\n" +
            "         LEFT JOIN item_characteristics ic ON i.id = ic.item_id\n" +
            "         LEFT JOIN characteristics c ON ic.characteristic_id = c.id\n" +
            "         LEFT JOIN characteristic_values cv ON ic.characteristic_value_id = cv.id\n" +
            "         LEFT JOIN users u ON u.id = i.user_id\n" +
            "WHERE i.id = :itemId\n" +
            "  AND i.title ->> :lang IS NOT NULL\n" +
            "GROUP BY i.id, u.id", nativeQuery = true)
    Item2DTO findByIdCustom(@Param("lang") String lang, @Param("itemId") UUID id);

}
