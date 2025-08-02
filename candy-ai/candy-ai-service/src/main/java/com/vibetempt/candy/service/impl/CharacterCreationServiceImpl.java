package com.vibetempt.candy.service.impl;

import com.alibaba.fastjson2.JSON;
import com.ruoyi.common.utils.SecurityUtils;
import com.vibetempt.candy.domain.AiCharacter;
import com.vibetempt.candy.domain.AiCharacterCreationSession;
import com.vibetempt.candy.domain.enums.CharacterCreationStep;
import com.vibetempt.candy.domain.dto.CharacterCreationConfig;
import com.vibetempt.candy.domain.dto.CreationProgress;
import com.vibetempt.candy.domain.dto.StepOptions;
import com.vibetempt.candy.domain.mapper.AiCharacterCreationSessionMapper;
import com.vibetempt.candy.service.AiCharacterService;
import com.vibetempt.candy.service.CharacterCreationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 角色创建服务实现类
 * 
 * @author vibetempt
 */
@Service
public class CharacterCreationServiceImpl implements CharacterCreationService {
    
    @Autowired
    private AiCharacterCreationSessionMapper sessionMapper;
    
    @Autowired
    private AiCharacterService aiCharacterService;
    
    @Override
    public CharacterCreationConfig getCreationConfig() {
        CharacterCreationConfig config = new CharacterCreationConfig();
        List<CharacterCreationConfig.StepConfig> steps = new ArrayList<>();
        
        // 步骤1: 风格选择
        CharacterCreationConfig.StepConfig styleStep = new CharacterCreationConfig.StepConfig();
        styleStep.setStep("style");
        styleStep.setTitle("Choose Style*");
        styleStep.setRequired(true);
        styleStep.setOptions(Arrays.asList(
            createOption("realistic", "Realistic", "/images/styles/realistic.jpg", null, null, Arrays.asList("occupation_hobbies"), false),
            createOption("anime", "Anime", "/images/styles/anime.jpg", null, null, new ArrayList<>(), false)
        ));
        steps.add(styleStep);
        
        // 步骤2: 基础属性
        CharacterCreationConfig.StepConfig basicStep = new CharacterCreationConfig.StepConfig();
        basicStep.setStep("basic_attributes");
        basicStep.setTitle("基础属性");
        basicStep.setRequired(true);
        basicStep.setSections(Arrays.asList(
            createEthnicitySection(),
            createAgeSection(),
            createEyeColorSection()
        ));
        steps.add(basicStep);
        
        // 步骤3: 发型发色
        CharacterCreationConfig.StepConfig hairStep = new CharacterCreationConfig.StepConfig();
        hairStep.setStep("hair");
        hairStep.setTitle("发型发色");
        hairStep.setRequired(true);
        hairStep.setSections(Arrays.asList(
            createHairStyleSection(),
            createHairColorSection()
        ));
        steps.add(hairStep);
        
        // 步骤4: 体型特征
        CharacterCreationConfig.StepConfig bodyStep = new CharacterCreationConfig.StepConfig();
        bodyStep.setStep("body");
        bodyStep.setTitle("体型特征");
        bodyStep.setRequired(true);
        bodyStep.setSections(Arrays.asList(
            createBodyTypeSection(),
            createBreastSizeSection(),
            createButtSizeSection()
        ));
        steps.add(bodyStep);
        
        // 步骤5: 个性选择
        CharacterCreationConfig.StepConfig personalityStep = new CharacterCreationConfig.StepConfig();
        personalityStep.setStep("personality");
        personalityStep.setTitle("Choose Personality*");
        personalityStep.setRequired(true);
        personalityStep.setOptions(createPersonalityOptions());
        steps.add(personalityStep);
        
        // 步骤6: 职业爱好 (可选)
        CharacterCreationConfig.StepConfig occupationStep = new CharacterCreationConfig.StepConfig();
        occupationStep.setStep("occupation_hobbies");
        occupationStep.setTitle("职业爱好");
        occupationStep.setRequired(false);
        occupationStep.setSections(Arrays.asList(
            createOccupationSection(),
            createHobbiesSection()
        ));
        steps.add(occupationStep);
        
        // 步骤7: 关系选择
        CharacterCreationConfig.StepConfig relationshipStep = new CharacterCreationConfig.StepConfig();
        relationshipStep.setStep("relationship");
        relationshipStep.setTitle("Choose Relationship*");
        relationshipStep.setRequired(true);
        relationshipStep.setOptions(createRelationshipOptions());
        steps.add(relationshipStep);
        
        // 步骤8: 确认信息
        CharacterCreationConfig.StepConfig summaryStep = new CharacterCreationConfig.StepConfig();
        summaryStep.setStep("summary");
        summaryStep.setTitle("Summary");
        summaryStep.setDescription("确认角色信息并创建角色");
        summaryStep.setRequired(true);
        steps.add(summaryStep);
        
        config.setSteps(steps);
        return config;
    }
    
