package com.pgs.praktyki.rzeintern1021.services;

import com.pgs.praktyki.rzeintern1021.aws.queues.UserActivationQueueSender;
import com.pgs.praktyki.rzeintern1021.aws.s3.AwsS3sender;
import com.pgs.praktyki.rzeintern1021.dto.UserDTO;
import com.pgs.praktyki.rzeintern1021.dto.UserRegisterDTO;
import com.pgs.praktyki.rzeintern1021.exceptions.UserDoesntExistException;
import com.pgs.praktyki.rzeintern1021.models.User;
import com.pgs.praktyki.rzeintern1021.repo.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockMultipartFile;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserActivationQueueSender userActivationQueueSender;

    @Mock
    private AwsS3sender awsS3sender;

    @Mock
    private User USER_ENTITYMOCK;

    @Mock
    private UserDTO USER_DTOMOCK;

    @InjectMocks
    private UserService userService;


    @Test
    void getUserEntityByName_should_return_user_if_exists() {
        when(userRepository.findUserByUsername(USER_ENTITYMOCK.getUsername())).thenReturn(USER_ENTITYMOCK);

        assertEquals(userService.getUserEntityByName(USER_ENTITYMOCK.getUsername()), USER_ENTITYMOCK);
    }

    @Test
    void getUserEntityByName_should_throw_exception() {
        assertThrows(UserDoesntExistException.class, () -> {
            userService.getUserEntityByName(null);
        });
    }

    @Test
    void getUserEntityByUUID_should_return_user_if_exists() {
        when(userRepository.findUserByUuid(USER_ENTITYMOCK.getUuid())).thenReturn(USER_ENTITYMOCK);

        assertEquals(userService.getUserEntityByUUID(USER_ENTITYMOCK.getUuid()), USER_ENTITYMOCK);
    }

    @Test
    void getUserEntityByUUID_should_throw_exception() {
        assertThrows(UserDoesntExistException.class, () -> {
            userService.getUserEntityByUUID(null);
        });
    }

    @Test
    void getAllUsers_should_return_list_of_users_status() {
        List<User> userList = new ArrayList<User>();
        userList.add(USER_ENTITYMOCK);
        userList.add(USER_ENTITYMOCK);
        when(userRepository.findAll()).thenReturn(userList);

        assertEquals(userService.getAllUsers(), userList);
    }

    @Test
    void searchUsers_should_return_list_of_serached_users() {

    }

    @Test
    void addUser_should_add_user() {
        User user = new User(1L, "uuid", "testuser1", "testpass1", "email", "fn", "ln", 55);
        when(userRepository.findUserByUsername(user.getUsername())).thenReturn(null);

        User testResponse = userService.addUser(new UserRegisterDTO(user));

        verify(userRepository, times(1)).save(testResponse);
    }

    @Test
    void addUser_should_throw_exception_when_user_exists() {
        when(userRepository.findUserByUsername(USER_ENTITYMOCK.getUsername())).thenReturn(USER_ENTITYMOCK);

        assertThrows(Exception.class, () -> {
            userService.addUser(new UserRegisterDTO(USER_ENTITYMOCK));
        });
    }

    @Test
    void activateUser_should_activate_user() {
        String validActivationCode = "activationCode";
        User user = new User(1L, "uuid", "testuser1", "testpass1", "email", "fn", "ln", 55);
        user.setActivationLink(validActivationCode);

        when(userRepository.findUserByUuid(user.getUuid())).thenReturn(user);

        assertEquals(userService.activateUser(user.getUuid(), validActivationCode).isActive(), true);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void activateUser_should_throw_exception_when_activationcode_is_invalid() {
        User user = new User(1L, "uuid7", "testuser1", "testpass1", "email", "fn", "ln", 55);
        user.setActivationLink("activationCode");
        String invalidCode = "invalidCode";

        when(userRepository.findUserByUuid(user.getUuid())).thenReturn(user);
        assertThrows(UserDoesntExistException.class, () -> {
            userService.activateUser(user.getUuid(), invalidCode);
        });
    }

    @Test
    void activateUser_should_throw_exception_when_uuid_is_invalid() {
        String invalidCode = "invalidCode";
        when(userRepository.findUserByUuid(USER_ENTITYMOCK.getUuid())).thenReturn(null);
        assertThrows(UserDoesntExistException.class, () -> {
            userService.activateUser(USER_ENTITYMOCK.getUuid(), invalidCode);
        });
    }

    @Test
    void modifyUserByUUID_should_return_user_when_ok() {
        when(userRepository.findUserByUuid(USER_ENTITYMOCK.getUuid())).thenReturn(USER_ENTITYMOCK);

        User response = userService.modifyUserByUUID(USER_ENTITYMOCK.getUuid(), new UserDTO(USER_ENTITYMOCK), null);

        verify(userRepository, times(1)).save(response);
    }

    @Test
    void modifyUserByUUID_should_change_user_avatar_when_provided() {
        MockMultipartFile multipartFile = new MockMultipartFile("name1", "avatarFileName", "contentType", "fileContent".getBytes());
        User testedUser = new User(1L, "uuid", "testuser1", "testpass1", "email", "fn", "ln", 55);
        testedUser.setAvatarLink("default_avatar.jpg");

        when(userRepository.findUserByUuid(testedUser.getUuid())).thenReturn(testedUser);

        User response = userService.modifyUserByUUID(testedUser.getUuid(), new UserDTO(testedUser), multipartFile);

        verify(userRepository, times(1)).save(response);
        assertNotEquals(response.getAvatarLink(), "default_avatar.jpg");
    }

    @Test
    void modifyUserByUUID_should_throw_exception_when_user_dont_exists() {
        when(userRepository.findUserByUuid(USER_DTOMOCK.getUuid())).thenReturn(null);

        assertThrows(Exception.class, () -> {
            userService.modifyUserByUUID(USER_DTOMOCK.getUuid(), USER_DTOMOCK, null);
        });
    }

    @Test
    void removeUserByUUID_should_remove_user() {
        when(userRepository.findUserByUuid(USER_ENTITYMOCK.getUuid())).thenReturn(USER_ENTITYMOCK);

        userService.removeUserByUUID(USER_ENTITYMOCK.getUuid());

        verify(userRepository, times(1)).save(USER_ENTITYMOCK);
    }

    @Test
    void removeUserByUUID_should_throw_exception() {
        assertThrows(UserDoesntExistException.class, () -> {
            userService.removeUserByUUID(null);
        });
    }

    @Test
    void findAllPageable_should_return_page() {
        Integer pageSize = 2;
        Pageable pageable = PageRequest.of(0, pageSize);

        List<User> userList = new ArrayList<User>();
        userList.add(USER_ENTITYMOCK);
        userList.add(USER_ENTITYMOCK);
        Page<User> page = new PageImpl<>(userList);

        when(userRepository.findAll(pageable)).thenReturn(page);

        assertEquals(userService.findAllPageable(pageable).getTotalElements(), pageSize.longValue());
    }

    @Test
    void saveUser_should_invoke_user_repository() {
        userService.saveUser(USER_ENTITYMOCK);

        verify(userRepository, times(1)).save(USER_ENTITYMOCK);
    }
}
