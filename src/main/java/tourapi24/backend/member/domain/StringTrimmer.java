package tourapi24.backend.member.domain;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;

@Slf4j
public class StringTrimmer {

    @PrePersist
    @PreUpdate
    public void trimStrings(Object entity) {
        Class<?> clazz = entity.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            if (field.getType().equals(String.class)) {
                try {
                    field.setAccessible(true);
                    String value = (String) field.get(entity);
                    if (value != null) {
                        String strippedValue = value.strip();
                        field.set(entity, strippedValue);
                    }
                } catch (Exception e) {
                    // 예외가 발생하면 로그를 남기고 원래 값을 유지
                    log.warn("Failed to strip field: {}. Original value will be kept. Error: {}",
                            field.getName(), e.getMessage());
                    // 예외 상세 정보는 디버그 레벨로 로깅
                    log.debug("Exception details:", e);
                }
            }
        }
    }
}
