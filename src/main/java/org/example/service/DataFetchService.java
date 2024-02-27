package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.SurveyPoint;
import org.example.dto.SurveyStatistics;
import org.example.model.Member;
import org.example.model.Participation;
import org.example.model.Survey;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class DataFetchService {

    private static final Integer NOT_ASKED = 1;
    private static final Integer REJECTED = 2;
    private static final Integer FILTERED = 3;
    private static final Integer COMPLETED = 4;

    private final List<Participation> participations;
    private final List<Member> members;
    private final List<Survey> surveys;

    public List<Member> fetchRespondersBySurveyId(Integer surveyId) {
        List<Integer> memberIds = participations.stream()
                .filter(participation -> participation.getSurveyId().equals(surveyId)
                        && Objects.equals(participation.getStatus(), COMPLETED))
                .map(Participation::getMemberId)
                .toList();

        return members.stream()
                .filter(member -> memberIds.contains(member.getMemberId()))
                .collect(Collectors.toList());
    }

    public List<Survey> fetchSurveyByMemberId(Integer memberId) {
        List<Integer> surveyIds = participations.stream()
                .filter(participation -> participation.getMemberId().equals(memberId)
                        && Objects.equals(participation.getStatus(), COMPLETED))
                .map(Participation::getSurveyId)
                .toList();

        return surveys.stream()
                .filter(survey -> surveyIds.contains(survey.getSurveyId()))
                .collect(Collectors.toList());
    }

    public List<SurveyPoint> fetchPointsByMemberId(Integer memberId) {
        List<SurveyPoint> result = new ArrayList<>();

        for (Participation participation : participations) {
            if (!participation.getMemberId().equals(memberId)) {
                continue;
            }
            if (Objects.equals(participation.getStatus(), NOT_ASKED) || Objects.equals(participation.getStatus(), REJECTED)) {
                continue;
            }

            for (Survey survey : surveys) {
                if (survey.getSurveyId().equals(participation.getSurveyId())) {
                    result.add(SurveyPoint.builder()
                            .surveyId(survey.getSurveyId())
                            .points(Objects.equals(participation.getStatus(), FILTERED) ?
                                    survey.getFilteredPoints() : survey.getCompletionPoints())
                            .build());
                }
            }
        }
        return result;
    }

    public List<Member> fetchMembersToBeInvitedBySurvey(Survey survey) {
        List<Integer> memberIdsInParticipations = participations.stream()
                .map(Participation::getMemberId)
                .distinct()
                .toList();
        List<Member> membersNotPresentInParticipations = members.stream()
                .filter(member -> !memberIdsInParticipations.contains(member.getMemberId())
                        && member.getIsActive())
                .toList();

        List<Integer> membersIdsNotParticipatedInSurvey = participations.stream()
                .filter(participation -> participation.getSurveyId().equals(survey.getSurveyId())
                        && Objects.equals(participation.getStatus(), NOT_ASKED))
                .map(Participation::getMemberId)
                .toList();
        List<Member> membersNotParticipatedInSurvey = members.stream()
                .filter(member -> membersIdsNotParticipatedInSurvey.contains(member.getMemberId())
                        && member.getIsActive())
                .toList();

        List<Member> result = new ArrayList<>();
        result.addAll(membersNotPresentInParticipations);
        result.addAll(membersNotParticipatedInSurvey);
        return result;
    }

    public List<SurveyStatistics> fetchSurveysStatistics() {
        List<SurveyStatistics> result = new ArrayList<>();
        for (Survey survey : surveys) {
            int numberOfCompletes = 0;
            int filteredParticipants = 0;
            int rejectedParticipants = 0;
            double sumLength = 0.0;
            int participationCounter = 0;
            for (Participation participation : participations) {
                if (participation.getSurveyId().equals(survey.getSurveyId())) {
                    numberOfCompletes = Objects.equals(participation.getStatus(), COMPLETED) ?
                            numberOfCompletes + 1 : numberOfCompletes;
                    filteredParticipants = Objects.equals(participation.getStatus(), FILTERED) ?
                            filteredParticipants + 1 : filteredParticipants;
                    rejectedParticipants = Objects.equals(participation.getStatus(), REJECTED) ?
                            rejectedParticipants + 1 : rejectedParticipants;
                }
                if (Objects.nonNull(participation.getLength())) {
                    sumLength += (double) participation.getLength();
                    participationCounter++;
                }
            }
            result.add(SurveyStatistics.builder()
                    .surveyId(survey.getSurveyId())
                    .surveyName(survey.getName())
                    .numberOfCompletes(numberOfCompletes)
                    .numberOfFilteredParticipants(filteredParticipants)
                    .numberOfRejectedParticipants(rejectedParticipants)
                    .averageLength(sumLength / participationCounter)
                    .build());
        }

        return result;
    }
}
