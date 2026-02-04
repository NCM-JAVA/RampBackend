package com.ramp.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.ramp.entity.*;
import com.ramp.req.*;

@Component
public class IndustrialUnitRegistrationMapper {

    public IndustrialUnitRegistration toEntity(IndustrialUnitRegistrationReq req, String userId) {
        IndustrialUnitRegistration entity = new IndustrialUnitRegistration();
        entity.setUserId(userId);

        // Step 1: Unit Details
        if (req.getUnitDetails() != null) {
            entity.setUnitDetails(mapUnitDetails(req.getUnitDetails()));
            entity.setOfficeAddress(mapOfficeAddress(req.getUnitDetails().getOfficeAddress()));
        }

        // Step 2: Constitution
        if (req.getConstitution() != null) {
            entity.setConstitutionType(req.getConstitution().getConstitutionType());
            List<PartnerDirector> partners = mapPartnersDirectors(req.getConstitution().getPartnersDirectors(), entity);
            entity.setPartnersDirectors(partners);
        }

        // Step 3: Operational Plan
        if (req.getOperationalPlan() != null) {
            mapOperationalPlan(req.getOperationalPlan(), entity);
        }

        // Step 4: Legal Details
        if (req.getLegalDetails() != null) {
            entity.setLegalDetails(mapLegalDetails(req.getLegalDetails()));
        }

        // Step 5: Financials
        if (req.getFinancials() != null) {
            entity.setFinancials(mapFinancials(req.getFinancials()));
        }

        // Step 6: Employment
        if (req.getEmployment() != null) {
            entity.setEmployment(mapEmployment(req.getEmployment()));
        }

        // Step 7: Declaration
        if (req.getDeclaration() != null) {
            entity.setDeclaration(mapDeclaration(req.getDeclaration()));
        }

        return entity;
    }

    public void mapUnitDetailsToEntity(UnitDetailsReq req, IndustrialUnitRegistration entity) {
        if (req != null) {
            entity.setUnitDetails(mapUnitDetails(req));
            entity.setOfficeAddress(mapOfficeAddress(req.getOfficeAddress()));
        }
    }

    public void mapConstitutionToEntity(ConstitutionReq req, IndustrialUnitRegistration entity) {
        if (req != null) {
            entity.setConstitutionType(req.getConstitutionType());
            // Clear and add to existing collection (required for orphanRemoval)
            entity.getPartnersDirectors().clear();
            if (req.getPartnersDirectors() != null) {
                for (PartnerDirectorReq partnerReq : req.getPartnersDirectors()) {
                    PartnerDirector partner = new PartnerDirector();
                    partner.setName(partnerReq.getName());
                    partner.setAddress(partnerReq.getAddress());
                    partner.setAadharDocPath(partnerReq.getAadharDocPath());
                    partner.setPanDocPath(partnerReq.getPanDocPath());
                    partner.setRegistration(entity);
                    entity.getPartnersDirectors().add(partner);
                }
            }
        }
    }

    public void mapOperationalPlanToEntity(OperationalPlanReq req, IndustrialUnitRegistration entity) {
        if (req != null) {
            mapOperationalPlan(req, entity);
        }
    }

    public void mapLegalDetailsToEntity(LegalDetailsReq req, IndustrialUnitRegistration entity) {
        if (req != null) {
            entity.setLegalDetails(mapLegalDetails(req));
        }
    }

    public void mapFinancialsToEntity(FixedCapitalInvestmentReq req, IndustrialUnitRegistration entity) {
        if (req != null) {
            entity.setFinancials(mapFinancials(req));
        }
    }

    public void mapEmploymentToEntity(EmploymentReq req, IndustrialUnitRegistration entity) {
        if (req != null) {
            entity.setEmployment(mapEmployment(req));
        }
    }

    public void mapDeclarationToEntity(DeclarationReq req, IndustrialUnitRegistration entity) {
        if (req != null) {
            entity.setDeclaration(mapDeclaration(req));
        }
    }

