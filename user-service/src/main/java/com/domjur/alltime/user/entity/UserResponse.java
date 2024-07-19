package com.domjur.alltime.user.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table (name = "user")
public class UserResponse {
    @Id
    private String userName;
    private String emailId;
    private String firstName;
    private String lastName;

}
