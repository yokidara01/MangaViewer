package com.example.aladinne.myapplicationandoidstudio.handler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Aladinne on 20/02/2016.
 */
public class GetDescriptionFromUrl {


    public String getDescription(String url )

    {
        if ((url==null) || (url.equals("")))
        {
            return "Description Not found";
        }

        String description ="" ;
        URL oracle = null;
        try {
            oracle = new URL(url);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(oracle.openStream()));

        String inputLine;
        while ((inputLine = in.readLine()) != null)
          // System.out.println(inputLine);
        description=description+'\n'+inputLine;

        in.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }



        String div ="<div class=\"sContent\" style=\"text-align:justify\">" ;


        String[] t = description.split(div);
        String aux = t[1].substring(t[1].indexOf("<BR>"),t[1].length());
        aux = aux.substring(0,aux.indexOf("</div>"));

        if (aux.contains("<div class=\"sCat\"><b>Artist(s)</b>"))
        {
            aux="Description not found.";
        }
        aux =aux.replace("<BR>", "");
        Pattern r;
        Matcher m;
        if(aux.contains("<"))
        {
           /*r = Pattern.compile("<(\"[^\"]*\"|'[^']*'|[^'\">])*>");
          m = r.matcher(aux);
            while (m.find())
            {
                aux=m.group(2);
            }*/
            aux=aux.replaceAll("\\<.*?>","");
        }
        return  aux;

    }


}
