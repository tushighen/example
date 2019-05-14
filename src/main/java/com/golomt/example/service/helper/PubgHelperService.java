package com.golomt.example.service.helper;

import com.golomt.example.utilities.LogUtilities;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

@Service
public class PubgHelperService {

    /**
     * Autowire
     **/

    @Autowired
    Environment env;


    public Response doSendRequest(ResteasyClient client) {

        Invocation.Builder builder;

        try {

            builder = client.target(env.getProperty("PUBG_API"))
                    .request()
                    .accept(env.getProperty("PUBG_ACCEPT"))
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + env.getProperty("PUBG_TOKEN"));
            return builder.get();

        } catch (Exception ex) {
            throw ex;
        }

    }

    public void doClose(ResteasyClient client, Response response) {

        try {

            if (client != null)
                client.close();

        } catch (Exception ex) {
            LogUtilities.info(this.getClass().getName(), "[pubg][close.client][" + ex.getMessage() + "]");
        }

        try {

            if (response != null)
                response.close();

        } catch (Exception ex) {
            LogUtilities.info(this.getClass().getName(), "[pubg][close.response][" + ex.getMessage() + "]");
        }

    }
}