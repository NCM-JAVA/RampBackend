package com.ramp.service;

import com.ramp.dto.ApplicationDocumentDto;
import com.ramp.dto.IncentiveApplicationDto;
import com.ramp.entity.ApplicationDocument;
import com.ramp.entity.IncentiveApplication;
import com.ramp.repo.ApplicationDocumentRepository;
import com.ramp.repo.IncentiveApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.Year;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class IncentiveApplicationService {

    private final IncentiveApplicationRepository applicationRepository;
    private final ApplicationDocumentRepository documentRepository;
    private final FileStorageService fileStorageService;

    // ========== CREATE (DRAFT) ==========

    public IncentiveApplicationDto create(IncentiveApplicationDto dto, String userId) {
        String customId = generateId(userId);

        IncentiveApplication entity = IncentiveApplication.builder()
                .id(customId)
                .userId(userId)
                .unitName(dto.getUnitName())
                .gstin(dto.getGstin())
                .udhyamNumber(dto.getUdhyamNumber())
                .investmentAmount(dto.getInvestmentAmount())
                .employmentCommitment(dto.getEmploymentCommitment())
                .factoryLocation(dto.getFactoryLocation())
                .factoryDistrict(dto.getFactoryDistrict())
                .factoryState(dto.getFactoryState())
                .factoryPoliceStation(dto.getFactoryPoliceStation())
                .factoryMobile(dto.getFactoryMobile())
                .factoryEmail(dto.getFactoryEmail())
                .officeLocation(dto.getOfficeLocation())
                .officeDistrict(dto.getOfficeDistrict())
                .officeState(dto.getOfficeState())
                .officePoliceStation(dto.getOfficePoliceStation())
                .officeMobile(dto.getOfficeMobile())
                .officeEmail(dto.getOfficeEmail())
                .declarationAccepted(dto.getDeclarationAccepted())
                .status("DRAFT")
                .build();

        IncentiveApplication saved = applicationRepository.save(entity);
        return toDto(saved);
    }

    // ========== UPDATE ==========

    public IncentiveApplicationDto update(String id, IncentiveApplicationDto dto, String userId) {
        IncentiveApplication entity = getAndValidateOwnership(id, userId);

        if (!"DRAFT".equals(entity.getStatus())) {
            throw new IllegalArgumentException("Only DRAFT applications can be updated");
        }

        entity.setUnitName(dto.getUnitName());
        entity.setGstin(dto.getGstin());
        entity.setUdhyamNumber(dto.getUdhyamNumber());
        entity.setInvestmentAmount(dto.getInvestmentAmount());
        entity.setEmploymentCommitment(dto.getEmploymentCommitment());
        entity.setFactoryLocation(dto.getFactoryLocation());
        entity.setFactoryDistrict(dto.getFactoryDistrict());
        entity.setFactoryState(dto.getFactoryState());
        entity.setFactoryPoliceStation(dto.getFactoryPoliceStation());
        entity.setFactoryMobile(dto.getFactoryMobile());
        entity.setFactoryEmail(dto.getFactoryEmail());
        entity.setOfficeLocation(dto.getOfficeLocation());
        entity.setOfficeDistrict(dto.getOfficeDistrict());
        entity.setOfficeState(dto.getOfficeState());
        entity.setOfficePoliceStation(dto.getOfficePoliceStation());
        entity.setOfficeMobile(dto.getOfficeMobile());
        entity.setOfficeEmail(dto.getOfficeEmail());
        entity.setDeclarationAccepted(dto.getDeclarationAccepted());

        IncentiveApplication saved = applicationRepository.save(entity);
        return toDto(saved);
    }

    // ========== GET BY ID ==========

    public IncentiveApplicationDto getById(String id, String userId) {
        IncentiveApplication entity = applicationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Application not found with id: " + id));

        // Allow admin to skip ownership check (userId = null)
        if (userId != null && !entity.getUserId().equals(userId)) {
            throw new IllegalArgumentException("You don't have permission to view this application");
        }

        return toDto(entity);
    }

    // ========== LIST ALL (PAGINATED) ==========

    public Page<IncentiveApplicationDto> getAll(Pageable pageable) {
        return applicationRepository.findAllByOrderByCreatedAtDesc(pageable)
                .map(this::toDtoSummary);
    }

    public Page<IncentiveApplicationDto> getByUser(String userId, Pageable pageable) {
        return applicationRepository.findByUserIdOrderByCreatedAtDesc(userId, pageable)
                .map(this::toDtoSummary);
    }

    // ========== UPLOAD DOCUMENT ==========

    public ApplicationDocumentDto uploadDocument(String applicationId, String documentType,
                                                  MultipartFile file, String userId) {
        IncentiveApplication application = getAndValidateOwnership(applicationId, userId);

        if (!"DRAFT".equals(application.getStatus())) {
            throw new IllegalArgumentException("Documents can only be uploaded to DRAFT applications");
        }

        // Validate document type
        if (!isValidDocumentType(documentType)) {
            throw new IllegalArgumentException(
                    "Invalid document type. Allowed: GST_CERTIFICATE, UDYAM_CERTIFICATE, BANK_STATEMENT");
        }

        // Store file
        String filePath = fileStorageService.storeFile(file, userId);

        // Save metadata
        ApplicationDocument document = ApplicationDocument.builder()
                .application(application)
                .documentType(documentType)
                .fileName(file.getOriginalFilename())
                .filePath(filePath)
                .build();

        ApplicationDocument saved = documentRepository.save(document);
        return toDocDto(saved);
    }

    // ========== SUBMIT ==========

    public IncentiveApplicationDto submit(String id, String userId) {
        IncentiveApplication entity = getAndValidateOwnership(id, userId);

        if (!"DRAFT".equals(entity.getStatus())) {
            throw new IllegalArgumentException("Only DRAFT applications can be submitted");
        }

        // Validate declaration
        if (!Boolean.TRUE.equals(entity.getDeclarationAccepted())) {
            throw new IllegalArgumentException("Declaration must be accepted before submitting");
        }

        // Validate at least 1 document uploaded
        long docCount = documentRepository.countByApplicationId(id);
        if (docCount == 0) {
            throw new IllegalArgumentException("At least one document must be uploaded before submitting");
        }

        entity.setStatus("SUBMITTED");
        IncentiveApplication saved = applicationRepository.save(entity);
        return toDto(saved);
    }

    // ========== HELPERS ==========

    private IncentiveApplication getAndValidateOwnership(String id, String userId) {
        IncentiveApplication entity = applicationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Application not found with id: " + id));

        if (!entity.getUserId().equals(userId)) {
            throw new IllegalArgumentException("You don't have permission to access this application");
        }

        return entity;
    }

    private String generateId(String userId) {
        int currentYear = Year.now().getValue();
        String prefix = "UNIT-" + userId + "-" + currentYear;
        long count = applicationRepository.countByIdStartingWith(prefix);
        String sequence = String.format("%03d", count + 1);
        return prefix + "-" + sequence;
    }

    private boolean isValidDocumentType(String type) {
        return "GST_CERTIFICATE".equals(type)
                || "UDYAM_CERTIFICATE".equals(type)
                || "BANK_STATEMENT".equals(type);
    }

    private IncentiveApplicationDto toDto(IncentiveApplication entity) {
        List<ApplicationDocumentDto> docs = entity.getDocuments() != null
                ? entity.getDocuments().stream().map(this::toDocDto).collect(Collectors.toList())
                : Collections.emptyList();

        return IncentiveApplicationDto.builder()
                .id(entity.getId())
                .userId(entity.getUserId())
                .unitName(entity.getUnitName())
                .gstin(entity.getGstin())
                .udhyamNumber(entity.getUdhyamNumber())
                .investmentAmount(entity.getInvestmentAmount())
                .employmentCommitment(entity.getEmploymentCommitment())
                .factoryLocation(entity.getFactoryLocation())
                .factoryDistrict(entity.getFactoryDistrict())
                .factoryState(entity.getFactoryState())
                .factoryPoliceStation(entity.getFactoryPoliceStation())
                .factoryMobile(entity.getFactoryMobile())
                .factoryEmail(entity.getFactoryEmail())
                .officeLocation(entity.getOfficeLocation())
                .officeDistrict(entity.getOfficeDistrict())
                .officeState(entity.getOfficeState())
                .officePoliceStation(entity.getOfficePoliceStation())
                .officeMobile(entity.getOfficeMobile())
                .officeEmail(entity.getOfficeEmail())
                .status(entity.getStatus())
                .declarationAccepted(entity.getDeclarationAccepted())
                .documents(docs)
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    private IncentiveApplicationDto toDtoSummary(IncentiveApplication entity) {
        return IncentiveApplicationDto.builder()
                .id(entity.getId())
                .userId(entity.getUserId())
                .unitName(entity.getUnitName())
                .gstin(entity.getGstin())
                .udhyamNumber(entity.getUdhyamNumber())
                .investmentAmount(entity.getInvestmentAmount())
                .status(entity.getStatus())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    private ApplicationDocumentDto toDocDto(ApplicationDocument doc) {
        return ApplicationDocumentDto.builder()
                .id(doc.getId())
                .applicationId(doc.getApplication().getId())
                .documentType(doc.getDocumentType())
                .fileName(doc.getFileName())
                .filePath(doc.getFilePath())
                .uploadedAt(doc.getUploadedAt())
                .build();
    }
}
