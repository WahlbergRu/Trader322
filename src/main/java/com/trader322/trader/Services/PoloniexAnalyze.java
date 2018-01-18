package com.trader322.trader.Services;


import com.sun.org.apache.xpath.internal.operations.Bool;
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
        if (calculatedValue > 2 || calculatedValue < -2) {
            DecimalFormat df = new DecimalFormat("#.##");
            String value = df.format(calculatedValue);
            String helpValue;
            if (calculatedValue > 0.5){
                helpValue = new Date(poloniexModel.getPoloniexIdentity().getTimestamp()) + " " + messageInterval + " " + poloniexModel.getName() + " " + value + "%";
            } else {
                helpValue = new Date(poloniexModel.getPoloniexIdentity().getTimestamp()) + " " + messageInterval + " " + poloniexModel.getName() + " " + value + "%";
            }

            this.coinText += "\n " + helpValue;

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
            this.coinText += "\n https://poloniex.com/exchange#" + lastPoloniex.getName() + "" +
                    "\n BVol:" + lastPoloniex.getBaseVolume()
                    + " Low/Last/High: " + String.format("%.8f", lastPoloniex.getLow24hr()) + "/" + String.format("%.8f", lastPoloniex.getLast()) + "/"+ String.format("%.8f", lastPoloniex.getHigh24hr());

            Boolean extremium = false;

            if (lastPoloniex.getLast()/lastPoloniex.getLow24hr() < 1.01){
                this.coinText += " ▼";
                extremium = true;
            }

            if (lastPoloniex.getLast()/lastPoloniex.getHigh24hr() > 0.99){
                this.coinText += " △";
                extremium = true;
            }

            if (extremium == true){

                String coinName = lastPoloniex.getName().split("_")[0];

                String cacheString = this.coinText;

                entry.getValue().forEach(
                        (PoloniexModel poloniexModel) -> {

                            if (Utils.timeBorders(poloniexModel.getPoloniexIdentity().getTimestamp(), currentTime, 60001)){
                                this.sendMessage(lastPoloniex, poloniexModel, "1 minute:  ");
                            } else if (Utils.timeBorders(poloniexModel.getPoloniexIdentity().getTimestamp(), currentTime, 120000)) {
                                this.sendMessage(lastPoloniex, poloniexModel, "2 minute:  ");
                            } else if (Utils.timeBorders(poloniexModel.getPoloniexIdentity().getTimestamp(), currentTime, 300000)) {
                                this.sendMessage(lastPoloniex, poloniexModel, "5 minute:  ");
                            } else if (Utils.timeBorders(poloniexModel.getPoloniexIdentity().getTimestamp(), currentTime, 600000)) {
                                this.sendMessage(lastPoloniex, poloniexModel, "10 minute: ");
                            } else if (Utils.timeBorders(poloniexModel.getPoloniexIdentity().getTimestamp(), currentTime, 900000)) {
                                this.sendMessage(lastPoloniex, poloniexModel, "15 minute: ");
                            } else if (Utils.timeBorders(poloniexModel.getPoloniexIdentity().getTimestamp(), currentTime, 1200000)) {
                                this.sendMessage(lastPoloniex, poloniexModel, "20 minute: ");
                            } else if (Utils.timeBorders(poloniexModel.getPoloniexIdentity().getTimestamp(), currentTime, 1500000)) {
                                this.sendMessage(lastPoloniex, poloniexModel, "25 minute: ");
                            } else if (Utils.timeBorders(poloniexModel.getPoloniexIdentity().getTimestamp(), currentTime, 1800000)) {
                                this.sendMessage(lastPoloniex, poloniexModel, "30 minute: ");
                            } else {

                            }
                        }
                );



                if (!cacheString.equals(this.coinText)){
                    this.coinText += "```";
                    this.botMessageListener.SendMessage(
                            coinName,
                            this.coinText
                    );
                }
            }
        }
    }

}
