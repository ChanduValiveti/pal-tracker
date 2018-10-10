package io.pivotal.pal.tracker;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class InMemoryTimeEntryRepository implements TimeEntryRepository {

    private Map<Long, TimeEntry> memMap = new HashMap<>();
    private long iD = 0;

    public TimeEntry create(TimeEntry timeEntry) {
        iD = iD+1;
        TimeEntry savedEntry =
                new TimeEntry(iD, timeEntry.getProjectId(), timeEntry.getUserId(), timeEntry.getDate(), timeEntry.getHours());
        this.memMap.put(iD, savedEntry);
        return savedEntry;
    }

    public TimeEntry find(Long id) {

        return this.memMap.get(id);
    }

    public TimeEntry update(Long id, TimeEntry timeEntry) {
        if (this.memMap.containsKey(id)) {
            TimeEntry updatedEntry =
                    new TimeEntry(id, timeEntry.getProjectId(), timeEntry.getUserId(), timeEntry.getDate(), timeEntry.getHours());
            this.memMap.put(id, updatedEntry);
            return updatedEntry;
        }
        return null;
    }

    public void delete(Long id) {

        this.memMap.remove(id);
    }

    public List<TimeEntry> list()
    {
        return memMap.values().stream().collect(Collectors.toList());

    }

}
