package cn.lchospital.baby.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class MediaVo {
    private String mediaId;
    private String url;
    private String jumpUrl;
}
