package mediaproject.its.service.Util;

import lombok.RequiredArgsConstructor;
import mediaproject.its.domain.entity.User;
import mediaproject.its.domain.repository.UserRepository;
import mediaproject.its.response.error.UserErrorCode;
import mediaproject.its.response.exception.CustomIllegalArgumentException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserUtil {

    private final UserRepository userRepository;

    public void validUser(String username){
        User user = userRepository.findByUsername(username);
        if(user == null){
            throw new CustomIllegalArgumentException(UserErrorCode.USER_NOT_FOUND_ERROR, UserErrorCode.USER_NOT_FOUND_ERROR.getMessage());
        }
    }


    public User findUser(String username){
        User user = userRepository.findByUsername(username);
        if(user == null){
            throw new CustomIllegalArgumentException(UserErrorCode.USER_NOT_FOUND_ERROR, UserErrorCode.USER_NOT_FOUND_ERROR.getMessage());
        }

        return user;
    }

    public User findUserById(int userId){
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new CustomIllegalArgumentException(UserErrorCode.USER_NOT_FOUND_ERROR,UserErrorCode.USER_NOT_FOUND_ERROR.getMessage()));

        return user;
    }
}