    @Override
    public AiCharacterCreationSession startCreation(Long userId) {
        // 检查是否有活跃会话
        AiCharacterCreationSession existingSession = sessionMapper.selectActiveSessionByUserId(userId);
        if (existingSession != null) {
            return existingSession;
        }
        
        // 创建新会话
        AiCharacterCreationSession session = new AiCharacterCreationSession();
        session.setUserId(userId);
        session.setSessionId(generateSessionId());
        session.setCurrentStep("style");
        session.setStatus("active");
        session.setSelections("{}");
        
        sessionMapper.insertAiCharacterCreationSession(session);
        return session;
    }
    
    @Override
    public StepOptions getStepOptions(String stepCode, AiCharacterCreationSession session) {
        CharacterCreationConfig config = getCreationConfig();
        CharacterCreationConfig.StepConfig stepConfig = config.getSteps().stream()
            .filter(step -> step.getStep().equals(stepCode))
            .findFirst()
            .orElse(null);
        
        if (stepConfig == null) {
            throw new RuntimeException("步骤不存在: " + stepCode);
        }
        
        StepOptions stepOptions = new StepOptions();
        stepOptions.setStepCode(stepCode);
        stepOptions.setTitle(stepConfig.getTitle());
        stepOptions.setDescription(stepConfig.getDescription());
        stepOptions.setRequired(stepConfig.isRequired());
        stepOptions.setOptions(stepConfig.getOptions());
        stepOptions.setSections(stepConfig.getSections());
        
        // 获取当前选择
        Map<String, Object> currentSelections = getCurrentSelections(session);
        stepOptions.setCurrentSelections(currentSelections);
        
        return stepOptions;
    }
    
    @Override
    public void saveStepSelection(String stepCode, Map<String, Object> selections, AiCharacterCreationSession session) {
        Map<String, Object> currentSelections = getCurrentSelections(session);
        currentSelections.putAll(selections);
        
        session.setSelections(JSON.toJSONString(currentSelections));
        sessionMapper.updateAiCharacterCreationSession(session);
    }
    
    @Override
    public String getNextStep(String currentStep, AiCharacterCreationSession session) {
        CharacterCreationStep currentStepEnum = CharacterCreationStep.getByCode(currentStep);
        if (currentStepEnum == null) {
            return null;
        }
        
        CharacterCreationStep nextStep = currentStepEnum.getNextStep();
        if (nextStep == null) {
            return null;
        }
        
        // 检查是否需要跳过步骤6
        if (nextStep.getCode().equals("occupation_hobbies")) {
            Map<String, Object> selections = getCurrentSelections(session);
            String style = (String) selections.get("style");
            if ("realistic".equals(style)) {
                nextStep = nextStep.getNextStep(); // 跳过步骤6
            }
        }
        
        return nextStep.getCode();
    }
    
