package org.example.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Survey {

    private Integer surveyId;
    private String name;
    private Integer expectedCompletes;
    private Integer completionPoints;
    private Integer filteredPoints;
}