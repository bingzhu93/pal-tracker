package io.pivotal.pal.tracker;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;


import javax.sql.DataSource;
import java.util.List;

public class JdbcTimeEntryRepository implements TimeEntryRepository {

    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;
    private long sequence = 0;

    public JdbcTimeEntryRepository(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public TimeEntry create(TimeEntry timeEntry) {
        if (timeEntry.getId()==0) {
            timeEntry.setId(++sequence);
        }
        jdbcTemplate.update(
                "INSERT INTO time_entries (id, project_id, user_id, date, hours) " +
                        "VALUES (?, ?, ?, ?, ?)", new Object[]{timeEntry.getId(), timeEntry.getProjectId(), timeEntry.getUserId()
                        , timeEntry.getDate(), timeEntry.getHours()}
        );
        return timeEntry;
    }

    @Override
    public TimeEntry find(Long id) {
        String sql = "SELECT * FROM time_entries WHERE id = ?";
        try {
            TimeEntry timeEntry = (TimeEntry) jdbcTemplate.queryForObject(
                    sql, new Object[]{id},
                    new BeanPropertyRowMapper(TimeEntry.class));
            return timeEntry;
        } catch (Exception e) {
            return null;
        }

    }

    @Override
    public List<TimeEntry> list() {
        String sql = "SELECT * FROM time_entries";
        try {
            List<TimeEntry> timeEntry = jdbcTemplate.query(
                    sql,
                    new BeanPropertyRowMapper(TimeEntry.class));
            return timeEntry;
        } catch (Exception e) {
            return null;
        }

    }

    @Override
    public TimeEntry update(Long id, TimeEntry timeEntry) {
        timeEntry.setId(id);
        jdbcTemplate.update("UPDATE time_entries SET project_id=?, user_id=?, date=?, hours=? WHERE id=?", new Object[]{timeEntry.getProjectId(), timeEntry.getUserId(),
                timeEntry.getDate(), timeEntry.getHours(), id});
        return timeEntry;
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update(
                "DELETE FROM time_entries WHERE id=?", new Object[]{id});

    }
}
