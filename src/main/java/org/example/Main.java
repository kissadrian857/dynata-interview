package org.example;

import org.example.model.Member;
import org.example.model.Participation;
import org.example.model.Status;
import org.example.model.Survey;
import org.example.service.CsvReader;
import org.example.service.DataFetchService;

import java.net.URL;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Member> members;
        List<Participation> participations;
        List<Status> statuses;
        List<Survey> surveys;

        URL membersUrl = Main.class.getResource("/members.csv");
        URL participationsUrl = Main.class.getResource("/participations.csv");
        URL statusesUrl = Main.class.getResource("/statuses.csv");
        URL surveysUrl = Main.class.getResource("/surveys.csv");

        CsvReader csvReader = new CsvReader();
        members = csvReader.readMembers(membersUrl.getPath());
        participations = csvReader.readParticipations(participationsUrl.getPath());
        statuses = csvReader.readStatuses(statusesUrl.getPath());
        surveys = csvReader.readSurveys(surveysUrl.getPath());

        DataFetchService dataFetchService = new DataFetchService(participations, members, surveys);

        System.out.println("a,");
        dataFetchService.fetchRespondersBySurveyId(2).stream()
                .forEach(System.out::println);

        System.out.println("\nb,");
        dataFetchService.fetchSurveyByMemberId(1).stream()
                .forEach(System.out::println);

        System.out.println("\nc,");
        dataFetchService.fetchPointsByMemberId(1).stream()
                .forEach(System.out::println);

        System.out.println("\nd,");
        dataFetchService.fetchMembersToBeInvitedBySurvey(Survey.builder().surveyId(1).build()).stream()
                .forEach(System.out::println);

        System.out.println("\ne,");
        dataFetchService.fetchSurveysStatistics().stream()
                .forEach(System.out::println);

    }
}