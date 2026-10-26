package ru.successfulege.analytics.config;
import java.util.List;
import org.springframework.stereotype.Component;
@Component
public class PageCatalog {
 public record SitePage(String slug, String title, String path) {}
 private final List<SitePage> pages = List.of(
  new SitePage("home", "КОНСУЛЬТАЦИОННЫЙ ПУНКТ «УспешныйЕГЭ» / Главная страница", "/"),
  new SitePage("russian", "Русский язык", "/russian"),
  new SitePage("mathematicsbase", "Математика (база)", "/mathematicsbase"),
  new SitePage("mathematicsprof", "Математика (профиль)", "/mathematicsprof"),
  new SitePage("literature", "Литература", "/literature"),
  new SitePage("history", "История", "/history"),
  new SitePage("social", "Обществознание", "/social"),
  new SitePage("english", "Английский язык", "/english"),
  new SitePage("french", "Французский язык", "/french"),
  new SitePage("german", "Немецкий язык", "/german"),
  new SitePage("geography", "География", "/geography"),
  new SitePage("biology", "Биология", "/biology"),
  new SitePage("chemical", "Химия", "/chemical"),
  new SitePage("physics", "Физика", "/physics"),
  new SitePage("informatics", "Информатика", "/informatics"));
 public List<SitePage> all() { return pages; }
}
