package com.moseeker.application.service.application;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import com.moseeker.application.domain.ChannelEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author: huangwenjian
 * @desc: application的origin和新channel的转换器
 * @since: 2019-11-22 11:03
 */
@Component
public class ChannelApplicationOriginConverter {

    /**
     * origin -> channelcode,source列表
     * @param origin
     * @return
     */
    public List<ChannelEntity> origin2Channel(Integer origin) {
        List<Integer> origins = origin2DecList(origin);
        List<ChannelEntity> channels = Lists.newArrayList();
        for (Integer standardOrigin : origins) {
            ChannelApplicationOrigin channelApplicationOrigin = Dict.getInstance().get(standardOrigin);
            channelApplicationOrigin.setOrigin(standardOrigin);
            channels.add(new ChannelEntity(channelApplicationOrigin.getCode(), channelApplicationOrigin.getSourceId()));
        }
        return channels;
    }

    /**
     * channelcode,sourceId -> origin
     * @param code
     * @param sourceId
     * @return
     */
    public Integer channel2Origin(String code, Integer sourceId) {
        ChannelApplicationOrigin value = new ChannelApplicationOrigin(code, sourceId);
        Integer origin = Dict.getInstance().getKey(value);
        return origin;
    }

    static class ChannelApplicationOrigin {

        /**
         * 原申请中的origin字段
         */
        private Integer origin;
        /**
         * 新的渠道code
         */
        private String code;
        /**
         * 新的来源id
         */
        private Integer sourceId;

        public ChannelApplicationOrigin() {
        }

        public ChannelApplicationOrigin(String code, Integer sourceId) {
            this.code = code;
            this.sourceId = sourceId;
        }

        public ChannelApplicationOrigin(Integer origin, String code, Integer sourceId) {
            this.origin = origin;
            this.code = code;
            this.sourceId = sourceId;
        }

        public Integer getOrigin() {
            return origin;
        }

        public void setOrigin(Integer origin) {
            this.origin = origin;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public Integer getSourceId() {
            return sourceId;
        }

        public void setSourceId(Integer sourceId) {
            this.sourceId = sourceId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ChannelApplicationOrigin that = (ChannelApplicationOrigin) o;
            return Objects.equal(code, that.code) &&
                    Objects.equal(sourceId, that.sourceId);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(code, sourceId);
        }
    }

    static class Dict extends LinkedHashMap<Integer, ChannelApplicationOrigin> {
        private static volatile Dict dict = null;

        private Dict() {
            // PC
            this.put(1, new ChannelApplicationOrigin("PU0005", 1));
            // 企业号
            this.put(2, new ChannelApplicationOrigin("PU0012", 1));
            // 聚合号
            this.put(4, new ChannelApplicationOrigin("PU0006", 1));
            // 51
            this.put(8, new ChannelApplicationOrigin("PU0001", 1));
            // 智联
            this.put(16, new ChannelApplicationOrigin("PU0002", 1));
            // 猎聘
            this.put(32, new ChannelApplicationOrigin("PU0003", 1));
            // 支付宝
            this.put(64, new ChannelApplicationOrigin("PU0011", 1));
            // 简历抽取
            this.put(128, new ChannelApplicationOrigin("PU0020", 1));
            // 员工代投
            this.put(256, new ChannelApplicationOrigin("PU0015", 1));
            // 程序导入（和黄简历导入)
            this.put(512, new ChannelApplicationOrigin("PU0020", 1));
            // email申请
            this.put(1024, new ChannelApplicationOrigin("PU0019", 1));
            // 最佳东方
            this.put(2048, new ChannelApplicationOrigin("PU0007", 1));
            // 一览英才
            this.put(4096, new ChannelApplicationOrigin("PU0008", 1));
            // JobsDB
            this.put(8192, new ChannelApplicationOrigin("PU0010", 1));
            // 民航
            this.put(16384, new ChannelApplicationOrigin("PU0009", 1));
            // 员工主动推荐
            this.put(32768, new ChannelApplicationOrigin("PU0013", 3));
            // 内推
            this.put(65536, new ChannelApplicationOrigin("PU0013", 3));
            // 58同城
            this.put(131072, new ChannelApplicationOrigin("PU0004", 1));
            // 间接内推(联系内推)
            this.put(262144, new ChannelApplicationOrigin("PU0013", 4));
            // 间接内推(邀请投递)
            this.put(524288, new ChannelApplicationOrigin("PU0013", 6));
            // 间接内推(转发投递)
            this.put(1048576, new ChannelApplicationOrigin("PU0013", 5));
            // 老员工回聘
            this.put(2097152, new ChannelApplicationOrigin("PU0016", 1));
            // 员工转岗
            this.put(4194304, new ChannelApplicationOrigin("PU0017", 1));
            // 猎头上传
            this.put(8388608, new ChannelApplicationOrigin("PU0014", 1));
            // hr推荐
            this.put(16777216, new ChannelApplicationOrigin("PU0018", 7));
            // 台湾104
            this.put(33554432, new ChannelApplicationOrigin("PU0021", 1));
        }

        public static Dict getInstance() {
            if (dict == null) {
                synchronized (Dict.class) {
                    if (dict == null) {
                        dict = new Dict();
                    }
                }
            }
            return dict;
        }

        public Integer getKey(ChannelApplicationOrigin value) {
            List<Integer> keyList = new ArrayList<>();
            for (Integer key : this.keySet()) {
                if (this.get(key).equals(value)) {
                    keyList.add(key);
                }
            }
            if (keyList != null && keyList.size() > 0) {
                return keyList.get(keyList.size() - 1);
            }
            return 16777216;
        }
    }

    /**
     * 将十进制origin转为list
     * @param origin
     * @return
     */
    private List<Integer> origin2DecList(Integer origin) {
        List<Integer> decList = Lists.newArrayList();
        // 获取二进制字符串
        String binStr = Integer.toBinaryString(origin);
        for (int i = 0; i < binStr.length(); i++) {
            Character letter = binStr.charAt(i);
            if (letter.equals('1')) {
                StringBuffer sb = new StringBuffer("1");
                for (int j = 0; j < binStr.length() - i - 1; j++) {
                    sb.append(0);
                }
                System.out.println(sb);
                Integer dec = Integer.parseInt(sb.toString(), 2);
                decList.add(dec);
            }
        }
        return decList;
    }

    public static void main(String[] args) {
        ChannelApplicationOriginConverter converter = new ChannelApplicationOriginConverter();
        List<Integer> integers = converter.origin2DecList(16777216);
        System.out.println(integers);
    }
}

