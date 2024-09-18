package cn.lchospital.baby.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class MomentDto {
    private String momentId;
    private String content;
    private List<MediaDto> mediaDtos;
    private String userId;
    private String userName;
    private String avatar;
    private Long createTime;
}
