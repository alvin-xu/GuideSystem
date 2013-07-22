package com.guidesystem.net;

import org.json.JSONObject;

public interface ResultCallBack {
	public void onSuccess(JSONObject result);
	public void onFail(JSONObject result);
}
