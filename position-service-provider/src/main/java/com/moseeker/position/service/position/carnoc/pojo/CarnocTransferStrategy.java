package com.moseeker.position.service.position.carnoc.pojo;


import com.moseeker.position.service.position.qianxun.Degree;

public class CarnocTransferStrategy {
    public enum CarnocDegree {
        None(Degree.None, "大专"),
        SpecicalSecondarySchool(Degree.SpecicalSecondarySchool, "中专"),
        HighSchool(Degree.HighSchool, "高中"),
        JuniorCollege(Degree.JuniorCollege, "大专"),
        College(Degree.College, "本科"),
        Master(Degree.Master, "硕士"),
        Doctor(Degree.Doctor, "博士"),
        MBA(Degree.MBA, "MBA/EMBA");

        CarnocDegree(Degree degree, String carnocDegreeName) {
            this.degree = degree;
            this.carnocDegreeName = carnocDegreeName;
        }

        Degree degree;
        String carnocDegreeName;


        public String getCarnocDegreeName() {
            return carnocDegreeName;
        }

        public static String moseekerToCarnoc(String degree) {
            for (CarnocDegree carnocDegree : values()) {
                if (carnocDegree.degree.getValue().equals(degree)) {
                    return carnocDegree.getCarnocDegreeName();
                }
            }
            return "";
        }
    }

    public enum WorkExp {
        UNLIMITED("不限"),
        WITHIN_ONE("1年以内"),
        MORE_THEN_ONE("1年以上"),
        MORE_THEN_THREE("3年以上"),
        MORE_THEN_FIVE("5年以上"),
        MORE_THEN_TEN("10年以上"),;

        WorkExp(String carnocExp) {
            this.carnocExp = carnocExp;
        }

        String carnocExp;

        public String getCarnocExp() {
            return carnocExp;
        }

        public static String moseekerToCarnoc(String degree) {
            String carnoc = "";
            switch (degree) {
                case "0":
                    carnoc = UNLIMITED.carnocExp;
                    break;
                case "1":
                    carnoc = WITHIN_ONE.carnocExp;
                    break;
                case "2":
                    carnoc = MORE_THEN_ONE.carnocExp;
                    break;
                case "3":
                case "4":
                    carnoc = MORE_THEN_THREE.carnocExp;
                    break;
                case "5":
                case "6":
                case "7":
                case "8":
                case "9":
                    carnoc = MORE_THEN_FIVE.carnocExp;
                    break;
                default:
                    carnoc = MORE_THEN_TEN.carnocExp;
                    break;
            }
            return carnoc;
        }
    }
}
