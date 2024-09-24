package io.github.mangocrisp.spring.taybct.demo;

import cn.hutool.extra.pinyin.engine.pinyin4j.Pinyin4jEngine;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author XiJieYin <br> 2024/6/3 17:17
 */
@SpringBootTest
public class Pinyin {

    @Test
    public void test1() throws BadHanyuPinyinOutputFormatCombination {
        Pinyin4jEngine engine = new Pinyin4jEngine();
        String pinyin = engine.getPinyin("李a晓b明", "");
        System.out.println(pinyin);
    }
}
