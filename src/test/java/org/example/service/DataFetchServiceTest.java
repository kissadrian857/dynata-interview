package org.example.service;

import org.example.dto.SurveyPoint;
import org.example.dto.SurveyStatistics;
import org.example.model.Member;
import org.example.model.Participation;
import org.example.model.Survey;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class DataFetchServiceTest {

    private static final Integer NOT_ASKED = 1;
    private static final Integer REJECTED = 2;
    private static final Integer FILTERED = 3;
    private static final Integer COMPLETED = 4;

    private DataFetchService underTest;

    @ParameterizedTest
    @MethodSource("fetchRespondersBySurveyIdSource")
    void fetchRespondersBySurveyId(List<Participation> participations, List<Member> members,
                                   Integer surveyId, List<Member> expectedResult) {
        //given
        underTest = new DataFetchService(participations, members, List.of());

        //when
        List<Member> actualResult = underTest.fetchRespondersBySurveyId(surveyId);

        //then
        assertThat(actualResult).hasSameElementsAs(expectedResult);
    }

    private static Stream<Arguments> fetchRespondersBySurveyIdSource() {
        Member member1 = Member.builder().memberId(1).fullName("Malissa Arn").build();
        Member member2 = Member.builder().memberId(2).fullName("Teri Villalobos").build();

        return Stream.of(
                Arguments.of(
                        List.of(),
                        List.of(member1),
                        1,
                        List.of()
                ),
                Arguments.of(
                        List.of(Participation.builder().surveyId(2).memberId(2).status(COMPLETED).build(),
                                Participation.builder().surveyId(2).memberId(1).status(NOT_ASKED).build(),
                                Participation.builder().surveyId(3).memberId(2).status(COMPLETED).build()),
                        List.of(member1, member2),
                        2,
                        List.of(member2)
                ),
                Arguments.of(
                        List.of(Participation.builder().surveyId(2).memberId(2).status(COMPLETED).build(),
                                Participation.builder().surveyId(2).memberId(1).status(COMPLETED).build()),
                        List.of(member1, member2),
                        2,
                        List.of(member1, member2)
                )
        );
    }

    @ParameterizedTest
    @MethodSource("fetchSurveyByMemberIdSource")
    void fetchSurveyByMemberId(List<Participation> participations, List<Survey> surveys,
                               Integer memberId, List<Survey> expectedResult) {
        //given
        underTest = new DataFetchService(participations, List.of(), surveys);

        //when
        List<Survey> actualResult = underTest.fetchSurveyByMemberId(memberId);

        //then
        assertThat(actualResult).hasSameElementsAs(expectedResult);
    }

    private static Stream<Arguments> fetchSurveyByMemberIdSource() {
        Survey survey1 = Survey.builder().surveyId(1).name("Survey1").build();
        Survey survey2 = Survey.builder().surveyId(2).name("Survey2").build();

        return Stream.of(
                Arguments.of(
                        List.of(),
                        List.of(survey1),
                        2,
                        List.of()
                ),
                Arguments.of(
                        List.of(Participation.builder().memberId(2).surveyId(1).status(COMPLETED).build(),
                                Participation.builder().memberId(2).surveyId(2).status(COMPLETED).build()),
                        List.of(survey1, survey2),
                        2,
                        List.of(survey1, survey2)
                ),
                Arguments.of(
                        List.of(Participation.builder().memberId(2).surveyId(1).status(REJECTED).build(),
                                Participation.builder().memberId(2).surveyId(2).status(COMPLETED).build()),
                        List.of(survey1, survey2),
                        2,
                        List.of(survey2)
                )
        );
    }

    @ParameterizedTest
    @MethodSource("fetchPointsByMemberIdSource")
    void fetchPointsByMemberId(List<Participation> participations, List<Survey> surveys,
                               Integer memberId, List<SurveyPoint> expectedResult) {
        //given
        underTest = new DataFetchService(participations, List.of(), surveys);

        //when
        List<SurveyPoint> actualResult = underTest.fetchPointsByMemberId(memberId);

        //then
        assertThat(actualResult).hasSameElementsAs(expectedResult);
    }

    private static Stream<Arguments> fetchPointsByMemberIdSource() {
        Survey survey1 = Survey.builder().surveyId(1).filteredPoints(3).completionPoints(5).build();
        Survey survey2 = Survey.builder().surveyId(2).filteredPoints(1).completionPoints(10).build();

        return Stream.of(
                Arguments.of(
                        List.of(Participation.builder().memberId(1).surveyId(1).status(COMPLETED).build(),
                                Participation.builder().memberId(1).surveyId(2).status(FILTERED).build()),
                        List.of(survey1, survey2),
                        1,
                        List.of(SurveyPoint.builder().surveyId(1).points(5).build(),
                                SurveyPoint.builder().surveyId(2).points(1).build())
                ),
                Arguments.of(
                        List.of(),
                        List.of(survey1, survey2),
                        1,
                        List.of()
                ),
                Arguments.of(
                        List.of(Participation.builder().memberId(1).surveyId(1).status(NOT_ASKED).build(),
                                Participation.builder().memberId(1).surveyId(2).status(FILTERED).build()),
                        List.of(survey1, survey2),
                        1,
                        List.of(SurveyPoint.builder().surveyId(2).points(1).build())
                )
        );
    }

    @ParameterizedTest
    @MethodSource("fetchMembersToBeInvitedBySurveySource")
    void fetchMembersToBeInvitedBySurvey(List<Participation> participations, List<Member> members,
                                         Survey survey, List<Member> expectedResult) {
        //given
        underTest = new DataFetchService(participations, members, List.of());

        //when
        List<Member> actualResult = underTest.fetchMembersToBeInvitedBySurvey(survey);

        //then
        assertThat(actualResult).hasSameElementsAs(expectedResult);
    }

    private static Stream<Arguments> fetchMembersToBeInvitedBySurveySource() {
        Member member1 = Member.builder().memberId(1).fullName("Malissa Arn").isActive(true).build();
        Member member2 = Member.builder().memberId(2).fullName("Teri Villalobos").isActive(true).build();
        Member member3 = Member.builder().memberId(3).fullName("Joella Verne").isActive(true).build();
        Member member4 = Member.builder().memberId(4).fullName("Bettyann Kizer").isActive(false).build();
        Survey survey = Survey.builder().surveyId(1).build();

        return Stream.of(
                Arguments.of(
                        List.of(),
                        List.of(member1, member2, member4),
                        survey,
                        List.of(member1, member2)
                ),
                Arguments.of(
                        List.of(Participation.builder().surveyId(1).memberId(1).status(NOT_ASKED).build(),
                                Participation.builder().surveyId(1).memberId(4).status(NOT_ASKED).build(),
                                Participation.builder().surveyId(1).memberId(3).status(COMPLETED).build(),
                                Participation.builder().surveyId(2).memberId(2).status(NOT_ASKED).build()),
                        List.of(member1, member2, member3, member4),
                        survey,
                        List.of(member1)
                ),
                Arguments.of(
                        List.of(Participation.builder().surveyId(1).memberId(1).status(NOT_ASKED).build(),
                                Participation.builder().surveyId(1).memberId(2).status(REJECTED).build()),
                        List.of(member1, member2, member3, member4),
                        survey,
                        List.of(member1, member3)
                )
        );
    }

    @ParameterizedTest
    @MethodSource("fetchSurveysStatisticsSource")
    void fetchSurveysStatistics(List<Participation> participations, List<Survey> surveys,
                                List<SurveyStatistics> expectedResult) {
        //given
        underTest = new DataFetchService(participations, List.of(), surveys);

        //when
        List<SurveyStatistics> actualResult = underTest.fetchSurveysStatistics();

        //then
        assertThat(actualResult).hasSameElementsAs(expectedResult);
    }

    private static Stream<Arguments> fetchSurveysStatisticsSource() {
        Survey survey1 = Survey.builder().surveyId(1).name("Survey1").build();
        Survey survey2 = Survey.builder().surveyId(2).name("Survey2").build();

        return Stream.of(
                Arguments.of(
                        List.of(Participation.builder().surveyId(1).memberId(1).status(COMPLETED).length(5).build(),
                                Participation.builder().surveyId(1).memberId(2).status(REJECTED).build(),
                                Participation.builder().surveyId(2).memberId(1).status(FILTERED).build(),
                                Participation.builder().surveyId(2).memberId(2).status(COMPLETED).length(3).build()),
                        List.of(survey1, survey2),
                        List.of(SurveyStatistics.builder()
                                        .surveyId(1)
                                        .surveyName("Survey1")
                                        .numberOfCompletes(1)
                                        .numberOfFilteredParticipants(0)
                                        .numberOfRejectedParticipants(1)
                                        .averageLength(5.0)
                                        .build(),
                                SurveyStatistics.builder()
                                        .surveyId(2)
                                        .surveyName("Survey2")
                                        .numberOfCompletes(1)
                                        .numberOfFilteredParticipants(1)
                                        .numberOfRejectedParticipants(0)
                                        .averageLength(3.0)
                                        .build()
                        )
                ),
                Arguments.of(
                        List.of(Participation.builder().surveyId(1).memberId(1).status(COMPLETED).length(5).build(),
                                Participation.builder().surveyId(1).memberId(2).status(COMPLETED).length(2).build()),
                        List.of(survey1),
                        List.of(SurveyStatistics.builder()
                                .surveyId(1)
                                .surveyName("Survey1")
                                .numberOfCompletes(2)
                                .numberOfFilteredParticipants(0)
                                .numberOfRejectedParticipants(0)
                                .averageLength(3.5)
                                .build()
                        )
                ),
                Arguments.of(
                        List.of(),
                        List.of(survey1),
                        List.of(SurveyStatistics.builder()
                                .surveyId(1)
                                .surveyName("Survey1")
                                .numberOfCompletes(0)
                                .numberOfFilteredParticipants(0)
                                .numberOfRejectedParticipants(0)
                                .averageLength(0.0)
                                .build()
                        )
                ),
                Arguments.of(
                        List.of(Participation.builder().surveyId(1).memberId(1).status(REJECTED).build(),
                                Participation.builder().surveyId(1).memberId(2).status(REJECTED).build()),
                        List.of(survey1, survey2),
                        List.of(SurveyStatistics.builder()
                                        .surveyId(1)
                                        .surveyName("Survey1")
                                        .numberOfCompletes(0)
                                        .numberOfFilteredParticipants(0)
                                        .numberOfRejectedParticipants(2)
                                        .averageLength(0.0)
                                        .build(),
                                SurveyStatistics.builder()
                                        .surveyId(2)
                                        .surveyName("Survey2")
                                        .numberOfCompletes(0)
                                        .numberOfFilteredParticipants(0)
                                        .numberOfRejectedParticipants(0)
                                        .averageLength(0.0)
                                        .build()
                        )
                )
        );
    }
}