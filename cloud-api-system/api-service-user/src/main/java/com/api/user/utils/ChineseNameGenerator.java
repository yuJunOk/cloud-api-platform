package com.api.user.utils;

import cn.hutool.core.lang.WeightRandom;
import cn.hutool.core.util.RandomUtil;

import java.util.*;

/**
 * 随机中文姓名生成工具类
 * 功能：
 * 1. 生成符合常见姓氏规则的中文名（单姓/复姓）
 * 2. 支持性别倾向（男/女/中性）
 * 3. 可控制是否包含生僻字
 * 4. 唯一性保障（生成不重复姓名）
 * @author pengYuJun
 */
public class ChineseNameGenerator {

    // 预定义数据集（静态常量）
    // ======================= 姓氏配置 =======================
    private static final List<String> COMMON_SURNAMES = Arrays.asList(
            "王", "李", "张", "刘", "陈", "杨", "赵", "黄", "周", "吴",
            "欧阳", "慕容", "司马", "诸葛", "长孙", "令狐"
    );

    /**
     * 姓氏权重（常见姓氏出现概率更高）
     */
    private static final WeightRandom<String> SURNAME_WEIGHT = new WeightRandom<>(
            Arrays.asList(
                    new WeightRandom.WeightObj<>("王", 0.2),
                    new WeightRandom.WeightObj<>("李", 0.15),
                    new WeightRandom.WeightObj<>("张", 0.15),
                    new WeightRandom.WeightObj<>("欧阳", 0.05),
                    new WeightRandom.WeightObj<>("司马", 0.03)
            )
    );

    // ======================= 名字配置 =======================
    // 常用汉字池（约2500个常用字，此处为示例，实际可扩展）
    private static final String COMMON_CHINESE_CHARS =
            "伟芳娜丽强秀英敏静杰刚宇平建华斌涛琳峰宁慧鑫勇艳超明雪洋轩婷";

    // 性别倾向字符池
    private static final String MALE_CHARS = "刚强勇毅俊峰浩鹏飞轩睿";
    private static final String FEMALE_CHARS = "婷娜芳琳媛妍慧娟雪莉";
    private static final String NEUTRAL_CHARS = "平华明杰鑫宁超洋";

    // ======================= 工具类配置 =======================
    private ChineseNameGenerator() {
    } // 私有构造，禁止实例化

    /**
     * 生成随机中文姓名
     *
     * @param gender           性别（null 表示中性）
     * @param allowRareSurname 是否允许生僻姓氏
     * @return 随机中文名，如 "王刚"、"欧阳雪"
     */
    public static String generate(Gender gender, boolean allowRareSurname) {
        String surname = getRandomSurname(allowRareSurname);
        String givenName = generateGivenName(gender, surname.length());
        return surname + givenName;
    }

    /**
     * 快速生成默认姓名（中性、常用姓氏）
     */
    public static String generate() {
        return generate(null, false);
    }

    /**
     * 生成批量唯一姓名
     *
     * @param count  需要生成的数量
     * @param gender 性别限制
     */
    public static Set<String> generateUniqueNames(int count, Gender gender) {
        Set<String> result = new LinkedHashSet<>();
        while (result.size() < count) {
            result.add(generate(gender, false));
        }
        return result;
    }

    // ======================= 私有方法 =======================
    private static String getRandomSurname(boolean allowRare) {
        return allowRare ?
                RandomUtil.randomEle(COMMON_SURNAMES) :
                SURNAME_WEIGHT.next();
    }

    private static String generateGivenName(Gender gender, int surnameLength) {
        int maxLength = (surnameLength > 1) ? 2 : 3; // 复姓时名字长度限制
        int targetLength = RandomUtil.randomInt(1, maxLength + 1);

        String pool = getCharPoolByGender(gender);
        return RandomUtil.randomString(pool, targetLength);
    }

    private static String getCharPoolByGender(Gender gender) {
        if (gender == null) {
            return COMMON_CHINESE_CHARS;
        }

        return switch (gender) {
            case MALE -> MALE_CHARS + NEUTRAL_CHARS;
            case FEMALE -> FEMALE_CHARS + NEUTRAL_CHARS;
            default -> COMMON_CHINESE_CHARS;
        };
    }

    // ======================= 枚举和配置类 =======================
    public enum Gender {
        /**
         *
         */
        MALE, FEMALE
    }

    // ======================= 使用示例 =======================
    public static void main(String[] args) {
        // 示例1: 生成10个中性姓名
        Set<String> names = ChineseNameGenerator.generateUniqueNames(10, null);
        System.out.println("随机姓名: " + names);

        // 示例2: 生成女性姓名（允许复姓）
        String femaleName = ChineseNameGenerator.generate(Gender.FEMALE, true);
        System.out.println("女性姓名: " + femaleName);

        // 示例3: 快速生成默认姓名
        System.out.println("默认姓名: " + ChineseNameGenerator.generate());
    }
}