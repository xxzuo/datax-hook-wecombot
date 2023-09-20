package com.xxzuo.wecombot;

import com.alibaba.datax.common.spi.Hook;
import com.alibaba.datax.common.util.Configuration;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
/**
 * @description
 * @author xxzuo
 * @date 2023/9/20 15:07
 **/
public class weComBot implements Hook {

    private static final Logger log = LoggerFactory.getLogger(weComBot.class);

    @Override
    public String getName() {
        return "weComBotHook";
    }

    @Override
    public void invoke(Configuration configuration, Map<String, Number> map) {
        log.info("job weComBot execute info {}", map.toString());
        String webHook = configuration.getString("job.weComBot.webhook", "");
        if (StringUtils.isBlank(webHook)) {
            log.info("job.weComBot.webhook not config! can not send msg to bot!");
            return;
        }
        String title = configuration.getString("job.weComBot.title", "");
        String mode = configuration.getString("job.weComBot.mode", "ERROR");

        String sendTemplate =
                "# %s \n" +
                        " - 读写记录总数:%d \n" +
                        " - 读写失败记录总数:%d ";

        Number readerNum = map.get("readSucceedRecords");
        Number errorNum = map.get("totalErrorRecords");

        try {
            String ALL = "ALL";
            String ERROR = "ERROR";
            if (ALL.equalsIgnoreCase(mode)) {
                weComBotUtils.sendMessageByWeComBotMarkdown(webHook, String.format(sendTemplate,
                        title,
                        readerNum == null ? 0 : readerNum.longValue(),
                        errorNum == null ? 0 : errorNum.longValue())
                );
            } else if (ERROR.equalsIgnoreCase(mode) && errorNum != null && errorNum.longValue() > 0) {
                weComBotUtils.sendMessageByWeComBotMarkdown(webHook, String.format(sendTemplate,
                        title,
                        readerNum == null ? 0 : readerNum.longValue(),
                        errorNum.longValue())
                );
            }
        } catch (Exception e) {
            log.error("hook error", e);
        }
    }
}
