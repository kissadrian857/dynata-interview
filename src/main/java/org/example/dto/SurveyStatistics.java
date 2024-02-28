package org.example.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class SurveyStatistics {

    private Integer surveyId;
    private String surveyName;
    private Integer numberOfCompletes;
    private Integer numberOfRejectedParticipants;
    private Integer numberOfFilteredParticipants;
    private Double averageLength;
}
