package com.phoenixhell.authbackend.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author phoenixhell
 * @since 2022/1/27 0027-上午 9:30
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyException extends RuntimeException {
    private Integer code;
    private String message;
}
