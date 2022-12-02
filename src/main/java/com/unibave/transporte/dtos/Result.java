package com.unibave.transporte.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Result {
  private AddressComponents[] address_components;
  private String formatted_address;
  private Geometry geometry;
}
