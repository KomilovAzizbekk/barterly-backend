package uz.mediasolutions.barterlybackend.webSocket.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import uz.mediasolutions.barterlybackend.webSocket.chatroom.ChatMessage;
import uz.mediasolutions.barterlybackend.webSocket.payload.ChatNotificationDTO;
import uz.mediasolutions.barterlybackend.webSocket.service.ChatMessageService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatMessageService chatMessageService;

    @GetMapping("/messages/{senderId}/{recipientId}")
    public ResponseEntity<List<ChatMessage>> findChatMessages(@PathVariable String senderId, @PathVariable String recipientId) {
        return ResponseEntity.ok(chatMessageService.findChatMessages(senderId, recipientId));
    }

    @MessageMapping("/chat")
    public void processChatMessage(@Payload ChatMessage chatMessage) {
        ChatMessage saved = chatMessageService.save(chatMessage);
        messagingTemplate.convertAndSendToUser(
                chatMessage.getRecipientId(),
                "/queue/messages",
                ChatNotificationDTO.builder()
                        .chatId(saved.getChatId())
                        .senderId(saved.getSenderId())
                        .recipientId(saved.getRecipientId())
                        .content(saved.getContent())
                        .timestamp(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format((TemporalAccessor) saved.getTimestamp()))
                        .build());
    }

}
