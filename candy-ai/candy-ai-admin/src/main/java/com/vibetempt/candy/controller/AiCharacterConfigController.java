package com.vibetempt.candy.controller;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.vibetempt.candy.domain.dto.CharacterCreationConfig;
import com.vibetempt.candy.service.CharacterCreationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * AI角色配置 Controller
 * 
 * @author vibetempt
 */
@RestController
@RequestMapping("/api/character/config")
public class AiCharacterConfigController extends BaseController {
    
    @Autowired
    private CharacterCreationService characterCreationService;
    
    /**
     * 获取角色创建配置
     */
    @GetMapping("/creation")
    public AjaxResult getCreationConfig() {
        CharacterCreationConfig config = characterCreationService.getCreationConfig();
        return success(config);
    }
    
    /**
     * 获取角色属性选项
     */
    @GetMapping("/options")
    public AjaxResult getCharacterOptions() {
        Map<String, Object> options = new HashMap<>();
        
        // 风格选项
        options.put("styles", Map.of(
            "realistic", Map.of("label", "Realistic", "imageUrl", "/images/styles/realistic.jpg"),
            "anime", Map.of("label", "Anime", "imageUrl", "/images/styles/anime.jpg")
        ));
        
        // 种族选项
        options.put("ethnicities", Map.of(
            "caucasian", Map.of("label", "Caucasian", "imageUrl", "/images/ethnicity/caucasian.jpg"),
            "latina", Map.of("label", "Latina", "imageUrl", "/images/ethnicity/latina.jpg"),
            "asian", Map.of("label", "Asian", "imageUrl", "/images/ethnicity/asian.jpg")
        ));
        
        // 年龄选项
        options.put("ages", Map.of(
            "teen", "Teen (18+)",
            "20s", "20s"
        ));
        
        // 眼睛颜色选项
        options.put("eyeColors", Map.of(
            "brown", Map.of("label", "Brown", "imageUrl", "/images/eyes/brown.jpg"),
            "blue", Map.of("label", "Blue", "imageUrl", "/images/eyes/blue.jpg"),
            "green", Map.of("label", "Green", "imageUrl", "/images/eyes/green.jpg")
        ));
        
        // 发型选项
        options.put("hairStyles", Map.of(
            "straight", Map.of("label", "Straight", "imageUrl", "/images/hair/straight.jpg"),
            "short", Map.of("label", "Short", "imageUrl", "/images/hair/short.jpg"),
            "long", Map.of("label", "Long", "imageUrl", "/images/hair/long.jpg")
        ));
        
        // 发色选项
        options.put("hairColors", Map.of(
            "blonde", "Blonde",
            "brunette", "Brunette",
            "black", "Black"
        ));
        
        // 体型选项
        options.put("bodyTypes", Map.of(
            "slim", Map.of("label", "Slim", "imageUrl", "/images/body/slim.jpg"),
            "athletic", Map.of("label", "Athletic", "imageUrl", "/images/body/athletic.jpg"),
            "voluptuous", Map.of("label", "Voluptuous", "imageUrl", "/images/body/voluptuous.jpg")
        ));
        
        // 胸围选项
        options.put("breastSizes", Map.of(
            "medium", Map.of("label", "Medium", "imageUrl", "/images/breast/medium.jpg"),
            "large", Map.of("label", "Large", "imageUrl", "/images/breast/large.jpg"),
            "huge", Map.of("label", "Huge", "imageUrl", "/images/breast/huge.jpg")
        ));
        
        // 臀围选项
        options.put("buttSizes", Map.of(
            "medium", Map.of("label", "Medium", "imageUrl", "/images/butt/medium.jpg"),
            "large", Map.of("label", "Large", "imageUrl", "/images/butt/large.jpg"),
            "athletic", Map.of("label", "Athletic", "imageUrl", "/images/butt/athletic.jpg")
        ));
        
        // 个性选项
        options.put("personalities", Map.of(
            "caregiver", Map.of("label", "Caregiver", "icon", "heart", "description", "Nurturing, protective, and always there to offer comfort."),
            "sage", Map.of("label", "Sage", "icon", "brain", "description", "Wise, reflective, and a source of guidance."),
            "innocent", Map.of("label", "Innocent", "icon", "star", "description", "Optimistic, naive, and sees world with wonder."),
            "jester", Map.of("label", "Jester", "icon", "hat", "description", "Playful, humorous, and always there to make you laugh."),
            "temptress", Map.of("label", "Temptress", "icon", "flask", "description", "Flirtatious, playful, and always leaving you wanting more."),
            "dominant", Map.of("label", "Dominant", "icon", "crown", "description", "Assertive, controlling, and commanding."),
            "submissive", Map.of("label", "Submissive", "icon", "handcuffs", "description", "Obedient, yielding, and happy to follow."),
            "lover", Map.of("label", "Lover", "icon", "heart-pink", "description", "Romantic, affectionate, and cherishes deep emotional."),
            "nympho", Map.of("label", "Nympho", "icon", "heart-fire", "description", "Insatiable, passionate, and constantly craving intimacy."),
            "mean", Map.of("label", "Mean", "icon", "snowflake", "description", "Cold, dismissive, and often sarcastic."),
            "confidant", Map.of("label", "Confidant", "icon", "hands", "description", "Trustworthy, a good listener, and always can offer advice."),
            "experimenter", Map.of("label", "Experimenter", "icon", "atom", "description", "Curious, willing, and always eager to try new things.")
        ));
        
        // 职业选项
        options.put("occupations", Map.of(
            "model", "Model",
            "doctor", "Doctor",
            "teacher", "Teacher",
            "nurse", "Nurse",
            "student", "Student",
            "artist", "Artist",
            "singer", "Singer/Musician",
            "actress", "Movie Star/Actress",
            "scientist", "Scientist",
            "lawyer", "Lawyer",
            "writer", "Writer",
            "photographer", "Photographer",
            "athlete", "Professional Athlete",
            "pilot", "Plane Pilot",
            "spy", "Spy",
            "dancer", "Dancer",
            "soldier", "Soldier",
            "police", "Police Officer",
            "firefighter", "Firefighter",
            "coach", "Life Coach",
            "kindergarten", "Kindergarten Teacher",
            "florist", "Florist",
            "baker", "Baker",
            "jewelry", "Jewelry Designer",
            "military", "Soldier/Military Personnel",
            "astronaut", "Astronaut",
            "massage", "Massage Therapist",
            "dentist", "Dentist",
            "nutritionist", "Nutritionist",
            "fitness", "Fitness Coach",
            "pharmacist", "Pharmacist",
            "hairdresser", "Hairdresser",
            "makeup", "Makeup Artist",
            "gynecologist", "Gynecologist",
            "librarian", "Librarian",
            "secretary", "Secretary",
            "social_worker", "Social Worker",
            "fashion_designer", "Fashion Designer",
            "interior_designer", "Interior Designer",
            "cook", "Cook",
            "designer", "Designer",
            "stylist", "Stylist",
            "esthetician", "Esthetician",
            "yoga", "Yoga Instructor",
            "flight_attendant", "Flight Attendant"
        ));
        
        // 爱好选项
        options.put("hobbies", Map.of(
            "photography", "Photography",
            "fitness", "Fitness",
            "vlogging", "Vlogging",
            "traveling", "Traveling",
            "hiking", "Hiking",
            "gaming", "Gaming",
            "parties", "Parties",
            "series", "Series",
            "anime", "Anime",
            "cosplay", "Cosplay",
            "self_development", "Self-Development",
            "writing", "Writing",
            "diy_crafting", "DIY Crafting",
            "veganism", "Veganism",
            "volunteering", "Volunteering",
            "cars", "Cars",
            "art", "Art",
            "netflix", "Watching Netflix",
            "manga_anime", "Manga And Anime",
            "martial_arts", "Martial Arts"
        ));
        
        // 关系选项
        options.put("relationships", Map.of(
            "stranger", Map.of("label", "Stranger", "icon", "fedora"),
            "school_mate", Map.of("label", "School Mate", "icon", "graduation_cap"),
            "colleague", Map.of("label", "Colleague", "icon", "briefcase"),
            "mentor", Map.of("label", "Mentor", "icon", "diamond_books"),
            "girlfriend", Map.of("label", "Girlfriend", "icon", "heart"),
            "sex_friend", Map.of("label", "Sex Friend", "icon", "gender_symbols"),
            "wife", Map.of("label", "Wife", "icon", "wedding_rings"),
            "mistress", Map.of("label", "Mistress", "icon", "crown_heart"),
            "friend", Map.of("label", "Friend", "icon", "clapping_hands"),
            "best_friend", Map.of("label", "Best Friend", "icon", "friendship_hands"),
            "step_sister", Map.of("label", "Step Sister", "icon", "heart_locket"),
            "step_mom", Map.of("label", "Step Mom", "icon", "heart_flame")
        ));
        
        return success(options);
    }
    
    /**
     * 获取角色创建步骤配置
     */
    @GetMapping("/steps")
    public AjaxResult getCreationSteps() {
        CharacterCreationConfig config = characterCreationService.getCreationConfig();
        return success(config.getSteps());
    }
    
    /**
     * 获取特定步骤的配置
     */
    @GetMapping("/steps/{stepCode}")
    public AjaxResult getStepConfig(@PathVariable String stepCode) {
        CharacterCreationConfig config = characterCreationService.getCreationConfig();
        return config.getSteps().stream()
            .filter(step -> step.getStep().equals(stepCode))
            .findFirst()
            .map(step -> success(step))
            .orElse(error("步骤不存在: " + stepCode));
    }
} 