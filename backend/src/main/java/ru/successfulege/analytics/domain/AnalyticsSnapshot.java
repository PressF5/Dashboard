package ru.successfulege.analytics.domain;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.*;
import java.time.Instant; import java.time.LocalDate;
import org.hibernate.annotations.JdbcTypeCode; import org.hibernate.type.SqlTypes;
@Entity @Table(name="analytics_snapshot",uniqueConstraints=@UniqueConstraint(name="uk_snapshot_day_comm_page",columnNames={"report_date","report_type","page_slug"}))
public class AnalyticsSnapshot {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(name="report_date",nullable=false) private LocalDate reportDate;
 @Column(name="report_type",nullable=false,length=80) private String reportType;
 @Column(name="page_slug",nullable=false,length=80) private String pageSlug;
 @Column(name="page_path",nullable=false,length=255) private String pagePath;
 @JdbcTypeCode(SqlTypes.JSON) @Column(name="payload",nullable=false,columnDefinition="jsonb") private JsonNode payload;
 @Column(name="fetched_at",nullable=false) private Instant fetchedAt;
 protected AnalyticsSnapshot() {}
 public Long getId(){return id;} public LocalDate getReportDate(){return reportDate;} public String getReportType(){return reportType;} public String getPageSlug(){return pageSlug;} public String getPagePath(){return pagePath;} public JsonNode getPayload(){return payload;} public Instant getFetchedAt(){return fetchedAt;}
}
