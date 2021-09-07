package com.phiz.common.auth.dao.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.phiz.common.core.dto.user.Address;
import com.phiz.common.core.dto.user.Device;
import com.phiz.common.core.dto.user.Name;
import lombok.*;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.time.LocalDate;

@Data
@Builder
@Document(collection = "user")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"userName"})
public class UserEntity {

    @Id
    private String id;

    @Indexed(unique = true)
    private String userName;

    @JsonIgnore
    private String password;

    private String mobileNumber;
    private String email;
    private LocalDate birthday;
    private String sex;
    private String userType;
    private Name name;
    private Address address;
    private Device device;
    private String resetToken;

    @CreatedDate
    private Instant created;

    @LastModifiedDate
    private Instant modified;

    @CreatedBy
    private String createdBy;

    @LastModifiedBy
    private String lastModifiedBy;


}
