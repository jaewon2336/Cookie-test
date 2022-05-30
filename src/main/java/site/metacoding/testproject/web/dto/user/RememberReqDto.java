package site.metacoding.testproject.web.dto.user;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class RememberReqDto {
    private String username;
    private String remember;
}
