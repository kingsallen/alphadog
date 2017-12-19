package com.moseeker.position.service.position.veryeast.pojo;

import com.moseeker.position.service.position.qianxun.Degree;
import com.moseeker.position.service.position.qianxun.WorkType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VeryEastTransferStrategy {
    private static Logger logger= LoggerFactory.getLogger(VeryEastTransferStrategy.class);

    public enum VeryEastDegree{
        //0:无 1:大专 2:本科 3:硕士 4:MBA 5:博士 6:中专 7:高中 8: 博士后 9:初中',
        None("0","不限", Degree.None),
        DEGREE1("1","初中",null),
        DEGREE2("2","高中",Degree.HighSchool),
        DEGREE3("3","中技",null),
        SpecicalSecondarySchool("4","中专", Degree.SpecicalSecondarySchool),
        JuniorCollege("5","大专", Degree.JuniorCollege),
        College("6","本科", Degree.College),
        Master("7","硕士", Degree.Master),
        MBA("7","硕士", Degree.MBA),
        Doctor("8","博士", Degree.Doctor),
        ;


        VeryEastDegree(String code, String text, Degree degree) {
            this.code = code;
            this.text = text;
            this.degree = degree;
        }

        public static String moseekerToOther(int d){
            Degree degree= Degree.instanceFromCode(d+"");
            for(VeryEastDegree t:values()){
                if(t.degree==degree){
                    return t.code;
                }
            }
            logger.info("no matched Job1001Degree");
            return "";
        }

        private String code;
        private String text;
        private Degree degree;
    }

    public enum WorkMode{
        QUANZHI("1","全职", WorkType.fullTime),
        JUNKE("1","全职",WorkType.contract),
        JIANZHI("2","兼职",WorkType.partTime),
        SHIXI("3","实习",WorkType.practice),
        LINSHI("4","临时",WorkType.other)
        ;
        /**/

        WorkMode(String code, String text, WorkType employment_type) {
            this.code = code;
            this.text = text;
            this.employment_type = employment_type;
        }

        public static String moseekerToOther(int employment_type ){
            for(WorkMode t:values()){
                if(t.employment_type.getValue()==employment_type){
                    return t.code;
                }
            }
            logger.info("no matched WorkMode");
            return "";
        }

        private String code;
        private String text;
        private WorkType employment_type;
    }
}
