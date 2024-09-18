package cn.lchospital.baby.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class StudentDto {
    private String studentId;
    private String studentName;
    private String avatar;
}
