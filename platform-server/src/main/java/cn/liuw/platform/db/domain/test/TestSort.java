package cn.liuw.platform.db.domain.test;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@TableName("test_sort")
@Data
@NoArgsConstructor
public class TestSort implements Serializable {


    private static final long serialVersionUID = -9112952813391480068L;

    private Integer id;

    private Integer sortbefore;

    private Integer sortafter;
}
        