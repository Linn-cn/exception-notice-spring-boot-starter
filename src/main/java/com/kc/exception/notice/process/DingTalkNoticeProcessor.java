package com.kc.exception.notice.process;

import com.kc.exception.notice.content.DingTalkExceptionInfo;
import com.kc.exception.notice.content.DingTalkResult;
import com.kc.exception.notice.content.ExceptionInfo;
import com.kc.exception.notice.properties.DingTalkProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

/**
 * 钉钉异常信息通知具体实现
 *
 * @author kongchong
 */
@Slf4j
public class DingTalkNoticeProcessor implements INoticeProcessor {

    private final DingTalkProperties dingTalkProperties;

    private final RestTemplate restTemplate;

    public DingTalkNoticeProcessor(RestTemplate restTemplate,
                                   DingTalkProperties dingTalkProperties) {
        Assert.hasText(dingTalkProperties.getWebHook(), "DingTalk webHook must not be null");
        this.dingTalkProperties = dingTalkProperties;
        this.restTemplate = restTemplate;
    }

    @Override
    public void sendNotice(ExceptionInfo exceptionInfo) {
        DingTalkExceptionInfo dingDingNotice = new DingTalkExceptionInfo(exceptionInfo,
                dingTalkProperties);
        DingTalkResult result = restTemplate.postForObject(dingTalkProperties.getWebHook(), dingDingNotice, DingTalkResult.class);
        log.debug(String.valueOf(result));
    }

}
