package com.pwx.mall.disk.common.util;

import java.util.List;

public class MsgJson {

    //操作信息
    private String msg;

    //操作码
    private String msgCode;
    
    //泛型  返回列表操作数据list
    private List<?> data;
    
    //对象  返回单个对象数据object
    private Object object;
    
    private String state;
    
    private String imgPath;

    
    public String getImgPath() {
		return imgPath;
	}


	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}


	public String getMsg() {
        return msg;
    }

    
    public void setMsg(String msg) {
        this.msg = msg;
    }

    
    public String getMsgCode() {
        return msgCode;
    }

    
    public void setMsgCode(String msgCode) {
        this.msgCode = msgCode;
    }

    
    public List<?> getData() {
        return data;
    }

    
    public void setData(List<?> data) {
        this.data = data;
    }

    
    public Object getObject() {
        return object;
    }

    
    public void setObject(Object object) {
        this.object = object;
    }
   
    
    public MsgJson(){
        super();
    }


    public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
	}


	@Override
    public String toString() {
        return "MsgJson [msg=" + msg + ", msgCode=" + msgCode + ", data=" + data + ", object=" + object + ", state=" + state + ", imgPath=" + imgPath + "]";
    }


    public MsgJson(String msg, String msgCode, List<?> data, Object object,String state,String imgPath){
        super();
        this.msg = msg;
        this.msgCode = msgCode;
        this.data = data;
        this.object = object;
        this.state=state;
        this.imgPath=imgPath;
    }
    
    
}
