package com.peerbuds.PaypalPrac;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.braintreegateway.BraintreeGateway;
import com.braintreegateway.Environment;
import com.braintreegateway.Result;
import com.braintreegateway.Transaction;
import com.braintreegateway.TransactionRequest;

@Controller
public class MainController {

	private static final String MERCHANT_ID = "access_token$production$7nrvjqp4dtxzksc4$371010ea6f636905d9c7f5ceb61e7958";
	
	private static BraintreeGateway gateway = new BraintreeGateway(MERCHANT_ID);
	
	
	@RequestMapping(value = "/checkout", method = RequestMethod.POST)
	public ModelAndView processPayment(HttpServletRequest request){
		String nonceFromClient = request.getParameter("payment_method_nonce");
		//double amountParameter = Double.parseDouble(request.getParameter("amount"));
		BigDecimal amount = BigDecimal.valueOf(10.0);
		TransactionRequest transactionRequest = new TransactionRequest()
			    .amount(amount)
			    .paymentMethodNonce(nonceFromClient);
				/*
			    transactionRequest.options().
			      paypal().
			        customField("PayPal custom field").
			        description("PaypalPrac").
			        done();
			    */
			    transactionRequest.deviceData(request.getParameter("device_data"));
			    /*
			    transactionRequest.orderId("Mapped to PayPal Invoice Number").
			    descriptor().
			      name("PaypalPrac").
			      done();
			    
			    transactionRequest.shippingAddress().
			        firstName("Prashant")
			        .lastName("Parekh")
			        .company("Klouddata")
			        .streetAddress("4580 Automall Pkway")
			        .extendedAddress("Suite 121")
			        .locality("Fremont")
			        .region("CA")
			        .postalCode("94539")
			        .countryCodeAlpha2("US")
			        .done(); 
			    */    
			    
			   
			    
		Result<Transaction> saleResult = gateway.transaction().sale(transactionRequest);


		if (saleResult.isSuccess()) {
		  Transaction transaction = saleResult.getTarget();
		  return new ModelAndView("result", "message", "Success ID: " + transaction.getId());
		} else {
		  return new ModelAndView("result", "message", "Message: " + saleResult.getMessage());
		}
	}
	
	@RequestMapping(value = "/make-payment", method = RequestMethod.GET)
	public String makePayment(){
		return "make-payment";
	}
	
	@RequestMapping(value = "/client-token", method = RequestMethod.POST)
	public @ResponseBody String processToken(){
		return gateway.clientToken().generate();
	}
	
	@RequestMapping(value = "/withdraw", method = RequestMethod.POST)
	public ModelAndView postWithdraw(HttpServletRequest request){
		
		
		return new ModelAndView("result", "message", "");
	}
	
}
