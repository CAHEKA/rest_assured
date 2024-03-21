package PojoClass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor()
@NoArgsConstructor(force = true)
public class UserCred {
    private String username;
    private String password;
}