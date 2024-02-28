package org.example.dto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SurveyPoint {

    private Integer surveyId;
    private Integer points;
}
