package com.scujcc.bug.fixdemo;

import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import com.btcchina.fix.BTCCFIXClient;
import com.btcchina.fix.BTCCMarketDataRequest;
import com.btcchina.fix.BTCCTradingRequest;
import com.btcchina.fix.UnsupportMarketException;

import quickfix.Application;
import quickfix.ConfigError;
import quickfix.DefaultMessageFactory;
import quickfix.DoNotSend;
import quickfix.Field;
import quickfix.FieldNotFound;
import quickfix.FileLogFactory;
import quickfix.FileStoreFactory;
import quickfix.IncorrectDataFormat;
import quickfix.IncorrectTagValue;
import quickfix.Initiator;
import quickfix.LogFactory;
import quickfix.Message;
import quickfix.MessageFactory;
import quickfix.MessageStoreFactory;
import quickfix.RejectLogon;
import quickfix.Session;
import quickfix.SessionID;
import quickfix.SessionNotFound;
import quickfix.SessionSettings;
import quickfix.SocketInitiator;
import quickfix.UnsupportedMessageType;
import quickfix.field.Account;
import quickfix.field.MassStatusReqID;
import quickfix.field.MassStatusReqType;
import quickfix.field.OrdType;
import quickfix.field.Side;
import quickfix.field.Symbol;
import quickfix.fix44.MessageCracker;

public class MyDemo {

	public static final String AK = Config.getAccessKey();

	public static final String SK = Config.getSecretKey();

	public static void main(String[] args) throws ConfigError {
		MyQuickFixApp app = new MyQuickFixApp();
		InputStream inputStream = BTCCFIXClient.class.getResourceAsStream("/quickfix-client.properties");
		SessionSettings settings = new SessionSettings(inputStream);
		MessageStoreFactory storeFactory = new FileStoreFactory(settings);
		LogFactory logFactory = new FileLogFactory(settings);
		MessageFactory messageFactory = new DefaultMessageFactory();
		Initiator initiator = new SocketInitiator(app, storeFactory, settings, logFactory, messageFactory);
		initiator.block();
		System.out.println("this is end");
	}

}

class MyQuickFixApp extends MessageCracker implements Application {
	private static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";

	@Override
	public void fromAdmin(Message message, SessionID session)
			throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, RejectLogon {
		print("fromAdmin", message);
	}

	@Override
	public void fromApp(Message message, SessionID session)
			throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, UnsupportedMessageType {
		// TODO Auto-generated method stub
		print("fromApp", message);
	}

	@Override
	public void onCreate(SessionID arg0) {
		// TODO Auto-generated method stub

		System.out.println("onCreate");

	}

	@Override
	public void onLogon(SessionID sessionID) {
		// TODO Auto-generated method stub
		System.out.println("onLogon");

		buy(sessionID);// (sessionID);
	}

	String toGetAccountInfoParamString() {
		String retString = "method=getAccountInfo&params=balance";
		return retString;
	}

	public void onMessage(Message message, Session session) {
		print("onMessage", message);
	}

	String getAccountString(String accesskey, String secretkey, String methodstr)
			throws InvalidKeyException, NoSuchAlgorithmException {
		String tonce = "" + (System.currentTimeMillis() * 1000);
		String params = "tonce=" + tonce.toString() + "&accesskey=" + accesskey + "&requestmethod=post&id=1&"
				+ methodstr;

		String hash = getSignature(params, secretkey);
		String userpass = accesskey + ":" + hash;
		String basicAuth = "Basic " + DatatypeConverter.printBase64Binary(userpass.getBytes());

		return tonce + ":" + basicAuth;
	}

	String getSignature(String data, String key) throws NoSuchAlgorithmException, InvalidKeyException {

		// get an hmac_sha1 key from the raw key bytes
		SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(), HMAC_SHA1_ALGORITHM);

		// get an hmac_sha1 Mac instance and initialize with the signing key
		Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
		mac.init(signingKey);

		// compute the hmac on input data bytes
		byte[] rawHmac = mac.doFinal(data.getBytes());

