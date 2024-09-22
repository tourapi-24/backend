package tourapi24.backend.place.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.ThreadLocalRandom;

@Getter
@RequiredArgsConstructor
public enum BusanGu {
    강서구(1),
    금정구(2),
    기장군(3),
    남구(4),
    동구(5),
    동래구(6),
    부산진구(7),
    북구(8),
    사상구(9),
    사하구(10),
    서구(11),
    수영구(12),
    연제구(13),
    영도구(14),
    중구(15),
    해운대구(16);

    private final int guCode;

    public static BusanGu getBusanGuByGuName(String guName) {
        for (BusanGu busanGu : BusanGu.values()) {
            if (busanGu.name().equals(guName)) {
                return busanGu;
            }
        }

        throw new IllegalArgumentException("부산광역시에 해당 구 이름이 존재하지 않습니다: " + guName);
    }

    public static BusanGu getRandomBusanGu() {
        BusanGu[] values = BusanGu.values();
        return values[ThreadLocalRandom.current().nextInt(values.length)];
    }

    public static BusanGu getBusanGuByGuCode(int guCode) {
        for (BusanGu busanGu : BusanGu.values()) {
            if (busanGu.guCode == guCode) {
                return busanGu;
            }
        }

        throw new IllegalArgumentException("부산광역시에 해당 구 코드가 존재하지 않습니다: " + guCode);
    }
}
