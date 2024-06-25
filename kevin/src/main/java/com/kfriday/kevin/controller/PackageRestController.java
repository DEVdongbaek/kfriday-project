package com.kfriday.kevin.controller;

import com.kfriday.kevin.common.ApiResponse;
import com.kfriday.kevin.dto.PackageDTO;
import com.kfriday.kevin.service.PackageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1") // API version Prefix
@RequiredArgsConstructor
public class PackageRestController {

    private final PackageService packageService;

    @PostMapping("/package")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<?> savePackage(@Valid @RequestBody final PackageDTO.RequestDTO requestDTO) { // @Valid를 이용한 유효성 검증

        packageService.savePackage(requestDTO);

        return ApiResponse.createSuccessWithNoContent(); // 공통 API를 반환하기 위한 ApiResponse 객체 사용
    }


    @GetMapping("/package/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<PackageDTO.ResponseDTO> getPackage(@PathVariable Long id) {

        PackageDTO.ResponseDTO responseDTO = packageService.getPackage(id);

        return ApiResponse.createSuccess(responseDTO); // 공통 API를 반환하기 위한 ApiResponse 객체 사용
    }


    @GetMapping("/packages/{offset}/{limit}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<PackageDTO.ResponseDTOs> getPackages(@PathVariable int offset, @PathVariable int limit) {

        PackageDTO.ResponseDTOs responseDTOs = packageService.getPackages(offset, limit);

        return ApiResponse.createSuccess(responseDTOs); // 공통 API를 반환하기 위한 ApiResponse 객체 사용
    }


    @PutMapping("/package/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<?> updatePackage(@PathVariable Long id, @Valid @RequestBody final PackageDTO.UpdateReqDTO requestDTO) {

        packageService.updatePackage(id, requestDTO);

        return ApiResponse.createSuccessWithNoContent(); // 공통 API를 반환하기 위한 ApiResponse 객체 사용
    }


    @DeleteMapping("/package/")
    @ResponseStatus(HttpStatus.NO_CONTENT) // 각 HTTP Method에 맞는 HTTP 상태 코드 반환
    public ApiResponse<?> deletePackage(@PathVariable Long id) {

        packageService.deletePackage(id);

        return ApiResponse.createSuccessWithNoContent(); // 공통 API를 반환하기 위한 ApiResponse 객체 사용
    }
}