		return bytArrayToHex(rawHmac);
	}

	String bytArrayToHex(byte[] a) {
		StringBuilder sb = new StringBuilder();
		for (byte b : a)
			sb.append(String.format("%02x", b & 0xff));
		return sb.toString();
	}

	String toGetOrdersParamString(String market) {
		String retString = "method=getOrders&params=1," + market + ",1000,0,0,1";
		return retString;
	}

	@Override
	public void onLogout(SessionID arg0) {
		// TODO Auto-generated method stub

		System.out.println("onLogout");

	}

	@Override
	public void toAdmin(Message message, SessionID arg1) {
		print("toAdmin", message);
	}

	@Override
	public void toApp(Message message, SessionID arg1) throws DoNotSend {
		print("toApp", message);
	}

	public void print(String method, Message message) {
		System.out.println("----------------" + method + "-------------------");

		System.out.println(message);

		for (Iterator<Field<?>> iter = message.iterator(); iter.hasNext();) {
			String str = iter.next().toString();
			System.out.println(str);
		}

		System.out.println("----------------" + method + "-------------------");

	}

	private void testit(SessionID sessionId) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				quickfix.Message message;

				// MARKET DATA INCREMENTAL REFRESH REQUEST (V)
				// message =
				// BTCCMarketDataRequest.marketDataIncrementalRequest("LTCBTC");
				// message =
				// BTCCMarketDataRequest.marketDataIncrementalRequest("LTCCNY");
				System.out.println("准备发送查行情消息。。。");
				message = BTCCMarketDataRequest.marketDataIncrementalRequest("BTCCNY");
				Session.lookupSession(sessionId).send(message);
				// try {
				// Thread.sleep(5000);
				// } catch (InterruptedException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// }

				// MARKET DATA SNAPSHOT FULL REFRESH REQUEST (V)
				// message =
				// BTCCMarketDataRequest.marketDataFullSnapRequest("LTCCNY");
				// Session.lookupSession(sessionID).send(message);
				// try {
				// Thread.sleep(10000);
				// } catch (InterruptedException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// }

				// UNSUBSCRIBE MARKET DATA INCREMENTAL REFRESH (V)
				// message =
				// BTCCMarketDataRequest.unsubscribeIncrementalRequest("LTCCNY");
				// Session.lookupSession(sessionID).send(message);
				// try {
				// Thread.sleep(3000);
				// } catch (InterruptedException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// }
			}
		}).start();
	}

	private void buy(SessionID sessionId) {

		new Thread(new Runnable() {
			@Override
			public void run() {
				quickfix.Message message = null;

				// MARKET DATA INCREMENTAL REFRESH REQUEST (V)
				// message =
				// BTCCMarketDataRequest.marketDataIncrementalRequest("LTCBTC");
				// message =
				// BTCCMarketDataRequest.marketDataIncrementalRequest("LTCCNY");
				System.out.println("准备用市场价格购买0.1比特币");
				// message =
				// BTCCMarketDataRequest.marketDataIncrementalRequest("BTCCNY");

				// try {
				// Thread.sleep(5000);
				// } catch (InterruptedException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// }

				// MARKET DATA SNAPSHOT FULL REFRESH REQUEST (V)
				// message =
				// BTCCMarketDataRequest.marketDataFullSnapRequest("LTCCNY");
				// Session.lookupSession(sessionID).send(message);
				// try {
				// Thread.sleep(10000);
				// } catch (InterruptedException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// }

				BTCCTradingRequest tradeRequest = new BTCCTradingRequest();

				// UNSUBSCRIBE MARKET DATA INCREMENTAL REFRESH (V)
				// message =
				// BTCCMarketDataRequest.unsubscribeIncrementalRequest("LTCCNY");
				try {
					message = tradeRequest.createNewOrderSingle(MyDemo.AK, MyDemo.SK, Side.SELL, OrdType.MARKET, 10,
							0.01, "BTCCNY"); //
				} catch (InvalidKeyException | NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (UnsupportMarketException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Session.lookupSession(sessionId).send(message);
				// try {
				// Thread.sleep(3000);
				// } catch (InterruptedException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// }
			}
		}).start();
	}

	private void testit2(SessionID sessionId) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				quickfix.Message message = null;

				// MARKET DATA INCREMENTAL REFRESH REQUEST (V)
				// message =
				// BTCCMarketDataRequest.marketDataIncrementalRequest("LTCBTC");
				// message =
				// BTCCMarketDataRequest.marketDataIncrementalRequest("LTCCNY");
				System.out.println("准备发送查行情消息。。。");
				// message =
				// BTCCMarketDataRequest.marketDataIncrementalRequest("BTCCNY");

				// try {
				// Thread.sleep(5000);
				// } catch (InterruptedException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// }

				// MARKET DATA SNAPSHOT FULL REFRESH REQUEST (V)
				// message =
				// BTCCMarketDataRequest.marketDataFullSnapRequest("LTCCNY");
				// Session.lookupSession(sessionID).send(message);
				// try {
				// Thread.sleep(10000);
				// } catch (InterruptedException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// }

				BTCCTradingRequest tradeRequest = new BTCCTradingRequest();

				// UNSUBSCRIBE MARKET DATA INCREMENTAL REFRESH (V)
				// message =
				// BTCCMarketDataRequest.unsubscribeIncrementalRequest("LTCCNY");
				try {
					message = tradeRequest.createUserAccountRequest(MyDemo.AK, MyDemo.SK);
				} catch (InvalidKeyException | NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Session.lookupSession(sessionId).send(message);
				// try {
				// Thread.sleep(3000);
				// } catch (InterruptedException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// }
			}
		}).start();
	}

}
