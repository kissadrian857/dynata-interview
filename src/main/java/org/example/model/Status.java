package org.example.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Status {

    private Integer statusId;
    private String name;
}
