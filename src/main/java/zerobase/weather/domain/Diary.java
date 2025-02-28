package zerobase.weather.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity(name = "diary")
@Getter
@Setter
@NoArgsConstructor
public class Diary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // identity는 id 생성을 DB에서 하도록 하는 설정
    private int id;
    private String weather;
    private String icon;
    private double temperature;
    private String text;
    private LocalDate date;

    public void setDateWeather(DateWeather dateWeather) {
        this.weather = dateWeather.getWeather();
        this.icon = dateWeather.getIcon();
        this.temperature = dateWeather.getTemperature();
    }
}