    @Override
    public String getPreviousStep(String currentStep, AiCharacterCreationSession session) {
        CharacterCreationStep currentStepEnum = CharacterCreationStep.getByCode(currentStep);
        if (currentStepEnum == null) {
            return null;
        }
        
        CharacterCreationStep previousStep = currentStepEnum.getPreviousStep();
        if (previousStep == null) {
            return null;
        }
        
        // 检查是否需要跳过步骤6
        if (previousStep.getCode().equals("occupation_hobbies")) {
            Map<String, Object> selections = getCurrentSelections(session);
            String style = (String) selections.get("style");
            if ("realistic".equals(style)) {
                previousStep = previousStep.getPreviousStep(); // 跳过步骤6
            }
        }
        
        return previousStep.getCode();
    }
    
    @Override
    public AiCharacter completeCreation(AiCharacterCreationSession session) {
        Map<String, Object> selections = getCurrentSelections(session);
        
        // 创建角色
        AiCharacter character = new AiCharacter();
        character.setName(generateCharacterName(selections));
        character.setDescription(generateCharacterDescription(selections));
        character.setCreatorId(session.getUserId());
        character.setCreateBy(SecurityUtils.getUsername());
        character.setStatus("0");
        
        // 设置角色属性
        character.setCharacterType("girls"); // 默认设置为girls类型
        character.setMembershipType("normal"); // 默认设置为普通会员
        character.setIsSystem("1"); // 系统创建的角色
        character.setStyle((String) selections.get("style"));
        character.setEthnicity((String) selections.get("ethnicity"));
        character.setAge((String) selections.get("age"));
        character.setEyeColor((String) selections.get("eyeColor"));
        character.setHairStyle((String) selections.get("hairStyle"));
        character.setHairColor((String) selections.get("hairColor"));
        character.setBodyType((String) selections.get("bodyType"));
        character.setBreastSize((String) selections.get("breastSize"));
        character.setButtSize((String) selections.get("buttSize"));
        character.setPersonality((String) selections.get("personality"));
        character.setOccupation((String) selections.get("occupation"));
        character.setHobbies((String) selections.get("hobbies"));
        character.setRelationship((String) selections.get("relationship"));
        
        // 保存角色
        AiCharacter savedCharacter = aiCharacterService.createCharacter(character);
        
        // 更新会话状态
        session.setStatus("completed");
        sessionMapper.updateAiCharacterCreationSession(session);
        
        return savedCharacter;
    }
    
    @Override
    public CreationProgress getProgress(AiCharacterCreationSession session) {
        CreationProgress progress = new CreationProgress();
        progress.setSessionId(session.getSessionId());
        progress.setCurrentStep(session.getCurrentStep());
        
        Map<String, Object> selections = getCurrentSelections(session);
        progress.setSelections(selections);
        
        // 计算进度
        List<CreationProgress.StepProgress> stepProgressList = new ArrayList<>();
        CharacterCreationConfig config = getCreationConfig();
        
        for (CharacterCreationConfig.StepConfig stepConfig : config.getSteps()) {
            CreationProgress.StepProgress stepProgress = new CreationProgress.StepProgress();
            stepProgress.setStepCode(stepConfig.getStep());
            stepProgress.setStepName(stepConfig.getTitle());
            stepProgress.setRequired(stepConfig.isRequired());
            stepProgress.setCurrent(stepConfig.getStep().equals(session.getCurrentStep()));
            
            // 检查是否完成
            boolean completed = isStepCompleted(stepConfig, selections);
            stepProgress.setCompleted(completed);
            
            // 检查是否跳过
            boolean skipped = isStepSkipped(stepConfig, selections);
            stepProgress.setSkipped(skipped);
            
            stepProgressList.add(stepProgress);
        }
        
        progress.setSteps(stepProgressList);
        
        // 计算进度百分比
        long completedSteps = stepProgressList.stream()
            .filter(step -> step.isCompleted() || step.isSkipped())
            .count();
        progress.setCompletedSteps((int) completedSteps);
        progress.setTotalSteps(stepProgressList.size());
        progress.setProgressPercentage((int) (completedSteps * 100 / stepProgressList.size()));
        
        return progress;
    }
    
