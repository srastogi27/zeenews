package com.zee.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;
import com.zee.constants.Constants;
import com.zee.utils.TestLogger;

public class NetworkEntity {

    public String baseURL;
    public String userAgent;
    public ArrayList<NetworkEntity> networkEntities = new ArrayList<NetworkEntity>();
    public HashMap<String, String> queryParams = new HashMap<String, String>();

    public NetworkEntity(String baseURL, String userAgent){
        this.baseURL = baseURL;
        this.userAgent = userAgent;
    }

    public NetworkEntity(){
    }

    public void setNetworkEntities(NetworkEntity networkEntity){
        networkEntities.add(networkEntity);
    }

    public ArrayList<NetworkEntity> getNetworkEnitiesList(){
        return networkEntities;
    }


    public void setQueryParams(ArrayList<NetworkEntity> networkEntities){
        String temp = null;
        if(networkEntities.size() > 0){
            TestLogger.getInstance().info("The URL is : " + networkEntities.get(0).baseURL);
            String[] array = networkEntities.get(0).baseURL.split(Constants.AMPHERCENT);
            for(int i = 0; i < array.length; i++){
                if(array[i].contains(Constants.EQUALS)){
                    if(!Pattern.compile(Constants.REGEX_ENDS_WITH_EQUAL).matcher(array[i]).find()){
                        if(i == 0){
                            temp = array[i].split(Constants.REGEX_QUESTION)[1];
                            queryParams.put(temp.split(Constants.EQUALS)[0], temp.split(Constants.EQUALS)[1]);
                        }
                        queryParams.put(array[i].split(Constants.EQUALS)[0], array[i].split(Constants.EQUALS)[1]);
                    }
                }
            }
        }else{
            TestLogger.getInstance().error("Duplicate URL's or None URL Found.");
        }

    }

    public String getQueryParams(String key){
        String value = "null";
        if(key.equalsIgnoreCase("EN")){
            if(!queryParams.containsKey("en"))
                value = queryParams.get("ea");
        }else if(queryParams.containsKey(key))
            value = queryParams.get(key);
        return value;
    }

    public void flushOutList(){
        if(networkEntities.size() > 0)
            networkEntities.clear();
    }

    public void flushOutMap(){
        if(queryParams.size() > 0)
            queryParams.clear();
    }

    public HashMap<String, String> getQueryParamsForMSite(){
        return queryParams;
    }

    public void setQueryParamsForMSite(ArrayList<String> list){
        String temp = null;
        if(list.size() > 0){
            for(String input : list){
                TestLogger.getInstance().info("The URL is : " + input);
                String[] array = list.get(0).split(Constants.AMPHERCENT);
                for(int i = 0; i < array.length; i++){
                    if(array[i].contains(Constants.EQUALS)){
                        if(!Pattern.compile(Constants.REGEX_ENDS_WITH_EQUAL).matcher(array[i]).find()){
                            if(i == 0){
                                temp = array[i].split(Constants.REGEX_QUESTION)[1];
                                queryParams.put(temp.split(Constants.EQUALS)[0], temp.split(Constants.EQUALS)[1]);
                            }
                            queryParams.put(array[i].split(Constants.EQUALS)[0], array[i].split(Constants.EQUALS)[1]);
                        }
                    }
                }
            }
        }
    }
    
    public void setQueryParamsForWeb(List<String> list){
        String temp = null;
        if(!list.isEmpty()){
			for (int i = 0; i < 1; i++) {
				TestLogger.getInstance().info("The URL is : " + list.get(i));
				String[] array = list.get(i).split(Constants.AMPHERCENT);
				if (i == 0) {
					temp = array[i].split(Constants.REGEX_QUESTION)[1];
					queryParams.put(temp.split(Constants.EQUALS)[0], temp.split(Constants.EQUALS)[1]);
				}
				for (int j = 0; j < array.length; j++) {
					if (array[j].contains(Constants.EQUALS)
							&& !Pattern.compile(Constants.REGEX_ENDS_WITH_EQUAL).matcher(array[j]).find() && !array[j].split(Constants.EQUALS)[0].equalsIgnoreCase(Constants.TID)) {
						queryParams.put(array[j].split(Constants.EQUALS)[0], array[j].split(Constants.EQUALS)[1]);
					}
				}
			}
		}
    }
}
