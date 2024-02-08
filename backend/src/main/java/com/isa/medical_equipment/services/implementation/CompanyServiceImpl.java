package com.isa.medical_equipment.services.implementation;

import com.isa.medical_equipment.entity.Company;
import com.isa.medical_equipment.repositories.CompanyRepository;
import com.isa.medical_equipment.services.interfaces.CompanyService;
import lombok.RequiredArgsConstructor;

import java.util.Collection;

@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository companyRepository;
    public Collection<Company> getAll() {
        return companyRepository.findAll();
    }
}