    private IndustrialUnitInfo mapUnitDetails(UnitDetailsReq req) {
        IndustrialUnitInfo info = new IndustrialUnitInfo();
        info.setName(req.getName());
        info.setLocation(req.getLocation());
        info.setPhone(req.getPhone());
        info.setEmail(req.getEmail());

        if (req.getAddress() != null) {
            Address address = new Address();
            address.setAddressLine(req.getAddress().getAddressLine());
            address.setPo(req.getAddress().getPo());
            address.setDistrict(req.getAddress().getDistrict());
            address.setState(req.getAddress().getState());
            info.setAddress(address);
        }

        return info;
    }

    private OfficeAddress mapOfficeAddress(OfficeAddressReq req) {
        if (req == null) {
            return null;
        }

        OfficeAddress officeAddress = new OfficeAddress();
        officeAddress.setSameAsFactory(req.getSameAsFactory());

        if (req.getAddress() != null && !Boolean.TRUE.equals(req.getSameAsFactory())) {
            Address address = new Address();
            address.setAddressLine(req.getAddress().getAddressLine());
            address.setPo(req.getAddress().getPo());
            address.setDistrict(req.getAddress().getDistrict());
            address.setState(req.getAddress().getState());
            officeAddress.setAddress(address);
        }

        return officeAddress;
    }

    private List<PartnerDirector> mapPartnersDirectors(List<PartnerDirectorReq> reqList, IndustrialUnitRegistration registration) {
        if (reqList == null || reqList.isEmpty()) {
            return new ArrayList<>();
        }

        return reqList.stream().map(req -> {
            PartnerDirector partner = new PartnerDirector();
            partner.setName(req.getName());
            partner.setAddress(req.getAddress());
            partner.setAadharDocPath(req.getAadharDocPath());
            partner.setPanDocPath(req.getPanDocPath());
            partner.setRegistration(registration);
            return partner;
        }).collect(Collectors.toList());
    }

    private void mapOperationalPlan(OperationalPlanReq req, IndustrialUnitRegistration entity) {
        entity.setCommencementDate(req.getCommencementDate());
        entity.setIndustryType(req.getIndustryType());
        entity.setActivityType(req.getActivityType());
        entity.setProductServiceName(req.getProductServiceName());

        if (req.getPowerRequirement() != null) {
            PowerRequirement power = new PowerRequirement();
            power.setPowerValue(req.getPowerRequirement().getPowerValue());
            power.setUnit(req.getPowerRequirement().getUnit());
            power.setLoadSanctionCertificatePath(req.getPowerRequirement().getLoadSanctionCertificatePath());
            entity.setPowerRequirement(power);
        }

        // Clear and add to existing collection (required for orphanRemoval)
        entity.getProductionCapacities().clear();
        if (req.getProductionCapacities() != null) {
            for (ProductionCapacityReq c : req.getProductionCapacities()) {
                ProductionCapacity capacity = new ProductionCapacity();
                capacity.setProduct(c.getProduct());
                capacity.setQuantity(c.getQuantity());
                capacity.setValue(c.getValue());
                capacity.setRegistration(entity);
                entity.getProductionCapacities().add(capacity);
            }
        }

        // Clear and add to existing collection (required for orphanRemoval)
        entity.getRawMaterials().clear();
        if (req.getRawMaterials() != null) {
            for (RawMaterialReq m : req.getRawMaterials()) {
                RawMaterial material = new RawMaterial();
                material.setMaterialName(m.getMaterialName());
                material.setRegistration(entity);
                entity.getRawMaterials().add(material);
            }
        }
    }

