package com.sns.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.SubscribeRequest;

@RestController
public class SNSController {

	@Autowired
	private AmazonSNSClient amazonSNSClient;
	
	String Topic_ARN="";
	
	@GetMapping("/addSubscription/{email}")
	public String addSubscription(@PathVariable String email) {
		SubscribeRequest request = new SubscribeRequest(Topic_ARN, "email", email);
		amazonSNSClient.subscribe(request);
		return "Subscription request is pending. Check your email: " + email;
	}
	
	@GetMapping("/sendNotification")
	public String publishMessageToTopic() {
		PublishRequest publishRequest=new PublishRequest(Topic_ARN,buidMessageBody(),"Notification: All server connected");
		amazonSNSClient.publish(publishRequest);
		return "Notification sent successfully!";
	}

	private String buidMessageBody() {
		return "Dear Employee ,\n"+
				"\n"+
				"Welcome! Server is connecting...."+"\n"+
				"All the server are accessible.\n"+
				"Next step notification will be sent out as soon as possible";
	}
}
