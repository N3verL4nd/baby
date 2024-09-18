package cn.lchospital.baby.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author N3verL4nd
 * @date 2020/3/25
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class UserLoginModel implements Serializable {

    private String userName;

    private String departmentId;

    private String departmentName;

}