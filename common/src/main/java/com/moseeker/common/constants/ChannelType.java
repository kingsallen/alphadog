package com.moseeker.common.constants;

import com.moseeker.common.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 渠道
 * <p>Company: MoSeeker</P>
 * <p>date: Nov 7, 2016</p>
 * <p>Email: wjf2255@gmail.com</p>
 *
 * @author wjf
 */
public enum ChannelType {

    NONE(0,"default","默认",""){
        @Override
        public String getOrigin(String origin) {

            return null;
        }
    },
    JOB51(1, "51job", "前程无忧", "common_retrieval_flow") {
        @Override
        public String getOrigin(String origin) {
            String result;
            if (StringUtils.isNullOrEmpty(origin)) {
                result = String.valueOf("1000000000000000");
            } else {

                result = getResult(origin, 16);
//				if(origin.length() >= 16) {
//					if(origin.charAt(origin.length()-16) == '0') {
//						result = String.valueOf(Long.valueOf(origin)+1000000000000000l);
//					} else {
//						result = origin;
//					}
//				} else {
//					result = String.valueOf(Long.valueOf(origin)+1000000000000000l);
//				}
            }
            return result;
        }
    }, LIEPIN(2, "liepin", "猎聘", "common_retrieval_flow") {
        @Override
        public String getOrigin(String origin) {
            String result;
            if (StringUtils.isNullOrEmpty(origin)) {
                result = String.valueOf("100000000000000000");
            } else {
                result = getResult(origin, 18);
//				if(origin.length() >= 18) {
//					if(origin.charAt(origin.length()-18) == '0') {
//						result = String.valueOf(Long.valueOf(origin)+100000000000000000l);
//					} else {
//						result = origin;
//					}
//				} else {
//					result = String.valueOf(Long.valueOf(origin)+100000000000000000l);
//				}
            }
            return result;
        }
    }, ZHILIAN(3, "zhaopin", "智联招聘", "common_retrieval_flow") {
        @Override
        public String getOrigin(String origin) {
            String result;
            if (StringUtils.isNullOrEmpty(origin)) {
                result = String.valueOf("10000000000000000");
            } else {
                result = getResult(origin, 17);
//				if(origin.length() >= 17) {
//					if(origin.charAt(origin.length()-17) == '0') {
//						result = String.valueOf(Long.valueOf(origin)+10000000000000000l);
//					} else {
//						result = origin;
//					}
//				} else {
//					result = String.valueOf(Long.valueOf(origin)+10000000000000000l);
//				}
            }
            return result;
        }
    }, LINKEDIN(4, "linkedin", "领英", "common_retrieval_flow") {
        @Override
        public String getOrigin(String origin) {

            return null;
        }
    }, ALIPAY(5, "alipay", "支付宝", "alipay_retrieval_flow") {
        @Override
        public String getOrigin(String origin) {
            String result;
            if (StringUtils.isNullOrEmpty(origin)) {
                result = String.valueOf("1000000000000000000");
            } else {
                result = getResult(origin, 19);
            }
            return result;
        }
    }, VERYEAST(6, "veryeast", "最佳东方", "common_retrieval_flow") {
        @Override
        public String getOrigin(String origin) {
            return null;
        }
    }, JOB1001(7, "job1001", "一览英才", "common_retrieval_flow") {
        @Override
        public String getOrigin(String origin) {
            return null;
        }
    }, JOBSDB(8, "jobsdb", "JobsDB", "common_retrieval_flow") {
        @Override
        public String getOrigin(String origin) {
            return null;
        }
    }, CARNOC(9, "carnoc", "民航招聘", "common_retrieval_flow") {
        @Override
        public String getOrigin(String origin) {
            return null;
        }
    }, MAIMAI(10,"maimai","脉脉","common_retrieval_flow"){
        @Override
        public String getOrigin(String origin) {
            return null;
        }
    },JOB58(11, "58.com", "58同城", "common_retrieval_flow"){
        @Override
        public String getOrigin(String origin) {
            String result;
            if (StringUtils.isNullOrEmpty(origin)) {
                result = "10000000000000000000000000000000000";
            } else {
                result = getResult(origin, 35);
            }
            return result;
        }
    }, TW104(12, "104", "104", "common_retrieval_flow") {
        @Override
        public String getOrigin(String origin) {
            return null;
        }
    }, UPLOAD51(20, "upload51", "51上传", "talent_upload") {
        @Override
        public String getOrigin(String origin) {
            String result;
            if (StringUtils.isNullOrEmpty(origin)) {
                result = "10000000000000000000";
            } else {
                result = getResult(origin, 20);
            }
            return result;
        }
    }, UPLOADZP(21, "uploadZP", "智联上传", "talent_upload") {
        @Override
        public String getOrigin(String origin) {
            String result;
            if (StringUtils.isNullOrEmpty(origin)) {
                result = "100000000000000000000";
            } else {
                result = getResult(origin, 21);
            }
            return result;
        }
    }, UPLOADLIEP(22, "uploadLieP", "猎聘上传", "talent_upload") {
        @Override
        public String getOrigin(String origin) {
            String result;
            if (StringUtils.isNullOrEmpty(origin)) {
                result = "1000000000000000000000";
            } else {
                result = getResult(origin, 22);
            }
            return result;
        }
    }, UPLOADEARST(23, "uploadEarst", "最佳东方", "talent_upload") {
        @Override
        public String getOrigin(String origin) {
            String result;
            if (StringUtils.isNullOrEmpty(origin)) {
                result = "10000000000000000000000";
            } else {
                result = getResult(origin, 23);
            }
            return result;
        }
    }, UPLOADLINK(24, "uploadlink", "英领上传", "talent_upload") {
        @Override
        public String getOrigin(String origin) {
            String result;
            if (StringUtils.isNullOrEmpty(origin)) {
                result = "100000000000000000000000";
            } else {
                result = getResult(origin, 24);
            }
            return result;
        }
    }, UPLOADPROFILE(25, "uploadprofile", "简历上传", "talent_upload") {
        @Override
        public String getOrigin(String origin) {
            String result;
            if (StringUtils.isNullOrEmpty(origin)) {
                result = "1000000000000000000000000";
            } else {
                result = getResult(origin, 25);
            }
            return result;
        }
    }, UPLOADJOBSDB(26, "uploadJobsDB", "JobsDB上传", "talent_upload") {
        @Override
        public String getOrigin(String origin) {
            String result;
            if (StringUtils.isNullOrEmpty(origin)) {
                result = "10000000000000000000000000";
            } else {
                result = getResult(origin, 26);
            }
            return result;
        }
    }, UPLOADCARNOC(27, "uploadCarnoc", "民航招聘上传", "talent_upload") {
        @Override
        public String getOrigin(String origin) {
            String result;
            if (StringUtils.isNullOrEmpty(origin)) {
                result = "100000000000000000000000000";
            } else {
                result = getResult(origin, 27);
            }
            return result;
        }
    }, UPLOADGRADUATES(28, "uploadGraduates", "应届生（上传）", "talent_upload") {
        @Override
        public String getOrigin(String origin) {
            String result;
            if (StringUtils.isNullOrEmpty(origin)) {
                result = "1000000000000000000000000000";
            } else {
                result = getResult(origin, 28);
            }
            return result;
        }
    },MVHOUSEJOB51UPLOAD(31, "mvHouse(51)upload", "前程无忧（主投导入）", "mvHouse"){
        @Override
        public String getOrigin(String origin) {
            String result;
            if (StringUtils.isNullOrEmpty(origin)) {
                result = "1000000000000000000000000000000";
            } else {
                result = getResult(origin, 31);
            }
            return result;
        }
    },MVHOUSEJOB51DOWNLOAD(32, "mvHouse(51)download", "前程无忧（下载导入）", "mvHouse"){
        @Override
        public String getOrigin(String origin) {
            String result;
            if (StringUtils.isNullOrEmpty(origin)) {
                result = "10000000000000000000000000000000";
            } else {
                result = getResult(origin, 32);
            }
            return result;
        }
    },MVHOUSEZHILIANUPLOAD(33, "mvHouse(ZhiLian)upload", "智联招聘（主投导入）", "mvHouse"){
        @Override
        public String getOrigin(String origin) {
            String result;
            if (StringUtils.isNullOrEmpty(origin)) {
                result = "100000000000000000000000000000000";
            } else {
                result = getResult(origin, 33);
            }
            return result;
        }
    },MVHOUSEZHILIANDOWNLOAD(34, "mvHouse(ZhiLian)download", "智联招聘（下载导入）", "mvHouse"){
        @Override
        public String getOrigin(String origin) {
            String result;
            if (StringUtils.isNullOrEmpty(origin)) {
                result = "1000000000000000000000000000000000";
            } else {
                result = getResult(origin, 34);
            }
            return result;
        }
    };

