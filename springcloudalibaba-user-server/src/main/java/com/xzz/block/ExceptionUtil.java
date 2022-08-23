package com.xzz.block;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.xzz.User;
import org.springframework.web.bind.annotation.PathVariable;

public class ExceptionUtil {
    public static User exceptionHandler(@PathVariable("id") Long id, BlockException ex) {
        ex.printStackTrace();
        return new User(-1L,"限流了","限流了");
    }
}