    @Override
    public void cancelCreation(AiCharacterCreationSession session) {
        session.setStatus("cancelled");
        sessionMapper.updateAiCharacterCreationSession(session);
    }
    
    @Override
    public AiCharacterCreationSession getSessionBySessionId(String sessionId) {
        return sessionMapper.selectAiCharacterCreationSessionBySessionId(sessionId);
    }
    
    @Override
    public AiCharacterCreationSession getActiveSessionByUserId(Long userId) {
        return sessionMapper.selectActiveSessionByUserId(userId);
    }
    
    // 私有辅助方法
    private CharacterCreationConfig.OptionConfig createOption(String value, String label, String imageUrl, 
                                                             String icon, String description, 
                                                             List<String> skipSteps, boolean locked) {
        CharacterCreationConfig.OptionConfig option = new CharacterCreationConfig.OptionConfig();
        option.setValue(value);
        option.setLabel(label);
        option.setImageUrl(imageUrl);
        option.setIcon(icon);
        option.setDescription(description);
        option.setSkipSteps(skipSteps);
        option.setLocked(locked);
        return option;
    }
    
    private CharacterCreationConfig.SectionConfig createEthnicitySection() {
        CharacterCreationConfig.SectionConfig section = new CharacterCreationConfig.SectionConfig();
        section.setName("ethnicity");
        section.setTitle("Choose Ethnicity*");
        section.setOptions(Arrays.asList(
            createOption("caucasian", "Caucasian", "/images/ethnicity/caucasian.jpg", null, null, null, false),
            createOption("latina", "Latina", "/images/ethnicity/latina.jpg", null, null, null, false),
            createOption("asian", "Asian", "/images/ethnicity/asian.jpg", null, null, null, false)
        ));
        return section;
    }
    
    private CharacterCreationConfig.SectionConfig createAgeSection() {
        CharacterCreationConfig.SectionConfig section = new CharacterCreationConfig.SectionConfig();
        section.setName("age");
        section.setTitle("Choose Age");
        section.setOptions(Arrays.asList(
            createOption("teen", "Teen (18+)", null, null, null, null, false),
            createOption("20s", "20s", null, null, null, null, false)
        ));
        return section;
    }
    
    private CharacterCreationConfig.SectionConfig createEyeColorSection() {
        CharacterCreationConfig.SectionConfig section = new CharacterCreationConfig.SectionConfig();
        section.setName("eyeColor");
        section.setTitle("Choose Eye Color*");
        section.setOptions(Arrays.asList(
            createOption("brown", "Brown", "/images/eyes/brown.jpg", null, null, null, false),
            createOption("blue", "Blue", "/images/eyes/blue.jpg", null, null, null, false),
            createOption("green", "Green", "/images/eyes/green.jpg", null, null, null, true)
        ));
        return section;
    }
    
    private CharacterCreationConfig.SectionConfig createHairStyleSection() {
        CharacterCreationConfig.SectionConfig section = new CharacterCreationConfig.SectionConfig();
        section.setName("hairStyle");
        section.setTitle("Choose Hair Style*");
        section.setOptions(Arrays.asList(
            createOption("straight", "Straight", "/images/hair/straight.jpg", null, null, null, false),
            createOption("short", "Short", "/images/hair/short.jpg", null, null, null, false),
            createOption("long", "Long", "/images/hair/long.jpg", null, null, null, false)
        ));
        return section;
    }
    
    private CharacterCreationConfig.SectionConfig createHairColorSection() {
        CharacterCreationConfig.SectionConfig section = new CharacterCreationConfig.SectionConfig();
        section.setName("hairColor");
        section.setTitle("Choose Hair Color*");
        section.setOptions(Arrays.asList(
            createOption("blonde", "Blonde", null, null, null, null, false),
            createOption("brunette", "Brunette", null, null, null, null, false),
            createOption("black", "Black", null, null, null, null, false)
        ));
        return section;
    }
    
