package cn.lchospital.baby.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class MomentVo {
    private String momentId;
    private String content;
    private List<MediaVo> mediaVos;
    private String userId;
    private String userName;
    private String avatar;
    private String url;
}
