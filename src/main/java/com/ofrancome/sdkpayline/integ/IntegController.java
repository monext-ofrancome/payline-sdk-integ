package com.ofrancome.sdkpayline.integ;

import com.payline.kit.utils.ConnectParams;
import com.payline.ws.model.*;
import com.payline.ws.wrapper.DirectPayment;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IntegController {

    @RequestMapping("/payline/{ref}/{contractNumber}")
    public String callPayline(
            @PathVariable final String ref,
            @PathVariable final String contractNumber) {

        final String accessKey = "myAccessKey";
        final String merchantId = "33804350758572";
        final String proxyHost = "myHost"; /* proxyHost (optional) */
        final String proxyPort = "myPort"; /* proxyPort (optional) */
        final String login = "myLogin"; /* proxyLogin (optional) */
        final String password = "myPassword"; /* proxyPassword (optional) */

        final ConnectParams connectParams = new ConnectParams(
                null, /* module */
                false, /* production */
                false, /* clientAuthentication */
                merchantId, /* Your merchant account login: merchantId */
                accessKey, /* Your access key to the Payline service: accessKey */
                proxyHost,
                proxyPort,
                login,      /* proxyLogin (optional) */
                password /* proxyPassword (optional) */
        );

        final DirectPayment directPayment = new DirectPayment(connectParams);
        final Payment payment = new Payment();
        payment.setAmount("9200");
        payment.setCurrency("978");
        payment.setAction("100");
        payment.setMode("CPT");
        payment.setContractNumber(contractNumber);

        final Order order = new Order();
        order.setRef(ref);
        order.setCountry("FR");
        order.setAmount("9200");
        order.setCurrency("978");
        order.setDate("23/04/2020 09:00");

        final Card card = new Card();
        card.setNumber("5075980000019002");
        card.setType("VISA");
        card.setCvx("000");
        card.setExpirationDate("1220");
        card.setCardholder("Olivier Francome");

        final PrivateDataList privateDataList = new PrivateDataList();
        final PrivateData privateData = new PrivateData();
        privateData.setKey("key1");
        privateData.setValue("value1");
        privateDataList.getPrivateData().add(privateData);

        final PrivateData privateData2 = new PrivateData();
        privateData2.setKey("key1");
        privateData2.setValue("value1");
        privateDataList.getPrivateData().add(privateData);

        final String version = "28";

        final DoAuthorizationResponse doAuthorizationResponse = directPayment.doAuthorization(payment, order,
                null, card, privateDataList, null, null, version, null);

        return "doAuthorization result :  " + "  - code :  " + doAuthorizationResponse.getResult().getCode() +
                " - short message :  " + doAuthorizationResponse.getResult().getShortMessage() +
                " - long message :  " + doAuthorizationResponse.getResult().getLongMessage();
    }
}
