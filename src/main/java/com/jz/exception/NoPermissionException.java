package com.jz.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author jack zhang
 * @version 1.0
 * @description: TODO
 * @date 2023/10/2 10:27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoPermissionException extends RuntimeException{

    private String msg;

}
