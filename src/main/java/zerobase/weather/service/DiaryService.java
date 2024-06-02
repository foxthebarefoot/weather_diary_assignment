package zerobase.weather.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import zerobase.weather.WeatherApplication;
import zerobase.weather.domain.DateWeather;
import zerobase.weather.domain.Diary;
import zerobase.weather.repository.DiaryRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class DiaryService {

    private final WeatherService weatherService;
    private final DiaryRepository diaryRepository;
    private static final Logger logger = LoggerFactory.getLogger(WeatherApplication.class);

    public DiaryService(WeatherService weatherService,
                        DiaryRepository diaryRepository) {
        this.weatherService = weatherService;
        this.diaryRepository = diaryRepository;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void createDiary(LocalDate date, String text) {
        logger.info("started to create diary of {}", date);
        // 날씨 데이터 가져오기
        DateWeather dateWeather = weatherService.getDateWeather(date);

        Diary diary = new Diary();
        diary.setDateWeather(dateWeather);
        diary.setDate(date);
        diary.setText(text);
        diaryRepository.save(diary);
        logger.info("finished to create diary of {}", date);
    }

    @Transactional(readOnly = true)
    public List<Diary> readDiary(LocalDate date) {
        logger.info("requested to get diary of {}", date);
        return diaryRepository.findAllByDate(date);
    }

    @Transactional(readOnly = true)
    public List<Diary> readDiaries(LocalDate startDate, LocalDate endDate) {
        logger.info("requested to get diary between {} and {}", startDate, endDate);
        return diaryRepository.findAllByDateBetween(startDate, endDate);
    }

    @Transactional
    public void updateDiary(LocalDate date, String text) {
        Diary diary = diaryRepository.getFirstByDate(date);
        logger.info("read diary for update of {}", date);
        diary.setText(text);
        diaryRepository.save(diary); // save 함수로 덮어쓰기가 된다.
        logger.info("finished to update diary of {}", date);
    }

    @Transactional
    public void deleteDiary(LocalDate date) {
        diaryRepository.deleteAllByDate(date);
        logger.info("finished to delete diary of {}", date);
    }

}
