package org.example.model;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Status {

    private Integer statusId;
    private String name;
}
