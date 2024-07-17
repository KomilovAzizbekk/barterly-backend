package uz.mediasolutions.barterlybackend.webSocket.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import uz.mediasolutions.barterlybackend.webSocket.payload.UserWebSocketDTO;
import uz.mediasolutions.barterlybackend.webSocket.service.UserWebSocketService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserWebSocketController {

    private final UserWebSocketService service;


    @MessageMapping("/user.addUser")
    @SendTo("/user/public")
    public UserWebSocketDTO addUser() {
        return service.addUser();
    }

    @MessageMapping("/user.disconnectUser")
    @SendTo("/user/public")
    public UserWebSocketDTO disconnect() {
        return service.disconnect();
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserWebSocketDTO>> getConnectedUsers() {
        return service.findConnectedUsers();
    }

}
