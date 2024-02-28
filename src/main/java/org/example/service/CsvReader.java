package org.example.service;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.example.model.Member;
import org.example.model.Participation;
import org.example.model.Status;
import org.example.model.Survey;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvReader {

    public List<Member> readMembers(String fileName) {
        List<Member> result = new ArrayList<>();
        try (CSVReader csvReader = new CSVReader(new FileReader(fileName))) {
            List<String[]> records = csvReader.readAll();
            records = records.subList(1, records.size());

            for (String[] record : records) {
                result.add(Member.builder()
                        .memberId(Integer.parseInt(record[0]))
                        .fullName(record[1])
                        .emailAddress(record[2])
                        .isActive("1".equals(record[3]) ? true : false)
                        .build());
            }
        } catch (IOException | CsvException exception) {
            exception.printStackTrace();
        }

        return result;
    }

    public List<Participation> readParticipations(String fileName) {
        List<Participation> result = new ArrayList<>();
        try (CSVReader csvReader = new CSVReader(new FileReader(fileName))) {
            List<String[]> records = csvReader.readAll();
            records = records.subList(1, records.size());

            for (String[] record : records) {
                result.add(Participation.builder()
                        .memberId(Integer.parseInt(record[0]))
                        .surveyId(Integer.parseInt(record[1]))
                        .status(Integer.parseInt(record[2]))
                        .length(record[3].isEmpty() ? null : Integer.parseInt(record[3]))
                        .build());
            }
        } catch (IOException | CsvException exception) {
            exception.printStackTrace();
        }

        return result;
    }

    public List<Status> readStatuses(String fileName) {
        List<Status> result = new ArrayList<>();
        try (CSVReader csvReader = new CSVReader(new FileReader(fileName))) {
            List<String[]> records = csvReader.readAll();
            records = records.subList(1, records.size());

            for (String[] record : records) {
                result.add(Status.builder()
                        .statusId(Integer.parseInt(record[0]))
                        .name(record[1])
                        .build());
            }
        } catch (IOException | CsvException exception) {
            exception.printStackTrace();
        }

        return result;
    }

    public List<Survey> readSurveys(String fileName) {
        List<Survey> result = new ArrayList<>();
        try (CSVReader csvReader = new CSVReader(new FileReader(fileName))) {
            List<String[]> records = csvReader.readAll();
            records = records.subList(1, records.size());

            for (String[] record : records) {
                result.add(Survey.builder()
                        .surveyId(Integer.parseInt(record[0]))
                        .name(record[1])
                        .expectedCompletes(Integer.parseInt(record[2]))
                        .completionPoints(Integer.parseInt(record[3]))
                        .filteredPoints(Integer.parseInt(record[4]))
                        .build());
            }
        } catch (IOException | CsvException exception) {
            exception.printStackTrace();
        }

        return result;
    }
}
