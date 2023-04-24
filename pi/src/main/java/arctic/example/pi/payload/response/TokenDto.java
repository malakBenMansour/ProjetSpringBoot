package arctic.example.pi.payload.response;

import lombok.Data;

@Data
public class TokenDto {

    String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}