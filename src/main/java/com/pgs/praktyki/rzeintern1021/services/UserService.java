package com.pgs.praktyki.rzeintern1021.services;

import com.pgs.praktyki.rzeintern1021.aws.queues.UserActivationQueueSender;
import com.pgs.praktyki.rzeintern1021.aws.s3.AwsS3sender;
import com.pgs.praktyki.rzeintern1021.dto.UserDTO;
import com.pgs.praktyki.rzeintern1021.dto.UserRegisterDTO;
import com.pgs.praktyki.rzeintern1021.exceptions.UserDoesntExistException;
import com.pgs.praktyki.rzeintern1021.exceptions.UserExistException;
import com.pgs.praktyki.rzeintern1021.models.User;
import com.pgs.praktyki.rzeintern1021.repo.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserActivationQueueSender userActivationQueueSender;
    private final AwsS3sender awsS3Sender;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public UserService(UserRepository userRepository, UserActivationQueueSender userActivationQueueSender, AwsS3sender awsS3Sender) {
        this.userRepository = userRepository;
        this.userActivationQueueSender = userActivationQueueSender;
        this.awsS3Sender = awsS3Sender;
    }

    public User getUserEntityByName(final String userName) throws UserDoesntExistException {
        if (userRepository.findUserByUsername(userName) != null) {
            return userRepository.findUserByUsername(userName);
        }
        throw new UserDoesntExistException("User not found!");
    }

    public User getUserEntityByUUID(final String uuid) throws UserDoesntExistException {
        if (userRepository.findUserByUuid(uuid) != null) {
            return userRepository.findUserByUuid(uuid);
        }
        throw new UserDoesntExistException("User not found!");
    }

    public List<User> getAllUsers() {
        return (List<User>) userRepository.findAll();
    }

    public Page<UserDTO> searchUsers(final String searchWord, Pageable pageable) {
        return new PageImpl<UserDTO>(userRepository.findUserBySearchWord(searchWord, pageable)
            .stream()
            .map(UserDTO::new)
            .collect(Collectors.toList()));
    }

    public User addUser(UserRegisterDTO userRegisterDTO) throws UserExistException {
        if (userRepository.findUserByUsername(userRegisterDTO.getUsername()) == null) {
            User user = userRegisterDTO.toEntity();
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            userActivationQueueSender.sendToQueue(user.getUuid() + "/" + user.getActivationLink());
            return user;
        }
        throw new UserExistException("User already exists!");
    }

    public User activateUser(final String uuid, final String activationCode) {
        if (userRepository.findUserByUuid(uuid) != null) {
            User user = userRepository.findUserByUuid(uuid);
            if (user.getActivationLink().equals(activationCode)) {
                user.setActivationLink(null);
                user.setActive(true);
                userRepository.save(user);
                return user;
            }
            throw new UserDoesntExistException("Activation code is not valid.");
        }
        throw new UserDoesntExistException("User doesn't exist, can't activate.");
    }

    public User modifyUserByUUID(final String uuid, UserDTO userDTO, MultipartFile avatar) throws UserDoesntExistException {
        if (userRepository.findUserByUuid(uuid) != null) {
            User user = userRepository.findUserByUuid(uuid);
            user = userDTO.saveToEntity(user);
            if (avatar != null) {
                if (user.getAvatarLink() != null) {
                    awsS3Sender.deleteFileByFilename(user.getAvatarLink());
                }
                user.setAvatarLink(awsS3Sender.uploadToS3(avatar, 16));
            }
            userRepository.save(user);
            return user;
        }
        throw new UserDoesntExistException("User of that uuid don't exist, can't be modified..");
    }

    public void removeUserByUUID(final String uuid) throws UserDoesntExistException {
        if (userRepository.findUserByUuid(uuid) != null) {
            User user = userRepository.findUserByUuid(uuid);
            user.setEnabled(false);
            userRepository.save(user);
        } else throw new UserDoesntExistException("User of that uuid don't exist, can't be deleted");
    }

    public Page<User> findAllPageable(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }


}
