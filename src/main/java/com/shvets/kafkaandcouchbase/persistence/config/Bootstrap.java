package com.shvets.kafkaandcouchbase.persistence.config;

import java.util.List;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;

import lombok.extern.slf4j.Slf4j;

import com.shvets.kafkaandcouchbase.persistence.model.Device;
import com.shvets.kafkaandcouchbase.persistence.model.House;
import com.shvets.kafkaandcouchbase.persistence.repository.DeviceRepository;
import com.shvets.kafkaandcouchbase.persistence.repository.HouseRepository;

@Configuration
@Slf4j
public class Bootstrap {

    @Bean
    ApplicationRunner checkDB(HouseRepository houseRepository,
                              DeviceRepository deviceRepository) {

        return args -> {
            List<House> houses = houseRepository.findAll();
            log.info("got houses: " + houses.size());
            if (CollectionUtils.isEmpty(houses)) {
                log.info("Starting houses generation...");
                for (int hInd = 0; hInd < 100; hInd++) {
                    House houseSaved = houseRepository.save(House.builder()
                                                                 .address(String.format("Penza, Voronova street, building #%s", hInd))
                                                                 .status("Active")
                                                                 .build());

                    for (int dInd = 0; dInd < 3; dInd++) {
                        Device device = deviceRepository.save(Device.builder()
                                                                    .type(String.valueOf(dInd))
                                                                    .house(new Device.ShortHouse(houseSaved.getId(),
                                                                            houseSaved.getAddress()))
                                                                    .build());
                    }
                }

                log.info("Houses and devices added");
            }
        };
    }

}