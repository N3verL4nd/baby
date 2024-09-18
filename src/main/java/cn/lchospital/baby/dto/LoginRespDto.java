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
public class LoginRespDto {
    private String token;
    private List<StudentDto> studentList;
}