    private CharacterCreationConfig.SectionConfig createBodyTypeSection() {
        CharacterCreationConfig.SectionConfig section = new CharacterCreationConfig.SectionConfig();
        section.setName("bodyType");
        section.setTitle("Choose Body Type*");
        section.setOptions(Arrays.asList(
            createOption("slim", "Slim", "/images/body/slim.jpg", null, null, null, false),
            createOption("athletic", "Athletic", "/images/body/athletic.jpg", null, null, null, false),
            createOption("voluptuous", "Voluptuous", "/images/body/voluptuous.jpg", null, null, null, false)
        ));
        return section;
    }
    
    private CharacterCreationConfig.SectionConfig createBreastSizeSection() {
        CharacterCreationConfig.SectionConfig section = new CharacterCreationConfig.SectionConfig();
        section.setName("breastSize");
        section.setTitle("Choose Breast Size*");
        section.setOptions(Arrays.asList(
            createOption("medium", "Medium", "/images/breast/medium.jpg", null, null, null, false),
            createOption("large", "Large", "/images/breast/large.jpg", null, null, null, false),
            createOption("huge", "Huge", "/images/breast/huge.jpg", null, null, null, false)
        ));
        return section;
    }
    
    private CharacterCreationConfig.SectionConfig createButtSizeSection() {
        CharacterCreationConfig.SectionConfig section = new CharacterCreationConfig.SectionConfig();
        section.setName("buttSize");
        section.setTitle("Choose Butt Size*");
        section.setOptions(Arrays.asList(
            createOption("medium", "Medium", "/images/butt/medium.jpg", null, null, null, false),
            createOption("large", "Large", "/images/butt/large.jpg", null, null, null, false),
            createOption("athletic", "Athletic", "/images/butt/athletic.jpg", null, null, null, false)
        ));
        return section;
    }
    
    private List<CharacterCreationConfig.OptionConfig> createPersonalityOptions() {
        return Arrays.asList(
            createOption("caregiver", "Caregiver", null, "heart", "Nurturing, protective, and always there to offer comfort.", null, false),
            createOption("sage", "Sage", null, "brain", "Wise, reflective, and a source of guidance.", null, false),
            createOption("innocent", "Innocent", null, "star", "Optimistic, naive, and sees world with wonder.", null, false),
            createOption("jester", "Jester", null, "hat", "Playful, humorous, and always there to make you laugh.", null, false),
            createOption("temptress", "Temptress", null, "flask", "Flirtatious, playful, and always leaving you wanting more.", null, false),
            createOption("dominant", "Dominant", null, "crown", "Assertive, controlling, and commanding.", null, false),
            createOption("submissive", "Submissive", null, "handcuffs", "Obedient, yielding, and happy to follow.", null, false),
            createOption("lover", "Lover", null, "heart-pink", "Romantic, affectionate, and cherishes deep emotional.", null, false),
            createOption("nympho", "Nympho", null, "heart-fire", "Insatiable, passionate, and constantly craving intimacy.", null, false),
            createOption("mean", "Mean", null, "snowflake", "Cold, dismissive, and often sarcastic.", null, false),
            createOption("confidant", "Confidant", null, "hands", "Trustworthy, a good listener, and always can offer advice.", null, false),
            createOption("experimenter", "Experimenter", null, "atom", "Curious, willing, and always eager to try new things.", null, false)
        );
    }
    
