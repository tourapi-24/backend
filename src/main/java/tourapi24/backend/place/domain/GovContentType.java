package tourapi24.backend.place.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum GovContentType {
    관광지(12),
    문화시설(14),
    축제공연행사(15),
    여행코스(25),
    레포츠(28),
    숙박(32),
    쇼핑(38),
    음식점(39);

    private final Integer id;

    public static GovContentType fromId(int id) {
        for (GovContentType value : values()) {
            if (value.id == id) {
                return value;
            }
        }

        throw new IllegalArgumentException("Unknown GovContentTypeId: " + id);
    }
}
