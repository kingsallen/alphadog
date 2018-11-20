package com.moseeker.useraccounts.service.impl.employee;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.db.hrdb.tables.pojos.HrEmployeeCustomFields;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * @Author: jack
 * @Date: 2018/10/22
 */
public class EmployeeExtInfoToolTest {
    @Test
    public void verifyCustomFieldInfo() throws Exception {

        Map<Integer, List<String>> customValues1 = new HashMap<>();
        customValues1.put(1, new ArrayList<String>(){{add("你好");}});
        customValues1.put(2, new ArrayList<String>(){{add("CS");}});

        Map<Integer, List<String>> customValues2 = new HashMap<>();
        customValues2.put(4, new ArrayList<String>(){{add("你好");}});
        customValues2.put(2, new ArrayList<String>(){{add("CS");}});

        Map<Integer, List<String>> customValues3 = new HashMap<>();
        customValues3.put(4, new ArrayList<>());
        customValues3.put(2, new ArrayList<String>(){{add("CS");}});

        Map<Integer, List<String>> customValues4 = new HashMap<>();
        customValues4.put(1, new ArrayList<String>(){{add("你好");}});
        customValues4.put(2, new ArrayList<String>(){{add("CS");}});
        customValues4.put(3, new ArrayList<String>(){{add("hello world!");}});

        List<HrEmployeeCustomFields> confs = new ArrayList<>();
        HrEmployeeCustomFields hrEmployeeCustomFields1 = new HrEmployeeCustomFields();
        hrEmployeeCustomFields1.setId(1);
        hrEmployeeCustomFields1.setFvalues("[\"你好\", \"他好\", \"大家好\"]");
        hrEmployeeCustomFields1.setOptionType(0);
        confs.add(hrEmployeeCustomFields1);

        HrEmployeeCustomFields hrEmployeeCustomFields2 = new HrEmployeeCustomFields();
        hrEmployeeCustomFields2.setId(2);
        hrEmployeeCustomFields2.setFvalues("[\"技术\", \"产品\", \"CS\"]");
        hrEmployeeCustomFields2.setOptionType(0);
        confs.add(hrEmployeeCustomFields2);

        HrEmployeeCustomFields hrEmployeeCustomFields3 = new HrEmployeeCustomFields();
        hrEmployeeCustomFields3.setId(3);
        hrEmployeeCustomFields3.setOptionType(1);
        hrEmployeeCustomFields3.setFvalues("[]");
        confs.add(hrEmployeeCustomFields3);



        assertEquals(true, EmployeeExtInfoTool.verifyCustomFieldInfo(customValues1, confs));
        assertEquals(false, EmployeeExtInfoTool.verifyCustomFieldInfo(customValues2, confs));
        assertEquals(false, EmployeeExtInfoTool.verifyCustomFieldInfo(customValues3, confs));
        assertEquals(true, EmployeeExtInfoTool.verifyCustomFieldInfo(customValues4, confs));
    }

    @Test
    public void testMergeCustomFieldValue() {
        Map<Integer, List<String>> customValues1 = new HashMap<>();
        customValues1.put(1, new ArrayList<String>(){{add("你好");}});
        customValues1.put(2, new ArrayList<String>(){{add("CS");}});

        Map<Integer, List<String>> customValues2 = new HashMap<>();
        customValues2.put(4, new ArrayList<String>(){{add("你好");}});
        customValues2.put(2, new ArrayList<String>(){{add("CS");}});

        Map<Integer, List<String>> customValues3 = new HashMap<>();
        customValues3.put(4, new ArrayList<>());
        customValues3.put(2, new ArrayList<String>(){{add("CS");}});

        Map<Integer, List<String>> customValues4 = new HashMap<>();
        customValues4.put(1, new ArrayList<String>(){{add("你好");}});
        customValues4.put(2, new ArrayList<String>(){{add("CS");}});
        customValues4.put(3, new ArrayList<String>(){{add("hello world!");}});

        Map<Integer, List<String>> form1 = new HashMap<>();
        form1.put(1, new ArrayList<String>(){{add("他好");}});
        JSONArray result1 = new JSONArray();
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("1", new ArrayList<String>(){{add("他好");}});
        result1.add(jsonObject1);

        Map<Integer, List<String>> form2 = new HashMap<>();
        form2.put(1, new ArrayList<>());

        JSONArray result2 = new JSONArray();

        assertEquals(result1.toJSONString(), EmployeeExtInfoTool.mergeCustomFieldValue("[{\"1\":[\"你好\"]}]", form1));
        assertEquals(result2.toJSONString(), EmployeeExtInfoTool.mergeCustomFieldValue("[{\"1\":[\"你好\"]}]", form2));
    }
}