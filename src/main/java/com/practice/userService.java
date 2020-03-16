package com.practice;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class userService {
  private List<String> dbUsers;
  private final JdbcTemplate jdbcTemplate;

  public userService(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

 @Cacheable(value = "users")
  //@Caching()
 //@CachePut(value = "users")
  public List<String> getdbUsers() throws InterruptedException {
    Thread.sleep(5000);
    System.out.println("---in db call ---");
    dbUsers= this.jdbcTemplate.queryForList("SELECT * FROM users").stream()
        .map((m) -> m.values().toString())
        .collect(Collectors.toList());
    return dbUsers;
  }
  @Scheduled(fixedRate = 86400000, initialDelay = 50000)
  @CacheEvict(value = "users", allEntries=true)
  public void evictAllcachesAtIntervals() {
    System.out.println("Evicted");
  }
  @CacheEvict(value = "users", allEntries=true)
  public void evictCache() {
    System.out.println("Evicted");
  }
}