    private LegalRegistrations mapLegalDetails(LegalDetailsReq req) {
        LegalRegistrations legal = new LegalRegistrations();
        legal.setUdyamIemNo(req.getUdyamIemNo());
        legal.setGstNo(req.getGstNo());
        legal.setTradingLicenseNo(req.getTradingLicenseNo());
        legal.setFactoryLicenseNo(req.getFactoryLicenseNo());
        legal.setSpcbConsent(req.getSpcbConsent());
        legal.setOtherRegistration(req.getOtherRegistration());
        legal.setUdyamIemDocPath(req.getUdyamIemDocPath());
        legal.setGstDocPath(req.getGstDocPath());
        legal.setFactoryLicenseDocPath(req.getFactoryLicenseDocPath());
        legal.setPollutionBoardConsentDocPath(req.getPollutionBoardConsentDocPath());
        return legal;
    }

    private FixedCapitalInvestment mapFinancials(FixedCapitalInvestmentReq req) {
        FixedCapitalInvestment investment = new FixedCapitalInvestment();
        investment.setLand(req.getLand() != null ? req.getLand() : 0.0);
        investment.setSiteDevelopment(req.getSiteDevelopment() != null ? req.getSiteDevelopment() : 0.0);
        investment.setFactoryBuilding(req.getFactoryBuilding() != null ? req.getFactoryBuilding() : 0.0);
        investment.setOfficeBuilding(req.getOfficeBuilding() != null ? req.getOfficeBuilding() : 0.0);
        investment.setPlantMachinery(req.getPlantMachinery() != null ? req.getPlantMachinery() : 0.0);
        investment.setElectricalInstallation(req.getElectricalInstallation() != null ? req.getElectricalInstallation() : 0.0);
        investment.setPreopExpenses(req.getPreopExpenses() != null ? req.getPreopExpenses() : 0.0);
        investment.setMiscAssets(req.getMiscAssets() != null ? req.getMiscAssets() : 0.0);
        return investment;
    }

    private EmploymentMatrix mapEmployment(EmploymentReq req) {
        EmploymentMatrix matrix = new EmploymentMatrix();

        if (req.getApst() != null) {
            EmploymentCount apst = new EmploymentCount();
            apst.setManagerial(req.getApst().getManagerial() != null ? req.getApst().getManagerial() : 0);
            apst.setSupervisory(req.getApst().getSupervisory() != null ? req.getApst().getSupervisory() : 0);
            apst.setSkilled(req.getApst().getSkilled() != null ? req.getApst().getSkilled() : 0);
            apst.setSemiSkilled(req.getApst().getSemiSkilled() != null ? req.getApst().getSemiSkilled() : 0);
            apst.setUnskilled(req.getApst().getUnskilled() != null ? req.getApst().getUnskilled() : 0);
            apst.setOthers(req.getApst().getOthers() != null ? req.getApst().getOthers() : 0);
            matrix.setApst(apst);
        }

        if (req.getNonApst() != null) {
            EmploymentCount nonApst = new EmploymentCount();
            nonApst.setManagerial(req.getNonApst().getManagerial() != null ? req.getNonApst().getManagerial() : 0);
            nonApst.setSupervisory(req.getNonApst().getSupervisory() != null ? req.getNonApst().getSupervisory() : 0);
            nonApst.setSkilled(req.getNonApst().getSkilled() != null ? req.getNonApst().getSkilled() : 0);
            nonApst.setSemiSkilled(req.getNonApst().getSemiSkilled() != null ? req.getNonApst().getSemiSkilled() : 0);
            nonApst.setUnskilled(req.getNonApst().getUnskilled() != null ? req.getNonApst().getUnskilled() : 0);
            nonApst.setOthers(req.getNonApst().getOthers() != null ? req.getNonApst().getOthers() : 0);
            matrix.setNonApst(nonApst);
        }

        return matrix;
    }

    private Declaration mapDeclaration(DeclarationReq req) {
        Declaration declaration = new Declaration();
        declaration.setIsDeclared(req.getIsDeclared());
        declaration.setFullName(req.getFullName());
        declaration.setSignatureFilePath(req.getSignatureFilePath());
        declaration.setSealFilePath(req.getSealFilePath());
        return declaration;
    }
}
