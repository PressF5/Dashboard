# Tilda Analytics Dashboard

Prototype for collecting built-in Tilda statistics for project `15288386` and 14 pages of the «УспешныйЕГЭ» consultation point. Raw responses are stored in PostgreSQL; the Next.js dashboard shows collection status.

## Stack
- Java 21, Spring Boot 3.5, Spring MVC, Spring Data JPA/JdbcTemplate
- PostgreSQL 16, Liquibase
- Next.js 15, React 19, Recharts

## Run locally
1. Install Java 21, Maven, Node.js 20+, npm and Docker.
2. Copy `.env.example` to `.env`; set a strong local PostgreSQL password.
3. Start DB: `docker compose up -d postgres`.
4. Export the variables from `.env` in your shell/IDE. Never commit `.env`.
5. Start API: `cd backend && mvn spring-boot:run`.
6. Start UI: `cd frontend && npm install && npm run dev`.
7. Open `http://localhost:3000`; API is `http://localhost:8080/api`.

## Session and security
The statistics submit endpoint is an observed internal endpoint, not a stable public API. `TILDA_COOKIE` is read from environment and must never be committed. Never commit passwords, `PHPSESSID`, `userid`, `hash`, CAPTCHA responses, or session data. This prototype reuses a valid session cookie; it does not bypass CAPTCHA or solve challenges. If the session expires, restore access through normal browser login and update the secret.

## Important limitations
- Verify report parameters, date filters and page filters against real responses. They are provisional; Tilda may ignore them or return another schema.
- UI intentionally shows counts of stored report snapshots, not fabricated visits/users. Raw JSON is retained so real metrics can be mapped after schema inspection.
- Page-oriented reports run per page with a provisional `filter[page]`; confirm it before relying on page-level results.
- This is an initial scaffold; no successful build or live Tilda collection is claimed yet.

## API
- `GET /api/pages` — page catalog.
- `GET /api/snapshots?from=YYYY-MM-DD&to=YYYY-MM-DD` — stored raw snapshots.
- `POST /api/sync?date=YYYY-MM-DD` — sync a date (defaults to yesterday).
- `GET /actuator/health` — health probe.

Daily sync runs at 03:15 server time. Configure `ANALYTICS_SYNC_CRON` to change it.
