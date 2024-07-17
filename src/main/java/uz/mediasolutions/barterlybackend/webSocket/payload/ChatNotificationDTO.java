package uz.mediasolutions.barterlybackend.webSocket.payload;

import lombok.*;

import java.security.SecureRandom;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatNotificationDTO {

    private String chatId;

    private String senderId;

    private String recipientId;

    private String content;

    private String timestamp;

}
