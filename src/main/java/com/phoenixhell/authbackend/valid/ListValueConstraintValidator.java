package com.phoenixhell.authbackend.valid;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.HashSet;
import java.util.Set;

/**
 * @author phoenixhell
 * @since 2021/11/13 0013-上午 10:40
 *
 * SpringBoot 新版本没有自动导入validation 程序包
 *
 * <dependency>
 *     <groupId>org.springframework.boot</groupId>
 *     <artifactId>spring-boot-starter-validation</artifactId>
 * </dependency>
 */

public class ListValueConstraintValidator implements ConstraintValidator<ListValue,Integer> {
    private final Set<Integer> set = new HashSet<>();

    @Override
    public void initialize(ListValue constraintAnnotation) {
        int[] values = constraintAnnotation.values();
        if (values.length > 0) {
            for (int v : values) {
                set.add(v);
            }
        }
    }

    /**
     * @param integer                    提交过来需要校验的值
     * @param constraintValidatorContext
     * @return
     */
    @Override
    public boolean isValid(Integer integer, ConstraintValidatorContext constraintValidatorContext) {
        return set.contains(integer);
    }
}
