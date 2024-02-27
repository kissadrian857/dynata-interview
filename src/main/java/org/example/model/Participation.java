package org.example.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Participation {

    private Integer memberId;
    private Integer surveyId;
    private Integer status;
    private Integer length;
}
