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
        System.out.println(step3Content);

        pattern = Pattern.compile(".*action=\"(.*)\" name=.*", Pattern.DOTALL);
        matcher = pattern.matcher(step3Content);
        String step4Action = "";
        if (matcher.matches()) {
            step4Action = matcher.group(1);
        }
        System.out.println("    action: " + step4Action);

        pattern = Pattern.compile(".*input value=\"(.*)\" .* name=awsso_up>.*", Pattern.DOTALL);
        matcher = pattern.matcher(step3Content);
        String step4AwssoUp = "";
        if (matcher.matches()) {
            step4AwssoUp = matcher.group(1);
        }
        System.out.println("    awsso_up: " + step4AwssoUp);

        pattern = Pattern.compile(".*input value=\"(.*)\" .* name=awsso_lu>.*", Pattern.DOTALL);
        matcher = pattern.matcher(step3Content);
        String step4AwssoLu = "";
        if (matcher.matches()) {
            step4AwssoLu = matcher.group(1);
        }
        System.out.println("    awsso_lu: " + step4AwssoLu);

        pattern = Pattern.compile(".*input value=\"(.*)\" .* name=awsso_au>.*", Pattern.DOTALL);
        matcher = pattern.matcher(step3Content);
        String step4AwssoAu = "";
        if (matcher.matches()) {
            step4AwssoAu = matcher.group(1);
        }
        System.out.println("    awsso_au: " + step4AwssoAu);

        pattern = Pattern.compile(".*input value=\"(.*)\" .* name=awsso_ka>.*", Pattern.DOTALL);
        matcher = pattern.matcher(step3Content);
        String step4AwssoKa = "";
        if (matcher.matches()) {
            step4AwssoKa = matcher.group(1);
        }
        System.out.println("    awsso_ka: " + step4AwssoKa);

        pattern = Pattern.compile(".*input value=\"(.*)\" .* name=awsso_kt>.*", Pattern.DOTALL);
        matcher = pattern.matcher(step3Content);
        String step4AwssoKt = "";
        if (matcher.matches()) {
            step4AwssoKt = matcher.group(1);
        }
        System.out.println("    awsso_kt: " + step4AwssoKt);

        step4Action = "https://s1.ariba.com" + step4Action;
        System.out.println("Step 4: " + step4Action + " ...");
        HttpPost step4Request = new HttpPost(step4Action);
        ArrayList<NameValuePair> step4Params = new ArrayList<NameValuePair>();
        step4Params.add(new BasicNameValuePair("awsso_ar", "true"));
        step4Params.add(new BasicNameValuePair("awsso_lia", "ACM"));
        step4Params.add(new BasicNameValuePair("awsso_up", step4AwssoUp));
        step4Params.add(new BasicNameValuePair("awsso_ap", "ACM"));
        step4Params.add(new BasicNameValuePair("awsso_lu", step4AwssoLu));
        step4Params.add(new BasicNameValuePair("awsso_au", step4AwssoAu));
        step4Params.add(new BasicNameValuePair("awsso_sl", "1800000"));
        step4Params.add(new BasicNameValuePair("awsso_sw", "60000"));
        step4Params.add(new BasicNameValuePair("awsso_ia", "true"));
        step4Params.add(new BasicNameValuePair("passwordadapter", "PasswordAdapter1"));
        step4Params.add(new BasicNameValuePair("awsso_fl", "1"));
        step4Params.add(new BasicNameValuePair("realm", "eads-t"));
        step4Params.add(new BasicNameValuePair("awsso_ka", step4AwssoKa));
        step4Params.add(new BasicNameValuePair("awsso_kt", step4AwssoKt));

        step4Request.setEntity(new UrlEncodedFormEntity(step4Params, HTTP.UTF_8));
        HttpResponse step4Response = httpClient.execute(step4Request);
        HttpEntity step4Entity = step4Response.getEntity();
        String step4Content = EntityUtils.toString(step4Entity);

        System.out.println(step4Content);

        pattern = Pattern.compile(".*<a.*href='(.*)'>.*", Pattern.DOTALL);
        matcher = pattern.matcher(step4Content);
        String step5Url = "";
        if (matcher.matches()) {
            step5Url = matcher.group(1);
        }
        step5Url = "https://s1.ariba.com" + step5Url;
        System.out.println("Step 5: " + step5Url + " ...");
        HttpGet step5Request = new HttpGet(step5Url);
        HttpResponse step5Response = httpClient.execute(step5Request);
        HttpEntity step5Entity = step5Response.getEntity();
        String step5Content = EntityUtils.toString(step5Entity);

        System.out.println("Searching sourcing ...");

        pattern = Pattern.compile(".*<script>RJS.*awssk=(.*)',.*,'.*'.*</script>.*", Pattern.DOTALL);
        matcher = pattern.matcher(step5Content);
        String step5awssk = "";
        if (matcher.matches()) {
            step5awssk = matcher.group(1);
        }
        System.out.println("awssk: " + step5awssk);

        pattern = Pattern.compile(".*bh=PML id=(.*)>Search</a>.*", Pattern.DOTALL);
        matcher = pattern.matcher(step5Content);
        String step5MenuId = "";
        if (matcher.matches()) {
            step5MenuId = matcher.group(1);
        }
        System.out.println("menuId: " + step5MenuId);

        pattern = Pattern.compile(".*<a id=(.*) bh=PMI.*>Sourcing Project</a>.*", Pattern.DOTALL);
        matcher = pattern.matcher(step5Content);
        String step5SubMenuId = "";
        if (matcher.matches()) {
            step5SubMenuId = matcher.group(1);
        }
        System.out.println("subMenuId: " + step5SubMenuId);

        System.out.println("Menu browsing ...");

        HttpGet step6Request = new HttpGet("https://s1.ariba.com/Sourcing/Main/aw?awr=4&awssk=" + step5awssk + "&awsn=" + step5MenuId + "," + step5SubMenuId
                + "&awst=0&awsl=0&awii=xmlhttp");
        HttpResponse step6Response = httpClient.execute(step6Request);
        HttpEntity step6Entity = step6Response.getEntity();

        String step6Content = EntityUtils.toString(step6Entity);

        System.out.println(step6Content);

    }

}
