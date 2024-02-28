package org.example.dto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SurveyStatistics {

    private Integer surveyId;
    private String surveyName;
    private Integer numberOfCompletes;
    private Integer numberOfRejectedParticipants;
    private Integer numberOfFilteredParticipants;
    private Double averageLength;
}
