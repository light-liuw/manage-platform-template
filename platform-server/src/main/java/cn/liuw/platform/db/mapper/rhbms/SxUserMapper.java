package cn.liuw.platform.db.mapper.rhbms;


import cn.liuw.platform.db.domain.rhbms.SxUser;
import cn.liuw.platform.db.domain.rhbms.response.SxUserXmlResponse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * @author liuw
 * @date 2020/8/26
 */
public interface SxUserMapper extends BaseMapper<SxUser> {

    /**
     * 查询生成xml的用户信息
     */
    List<SxUserXmlResponse> selectUserXmlList();

}
