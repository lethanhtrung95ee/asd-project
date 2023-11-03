package asd.ecommercenew.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDtoRequest {

    private String email;

    private String username;

    private String firstName;

    private String lastName;

    private AddressDtoRequest addressDtoRequest;
}
