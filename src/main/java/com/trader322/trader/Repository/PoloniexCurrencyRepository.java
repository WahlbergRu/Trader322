package com.trader322.trader.Repository;

import com.trader322.trader.Model.PoloniexIdentity;
import com.trader322.trader.Model.PoloniexModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PoloniexCurrencyRepository extends JpaRepository<PoloniexModel, PoloniexIdentity>{
    List<PoloniexModel> findByPoloniexIdentity_TimestampBetween(long TimestampStart, long TimestampEnd);
}
