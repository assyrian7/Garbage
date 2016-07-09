package com.peerbuds.PaypalPrac;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.paypal.api.openidconnect.CreateFromAuthorizationCodeParameters;
import com.paypal.api.openidconnect.Session;
import com.paypal.api.openidconnect.Tokeninfo;
import com.paypal.api.openidconnect.Userinfo;
import com.paypal.api.openidconnect.UserinfoParameters;
import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Details;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.Transaction;
import com.paypal.base.ClientCredentials;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import com.paypal.exception.ClientActionRequiredException;
import com.paypal.exception.HttpErrorException;
import com.paypal.exception.InvalidCredentialException;
import com.paypal.exception.InvalidResponseDataException;
import com.paypal.exception.MissingCredentialException;
import com.paypal.exception.SSLConfigurationException;
import com.paypal.sdk.exceptions.OAuthException;
import com.paypal.svcs.services.AdaptivePaymentsService;
import com.paypal.svcs.types.ap.ExecutePaymentRequest;
import com.paypal.svcs.types.ap.PayRequest;
import com.paypal.svcs.types.ap.PayResponse;
import com.paypal.svcs.types.ap.PaymentDetailsRequest;
import com.paypal.svcs.types.ap.PaymentDetailsResponse;
import com.paypal.svcs.types.ap.PreapprovalRequest;
import com.paypal.svcs.types.ap.Receiver;
import com.paypal.svcs.types.ap.ReceiverList;
import com.paypal.svcs.types.common.RequestEnvelope;

@Controller
public class PaymentController {

	private static final String clientID = "AejQKaW2cxtS_y0wTH5xt9LfqY0-eugZSYWteLOsPekHx-3KAcub2LaflTG6J70XkigC55c9liI0ot3S";
	private static final String clientSecret = "EP7fN3gp8k8HvIKEJJUx5_H1KZP7D9A14gMN_I3fmA6DJDLDuborXHAXlV5GNXmnMdJvtWLAWgrkmUmF"; 
	//private static final String username = "assyrian7_api1.paulbadalian.com";
	//private static final String password = "VR4E9RHW2GD59EUG";
	//private static final String signature = "AFcWxV21C7fd0v3bYYYRCpSSRl31AIp5J6CujmP6DxsjxFO0h1qJ-iHv";
	//private static final String app_id = "APP-80W284485P519543T";
	private static final String username = "jb-us-seller_api1.paypal.com";
	private static final String password = "WX4WTU3S8MY44S7F";
	private static final String signature = "AFcWxV21C7fd0v3bYYYRCpSSRl31A7yDhhsPUU2XhtMoZXsWHFxu";
	private static final String app_id = "APP-80W284485P519543T";
	Map<String, String> map = new HashMap<String, String>();
	
	@RequestMapping(value = "/withdraw", method = RequestMethod.GET)
	public ModelAndView getWithdraw(){
		
		Map<String, String> configurationMap = new HashMap<String, String>();
		configurationMap.put("mode", "sandbox");

		APIContext apiContext = new APIContext();
		apiContext.setConfigurationMap(configurationMap);

		List<String> scopelist = new ArrayList<String>();
		scopelist.add("openid");
		scopelist.add("email");
		scopelist.add("address");
		scopelist.add("profile");

		String redirectURI = "http://127.0.0.1:8080/PaypalPrac/make-paypal-payment";

		ClientCredentials clientCredentials = new ClientCredentials();
		clientCredentials.setClientID(clientID);

		String redirectUrl = Session.getRedirectURL(redirectURI, scopelist, apiContext, clientCredentials); 
		
		return new ModelAndView("withdraw", "link", redirectUrl);
	}
	
	@RequestMapping(value = "/make-paypal-payment", method = RequestMethod.GET)
	public ModelAndView makePaypalPayment(HttpServletRequest request) throws PayPalRESTException{

		Map<String, String> configurationMap = new HashMap<String, String>();
		configurationMap.put("mode", "sandbox");

		APIContext apiContext = new APIContext();
		apiContext.setConfigurationMap(configurationMap);

		CreateFromAuthorizationCodeParameters param = new CreateFromAuthorizationCodeParameters();
		param.setClientID(clientID);
		param.setClientSecret(clientSecret);
		param.setCode(request.getParameter("code"));
		
		Tokeninfo info = Tokeninfo.createFromAuthorizationCode(apiContext, param);
		String accessToken = info.getAccessToken(); 
		
		UserinfoParameters userParam = new UserinfoParameters();
		userParam.setAccessToken(accessToken);

		Userinfo userInfo = Userinfo.getUserinfo(accessToken);
		
		
		return new ModelAndView("result", "message", userInfo.toJSON().toString());
		
	}
	@RequestMapping(value = "/pay-peerbuds", method = RequestMethod.GET)
	public String initMassPayment(HttpServletRequest request){
		/*
		Map<String, String> configurationMap = new HashMap<String, String>();
	    configurationMap.put("clientId", clientID);
	    configurationMap.put("clientSecret", clientSecret);
	    configurationMap.put("service.EndPoint", "https://api.paypal.com/");
	    APIContext apiContext = new APIContext();
	    apiContext.setConfigurationMap(configurationMap);
	    CreateFromAuthorizationCodeParameters param = new CreateFromAuthorizationCodeParameters();
	    param.setCode(code);
	    Tokeninfo info = Tokeninfo.createFromAuthorizationCode(apiContext, param);
	    String accessToken = info.getAccessToken();
		HttpSession session = request.getSession();
		*/
		return "pay-peerbuds";
	}
	
