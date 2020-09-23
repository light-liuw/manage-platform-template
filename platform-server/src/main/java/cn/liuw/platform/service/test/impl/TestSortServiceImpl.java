package cn.liuw.platform.service.test.impl;


import cn.liuw.platform.db.domain.test.TestSort;
import cn.liuw.platform.db.mapper.test.TestSortMapper;
import cn.liuw.platform.service.test.TestSortService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author liuw
 * @date 2020/9/10
 */
@Service
@Slf4j
public class TestSortServiceImpl implements TestSortService {
    
    @Resource
    private TestSortMapper testSortMapper;
    
    @Override
    public String testSort() {
        List<TestSort> sortList = testSortMapper.selectList(null);
        sortList.stream().forEach(e -> {

            System.out.println(e.getSortbefore());
            System.out.println(e.getSortbefore().getClass());
            System.out.println(e.getSortbefore().getClass());
            System.out.println(e.getSortafter());
            
            boolean ba = e.getSortbefore() == e.getSortafter();

            System.out.println(ba);
            
//            // 1. 按逗号分割
//            String[] beforeArrStr = e.getSortbefore().split(",");
//
//            int[] beforeArrInt = Arrays.stream(beforeArrStr).mapToInt(Integer::parseInt).toArray();
//            
//            Arrays.sort(beforeArrInt);
//
//            String result = StringUtils.join(beforeArrInt, ',');
//            
//            e.setSortafter(result);
//            
//            testSortMapper.updateById(e);
//            
//            log.info("before: {}, result: {}", e.getSortbefore(),result);
            
        });
        return null;
    }
}
