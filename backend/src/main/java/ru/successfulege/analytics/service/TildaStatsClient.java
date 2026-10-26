package ru.successfulege.analytics.service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
/** Adapter for Tilda's observed internal statistics endpoint; it is not a stable public API. */
@Component
public class TildaStatsClient {
 private final RestClient client; private final String projectId; private final String cookie;
 public TildaStatsClient(@Value("${tilda.stats-url}") String url,@Value("${tilda.project-id}") String projectId,@Value("${tilda.cookie:}") String cookie){this.client=RestClient.builder().baseUrl(url).build();this.projectId=projectId;this.cookie=cookie;}
 public String fetch(String reportType,String pagePath,String date){
  if(cookie==null||cookie.isBlank()) throw new IllegalStateException("TILDA_COOKIE is empty; configure a valid session cookie in the environment.");
  MultiValueMap<String,String> form=new LinkedMultiValueMap<>(); form.add("comm",reportType); form.add("projectid",projectId); form.add("date_from",date); form.add("date_to",date); if(pagePath!=null&&!pagePath.isBlank())form.add("filter[page]",pagePath);
  return client.post().contentType(MediaType.APPLICATION_FORM_URLENCODED).header("Cookie",cookie).header("Origin","https://tilda.cc").header("Referer","https://tilda.cc/projects/"+projectId+"/statistics/").body(form).retrieve().body(String.class);
 }
}
