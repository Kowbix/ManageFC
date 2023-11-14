package com.example.ManageFC.funkction;

import com.example.ManageFC.entity.Team;
import com.example.ManageFC.entity.User;
import com.example.ManageFC.entity.plans.Plan;
import com.example.ManageFC.enums.PlanType;
import com.example.ManageFC.service.UserService;
import com.example.ManageFC.service.plan.PlanService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


@Service
@AllArgsConstructor
public class IndividualPlanCsvReader {

    private final UserService userService;
    private final PlanService planService;

    public void readCSVFile(MultipartFile file, Team team){
        try{
            InputStreamReader inputStreamReader = new InputStreamReader(file.getInputStream());
            BufferedReader fileReader = new BufferedReader(new BufferedReader(inputStreamReader));
            String line = "";

            while((line = fileReader.readLine()) != null) {
                String[] row = line.split(";");

                Plan plan = new Plan();

                plan.setName(row[0]);
                plan.setDescription(row[1]);
                plan.setPlace(row[2]);

                DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate date = LocalDate.parse(row[3], dateFormat);
                plan.setDate(date);

                DateTimeFormatter timeFormat = DateTimeFormatter.ISO_TIME;
                LocalTime time = LocalTime.parse(row[4], timeFormat);
                plan.setTime(time);

                User user = userService.findUserByEmail(row[5]);
                List<User> users = new ArrayList<>();
                users.add(user);
                plan.setUsers(users);

                plan.setTeam(team);
                plan.setPlanType(PlanType.INDIVIDUAL);

                planService.addPlan(plan);
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static boolean isCSVFile(MultipartFile file){
        if(file == null) {
            return false;
        }

        return file.getName().toLowerCase().endsWith(".csv");
        
    }

}
