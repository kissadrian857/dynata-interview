package org.example.service;

import org.example.Main;
import org.example.model.Member;
import org.example.model.Participation;
import org.example.model.Status;
import org.example.model.Survey;
import org.junit.jupiter.api.Test;

import java.net.URL;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CsvReaderTest {

    private CsvReader underTest = new CsvReader();

    @Test
    void readMembers() {
        //given
        URL membersUrl = Main.class.getResource("/test-members.csv");
        List<Member> expectedResult = List.of(Member.builder()
                        .memberId(1)
                        .fullName("Malissa Arn")
                        .emailAddress("MalissaArn0202@gmail.com")
                        .isActive(true)
                        .build(),
                Member.builder()
                        .memberId(2)
                        .fullName("Teri Villalobos")
                        .emailAddress("TeriVillalobos6990@gmail.com")
                        .isActive(false)
                        .build());

        //when
        List<Member> actualResult = underTest.readMembers(membersUrl.getPath());

        //then
        assertThat(actualResult).hasSameElementsAs(expectedResult);
    }

    @Test
    void readParticipations() {
        //given
        URL participationsUrl = Main.class.getResource("/test-participations.csv");
        List<Participation> expectedResult = List.of(
                Participation.builder().memberId(1).surveyId(16).status(4).length(10).build(),
                Participation.builder().memberId(2).surveyId(25).status(3).length(null).build());

        //when
        List<Participation> actualResult = underTest.readParticipations(participationsUrl.getPath());

        //then
        assertThat(actualResult).hasSameElementsAs(expectedResult);
    }

    @Test
    void readStatuses() {
        //given
        URL statusesUrl = Main.class.getResource("/test-statuses.csv");
        List<Status> expectedResult = List.of(
                Status.builder().statusId(1).name("Not asked").build(),
                Status.builder().statusId(2).name("Rejected").build());

        //when
        List<Status> actualResult = underTest.readStatuses(statusesUrl.getPath());

        //then
        assertThat(actualResult).hasSameElementsAs(expectedResult);
    }

    @Test
    void readSurveys() {
        //given
        URL surveysUrl = Main.class.getResource("/test-surveys.csv");
        List<Survey> expectedResult = List.of(
                Survey.builder()
                        .surveyId(1)
                        .name("Survey 01")
                        .expectedCompletes(30)
                        .completionPoints(5)
                        .filteredPoints(2)
                        .build(),
                Survey.builder()
                        .surveyId(2)
                        .name("Survey 02")
                        .expectedCompletes(70)
                        .completionPoints(15)
                        .filteredPoints(4)
                        .build());

        //when
        List<Survey> actualResult = underTest.readSurveys(surveysUrl.getPath());

        //then
        assertThat(actualResult).hasSameElementsAs(expectedResult);
    }
}