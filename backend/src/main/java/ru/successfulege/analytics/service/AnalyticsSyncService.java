package ru.successfulege.analytics.service;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Instant; import java.time.LocalDate; import java.util.LinkedHashMap; import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate; import org.springframework.stereotype.Service; import org.springframework.transaction.annotation.Transactional;
import ru.successfulege.analytics.config.PageCatalog;
@Service
public class AnalyticsSyncService {
 private static final String[] PROJECT_REPORTS={"project-main-stats","project-utm-table","project-event-table","project-source-list","project-geo-list"};
 private final TildaStatsClient client; private final PageCatalog pages; private final JdbcTemplate jdbc; private final ObjectMapper mapper;
 public AnalyticsSyncService(TildaStatsClient client,PageCatalog pages,JdbcTemplate jdbc,ObjectMapper mapper){this.client=client;this.pages=pages;this.jdbc=jdbc;this.mapper=mapper;}
 @Transactional public Map<String,Object> sync(LocalDate date){int saved=0;Map<String,Object> failures=new LinkedHashMap<>();
  for(String report:PROJECT_REPORTS)saved+=fetchAndUpsert(date,report,"__project__","",null,failures);
  // Provisional page filter: verify it against sanitized real responses before trusting page-level numbers.
  for(PageCatalog.SitePage page:pages.all())for(String report:new String[]{"page-top-table","site-top-table"})saved+=fetchAndUpsert(date,report,page.slug(),page.path(),page.path(),failures);
  return Map.of("date",date.toString(),"savedOrUpdated",saved,"failures",failures);
 }
 private int fetchAndUpsert(LocalDate date,String report,String slug,String path,String filterPath,Map<String,Object> failures){try{
  String payload=client.fetch(report,filterPath,date.toString()); mapper.readTree(payload);
  jdbc.update("INSERT INTO analytics_snapshot(report_date,report_type,page_slug,page_path,payload,fetched_at) VALUES (?,?,?, ?,CAST(? AS jsonb),?) ON CONFLICT (report_date,report_type,page_slug) DO UPDATE SET page_path=EXCLUDED.page_path,payload=EXCLUDED.payload,fetched_at=EXCLUDED.fetched_at",date,report,slug,path,payload,Instant.now());return 1;
 }catch(Exception ex){failures.put(report+":"+slug,ex.getClass().getSimpleName()+": "+safeMessage(ex));return 0;}}
 private String safeMessage(Exception ex){String m=ex.getMessage(); if(m==null)return "request failed"; return m.replaceAll("(?i)(PHPSESSID|userid|hash)=([^;\\s]+)","$1=[REDACTED]");}
}
