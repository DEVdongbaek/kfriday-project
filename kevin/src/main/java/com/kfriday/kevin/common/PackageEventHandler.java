package com.kfriday.kevin.common;

import com.kfriday.kevin.dto.PackageEventDTO;
import com.kfriday.kevin.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
@Slf4j
public class PackageEventHandler {

    private final ImageService imageService;

    @Async
    @EventListener
    public void process(PackageEventDTO packageEventDTO) {

        log.info("EventListener를 통해 Event가 비동기로 Driven 되었습니다.");

        imageService.saveImages(packageEventDTO);

    }


}
