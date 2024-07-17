package uz.mediasolutions.barterlybackend.webSocket.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import uz.mediasolutions.barterlybackend.entity.User;
import uz.mediasolutions.barterlybackend.enums.UserSocketStatusEnum;
import uz.mediasolutions.barterlybackend.repository.UserRepository;
import uz.mediasolutions.barterlybackend.utills.CommonUtils;
import uz.mediasolutions.barterlybackend.webSocket.payload.UserWebSocketDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserWebSocketService {

    private final UserRepository userRepository;

    public UserWebSocketDTO addUser() {
        User user = (User) CommonUtils.getUserFromSecurityContext();
        if (user != null) {
            user.setSocketStatus(UserSocketStatusEnum.ONLINE);
            user = userRepository.save(user);
            return new UserWebSocketDTO(user.getId(), user.getUsername(), user.getSocketStatus().name());
        }
        return null;
    }

    public UserWebSocketDTO disconnect() {
        User user = (User) CommonUtils.getUserFromSecurityContext();
        if (user != null) {
            Optional<User> optionalUser = userRepository.findById(user.getId());
            if (optionalUser.isPresent()) {
                user.setSocketStatus(UserSocketStatusEnum.OFFLINE);
                user = userRepository.save(optionalUser.get());
                return new UserWebSocketDTO(user.getId(), user.getUsername(), user.getSocketStatus().name());
            }
        }
        return null;
    }

    public ResponseEntity<List<UserWebSocketDTO>> findConnectedUsers() {
        List<User> users = userRepository.findAllBySocketStatus(UserSocketStatusEnum.ONLINE);
        List<UserWebSocketDTO> userWebSocketDTOs = new ArrayList<>();
        for (User user : users) {
            userWebSocketDTOs.add(new UserWebSocketDTO(user.getId(), user.getUsername(), user.getSocketStatus().name()));
        }
        return ResponseEntity.ok(userWebSocketDTOs);
    }

}
