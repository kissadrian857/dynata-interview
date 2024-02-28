package org.example.model;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Participation {

    private Integer memberId;
    private Integer surveyId;
    private Integer status;
    private Integer length;
}
