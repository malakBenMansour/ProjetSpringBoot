package arctic.example.pi.payload.request;

import java.util.Date;
import java.util.Set;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.*;

import arctic.example.pi.entity.Role;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor

public class SignupRequest {
    @NotBlank
    @Size(min = 3, max = 20)
    @NonNull private String username;
 
    @NotBlank
    @Size(max = 50)
    @Email
    @NonNull private String email;
    
    private Set<String> role;
    
    @NotBlank
    @Size(min = 6, max = 40)
    @NonNull  private String password;
    
    @NotBlank
    @NonNull private String nom;
    @NotBlank
    @NonNull private String prenom;
    
    @NotBlank
    private String address;
    @NotBlank
    @Size(min = 8, max = 12)
    private String tel;
    
    private boolean enabled;
    private String verificationCode;
	private boolean accountVerified;

    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern="yyyy-MM-dd")
	private Date birth;



    private Long organisationId;
    
    
}
