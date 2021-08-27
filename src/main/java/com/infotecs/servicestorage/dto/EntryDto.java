package com.infotecs.servicestorage.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class EntryDto implements Comparable<EntryDto> {

    @NotBlank(message = "Key should not be empty or null")
    private String key;

    @NotBlank(message = "Value should not be empty or null")
    private String value;

    private long ttl = System.currentTimeMillis() / 1000 + 2100L;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public long getTtl() {
        return ttl;
    }

    public void setTtl(long ttl) {
        if (ttl <= 0)
            this.ttl = System.currentTimeMillis() / 1000 + 2100L;
        else
            this.ttl = System.currentTimeMillis() / 1000 + ttl;
    }

    @Override
    public int compareTo(EntryDto o) {
        if (this.getKey().equals(o.getKey())) return 0;
        if (this.ttl - o.ttl < 0) return -1;
        return 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntryDto entryDto = (EntryDto) o;
        return this.getKey().equals(entryDto.getKey());
    }

    @Override
    public String toString() {
        return "EntryDto{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                ", ttl=" + ttl +
                '}';
    }
}
