package ru.successfulege.analytics.service;
import java.time.LocalDate; import org.slf4j.Logger; import org.slf4j.LoggerFactory; import org.springframework.scheduling.annotation.Scheduled; import org.springframework.stereotype.Component;
@Component public class AnalyticsSyncScheduler {
 private static final Logger log=LoggerFactory.getLogger(AnalyticsSyncScheduler.class); private final AnalyticsSyncService sync;
 public AnalyticsSyncScheduler(AnalyticsSyncService sync){this.sync=sync;}
 /** Runs at 03:15 server time and attempts to collect the previous calendar day. */
 @Scheduled(cron="${analytics.sync-cron:0 15 3 * * *}") public void collectYesterday(){try{log.info("Starting Tilda analytics sync for yesterday");sync.sync(LocalDate.now().minusDays(1));}catch(Exception ex){log.error("Scheduled Tilda analytics sync failed",ex);}}
}
