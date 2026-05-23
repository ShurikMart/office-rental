package com.example.office_rental.config;

import com.example.office_rental.model.*;
import com.example.office_rental.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final BuildingRepository buildingRepository;
    private final OfficeSpaceRepository officeSpaceRepository;
    private final FloorPlanRepository floorPlanRepository;

    @Override
    public void run(String... args) {

        // Чтобы данные не дублировались при каждом запуске
        if (buildingRepository.count() > 0) {
            return;
        }


        // ===== BUILDING =====

        Building building = new Building(
                "Business Center Alpha",
                "Main Street 10",
                5
        );

        buildingRepository.save(building);

        // ===== OFFICE 101 =====

        OfficeSpace office101 = new OfficeSpace(
                "101",
                45.0,
                1,
                6,
                OfficeType.STANDARD,
                true,
                1200.0,
                OfficeStatus.FREE,
                building
        );

        officeSpaceRepository.save(office101);

        FloorPlan fp101 = new FloorPlan(
                office101,
                1,
                100.0,
                268.0,
                113.0,
                108.0
        );

        floorPlanRepository.save(fp101);

        // ===== OFFICE 102 =====

        OfficeSpace office102 = new OfficeSpace(
                "102",
                30.0,
                1,
                4,
                OfficeType.MEETING_ROOM,
                false,
                900.0,
                OfficeStatus.FREE,
                building
        );

        officeSpaceRepository.save(office102);

        FloorPlan fp102 = new FloorPlan(
                office102,
                1,
                485.0,
                630.0,
                183.0,
                96.0
        );

        floorPlanRepository.save(fp102);

        // ===== OFFICE 103 =====

        OfficeSpace office103 = new OfficeSpace(
                "103",
                60.0,
                1,
                10,
                OfficeType.SERVER_ROOM,
                true,
                2000.0,
                OfficeStatus.FREE,
                building
        );

        officeSpaceRepository.save(office103);

        FloorPlan fp103 = new FloorPlan(
                office103,
                1,
                178.0,
                628.0,
                140.0,
                96.0
        );

        floorPlanRepository.save(fp103);

    }

    @Bean
    CommandLineRunner initUsers(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {

        return args -> {

            if (userRepository.count() == 0) {

                User admin = new User(

                        "admin",

                        "admin@example.com",

                        passwordEncoder.encode("55849276"),

                        Role.ADMIN
                );

                userRepository.save(admin);
            }
        };
    }

}
