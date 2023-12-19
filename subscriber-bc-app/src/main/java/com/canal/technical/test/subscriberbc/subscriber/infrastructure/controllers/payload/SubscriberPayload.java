package com.canal.technical.test.subscriberbc.subscriber.infrastructure.controllers.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;

public record SubscriberPayload(@JsonProperty String subscriberId,
                                @JsonProperty @NotNull @NotBlank @NotEmpty String firstName,
                                @JsonProperty @NotNull @NotBlank @NotEmpty String lastName,
                                @JsonProperty @NotNull @NotBlank @NotEmpty @Email String mail,
                                @JsonProperty @NotNull @NotBlank @NotEmpty @Pattern(regexp="(^$|(0)([1-9])([0-9]{8}))") String phoneNumber,
                                @JsonProperty boolean isActive) {

}
