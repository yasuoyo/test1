package com.example.test;

import com.example.test.bean.UserBean;
import com.example.test.service.UserService;
import org.elasticsearch.common.geo.GeoDistance;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.GeoDistanceQueryBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestApplicationTests {

    @Autowired
    UserService userService;

    @Test
    public void contextLoads() {
        UserBean userBean = userService.loginIn("a","a");
        System.out.println("该用户ID为：");
        System.out.println(userBean.getId());
    }

    @Test
    public void test() {
        Map<String, Object> map1 = new HashMap<>();
        Map<String, Object> map2 = new HashMap<>();
        List<Map<String, Object>> testData1 = new ArrayList<>();
        List<Map<String, Object>> testData2 = new ArrayList<>();
        map1.put("id", "001");
        map1.put("name", "小明");
        map2.put("id", "002");
        map2.put("name", "小李");
        testData1 = testData(100);
        testData1.add(map1);
        testData1.add(map2);
        testData2 = testData(10000);
        //下面我们找出testData1中id=001和002在testData2中的年龄
        //
        long begin1 = Calendar.getInstance().getTimeInMillis();
        for (Map<String, Object> m1 : testData1) {
            for (Map<String, Object> m2 : testData2) {
                if (m1.get("id").equals(m2.get("id"))) {
                    m1.put("age", m2.get("age"));
                }
            }
        }
        long end1 = Calendar.getInstance().getTimeInMillis();
        System.out.println("第一种方法的执行时间" + (end1 - begin1));
        //第二种方法先把testData2转map
        long begin2 = Calendar.getInstance().getTimeInMillis();
        Map mm = ListToMap(testData2, "id");
        for (Map<String, Object> m1 : testData1) {
            m1.put("age", ((List<Map<String, Object>>) mm.get(m1.get("id"))).get(0).get("age"));
        }
        long end2 = Calendar.getInstance().getTimeInMillis();
        System.out.println("第二种方法的执行时间" + (end2 - begin2));

    }

    public Map<String, Object> ListToMap(List<Map<String, Object>> list, String key) {
        Map<String, Object> map = new HashMap<>();
        for (Map<String, Object> m : list) {
            String id = m.get(key) + "";
            if (!map.containsKey(id)) {
                map.put(id, new ArrayList<Map<String, Object>>());
            }
            List<Map<String, Object>> temp = (List<Map<String, Object>>) map.get(id);
            temp.add(m);
        }
        return map;
    }

    public List<Map<String, Object>> testData(int k) {
        List<Map<String, Object>> list = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            Map<String, Object> m = new HashMap<>();
            m.put("id", i + "");
            m.put("age", i);
            list.add(m);
        }
        Map<String, Object> map5 = new HashMap<>();
        Map<String, Object> map6 = new HashMap<>();
        map5.put("id", "001");
        map5.put("age", 26);
        map6.put("id", "002");
        map6.put("age", 27);
        list.add(map5);
        list.add(map6);
        return list;
    }

    @Test
    public void test11() {
        String srcLat = "36.105215";
        String srcLon = "120.384428";
        String dstLat = "36.283843";
        String dstLon = "120.421221";
        double distance = GeoDistance.ARC.calculate(Double.valueOf(srcLat), Double.valueOf(srcLon), Double.valueOf(dstLat), Double.valueOf(dstLon), DistanceUnit.METERS);
        System.out.println("距离为:"+distance+"米");
    }

    @Test
    public void test12() {
      List list = new ArrayList<>();
      list.add("1");
      list.add("2");
      String[] strArray = new String[list.size()];
      System.out.println(Arrays.toString(list.toArray(strArray)));
    }

}
