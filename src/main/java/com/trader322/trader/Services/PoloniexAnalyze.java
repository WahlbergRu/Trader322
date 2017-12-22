package com.trader322.trader.Services;


import com.trader322.trader.Model.PoloniexModel;
import com.trader322.trader.Repository.PoloniexCurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Service("poloniexAnalyze")
@Transactional
public class PoloniexAnalyze {

    @Autowired
    private PoloniexCurrencyRepository poloniexCurrencyRepository;

    PoloniexAnalyze(PoloniexCurrencyRepository poloniexCurrencyRepository){
        this.poloniexCurrencyRepository = poloniexCurrencyRepository;
    }

//    TODO: change type if need
    public void checkCurrency(){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        List<PoloniexModel> poloniexModelList = poloniexCurrencyRepository.findByPoloniexIdentity_TimestampBetween(
            timestamp.getTime() - 1800000, timestamp.getTime() - 1
        );

//        poloniexModelList.stream().map(
//            poloniexModel -> {
//
//            }
//        );
//
//        System.out.println(poloniexModelList);
    }

}
