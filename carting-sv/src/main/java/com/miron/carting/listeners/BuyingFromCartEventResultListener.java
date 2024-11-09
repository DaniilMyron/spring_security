package com.miron.carting.listeners;

import com.miron.carting.exceptions.InvalidMessageException;
import com.miron.carting.services.impl.CartService;
import com.miron.core.converter.StringPayloadDeserializer;
import com.miron.core.converter.UsernameDeserializer;
import com.miron.core.message.BuyingFromCartStatusEnum;
import com.miron.core.message.ChangeBalanceStatusEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class BuyingFromCartEventResultListener {
    @Autowired
    private CartService cartService;
    private static final Logger LOGGER = LoggerFactory.getLogger(BuyingFromCartEventResultListener.class);

    @KafkaListener(topics = "miron-buying-from-cart-event-result-carting", groupId = "groupId")
    public void listens(final String serializedBuyingFromCartEventResult) {
        LOGGER.info("Received serialized buying from cart event result: {}", serializedBuyingFromCartEventResult);
        try {
            var retrievedJsonObject = StringPayloadDeserializer.readStringAsJSONObject(serializedBuyingFromCartEventResult);
            var payloadStatus = retrievedJsonObject.getEnum(BuyingFromCartStatusEnum.class, "status");

            var username = retrievedJsonObject.getString("authenticatedUsername");
            username = UsernameDeserializer.readUsernameFromPayload(username);
            if(payloadStatus == BuyingFromCartStatusEnum.CANCELLED){
                LOGGER.error("Not pass validation: {}", retrievedJsonObject.getJSONArray("productsInCart"));

                var canceledProductsCount = retrievedJsonObject.getJSONObject("count");
                cartService.cancellBuyingFromCart(canceledProductsCount);
                cartService.changeUserBalance(username, ChangeBalanceStatusEnum.REJECTED);
            } else if(payloadStatus == BuyingFromCartStatusEnum.CONFIRMED){
                cartService.changeUserBalance(username, ChangeBalanceStatusEnum.CONFIRMED);
            }
        } catch(final InvalidMessageException ex) {
            LOGGER.error("Invalid message received: {}", serializedBuyingFromCartEventResult);
        }
    }
}