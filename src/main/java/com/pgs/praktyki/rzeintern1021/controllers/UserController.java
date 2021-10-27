package com.pgs.praktyki.rzeintern1021.controllers;

import com.pgs.praktyki.rzeintern1021.dto.UserDTO;
import com.pgs.praktyki.rzeintern1021.dto.UserRegisterDTO;
import com.pgs.praktyki.rzeintern1021.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Validated
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "{uuid}")
    public ResponseEntity<UserDTO> getUserByUUID(@PathVariable String uuid) {
        return ResponseEntity.status(HttpStatus.OK).body(new UserDTO(userService.getUserEntityByUUID(uuid)));
    }

    @GetMapping("/list_all")
    public ResponseEntity<List<UserDTO>> listUsers() {
        return new ResponseEntity<>(userService.getAllUsers().stream().map(UserDTO::new).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/list_page")
    public ResponseEntity<Page<UserDTO>> listUsersPage(@PageableDefault(size = 10) Pageable pageable) {
        return new ResponseEntity<>(new PageImpl<>(userService.findAllPageable(pageable).stream().map(UserDTO::new).collect(Collectors.toList())), HttpStatus.OK);
    }

    @GetMapping("/search/{searchWord}")
    public ResponseEntity<Page<UserDTO>> searchUsers(@PathVariable("searchWord") String searchWord, @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.searchUsers(searchWord, pageable));
    }

    @PostMapping
    public ResponseEntity<UserDTO> addUser(@RequestBody @Valid UserRegisterDTO userRegisterDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new UserDTO(userService.addUser(userRegisterDTO)));
    }

    @PatchMapping(value = "{uuid}/activate/{activationCode}")
    public ResponseEntity<UserDTO> activateUser(@PathVariable("uuid") String uuid, @PathVariable("activationCode") String activationCode) {
        return ResponseEntity.status(HttpStatus.OK).body(new UserDTO(userService.activateUser(uuid, activationCode)));
    }

    @PatchMapping(value = "{uuid}", consumes = {"multipart/form-data"})
    public ResponseEntity<UserDTO> editUserByUUID(@PathVariable("uuid") String uuid, @RequestPart(value = "user") UserDTO userDTO, @RequestPart(value = "avatar", required = false) MultipartFile avatar) {
        return ResponseEntity.status(HttpStatus.OK).body(new UserDTO(userService.modifyUserByUUID(uuid, userDTO, avatar)));
    }

    @DeleteMapping(value = "{uuid}")
    public ResponseEntity<Void> removeUserbyUUID(@PathVariable String uuid) {
        userService.removeUserByUUID(uuid);
        return ResponseEntity.noContent().build();
    }
}
