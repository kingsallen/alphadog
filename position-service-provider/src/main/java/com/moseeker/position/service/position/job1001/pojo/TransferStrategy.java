package com.moseeker.position.service.position.job1001.pojo;

import com.moseeker.position.service.position.qianxun.Degree;
import com.moseeker.position.service.position.qianxun.WorkType;

public class TransferStrategy {
    public enum Job1001Degree{
        //0:无 1:大专 2:本科 3:硕士 4:MBA 5:博士 6:中专 7:高中 8: 博士后 9:初中',
        DEGREE0("0","不限", Degree.None),
        DEGREE1("90","博士后",Degree.PostDoctor),
        DEGREE2("80","博士",Degree.Doctor),
        DEGREE3("75","MBA",Degree.MBA),
        DEGREE4("70","硕士",Degree.Master),
        DEGREE5("60","本科",Degree.College),
        DEGREE6("50","大专",Degree.JuniorCollege),
        DEGREE7("40","中专",Degree.SpecicalSecondarySchool),
        DEGREE8("30","中技",null),
        DEGREE9("20","高中",null),
        DEGREE10("10","初中",null)
        ;

        Job1001Degree(String code, String text, Degree degree) {
            this.code = code;
            this.text = text;
            this.degree = degree;
        }

        public static String moseekerToJob1001(int d ){
            Degree degree=Degree.instanceFromCode(d+"");
            for(Job1001Degree t:values()){
                if(t.degree==degree){
                    return t.code;
                }
            }
            throw new IllegalArgumentException("no matched JobType");
        }

        private String code;
        private String text;
        private Degree degree;
    }


    public enum JobType{
        //0:全职，1：兼职：2：合同工 3:实习 9:其他',
        QUANZHI("quanzhi","全职",WorkType.fullTime),
        JIANZHI("jianzhi","兼职",WorkType.partTime),
        LINSHI("linshi","临时",WorkType.contract),
        SHIXI("shixi","实习",WorkType.practice),
        JUNKE("junke","均可",WorkType.other)
        ;
        /**/

        JobType(String code, String text, WorkType employment_type) {
            this.code = code;
            this.text = text;
            this.employment_type = employment_type;
        }

        public static String moseekerToJob1001(int employment_type ){
            for(JobType t:values()){
                if(t.employment_type.getValue()==employment_type){
                    return t.code;
                }
            }
            throw new IllegalArgumentException("no matched JobType");
        }

        private String code;
        private String text;
        private WorkType employment_type;
    }

    public enum Sex{
        MIS("mis","女",0),
        SIR("sir","男",1),
        UNLI("unli","不限",1)
        ;
        Sex(String code, String text, int gender) {
            this.code = code;
            this.text = text;
            this.gender = gender;
        }

        public static String moseekerToJob1001(int gender ){
            for(Sex t:values()){
                if(t.gender==gender){
                    return t.code;
                }
            }
            throw new IllegalArgumentException("no matched Sex");
        }

        private String code;
        private String text;
        private int gender;
    }

    public enum Target{
        TARGET0(0,"应届生",1),
        TARGET1(1,"社会人才",0),
        TARGET2(2,"社会人才或应届生均可",-1)
        ;

        Target(int code, String text, int candidate_source) {
            this.code = code;
            this.text = text;
            this.candidate_source = candidate_source;
        }

        public static int moseekerToJob1001(int candidate_source){
            for(Target t:values()){
                if(t.candidate_source==candidate_source){
                    return t.code;
                }
            }
            throw new IllegalArgumentException("no matched target");
        }

        private int code;
        private String text;
        private int candidate_source;
    }
}
