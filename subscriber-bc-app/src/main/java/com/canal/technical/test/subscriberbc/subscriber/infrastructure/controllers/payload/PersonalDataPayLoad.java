package com.canal.technical.test.subscriberbc.subscriber.infrastructure.controllers.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;

public record PersonalDataPayLoad(@JsonProperty  String firstName,
                                  @JsonProperty String lastName,
                                  @JsonProperty  @Email String mail,
                                  @JsonProperty @Pattern(regexp="(^$|(0)([1-9])([0-9]{8}))") String phoneNumber) {

}
