package com.unibave.transporte.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ResponseDistance {

    @JsonProperty("origin_addresses")
    private String[] origin_addresses;

    @JsonProperty("rows")
    private Rows [] rows;
}
