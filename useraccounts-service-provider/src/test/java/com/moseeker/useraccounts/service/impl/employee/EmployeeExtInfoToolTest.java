package com.moseeker.useraccounts.service.impl.employee;

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
        customValues2.put(3, new ArrayList<String>(){{add("你好");}});
        customValues2.put(2, new ArrayList<String>(){{add("CS");}});

        Map<Integer, List<String>> customValues3 = new HashMap<>();
        customValues3.put(3, new ArrayList<>());
        customValues3.put(2, new ArrayList<String>(){{add("CS");}});

        List<HrEmployeeCustomFields> confs = new ArrayList<>();
        HrEmployeeCustomFields hrEmployeeCustomFields1 = new HrEmployeeCustomFields();
        hrEmployeeCustomFields1.setId(1);
        hrEmployeeCustomFields1.setFvalues("[\"你好\", \"他好\", \"大家好\"]");
        confs.add(hrEmployeeCustomFields1);

        HrEmployeeCustomFields hrEmployeeCustomFields2 = new HrEmployeeCustomFields();
        hrEmployeeCustomFields2.setId(2);
        hrEmployeeCustomFields2.setFvalues("[\"技术\", \"产品\", \"CS\"]");
        confs.add(hrEmployeeCustomFields2);

        assertEquals(true, EmployeeExtInfoTool.verifyCustomFieldInfo(customValues1, confs));
        assertEquals(false, EmployeeExtInfoTool.verifyCustomFieldInfo(customValues2, confs));
        assertEquals(false, EmployeeExtInfoTool.verifyCustomFieldInfo(customValues3, confs));
    }

}