	@RequestMapping(value = "review-payment", method = RequestMethod.GET)
	public String review(HttpServletRequest request){
		HttpSession session = request.getSession();
		return "result";
	}
	
	@RequestMapping(value = "/pay-peerbuds", method = RequestMethod.POST)
	public Callable<String> processMassPayment(HttpServletRequest request) throws SSLConfigurationException, InvalidCredentialException, UnsupportedEncodingException, HttpErrorException, InvalidResponseDataException, ClientActionRequiredException, MissingCredentialException, OAuthException, IOException, InterruptedException{
		
		return new Callable<String>(){
			
			public String call() throws SSLConfigurationException, InvalidCredentialException, UnsupportedEncodingException, HttpErrorException, InvalidResponseDataException, ClientActionRequiredException, MissingCredentialException, OAuthException, IOException, InterruptedException{
				
				String response = "";
				HttpSession session = request.getSession();
				/*
				Map<String, String> sdkConfig = new HashMap<String, String>();
				sdkConfig.put("mode", "sandbox");
				sdkConfig.put("acct1.UserName", "jb-us-seller_api1.paypal.com");
				sdkConfig.put("acct1.Password", "WX4WTU3S8MY44S7F");
				sdkConfig.put("acct1.Signature","AFcWxV21C7fd0v3bYYYRCpSSRl31A7yDhhsPUU2XhtMoZXsWHFxu-RWy");
				sdkConfig.put("acct1.AppId","APP-80W284485P519543T");

				AdaptivePaymentsService adaptivePaymentsService = new AdaptivePaymentsService(sdkConfig);
				*/
				
					String tutorEmail = request.getParameter("tutor-email");
					String adminEmail = "prashantp@klouddata.com";
					String studentEmail = request.getParameter("student-email");
					double amount = Double.parseDouble(request.getParameter("amount"));
					/*
					RequestEnvelope requestEnvelope = new RequestEnvelope("en_US");
					
					PayRequest tutorRequest = new PayRequest();
					PayRequest chainRequest = new PayRequest();
										
					List<Receiver> chainReceivers = new ArrayList<Receiver>();
					Receiver tutorRec = new Receiver();
					tutorRec.setAmount(amount * .80);
					tutorRec.setEmail(tutorEmail);
					tutorRec.setPrimary(true);
					chainReceivers.add(tutorRec);
					Receiver peerbudsRec = new Receiver();
					peerbudsRec.setAmount(amount * .20);
					peerbudsRec.setEmail(adminEmail);
					chainReceivers.add(peerbudsRec);
					chainRequest.setReceiverList(new ReceiverList(chainReceivers));
					
					List<Receiver> receivers = new ArrayList<Receiver>();
					Receiver receiver_1 = new Receiver();
					receiver_1.setAmount(amount * .80);
					receiver_1.setEmail("sahilp@peerbuds.com");
					receivers.add(receiver_1);
	
					Receiver receiver_2 = new Receiver();
					receiver_2.setAmount(amount * .20);
					receiver_2.setEmail(adminEmail);
					receivers.add(receiver_2);
					ReceiverList receiverList = new ReceiverList(receivers);
					tutorRequest.setReceiverList(receiverList);
									
					tutorRequest.setRequestEnvelope(requestEnvelope); 
					tutorRequest.setActionType("PAY");
					tutorRequest.setCancelUrl("https://www.google.com");
					tutorRequest.setReturnUrl("https://localhost:8080/PaypalPrac/review-payment");
					tutorRequest.setCurrencyCode("USD");
					tutorRequest.setIpnNotificationUrl("http://replaceIpnUrl.com");
	
					chainRequest.setRequestEnvelope(requestEnvelope); 
					chainRequest.setActionType("PAY");
					chainRequest.setCancelUrl("https://devtools-paypal.com/guide/ap_implicit_payment?cancel=true");
					chainRequest.setReturnUrl("https://devtools-paypal.com/guide/ap_implicit_payment?success=true");
					chainRequest.setCurrencyCode("USD");
					chainRequest.setIpnNotificationUrl("http://replaceIpnUrl.com");
					
					PayResponse tutorResponse = adaptivePaymentsService.pay(tutorRequest);
					//PayResponse chainResponse = adaptivePaymentsService.pay(chainRequest);
	
					PaymentDetailsRequest tutorDetailsRequest = new PaymentDetailsRequest(requestEnvelope);
					tutorDetailsRequest.setPayKey(tutorResponse.getPayKey());
					PaymentDetailsResponse tutorDetailsResponse = adaptivePaymentsService.paymentDetails(tutorDetailsRequest);
					*/
					
					
					PayRequest payRequest = new PayRequest();
			 		
					List<Receiver> receivers = new ArrayList<Receiver>();
					Receiver receiver_1 = new Receiver();
					receiver_1.setAmount(amount * .80);
					receiver_1.setEmail(tutorEmail);
					receivers.add(receiver_1);
	
					Receiver receiver_2 = new Receiver();
					receiver_2.setAmount(amount * .20);
					receiver_2.setEmail("prashantp@klouddata.com");
					receivers.add(receiver_2);
					ReceiverList receiverList = new ReceiverList(receivers);
	
					payRequest.setReceiverList(receiverList);
	
					RequestEnvelope requestEnvelope = new RequestEnvelope("en_US");
					payRequest.setRequestEnvelope(requestEnvelope); 
					payRequest.setActionType("PAY");
					payRequest.setCancelUrl("http://localhost:8080/PaypalPrac/pay-peerbuds");
					payRequest.setReturnUrl("http://localhost:8080/PaypalPrac/review-payment");
					payRequest.setCurrencyCode("USD");
					payRequest.setIpnNotificationUrl("http://replaceIpnUrl.com");
	
					Map<String, String> sdkConfig = new HashMap<String, String>();
					sdkConfig.put("mode", "sandbox");
					sdkConfig.put("acct1.UserName", "jb-us-seller_api1.paypal.com");
					sdkConfig.put("acct1.Password", "WX4WTU3S8MY44S7F");
					sdkConfig.put("acct1.Signature","AFcWxV21C7fd0v3bYYYRCpSSRl31A7yDhhsPUU2XhtMoZXsWHFxu-RWy");
					sdkConfig.put("acct1.AppId","APP-80W284485P519543T");
	
					AdaptivePaymentsService adaptivePaymentsService = new AdaptivePaymentsService(sdkConfig);
					PayResponse payResponse = adaptivePaymentsService.pay(payRequest);
					
					PaymentDetailsRequest tutorDetailsRequest = new PaymentDetailsRequest(requestEnvelope);
					tutorDetailsRequest.setPayKey(payResponse.getPayKey());
					PaymentDetailsResponse tutorDetailsResponse = adaptivePaymentsService.paymentDetails(tutorDetailsRequest);	
					
					session.setAttribute("key", payResponse.getPayKey());
					
					return "redirect:https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_ap-payment&paykey=" + payResponse.getPayKey();
					//return new ModelAndView("result", "message", tutorResponse.getResponseEnvelope().getAck().getValue());
			}
		};
	}
	
