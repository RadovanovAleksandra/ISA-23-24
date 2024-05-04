package com.isa.medical_equipment.controllers;

import com.isa.medical_equipment.dto.*;
import com.isa.medical_equipment.entity.CompanyRate;
import com.isa.medical_equipment.repositories.*;
import com.isa.medical_equipment.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "*")
@RequestMapping("/api/statistics")
@RestController
@RequiredArgsConstructor
@Tag(name = "Statistics", description = "Controller for getting statistics data for company admins")
public class StatisticsController {

    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final CompanyRateRepository companyRateRepository;
    private final TermsRepository termsRepository;

    @GetMapping("average-rate")
    @Operation(summary = "Get average rate for company")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully fetched data"),
            @ApiResponse(responseCode = "400", description = "Failed to fetch data")
    })
    public ResponseEntity<?> getAverageRateForCompany() {
        SecurityContext context = SecurityContextHolder.getContext();
        var authUser = (UserDetailsImpl) context.getAuthentication().getPrincipal();
        var user = userRepository.findById(authUser.getId()).get();

        var companies = companyRepository.findAll();
        var yourCompanyOpt = companies.stream().filter(x -> x.getAdmins().contains(user)).findFirst();
        if (yourCompanyOpt.isEmpty()) {
            var dto = new CommonResponseDto();
            dto.setMessage("There is no company where you are admin");
            return ResponseEntity.badRequest().body(dto);
        }

        var yourCompany = yourCompanyOpt.get();

        var rates = companyRateRepository.findByCompany(yourCompany);
        if (rates.size() == 0) {
            return ResponseEntity.ok(new StatisticsAverageRateResponseDto());
        }

        var averageRate = rates.stream().mapToDouble(CompanyRate::getRate).sum() / rates.size();

        var response = new StatisticsAverageRateResponseDto();
        response.setAverageRate(averageRate);
        return ResponseEntity.ok(response);
    }

    @GetMapping("number-of-terms")
    @Operation(summary = "Get number of terms grouped by selected time period for company")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully fetched data"),
            @ApiResponse(responseCode = "400", description = "Failed to fetch data")
    })
    public ResponseEntity<?> getNumberOfTerms(@RequestParam NumberOfTermsRequestParam criteria) {
        SecurityContext context = SecurityContextHolder.getContext();
        var authUser = (UserDetailsImpl) context.getAuthentication().getPrincipal();
        var user = userRepository.findById(authUser.getId()).get();

        var companies = companyRepository.findAll();
        var yourCompanyOpt = companies.stream().filter(x -> x.getAdmins().contains(user)).findFirst();
        if (yourCompanyOpt.isEmpty()) {
            var dto = new CommonResponseDto();
            dto.setMessage("There is no company where you are admin");
            return ResponseEntity.badRequest().body(dto);
        }

        var yourCompany = yourCompanyOpt.get();
        var terms = termsRepository.findByCompany(yourCompany);

        var responseList = new ArrayList<StatisticsResponseItemDto>();

        HashMap<Integer, Integer> responseFormat = new HashMap<>();
        if (criteria == NumberOfTermsRequestParam.MONTH) {
            responseFormat.put(1, 0);
            responseFormat.put(2, 0);
            responseFormat.put(3, 0);
            responseFormat.put(4, 0);
            responseFormat.put(5, 0);
            responseFormat.put(6, 0);
            responseFormat.put(7, 0);
            responseFormat.put(8, 0);
            responseFormat.put(9, 0);
            responseFormat.put(10, 0);
            responseFormat.put(11, 0);
            responseFormat.put(12, 0);

            for (var term : terms) {
                var month = term.getStart().getMonth().getValue();
                var currentNumber = responseFormat.get(month) + 1;
                responseFormat.put(month, currentNumber);
            }

            for (Map.Entry<Integer, Integer> entry : responseFormat.entrySet()) {
                if (entry.getKey() == 1) {
                    var item = new StatisticsResponseItemDto();
                    item.setKey("January");
                    item.setValue(entry.getValue());
                    responseList.add(item);
                } else if (entry.getKey() == 2) {
                    var item = new StatisticsResponseItemDto();
                    item.setKey("February");
                    item.setValue(entry.getValue());
                    responseList.add(item);
                } else if (entry.getKey() == 3) {
                    var item = new StatisticsResponseItemDto();
                    item.setKey("March");
                    item.setValue(entry.getValue());
                    responseList.add(item);
                } else if (entry.getKey() == 4) {
                    var item = new StatisticsResponseItemDto();
                    item.setKey("April");
                    item.setValue(entry.getValue());
                    responseList.add(item);
                } else if (entry.getKey() == 5) {
                    var item = new StatisticsResponseItemDto();
                    item.setKey("May");
                    item.setValue(entry.getValue());
                    responseList.add(item);
                }else if (entry.getKey() == 6) {
                    var item = new StatisticsResponseItemDto();
                    item.setKey("June");
                    item.setValue(entry.getValue());
                    responseList.add(item);
                } else if (entry.getKey() == 7) {
                    var item = new StatisticsResponseItemDto();
                    item.setKey("July");
                    item.setValue(entry.getValue());
                    responseList.add(item);
                } else if (entry.getKey() == 8) {
                    var item = new StatisticsResponseItemDto();
                    item.setKey("August");
                    item.setValue(entry.getValue());
                    responseList.add(item);
                } else if (entry.getKey() == 9) {
                    var item = new StatisticsResponseItemDto();
                    item.setKey("September");
                    item.setValue(entry.getValue());
                    responseList.add(item);
                } else if (entry.getKey() == 10) {
                    var item = new StatisticsResponseItemDto();
                    item.setKey("October");
                    item.setValue(entry.getValue());
                    responseList.add(item);
                } else if (entry.getKey() == 11) {
                    var item = new StatisticsResponseItemDto();
                    item.setKey("November");
                    item.setValue(entry.getValue());
                    responseList.add(item);
                } else if (entry.getKey() == 12) {
                    var item = new StatisticsResponseItemDto();
                    item.setKey("December");
                    item.setValue(entry.getValue());
                    responseList.add(item);
                }
            }
        } else if (criteria == NumberOfTermsRequestParam.QUARTAL) {
            responseFormat.put(1, 0);
            responseFormat.put(2, 0);
            responseFormat.put(3, 0);

            for (var term : terms) {
                var month = term.getStart().getMonth().getValue();
                if (month <=4) {
                    var currentNumber = responseFormat.get(1) + 1;
                    responseFormat.put(1, currentNumber);
                } else if (month <=8 ) {
                    var currentNumber = responseFormat.get(2) + 1;
                    responseFormat.put(2, currentNumber);
                } else {
                    var currentNumber = responseFormat.get(3) + 1;
                    responseFormat.put(4, currentNumber);
                }
            }

            for (Map.Entry<Integer, Integer> entry : responseFormat.entrySet()) {
                if (entry.getKey() == 1) {
                    var item = new StatisticsResponseItemDto();
                    item.setKey("First");
                    item.setValue(entry.getValue());
                    responseList.add(item);
                } else if (entry.getKey() == 2) {
                    var item = new StatisticsResponseItemDto();
                    item.setKey("Second");
                    item.setValue(entry.getValue());
                    responseList.add(item);
                } else if (entry.getKey() == 3) {
                    var item = new StatisticsResponseItemDto();
                    item.setKey("Third");
                    item.setValue(entry.getValue());
                    responseList.add(item);
                }
            }
        } else {
            for (int i = 1999; i <= LocalDate.now().getYear(); i++){
                responseFormat.put(i, 0);
            }

            for (var term : terms) {
                var year = term.getStart().getYear();
                var currentNumber = responseFormat.get(year) + 1;
                responseFormat.put(year, currentNumber);
            }

            for (Map.Entry<Integer, Integer> entry : responseFormat.entrySet()) {
                var item = new StatisticsResponseItemDto();
                item.setKey(entry.getKey().toString());
                item.setValue(entry.getValue());
                responseList.add(item);
            }

        }

        return ResponseEntity.ok(responseList);
    }

    @GetMapping("number-of-reservations")
    @Operation(summary = "Get number of reservations grouped by selected time period for company")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully fetched data"),
            @ApiResponse(responseCode = "400", description = "Failed to fetch data")
    })
    public ResponseEntity<?> getNumberOfReservations(@RequestParam NumberOfTermsRequestParam criteria) {
        SecurityContext context = SecurityContextHolder.getContext();
        var authUser = (UserDetailsImpl) context.getAuthentication().getPrincipal();
        var user = userRepository.findById(authUser.getId()).get();

        var companies = companyRepository.findAll();
        var yourCompanyOpt = companies.stream().filter(x -> x.getAdmins().contains(user)).findFirst();
        if (yourCompanyOpt.isEmpty()) {
            var dto = new CommonResponseDto();
            dto.setMessage("There is no company where you are admin");
            return ResponseEntity.badRequest().body(dto);
        }

        var yourCompany = yourCompanyOpt.get();
        var terms = termsRepository.findByCompanyAndReservationIsNotNull(yourCompany);

        var responseList = new ArrayList<StatisticsResponseItemDto>();

        HashMap<Integer, Integer> responseFormat = new HashMap<>();
        if (criteria == NumberOfTermsRequestParam.MONTH) {
            responseFormat.put(1, 0);
            responseFormat.put(2, 0);
            responseFormat.put(3, 0);
            responseFormat.put(4, 0);
            responseFormat.put(5, 0);
            responseFormat.put(6, 0);
            responseFormat.put(7, 0);
            responseFormat.put(8, 0);
            responseFormat.put(9, 0);
            responseFormat.put(10, 0);
            responseFormat.put(11, 0);
            responseFormat.put(12, 0);

            for (var term : terms) {
                var month = term.getStart().getMonth().getValue();
                var currentNumber = responseFormat.get(month) + 1;
                responseFormat.put(month, currentNumber);
            }

            for (Map.Entry<Integer, Integer> entry : responseFormat.entrySet()) {
                if (entry.getKey() == 1) {
                    var item = new StatisticsResponseItemDto();
                    item.setKey("January");
                    item.setValue(entry.getValue());
                    responseList.add(item);
                } else if (entry.getKey() == 2) {
                    var item = new StatisticsResponseItemDto();
                    item.setKey("February");
                    item.setValue(entry.getValue());
                    responseList.add(item);
                } else if (entry.getKey() == 3) {
                    var item = new StatisticsResponseItemDto();
                    item.setKey("March");
                    item.setValue(entry.getValue());
                    responseList.add(item);
                } else if (entry.getKey() == 4) {
                    var item = new StatisticsResponseItemDto();
                    item.setKey("April");
                    item.setValue(entry.getValue());
                    responseList.add(item);
                } else if (entry.getKey() == 5) {
                    var item = new StatisticsResponseItemDto();
                    item.setKey("May");
                    item.setValue(entry.getValue());
                    responseList.add(item);
                }else if (entry.getKey() == 6) {
                    var item = new StatisticsResponseItemDto();
                    item.setKey("June");
                    item.setValue(entry.getValue());
                    responseList.add(item);
                } else if (entry.getKey() == 7) {
                    var item = new StatisticsResponseItemDto();
                    item.setKey("July");
                    item.setValue(entry.getValue());
                    responseList.add(item);
                } else if (entry.getKey() == 8) {
                    var item = new StatisticsResponseItemDto();
                    item.setKey("August");
                    item.setValue(entry.getValue());
                    responseList.add(item);
                } else if (entry.getKey() == 9) {
                    var item = new StatisticsResponseItemDto();
                    item.setKey("September");
                    item.setValue(entry.getValue());
                    responseList.add(item);
                } else if (entry.getKey() == 10) {
                    var item = new StatisticsResponseItemDto();
                    item.setKey("October");
                    item.setValue(entry.getValue());
                    responseList.add(item);
                } else if (entry.getKey() == 11) {
                    var item = new StatisticsResponseItemDto();
                    item.setKey("November");
                    item.setValue(entry.getValue());
                    responseList.add(item);
                } else if (entry.getKey() == 12) {
                    var item = new StatisticsResponseItemDto();
                    item.setKey("December");
                    item.setValue(entry.getValue());
                    responseList.add(item);
                }
            }
        } else if (criteria == NumberOfTermsRequestParam.QUARTAL) {
            responseFormat.put(1, 0);
            responseFormat.put(2, 0);
            responseFormat.put(3, 0);

            for (var term : terms) {
                var month = term.getStart().getMonth().getValue();
                if (month <=4) {
                    var currentNumber = responseFormat.get(1) + 1;
                    responseFormat.put(1, currentNumber);
                } else if (month <=8 ) {
                    var currentNumber = responseFormat.get(2) + 1;
                    responseFormat.put(2, currentNumber);
                } else {
                    var currentNumber = responseFormat.get(3) + 1;
                    responseFormat.put(4, currentNumber);
                }
            }

            for (Map.Entry<Integer, Integer> entry : responseFormat.entrySet()) {
                if (entry.getKey() == 1) {
                    var item = new StatisticsResponseItemDto();
                    item.setKey("First");
                    item.setValue(entry.getValue());
                    responseList.add(item);
                } else if (entry.getKey() == 2) {
                    var item = new StatisticsResponseItemDto();
                    item.setKey("Second");
                    item.setValue(entry.getValue());
                    responseList.add(item);
                } else if (entry.getKey() == 3) {
                    var item = new StatisticsResponseItemDto();
                    item.setKey("Third");
                    item.setValue(entry.getValue());
                    responseList.add(item);
                }
            }
        } else {
            for (int i = 1999; i <= LocalDate.now().getYear(); i++){
                responseFormat.put(i, 0);
            }

            for (var term : terms) {
                var year = term.getStart().getYear();
                var currentNumber = responseFormat.get(year) + 1;
                responseFormat.put(year, currentNumber);
            }

            for (Map.Entry<Integer, Integer> entry : responseFormat.entrySet()) {
                var item = new StatisticsResponseItemDto();
                item.setKey(entry.getKey().toString());
                item.setValue(entry.getValue());
                responseList.add(item);
            }

        }

        return ResponseEntity.ok(responseList);
    }

    @GetMapping("total-income")
    @Operation(summary = "Get total income by selected time period for company")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully fetched data"),
            @ApiResponse(responseCode = "400", description = "Failed to fetch data")
    })
    public ResponseEntity<?> getTotalIncomeByCompany(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        SecurityContext context = SecurityContextHolder.getContext();
        var authUser = (UserDetailsImpl) context.getAuthentication().getPrincipal();
        var user = userRepository.findById(authUser.getId()).get();

        var companies = companyRepository.findAll();
        var yourCompanyOpt = companies.stream().filter(x -> x.getAdmins().contains(user)).findFirst();
        if (yourCompanyOpt.isEmpty()) {
            var dto = new CommonResponseDto();
            dto.setMessage("There is no company where you are admin");
            return ResponseEntity.badRequest().body(dto);
        }

        var yourCompany = yourCompanyOpt.get();

        var terms = termsRepository.findByCompanyAndReservationIsNotNull(yourCompany);
        terms = terms.stream().filter(x -> x.getStart().isAfter( start.atTime(LocalTime.MIN) ) && x.getStart().isBefore( end.atTime(LocalTime.MIN) )).toList();
        var totalIncome = terms.stream().mapToInt(x->x.getReservation().getPrice()).sum();

        var response = new StatisticsTotalIncomerOfReservations();
        response.setTotalIncome(totalIncome);
        return ResponseEntity.ok(response);
    }
}
