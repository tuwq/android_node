package com.tuwq.quickindex;

import android.text.TextUtils;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;

public class PinYinUtil {
    /**
     * 获取汉字的拼音
     * @param chinese
     * @return
     */
    public static String getPinYin(String chinese){
        if(TextUtils.isEmpty(chinese))return null;

        //输出的格式化对象，用来决定输出字母的大小写，是否带有音标之类
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.UPPERCASE);//设置大写字母
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);//设置不带有音标


        //由于只能对单个汉字进行获取，所以要将字符串转为字符数组，一个一个获取，最后拼接
        StringBuilder builder = new StringBuilder();
        char[] chars = chinese.toCharArray();

        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            //1.过滤空格
            if(Character.isWhitespace(c)){
                //如果是空格，则忽略即可
                continue;
            }

            //2.简单判断是否是汉字:一个字节范围是-128~127,所以汉字一定大于127
            if(c > 127){
                //说明有可能是汉字,那么利用Pinyin4j的api进行获取
                try {
                    //由于多音字的存在，返回的是数组，比如：单： {dan, chan ,shan}
                    String[] pinyinArr = PinyinHelper.toHanyuPinyinStringArray(c, format);
                    if(pinyinArr!=null){
                        //问题来了：取哪个拼音？答：暂时只能取第1个
                        //为啥呢？：首先大部分汉字只有一个读音，对于多音字的情况，由于我们实在无能为力去
                        //判断它的真实读音，也只能取第1个罢了。
                        builder.append(pinyinArr[0]);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    //如果异常，说明不是正确的汉字，直接忽略
                }
            }else {
                //说明肯定不是汉字，一般是英文字母,我们选择直接拼接
                builder.append(c);
            }

        }

        return builder.toString();
    }
}