    private CharacterCreationConfig.SectionConfig createOccupationSection() {
        CharacterCreationConfig.SectionConfig section = new CharacterCreationConfig.SectionConfig();
        section.setName("occupation");
        section.setTitle("Choose Occupation*");
        section.setOptions(Arrays.asList(
            createOption("model", "Model", null, null, null, null, false),
            createOption("doctor", "Doctor", null, null, null, null, false),
            createOption("teacher", "Teacher", null, null, null, null, false),
            createOption("nurse", "Nurse", null, null, null, null, false),
            createOption("student", "Student", null, null, null, null, false),
            createOption("artist", "Artist", null, null, null, null, false),
            createOption("singer", "Singer/Musician", null, null, null, null, false),
            createOption("actress", "Movie Star/Actress", null, null, null, null, false),
            createOption("scientist", "Scientist", null, null, null, null, false),
            createOption("lawyer", "Lawyer", null, null, null, null, false),
            createOption("writer", "Writer", null, null, null, null, false),
            createOption("photographer", "Photographer", null, null, null, null, false),
            createOption("athlete", "Professional Athlete", null, null, null, null, false),
            createOption("pilot", "Plane Pilot", null, null, null, null, false),
            createOption("spy", "Spy", null, null, null, null, false),
            createOption("dancer", "Dancer", null, null, null, null, false),
            createOption("soldier", "Soldier", null, null, null, null, false),
            createOption("police", "Police Officer", null, null, null, null, false),
            createOption("firefighter", "Firefighter", null, null, null, null, false),
            createOption("coach", "Life Coach", null, null, null, null, false),
            createOption("kindergarten", "Kindergarten Teacher", null, null, null, null, false),
            createOption("florist", "Florist", null, null, null, null, false),
            createOption("baker", "Baker", null, null, null, null, false),
            createOption("jewelry", "Jewelry Designer", null, null, null, null, false),
            createOption("military", "Soldier/Military Personnel", null, null, null, null, false),
            createOption("astronaut", "Astronaut", null, null, null, null, false),
            createOption("massage", "Massage Therapist", null, null, null, null, false),
            createOption("dentist", "Dentist", null, null, null, null, false),
            createOption("nutritionist", "Nutritionist", null, null, null, null, false),
            createOption("fitness", "Fitness Coach", null, null, null, null, false),
            createOption("pharmacist", "Pharmacist", null, null, null, null, false),
            createOption("hairdresser", "Hairdresser", null, null, null, null, false),
            createOption("makeup", "Makeup Artist", null, null, null, null, false),
            createOption("gynecologist", "Gynecologist", null, null, null, null, false),
            createOption("librarian", "Librarian", null, null, null, null, false),
            createOption("secretary", "Secretary", null, null, null, null, false),
            createOption("social_worker", "Social Worker", null, null, null, null, false),
            createOption("fashion_designer", "Fashion Designer", null, null, null, null, false),
            createOption("interior_designer", "Interior Designer", null, null, null, null, false),
            createOption("cook", "Cook", null, null, null, null, false),
            createOption("designer", "Designer", null, null, null, null, false),
            createOption("stylist", "Stylist", null, null, null, null, false),
            createOption("esthetician", "Esthetician", null, null, null, null, false),
            createOption("yoga", "Yoga Instructor", null, null, null, null, false),
            createOption("flight_attendant", "Flight Attendant", null, null, null, null, false)
        ));
        return section;
    }
    
    private CharacterCreationConfig.SectionConfig createHobbiesSection() {
        CharacterCreationConfig.SectionConfig section = new CharacterCreationConfig.SectionConfig();
        section.setName("hobbies");
        section.setTitle("Choose Hobbies*");
        section.setMaxSelect(3);
        section.setDescription("You can choose up to 3 variants");
        section.setOptions(Arrays.asList(
            createOption("photography", "Photography", null, null, null, null, false),
            createOption("fitness", "Fitness", null, null, null, null, false),
            createOption("vlogging", "Vlogging", null, null, null, null, false),
            createOption("traveling", "Traveling", null, null, null, null, false),
            createOption("hiking", "Hiking", null, null, null, null, false),
            createOption("gaming", "Gaming", null, null, null, null, false),
            createOption("parties", "Parties", null, null, null, null, false),
            createOption("series", "Series", null, null, null, null, false),
            createOption("anime", "Anime", null, null, null, null, false),
            createOption("cosplay", "Cosplay", null, null, null, null, false),
            createOption("self_development", "Self-Development", null, null, null, null, false),
            createOption("writing", "Writing", null, null, null, null, false),
            createOption("diy_crafting", "DIY Crafting", null, null, null, null, false),
            createOption("veganism", "Veganism", null, null, null, null, false),
            createOption("volunteering", "Volunteering", null, null, null, null, false),
            createOption("cars", "Cars", null, null, null, null, false),
            createOption("art", "Art", null, null, null, null, false),
            createOption("netflix", "Watching Netflix", null, null, null, null, false),
            createOption("manga_anime", "Manga And Anime", null, null, null, null, false),
            createOption("martial_arts", "Martial Arts", null, null, null, null, false)
        ));
        return section;
    }
    
