package cz.upce.fei.nnpiacv.dto;

import cz.upce.fei.nnpiacv.domain.Profile;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponseDto {
    private Long id;
    private String email;
    private String password;
    private Integer version;
    private Profile profile;
    private boolean active;

}
