package com.trader322.trader.Services;


import com.trader322.trader.ChatBot.BotMessageListener;
import com.trader322.trader.Model.PoloniexModel;
import com.trader322.trader.Repository.PoloniexCurrencyRepository;
import com.trader322.trader.Utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Stream;

import static com.trader322.trader.Utils.ConsoleColors.*;

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

        System.out.println(poloniexModelList);

        String discordResponse;

        for (Map.Entry<String, List<PoloniexModel>> entry : mapPoloniexModel.entrySet()) {
            Long currentTime = timestamp.getTime();
            PoloniexModel lastPoloniex = entry.getValue().get(entry.getValue().size() - 1);

            entry.getValue().forEach(
                (PoloniexModel poloniexModel) -> {

                    if (Utils.timeBorders(poloniexModel.getPoloniexIdentity().getTimestamp(), currentTime, 60001)){
                        this.botMessageListener.SendMessage(this.botMessageListener.pumpAndDumpsChannel, "test");


                        System.out.println(WHITE + ("https://poloniex.com/exchange#"+poloniexModel.getName()));

//                            logic of 1 minute
                        System.out.println(
                                YELLOW_BOLD + "1 minute: " +
                                BLUE + poloniexModel.getName() + " " +
                                YELLOW + (((lastPoloniex.getLast() - poloniexModel.getLast())/ lastPoloniex.getLast()) * 100) + GREEN + "%"
                        );
                    } else if (Utils.timeBorders(poloniexModel.getPoloniexIdentity().getTimestamp(), currentTime, 120000)) {
                        System.out.println(
                                YELLOW_BOLD + "2 minute: " +
                                BLUE + poloniexModel.getName() + " " +
                                YELLOW + (((lastPoloniex.getLast() - poloniexModel.getLast())/ lastPoloniex.getLast()) * 100) + GREEN + "%");
                    } else if (Utils.timeBorders(poloniexModel.getPoloniexIdentity().getTimestamp(), currentTime, 300000)) {
                        System.out.println(
                                YELLOW_BOLD + "5 minute: " +
                                BLUE + poloniexModel.getName() + " " +
                                YELLOW + (((lastPoloniex.getLast() - poloniexModel.getLast())/ lastPoloniex.getLast()) * 100) + GREEN + "%");
                    } else if (Utils.timeBorders(poloniexModel.getPoloniexIdentity().getTimestamp(), currentTime, 600000)) {
                        System.out.println(
                                YELLOW_BOLD + "10 minutes: " +
                                        BLUE + poloniexModel.getName() + " " +
                                        YELLOW + (((lastPoloniex.getLast() - poloniexModel.getLast())/ lastPoloniex.getLast()) * 100) + GREEN + "%");
                    } else if (Utils.timeBorders(poloniexModel.getPoloniexIdentity().getTimestamp(), currentTime, 900000)) {
                        System.out.println(
                                YELLOW_BOLD + "15 minutes: " +
                                        BLUE + poloniexModel.getName() + " " +
                                        YELLOW + (((lastPoloniex.getLast() - poloniexModel.getLast())/ lastPoloniex.getLast()) * 100) + GREEN + "%");
                    } else if (Utils.timeBorders(poloniexModel.getPoloniexIdentity().getTimestamp(), currentTime, 1200000)) {
                        System.out.println(
                                YELLOW_BOLD + "20 minutes: " +
                                BLUE + poloniexModel.getName() + " " +
                                YELLOW + (((lastPoloniex.getLast() - poloniexModel.getLast())/ lastPoloniex.getLast()) * 100) + GREEN + "%");
                    } else if (Utils.timeBorders(poloniexModel.getPoloniexIdentity().getTimestamp(), currentTime, 1500000)) {
                        System.out.println(
                                YELLOW_BOLD + "25 minutes: " +
                                        BLUE + poloniexModel.getName() + " " +
                                        YELLOW + (((lastPoloniex.getLast() - poloniexModel.getLast())/ lastPoloniex.getLast()) * 100) + GREEN + "%");
                    } else if (Utils.timeBorders(poloniexModel.getPoloniexIdentity().getTimestamp(), currentTime, 1800000)) {
                        System.out.println(
                                YELLOW_BOLD + "30 minutes: " +
                                        BLUE + poloniexModel.getName() + " " +
                                        YELLOW + (((lastPoloniex.getLast() - poloniexModel.getLast())/ lastPoloniex.getLast()) * 100) + GREEN + "%");
                    } else {

                    }
                }
            );

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
