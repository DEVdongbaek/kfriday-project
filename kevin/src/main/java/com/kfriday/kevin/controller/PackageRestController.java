package com.kfriday.kevin.controller;

import com.kfriday.kevin.common.ApiResponse;
import com.kfriday.kevin.dto.PackageDTO;
import com.kfriday.kevin.service.PackageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class PackageRestController {

    private final PackageService packageService;

    @PostMapping("/daily")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<?> savePackage(@Valid @RequestBody final PackageDTO.RequestDTO requestDTO) {

        packageService.savePackage(requestDTO);

        return ApiResponse.createSuccessWithNoContent();
    }


    @GetMapping("/daily/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<PackageDTO.ResponseDTO> getPackage(@PathVariable Long id) {

        PackageDTO.ResponseDTO responseDTO = packageService.getPackage(id);

        return ApiResponse.createSuccess(responseDTO);
    }


    @GetMapping("/dailies/{offset}/{limit}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<PackageDTO.ResponseDTOs> getPackages(@PathVariable int offset, @PathVariable int limit) {

        PackageDTO.ResponseDTOs responseDTOs = packageService.getPackages(offset, limit);

        return ApiResponse.createSuccess(responseDTOs);
    }


    @PutMapping("/daily/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<?> updatePackage(@PathVariable Long id, @Valid @RequestBody final PackageDTO.UpdateReqDTO requestDTO) {

        packageService.updatePackage(id, requestDTO);

        return ApiResponse.createSuccessWithNoContent();
    }

    @DeleteMapping("/daily/")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ApiResponse<?> deletePackage(@PathVariable Long id) {

        packageService.deletePackage(id);

        return ApiResponse.createSuccessWithNoContent();
    }
}
