package gr.cs.uoi.daintiness.core;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonConverter {
	
	
	
	public String convertListToJsonString(List<?> list) {
		
		String jsonString=null;
		
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		jsonString = gson.toJson(list);
		return jsonString;
		
		
	}

}
