package com.kfriday.kevin.exception;

import lombok.Getter;

@Getter
public enum MessageCode {

    DOES_NOT_EXIST_PACKAGE("해당 패키지가 존재하지 않습니다.");

    // 메시지를 반환하는 메서드
    private final String message;

    // Enum 생성자
    MessageCode(String message) {
        this.message = message;
    }

}