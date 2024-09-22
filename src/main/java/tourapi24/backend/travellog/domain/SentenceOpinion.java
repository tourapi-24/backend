package tourapi24.backend.travellog.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SentenceOpinion {

    // 사진
    PHOTO_SPOT("PHOTO", "📷포토스팟이 많아요"),
    PRETTY_COLORS("PHOTO", "🌈색감이 예뻐요"),
    SNS_FRIENDLY("PHOTO", "📱sns에 올리기 좋아요"),
    SPECIAL_PHOTO_ZONE("PHOTO", "✨특별한 포토존이 있어요"),
    BEST_SHOT("PHOTO", "🤴인생샷을 찍을 수 있어요"),
    GREAT_NATURAL_LIGHT("PHOTO", "🌞자연광이 멋져요"),
    BEAUTIFUL_LIGHTING("PHOTO", "💡조명이 아름다워요"),
    PANORAMIC_VIEW("PHOTO", "🏞️파노라마 뷰가 멋져요"),

    // 분위기
    PEACEFUL_ATMOSPHERE("ATMOSPHERE", "😌고즈넉한 분위기를 느낄 수 있어요"),
    QUIET_AND_PEACEFUL("ATMOSPHERE", "🤫고요하고 평화로워요"),
    ROMANTIC("ATMOSPHERE", "🥰로맨틱해요"),
    TRADITIONAL("ATMOSPHERE", "🛖전통적이에요"),
    CLEAN_SEA("ATMOSPHERE", "🌊바다가 깨끗해요"),
    FAIRYTALE_LIKE("ATMOSPHERE", "🦄동화같아요"),
    SPLENDID("ATMOSPHERE", "🎆화려해요"),
    HEARTWARMING("ATMOSPHERE", "🤝정겨워요"),
    EXOTIC("ATMOSPHERE", "🗺️이국적이에요"),

    // 편의시설
    CLEAN_FACILITIES("CONVENIENCE", "🧼시설이 청결해요"),
    CLEAN_RESTROOMS("CONVENIENCE", "🚽화장실이 깨끗해요"),
    SPACIOUS_PARKING("CONVENIENCE", "🚘주차장이 넓어요"),
    GOOD_ACCESSIBILITY("CONVENIENCE", "🚊접근성이 좋아요"),
    WHEELCHAIR_ACCESSIBLE("CONVENIENCE", "🧑‍🦽휠체어 접근성이 좋아요"),
    FRIENDLY_STAFF("CONVENIENCE", "☺️친절해요"),

    // 관광
    HISTORICAL_VALUE("TOURISM", "🗿역사적인 가치가 있어요"),
    CULTURAL_EXPERIENCE("TOURISM", "🧜‍♀️문화체험이 가능해요"),
    VARIOUS_ACTIVITIES("TOURISM", "💃다양한 체험 프로그램이 있어요"),
    CHILD_FRIENDLY("TOURISM", "👼아이들과 함께하기 좋아요"),
    MANY_ARTWORKS("TOURISM", "🎨예술작품이 많아요"),
    GOOD_FOR_WALKING_TOUR("TOURISM", "🥾도보여행하기 좋아요"),
    FEW_TOURISTS("TOURISM", "😊관광객이 적어 여유로워요"),
    BEAUTIFUL_NATURE("TOURISM", "🏞️자연경관이 아름다워요"),
    BEAUTIFUL_NIGHT_VIEW("TOURISM", "🌉야경이 아름다워요"),
    GOOD_FOR_DRIVE("TOURISM", "🛣️드라이브하기 좋아요"),
    BEAUTIFUL_SUNSET("TOURISM", "🌇일몰이 멋져요"),
    BEAUTIFUL_SUNRISE("TOURISM", "🌄일출이 멋져요"),
    WELL_MAINTAINED_WALKING_PATHS("TOURISM", "🚶‍♀️산책로가 잘 조성되어 있어요"),
    MANY_FLOWERS("TOURISM", "🌸꽃이 많이 피어있어요"),

    // 음식
    LOCAL_FOOD("FOOD", "🍢지역음식을 맛 볼 수 있어요"),
    DELICIOUS_FOOD("FOOD", "🍲음식이 맛있어요"),
    DELICIOUS_DESSERT("FOOD", "🍮디저트가 맛있어요"),
    DELICIOUS_DRINKS("FOOD", "🥤음료가 맛있어요"),
    GOOD_VALUE("FOOD", "💸가성비가 좋아요"),
    VARIOUS_STREET_FOOD("FOOD", "🌮길거리 음식이 다양해요");

    private final String category;
    private final String description;
}
