package com.domjur.alltime.auth.service;

import com.domjur.alltime.auth.config.KeycloakConfig;
import com.domjur.alltime.auth.dto.UserDto;
import com.domjur.alltime.auth.exception.UserCustomException;
import com.domjur.alltime.auth.response.UserResponse;
import com.domjur.alltime.auth.util.Credentials;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class KeycloakService {

    @Autowired
    private KafkaTemplate<String, UserResponse> kafkaTemplate;

    @Autowired
    private Keycloak keycloak;

    @Value("${keycloak.realm}")
    public String realm;

    @Value("${topic}")
    public String topic;



    public UserResponse addUser(UserDto userDto){
        UserResponse existingUsersWithSameUserName = this.getUser(userDto.getUserName());
        if(existingUsersWithSameUserName!=null)
            throw new UserCustomException("user with given username already exists", "USER_EXISTS");
        CredentialRepresentation credential = Credentials
                .createPasswordCredentials(userDto.getPassword());
        UserRepresentation user = new UserRepresentation();
        user.setUsername(userDto.getUserName());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmailId());
        user.setCredentials(Collections.singletonList(credential));
        user.setGroups(Arrays.asList(userDto.getGroup()));
        user.setEnabled(true);

        UsersResource instance = getInstance();
        instance.create(user);
        UserResponse userResponse = UserResponse
                .builder()
                .userName(userDto.getUserName())
                .emailId(userDto.getEmailId())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .build();
        kafkaTemplate.send(topic, userResponse.getUserName(),userResponse);
        return userResponse;
    }

    public UserResponse getUser(String userName){
        UsersResource usersResource = getInstance();
        List<UserRepresentation> user = usersResource.search(userName, true);
        if(user.size()>1)
            throw new UserCustomException("more than one user with same userName exists", "MULTIPLE_USERS_EXISTS");
        if(user.size()==1) {
            UserRepresentation userDetails = user.get(0);
            UserResponse userResponse = UserResponse
                    .builder()
                    .userName(userDetails.getUsername())
                    .emailId(userDetails.getEmail())
                    .firstName(userDetails.getFirstName())
                    .lastName(userDetails.getLastName())
                    .build();
            return userResponse;
        }
        return null;

    }

    /*public void updateUser(String userId, UserDto userDto){
        CredentialRepresentation credential = Credentials
                .createPasswordCredentials(userDto.getPassword());
        UserRepresentation user = new UserRepresentation();
        user.setUsername(userDto.getUserName());
        user.setFirstName(userDto.getFirstname());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmailId());
        user.setCredentials(Collections.singletonList(credential));
        UsersResource usersResource = getInstance();
        usersResource.get(userId).update(user);
    }
    public void deleteUser(String userId){
        UsersResource usersResource = getInstance();
        usersResource.get(userId)
                .remove();
    }


    public void sendVerificationLink(String userId){
        UsersResource usersResource = getInstance();
        usersResource.get(userId)
                .sendVerifyEmail();
    }

    public void sendResetPassword(String userId){
        UsersResource usersResource = getInstance();

        usersResource.get(userId)
                .executeActionsEmail(Arrays.asList("UPDATE_PASSWORD"));
    }*/

    public UsersResource getInstance(){
        return keycloak.realm(realm).users();
    }


}
