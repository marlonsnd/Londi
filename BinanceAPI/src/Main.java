import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.binance.BinanceExchange;
import org.knowm.xchange.binance.dto.account.BinanceBalance;
import org.knowm.xchange.binance.dto.account.BinanceAccountInformation;
import org.knowm.xchange.binance.service.BinanceAccountServiceRaw;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.exceptions.RateLimitExceededException;
import org.knowm.xchange.service.account.AccountService;


/*
 * Main.java
 * Copyright (c) LONDI.
 * Criado em 08/03/2023 - 01:59:31
 * @author Márlon Schoenardie
 */
/**
 *
 * @author Márlon Schoenardie
 */
public class Main {
    
    public static void chamadaAnterio() throws IOException {
        
    }

    public static void main(String[] args) throws IOException {
        try {
            ExchangeSpecification specification = new ExchangeSpecification(BinanceExchange.class);
            specification.setApiKey("2RemMySWNvjacNFZZRP4DZMnLRatUUsLPas70uL4aIWSoCnnLFeCSwngtspFMT8w");
            specification.setSecretKey("KE87Wo7GoTaQ6vhwvoF5HGmEXUSePzQLwNUmE9BV9jDFtCwDOBJKC5TvobVbX7Ei");
            Exchange binanceExchange = ExchangeFactory.INSTANCE.createExchange(specification);

            AccountInfo accountInfo =  binanceExchange.getAccountService().getAccountInfo();
            Wallet wallet = accountInfo.getWallet();
            Map<Currency, Balance> balances = wallet.getBalances();

            //---LISTA SALDO DAS MOEDAS DA CARTEIRA DO CLIENTE---
            for (Map.Entry<Currency, Balance> entry : balances.entrySet()) {
                Currency currency = entry.getKey();
                Balance balance = entry.getValue();
                BigDecimal available = balance.getAvailable();
                if (available.doubleValue() >=   0.00000001) {
                    System.out.println("Moeda: " + currency + " - Saldo disponível: " + available);
                }
            }

//            ---CONVERTE MOEDA PARA USDT--
//            MarketDataService marketDataService = binanceExchange.getMarketDataService();
//            TradeService tradeService = binanceExchange.getTradeService();
//            CurrencyPair currencyPair = new CurrencyPair("BTC", "USDT");
//            BigDecimal amount = new BigDecimal("0.1"); // trocar 0.1 BTC por USDT
//            BigDecimal price = marketDataService.getTicker(currencyPair).getLast();
//            LimitOrder limitOrder = new LimitOrder(Order.OrderType.ASK, amount, currencyPair, "", null, price);
//            String orderId = tradeService.placeLimitOrder(limitOrder);
//            System.out.println("Order ID: " + orderId);

       } catch (Throwable t) {
            t.printStackTrace();
       }
    }
}
