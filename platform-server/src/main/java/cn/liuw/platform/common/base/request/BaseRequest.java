package cn.liuw.platform.common.base.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author liuw
 * @date: 2020-08-20
 */
@Data
@NoArgsConstructor
@ApiModel(value = "请求基本对象")
public class BaseRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 数据起始位置
     */
    @ApiModelProperty(value = "数据起始位置", example = "10")
    private Integer startIndex;

    /**
     * 查询当前页
     */
    @ApiModelProperty(value = "当前页", example = "1")
    private Integer page;

    /**
     * 每页显示的条数
     */
    @ApiModelProperty(value = "每页显示条数", example = "10")
    private Integer pageSize;

    /**
     * 获取排序字段
     */
    @ApiModelProperty(value = "排序字段", example = "create_time")
    private String orderColumn;

    /**
     * 获取排序方式
     */
    @ApiModelProperty(value = "排序方式", example = "ASC OR DESC")
    private String orderDir;

    public Integer getStartIndex() {
        // 传递是page的时候
        if (null != page) {
            return this.pageSize * (this.page - 1);
        } else {
            return startIndex;
        }
    }

    // 设置默认值
    public Integer getPage() {
        return page == null ? 1 : page;
    }
    
    // 设置默认值
    public Integer getPageSize() {
        return pageSize == null ? 10 : pageSize;
    }
}
