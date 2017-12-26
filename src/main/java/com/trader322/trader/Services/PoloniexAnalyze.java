package com.trader322.trader.Services;


import com.trader322.trader.ChatBot.BotMessageListener;
import com.trader322.trader.Model.PoloniexModel;
import com.trader322.trader.Repository.PoloniexCurrencyRepository;
import com.trader322.trader.Utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service("poloniexAnalyze")
@Transactional
public class PoloniexAnalyze {

    @Autowired
    private PoloniexCurrencyRepository poloniexCurrencyRepository;

    private BotMessageListener botMessageListener;

    PoloniexAnalyze(
        PoloniexCurrencyRepository poloniexCurrencyRepository,
        BotMessageListener botMessageListener
    ){
        this.botMessageListener = botMessageListener;
        this.poloniexCurrencyRepository = poloniexCurrencyRepository;
    }

    public String coinText = "";

    public void sendMessage(PoloniexModel lastPoloniex, PoloniexModel poloniexModel, String messageInterval){
        double calculatedValue = ((lastPoloniex.getLast() - poloniexModel.getLast())/ lastPoloniex.getLast()) * 100;
        if (calculatedValue > 0.5 || calculatedValue < -0.5) {
            DecimalFormat df = new DecimalFormat("#.##");
            String value = df.format(calculatedValue);
            String helpValue;
            if (calculatedValue > 0.5){
                helpValue = new Date(poloniexModel.getPoloniexIdentity().getTimestamp()) + " " + poloniexModel.getName() + " " + value + "%";
            } else {
                helpValue = new Date(poloniexModel.getPoloniexIdentity().getTimestamp()) + " " + poloniexModel.getName() + " " + value + "%";
            }

            this.coinText += "\n " + messageInterval + helpValue;

        }
    }

//    TODO: change type if need
    public void checkCurrency(){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        List<PoloniexModel> poloniexModelList = poloniexCurrencyRepository.findByPoloniexIdentity_TimestampBetween(
            timestamp.getTime() - 1800000, timestamp.getTime() - 0
        );

        Map<String, List<PoloniexModel>> mapPoloniexModel= new HashMap<>();

        poloniexModelList.forEach(
            poloniexModel -> {
                String poloniexModelName = poloniexModel.getName();
                List<PoloniexModel> mapPoloniexModelName = mapPoloniexModel.get(poloniexModelName);
                if (mapPoloniexModelName == null) {
                    mapPoloniexModelName = new ArrayList<>();
                    mapPoloniexModel.put(poloniexModelName,mapPoloniexModelName);
                }
                mapPoloniexModelName.add(poloniexModel);
            }
        );

        String discordResponse;

        for (Map.Entry<String, List<PoloniexModel>> entry : mapPoloniexModel.entrySet()) {
            Long currentTime = timestamp.getTime();
            PoloniexModel lastPoloniex = entry.getValue().get(entry.getValue().size() - 1);

            this.coinText = "```";
            this.coinText += "\n https://poloniex.com/exchange#" + lastPoloniex.getName() + "[link]";

            AtomicInteger i = new AtomicInteger();
            entry.getValue().forEach(
                (PoloniexModel poloniexModel) -> {

                    if (Utils.timeBorders(poloniexModel.getPoloniexIdentity().getTimestamp(), currentTime, 60001)){
                        this.sendMessage(lastPoloniex, poloniexModel, "1 minute:  ");
                        i.getAndIncrement();
                    } else if (Utils.timeBorders(poloniexModel.getPoloniexIdentity().getTimestamp(), currentTime, 120000)) {
                        this.sendMessage(lastPoloniex, poloniexModel, "2 minute:  ");
                        i.getAndIncrement();
                    } else if (Utils.timeBorders(poloniexModel.getPoloniexIdentity().getTimestamp(), currentTime, 300000)) {
                        this.sendMessage(lastPoloniex, poloniexModel, "5 minute:  ");
                        i.getAndIncrement();
                    } else if (Utils.timeBorders(poloniexModel.getPoloniexIdentity().getTimestamp(), currentTime, 600000)) {
                        this.sendMessage(lastPoloniex, poloniexModel, "10 minute: ");
                        i.getAndIncrement();
                    } else if (Utils.timeBorders(poloniexModel.getPoloniexIdentity().getTimestamp(), currentTime, 900000)) {
                        this.sendMessage(lastPoloniex, poloniexModel, "15 minute: ");
                        i.getAndIncrement();
                    } else if (Utils.timeBorders(poloniexModel.getPoloniexIdentity().getTimestamp(), currentTime, 1200000)) {
                        this.sendMessage(lastPoloniex, poloniexModel, "20 minute: ");
                        i.getAndIncrement();
                    } else if (Utils.timeBorders(poloniexModel.getPoloniexIdentity().getTimestamp(), currentTime, 1500000)) {
                        this.sendMessage(lastPoloniex, poloniexModel, "25 minute: ");
                        i.getAndIncrement();
                    } else if (Utils.timeBorders(poloniexModel.getPoloniexIdentity().getTimestamp(), currentTime, 1800000)) {
                        this.sendMessage(lastPoloniex, poloniexModel, "30 minute: ");
                        i.getAndIncrement();
                    } else {

                    }
                }
            );


            this.coinText += "```";

            if (i.get() > 0){
                this.botMessageListener.SendMessage(
                        this.botMessageListener.pumpAndDumpsChannel,
                        this.coinText
                );
            }

//            System.out.println(entry.getValue());

//            Stream<PoloniexModel> streamSortedListPoloniexModel = entry.getValue()
//                    .stream()
//                    .sorted(Comparator.comparingLong(f -> f.getPoloniexIdentity().getTimestamp()));
//
//            PoloniexModel poloniexModelFirst = streamSortedListPoloniexModel.findFirst().get();
//
//            streamSortedListPoloniexModel.filter(
//                    poloniexModel -> {
//                        (poloniexModel.getLast() - poloniexModelFirst.getLast())
//                    }
//                );
//
//            System.out.println("item " + entry.getKey() + " count : ");
//
//            System.out.println("Item : " + entry.getKey() + " Count : " + entry.getValue());
        }
    }

}
