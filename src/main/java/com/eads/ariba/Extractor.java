package com.eads.ariba;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Extractor {

    public static void main(String[] args) throws Exception {

        // create the HTTP client
        DefaultHttpClient httpClient = new DefaultHttpClient();

        System.out.println("Step 1: SSOActions ....");
        HttpGet step1Request = new HttpGet("https://s1.ariba.com/Sourcing/Main/ad/loginPage/SSOActions?awsso_ap=ACM&awsr=true&realm=eads-t&awsso_hpk=true");
        HttpResponse step1Response = httpClient.execute(step1Request);
        HttpEntity step1Entity = step1Response.getEntity();

        String step1EntityContent = EntityUtils.toString(step1Entity);

        System.out.println("Step 2: SSOActions ...");
        HttpPost step2Request = new HttpPost("https://s1.ariba.com/Sourcing/Main/ad/login/SSOActions?awr=1&realm=eads-T");
        ArrayList<NameValuePair> step2Params = new ArrayList<NameValuePair>();
        Pattern pattern = Pattern.compile(".*value=\"(.*)\" type=hidden name=ssocc>.*", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(step1EntityContent);
        String ssocc = "";
        if (matcher.matches()) {
            ssocc = matcher.group(1);
        }
        // System.out.println("SSOCC: " + ssocc);
        step2Params.add(new BasicNameValuePair("ssocc", ssocc));
        step2Params.add(new BasicNameValuePair("timezone", "-120"));
        step2Params.add(new BasicNameValuePair("timezoneFeb", "-120"));
        step2Params.add(new BasicNameValuePair("timezoneAug", "-120"));
        step2Params.add(new BasicNameValuePair("clientTime", "" + System.currentTimeMillis()/1000));
        step2Params.add(new BasicNameValuePair("ApplicationId", "ACM"));
        step2Params.add(new BasicNameValuePair("isLoginForm", "1"));
        step2Params.add(new BasicNameValuePair("passwordadapter", "PasswordAdapter1"));
        step2Params.add(new BasicNameValuePair("awsr", "true"));
        step2Params.add(new BasicNameValuePair("realm", "eads-t"));
        step2Params.add(new BasicNameValuePair("AWLogFilter", "Password"));
        step2Params.add(new BasicNameValuePair("UserName", "go2egp99"));
        step2Params.add(new BasicNameValuePair("Password", "toulouse15"));

        step2Request.setEntity(new UrlEncodedFormEntity(step2Params, HTTP.UTF_8));

        HttpResponse step2Response = httpClient.execute(step2Request);
        HttpEntity step2Entity = step2Response.getEntity();
        String step2Content = EntityUtils.toString(step2Entity);
        // System.out.println(step2Content);
        pattern = Pattern.compile(".*<a.*href='(.*)'>.*", Pattern.DOTALL);
        matcher = pattern.matcher(step2Content);
        String step3Url = "";
        if (matcher.matches()) {
            step3Url = matcher.group(1);
        }

        System.out.println("Step 3: " + step3Url + " ...");
        HttpGet step3Request = new HttpGet(step3Url);
        HttpResponse step3Response = httpClient.execute(step3Request);
        HttpEntity step3Entity = step3Response.getEntity();
        String step3Content = EntityUtils.toString(step3Entity);

        // action
        pattern = Pattern.compile(".*action=\"(.*)\" id=.*", Pattern.DOTALL);
        matcher = pattern.matcher(step3Content);
        String step4Action = "";
        if (matcher.matches()) {
            step4Action = matcher.group(1);
        }
        // System.out.println("    action: " + step4Action);

        // awsso_ar
        pattern = Pattern.compile(".*input value=(.*) type=hidden name=awsso_ar>.*", Pattern.DOTALL);
        matcher = pattern.matcher(step3Content);
        String step4AwssoAr = "";
        if (matcher.matches()) {
            step4AwssoAr = matcher.group(1);
        }
        // System.out.println("    awsso_ar = " + step4AwssoAr);

        // awsso_lia
        pattern = Pattern.compile(".*input value=(.*) type=hidden name=awsso_lia>.*", Pattern.DOTALL);
        matcher = pattern.matcher(step3Content);
        String step4AwssoLia = "";
        if (matcher.matches()) {
            step4AwssoLia = matcher.group(1);
        }
        // System.out.println("    awsso_lia = " + step4AwssoLia);

        // awsso_up
        pattern = Pattern.compile(".*input value=\"(.*)\" type=hidden name=awsso_up>.*", Pattern.DOTALL);
        matcher = pattern.matcher(step3Content);
        String step4AwssoUp = "";
        if (matcher.matches()) {
            step4AwssoUp = matcher.group(1);
        }
        // System.out.println("    awsso_up = " + step4AwssoUp);

        // awsso_ap
        pattern = Pattern.compile(".*input value=(.*) type=hidden name=awsso_ap>.*", Pattern.DOTALL);
        matcher = pattern.matcher(step3Content);
        String step4AwssoAp = "";
        if (matcher.matches()) {
            step4AwssoAp = matcher.group(1);
        }
        // System.out.println("    awsso_ap = " + step4AwssoAp);

        // awsso_lu
        pattern = Pattern.compile(".*input value=\"(.*)\" type=hidden name=awsso_lu>.*", Pattern.DOTALL);
        matcher = pattern.matcher(step3Content);
        String step4AwssoLu = "";
        if (matcher.matches()) {
            step4AwssoLu = matcher.group(1);
        }
        // System.out.println("    awsso_lu = " + step4AwssoLu);

        // awsso_au
        pattern = Pattern.compile(".*input value=\"(.*)\" type=hidden name=awsso_au>.*", Pattern.DOTALL);
        matcher = pattern.matcher(step3Content);
        String step4AwssoAu = "";
        if (matcher.matches()) {
            step4AwssoAu = matcher.group(1);
        }
        // System.out.println("    awsso_au = " + step4AwssoAu);

        // awsso_sl
        pattern = Pattern.compile(".*input value=(.*) type=hidden name=awsso_sl>.*", Pattern.DOTALL);
        matcher = pattern.matcher(step3Content);
        String step4AwssoSl = "";
        if (matcher.matches()) {
            step4AwssoSl = matcher.group(1);
        }
        // System.out.println("    awsso_sl = " + step4AwssoSl);

        // awsso_sw
        pattern = Pattern.compile(".*input value=(.*) type=hidden name=awsso_sw>.*", Pattern.DOTALL);
        matcher = pattern.matcher(step3Content);
        String step4AwssoSw = "";
        if (matcher.matches()) {
            step4AwssoSw = matcher.group(1);
        }
        // System.out.println("    awsso_sw = " + step4AwssoSw);

        // awsso_ia
        pattern = Pattern.compile(".*input value=(.*) type=hidden name=awsso_ia>.*", Pattern.DOTALL);
        matcher = pattern.matcher(step3Content);
        String step4AwssoIa = "";
        if (matcher.matches()) {
            step4AwssoIa = matcher.group(1);
        }
        // System.out.println("    awsso_ia = " + step4AwssoIa);

        // passwordadapter
        pattern = Pattern.compile(".*input value=(.*) type=hidden name=passwordadapter>.*", Pattern.DOTALL);
        matcher = pattern.matcher(step3Content);
        String step4Passwordadapter = "";
        if (matcher.matches()) {
            step4Passwordadapter = matcher.group(1);
        }
        // System.out.println("    passwordadapter = " + step4Passwordadapter);

        // awsso_fl
        pattern = Pattern.compile(".*input value=(.*) type=hidden name=awsso_fl>.*", Pattern.DOTALL);
        matcher = pattern.matcher(step3Content);
        String step4AwssoFl = "";
        if (matcher.matches()) {
            step4AwssoFl = matcher.group(1);
        }
        // System.out.println("    awsso_fl = " + step4AwssoFl);

        // realm
        pattern = Pattern.compile(".*input value=\"(.*)\" type=hidden name=realm>.*", Pattern.DOTALL);
        matcher = pattern.matcher(step3Content);
        String step4Realm = "";
        if (matcher.matches()) {
            step4Realm = matcher.group(1);
        }
        // System.out.println("    realm = " + step4Realm);

        // awsso_ka
        pattern = Pattern.compile(".*input value=\"(.*)\" type=hidden name=awsso_ka>.*", Pattern.DOTALL);
        matcher = pattern.matcher(step3Content);
        String step4AwssoKa = "";
        if (matcher.matches()) {
            step4AwssoKa = matcher.group(1);
        }
        // System.out.println("    awsso_ka = " + step4AwssoKa);

        // awsso_kt
        pattern = Pattern.compile(".*input value=(.*) type=hidden name=awsso_kt>.*", Pattern.DOTALL);
        matcher = pattern.matcher(step3Content);
        String step4AwssoKt = "";
        if (matcher.matches()) {
            step4AwssoKt = matcher.group(1);
        }
        // System.out.println("    awsso_kt = " + step4AwssoKt);

        System.out.println("Step 4: " + step4Action + " ...");
        HttpPost step4Request = new HttpPost(step4Action);
        ArrayList<NameValuePair> step4Params = new ArrayList<NameValuePair>();
        step4Params.add(new BasicNameValuePair("awsso_ar", step4AwssoAr));
        step4Params.add(new BasicNameValuePair("awsso_lia", step4AwssoLia));
        step4Params.add(new BasicNameValuePair("awsso_up", step4AwssoUp));
        step4Params.add(new BasicNameValuePair("awsso_ap", step4AwssoAp));
        step4Params.add(new BasicNameValuePair("awsso_lu", step4AwssoLu));
        step4Params.add(new BasicNameValuePair("awsso_au", step4AwssoAu));
        step4Params.add(new BasicNameValuePair("awsso_sl", step4AwssoSl));
        step4Params.add(new BasicNameValuePair("awsso_sw", step4AwssoSw));
        step4Params.add(new BasicNameValuePair("awsso_ia", step4AwssoIa));
        step4Params.add(new BasicNameValuePair("passwordadapter", step4Passwordadapter));
        step4Params.add(new BasicNameValuePair("awsso_fl", step4AwssoFl));
        step4Params.add(new BasicNameValuePair("realm", step4Realm));
        step4Params.add(new BasicNameValuePair("awsso_ka", step4AwssoKa));
        step4Params.add(new BasicNameValuePair("awsso_kt", step4AwssoKt));

        step4Request.setEntity(new UrlEncodedFormEntity(step4Params, HTTP.UTF_8));
        HttpResponse step4Response = httpClient.execute(step4Request);
        HttpEntity step4Entity = step4Response.getEntity();
        String step4Content = EntityUtils.toString(step4Entity);

        // step 5 AWRedirect
        pattern = Pattern.compile(".*<a id='AWRedirect' href='(.*)'>.*", Pattern.DOTALL);
        matcher = pattern.matcher(step4Content);
        String step5Action = "";
        if (matcher.matches()) {
            step5Action = matcher.group(1);
        }
        step5Action = "https://s1.ariba.com" + step5Action;
        System.out.println("Step 5: " + step5Action + " ...");

        HttpGet step5Request = new HttpGet(step5Action);
        HttpResponse step5Response = httpClient.execute(step5Request);
        HttpEntity step5Entity = step5Response.getEntity();
        String step5Content = EntityUtils.toString(step5Entity);

        // System.out.println(step5Content);

        //
        // step 6
        //

        // search menu ID
        pattern = Pattern.compile(".*<a href=\"#\" class=\"\" _mid=AMBSearch bh=PML id=(.*)>Search</a>.*", Pattern.DOTALL);
        matcher = pattern.matcher(step5Content);
        String step6SearchMenuId = "";
        if (matcher.matches()) {
            step6SearchMenuId = matcher.group(1);
        }
        // System.out.println("    Search Menu ID = " + step6SearchMenuId);

        // sourcing project menu ID
        pattern = Pattern.compile(".*<a id=(.*) bh=PMI class=\"mC\" href=\"#\" tabIndex=\"-1\">Sourcing Project</a>.*", Pattern.DOTALL);
        matcher = pattern.matcher(step5Content);
        String step6SourcingProjectMenuId = "";
        if (matcher.matches()) {
            step6SourcingProjectMenuId = matcher.group(1);
        }
        // System.out.println("    Sourcing Project Menu ID = " + step6SourcingProjectMenuId);

        // awssk
        pattern = Pattern.compile(".*awssk=(.*)&realm.*");
        matcher = pattern.matcher(step5Action);
        String awssk = "";
        if (matcher.matches()) {
            awssk = matcher.group(1);
        }
        // System.out.println("    awssk = " + awssk);

        System.out.println("Step 6: https://s1.ariba.com/Sourcing/Main/aw?awii=xmlhttp&awr=9&awsl=0&awst=0&awssk=" + awssk + "&awsn=" + step6SearchMenuId + "," + step6SourcingProjectMenuId + " ...");

        HttpGet step6Request = new HttpGet("https://s1.ariba.com/Sourcing/Main/aw?awii=xmlhttp&awr=9&awsl=0&awst=0&awssk=" + awssk + "&awsn=" + step6SearchMenuId + "," + step6SourcingProjectMenuId);
        HttpResponse step6Response = httpClient.execute(step6Request);
        HttpEntity step6Entity = step6Response.getEntity();
        String step6Content = EntityUtils.toString(step6Entity);

        System.out.println(step6Content);

        //
        // step 7
        //

        // System.out.println("Step 7: Launch search");

        // HttpPost step7Request = new HttpPost("https://s1.ariba.com/Sourcing/Main/aw");
        // ArrayList<NameValuePair> step7Params = new ArrayList<NameValuePair>();



    }

}
