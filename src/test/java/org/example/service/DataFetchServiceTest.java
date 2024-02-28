package org.example.service;

import org.example.dto.SurveyPoint;
import org.example.dto.SurveyStatistics;
import org.example.model.Member;
import org.example.model.Participation;
import org.example.model.Survey;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class DataFetchServiceTest {

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
        return Stream.of(
                Arguments.of()
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
        return Stream.of(
                Arguments.of()
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
        return Stream.of(
                Arguments.of()
        );
    }

    @ParameterizedTest
    @MethodSource("fetchMembersToBeInvitedBySurveySource")
    void fetchMembersToBeInvitedBySurvey(List<Participation> participations, List<Member> members, List<Survey> surveys,
                                         Survey survey, List<Member> expectedResult) {
        //given
        underTest = new DataFetchService(participations, members, surveys);

        //when
        List<Member> actualResult = underTest.fetchMembersToBeInvitedBySurvey(survey);

        //then
        assertThat(actualResult).hasSameElementsAs(expectedResult);
    }

    private static Stream<Arguments> fetchMembersToBeInvitedBySurveySource() {
        return Stream.of(
                Arguments.of()
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
        return Stream.of(
                Arguments.of()
        );
    }
}