package megabrain.gyeongnamgyeongmae.domain.user.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserItemSearchDto {

    @NotNull
    private FindStatus closed;

    @NotNull
    private Long page;

}
