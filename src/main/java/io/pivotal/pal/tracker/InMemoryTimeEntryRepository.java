package io.pivotal.pal.tracker;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryTimeEntryRepository implements TimeEntryRepository {

    List<TimeEntry> data = new ArrayList<>();

    @Override
    public TimeEntry create(TimeEntry inputTimeEntry) {
        data.add(inputTimeEntry);
        inputTimeEntry.setId(data.size());
        return inputTimeEntry;
    }

    @Override
    public TimeEntry find(long id) {
        TimeEntry result = null;
        for (TimeEntry timeEntry : data) {
            if (timeEntry.getId() == id) {
                result = timeEntry;
            }
        }
        return result;
    }

    @Override
    public List<TimeEntry> list() {
        return data;
    }

    @Override
    public TimeEntry update(long id, TimeEntry timeEntry) {

        for(int i = 0; i < data.size(); i++){
            TimeEntry entry = data.get(i);
            if (entry.getId() == id) {
                //data.set(i, timeEntry);
                updateEntry(entry, timeEntry);
                return entry;
            }
        }
        return null;
    }

    private void updateEntry(TimeEntry entry, TimeEntry timeEntry) {
        entry.setId(timeEntry.getId());
        entry.setProjectId(timeEntry.getProjectId());
        entry.setDate(timeEntry.getDate());
        entry.setHours(timeEntry.getHours());
        entry.setUserId(timeEntry.getUserId());
    }

    @Override
    public void delete(long id) {

        for (TimeEntry timeEntry : data) {
            if (timeEntry.getId() == id) {
                data.remove(timeEntry);
                return;
            }
        }

    }

}
