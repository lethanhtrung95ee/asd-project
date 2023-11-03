package asd.ecommercenew.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDtoResponse implements Serializable {

    private int id;

    private String email;

    private String username;

    private String firstName;

    private String lastName;

    private boolean active;

    private boolean isDeleted;

}
