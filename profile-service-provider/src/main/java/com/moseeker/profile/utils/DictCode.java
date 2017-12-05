package com.moseeker.profile.utils;

/**
 * Created by lucky8987 on 17/10/11.
 */
public class DictCode {

    /**
     0	请选择企业规模<br/>
     1	少于15人<br/>
     2	15-50人<br/>
     3	50-150人<br/>
     4	150-500人<br/>
     5	500-1000人<br/>
     6	1000-5000人<br/>
     7	5000-10000人<br/>
     8	10000人以上<br/>
     * @param topValue
     * @return
     */
    public static int companyScale(int topValue) {
        if (topValue < 15) {
            return 1;
        } else if (topValue < 50) {
            return 2;
        } else if (topValue < 150) {
            return 3;
        } else if (topValue < 500) {
            return 4;
        } else if (topValue < 1000) {
            return 5;
        } else if (topValue < 5000) {
            return 6;
        } else if (topValue < 10000) {
            return 7;
        } else if (topValue >= 10000) {
            return 8;
        } else {
            return 0;
        }
    }

    /**
     0	女 <br/>
     1	男 <br/>
     2	不限 <br/>
     * @param gender
     * @return
     */
    public static int gender(String gender) {
        switch (gender) {
            case "男":
                return 1;
            case "女":
                return 2;
            case "保密":
                return 3;
            default:
                return 3;
        }
    }

    /**
     0	未填写 <br/>
     1	2k以下 <br/>
     2	2k-4k <br/>
     3	4k-6k <br/>
     4	6k-8k <br/>
     5	8k-10k <br/>
     6	10k-15k <br/>
     7	15k-25k <br/>
     8	25k及以上 <br/>
     * @param topValue
     * @return
     */
    public static int salary(int topValue){
        if (topValue < 2000) {
            return 1;
        } else if (topValue < 4000) {
            return 2;
        } else if (topValue < 6000) {
            return 3;
        } else if (topValue < 8000) {
            return 5;
        } else if (topValue < 10000) {
            return 6;
        } else if (topValue < 15000) {
            return 7;
        } else if (topValue >= 25000) {
            return 8;
        } else {
            return 0;
        }
    }

    /**
     * 工作性质
     * @param workType
     * @return
     */
    public static int workType(String workType) {
        switch (workType) {
            case "全职":
                return 1;
            case "兼职":
                return 2;
            case "实习":
                return 3;
            default:
                return 0;
        }
    }
}
