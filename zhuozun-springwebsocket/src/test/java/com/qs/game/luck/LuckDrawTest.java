package com.qs.game.luck;

import org.junit.Before;
import org.junit.Test;

import java.util.*;

/**
 * Created by zun.wei on 2018/11/30 14:51.
 * Description:
 */
public class LuckDrawTest {

    private static List<Award> awards = new ArrayList<>();

    class Award {
        int id;
        String name;
        int type; // 1金币；2钻石；3红包
        int gailv;

        Award(int id, String name, int type, int gailv) {
            this.id = id;
            this.name = name;
            this.type = type;
            this.gailv = gailv;
        }

        public int getGailv() {
            return gailv;
        }

        public void setGailv(int gailv) {
            this.gailv = gailv;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    @Before
    public void before() {
        awards.add(new Award(1, "金币1000", 1, 2380));
        awards.add(new Award(3, "钻石5", 2, 2480));
        awards.add(new Award(2, "金币2000", 1, 2180));
        awards.add(new Award(5, "红包0.1", 3, 480));
        awards.add(new Award(4, "钻石10", 2, 2380));
        awards.add(new Award(6, "红包0.2", 3, 100));
    }

    @Test
    public void luck01() {
        Map<Integer, Integer> treeMap = new TreeMap<>();
        int sumGailv = 0;
        for (Award award : awards) {
            sumGailv += award.getGailv();
            treeMap.put(sumGailv, award.id);
        }

        List<Integer> ids = new LinkedList<>();
        for (int i = 0; i < 10000000; i++) {
            ids.add(getAwardId(sumGailv, treeMap));
        }

        long id1 = ids.stream().filter(e -> e == 1).count();
        long id2 = ids.stream().filter(e -> e == 2).count();
        long id3 = ids.stream().filter(e -> e == 3).count();
        long id4 = ids.stream().filter(e -> e == 4).count();
        long id5 = ids.stream().filter(e -> e == 5).count();
        long id6 = ids.stream().filter(e -> e == 6).count();

        // id1,id2,id3,id4,id5,id6= 2380,2180,2480,2380,480,100
        System.out.println("id1,id2,id3,id4,id5,id6= " + id1 + "," + id2 + "," + id3 + "," + id4 + "," + id5 + "," + id6);

    }

    private int getAwardId(int sumGailv, Map<Integer, Integer> treeMap) {
        Random random = new Random();
        int randomNum = random.nextInt(sumGailv) + 1;
        int resultId = 0;
        for (Map.Entry<Integer, Integer> entry : treeMap.entrySet()) {
            Integer keyValue = entry.getKey();
            if (randomNum <= keyValue) {
                resultId = entry.getValue();
                break;
            }
        }
        return resultId;
    }


    private int luckAward() {

        return 0;
    }

}
