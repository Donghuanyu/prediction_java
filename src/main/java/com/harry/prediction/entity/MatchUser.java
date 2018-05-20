package com.harry.prediction.entity;

/**
 * 第三放（知乎）用户
 * note：字段属性中，问题有关的字段，每个问题以符号“|”分开
 */
public class MatchUser {

    private String id;

    private String name;

    private String token;

    private String avatarUrl;

    /**
     * 性别：男、女、未知
     */
    private String gender;

    /**
     * 公司
     */
    private String employment;

    /**
     * 行业
     */
    private String business;

    /**
     * 创建的问题
     */
    private String answerCreate;

    /**
     * 回答的问题
     */
    private String questionFollow;

    /**
     * 投票的问题
     */
    private String answerVoteUp;

    /**
     * 投票的答案
     */
    private String answerVoteUpAnswer;

    /**
     * 点赞次数
     */
    private int followingCount;

    /**
     * 被点赞次数
     */
    private int followerCount;

    /**
     * 回答次数
     */
    private int answerCount;

    /**
     * 文章数量
     */
    private int articleCount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmployment() {
        return employment;
    }

    public void setEmployment(String employment) {
        this.employment = employment;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public String getAnswerCreate() {
        return answerCreate;
    }

    public void setAnswerCreate(String answerCreate) {
        this.answerCreate = answerCreate;
    }

    public String getQuestionFollow() {
        return questionFollow;
    }

    public void setQuestionFollow(String questionFollow) {
        this.questionFollow = questionFollow;
    }

    public String getAnswerVoteUp() {
        return answerVoteUp;
    }

    public void setAnswerVoteUp(String answerVoteUp) {
        this.answerVoteUp = answerVoteUp;
    }

    public String getAnswerVoteUpAnswer() {
        return answerVoteUpAnswer;
    }

    public void setAnswerVoteUpAnswer(String answerVoteUpAnswer) {
        this.answerVoteUpAnswer = answerVoteUpAnswer;
    }

    public int getFollowingCount() {
        return followingCount;
    }

    public void setFollowingCount(int followingCount) {
        this.followingCount = followingCount;
    }

    public int getFollowerCount() {
        return followerCount;
    }

    public void setFollowerCount(int followerCount) {
        this.followerCount = followerCount;
    }

    public int getAnswerCount() {
        return answerCount;
    }

    public void setAnswerCount(int answerCount) {
        this.answerCount = answerCount;
    }

    public int getArticleCount() {
        return articleCount;
    }

    public void setArticleCount(int articleCount) {
        this.articleCount = articleCount;
    }
}
