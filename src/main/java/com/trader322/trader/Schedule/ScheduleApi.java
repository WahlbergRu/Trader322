package com.trader322.trader.Schedule;

import com.trader322.trader.DataGrabber.PoloniexCurrencyGrabber;
import com.trader322.trader.Services.PoloniexAnalyze;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ScheduleApi {

    @Autowired
    PoloniexCurrencyGrabber poloniexCurrencyGrabber;

    @Autowired
    PoloniexAnalyze poloniexAnalyze;

    ScheduleApi(
            PoloniexCurrencyGrabber poloniexCurrencyGrabber,
            PoloniexAnalyze poloniexAnalyze
    ) {
        this.poloniexCurrencyGrabber = poloniexCurrencyGrabber;
        this.poloniexAnalyze = poloniexAnalyze;
    }

    @Scheduled(fixedRate = 60000)
    private void grabData() {
        this.poloniexCurrencyGrabber.GrabInformation();
        this.poloniexAnalyze.checkCurrency();
//        ChatBot chatBot = new ChatBot("MzkzMDA3MzQxNzk0MDk5MjAx.DRvgTA.XX76MoRzAHYiSBJxah3tE-Qid3g");
//        chatBot.send
//        this.chatBot = new ChatBot("MzkzMDA3MzQxNzk0MDk5MjAx.DRvgTA.XX76MoRzAHYiSBJxah3tE-Qid3g");

    }


}
