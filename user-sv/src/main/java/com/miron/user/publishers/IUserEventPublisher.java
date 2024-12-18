package com.miron.user.publishers;

import com.miron.core.message.CheckBalanceStatusEnum;
import com.miron.core.models.ProductsInCartToReturn;
import com.miron.core.models.UserInfoForCheck;
import com.miron.user.domain.User;

public interface IUserEventPublisher {
    void publishUserRegistrationEvent(User user);
    void publishCheckBalanceEvent(String username, int requiredSum, int productRequiredId, CheckBalanceStatusEnum status);

    void publishUserInfoForCheck(UserInfoForCheck userInfoForCheck);

    void publishGetBackProductsInCart(ProductsInCartToReturn productsInCartToReturn);
}