	public Payment createPayment(String totalAmount) {
		Payment createdPayment = null;
		Payment executedPayment = null;

		// ### Api Context
		// Pass in a `ApiContext` object to authenticate
		// the call and to send a unique request id
		// (that ensures idempotency). The SDK generates
		// a request id if you do not pass one explicitly.
		APIContext apiContext = new APIContext(clientID, clientSecret, "sandbox");
		// ###Details
		// Let's you specify details of a payment amount.
		Details details = new Details();
		details.setShipping("1");

		// ###Amount
		// Let's you specify a payment amount.
		Amount amount = new Amount();
		amount.setCurrency("USD");
		// Total must be equal to sum of shipping, tax and subtotal.
		amount.setTotal(totalAmount);
		amount.setDetails(details);

		// ###Transaction
		// A transaction defines the contract of a
		// payment - what is the payment for and who
		// is fulfilling it. Transaction is created with
		// a `Payee` and `Amount` types
		Transaction transaction = new Transaction();
		transaction.setAmount(amount);
		transaction
				.setDescription("This is the payment transaction description.");

		// ### Items	
		// The Payment creation API requires a list of
		// Transaction; add the created `Transaction`
		// to a List
		List<Transaction> transactions = new ArrayList<Transaction>();
		transactions.add(transaction);

		// ###Payer
		// A resource representing a Payer that funds a payment
		// Payment Method
		// as 'paypal'
		Payer payer = new Payer();
		payer.setPaymentMethod("paypal");

		// ###Payment
		// A Payment Resource; create one using
		// the above types and intent as 'sale'
		Payment payment = new Payment();
		payment.setIntent("sale");
		payment.setPayer(payer);
		payment.setTransactions(transactions);

		// Create a payment by posting to the APIService
		// using a valid AccessToken
		// The return object contains the status;
		try {
			createdPayment = payment.create(apiContext);
			
		} catch (PayPalRESTException e) {
		}
	
		PaymentExecution paymentExecution = new PaymentExecution();
		paymentExecution.setPayerId(createdPayment.getId());	
		
		try {
			executedPayment = payment.execute(apiContext, paymentExecution);
		} catch (PayPalRESTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return createdPayment;
	}

	
}
