package org.example.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Member {

    private Integer memberId;
    private String fullName;
    private String emailAddress;
    private Boolean isActive;
}
