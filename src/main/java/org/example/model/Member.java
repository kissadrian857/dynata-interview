package org.example.model;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Member {

    private Integer memberId;
    private String fullName;
    private String emailAddress;
    private Boolean isActive;
}
