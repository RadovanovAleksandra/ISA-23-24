package com.isa.medical_equipment.scheduler;

import com.isa.medical_equipment.repositories.PenaltyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class MonthlyScheduler {

    @Autowired
    private PenaltyRepository penaltyRepository;

    @Scheduled(cron = "0 0 0 1 * ?")
//    @Scheduled(fixedRate = 2000) // pokrece se na svake 2 sekunde, za olaksano testiranje
    public void performMonthlyPenaltiesDelete() {
        var today = LocalDate.now();
        if (today.getDayOfMonth() == 1) {
            penaltyRepository.deleteAll();
        }
    }
}