    private List<CharacterCreationConfig.OptionConfig> createRelationshipOptions() {
        return Arrays.asList(
            createOption("stranger", "Stranger", null, "fedora", null, null, false),
            createOption("school_mate", "School Mate", null, "graduation_cap", null, null, false),
            createOption("colleague", "Colleague", null, "briefcase", null, null, false),
            createOption("mentor", "Mentor", null, "diamond_books", null, null, false),
            createOption("girlfriend", "Girlfriend", null, "heart", null, null, false),
            createOption("sex_friend", "Sex Friend", null, "gender_symbols", null, null, false),
            createOption("wife", "Wife", null, "wedding_rings", null, null, false),
            createOption("mistress", "Mistress", null, "crown_heart", null, null, false),
            createOption("friend", "Friend", null, "clapping_hands", null, null, false),
            createOption("best_friend", "Best Friend", null, "friendship_hands", null, null, false),
            createOption("step_sister", "Step Sister", null, "heart_locket", null, null, false),
            createOption("step_mom", "Step Mom", null, "heart_flame", null, null, false)
        );
    }
    
    private Map<String, Object> getCurrentSelections(AiCharacterCreationSession session) {
        if (session.getSelections() == null || session.getSelections().isEmpty()) {
            return new HashMap<>();
        }
        try {
            return JSON.parseObject(session.getSelections(), Map.class);
        } catch (Exception e) {
            return new HashMap<>();
        }
    }
    
    private String generateSessionId() {
        return "session_" + System.currentTimeMillis() + "_" + UUID.randomUUID().toString().substring(0, 8);
    }
    
    private String generateCharacterName(Map<String, Object> selections) {
        // 简单的角色名称生成逻辑
        String style = (String) selections.get("style");
        String personality = (String) selections.get("personality");
        return style + "_" + personality + "_" + System.currentTimeMillis();
    }
    
    private String generateCharacterDescription(Map<String, Object> selections) {
        // 简单的角色描述生成逻辑
        StringBuilder description = new StringBuilder();
        description.append("A ").append(selections.get("style")).append(" character");
        if (selections.get("personality") != null) {
            description.append(" with ").append(selections.get("personality")).append(" personality");
        }
        return description.toString();
    }
    
    private boolean isStepCompleted(CharacterCreationConfig.StepConfig stepConfig, Map<String, Object> selections) {
        if (stepConfig.getSections() != null) {
            // 多分组步骤，检查每个分组是否完成
            for (CharacterCreationConfig.SectionConfig section : stepConfig.getSections()) {
                if (!selections.containsKey(section.getName())) {
                    return false;
                }
            }
            return true;
        } else if (stepConfig.getOptions() != null) {
            // 单选步骤，检查是否有选择
            for (CharacterCreationConfig.OptionConfig option : stepConfig.getOptions()) {
                if (selections.containsKey(stepConfig.getStep()) && 
                    selections.get(stepConfig.getStep()).equals(option.getValue())) {
                    return true;
                }
            }
        }
        return false;
    }
    
    private boolean isStepSkipped(CharacterCreationConfig.StepConfig stepConfig, Map<String, Object> selections) {
        if ("occupation_hobbies".equals(stepConfig.getStep())) {
            String style = (String) selections.get("style");
            return "realistic".equals(style);
        }
        return false;
    }
} 