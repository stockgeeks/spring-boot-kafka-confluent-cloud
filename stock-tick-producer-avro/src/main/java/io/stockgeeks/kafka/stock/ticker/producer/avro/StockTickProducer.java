package io.stockgeeks.kafka.stock.ticker.producer.avro;

import io.stockgeeks.stock.tick.avro.StockTick;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class StockTickProducer {

  private final KafkaTemplate<String, StockTick> kafkaTemplate;

  public StockTickProducer(KafkaTemplate<String, StockTick> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  public void produce(StockTick stockTick) {
    log.info("Produce stock tick: {}, {} {}", stockTick.getSymbol(), stockTick.getCurrency(), stockTick.getTradeValue());
    kafkaTemplate.send(StockTickProducerAvroApplication.TOPIC_NAME, stockTick.getSymbol(), stockTick);
  }
}