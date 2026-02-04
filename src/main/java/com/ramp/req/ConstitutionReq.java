package com.ramp.req;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import com.ramp.enums.ConstitutionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConstitutionReq {
    @NotNull(message = "Constitution type is required")
    private ConstitutionType constitutionType;

    @Valid
    private List<PartnerDirectorReq> partnersDirectors;
}
