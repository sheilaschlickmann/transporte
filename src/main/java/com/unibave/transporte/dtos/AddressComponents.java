package com.unibave.transporte.dtos;

import lombok.Data;

@Data
public class AddressComponents {
    private String long_name;
    private String short_name;
    private String[] types;
}
