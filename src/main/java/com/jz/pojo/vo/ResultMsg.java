package com.jz.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author jack zhang
 * @version 1.0
 * @description: TODO
 * @date 2023/10/1 20:55
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultMsg {

    private Integer code;

    private String msg;

    private Object data;

    private Long total;

    public static ResultMsg SUCCESS(String msg, Object data, Long total){
        return new ResultMsg(1,msg,data,total);
    }

    public static ResultMsg FAIL(String msg){
        return new ResultMsg(0,msg,null,null);
    }

}
