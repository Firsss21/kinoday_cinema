package ru.kinoday.cinema.cinema.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.concurrent.TimeUnit;

@XmlRootElement(name = "rating")
@Getter
class KinopoiskResponse{
    @XmlElement(name = "kp_rating", required = false)
    private float ratingKp;
    @XmlElement(name = "imdb_rating", required = false)
    private float ratingImdb;
}

@Service
@AllArgsConstructor
public class KinopoiskService {


    private final RestTemplate restTemplate = new RestTemplate();
    private final String url = "https://rating.kinopoisk.ru/";
    private final String format = ".xml";

    @Cacheable("kinopoisk")
    public KinopoiskResponse getRating(long filmId) {
        KinopoiskResponse rating = restTemplate.getForObject(url + filmId + format, KinopoiskResponse.class);
        return rating;
    }

    @Scheduled(fixedRate = 6 * 60 * 60 * 1000)
    @CacheEvict(value="kinopoisk", allEntries=true)
    public void evictCache() {}
}
