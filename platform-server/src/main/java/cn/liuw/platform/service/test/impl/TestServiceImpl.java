package cn.liuw.platform.service.test.impl;


import cn.liuw.platform.db.domain.system.request.ValidObjectRequest;
import cn.liuw.platform.db.domain.test.TestSort;
import cn.liuw.platform.db.mapper.test.TestSortMapper;
import cn.liuw.platform.service.test.TestService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liuw
 * @date 2020/9/10
 */
@Service
@Slf4j
public class TestServiceImpl implements TestService {
    
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

    /**
     * 实现思路：配置校验规则，根据对象反射取属性校验
     * @param validObjectRequest
     */
    @Override
    public String validObject(ValidObjectRequest validObjectRequest) {
        StringBuilder result = new StringBuilder();
        // 校验规则
        HashMap<String, String[]> rules = new HashMap<>();
        rules.put("all", new String[]{"username","password","type"});
        rules.put("1", new String[]{"value1"});
        rules.put("2", new String[]{"value2"});
        rules.put("3", new String[]{"value3","value33"});
        
        try {
            Map<String, Object[]> objectMap = objectToMap(validObjectRequest);
            // 校验类型 all
            result.append(checkRules("all", rules, objectMap))
                    //  objectType
                    .append(checkRules(validObjectRequest.getType(), rules, objectMap));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    /**
     * 将Object对象里面的属性和值转化成Map对象
     *
     * @param obj
     * @return
     * @throws IllegalAccessException
     */
    public Map<String, Object[]> objectToMap(Object obj) throws IllegalAccessException {
        Map<String, Object[]> map = new HashMap<String,Object[]>();
        Class<?> clazz = obj.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            String fieldName = field.getName();
            Object[] objects = new Object[2];
            objects[0] = field.getType();
            objects[1] = field.get(obj);
            map.put(fieldName, objects);
        }
        return map;
    }

    /**
     * 根据类型和规则校验Map
     * @param type
     * @param rules
     * @param objectMap
     * @return
     */
    public String checkRules(String type, HashMap<String, String[]> rules, Map<String, Object[]> objectMap) {
        StringBuilder checkResult =new StringBuilder();
        // 校验 all 类型
        String[] fields = rules.get(type);
        if(fields != null && fields.length > 0) {
            for (String field : fields) {
                Object[] objects = objectMap.get(field);
                if((Class<String>) objects[0] == String.class ) {
                    // 如果是字符串
                    System.out.println(field + "is String!");
                    if(StringUtils.isBlank(String.valueOf(objects[1]))) {
                        checkResult.append(field).append(",");
                    }
                } else if((Class<Date>) objects[0] == Date.class) {
                    // 如果是日期
                    System.out.println(field + "is Date!");
                    System.out.println("date is " + (Date)objects[1]);
                    if( (Date)objects[1] == null) {
                        checkResult.append(field).append(",");
                    }
                }
                
                
            }
        } 
        return checkResult.toString();
    }
    
}
