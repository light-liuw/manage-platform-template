package cn.liuw.platform.service.system.impl;

import cn.liuw.platform.db.domain.system.SystemOrg;
import cn.liuw.platform.db.domain.system.response.OrgTreeResponse;
import cn.liuw.platform.db.mapper.system.SystemOrgMapper;
import cn.liuw.platform.service.system.SystemOrgService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
@CacheConfig(cacheNames = "ORG")
public class SystemOrgServiceImpl implements SystemOrgService {

    @Resource
    private SystemOrgMapper systemOrgMapper;

    @Override
    @Cacheable(key = "'TREE'")
    public List<OrgTreeResponse> getOrgTree() {
        List<OrgTreeResponse> responseList = new ArrayList<>();

        // 查询有效的组织机构
        QueryWrapper<SystemOrg> orgQueryWrapper = new QueryWrapper<>();
        orgQueryWrapper.eq("status", "1");
        List<SystemOrg> orgList = systemOrgMapper.selectList(orgQueryWrapper);

        // 添加父节点
        orgList.stream().forEach(e -> {
            if (0 == e.getParentId()) {
                OrgTreeResponse orgTree = new OrgTreeResponse();
                orgTree.setId(e.getId());
                orgTree.setName(e.getName());
                orgTree.setCode(e.getCode());
                responseList.add(orgTree);
            }
        });

        // 递归添加子组织机构
        responseList.stream().forEach(e -> {
            e.setChildren(getChildTree(e.getId(), orgList));
        });

        // 增加虚拟父节点
        List<OrgTreeResponse> resultList = new ArrayList<>();
        OrgTreeResponse result = new OrgTreeResponse();
        result.setId(0);
        result.setName("组织结构树");
        result.setChildren(responseList);
        resultList.add(result);

        return resultList;
    }

    // 递归子机构
    private List<OrgTreeResponse> getChildTree(Integer parentId, List<SystemOrg> orgList) {

        List<OrgTreeResponse> children = new ArrayList<>();

        orgList.stream().forEach(e -> {
            OrgTreeResponse orgTree = new OrgTreeResponse();
            if (null != e.getParentId() && e.getParentId() == parentId) {
                orgTree.setId(e.getId());
                orgTree.setName(e.getName());
                orgTree.setCode(e.getCode());
                children.add(orgTree);
            }
        });

        // 递归子机构
        children.stream().forEach(e -> {
            e.setChildren(getChildTree(e.getId(), orgList));
        });

        // 递归退出条件
        if (children.size() == 0) {
            return null;
        }

        return children;
    }
}
