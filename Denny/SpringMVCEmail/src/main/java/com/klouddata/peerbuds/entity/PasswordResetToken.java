package com.klouddata.peerbuds.entity;

import java.util.Date;

public class PasswordResetToken 
{
	private static final int EXPIRATION_TIME = 60 * 24;
	
	private String token;
	private Date expiryDate;
}
