package com.moseeker.position.service.position.base.refresh.handler;

import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OccupationResultHandlerUtil {
    public static void generateNewCode(List<AbstractOccupationResultHandler.Occupation> occupationList, String... seeds) {

        try {
            String code = "";
            MessageDigest digest = MessageDigest.getInstance("MD5");
            Map<Integer, Map<List<String>, String>> allLevelOccupation = new HashMap<>();
            for (int i = 0; i < occupationList.size(); i++) {
                AbstractOccupationResultHandler.Occupation o = occupationList.get(i);

                List<String> texts = o.getText();
                List<String> codes = o.getCode();

                for (int j = 1; j <= texts.size(); j++) {
                    List<String> occupationText = texts.subList(0, j);
                    Map<List<String>, String> oneLevelOccupation = getOrInitIfNotExist(allLevelOccupation, j);
                    if (!oneLevelOccupation.containsKey(occupationText)) {
                        code = md5(digest, concatKey(occupationText, seeds));
                        oneLevelOccupation.put(occupationText, code);
                    }
                    codes.add(oneLevelOccupation.get(occupationText).toString());

                }
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

    }

    private static Map<List<String>, String> getOrInitIfNotExist(Map<Integer, Map<List<String>, String>> allLevelOccupation, Integer j) {
        if (!allLevelOccupation.containsKey(j)) {
            allLevelOccupation.put(j, new HashMap<>());
        }
        return allLevelOccupation.get(j);
    }



    /**
     * 拼接需要加密的key
     *
     * @param texts
     * @param seeds
     * @return
     */
    private static String concatKey(List<String> texts, String... seeds) {
        StringBuilder key = new StringBuilder();
        for (String text : texts) {
            key.append("_" + text);
        }
        for (String seed : seeds) {
            key.append("_" + seed);
        }
        key.delete(0, 1);
        return key.toString();
    }

    private static String md5(MessageDigest digest, String str) throws NoSuchAlgorithmException {
        digest.reset();
        StringBuilder md5 = new StringBuilder();
        digest.update(str.getBytes());
        byte strDigest[] = digest.digest();
        for (int i = 0; i < strDigest.length; i++) {
            String param = Integer.toString((strDigest[i] & 0xff) + 0x100, 16);
            md5.append(param.substring(1));
        }
        return md5.toString();
    }
}
