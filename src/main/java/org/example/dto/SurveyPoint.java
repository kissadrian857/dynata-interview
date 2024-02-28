package org.example.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class SurveyPoint {

    private Integer surveyId;
    private Integer points;
}
