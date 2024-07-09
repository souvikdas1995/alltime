package com.domjur.alltime.auth.service;

import com.domjur.alltime.auth.dto.UserDto;
import com.domjur.alltime.auth.security.KeycloakConfig;
import com.domjur.alltime.auth.util.Credentials;
import lombok.AllArgsConstructor;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class KeycloakService {




    public void addUser(UserDto userDto){
        CredentialRepresentation credential = Credentials
                .createPasswordCredentials(userDto.getPassword());
        UserRepresentation user = new UserRepresentation();
        user.setUsername(userDto.getUserName());
        user.setFirstName(userDto.getFirstname());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmailId());
        user.setCredentials(Collections.singletonList(credential));
        user.setGroups(Arrays.asList(userDto.getGroup()));
        user.setEnabled(true);

        UsersResource instance = getInstance();
        instance.create(user);
    }

    public List<UserRepresentation> getUser(String userName){
        UsersResource usersResource = getInstance();
        List<UserRepresentation> user = usersResource.search(userName, true);
        return user;

    }

    public void updateUser(String userId, UserDto userDto){
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
    }

    public UsersResource getInstance(){
        return KeycloakConfig.getInstance().realm(KeycloakConfig.realm).users();
    }


}