    ChannelType(int value, String name, String alias, String retriveName) {
        this.value = value;
        this.name = name;
        this.alias = alias;
        this.retriveName = retriveName;
    }

    /*
     当lengthNum大于数组长度时，重新创建数组，并将原有数组纳入到新创建的数组中去
     */
    private static String charArrayAdd(char[] array1, int lengthNum) {
        String result = "";
        char[] array = new char[lengthNum];
        array[0] = '1';
        for (int i = 1; i < array.length - array1.length; i++) {
            array[i] = '0';
        }

        for (int i = array.length - array1.length, k = 0; i < array.length && k < array1.length; i++, k++) {
            array[i] = array1[k];
        }
        return charArrayToConvertString(array);
    }

    /*
     将char数组转化城字符串
     */
    private static String charArrayToConvertString(char[] array) {
        String result = "";
        for (char item : array) {
            result += String.valueOf(item);
        }
        return result;
    }

    /*
     根据来源获取新的来源
     首先比较lengthNum和origin.length()
     1，如果大于则执行：当lengthNum大于数组长度时，重新创建数组，并将原有数组纳入到新创建的数组中去
     2，小于等于：
       1，所求位数正好是1，则原来的origin不变
       2，所求为0;则改为变为0
     由于long最大长度19位，所以转换成数组来计算
     */
    private static String getResult(String origin, int lengthNum) {
        String result = "";
        if (origin.length() >= lengthNum) {
            if (origin.charAt(origin.length() - lengthNum) == '0') {
                char[] array = origin.toCharArray();
                array[origin.length() - lengthNum] = '1';
                result = charArrayToConvertString(array);
            } else {
                result = origin;
            }
        } else {
            result = charArrayAdd(origin.toCharArray(), lengthNum);
        }
        return result;
    }

