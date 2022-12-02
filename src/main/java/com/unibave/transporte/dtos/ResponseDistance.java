package com.unibave.transporte.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ResponseDistance {

    @JsonProperty("rows")
    private Rows [] rows;
}
