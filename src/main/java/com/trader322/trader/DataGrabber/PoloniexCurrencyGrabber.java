package com.trader322.trader.DataGrabber;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trader322.trader.Model.PoloniexIdentity;
import com.trader322.trader.Model.PoloniexModel;
import com.trader322.trader.Repository.PoloniexCurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

@Service("poloniexCurrencyGrabber")
@Transactional
public class PoloniexCurrencyGrabber {

    @Autowired
    private PoloniexCurrencyRepository poloniexCurrencyRepository;

    PoloniexCurrencyGrabber(PoloniexCurrencyRepository poloniexCurrencyRepository) {
        this.poloniexCurrencyRepository = poloniexCurrencyRepository;
    }

//    public List<PoloniexCurrencyRepository> findByTimestamp {
//
//    }

    public void GrabInformation(){

        final String uri = "https://poloniex.com/public?command=returnTicker";


        RestTemplate restTemplate = new RestTemplate();
        String json = restTemplate.getForObject(uri, String.class);

        Map<String,PoloniexModel> map = new HashMap<String,PoloniexModel>();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        try {
            //convert JSON string to Map
            map = mapper.readValue(json, new TypeReference<HashMap<String,PoloniexModel>>(){});

        } catch (Exception e) {
            System.out.println(e);
//            logger.info("Exception converting {} to map", json, e);
        }

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        map.forEach((k, v) -> {
            v.setName(k);
            v.setPoloniexIdentity(new PoloniexIdentity(v.getId(), timestamp.getTime()));
            poloniexCurrencyRepository.save(v);
        });

    }


}
