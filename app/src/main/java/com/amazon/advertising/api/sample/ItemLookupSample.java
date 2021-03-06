/**********************************************************************************************
 * Copyright 2009 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file 
 * except in compliance with the License. A copy of the License is located at
 *
 *       http://aws.amazon.com/apache2.0/
 *
 * or in the "LICENSE.txt" file accompanying this file. This file is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under the License. 
 *
 * ********************************************************************************************
 *
 *  Amazon Product Advertising API
 *  Signed Requests Sample Code
 *
 *  API Version: 2009-03-31
 *
 */

package com.amazon.advertising.api.sample;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import android.content.Context;

/*
 * This class shows how to make a simple authenticated ItemLookup call to the
 * Amazon Product Advertising API.
 * 
 * See the README.html that came with this sample for instructions on
 * configuring and running the sample.
 */
public class ItemLookupSample {
    /*
     * Your AWS Access Key ID, as taken from the AWS Your Account page.
     */
    //private static final String AWS_ACCESS_KEY_ID = "AKIAJJH6UG3TAQDTSACA";

    /*
     * Your AWS Secret Key corresponding to the above ID, as taken from the AWS
     * Your Account page.
     */
    //private static final String AWS_SECRET_KEY = "1/qdMQnQKs9/NjiszuJwb1uMQg3rzoS/xbDM5sTt";

    /*
     * Use one of the following end-points, according to the region you are
     * interested in:
     * 
     *      US: ecs.amazonaws.com 
     *      CA: ecs.amazonaws.ca 
     *      UK: ecs.amazonaws.co.uk 
     *      DE: ecs.amazonaws.de 
     *      FR: ecs.amazonaws.fr 
     *      JP: ecs.amazonaws.jp
     * 
     */
    private static final String ENDPOINT = "ecs.amazonaws.jp";
    //private static final String ENDPOINT = "webservices.amazon.co.jp";

    /*
     * The Item ID to lookup. The value below was selected for the US locale.
     * You can choose a different value if this value does not work in the
     * locale of your choice.
     */
    private static final String ITEM_ID = "0545010225";

    /**
     * 現在呼び出されない未使用のクラス
     * @param content
     */
    public void CallSampleItemLookup(Context content) {
        /*
         * Set up the signed requests helper 
         */
    	//serializeInformationAWS(content);
    	InformationRestAmazonAWSSerialize aws 
    	 = deSerializeInformationAWS(content);
        SignedRequestsHelper helper;
        try {
           helper = SignedRequestsHelper.getInstance(ENDPOINT, aws.AWS_ACCESS_KEY_ID, aws.AWS_SECRET_KEY);
            //helper = SignedRequestsHelper.getInstance(ENDPOINT, AWS_ACCESS_KEY_ID, AWS_SECRET_KEY);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        
        String requestUrl = null;
        String title = null;

        /* The helper can sign requests in two forms - map form and string form */
        
        /*
         * Here is an example in map form, where the request parameters are stored in a map.
         */
        System.out.println("Map form example:");
        Map<String, String> params = new HashMap<String, String>();
        params.put("Service", "AWSECommerceService");
        params.put("Version", "2011-08-01");
        params.put("Operation", "ItemLookup");
        params.put("ItemId", ITEM_ID);
        params.put("ResponseGroup", "Small");
        params.put("AssociateTag", "tritritri-22");
        
        requestUrl = helper.sign(params);
        System.out.println("Signed Request is \"" + requestUrl + "\"");

        title = fetchTitle(requestUrl);
        System.out.println("Signed Title is \"" + title + "\"");
        System.out.println();

    }

    /*
     * Utility function to fetch the response from the service and extract the
     * title from the XML.
     */
    private static String fetchTitle(String requestUrl) {
        String title = null;
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(requestUrl);
            Node titleNode = doc.getElementsByTagName("Title").item(0);
            title = titleNode.getTextContent();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return title;
    }
    /**
     * 
     * @param content ���C�A�E�g�̃R���e���c
     */
    @SuppressWarnings("unused")
	private void serializeInformationAWS(Context content) {
        try {
            FileOutputStream fs = content.openFileOutput("myfile.txt", Context.MODE_PRIVATE);
            ObjectOutputStream objOutStream =  new ObjectOutputStream(fs);
            
            InformationRestAmazonAWSSerialize info 
            = new InformationRestAmazonAWSSerialize();
            
            objOutStream.writeObject(info);
            objOutStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 
     * @param content�@���C�A�E�g�̃R���e��
     * @return�@amazon�̃��O�C�����N���X���擾���܂�
     */
    private InformationRestAmazonAWSSerialize deSerializeInformationAWS(Context content) {
    	InformationRestAmazonAWSSerialize aws=null;   
    	try {

    		   FileInputStream fs = content.openFileInput("myfile.txt");
    		      ObjectInputStream objInStream 
    		        = new ObjectInputStream(fs);
    		      aws 
    		      = (InformationRestAmazonAWSSerialize) objInStream.readObject();
    		      objInStream.close();
    		      
    		    } catch (FileNotFoundException e) {
    		      e.printStackTrace();
    		    } catch (IOException e) {
    		      e.printStackTrace();
    		    } catch (ClassNotFoundException e) {
    		      e.printStackTrace();
    		    }
    	   return aws;
    
    }
}
