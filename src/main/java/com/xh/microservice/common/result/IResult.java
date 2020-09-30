package com.xh.microservice.common.result;

import java.io.Serializable;

public interface IResult extends Serializable {
    String getMessage();
    int getCode();
}
