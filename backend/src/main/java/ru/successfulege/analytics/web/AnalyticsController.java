package ru.successfulege.analytics.web;
import java.time.LocalDate; import org.springframework.format.annotation.DateTimeFormat; import org.springframework.web.bind.annotation.*;
import ru.successfulege.analytics.config.PageCatalog; import ru.successfulege.analytics.repository.AnalyticsSnapshotRepository; import ru.successfulege.analytics.service.AnalyticsSyncService;
@RestController @RequestMapping("/api")
public class AnalyticsController {
 private final AnalyticsSyncService sync; private final AnalyticsSnapshotRepository repository; private final PageCatalog pages;
 public AnalyticsController(AnalyticsSyncService sync,AnalyticsSnapshotRepository repository,PageCatalog pages){this.sync=sync;this.repository=repository;this.pages=pages;}
 @GetMapping("/pages") public Object pages(){return pages.all();}
 @GetMapping("/snapshots") public Object snapshots(@RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate from,@RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate to){if(from.isAfter(to))throw new IllegalArgumentException("'from' must be before or equal to 'to'");return repository.findByReportDateBetweenOrderByReportDateAsc(from,to);}
 @PostMapping("/sync") public Object sync(@RequestParam(required=false) @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate date){return sync.sync(date==null?LocalDate.now().minusDays(1):date);}
}
