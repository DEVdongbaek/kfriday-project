package com.kfriday.kevin.service;

import com.kfriday.kevin.dto.ImageDTO;
import com.kfriday.kevin.dto.PackageDTO;
import com.kfriday.kevin.dto.PackageEventDTO;
import com.kfriday.kevin.entity.Package;
import com.kfriday.kevin.exception.customException.DoesNotExistPackageException;
import com.kfriday.kevin.repository.PackageJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.kfriday.kevin.exception.MessageCode.DOES_NOT_EXIST_PACKAGE;

@Service
@Log4j
@RequiredArgsConstructor
public class PackageService {

    private final ApplicationEventPublisher eventPublisher;

    private final ImageService imageService;

    private final PackageJpaRepository packageJpaRepository;

    @Transactional
    public void savePackage(PackageDTO.RequestDTO requestDTO) {

        Package packageEntity = packageJpaRepository.save(Package.of(requestDTO));

        eventPublisher.publishEvent(PackageEventDTO.of(packageEntity, requestDTO.getImages()));

    }

    @Transactional(readOnly = true)
    public PackageDTO.ResponseDTO getPackage(Long id) throws DoesNotExistPackageException {

        Optional<Package> packageOp = packageJpaRepository.findById(id);

        if (packageOp.isEmpty()) {
            throw new DoesNotExistPackageException(DOES_NOT_EXIST_PACKAGE.getMessage());
        }

        Package packageEntity = packageOp.get();

        List<ImageDTO.ResponseDTO> images = imageService.getImages(packageEntity.getId());

        return PackageDTO.ResponseDTO.of(packageEntity, images);

    }

    @Transactional(readOnly = true)
    public PackageDTO.ResponseDTOs getPackages(int offset, int limit) {
        List<Package> packages = packageJpaRepository.findPackagesWithOffsetAndLimit(PageRequest.of(offset, limit));

        return PackageDTO.ResponseDTOs.of(
                packages.stream().map(
                packageEntity ->
                        PackageDTO.ResponseDTO.of(packageEntity, imageService.getImages(packageEntity.getId()))
        ).toList());
    }

    @Transactional
    public void updatePackage(Long id, PackageDTO.UpdateReqDTO updateReqDTO) throws DoesNotExistPackageException {

        Optional<Package> packageOp = packageJpaRepository.findById(id);

        if (packageOp.isEmpty()) {
            throw new DoesNotExistPackageException(DOES_NOT_EXIST_PACKAGE.getMessage());
        }

        Package packageEntity = packageOp.get();

        packageEntity.updateTrackingNo(updateReqDTO);

    }

    @Transactional
    public void deletePackage(Long id) {

        Optional<Package> packageOp = packageJpaRepository.findById(id);

        if (packageOp.isEmpty()) {
            throw new DoesNotExistPackageException(DOES_NOT_EXIST_PACKAGE.getMessage());
        }

        Package packageEntity = packageOp.get();

        packageJpaRepository.delete(packageEntity);
    }
}