    private int value = 0;                //渠道值
    private String name = null;            //渠道名称
    private String alias = null;        //渠道别名
    private String retriveName;

    public abstract String getOrigin(String origin);

    private static final String BINDING = "position/binding"; //帐号绑定请求名称
    private static final String REMAIN_NUM = "position/remain"; //帐号绑定请求名称

    private static final Map<Integer, ChannelType> intToEnum
            = new HashMap<Integer, ChannelType>();

    static { // Initialize map from constant name to enum constant
        for (ChannelType op : values())
            intToEnum.put(op.getValue(), op);
    }


    public static boolean containsChannelType(int value) {
        return intToEnum.containsKey(value);
    }

    public static ChannelType instaceFromInteger(int value) {
        return intToEnum.get(value);
    }

    public int getValue() {
        return this.value;
    }

    /**
     * 返回该渠道的绑定请求地址
     *
     * @param domain
     * @param domain chaos域名
     * @return
     */
    public String getBindURI(String domain) {
        return domain + "/" + name + "/" + BINDING;
    }

    /**
     * 返回该渠道的绑定请求地址
     *
     * @param domain chaos域名
     * @return
     */
    public String getRemain(String domain) {
        return domain + "/" + name + "/" + REMAIN_NUM;
    }

    /**
     * 返回该渠道的可发布职位数的请求地址
     *
     * @param domain chaos域名
     * @return
     */
    public String getRemainURI(String domain) {
        return domain + "/" + name + "/" + REMAIN_NUM;
    }

    public String getAlias() {
        return alias;
    }

    public String getRetriveName() {
        return retriveName;
    }

    /**
     * 简历回收 中  profile_profile.source（简历来源）的值
     *
     * @return
     */
    public int getRetrievalSource() {
        return 210 + value;
    }
}
