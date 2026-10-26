package ru.successfulege.analytics.repository;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.successfulege.analytics.domain.AnalyticsSnapshot;
public interface AnalyticsSnapshotRepository extends JpaRepository<AnalyticsSnapshot,Long> {
 List<AnalyticsSnapshot> findByReportDateBetweenOrderByReportDateAsc(LocalDate from, LocalDate to);
}
